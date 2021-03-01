package com.example.empleos.controladores;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.empleos.modelos.Anuncios;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class ServicioWebAnuncios {
    Context context;

    //Se define la URL del servicio
    String host="http://192.168.56.1:8050";
    //String host="http://192.168.1.17:8089";
    //String host="http://10.20.11.19:8080";
    String getAllAnuncios="/anuncios/all";
    String searchAnuncios="/anuncios/get/";
    String createAnuncio="/anuncios/create";
    String deleteAnuncio="/anuncios/delete/";
    String updateAnuncio="/anuncios/modify/";
    String createPostular="/postulaciones/create";
    String getPostulaciones="/postulaciones/get/";
    String deletePostulacion="/postulaciones/delete/";
    String login="/usuarios/ingresar";

    public ServicioWebAnuncios(Context context) {
        this.context = context;
    }

    public void ObtenerTodos(final volleyResponseListener listener){
        String path=host.concat(getAllAnuncios);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, path, null,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                listener.onResponse(response);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "No hay conexión", Toast.LENGTH_SHORT);
            }
        });
        AnunciosSingletonVolly.getInstance(context).addToRequestqueue(request);
    }

    public void Buscar(String consulta,final volleyResponseListener listener){
        String path=host.concat(searchAnuncios).concat(consulta);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, path, null,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                listener.onResponse(response);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "No hay conexión", Toast.LENGTH_SHORT);
            }
        });
        AnunciosSingletonVolly.getInstance(context).addToRequestqueue(request);
    }


    public  void CrearAnuncio(JSONObject jsonObject){
        String path=host.concat(createAnuncio);
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.POST,path,jsonObject,new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(context, "Se ha guardado el alumno correctamente", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        AnunciosSingletonVolly.getInstance(context).addToRequestqueue(request);
    }

    public void ModificarAnuncio(String id,JSONObject jsonObject){
        String path=host.concat(updateAnuncio).concat(id);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, path, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(context, "Se ha modificado correctamente", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error:","Ha habido un error");
            }
        });
        AnunciosSingletonVolly.getInstance(context).addToRequestqueue(request);
    }

    public void EliminarAnuncio(String id){
        String path=host.concat(deleteAnuncio).concat(id);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, path, null,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "No hay conexión", Toast.LENGTH_SHORT);
            }
        });
        AnunciosSingletonVolly.getInstance(context).addToRequestqueue(request);
    }

    public void ObtenerPostulaciones(String id,final volleyResponseListener listener){
        String path=host.concat(getPostulaciones).concat(id);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, path, null,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                listener.onResponse(response);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "No hay conexión", Toast.LENGTH_SHORT);
            }
        });
        AnunciosSingletonVolly.getInstance(context).addToRequestqueue(request);
    }

    public void EliminarPostulacion(String id){
        String path=host.concat(deletePostulacion).concat(id);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, path, null,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "No hay conexión", Toast.LENGTH_SHORT);
            }
        });
        AnunciosSingletonVolly.getInstance(context).addToRequestqueue(request);
    }

    public  void Postular(JSONObject jsonObject){
        String path=host.concat(createPostular);
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.POST,path,jsonObject,new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(context, "Se ha guardado el alumno correctamente", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        AnunciosSingletonVolly.getInstance(context).addToRequestqueue(request);
    }

    public  void IniciarSesion(JSONObject jsonObject,final volleyResponseListener listener){
        String path=host.concat(login);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, path, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                listener.enRespuesta(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error:","Ha habido un error");
            }
        });
        AnunciosSingletonVolly.getInstance(context).addToRequestqueue(request);
    }


    public interface volleyResponseListener{
        void onError(String message);

        void onResponse(JSONArray response);

        void enRespuesta(JSONObject response);
    }
}

