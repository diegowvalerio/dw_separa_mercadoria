package com.example.dwseparamercadoria.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.dwseparamercadoria.R;
import com.example.dwseparamercadoria.adapter.PedidoAdapter;
import com.example.dwseparamercadoria.entidades.Pedido;

public class Pedidos extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);
        //this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE);

        this.listView = (ListView) findViewById(R.id.listview_pedidos);
        PedidoAdapter pedidoAdapter = new PedidoAdapter(this,new Pedido().getLista());
        this.listView.setAdapter(pedidoAdapter);
        listView.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Pedido pedido = (Pedido) parent.getItemAtPosition(position);
        Intent intent = new Intent(this, Itens.class);
        intent.putExtra("idpedido",pedido.getId());
        startActivity(intent);
    }
}