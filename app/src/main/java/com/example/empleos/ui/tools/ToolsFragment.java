package com.example.empleos.ui.tools;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.empleos.MainActivity;
import com.example.empleos.controladores.ControladorAnuncios;
import com.example.empleos.R;
import com.example.empleos.controladores.ServicioWebAnuncios;
import com.example.empleos.modelos.Anuncios;


import org.json.JSONObject;

public class ToolsFragment extends Fragment implements AdapterView.OnItemSelectedListener,View.OnClickListener{

    private Spinner spinner;
    private EditText titulo,puesto,descripcion,area,ciudad,direccion;
    private Button btnCrearAnuncio;
    private ControladorAnuncios ca =new ControladorAnuncios();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tools, container, false);



        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        spinner = (Spinner)getActivity().findViewById(R.id.dropdownProvincia);
        titulo = getActivity().findViewById(R.id.txtTitulo);
        puesto = getActivity().findViewById(R.id.txtPuesto);
        descripcion = getActivity().findViewById(R.id.txtDescripcion);
        area = getActivity().findViewById(R.id.txtArea);
        ciudad = getActivity().findViewById(R.id.txtCiudad);
        direccion = getActivity().findViewById(R.id.txtDireccion);
        btnCrearAnuncio = (Button) getActivity().findViewById(R.id.btncrearanuncio);
        btnCrearAnuncio.setOnClickListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getContext(), "YOUR SELECTION IS : " + parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        ServicioWebAnuncios sw = new ServicioWebAnuncios(getContext());
        switch (v.getId()){
            case (R.id.btncrearanuncio):
                try {
                    if(!titulo.getText().toString().isEmpty() && !puesto.getText().toString().isEmpty() && !descripcion.getText().toString().isEmpty() && !area.getText().toString().isEmpty() && !ciudad.getText().toString().isEmpty() && !direccion.getText().toString().isEmpty()) {
                        JSONObject parametros = new JSONObject();
                        String provincia = spinner.getSelectedItem().toString();
                        parametros.put("titulo", titulo.getText().toString());
                        parametros.put("puesto", puesto.getText().toString());
                        parametros.put("descripcion", descripcion.getText().toString());
                        parametros.put("area", area.getText().toString());
                        parametros.put("provincia", provincia);
                        parametros.put("ciudad", ciudad.getText().toString());
                        parametros.put("direccion", direccion.getText().toString());
                        parametros.put("persona_id", "2");
                        sw.CrearAnuncio(parametros);
                        limpiar();


                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage("Su anuncio se ha publicado con éxito")
                                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                    }
                                });
                        // Create the AlertDialog object and return it
                        builder.show();
                    }
                    else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage("Existen campos vacios")
                                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                    }
                                });
                        // Create the AlertDialog object and return it
                        builder.show();
                    }
                    break;
                }catch (Exception ex){
                    Log.e("error: ", ex.getMessage());
                }
                break;
        }
    }

    private void limpiar(){
        titulo.setText("");
        puesto.setText("");
        descripcion.setText("");
        area.setText("");
        ciudad.setText("");
        direccion.setText("");
    }
}