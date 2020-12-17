package com.example.dwseparamercadoria.entidades;

import com.example.dwseparamercadoria.banco.DB;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;


public class PedidoItem_leitura {

    private Integer idleitura;
    private Integer qtde_leitura;
    private Timestamp datahora_separacao;
    private String usuario;
    private Integer iditem; // tabela PedidoItem


    public PedidoItem_leitura(){
        super();
    }

    //busca itens no banco do servidor
    public ArrayList<PedidoItem_leitura> getLista(Integer iditem){
        DB db = new DB();
        ArrayList<PedidoItem_leitura> lista = new ArrayList<>();

        try {
            ResultSet resultSet = db.select("select * from tbpedidoitem_leitura where iditem  = '"+iditem+"'");
            if(resultSet != null){
                while (resultSet.next()){
                    PedidoItem_leitura p = new PedidoItem_leitura();

                    p.setIdleitura(resultSet.getInt("idleitura"));
                    p.setQtde_leitura(resultSet.getInt("qtde_leitura"));
                    p.setDatahora_separacao(resultSet.getTimestamp("datahora_separacao"));
                    p.setIditem(resultSet.getInt("iditem"));
                    p.setUsuario(resultSet.getString("usuario"));
                    lista.add(p);
                }
            }
        }catch (Exception e){
           e.printStackTrace();
        }
        return lista;
    }

    public void inserir(){
        String comando ="";
        comando = ("INSERT INTO tbpedidoitem_leitura (qtde_leitura,datahora_separacao, usuario ,iditem)  VALUES ('"+ this.getQtde_leitura()+"','"+ this.getDatahora_separacao()+"','"+ this.getUsuario()+"','"+ this.getIditem()+"'); ");
        DB db = new DB();
        db.execute(comando);

    }

    public void excluir() {
        String comando ="";
        comando = ("DELETE FROM tbpedidoitem_leitura WHERE IDLEITURA = "+this.getIdleitura());
        DB db = new DB();
        db.execute(comando);
    }

    public Integer getIdleitura() {
        return idleitura;
    }

    public void setIdleitura(Integer idleitura) {
        this.idleitura = idleitura;
    }

    public Integer getQtde_leitura() {
        return qtde_leitura;
    }

    public void setQtde_leitura(Integer qtde_leitura) {
        this.qtde_leitura = qtde_leitura;
    }

    public Timestamp getDatahora_separacao() {
        return datahora_separacao;
    }

    public void setDatahora_separacao(Timestamp datahora_separacao) {
        this.datahora_separacao = datahora_separacao;
    }

    public Integer getIditem() {
        return iditem;
    }

    public void setIditem(Integer iditem) {
        this.iditem = iditem;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
