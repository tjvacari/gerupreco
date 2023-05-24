package com.vacari.gerupreco.activity;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vacari.gerupreco.R;
import com.vacari.gerupreco.adapter.LowestPriceAdapter;
import com.vacari.gerupreco.retrofit.RetrofitRequest;

import java.util.ArrayList;

public class LowestPriceActivity extends AppCompatActivity {

    private LowestPriceAdapter mAdapter;
    private static ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lowest_price);

        String barCode = getIntent().getExtras().getString("BARCODE");
        initGUI();
        search(barCode);
    }

    private void initGUI() {
        RecyclerView mRecyclerView = findViewById(R.id.recycler_price_id);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mAdapter = new LowestPriceAdapter(new ArrayList<>(), this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void search(String barCode) {
        showProgress();
        RetrofitRequest.searchLowestPrice(barCode, this, data -> {
            mAdapter.refresh(data);
            closeProgress();
        });
    }

    private void showProgress() {
        progressDialog = ProgressDialog.show(this, getString(R.string.app_name),
                getString(R.string.search), true);
    }

    public void closeProgress() {
        if(progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public void openMaps() {
//        Uri gmmIntentUri = Uri.parse("geo:0,0?q=1600 Amphitheatre Parkway, Mountain+View, California");
//        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//        mapIntent.setPackage("com.google.android.apps.maps");
//        startActivity(mapIntent);
    }
}