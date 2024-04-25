package com.currencyconverter.Repo;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.currencyconverter.Dao.CurrencyDao;
import com.currencyconverter.Modal.ConversionRateResponse;
import com.currencyconverter.Modal.ConversionRates;
import com.currencyconverter.Network.ApiService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrencyRepository {
    private final CurrencyDao currencyDao;
    @Inject
    ApiService apiService;
    @Inject
    SharedPreferences sharedPreferences;

    @Inject
    public CurrencyRepository(CurrencyDao currencyDao) {
        this.currencyDao = currencyDao;
    }

    public void GetCurrencyRate() {
       long nextUpdateTime= sharedPreferences.getLong("time_next_update_unix",0);
       long CurrentTime= System.currentTimeMillis();
        if (nextUpdateTime < CurrentTime) {
            apiService.GetCurrencyRate().enqueue(new Callback<ConversionRateResponse>() {
                @Override
                public void onResponse(@NonNull Call<ConversionRateResponse> call, @NonNull Response<ConversionRateResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Log.d("TAG", "onResponse: " + response.body());
                        ConversionRateResponse rateResponse = response.body();
                        if (Objects.equals(rateResponse.getResult(), "success")) {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putLong("time_last_update_unix", rateResponse.getTimeLastUpdateUnix());
                            editor.putLong("time_next_update_unix", rateResponse.getTimeNextUpdateUnix());
                            editor.putString("time_last_update_utc", rateResponse.getTimeLastUpdateUtc());
                            editor.putString("time_next_update_utc", rateResponse.getTimeNextUpdateUtc());
                            editor.putString("base_code", rateResponse.getBaseCode());
                            editor.apply();
                            List<ConversionRates> conversionRatesList = new ArrayList<>();
                            for (Map.Entry<String, Double> entry : rateResponse.getConversionRates().entrySet()) {
                                System.out.println(entry.getKey() + ": " + entry.getValue());
                                Log.d("TAG", entry.getKey() + ": " + entry.getValue());
                                ConversionRates conversionRates = new ConversionRates(entry.getKey(),entry.getValue());
                                conversionRatesList.add(conversionRates);
                            }
                            currencyDao.InsertCurrency(conversionRatesList);
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ConversionRateResponse> call, @NonNull Throwable t) {

                }
            });
        }

    }

   public LiveData<List<ConversionRates>> getConversionRate() {
        return currencyDao.getConversionRate();
    }

}
