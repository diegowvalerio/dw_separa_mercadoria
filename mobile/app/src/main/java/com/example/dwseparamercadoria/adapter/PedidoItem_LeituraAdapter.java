package com.example.dwseparamercadoria.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.dwseparamercadoria.R;
import com.example.dwseparamercadoria.entidades.PedidoItem_leitura;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PedidoItem_LeituraAdapter extends ArrayAdapter<PedidoItem_leitura> {
    private Context context;
    private ArrayList<PedidoItem_leitura> lista;

    public PedidoItem_LeituraAdapter(Context context, ArrayList<PedidoItem_leitura> lista){
        super(context,0,lista);
        this.context = context;
        this.lista = lista;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final PedidoItem_leitura itemPosicao = this.lista.get(position);

        View layout = LayoutInflater.from(this.context).inflate(R.layout.lista_leituras,parent,false);
        if(itemPosicao != null) {

            TextView leituraid = (TextView) layout.findViewById(R.id.leituraid);
            leituraid.setText(itemPosicao.getIdleitura().toString());

            TextView qtdeleitura = (TextView) layout.findViewById(R.id.qtdeleitura);
            qtdeleitura.setText(itemPosicao.getQtde_leitura().toString());

            String dataEmUmFormato = itemPosicao.getDatahora_separacao().toString();
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date data = null;
            try {
                data = formato.parse(dataEmUmFormato);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            formato.applyPattern("dd/MM/yyyy HH:mm:ss");
            String dataFormatada = formato.format(data);

            TextView dataleitura = (TextView) layout.findViewById(R.id.dataleitura);
            dataleitura.setText(dataFormatada);

            TextView usuario = layout.findViewById(R.id.usuario);
            usuario.setText(itemPosicao.getUsuario().toString());

        }

        return layout;
    }
}
