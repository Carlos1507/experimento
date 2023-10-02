package com.example.contactosysensores.acelerometro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.contactosysensores.R;
import com.example.contactosysensores.magnetometro.PersonasMagnetometroVM;
import com.example.contactosysensores.servicio.Persona;

import java.util.ArrayList;
import java.util.List;

public class ListaAcelerAdapter extends RecyclerView.Adapter<ListaAcelerAdapter.AcelerometroViewHolder> {
    private List<Persona> listaAceler;
    private Context context;
    private PersonasAcelerometroVM personasAcelerometroVM;
    @NonNull
    @Override
    public AcelerometroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rv, parent, false);
        return new AcelerometroViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull AcelerometroViewHolder holder, int position) {
        Persona persona = listaAceler.get(position);
        holder.persona = persona;
        ImageView fotoPersona = holder.itemView.findViewById(R.id.imagenPersona);
        TextView genero = holder.itemView.findViewById(R.id.generoPersona);
        TextView phone = holder.itemView.findViewById(R.id.phonePersona);
        TextView email = holder.itemView.findViewById(R.id.emailPersona);
        TextView nombre = holder.itemView.findViewById(R.id.nombrePersona);
        TextView ciudad = holder.itemView.findViewById(R.id.ciudadPersona);
        TextView pais = holder.itemView.findViewById(R.id.paisPersona);
        ImageView eliminarImagen = holder.itemView.findViewById(R.id.eliminar);
        Glide.with(context).load(persona.getPicture().getLarge()).into(fotoPersona);
        genero.setText("Género: "+persona.getGender());
        phone.setText("Phone: "+ persona.getPhone());
        email.setText("Email: "+persona.getEmail());
        ciudad.setText("Ciudad: "+persona.getLocation().getCity());
        pais.setText("País: "+persona.getLocation().getCountry());
        nombre.setText(persona.getName().getTitle()+" "+persona.getName().getFirst()+" "+persona.getName().getLast());
        eliminarImagen.setOnClickListener(view -> {
            ArrayList<Persona> listaActual = personasAcelerometroVM.getListaPersonasAcelerometro().getValue();
            listaActual.remove(persona);
            personasAcelerometroVM.getListaPersonasAcelerometro().postValue(listaActual);
            notifyDataSetChanged();
        });
    }
    @Override
    public int getItemCount() {
        return listaAceler.size();
    }
    public class AcelerometroViewHolder extends RecyclerView.ViewHolder{
        Persona persona;
        public AcelerometroViewHolder(@NonNull View itemView){
            super(itemView);
        }
    }

    public List<Persona> getListaAceler() {
        return listaAceler;
    }

    public void setListaAceler(List<Persona> listaAceler) {
        this.listaAceler = listaAceler;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public PersonasAcelerometroVM getPersonasAcelerometroVM() {
        return personasAcelerometroVM;
    }

    public void setPersonasAcelerometroVM(PersonasAcelerometroVM personasAcelerometroVM) {
        this.personasAcelerometroVM = personasAcelerometroVM;
    }
}
