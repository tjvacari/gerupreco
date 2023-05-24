package com.vacari.gerupreco.dialog;

import android.content.DialogInterface;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.vacari.gerupreco.R;
import com.vacari.gerupreco.activity.MainActivity;
import com.vacari.gerupreco.model.firebase.Item;
import com.vacari.gerupreco.model.sqlite.Notification;
import com.vacari.gerupreco.repository.ItemRepository;
import com.vacari.gerupreco.repository.NotificationRepository;
import com.vacari.gerupreco.util.Callback;
import com.vacari.gerupreco.util.StringUtil;

import java.math.BigDecimal;

public class CreateNotificationProductDialog {

    private MainActivity context;
    private Item item;

    public CreateNotificationProductDialog(MainActivity context, Item item) {
        this.context = context;
        this.item = item;
    }

    public void show() {
        View dialog = context.getLayoutInflater().inflate(R.layout.notification_dialog, null);

        new MaterialAlertDialogBuilder(context)
                .setTitle(R.string.new_notification)
                .setCancelable(false)
                .setView(dialog)
                .setPositiveButton(R.string.save, (DialogInterface dialogInterface, int i) -> {
                    if(createNotification(dialog)) {
                        dialogInterface.dismiss();
                        Toast.makeText(context, R.string.notification_success, Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton(R.string.cancel, (DialogInterface dialogInterface, int i) -> {
                    dialogInterface.dismiss();
                }).show();
    }

    private boolean createNotification(View dialog) {
        EditText editPrice = dialog.findViewById(R.id.nt_price);
        if(StringUtil.isEmpty(editPrice.getText().toString())) {
            return false;
        }

        Notification notifcation = new Notification();
        notifcation.setActive(true);
        notifcation.setBarCorde(item.getBarCode());
        notifcation.setDescription(item.getDescription());
        notifcation.setTargetPrice(new BigDecimal(editPrice.getText().toString()));

        NotificationRepository.saveNotification(context, notifcation);
        return true;
    }
}
