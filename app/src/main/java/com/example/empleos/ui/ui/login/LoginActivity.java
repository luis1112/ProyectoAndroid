package com.example.empleos.ui.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.empleos.MainActivity;
import com.example.empleos.R;
import com.example.empleos.controladores.ServicioWebAnuncios;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity  {
    EditText username, password;
    Button login;
    ServicioWebAnuncios sw = new ServicioWebAnuncios(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        cargarComponentes();
    }

    private void cargarComponentes() {
        username= findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usu = username.getText().toString();
                String cla = password.getText().toString();
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("username",usu);
                    jsonObject.put("password",cla);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                sw.IniciarSesion(jsonObject, new ServicioWebAnuncios.volleyResponseListener() {
                    @Override
                    public void onError(String message) {
                        Log.e("error", message);
                        Toast.makeText(LoginActivity.this, "No posee conexión a internet", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(JSONArray response) {

                    }

                    @Override
                    public void enRespuesta(JSONObject response) {
                        try {
                            String estado = response.getString("success");
                            if (estado == "true"){
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("username", response.getString("data"));
                            intent.putExtras(bundle);
                            startActivity(intent);
                                Toast.makeText(LoginActivity.this, "Se ha iniciado sesión correctamente", Toast.LENGTH_SHORT).show();
                            limpiar();
                            }else if(estado == "false"){
                                limpiarcontra();
                                Toast.makeText(LoginActivity.this, "Sus datos son incorrectos", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this, "Sus datos son incorrectos", Toast.LENGTH_SHORT).show();

                        }
                    }

                });

            }

            private void limpiar() {
                username.setText("");
                password.setText("");
            }

            private void limpiarcontra() {
                password.setText("");
            }
        });
    }
}
