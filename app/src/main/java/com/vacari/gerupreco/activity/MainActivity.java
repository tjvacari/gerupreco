package com.vacari.gerupreco.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;
import com.vacari.gerupreco.R;
import com.vacari.gerupreco.adapter.ItemAdapter;
import com.vacari.gerupreco.dialog.GenericDialog;
import com.vacari.gerupreco.dialog.RegisterProductDialog;
import com.vacari.gerupreco.model.Item;
import com.vacari.gerupreco.repository.ItemRepository;
import com.vacari.gerupreco.update.UpdateJob;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ItemAdapter mAdapter;

    private ActivityResultLauncher<ScanOptions> barcodeLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UpdateJob.initJobUpdate(this);
        setContentView(R.layout.activity_main);

        initGUI();
        configureActions();
        registerResults();
        searchItems();
    }

    private void initGUI() {
        RecyclerView mRecyclerView = findViewById(R.id.main_recycler);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mAdapter = new ItemAdapter(new ArrayList<>(), this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void configureActions() {
        SwipeRefreshLayout swipe = findViewById(R.id.main_swipe);
        swipe.setOnRefreshListener(() -> searchItems());
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
        ScanOptions options = new ScanOptions();
        // TODO realizar consulta na nota parana quando ler qrcode
//        options.setDesiredBarcodeFormats(ScanOptions.EAN_13, ScanOptions.EAN_8);
        options.setDesiredBarcodeFormats(ScanOptions.EAN_13);
        options.setBeepEnabled(false);
        options.setOrientationLocked(false);
        barcodeLauncher.launch(options);
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
        ItemRepository.searchItem(data -> {
            mAdapter.refresh(data);
            SwipeRefreshLayout swipe = findViewById(R.id.main_swipe);
            swipe.setRefreshing(false);
        });
    }

    public void openLowestPrice(String barCode) {
        Intent intent = new Intent(this, LowestPriceActivity.class);
        intent.putExtra("BARCODE", barCode);
        startActivity(intent);
    }

    public void deleteItem(Item item) {
        GenericDialog.showConfirmDeleteDialog(this, data -> ItemRepository.delete(item.getId(), dat -> searchItems()));
    }

    public boolean existProduct(String barCode) {
        if(mAdapter.existProduct(barCode)) {
            GenericDialog.showDialogError(this, getString(R.string.product_duplicate));
            return true;
        }

        return false;
    }
}