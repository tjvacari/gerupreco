package com.vacari.gerupreco.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.vacari.gerupreco.R;
import com.vacari.gerupreco.activity.idea.IdeaActivity;
import com.vacari.gerupreco.activity.lowestprice.LowestPriceProduct;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configureActions();
    }

    private void configureActions() {
        findViewById(R.id.card_gerometro).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, IdeaActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.card_gerupreco).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LowestPriceProduct.class);
            startActivity(intent);
        });
    }

}