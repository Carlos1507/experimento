package com.example.contactosysensores.acelerometro;

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
import android.widget.Toast;

import com.example.contactosysensores.R;
import com.example.contactosysensores.VistaVM;
import com.example.contactosysensores.databinding.FragmentAcelerometroBinding;

import java.text.DecimalFormat;
import java.util.List;

public class AcelerometroFragment extends Fragment implements SensorEventListener {
    FragmentAcelerometroBinding binding;
    private VistaVM vistaVM;
    private ListaAcelerAdapter listaAcelerAdapter;
    private PersonasAcelerometroVM personasAcelerometroVM;
    DecimalFormat df = new DecimalFormat("#.##");
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
        SensorManager sensorManager = (SensorManager) requireActivity().getSystemService(SENSOR_SERVICE);
        if (sensorManager != null){
            Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            if (sensor!=null){
                Log.d("msg-test", "si tengo acelerometro");
                sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI);
            }else{
                Log.d("msg-test","no tengo acelerometro");
            }
        }

        NavController navController = NavHostFragment.findNavController(AcelerometroFragment.this);
        personasAcelerometroVM.getListaPersonasAcelerometro().observe(this, lista->{
            listaAcelerAdapter = new ListaAcelerAdapter();
            listaAcelerAdapter.setContext(getContext());
            listaAcelerAdapter.setListaAceler(lista);
            listaAcelerAdapter.setPersonasAcelerometroVM(personasAcelerometroVM);
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

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        int type = sensorEvent.sensor.getType();
        if (type==Sensor.TYPE_ACCELEROMETER){
            double aceleracion_total = Math.sqrt(Math.pow(sensorEvent.values[0],2)+
                                                Math.pow(sensorEvent.values[1],2)+
                                                Math.pow(sensorEvent.values[2],2));
            if (aceleracion_total>15.0){
                Toast.makeText(requireActivity(), String.valueOf(df.format(aceleracion_total))+" m/s^2", Toast.LENGTH_SHORT).show();
                int itemCount = binding.recyclerAceler.getAdapter().getItemCount();
                if (itemCount>0)
                    binding.recyclerAceler.smoothScrollToPosition(itemCount-1);
            }
        }
    }
    @Override
    public void onStop(){
        super.onStop();
        SensorManager sensorManager = (SensorManager) requireActivity().getSystemService(SENSOR_SERVICE);
        sensorManager.unregisterListener(this);
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}