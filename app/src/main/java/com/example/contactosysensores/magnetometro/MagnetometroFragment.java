package com.example.contactosysensores.magnetometro;

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
import com.example.contactosysensores.databinding.FragmentMagnetometroBinding;

public class MagnetometroFragment extends Fragment {
    FragmentMagnetometroBinding binding;
    private VistaVM vistaVM;
    private ListaMagnetAdapter listaMagnetAdapter;
    private PersonasMagnetometroVM personasMagnetometroVM;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        vistaVM = new ViewModelProvider(requireActivity()).get(VistaVM.class);
        personasMagnetometroVM = new ViewModelProvider(requireActivity()).get(PersonasMagnetometroVM.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMagnetometroBinding.inflate(inflater, container, false);
        NavController navController = NavHostFragment.findNavController(MagnetometroFragment.this);
        personasMagnetometroVM.getListaPersonasMagnetometro().observe(this, lista->{
            listaMagnetAdapter = new ListaMagnetAdapter();
            listaMagnetAdapter.setContext(getContext());
            listaMagnetAdapter.setListaMagnet(lista);
            binding.recyclerMagnet.setAdapter(listaMagnetAdapter);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            binding.recyclerMagnet.setLayoutManager(linearLayoutManager);
        });


        vistaVM.getVistaActual().observe(this, vistaActual->{
            Log.i("MagnetometroFragment", "Valor observado: " + vistaActual);
            if (vistaActual.equals("Acelerómetro")){
                navController.navigate(R.id.action_magnetometroFragment_to_acelerometroFragment);
            }
        });


        return binding.getRoot();
    }
}