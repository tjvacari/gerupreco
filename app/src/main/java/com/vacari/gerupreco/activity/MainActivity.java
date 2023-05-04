package com.vacari.gerupreco.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;
import com.vacari.gerupreco.adapter.ItemsAdapter;
import com.vacari.gerupreco.R;
import com.vacari.gerupreco.dialog.RegisterProductDialog;
import com.vacari.gerupreco.model.Item;
import com.vacari.gerupreco.repository.CallbackRepo;
import com.vacari.gerupreco.repository.ItemRepository;
import com.vacari.gerupreco.update.UpdateJob;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ItemsAdapter mAdapter;

    private ActivityResultLauncher<ScanOptions> barcodeLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UpdateJob.initJobUpdate(this);
        setContentView(R.layout.activity_main);

        initGUI();
        registerResults();
        searchItems();
    }

    private void registerResults() {
        barcodeLauncher = registerForActivityResult(new ScanContract(),
                result -> {
                    if(result.getContents() != null) {
                        new RegisterProductDialog(MainActivity.this, result.getContents()).show();
                    }
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
        ItemRepository.searchItem(data -> mAdapter.refresh(data));
    }
}