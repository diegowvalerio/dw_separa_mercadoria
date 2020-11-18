package com.example.dwseparamercadoria.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.dwseparamercadoria.R;
import com.example.dwseparamercadoria.entidades.Pedido;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PedidoAdapter extends ArrayAdapter<Pedido> {
    private Context context;
    private ArrayList<Pedido> lista;

    public PedidoAdapter(Context context, ArrayList<Pedido> lista){
        super(context,0,lista);
        this.context = context;
        this.lista = lista;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Pedido itemPosicao = this.lista.get(position);

        convertView = LayoutInflater.from(this.context).inflate(R.layout.lista_pedidos,parent,false);
        final View layout = convertView;

        TextView pedidoid = (TextView) convertView.findViewById(R.id.pedidoid);
        pedidoid.setText (itemPosicao.getPedidoid().toString());

        TextView nomecliente = (TextView) convertView.findViewById(R.id.nomecliente);
        nomecliente.setText(itemPosicao.getNomecliente());

        TextView tipopedido = (TextView) convertView.findViewById(R.id.tipopedido);
        tipopedido.setText(itemPosicao.getTipopedido());

        String dataEmUmFormato = itemPosicao.getDatapedido().toString();
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        Date data = null;
        try {
            data = formato.parse(dataEmUmFormato);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        formato.applyPattern("dd/MM/yyyy");
        String dataFormatada = formato.format(data);


        TextView datapedido = (TextView) convertView.findViewById(R.id.datapedido);
        datapedido.setText(dataFormatada);

        return convertView;
    }
}
