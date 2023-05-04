package com.vacari.gerupreco.dialog;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class GenericDialog {

    public static void showDialogError(AppCompatActivity context, String message) {
        context.runOnUiThread(() -> {
            new AlertDialog.Builder(context)
                    .setTitle("Erro")
                    .setMessage(message)
                    .show();
        });
    }
}
