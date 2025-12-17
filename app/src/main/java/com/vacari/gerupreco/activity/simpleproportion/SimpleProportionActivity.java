package com.vacari.gerupreco.activity.simpleproportion;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.vacari.gerupreco.R;

public class SimpleProportionActivity extends AppCompatActivity {

    private EditText value1;
    private EditText value2;
    private EditText value3;
    private EditText value4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_proportion);

        value1 = findViewById(R.id.value1);
        value2 = findViewById(R.id.value2);
        value3 = findViewById(R.id.value3);
        value4 = findViewById(R.id.value4);

        configureActions();
    }

    private void configureActions() {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                calculate();
            }
        };

        value1.addTextChangedListener(textWatcher);
        value2.addTextChangedListener(textWatcher);
        value3.addTextChangedListener(textWatcher);
    }

    private void calculate() {
        try {
            double v1 = Double.parseDouble(value1.getText().toString().replace(",", "."));
            double v2 = Double.parseDouble(value2.getText().toString().replace(",", "."));
            double v3 = Double.parseDouble(value3.getText().toString().replace(",", "."));

            if (v1 != 0) {
                double v4 = (v2 * v3) / v1;
                value4.setText(String.format("%.2f", v4));
            } else {
                value4.setText("");
            }
        } catch (NumberFormatException e) {
            value4.setText("");
        }
    }
}
