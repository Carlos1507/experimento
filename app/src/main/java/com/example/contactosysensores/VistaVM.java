package com.example.contactosysensores;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class VistaVM extends ViewModel {
    private final MutableLiveData<String> vistaActual = new MutableLiveData<>();

    public MutableLiveData<String> getVistaActual() {
        return vistaActual;
    }
}
