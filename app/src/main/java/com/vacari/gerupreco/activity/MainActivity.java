package com.vacari.gerupreco.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.vacari.gerupreco.R;
import com.vacari.gerupreco.activity.lowestprice.LowestPriceProduct;
import com.vacari.gerupreco.activity.simpleproportion.SimpleProportionActivity;
import com.vacari.gerupreco.update.UpdateJob;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UpdateJob.initJobUpdate(this);
    }

    public void configureActions() {
        findViewById(R.id.card_gerometro).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SimpleProportionActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.card_gerupreco).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LowestPriceProduct.class);
            startActivity(intent);
        });
    }

}