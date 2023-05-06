package com.vacari.gerupreco.dialog;

import android.content.DialogInterface;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.vacari.gerupreco.R;
import com.vacari.gerupreco.activity.MainActivity;
import com.vacari.gerupreco.model.Item;
import com.vacari.gerupreco.repository.ItemRepository;
import com.vacari.gerupreco.util.Callback;

import java.util.HashMap;
import java.util.Map;

public class RegisterProductDialog {

    private MainActivity context;
    private String barCode;

    public RegisterProductDialog(MainActivity context, String barCode) {
        this.context = context;
        this.barCode = barCode;
    }

    public void show() {
        View dialog = context.getLayoutInflater().inflate(R.layout.register_dialog, null);
        Spinner spinner = (Spinner) dialog.findViewById(R.id.edit_unitMeasure);
        EditText editCodBarras = dialog.findViewById(R.id.edit_barCode);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.unit_measurement, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);

        editCodBarras.setText(barCode);

        new MaterialAlertDialogBuilder(context)
                .setTitle(R.string.new_product)
                .setCancelable(false)
                .setView(dialog)
                .setPositiveButton(R.string.save, (DialogInterface dialogInterface, int i) -> {
                    if(saveProduct(dialog)) {
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton(R.string.cancel, (DialogInterface dialogInterface, int i) -> {
                    dialogInterface.dismiss();
                }).show();
    }

    private boolean saveProduct(View dialog) {
        EditText editBarCode = dialog.findViewById(R.id.edit_barCode);

        if(context.existProduct(editBarCode.getText().toString())) {
            return false;
        }

        EditText description = dialog.findViewById(R.id.edit_description);
        EditText size = dialog.findViewById(R.id.edit_size);
        Spinner unitMeasure = dialog.findViewById(R.id.edit_unitMeasure);

        Map<String, Object> item = new HashMap<>();
        item.put("barCode", editBarCode.getText().toString());
        item.put("description", description.getText().toString());
        item.put("size", size.getText().toString());
        item.put("unitMeasure", unitMeasure.getSelectedItem().toString());

        ItemRepository.save(item, (Callback<Item>) data -> {
            context.searchItems();
        });

        return true;
    }
}
