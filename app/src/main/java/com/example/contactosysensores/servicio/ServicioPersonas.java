package com.example.contactosysensores.servicio;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ServicioPersonas {
    @GET("/api/")
    Call<ResultAPI> random();
}
