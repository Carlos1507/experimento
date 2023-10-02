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
    private Sensor accelerometer;
    private Sensor magnetometer;
    private float[] accelerometerReading = new float[3];
    private float[] magnetometerReading = new float[3];
    private float[] rotationMatrix = new float[9];
    private float[] orientationAngles = new float[3];

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
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

            if (accelerometer != null && magnetometer != null) {
                sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
                sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);
            } else {
                Log.d("mgs-test", "Sensores no disponibles");
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
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            System.arraycopy(sensorEvent.values, 0, accelerometerReading, 0, accelerometerReading.length);
        } else if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            System.arraycopy(sensorEvent.values, 0, magnetometerReading, 0, magnetometerReading.length);
        }
        SensorManager.getRotationMatrix(rotationMatrix, null, accelerometerReading, magnetometerReading);
        SensorManager.getOrientation(rotationMatrix, orientationAngles);
        float azimuthInDegress = Math.abs((float) Math.toDegrees(orientationAngles[0]));
        float startAngle = 0.0f;
        float endAngle = 180.0f;
        float opacity = 1.0f;
        if (azimuthInDegress >= startAngle && azimuthInDegress <= endAngle) {
            float range = endAngle - startAngle;
            float adjustedAngle = azimuthInDegress - startAngle;
            opacity = 1.0f - (adjustedAngle / range);
        }
        binding.recyclerMagnet.setAlpha(opacity);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
    @Override
    public void onStop(){
        super.onStop();
        SensorManager sensorManager = (SensorManager) requireActivity().getSystemService(SENSOR_SERVICE);
        sensorManager.unregisterListener(this);
    }
}