package com.example.empleos.ui.gallery;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.empleos.R;
import com.example.empleos.controladores.ServicioWebAnuncios;
import com.example.empleos.modelos.Anuncios;
import com.example.empleos.vistas.adapters.AnuncioAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment implements View.OnClickListener{
    RecyclerView recyclerAnuncios;
    List<Anuncios> listaAnuncios;
    AnuncioAdapter adapter;
    TextView consulta;
    Button boton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerAnuncios=(RecyclerView) getActivity().findViewById(R.id.recyclerBuscarAnuncios);
        consulta = getActivity().findViewById(R.id.txtBuscarEmpleo);
        boton = (Button) getActivity().findViewById(R.id.btnBuscar);
        boton.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        ServicioWebAnuncios sw = new ServicioWebAnuncios(getContext());
        switch (v.getId()){
            case (R.id.btnBuscar):
                if(!consulta.getText().toString().isEmpty()) {
                    sw.Buscar(consulta.getText().toString(), new ServicioWebAnuncios.volleyResponseListener() {
                        @Override
                        public void onError(String message) {
                            Log.e("Error nuevo", message);
                        }

                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                if (response.length()==0){
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                    builder.setMessage("Lo sentimos, aún no hay empleos de ese tipo.")
                                            .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.dismiss();
                                                }
                                            });
                                    // Create the AlertDialog object and return it
                                    builder.show();
                                    consulta.setText("");
                                } else{
                                    JsonParse(response);
                                    consulta.setText("");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void enRespuesta(JSONObject response) {

                        }
                    });
                } else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Ingrese el tipo de empleo que desea buscar")
                            .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                }
                            });
                    // Create the AlertDialog object and return it
                    builder.show();
                }
                break;
        }
    }

    private void JsonParse(JSONArray jsonArray) throws JSONException {
        listaAnuncios=new ArrayList<Anuncios>();
        for (int i=0 ; i<jsonArray.length() ; i++){
            JSONObject anuncios = jsonArray.getJSONObject(i);
            Anuncios anuncio = new Anuncios();
            anuncio.setAnuncio_id(anuncios.getInt("anuncio_id"));
            anuncio.setTitulo(anuncios.getString("titulo"));
            anuncio.setPuesto(anuncios.getString("puesto"));
            anuncio.setDescripcion(anuncios.getString("descripcion"));
            anuncio.setArea(anuncios.getString("area"));
            anuncio.setPais("Ecuador");
            anuncio.setProvincia(anuncios.getString("provincia"));
            anuncio.setCiudad(anuncios.getString("ciudad"));
            anuncio.setDireccion(anuncios.getString("direccion"));
            listaAnuncios.add(anuncio);
            recyclerAnuncios.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter = new AnuncioAdapter(listaAnuncios);
            recyclerAnuncios.setAdapter(adapter);
        }
    }
}