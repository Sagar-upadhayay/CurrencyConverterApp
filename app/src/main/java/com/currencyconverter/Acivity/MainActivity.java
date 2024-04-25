package com.currencyconverter.Acivity;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.currencyconverter.Modal.ConversionRates;
import com.currencyconverter.R;
import com.currencyconverter.ViewModal.CurrencyViewModal;
import com.currencyconverter.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private static List<ConversionRates> conversionRatesList = new ArrayList<>();
    CurrencyViewModal currencyViewModal;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        currencyViewModal = new ViewModelProvider(this).get(CurrencyViewModal.class);
        currencyViewModal.GetCurrencyRate();
        currencyViewModal.getConversionRate().observe(this, list -> {
            conversionRatesList.clear();
            conversionRatesList.addAll(list);
            ArrayAdapter<ConversionRates> arrayAdapter1 = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, list);
            arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            binding.sourceCurrencyText.setAdapter(arrayAdapter1);
            binding.sourceCurrencyText.setText(list.get(0).getCurrencyCode(), false);
            ArrayAdapter<ConversionRates> arrayAdapter2 = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, list);
            arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            binding.targetCurrencyText.setAdapter(arrayAdapter2);
            binding.targetCurrencyText.setText("INR", false);
        });
        binding.txtamount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.amountLayoutBox.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.convertButton.setOnClickListener(v -> {
            String sourceCountryName = binding.sourceCurrencyText.getText().toString().trim();
            String TargetCountryName = binding.targetCurrencyText.getText().toString().trim();
            String Amount = binding.txtamount.getEditableText().toString();
            ConversionRates SourceConversionRate = findRate(sourceCountryName);
            ConversionRates TargetConversionRate = findRate(TargetCountryName);
            ConversionRates usdRate = findRate("USD");
            if (SourceConversionRate == null) {
                return;
            }
            if (TargetConversionRate == null) {
                return;
            }
            if (usdRate == null) {
                return;
            }
            if (Amount.isEmpty()) {
                binding.amountLayoutBox.setError("Please Enter Amount");
                return;
            }

            //Enter Amount convert into double
            double amountIn = Double.parseDouble(Amount);
            //Convert Source Rate into USD
            double sc = usdRate.getConversionRate() / SourceConversionRate.getConversionRate();

            double amountInSource = amountIn * sc;
            //Convert TotalSourceUSD into TargetRate
            double cam = amountInSource * TargetConversionRate.getConversionRate();

            double fValue =  Math.round(cam * 10000.0) / 10000.0;
            binding.convertedAmount.setText("Amount : "+fValue);


        });


    }

    private ConversionRates findRate(String name) {
        if (!conversionRatesList.isEmpty()) {
            for (ConversionRates user : conversionRatesList) {
                if (user.getCurrencyCode().equals(name)) {
                    return user;
                }
            }
        }
        return null;
    }
}