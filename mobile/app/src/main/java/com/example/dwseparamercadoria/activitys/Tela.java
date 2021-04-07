package com.example.dwseparamercadoria.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.dwseparamercadoria.R;

public class Tela extends AppCompatActivity {
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela);

        bundle = getIntent().getExtras();

    }

    public void verpedidos(View view){
        Intent intent = new Intent(this, Pedidos.class);
        intent.putExtra("usuario", bundle.getString("usuario"));
        startActivity(intent);
    }

    public void verseparando(View view){
        Intent intent = new Intent(this, Separando.class);
        intent.putExtra("usuario", bundle.getString("usuario"));
        startActivity(intent);
    }

    public void verseparados(View view){
        Intent intent = new Intent(this, Separados.class);
        intent.putExtra("usuario", bundle.getString("usuario"));
        startActivity(intent);
    }
}