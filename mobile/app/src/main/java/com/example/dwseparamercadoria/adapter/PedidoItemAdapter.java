package com.example.dwseparamercadoria.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dwseparamercadoria.R;
import com.example.dwseparamercadoria.entidades.PedidoItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PedidoItemAdapter extends ArrayAdapter<PedidoItem> {
    private Context context;
    private ArrayList<PedidoItem> lista;

    public PedidoItemAdapter(Context context, ArrayList<PedidoItem> lista){
        super(context,0,lista);
        this.context = context;
        this.lista = lista;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final PedidoItem itemPosicao = this.lista.get(position);

        View layout = LayoutInflater.from(this.context).inflate(R.layout.lista_itens,parent,false);
        if(itemPosicao != null) {

            TextView produtoid = (TextView) layout.findViewById(R.id.produtoid);
            produtoid.setText(itemPosicao.getCodigoproduto().toString());

            TextView produto = (TextView) layout.findViewById(R.id.nomeproduto);
            produto.setText(itemPosicao.getNomeproduto());

            TextView qtde = (TextView) layout.findViewById(R.id.qtde);
            qtde.setText(itemPosicao.getQuantidadeproduto().toString());

            TextView local = (TextView) layout.findViewById(R.id.localizacao);
            local.setText(itemPosicao.getLocalizacao());

            TextView ean = (TextView) layout.findViewById(R.id.ean);
            ean.setText(itemPosicao.getEan());

            if(itemPosicao.getSeparado() != null) {
                if (itemPosicao.getSeparado().equals("SIM")) {
                    layout.setBackgroundColor(Color.rgb(187 ,255,255));
                }
                if(itemPosicao.getSeparado().equals("INICIO")){
                    layout.setBackgroundColor(Color.rgb(255 ,235,205));
                }
            }
        }

        return layout;
    }
}
