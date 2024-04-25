package com.currencyconverter.Database;

import android.icu.util.CurrencyAmount;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.currencyconverter.Dao.CurrencyDao;
import com.currencyconverter.Modal.ConversionRates;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {ConversionRates.class},version = 1,exportSchema = false)
public abstract class CurrencyDatabase extends RoomDatabase {
    public abstract CurrencyDao currencyDao();
}
