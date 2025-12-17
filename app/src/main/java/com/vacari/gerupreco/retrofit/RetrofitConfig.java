package com.vacari.gerupreco.retrofit;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {
//    public static final String BASE_URL = "https://menorpreco.notaparana.pr.gov.br/api/v1/produtos?local=6g9fp8frx&offset=0&raio=20&data=-1&ordem=0&gtin=/";
    public static final String BASE_URL = "https://menorpreco.notaparana.pr.gov.br/api/v1/";

    public Retrofit build() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder().connectTimeout(6, TimeUnit.SECONDS).readTimeout(6, TimeUnit.SECONDS);

        httpClient.addInterceptor(chain -> {
            Request request = chain.request().newBuilder()
                    .addHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36")
                    .build();
            return chain.proceed(request);
        });

        return new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(BASE_URL).client(httpClient.build()).build();

//        return new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
    }
}
