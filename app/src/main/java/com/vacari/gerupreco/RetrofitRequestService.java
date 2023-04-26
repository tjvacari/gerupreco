package com.vacari.gerupreco;

import com.vacari.gerupreco.to.MenorPrecoTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitRequestService {

    @GET("produtos")
    public Call<MenorPrecoTO> callProdutos(@Query("local") String idLocal, @Query("offset") Integer offset, @Query("raio") Integer raio, @Query("data") Integer data,
             @Query("ordem") Integer ordem, @Query("gtin") String gtin);
}
