package com.vacari.gerupreco;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;
import com.vacari.gerupreco.to.ItemTO;
import com.vacari.gerupreco.update.UpdateJob;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static Context contextOfApplication;

    private ItemsAdapter mAdapter;

    private ActivityResultLauncher<ScanOptions> barcodeLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UpdateJob.initJobUpdate(this);
        contextOfApplication = getApplicationContext();
        setContentView(R.layout.activity_main);

        initGUI();
        searchItems();

        barcodeLauncher = registerForActivityResult(new ScanContract(),
                result -> {
                    if(result.getContents() != null) {
                        registerProduct(result.getContents());
                    }
                });
    }

    private void registerProduct(String barCode) {
        View dialog = getLayoutInflater().inflate(R.layout.register_dialog, null);
        Spinner spinner = (Spinner) dialog.findViewById(R.id.unidadeMedida);
        EditText editCodBarras = dialog.findViewById(R.id.editBarCode);
        EditText descricao = dialog.findViewById(R.id.editDescricao);
        EditText tamanho = dialog.findViewById(R.id.editTamanho);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.unit_measurement, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);

        EditText editBarCode = dialog.findViewById(R.id.editBarCode);
        editBarCode.setText(barCode);

        new MaterialAlertDialogBuilder(this)
                .setTitle("Novo Produto")
                .setView(dialog)
                .setPositiveButton("Salvar", (DialogInterface dialogInterface, int i) -> {
                    saveProduct(editCodBarras.getText().toString(), descricao.getText().toString(), tamanho.getText().toString(), spinner.getSelectedItem().toString());
                })
                .setNegativeButton("Cancelar", (DialogInterface dialogInterface, int i) -> {
                }).show();
    }

    public void saveProduct(String codBarras, String descricoa, String tamanho, String unidadeMedida) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> user = new HashMap<>();
        user.put("codigoBarras", codBarras);
        user.put("descricao", descricoa);
        user.put("tamanho", tamanho);
        user.put("medida", unidadeMedida);

        db.collection("item")
                .add(user)
                .addOnSuccessListener(documentReference -> {
                    searchItems();
                })
                .addOnFailureListener(e -> {

                });
    }

    public void openScanBarCode() {
        barcodeLauncher.launch(new ScanOptions());
    }

    private void initGUI() {
        RecyclerView mRecyclerView = findViewById(R.id.recycler_id);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mAdapter = new ItemsAdapter(new ArrayList<>(), this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.scan_barcode) {
            openScanBarCode();
            return true;
        }

        return false;
    }

    public void searchItems() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("item")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<ItemTO> itemTOList = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Map<String, Object> data = document.getData();
                            ObjectMapper objectMapper = new ObjectMapper();
                            ItemTO item = objectMapper.convertValue(data, ItemTO.class);
                            itemTOList.add(item);
                        }
                        mAdapter.refresh(itemTOList);
                    }
                });
    }

    public void showDialogError(String message) {
        this.runOnUiThread(() -> {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Erro")
                    .setMessage(message)
                    .show();
        });
    }

    private void updateAdapter(final List<ItemTO> itemList) {
        this.runOnUiThread(() -> mAdapter.update(itemList));
    }

    private void incrementAdapter(final List<ItemTO> itemList) {
//        this.runOnUiThread(() -> mAdapter.increment(itemList));
    }
}