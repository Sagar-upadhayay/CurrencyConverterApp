package com.currencyconverter.Network;

import com.currencyconverter.Modal.ConversionRateResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("USD")
    Call<ConversionRateResponse> GetCurrencyRate();
}
