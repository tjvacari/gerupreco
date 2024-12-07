package com.vacari.gerupreco.retrofit;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.vacari.gerupreco.R;
import com.vacari.gerupreco.activity.lowestprice.LowestPriceActivity;
import com.vacari.gerupreco.model.notaparana.LowestPrice;
import com.vacari.gerupreco.model.notaparana.Product;
import com.vacari.gerupreco.util.Callback;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class RetrofitRequest {

    public static void searchLowestPrice(String barCode, LowestPriceActivity context, Callback<List<Product>> callbackRepo) {

//        WebView webView = new WebView(context);
//        WebSettings settings = webView.getSettings();
//        settings.setUserAgentString("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36");
//        settings.setJavaScriptEnabled(true);
//        webView.addJavascriptInterface(new JavascriptInterface(callbackRepo), "Android");
//        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                view.evaluateJavascript("javascript:window.Android.receiveJson(document.body.innerText)", null);
//            }
//
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                return true;
//            }
//        });
//
//
//        webView.loadUrl(RetrofitConfig.BASE_URL + barCode);


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
