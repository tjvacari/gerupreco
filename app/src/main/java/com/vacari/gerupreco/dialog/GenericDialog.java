package com.vacari.gerupreco.dialog;

import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.vacari.gerupreco.R;
import com.vacari.gerupreco.model.Item;
import com.vacari.gerupreco.util.Callback;

public class GenericDialog {

    public static void showDialogError(AppCompatActivity context, String message) {
        context.runOnUiThread(() -> {
            new AlertDialog.Builder(context)
                    .setTitle(context.getString(R.string.error))
                    .setMessage(message)
                    .setPositiveButton(R.string.ok, null)
                    .show();
        });
    }

    public static void showConfirmDeleteDialog(AppCompatActivity context, Callback<Item> callback) {
        context.runOnUiThread(() -> {
            DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        callback.callback(null);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.dismiss();
                        break;
                }
            };

            new AlertDialog.Builder(context).setTitle(R.string.app_name).setMessage(R.string.delete_dialog)
                    .setPositiveButton(R.string.delete, dialogClickListener).setNegativeButton(R.string.cancel, dialogClickListener).setCancelable(false).show();
        });
    }
}
