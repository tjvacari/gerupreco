package com.vacari.gerupreco.dialog.lowestprice;

import android.content.DialogInterface;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.vacari.gerupreco.R;
import com.vacari.gerupreco.activity.lowestprice.LowestPriceProduct;
import com.vacari.gerupreco.model.firebase.Item;
import com.vacari.gerupreco.repository.ItemRepository;
import com.vacari.gerupreco.util.Callback;

public class RegisterProductDialog {

    private LowestPriceProduct context;
    private String barCode;
    private View dialog;
    private Item item;
    private boolean isNew;

    public RegisterProductDialog(LowestPriceProduct context, String barCode, Item item) {
        this.context = context;
        this.barCode = barCode;
        this.item = item;
        this.isNew = item == null;

        initGUI();
        setInfo();
    }

    private void initGUI() {
        dialog = context.getLayoutInflater().inflate(R.layout.register_dialog, null);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.unit_measurement, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner editUnitMeasure = dialog.findViewById(R.id.edit_unitMeasure);
        editUnitMeasure.setAdapter(adapter);
        editUnitMeasure.setSelection(0);
    }

    private void setInfo() {
        EditText editBarCode = dialog.findViewById(R.id.edit_barCode);
        EditText editDescription = dialog.findViewById(R.id.edit_description);
        EditText editSize = dialog.findViewById(R.id.edit_size);
        Spinner editUnitMeasure = dialog.findViewById(R.id.edit_unitMeasure);

        if(barCode != null) {
            editBarCode.setText(barCode);
        }

        if(!isNew) {
            editBarCode.setText(item.getBarCode());
            editDescription.setText(item.getDescription());
            editSize.setText(item.getSize());

            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.unit_measurement, android.R.layout.simple_spinner_item);
            editUnitMeasure.setSelection(adapter.getPosition(item.getUnitMeasure()));
        }
    }

    public void show() {
        new MaterialAlertDialogBuilder(context)
                .setTitle(isNew ? R.string.new_product : R.string.edit)
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

        if(isNew && context.existProduct(editBarCode.getText().toString())) {
            return false;
        }

        EditText description = dialog.findViewById(R.id.edit_description);
        EditText size = dialog.findViewById(R.id.edit_size);
        Spinner unitMeasure = dialog.findViewById(R.id.edit_unitMeasure);

        Item item = new Item();
        item.setDescription(description.getText().toString());
        item.setBarCode(editBarCode.getText().toString());
        item.setSize(size.getText().toString());
        item.setUnitMeasure(unitMeasure.getSelectedItem().toString());

        if(isNew) {
            ItemRepository.save(item, (Callback<Item>) data -> context.searchItems());
        } else {
            item.setId(this.item.getId());
            ItemRepository.update(item, (Callback<Item>) data -> context.searchItems());
        }
        return true;
    }
}
