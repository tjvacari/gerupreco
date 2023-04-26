package com.vacari.gerupreco;

import android.content.Context;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.vacari.gerupreco.to.MenorPrecoTO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitRequest {

    public static void buscarEmpreendimento(String barCode, MenorPrecoActivity context, MenorPrecoAdapter adapter) {
        RetrofitRequestService apiService = new RetrofitConfig().build().create(RetrofitRequestService.class);
        Call<MenorPrecoTO> call = apiService.callProdutos("6g9fp8frx", 0, 20, -1, 0, barCode);
        call.enqueue(new Callback<MenorPrecoTO>() {
            @Override
            public void onResponse(Call<MenorPrecoTO> call, Response<MenorPrecoTO> response) {
                context.runOnUiThread(() -> {
                    adapter.refresh(response.body().getProdutos());
                });
                context.closeProgress();
            }

            @Override
            public void onFailure(Call<MenorPrecoTO> call, Throwable t) {
                Toast.makeText(context, "Errooo", Toast.LENGTH_LONG).show();
                context.closeProgress();
            }
        });
    }
}
