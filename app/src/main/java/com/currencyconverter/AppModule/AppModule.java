package com.currencyconverter.AppModule;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.room.Room;

import com.currencyconverter.Dao.CurrencyDao;
import com.currencyconverter.Database.CurrencyDatabase;
import com.currencyconverter.Network.ApiService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import kotlin.jvm.Synchronized;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {
    private static volatile CurrencyDatabase INSTANCE = null;
    private static Retrofit retrofit = null;

    static {
        System.loadLibrary("keys");
    }
    public static native String GetUrl();
    @Provides
    @Singleton
    public static synchronized CurrencyDatabase provideDatabaseInstance(@ApplicationContext Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, CurrencyDatabase.class, "CurrencyDatabase").fallbackToDestructiveMigration().build();
        }
        return INSTANCE;
    }

    @Provides
    public static CurrencyDao provideCurrencyDao(CurrencyDatabase currencyDatabase) {
        return currencyDatabase.currencyDao();
    }

    @Provides
    @Singleton
    public static Retrofit retrofit() {
        if (retrofit == null) {
            String baseUrl = GetUrl();
            final OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(1, TimeUnit.MINUTES).writeTimeout(1, TimeUnit.MINUTES).readTimeout(1, TimeUnit.MINUTES).build();
            retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).callbackExecutor(Executors.newFixedThreadPool(5)).client(okHttpClient).build();
        }
        return retrofit;
    }

    @Provides
    @Singleton
    public static ApiService provideApiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }
    @Provides
    @Singleton
    public static SharedPreferences provideSharedPreferences(@ApplicationContext Context context){
        return  context.getSharedPreferences("LastResponse",Context.MODE_PRIVATE);
    }
}
