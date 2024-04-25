package com.currencyconverter.ViewModal;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.currencyconverter.Modal.ConversionRates;
import com.currencyconverter.Repo.CurrencyRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class CurrencyViewModal extends ViewModel {
    private CurrencyRepository currencyRepository;

    @Inject
    public CurrencyViewModal(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public void GetCurrencyRate(){
        currencyRepository.GetCurrencyRate();
    }
    public LiveData<List<ConversionRates>> getConversionRate() {
        return currencyRepository.getConversionRate();
    }

}
