package com.vacari.gerupreco;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.vacari.gerupreco.to.ItemTO;
import com.vacari.gerupreco.update.UpdateJob;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static Context contextOfApplication;

    private ItemsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UpdateJob.initJobUpdate(this);
        contextOfApplication = getApplicationContext();
        setContentView(R.layout.activity_main);

        initGUI();
        searchItems();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initGUI() {
        RecyclerView mRecyclerView = findViewById(R.id.recycler_id);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mAdapter = new ItemsAdapter(new ArrayList<ItemTO>(), this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.configuracao_id:
//                new ConfiguracaoDialog(this).show();
//                return true;
//            case R.id.filtro_id:
//                mDialog.show();
//                return true;
//            default:
//                return false;
//        }

        return false;
    }

    public void searchItems() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("item")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<ItemTO> itemTOList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> data = document.getData();
                                ObjectMapper objectMapper = new ObjectMapper();
                                ItemTO item = objectMapper.convertValue(data, ItemTO.class);
                                itemTOList.add(item);
                            }
                            mAdapter.increment(itemTOList);
                        }
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
        this.runOnUiThread(() -> mAdapter.increment(itemList));
    }
}