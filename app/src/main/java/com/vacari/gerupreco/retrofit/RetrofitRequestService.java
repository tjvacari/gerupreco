package com.vacari.gerupreco.retrofit;

import com.vacari.gerupreco.model.notaparana.LowestPrice;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitRequestService {

    @GET("produtos")
    public Call<LowestPrice> callProdutos(@Query("local") String idLocal, @Query("offset") Integer offset, @Query("raio") Integer raio, @Query("data") Integer data,
                                          @Query("ordem") Integer ordem, @Query("gtin") String gtin);
}
