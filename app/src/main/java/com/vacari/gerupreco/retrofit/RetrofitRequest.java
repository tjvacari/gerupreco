package com.vacari.gerupreco.retrofit;

import android.widget.Toast;

import com.vacari.gerupreco.activity.LowestPriceActivity;
import com.vacari.gerupreco.adapter.LowestAdapterAdapter;
import com.vacari.gerupreco.model.notaparana.LowestPrice;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitRequest {

    public static void searchLowestPrice(String barCode, LowestPriceActivity context, LowestAdapterAdapter adapter) {
        RetrofitRequestService apiService = new RetrofitConfig().build().create(RetrofitRequestService.class);
        Call<LowestPrice> call = apiService.callProdutos("6g9fp8frx", 0, 20, -1, 0, barCode);
        call.enqueue(new Callback<LowestPrice>() {
            @Override
            public void onResponse(Call<LowestPrice> call, Response<LowestPrice> response) {
                context.runOnUiThread(() -> {
                    adapter.refresh(response.body().getProdutos());
                });
                context.closeProgress();
            }

            @Override
            public void onFailure(Call<LowestPrice> call, Throwable t) {
                Toast.makeText(context, "Errooo", Toast.LENGTH_LONG).show();
                context.closeProgress();
            }
        });
    }
}
