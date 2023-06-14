package com.vacari.gerupreco.retrofit;

import android.util.Log;

import com.google.gson.Gson;
import com.vacari.gerupreco.model.notaparana.LowestPrice;
import com.vacari.gerupreco.model.notaparana.Product;
import com.vacari.gerupreco.util.Callback;

import java.util.List;

public class JavascriptInterface {

    private Callback<List<Product>> callbackRepo;
    public JavascriptInterface(Callback<List<Product>> callbackRepo) {
        this.callbackRepo = callbackRepo;
    }

    @android.webkit.JavascriptInterface
        public void receiveJson(String json) {
            LowestPrice p = new Gson().fromJson(json, LowestPrice.class);
            callbackRepo.callback(p.getProdutos());
        }
    }