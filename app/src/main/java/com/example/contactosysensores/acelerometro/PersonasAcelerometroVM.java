package com.example.contactosysensores.acelerometro;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.contactosysensores.servicio.Persona;

import java.util.ArrayList;

public class PersonasAcelerometroVM extends ViewModel {
    private final MutableLiveData<ArrayList<Persona>> listaPersonasAcelerometro = new MutableLiveData<ArrayList<Persona>>();

    public MutableLiveData<ArrayList<Persona>> getListaPersonasAcelerometro() {
        return listaPersonasAcelerometro;
    }
}
