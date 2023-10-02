package com.example.contactosysensores.magnetometro;

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
import com.example.contactosysensores.servicio.Persona;

import java.util.ArrayList;
import java.util.List;

public class ListaMagnetAdapter extends RecyclerView.Adapter<ListaMagnetAdapter.MagnetometroViewHolder> {
    private List<Persona> listaMagnet;
    private Context context;
    private PersonasMagnetometroVM personasMagnetometroVM;
    @NonNull
    @Override
    public ListaMagnetAdapter.MagnetometroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rv, parent, false);
        return new ListaMagnetAdapter.MagnetometroViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ListaMagnetAdapter.MagnetometroViewHolder holder, int position) {
        Persona persona = listaMagnet.get(position);
        holder.persona = persona;
        ImageView fotoPersona = holder.itemView.findViewById(R.id.imagenPersona);
        TextView genero = holder.itemView.findViewById(R.id.generoPersona);
        TextView phone = holder.itemView.findViewById(R.id.phonePersona);
        TextView email = holder.itemView.findViewById(R.id.emailPersona);
        TextView nombre = holder.itemView.findViewById(R.id.nombrePersona);
        TextView ciudad = holder.itemView.findViewById(R.id.ciudadPersona);
        TextView pais = holder.itemView.findViewById(R.id.paisPersona);
        ImageView eliminarPersona = holder.itemView.findViewById(R.id.eliminar);
        Glide.with(context).load(persona.getPicture().getLarge()).into(fotoPersona);
        genero.setText("Género: "+persona.getGender());
        phone.setText("Phone: "+ persona.getPhone());
        email.setText("Email: "+persona.getEmail());
        ciudad.setText("Ciudad: "+persona.getLocation().getCity());
        pais.setText("País: "+persona.getLocation().getCountry());
        nombre.setText(persona.getName().getTitle()+" "+persona.getName().getFirst()+" "+persona.getName().getLast());
        eliminarPersona.setOnClickListener(view -> {
            ArrayList<Persona> listaActual = personasMagnetometroVM.getListaPersonasMagnetometro().getValue();
            listaActual.remove(persona);
            personasMagnetometroVM.getListaPersonasMagnetometro().postValue(listaActual);
            notifyDataSetChanged();
        });
    }
    @Override
    public int getItemCount() {
        return listaMagnet.size();
    }
    public class MagnetometroViewHolder extends RecyclerView.ViewHolder{
        Persona persona;
        public MagnetometroViewHolder(@NonNull View itemView){
            super(itemView);
        }
    }

    public List<Persona> getListaMagnet() {
        return listaMagnet;
    }

    public void setListaMagnet(List<Persona> listaMagnet) {
        this.listaMagnet = listaMagnet;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public PersonasMagnetometroVM getPersonasMagnetometroVM() {
        return personasMagnetometroVM;
    }

    public void setPersonasMagnetometroVM(PersonasMagnetometroVM personasMagnetometroVM) {
        this.personasMagnetometroVM = personasMagnetometroVM;
    }
}
