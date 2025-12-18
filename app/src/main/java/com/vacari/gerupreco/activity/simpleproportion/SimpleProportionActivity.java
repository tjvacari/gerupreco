package com.vacari.gerupreco.activity.simpleproportion;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vacari.gerupreco.R;

import java.util.ArrayList;
import java.util.List;

public class SimpleProportionActivity extends AppCompatActivity {

    private EditText value1;
    private EditText value2;
    private LinearLayout dynamicRowsContainer;
    private FloatingActionButton btnAddRow;
    private List<RowViews> rows = new ArrayList<>();

    private static class RowViews {
        View root;
        EditText value3;
        EditText value4;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_proportion);

        value1 = findViewById(R.id.value1);
        value2 = findViewById(R.id.value2);
        dynamicRowsContainer = findViewById(R.id.dynamicRowsContainer);
        btnAddRow = findViewById(R.id.btnAddRow);

        btnAddRow.setOnClickListener(v -> addNewRow());

        configureBaseActions();
        
        // Adiciona a primeira linha automaticamente
        addNewRow();
    }

    private void configureBaseActions() {
        TextWatcher baseWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                calculateAll();
            }
        };

        value1.addTextChangedListener(baseWatcher);
        value2.addTextChangedListener(baseWatcher);
    }

    private void addNewRow() {
        View rowView = LayoutInflater.from(this).inflate(R.layout.item_proportion_row, dynamicRowsContainer, false);
        RowViews row = new RowViews();
        row.root = rowView;
        row.value3 = rowView.findViewById(R.id.value3);
        row.value4 = rowView.findViewById(R.id.value4);
        
        View btnRemove = rowView.findViewById(R.id.btnRemove);
        btnRemove.setOnClickListener(v -> {
            dynamicRowsContainer.removeView(rowView);
            rows.remove(row);
        });

        row.value3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                calculateRow(row);
            }
        });

        rows.add(row);
        dynamicRowsContainer.addView(rowView);
    }

    private void calculateAll() {
        for (RowViews row : rows) {
            calculateRow(row);
        }
    }

    private void calculateRow(RowViews row) {
        try {
            String s1 = value1.getText().toString().replace(",", ".");
            String s2 = value2.getText().toString().replace(",", ".");
            String s3 = row.value3.getText().toString().replace(",", ".");

            if (s1.isEmpty() || s2.isEmpty() || s3.isEmpty()) {
                row.value4.setText("");
                return;
            }

            double v1 = Double.parseDouble(s1);
            double v2 = Double.parseDouble(s2);
            double v3 = Double.parseDouble(s3);

            if (v1 != 0) {
                double v4 = (v2 * v3) / v1;
                row.value4.setText(String.format("%.2f", v4));
            } else {
                row.value4.setText("");
            }
        } catch (NumberFormatException e) {
            row.value4.setText("");
        }
    }
}
