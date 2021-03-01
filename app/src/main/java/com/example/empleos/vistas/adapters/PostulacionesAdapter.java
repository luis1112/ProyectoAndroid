package com.example.empleos.vistas.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.empleos.R;
import com.example.empleos.controladores.ServicioWebAnuncios;
import com.example.empleos.modelos.Postulaciones;

import java.util.List;

public class PostulacionesAdapter extends RecyclerView.Adapter<PostulacionesAdapter.ViewHolderPostulaciones>  {

    List<Postulaciones> lista;

    // la lista va a tener objetos de la clase artista
    public PostulacionesAdapter(List<Postulaciones> lista) {
        this.lista = lista;
    }

    // inflate sirve para cargar las vistas
    //se carga la vista
    @Override
    public ViewHolderPostulaciones onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_postulacion, null);
        ViewHolderPostulaciones viewHolder = new ViewHolderPostulaciones(view);
        return viewHolder;
    }

    //se cargar los datos de la vista
    @Override
    public void onBindViewHolder(ViewHolderPostulaciones viewHolderPostulaciones, int pos) {
        viewHolderPostulaciones.datoID.setText(""+lista.get(pos).getPostulacion_id());
        viewHolderPostulaciones.datoMensaje.setText(""+lista.get(pos).getMensaje());
        viewHolderPostulaciones.datoSalario.setText(""+lista.get(pos).getSalario());
    }

    //Es para saber cuantos items va a tener la lista
    @Override
    public int getItemCount() {
        return lista.size();
    }


    public class ViewHolderPostulaciones extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView datoSalario, datoMensaje,datoID;
        Button eliminar;
        public ViewHolderPostulaciones(View itemView) {
            super(itemView);
            datoSalario = itemView.findViewById(R.id.lblSalario);
            datoMensaje = itemView.findViewById(R.id.lblMensaje);
            datoID = itemView.findViewById(R.id.txtIDPostulacion);
            eliminar = itemView.findViewById(R.id.btnEliminarPostulacion);
            eliminar.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            ServicioWebAnuncios sw = new ServicioWebAnuncios(v.getContext());
            switch (v.getId()){
                case (R.id.btnEliminarPostulacion):
                    sw.EliminarPostulacion(datoID.getText().toString());
                    Toast.makeText(v.getContext(), "Se ha eliminado correctamente", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}