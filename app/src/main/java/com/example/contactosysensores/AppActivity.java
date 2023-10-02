package com.example.contactosysensores;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.example.contactosysensores.acelerometro.PersonasAcelerometroVM;
import com.example.contactosysensores.databinding.ActivityAppBinding;
import com.example.contactosysensores.magnetometro.PersonasMagnetometroVM;
import com.example.contactosysensores.servicio.Persona;
import com.example.contactosysensores.servicio.ResultAPI;
import com.example.contactosysensores.servicio.ServicioPersonas;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppActivity extends AppCompatActivity {
    ActivityAppBinding binding;

    String textoMagnetometro = "Ir al Magnetómetro";
    String textoAcelerometro = "Ir al Acelerómetro";
    ServicioPersonas servicioPersonas = new Retrofit.Builder()
            .baseUrl("https://randomuser.me")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ServicioPersonas.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAppBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        VistaVM vistaVM = new ViewModelProvider(AppActivity.this).get(VistaVM.class);
        PersonasAcelerometroVM personasAcelerometroVM = new ViewModelProvider(AppActivity.this).get(PersonasAcelerometroVM.class);
        PersonasMagnetometroVM personasMagnetometroVM = new ViewModelProvider(AppActivity.this).get(PersonasMagnetometroVM.class);

        personasMagnetometroVM.getListaPersonasMagnetometro().postValue(new ArrayList<>());
        personasAcelerometroVM.getListaPersonasAcelerometro().postValue(new ArrayList<>());
        vistaVM.getVistaActual().postValue("Inicio");

        binding.butonCambiar.setOnClickListener(view -> {
            if(binding.butonCambiar.getText().toString().equals(textoAcelerometro)){
                vistaVM.getVistaActual().postValue("Acelerómetro");
                binding.butonCambiar.setText(textoMagnetometro);
            }else{
                vistaVM.getVistaActual().postValue("Magnetómetro");
                binding.butonCambiar.setText(textoAcelerometro);
            }
        });
        binding.botonAgregar.setOnClickListener(view -> {
            binding.botonAgregar.setEnabled(false);
            binding.butonCambiar.setEnabled(false);
            if (binding.butonCambiar.getText().toString().equals(textoMagnetometro)){
                // Estoy en el Acelerómetro
                servicioPersonas.random().enqueue(new Callback<ResultAPI>() {
                    @Override
                    public void onResponse(Call<ResultAPI> call, Response<ResultAPI> response) {
                        if (response.isSuccessful()){
                            ArrayList<Persona> listaUsuariosAcelerómetro = personasAcelerometroVM.getListaPersonasAcelerometro().getValue();
                            Persona persona = response.body().getResults().get(0);
                            listaUsuariosAcelerómetro.add(persona);
                            personasAcelerometroVM.getListaPersonasAcelerometro().postValue(listaUsuariosAcelerómetro);
                            binding.botonAgregar.setEnabled(true);
                            binding.butonCambiar.setEnabled(true);
                        }
                    }
                    @Override
                    public void onFailure(Call<ResultAPI> call, Throwable t) {
                    }
                });

            }else{
                // Estoy en el magnetómetro
                servicioPersonas.random().enqueue(new Callback<ResultAPI>() {
                    @Override
                    public void onResponse(Call<ResultAPI> call, Response<ResultAPI> response) {
                        if (response.isSuccessful()){
                            ArrayList<Persona> listaUsuariosMagnetómetro = personasMagnetometroVM.getListaPersonasMagnetometro().getValue();
                            Persona persona = response.body().getResults().get(0);
                            listaUsuariosMagnetómetro.add(persona);
                            personasMagnetometroVM.getListaPersonasMagnetometro().postValue(listaUsuariosMagnetómetro);
                            binding.botonAgregar.setEnabled(true);
                            binding.butonCambiar.setEnabled(true);
                        }
                    }
                    @Override
                    public void onFailure(Call<ResultAPI> call, Throwable t) {
                    }
                });


            }
        });
    }
}