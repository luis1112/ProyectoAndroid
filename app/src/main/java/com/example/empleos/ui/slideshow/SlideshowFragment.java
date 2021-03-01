package com.example.empleos.ui.slideshow;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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

public class SlideshowFragment extends Fragment {

    RecyclerView recyclerAnuncios;
    List<Anuncios> listaAnuncios;
    AnuncioAdapter adapter;

    //Objeto de tipo servicio web

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ServicioWebAnuncios sw = new ServicioWebAnuncios(getContext());
        recyclerAnuncios=(RecyclerView) getActivity().findViewById(R.id.recyclerAnuncios);
        sw.ObtenerTodos(new ServicioWebAnuncios.volleyResponseListener() {
            @Override
            public void onError(String message) {
                Log.e("Error nuevo", message);
            }

            @Override
            public void onResponse(JSONArray response) {
                try {
                    JsonParse(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void enRespuesta(JSONObject response) {

            }
        });
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