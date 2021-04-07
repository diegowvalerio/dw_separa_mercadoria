package com.example.dwseparamercadoria.activitys;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dwseparamercadoria.R;
import com.example.dwseparamercadoria.adapter.PedidoItem_LeituraAdapter;
import com.example.dwseparamercadoria.entidades.PedidoItem;
import com.example.dwseparamercadoria.entidades.PedidoItem_leitura;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Leitura extends AppCompatActivity implements AdapterView.OnItemLongClickListener {
    private PedidoItem item;
    private PedidoItem_leitura pedidoItem_leitura;
    private ListView listView;
    private ImageView imagem;
    private TextView codigo;
    private TextView nome;
    private TextView qtde;
    private TextView local;
    private String ean;
    private Button btnleitura;
    private String usuario;
    private EditText codigobarras;

    //imagem
    Bitmap raw;
    byte[] fotoArray;

    private Integer qtdelido;
    private TextView lido;
    private AlertDialog alerta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leitura);
        lido = findViewById(R.id.lido);
        imagem = findViewById(R.id.img);
        codigo = findViewById(R.id.produtoid);
        nome = findViewById(R.id.nomeproduto);
        qtde = findViewById(R.id.qtde);
        local = findViewById(R.id.local);
        listView = findViewById(R.id.listview_leitura);
        listView.setOnItemLongClickListener(this);

        codigobarras = findViewById(R.id.codigobarras);
        //codigobarras.requestFocus();
        codigobarras.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_DOWN) {

                    if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER || keyCode == KeyEvent.KEYCODE_ENTER) {
                        if(qtdelido < item.getQuantidadeproduto().intValue()) {
                            int sobra = item.getQuantidadeproduto().intValue() - qtdelido;

                            final int finalSobra = sobra;

                            if (codigobarras.getText() != null) {
                                String codigo = codigobarras.getText().toString();
                                if (codigo.length() == 13) {
                                    if (ean.equals(codigo)) {
                                        pedidoItem_leitura = new PedidoItem_leitura();
                                        pedidoItem_leitura.setQtde_leitura(1);
                                        pedidoItem_leitura.setIditem(item.getIditem());
                                        pedidoItem_leitura.setDatahora_separacao(new Timestamp(System.currentTimeMillis()));
                                        pedidoItem_leitura.setUsuario(usuario);
                                        pedidoItem_leitura.inserir();
                                        atualizalista();
                                        calculaleituras();
                                        codigobarras.setText(null);
                                    }else{
                                        codigobarras.setText(null);
                                        alert("Erro: Produto não encontrado !");
                                    }
                                } else if (codigo.length() == 18) {
                                    String codigo13 = codigo.substring(0, 13);
                                    String qtde = codigo.substring(13, 18);
                                    if (ean.equals(codigo13)) {
                                        if (Integer.valueOf(qtde) != 0 && Integer.valueOf(qtde) <= finalSobra) {
                                            //Toast.makeText(this, "qtde: "+Integer.valueOf(qtde), Toast.LENGTH_LONG).show();
                                            pedidoItem_leitura = new PedidoItem_leitura();
                                            pedidoItem_leitura.setQtde_leitura(Integer.valueOf(qtde));
                                            pedidoItem_leitura.setIditem(item.getIditem());
                                            pedidoItem_leitura.setDatahora_separacao(new Timestamp(System.currentTimeMillis()));
                                            pedidoItem_leitura.setUsuario(usuario);
                                            pedidoItem_leitura.inserir();
                                            atualizalista();
                                            calculaleituras();
                                            codigobarras.setText(null);
                                        } else {
                                            codigobarras.setText(null);
                                            alert("Quantidade inválida !");
                                        }
                                    }else{
                                        codigobarras.setText(null);
                                        alert("Erro: Produto não encontrado !");
                                    }
                                } else {
                                    codigobarras.setText(null);
                                    alert("Erro: Produto não encontrado !");
                                }
                            }
                        }else{
                            codigobarras.setText(null);
                            alert("Produto já esta separado por completo !");
                        }
                        return true;
                    }
                }
                return false;
            }
        });

        btnleitura = findViewById(R.id.btnleitor);
        final Activity activity = this;
        btnleitura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(qtdelido < item.getQuantidadeproduto().intValue()) {
                    IntentIntegrator integrator = new IntentIntegrator(activity);
                    integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                    integrator.setPrompt("Leitor de Código de Barras");
                    integrator.setCameraId(0);
                    integrator.setBeepEnabled(true);
                    integrator.setOrientationLocked(false);
                    integrator.initiateScan();
                }else{
                    alert("Produto já esta separado por completo !");
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        preencherLista();
    }
    private void preencherLista() {

        Bundle bundle = getIntent().getExtras();
        if (bundle!=null && bundle.containsKey("item")) {
            usuario = bundle.getString("usuario");

            item = new PedidoItem().getItem(bundle.getInt("item"));
            if (item != null) {
                codigo.setText(item.getCodigoproduto().toString());
                nome.setText(item.getNomeproduto());
                qtde.setText(item.getQuantidadeproduto().toString());
                local.setText(item.getLocalizacao());
                ean = item.getEan();
                fotoArray = item.getImagem();
                if(fotoArray!=null){
                    raw  = BitmapFactory.decodeByteArray(fotoArray,0,fotoArray.length);
                    imagem.setImageBitmap(raw);
                }
                PedidoItem_LeituraAdapter pedidoItem_leituraAdapter = new PedidoItem_LeituraAdapter(this,new PedidoItem_leitura().getLista(bundle.getInt("item")));
                listView.setAdapter(pedidoItem_leituraAdapter);

                calculaleituras();
                Log.i("EAN DO PRODUTO ",ean);
            }

        }
    }

    private void atualizalista(){
        PedidoItem_LeituraAdapter pedidoItem_leituraAdapter = new PedidoItem_LeituraAdapter(this,new PedidoItem_leitura().getLista(item.getIditem()));
        listView.setAdapter(pedidoItem_leituraAdapter);
    }

    private void calculaleituras(){
        qtdelido = 0;
        if(item != null) {
            List<PedidoItem_leitura> l = new PedidoItem_leitura().getLista(item.getIditem());
            for (PedidoItem_leitura p : l){
                qtdelido = qtdelido + p.getQtde_leitura();
            }

            if(item.getQuantidadeproduto().intValue() == qtdelido){
                item.alterar_separado("SIM",qtdelido);
            }else if(item.getQuantidadeproduto().intValue() != qtdelido && qtdelido > 0){
                item.alterar_separado("INICIO",qtdelido);
            }else{
                item.alterar_separado("NAO",qtdelido);
            }
            lido.setText(qtdelido.toString());
        }
    }

    public void leitura_manual(View view){
        if(qtdelido < item.getQuantidadeproduto().intValue()) {
            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_NUMBER);

            int sobra = item.getQuantidadeproduto().intValue();
            if (item.getQuantidadeseparada() != null) {
                sobra = item.getQuantidadeproduto().intValue() - qtdelido;
            }
            final int finalSobra = sobra;
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Leitura Manual")
                    .setMessage("Informe a quantidade :")
                    .setView(input)
                    .setPositiveButton("Gravar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (input.getText() != null) {
                                int qtde = Integer.parseInt(input.getText().toString());
                                if (qtde != 0 && qtde <= finalSobra) {
                                    pedidoItem_leitura = new PedidoItem_leitura();
                                    pedidoItem_leitura.setQtde_leitura(qtde);
                                    pedidoItem_leitura.setIditem(item.getIditem());
                                    pedidoItem_leitura.setDatahora_separacao(new Timestamp(System.currentTimeMillis()));
                                    pedidoItem_leitura.setUsuario(usuario);
                                    pedidoItem_leitura.inserir();
                                    atualizalista();
                                    calculaleituras();
                                } else {
                                    alert("Quantidade inválida !");
                                }
                            }
                        }

                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
        }else{
            alert("Produto já esta separado por completo !");
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(qtdelido < item.getQuantidadeproduto().intValue()) {
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

            //linha para teste
            //ean = "7898378252519";
            // não esquecer de mudar linha acima;


            int sobra = item.getQuantidadeproduto().intValue() - qtdelido;

            final int finalSobra = sobra;

            if (result != null) {
                if (result.getContents() != null) {
                    String codigo = result.getContents();
                    if (codigo.length() == 13) {
                        if (ean.equals(result.getContents())) {

                            pedidoItem_leitura = new PedidoItem_leitura();
                            pedidoItem_leitura.setQtde_leitura(1);
                            pedidoItem_leitura.setIditem(item.getIditem());
                            pedidoItem_leitura.setDatahora_separacao(new Timestamp(System.currentTimeMillis()));
                            pedidoItem_leitura.setUsuario(usuario);
                            pedidoItem_leitura.inserir();
                            atualizalista();
                            calculaleituras();
                            //alert("Produto selecionado com sucesso !");
                        }
                    } else if (codigo.length() == 18) {
                        String codigo13 = codigo.substring(0, 13);
                        String qtde = codigo.substring(13, 18);
                        if (ean.equals(codigo13)) {
                            if (Integer.valueOf(qtde) != 0 && Integer.valueOf(qtde) <= finalSobra) {
                                //Toast.makeText(this, "qtde: "+Integer.valueOf(qtde), Toast.LENGTH_LONG).show();
                                pedidoItem_leitura = new PedidoItem_leitura();
                                pedidoItem_leitura.setQtde_leitura(Integer.valueOf(qtde));
                                pedidoItem_leitura.setIditem(item.getIditem());
                                pedidoItem_leitura.setDatahora_separacao(new Timestamp(System.currentTimeMillis()));
                                pedidoItem_leitura.setUsuario(usuario);
                                pedidoItem_leitura.inserir();
                                atualizalista();
                                calculaleituras();
                            }else{
                                alert("Quantidade inválida !");
                            }
                        }

                    } else {
                        alert("Erro: Produto não encontrado !");
                    }
                } else {
                    alert("Leitor Cancelado");
                }
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }else{
            alert("Produto já esta separado por completo !");
        }
    }

    private void alert(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        final PedidoItem_leitura leitura = (PedidoItem_leitura) parent.getItemAtPosition(position);
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Exclusão")
                .setMessage("Confirma Exclusão da Leitura selecionada ?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        leitura.excluir();
                        atualizalista();
                        calculaleituras();
                    }

                })
                .setNegativeButton("Não", null)
                .show();

        return false;
    }
}