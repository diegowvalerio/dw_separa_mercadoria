package com.example.dwseparamercadoria.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dwseparamercadoria.R;
import com.example.dwseparamercadoria.adapter.PedidoItemAdapter;
import com.example.dwseparamercadoria.entidades.Pedido;
import com.example.dwseparamercadoria.entidades.PedidoItem;

import java.util.ArrayList;
import java.util.List;

public class Itens extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itens);

        this.listView = (ListView) findViewById(R.id.listview_itens);
        listView.setOnItemClickListener(this);
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

            altera_status("SEPARANDO");
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = getIntent().getExtras();

        PedidoItem item = (PedidoItem) parent.getItemAtPosition(position);
            Intent intent = new Intent(this, Leitura.class);
            intent.putExtra("item", item.getIditem());
            intent.putExtra("usuario", bundle.getString("usuario"));
            startActivity(intent);

    }
    //altera status do pedido de ABERTO para SEPARANDO
    private void altera_status(String status){
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null && bundle.containsKey("idpedido")) {
            Pedido pedido = new Pedido();
            pedido.alterar_status(bundle.getInt("idpedido"),status);
        }
    }

    @Override
    public void onBackPressed() {
        int c = 0;
        int t = 0;
        Bundle bundle = getIntent().getExtras();
        List<PedidoItem> itens = new PedidoItem().getLista(bundle.getInt("idpedido"));
        for(PedidoItem i:itens){
           if(i.getQuantidadeseparada() > 0){
               c = c + i.getQuantidadeseparada();
               t = t + i.getQuantidadeproduto().intValue();
           }
       }
        if(c > 0){
            if(c == t){
                altera_status("SEPARADO");
                finish();
            }else {
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Fechando Pedido")
                        .setMessage("Houve inicio de separação, Pedido ficará em PAUSA aguardando continuação ! Deseja continuar ?")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                altera_status("PAUSADO");
                                finish();
                            }

                        })
                        .setNegativeButton("Não", null)
                        .show();
            }
        }else{
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Fechando Pedido")
                    .setMessage("Nenhum item separado, Pedido voltará para lista de Separação ! Deseja continuar ?")
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            altera_status("ABERTO");
                            finish();
                        }

                    })
                    .setNegativeButton("Não", null)
                    .show();
        }
        Log.i("QUANTIDADE-x-SEPARADA",""+t+"-"+c);
    }
}