package com.example.empleos.controladores;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class AnunciosSingletonVolly {
    private RequestQueue queue;
    private Context context;
    private static AnunciosSingletonVolly miInstancia;

    public AnunciosSingletonVolly(Context context) {
        this.context = context;
        queue=getRequestQueue();
    }

    public RequestQueue getRequestQueue(){
        if (queue==null)
            queue = Volley.newRequestQueue(context.getApplicationContext());
        return queue;
    }

    public static synchronized AnunciosSingletonVolly getInstance(Context context){
        if(miInstancia==null)
            miInstancia=new AnunciosSingletonVolly(context);
        return miInstancia;
    }

    //<T> Coleccion de cualquier tipo
    public <T> void addToRequestqueue(Request request){
        queue.add(request);
    }
}
