package com.example.dwseparamercadoria.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dwseparamercadoria.R;
import com.example.dwseparamercadoria.adapter.PedidoAdapter;
import com.example.dwseparamercadoria.entidades.Pedido;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Pedidos extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private ListView listView;
    private Integer posicaocorreta = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);
        //this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE);

        listView = (ListView) findViewById(R.id.listview_pedidos);
        listView.setOnItemClickListener(this);


        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                atualiza();
            }
        },5000,5000); //20 segundos = 20.000 s

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = getIntent().getExtras();
        Pedido pedido = (Pedido) parent.getItemAtPosition(position);

        if(position == posicaocorreta || pedido.getStatus().equals("PAUSADO"))  {

            Intent intent = new Intent(this, Itens.class);
            intent.putExtra("idpedido", pedido.getId());
            intent.putExtra("usuario", bundle.getString("usuario"));
            intent.putExtra("tempo",pedido.getCronometro());
            startActivity(intent);


        }else{
            Toast.makeText(this, "Seleção Inválida, siga a sequência ou continue os pedidos pausados "+position, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        preencherLista();
    }

    private void preencherLista() {
        ArrayList<Pedido> lista = new Pedido().getLista();
        PedidoAdapter pedidoAdapter = new PedidoAdapter(this,lista);
        listView.setAdapter(pedidoAdapter);

        for(Pedido p : lista){
            if(p.getStatus().equals("ABERTO") && posicaocorreta == -1){
                posicaocorreta = lista.indexOf(p);
                //Log.i("posição",""+posicaocorreta);
            }
        }
    }

    private void atualiza(){
        Handler refresh = new Handler(Looper.getMainLooper());
        refresh.post(new Runnable() {
            public void run()
            {
                preencherLista();
            }
        });
    }

}