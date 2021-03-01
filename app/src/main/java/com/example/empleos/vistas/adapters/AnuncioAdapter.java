package com.example.empleos.vistas.adapters;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Toast;

import com.example.empleos.R;
import com.example.empleos.controladores.ServicioWebAnuncios;
import com.example.empleos.modelos.Anuncios;
import com.example.empleos.modelos.Postulaciones;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AnuncioAdapter extends RecyclerView.Adapter<AnuncioAdapter.ViewHolderAnuncios> {
    Spinner spinner;
    List<Anuncios> lista;
    TextView IDModificar,idpostular;
    EditText tituloModificar,puestoModificar,descripcionModificar,areaModificar,ciudadModificar,direccionModificar,mensaje,salario;
    List<Postulaciones> listaPostulaciones;
    PostulacionesAdapter adapter;

    Button modificarModificar,postular;
    public AnuncioAdapter(List<Anuncios> lista){
        this.lista = lista;
    }


    @Override
    public ViewHolderAnuncios onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_anuncios, null);
        ViewHolderAnuncios viewHolder = new ViewHolderAnuncios(view);
        return viewHolder;
    }
    //se cargar los datos de la vista
    @Override
    public void onBindViewHolder(ViewHolderAnuncios viewHolderAnuncios, final int pos) {
        viewHolderAnuncios.id.setText(""+lista.get(pos).getAnuncio_id());
        viewHolderAnuncios.titulo.setText(""+lista.get(pos).getTitulo());
        viewHolderAnuncios.puesto.setText(""+lista.get(pos).getPuesto());
        viewHolderAnuncios.descripcion.setText(""+lista.get(pos).getDescripcion());
        viewHolderAnuncios.area.setText(""+lista.get(pos).getArea());
        //viewHolderAnuncios.pais.setText(""+lista.get(pos).getPais());
        viewHolderAnuncios.provincia.setText(""+lista.get(pos).getProvincia());
        viewHolderAnuncios.ciudad.setText(""+lista.get(pos).getCiudad());
        viewHolderAnuncios.direccion.setText(""+lista.get(pos).getDireccion());
    }

    //Es para saber cuantos items va a tener la lista
    @Override
    public int getItemCount() {
        return lista.size();
    }



    public class ViewHolderAnuncios extends RecyclerView.ViewHolder implements  View.OnClickListener{
        TextView id,titulo,puesto,descripcion,area,pais,provincia,ciudad,direccion;
        Button modificar,eliminar,postular;
        RecyclerView recyclerPostulaciones;
        public ViewHolderAnuncios(View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.lblID);
            titulo = itemView.findViewById(R.id.lblTitulo);
            puesto = itemView.findViewById(R.id.lblPuesto);
            descripcion = itemView.findViewById(R.id.lblDescripcion);
            area = itemView.findViewById(R.id.lblArea);
            provincia = itemView.findViewById(R.id.lblProvincia);
            ciudad = itemView.findViewById(R.id.lblCiudad);
            direccion = itemView.findViewById(R.id.lblDireccion);
            modificar = itemView.findViewById(R.id.btnModificar);
            eliminar = itemView.findViewById(R.id.btnEliminar);
            postular = itemView.findViewById(R.id.btnPostular);
            postular.setOnClickListener(this);
            modificar.setOnClickListener(this);
            eliminar.setOnClickListener(this);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    ServicioWebAnuncios sw = new ServicioWebAnuncios(v.getContext());
                    Dialog dlg = new Dialog(v.getContext());
                    dlg.setContentView(R.layout.dlg_postulaciones);
                    sw.ObtenerPostulaciones(id.getText().toString(),new ServicioWebAnuncios.volleyResponseListener() {
                        @Override
                        public void onError(String message) {
                            Log.e("Error nuevo", message);
                        }

                        @Override
                        public void onResponse(JSONArray response) {
                            Log.e("ASD",response+"");
                            try {
                                JsonParse(response,v);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void enRespuesta(JSONObject response) {

                        }
                    });
                    recyclerPostulaciones = dlg.findViewById(R.id.recyclerPostulaciones);
                    dlg.show();
                    //Log.e("ASD",id.getText()+"");
                }
            });
        }

        @SuppressLint("ResourceType")
        @Override
        public void onClick(View v) {
            ServicioWebAnuncios sw = new ServicioWebAnuncios(v.getContext());
            switch (v.getId()){
                case (R.id.btnPostular):
                    cargarDialogoPostular(v,id.getText().toString());
                    break;
                case (R.id.btnModificar):
                    Anuncios anuncio=new Anuncios();
                    anuncio.setAnuncio_id(Integer.parseInt(id.getText().toString()));
                    anuncio.setTitulo(titulo.getText()+"");
                    anuncio.setArea(area.getText()+"");
                    anuncio.setPuesto(puesto.getText()+"");
                    anuncio.setDescripcion(descripcion.getText()+"");
                    anuncio.setCiudad(ciudad.getText()+"");
                    anuncio.setDireccion(direccion.getText()+"");
                    cargarDialogoModificar(v,anuncio);
                    break;
                case (R.id.btnEliminar):
                    sw.EliminarAnuncio(id.getText().toString());
                    Toast.makeText(v.getContext(), "Se ha eliminado correctamente", Toast.LENGTH_SHORT).show();
                    break;
                case (R.id.btnModificarModificar):
                    JSONObject parametros = new JSONObject();
                    String provincia = spinner.getSelectedItem().toString();
                    try {
                        if(!tituloModificar.getText().toString().isEmpty() && !puestoModificar.getText().toString().isEmpty() && !descripcionModificar.getText().toString().isEmpty() && !areaModificar.getText().toString().isEmpty() && !ciudadModificar.getText().toString().isEmpty() && !direccionModificar.getText().toString().isEmpty()) {
                            parametros.put("titulo", tituloModificar.getText().toString());
                            parametros.put("puesto", puestoModificar.getText().toString());
                            parametros.put("descripcion", descripcionModificar.getText().toString());
                            parametros.put("area", areaModificar.getText().toString());
                            parametros.put("provincia", provincia);
                            parametros.put("ciudad", ciudadModificar.getText().toString());
                            parametros.put("direccion", direccionModificar.getText().toString());
                            parametros.put("persona_id", "2");
                        }else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                            builder.setMessage("Existen campos vacios")
                                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.dismiss();
                                        }
                                    });
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        sw.ModificarAnuncio(id.getText().toString(),parametros);

                    Toast.makeText(v.getContext(), "Se ha modificado correctamente", Toast.LENGTH_SHORT).show();
                    break;
                case (R.id.btnPostularPostular):
                    JSONObject parametro = new JSONObject();
                    try {
                        if(!salario.getText().toString().isEmpty() && !mensaje.getText().toString().isEmpty()){
                        parametro.put("salario",salario.getText().toString());
                        parametro.put("mensaje",mensaje.getText().toString());
                        parametro.put("anuncio_id",Integer.parseInt(idpostular.getText().toString()));
                        parametro.put("persona_id",2);
                        limpiar();
                            sw.Postular(parametro);
                            Toast.makeText(v.getContext(), "Se ha enviado su postulación", Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(v.getContext(), "Hay campos vacios", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }

        private void cargarDialogoPostular(View v,String id) {
            Dialog dlg = new Dialog(v.getContext());
            dlg.setContentView(R.layout.dlg_postular_anuncio);

            idpostular = dlg.findViewById(R.id.txtPostularAnuncioID);
            mensaje = dlg.findViewById(R.id.txtMensaje);
            salario = dlg.findViewById(R.id.txtSalario);
            postular = dlg.findViewById(R.id.btnPostularPostular);

            idpostular.setText(id);
            postular.setOnClickListener(this);
            dlg.show();
        }

        private void cargarDialogoModificar(View v,Anuncios anuncio) {
            Dialog dlg = new Dialog(v.getContext());
            dlg.setContentView(R.layout.dlg_modificar_anuncio);
            spinner = dlg.findViewById(R.id.dropdownModificarProvincia);

            IDModificar = dlg.findViewById(R.id.txtModificarlID);
            tituloModificar = dlg.findViewById(R.id.txtModificarTitulo);
            puestoModificar = dlg.findViewById(R.id.txtModificarPuesto);
            descripcionModificar = dlg.findViewById(R.id.txtModificarDescripcion);
            areaModificar = dlg.findViewById(R.id.txtModificarArea);
            ciudadModificar = dlg.findViewById(R.id.txtModificarCiudad);
            direccionModificar = dlg.findViewById(R.id.txtModificarDireccion);
            modificarModificar = dlg.findViewById(R.id.btnModificarModificar);

            IDModificar.setText(""+anuncio.getAnuncio_id());
            tituloModificar.setText(anuncio.getTitulo());
            puestoModificar.setText(anuncio.getPuesto());
            descripcionModificar.setText(anuncio.getDescripcion());
            areaModificar.setText(anuncio.getArea());
            ciudadModificar.setText(anuncio.getCiudad());
            direccionModificar.setText(anuncio.getDireccion());
            modificarModificar.setOnClickListener(this);
            dlg.show();
        }

        private void limpiar(){
            salario.setText("");
            mensaje.setText("");
        }

        private void JsonParse(JSONArray jsonArray,View v) throws JSONException {
            listaPostulaciones=new ArrayList<Postulaciones>();
            for (int i=0 ; i<jsonArray.length() ; i++){
                JSONObject postulaciones = jsonArray.getJSONObject(i);
                Postulaciones postulacion = new Postulaciones();
                postulacion.setPostulacion_id(postulaciones.getInt("postulacion_id"));
                postulacion.setSalario(postulaciones.getInt("salario"));
                postulacion.setMensaje(postulaciones.getString("mensaje"));
                listaPostulaciones.add(postulacion);
                recyclerPostulaciones.setLayoutManager(new LinearLayoutManager(v.getContext()));
                adapter = new PostulacionesAdapter(listaPostulaciones);
                recyclerPostulaciones.setAdapter(adapter);
            }
        }
    }
}
