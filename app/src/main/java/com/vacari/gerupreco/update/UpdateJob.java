package com.vacari.gerupreco.update;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.vacari.gerupreco.R;
import com.vacari.gerupreco.model.AppVersion;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UpdateJob {

    private static AppCompatActivity context;
    private static AlertDialog confirmationDialog;
    private static ProgressDialog progressDialog;

    public static void initJobUpdate(AppCompatActivity context) {
        UpdateJob.context = context;

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("appVersion").addSnapshotListener((value, error) -> {
            List<DocumentChange> documentChanges = value.getDocumentChanges();
            if(documentChanges != null && documentChanges.size() == 1) {
                Map<String, Object> data = documentChanges.get(0).getDocument().getData();

                ObjectMapper objectMapper = new ObjectMapper();
                AppVersion appVersionTO = objectMapper.convertValue(data, AppVersion.class);

                checkVerisonCode(appVersionTO);
            }
        });
    }

    private static void checkVerisonCode(AppVersion appVersionTO) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo currentApp = packageManager.getPackageInfo(context.getPackageName(), 0);

            if(currentApp.versionCode < appVersionTO.getVersionCode()) {
                showDialogUpdate(appVersionTO);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void showDialogUpdate(AppVersion appVersionTO) {
        if((confirmationDialog != null && confirmationDialog.isShowing()) ||
                (progressDialog != null && progressDialog.isShowing())) {
            return;
        }

        DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    initUpdate(appVersionTO);
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    context.finishAffinity();
                    break;
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        confirmationDialog = builder.setTitle(R.string.app_name).setMessage(R.string.update_dialog).setPositiveButton(R.string.update, dialogClickListener)
                .setNegativeButton(R.string.exit, dialogClickListener).setCancelable(false).show();
    }

    private static void initUpdate(AppVersion appVersionTO) {
        if(requestInstallUnknownApp()) {
            return;
        }

        showProgress();

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(() -> {

            deleteDir();
            File outputFile = getFileApk();

            try {
                outputFile.createNewFile();

                URL u = new URL(appVersionTO.getUrl());
                URLConnection conn = u.openConnection();
                int contentLength = conn.getContentLength();

                DataInputStream stream = new DataInputStream(u.openStream());

                byte[] buffer = new byte[contentLength];
                stream.readFully(buffer);
                stream.close();

                DataOutputStream fos = new DataOutputStream(new FileOutputStream(outputFile));
                fos.write(buffer);
                fos.flush();
                fos.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            handler.post(() -> {
                closeProgress();
                installApk(outputFile);
            });
        });
    }

    public static void deleteDir() {
        for (File f : context.getFilesDir().listFiles()) {
            f.delete();
        }
    }

    private static boolean requestInstallUnknownApp() {
        if (!context.getPackageManager().canRequestPackageInstalls()) {
            DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        context.startActivity(new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES).setData(Uri.parse(String.format("package:%s", context.getPackageName()))));
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        context.finishAffinity();
                        break;
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            confirmationDialog = builder.setTitle(R.string.app_name).setMessage(R.string.install_unknown)
                    .setPositiveButton(R.string.update, dialogClickListener).setCancelable(false).show();

            return true;
        }
        return false;
    }

    private static void installApk(File outputFile) {
        Uri apk = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", outputFile);
        Intent intent = new Intent(Intent.ACTION_INSTALL_PACKAGE);
        intent.setDataAndType(apk, "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        context.startActivity(intent);
    }

    private static void showProgress() {
        progressDialog = ProgressDialog.show(context, context.getString(R.string.app_name),
                context.getString(R.string.downloading), true);
    }

    private static void closeProgress() {
        if(progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private static File getFileApk() {
        long current = System.currentTimeMillis();
        File filesDir = new File(context.getFilesDir(), "app-" + current + ".apk");
        if(!filesDir.exists()) {
            filesDir.mkdirs();
        }
        return filesDir;
    }
}
