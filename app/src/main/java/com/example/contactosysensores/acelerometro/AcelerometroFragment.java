package com.example.contactosysensores.acelerometro;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.contactosysensores.R;
import com.example.contactosysensores.VistaVM;
import com.example.contactosysensores.databinding.FragmentAcelerometroBinding;

public class AcelerometroFragment extends Fragment {
    FragmentAcelerometroBinding binding;
    private VistaVM vistaVM;
    private ListaAcelerAdapter listaAcelerAdapter;
    private PersonasAcelerometroVM personasAcelerometroVM;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        vistaVM = new ViewModelProvider(requireActivity()).get(VistaVM.class);
        personasAcelerometroVM = new ViewModelProvider(requireActivity()).get(PersonasAcelerometroVM.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAcelerometroBinding.inflate(inflater, container, false);
        NavController navController = NavHostFragment.findNavController(AcelerometroFragment.this);

        personasAcelerometroVM.getListaPersonasAcelerometro().observe(this, lista->{
            listaAcelerAdapter = new ListaAcelerAdapter();
            listaAcelerAdapter.setContext(getContext());
            listaAcelerAdapter.setListaAceler(lista);
            binding.recyclerAceler.setAdapter(listaAcelerAdapter);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            binding.recyclerAceler.setLayoutManager(linearLayoutManager);
        });
        vistaVM.getVistaActual().observe(this, vistaActual->{
            Log.i("AcelerometroFragment", "Valor observado: " + vistaActual);
            if (vistaActual.equals("Magnetómetro")){
                navController.navigate(R.id.action_acelerometroFragment_to_magnetometroFragment);
            }
        });

        return binding.getRoot();
    }
}