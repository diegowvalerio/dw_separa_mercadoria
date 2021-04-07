package com.example.dwseparamercadoria.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.dwseparamercadoria.R;
import com.example.dwseparamercadoria.adapter.PedidoAdapter;
import com.example.dwseparamercadoria.entidades.Pedido;
import com.example.dwseparamercadoria.entidades.PedidoItem;

import java.util.ArrayList;
import java.util.List;

public class Separando extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_separando);

        listView = (ListView) findViewById(R.id.listview_pedidos);
        listView.setOnItemClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        preencherLista();
    }

    private void preencherLista() {
        ArrayList<Pedido> lista = new Pedido().getLista_Separando();
        PedidoAdapter pedidoAdapter = new PedidoAdapter(this,lista);
        listView.setAdapter(pedidoAdapter);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final Pedido pedido = (Pedido) parent.getItemAtPosition(position);
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Status")
                .setMessage("Deseja voltar o Pedido para Lista de Pedidos disponíveis ?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int c = 0;
                        int t = 0;
                        List<PedidoItem> itens = new PedidoItem().getLista(pedido.getId());
                        for(PedidoItem i:itens){
                            c = c + i.getQuantidadeseparada();
                            t = t + i.getQuantidadeproduto().intValue();
                        }
                        if(c > 0){
                            pedido.alterar_status(pedido.getId(),"PAUSADO");
                        }else{
                            pedido.alterar_status(pedido.getId(),"ABERTO");
                        }
                        finish();
                    }
                })
                .setNegativeButton("Não", null)
                .show();
    }
}