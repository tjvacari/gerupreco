package com.vacari.gerupreco.retrofit;

import android.widget.Toast;

import com.vacari.gerupreco.R;
import com.vacari.gerupreco.activity.LowestPriceActivity;
import com.vacari.gerupreco.model.notaparana.LowestPrice;
import com.vacari.gerupreco.model.notaparana.Product;
import com.vacari.gerupreco.util.Callback;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class RetrofitRequest {

    public static void searchLowestPrice(String barCode, LowestPriceActivity context, Callback<List<Product>> callbackRepo) {
        RetrofitRequestService apiService = new RetrofitConfig().build().create(RetrofitRequestService.class);
        Call<LowestPrice> call = apiService.callProdutos("6g9fp8frx", 0, 20, -1, 0, barCode);
        call.enqueue(new retrofit2.Callback<LowestPrice>() {
            @Override
            public void onResponse(Call<LowestPrice> call, Response<LowestPrice> response) {
                callbackRepo.callback(response.body().getProdutos());
            }

            @Override
            public void onFailure(Call<LowestPrice> call, Throwable t) {
                Toast.makeText(context, context.getString(R.string.error), Toast.LENGTH_LONG).show();
                context.closeProgress();
            }
        });
    }
}
