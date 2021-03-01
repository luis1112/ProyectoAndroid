package com.example.empleos.ui.send;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.empleos.MainActivity;
import com.example.empleos.R;
import com.example.empleos.ui.ui.login.LoginActivity;

public class SendFragment extends Fragment  implements View.OnClickListener{
    Button boton;
    private SendViewModel sendViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_send, container, false);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        boton = (Button) getActivity().findViewById(R.id.btnSalir);
        boton.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.btnSalir):
                Intent intent = new Intent(v.getContext(), LoginActivity.class);
                startActivity(intent);
            break;
        }
    }
}