package com.example.dwseparamercadoria.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.dwseparamercadoria.R;
import com.example.dwseparamercadoria.adapter.PedidoItemAdapter;
import com.example.dwseparamercadoria.entidades.PedidoItem;

public class Itens extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itens);

        this.listView = (ListView) findViewById(R.id.listview_itens);
    }

    @Override
    protected void onStart() {
        super.onStart();
        preencherLista();
    }

    private void preencherLista() {

        Bundle bundle = getIntent().getExtras();
        if (bundle!=null && bundle.containsKey("idpedido")) {
            PedidoItemAdapter pedidoItemAdapter = new PedidoItemAdapter(this,new PedidoItem().getLista(bundle.getInt("idpedido")));
            listView.setAdapter(pedidoItemAdapter);
        }
    }
}