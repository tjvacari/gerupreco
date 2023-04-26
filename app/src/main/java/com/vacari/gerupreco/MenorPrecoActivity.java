package com.vacari.gerupreco;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MenorPrecoActivity extends AppCompatActivity {

    private MenorPrecoAdapter mAdapter;
    private static ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menor_preco);

        String barCode = getIntent().getExtras().getString("BARCODE");

        RecyclerView mRecyclerView = findViewById(R.id.recycler_preco_id);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mAdapter = new MenorPrecoAdapter(new ArrayList<>(), this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        search(barCode);
    }

    private void search(String barCode) {
        showProgress();

        RetrofitRequest.buscarEmpreendimento(barCode, this, mAdapter);
    }

    public void showDialogError(String message) {
        this.runOnUiThread(() -> {
            new AlertDialog.Builder(MenorPrecoActivity.this)
                    .setTitle("Erro")
                    .setMessage(message)
                    .show();
        });
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