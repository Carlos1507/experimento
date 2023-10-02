package com.example.contactosysensores.magnetometro;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.contactosysensores.servicio.Persona;

import java.util.ArrayList;

public class PersonasMagnetometroVM extends ViewModel {
    private final MutableLiveData<ArrayList<Persona>> listaPersonasMagnetometro = new MutableLiveData<ArrayList<Persona>>();

    public MutableLiveData<ArrayList<Persona>> getListaPersonasMagnetometro() {
        return listaPersonasMagnetometro;
    }
}
