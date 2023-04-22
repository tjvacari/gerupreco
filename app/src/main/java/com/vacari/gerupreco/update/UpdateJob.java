package com.vacari.gerupreco.update;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.vacari.gerupreco.R;
import com.vacari.gerupreco.to.AppVersionTO;

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
                AppVersionTO appVersionTO = objectMapper.convertValue(data, AppVersionTO.class);

                checkVerisonCode(appVersionTO);
            }
        });
    }

    private static void checkVerisonCode(AppVersionTO appVersionTO) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo currentApp = packageManager.getPackageInfo(context.getPackageName(), 0);

            if(getFileApk().exists()) {
                PackageInfo apk = packageManager.getPackageArchiveInfo(getFileApk().getAbsolutePath(), 0);
                if(apk.versionCode > currentApp.versionCode && apk.versionCode == appVersionTO.getVersionCode()) {
                    installApk();
                    return;
                }
            }

            if(currentApp.versionCode < appVersionTO.getVersionCode()) {
                showDialogUpdate(appVersionTO);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void showDialogUpdate(AppVersionTO appVersionTO) {
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
        confirmationDialog = builder.setTitle(R.string.app_name).setMessage(R.string.update_dialog).setPositiveButton(R.string.atualizar, dialogClickListener)
                .setNegativeButton(R.string.sair, dialogClickListener).setCancelable(false).show();
    }

    private static void initUpdate(AppVersionTO appVersionTO) {
        showProgress();

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(() -> {
            try {
                File filesDir = getPathApk();

                if(filesDir.listFiles().length > 0) {
                    for(File file : filesDir.listFiles()) {
                        file.delete();
                    }
                }

                File outputFile = getFileApk();

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
                installApk();
            });
        });
    }

    private static void installApk() {
        if(context.getPackageManager().canRequestPackageInstalls()) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(getFileApk()), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    private static void showProgress() {
        progressDialog = ProgressDialog.show(context, context.getResources().getString(R.string.app_name),
                context.getResources().getString(R.string.downloading), true);
    }

    private static void closeProgress() {
        if(progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private static File getFileApk() {
        File filesDir = new File(getPathApk(), "app.apk");
        if(!filesDir.exists()) {
            filesDir.mkdirs();
        }
        return filesDir;
    }

    private static File getPathApk() {
        return new File(context.getFilesDir(), "apk");
    }
}
