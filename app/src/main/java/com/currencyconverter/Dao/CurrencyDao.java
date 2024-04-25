package com.currencyconverter.Dao;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.currencyconverter.Modal.ConversionRates;

import java.util.List;

@Dao
public interface CurrencyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void InsertCurrency(List<ConversionRates> list);
    @Query("SELECT * FROM CurrencyTable")
    LiveData<List<ConversionRates>> getConversionRate();
}
