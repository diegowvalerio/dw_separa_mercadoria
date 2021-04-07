package com.example.dwseparamercadoria.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.dwseparamercadoria.R;
import com.example.dwseparamercadoria.adapter.PedidoAdapter;
import com.example.dwseparamercadoria.entidades.Pedido;

import java.util.ArrayList;

public class Separados extends AppCompatActivity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_separados);

        listView = (ListView) findViewById(R.id.listview_pedidos);
        //listView.setOnItemClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        preencherLista();
    }

    private void preencherLista() {
        ArrayList<Pedido> lista = new Pedido().getLista_Separandos();
        PedidoAdapter pedidoAdapter = new PedidoAdapter(this,lista);
        listView.setAdapter(pedidoAdapter);

    }
}