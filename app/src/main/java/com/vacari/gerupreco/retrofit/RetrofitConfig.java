package com.vacari.gerupreco.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {
    private static final String BASE_URL = "https://menorpreco.notaparana.pr.gov.br/api/v1/";

    public Retrofit build() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
