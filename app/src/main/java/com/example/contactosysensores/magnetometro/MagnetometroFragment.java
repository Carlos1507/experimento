package com.example.contactosysensores.magnetometro;

import static android.content.Context.SENSOR_SERVICE;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
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

public class MagnetometroFragment extends Fragment implements SensorEventListener {
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
        SensorManager sensorManager = (SensorManager) requireActivity().getSystemService(SENSOR_SERVICE);
        if (sensorManager != null){
            Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
            if (sensor!=null){
                Log.d("msg-test", "si tengo acelerometro");
                sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI);
            }else{
                Log.d("msg-test","no tengo acelerometro");
            }
        }



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

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}