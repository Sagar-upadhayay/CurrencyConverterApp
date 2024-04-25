package com.currencyconverter.Modal;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "CurrencyTable")
public class ConversionRates {
//    @PrimaryKey(autoGenerate = true)
//    private int id;
    @PrimaryKey
    @NonNull
    private String currencyCode;
    private double conversionRate;

    public ConversionRates(@NonNull String currencyCode, double conversionRate) {
        this.currencyCode = currencyCode;
        this.conversionRate = conversionRate;
    }



    @NonNull
    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(@NonNull String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public double getConversionRate() {
        return conversionRate;
    }

    public void setConversionRate(double conversionRate) {
        this.conversionRate = conversionRate;
    }

    @NonNull
    @Override
    public String toString() {
        return currencyCode;
    }
}
