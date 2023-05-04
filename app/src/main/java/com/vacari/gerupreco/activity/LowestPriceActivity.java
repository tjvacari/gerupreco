package com.vacari.gerupreco.activity;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vacari.gerupreco.adapter.LowestAdapterAdapter;
import com.vacari.gerupreco.R;
import com.vacari.gerupreco.retrofit.RetrofitRequest;

import java.util.ArrayList;

public class LowestPriceActivity extends AppCompatActivity {

    private LowestAdapterAdapter mAdapter;
    private static ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lowest_price);

        String barCode = getIntent().getExtras().getString("BARCODE");

        RecyclerView mRecyclerView = findViewById(R.id.recycler_preco_id);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mAdapter = new LowestAdapterAdapter(new ArrayList<>(), this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        search(barCode);
    }

    private void search(String barCode) {
        showProgress();

        RetrofitRequest.searchLowestPrice(barCode, this, mAdapter);
    }

    private void showProgress() {
        progressDialog = ProgressDialog.show(this, getResources().getString(R.string.app_name),
                "Buscando...", true);
    }

    public void closeProgress() {
        if(progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}