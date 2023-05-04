package com.vacari.gerupreco.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.vacari.gerupreco.R;
import com.vacari.gerupreco.activity.MainActivity;
import com.vacari.gerupreco.model.Item;
import com.vacari.gerupreco.repository.CallbackRepo;
import com.vacari.gerupreco.repository.ItemRepository;

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
        Spinner spinner = (Spinner) dialog.findViewById(R.id.unidadeMedida);
        EditText editCodBarras = dialog.findViewById(R.id.editBarCode);
        EditText descricao = dialog.findViewById(R.id.editDescricao);
        EditText tamanho = dialog.findViewById(R.id.editTamanho);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.unit_measurement, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);

        editCodBarras.setText(barCode);

        new MaterialAlertDialogBuilder(context)
                .setTitle("Novo Produto")
                .setView(dialog)
                .setPositiveButton("Salvar", (DialogInterface dialogInterface, int i) -> {
                    saveProduct(dialog);
                })
                .setNegativeButton("Cancelar", (DialogInterface dialogInterface, int i) -> {
                }).show();
    }

    private void saveProduct(View dialog) {
        EditText editBarCode = dialog.findViewById(R.id.editBarCode);
        EditText description = dialog.findViewById(R.id.editDescricao);
        EditText size = dialog.findViewById(R.id.editTamanho);
        Spinner unitMeasure = dialog.findViewById(R.id.unidadeMedida);

        Map<String, Object> item = new HashMap<>();
        item.put("barCode", editBarCode.getText().toString());
        item.put("description", description.getText().toString());
        item.put("size", size.getText().toString());
        item.put("unitMeasure", unitMeasure.getSelectedItem().toString());

        ItemRepository.save(item, (CallbackRepo<Item>) data -> {
            context.searchItems();
        });
    }
}
