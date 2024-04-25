package com.currencyconverter;

import android.app.Application;

import com.google.android.material.color.DynamicColors;
import com.google.android.material.color.utilities.DynamicColor;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DynamicColors.applyToActivitiesIfAvailable(this);
    }
}
