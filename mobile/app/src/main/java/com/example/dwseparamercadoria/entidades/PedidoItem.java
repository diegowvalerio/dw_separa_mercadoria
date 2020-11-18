package com.example.dwseparamercadoria.entidades;

import android.util.Log;

import com.example.dwseparamercadoria.banco.DB;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;


public class PedidoItem {

    private Integer iditem;
    private BigDecimal pedidoid;
    private BigDecimal codigoproduto;
    private String nomeproduto;
    private BigDecimal quantidadeproduto;
    private String localizacao;
    private Timestamp datahora_separacao;
    private String ean;
    private Integer id; // tabela Pedido

    public PedidoItem(){
        super();
    }

    //busca itens no banco do servidor
    public ArrayList<PedidoItem> getLista(Integer idpedido){
        DB db = new DB();
        ArrayList<PedidoItem> lista = new ArrayList<>();

        try {
            ResultSet resultSet = db.select("SELECT * FROM TBPEDIDOITEM WHERE ID ='"+idpedido+"'");
            if(resultSet != null){
                while (resultSet.next()){
                    PedidoItem p = new PedidoItem();

                    p.setIditem(resultSet.getInt("iditem"));
                    p.setPedidoid(resultSet.getBigDecimal("pedidoid"));
                    p.setCodigoproduto(resultSet.getBigDecimal("codigoproduto"));
                    p.setNomeproduto(resultSet.getString("nomeproduto"));
                    p.setQuantidadeproduto(resultSet.getBigDecimal("quantidadeproduto"));
                    p.setLocalizacao(resultSet.getString("localizacao"));
                    p.setEan(resultSet.getString("ean"));
                    p.setId(resultSet.getInt("id"));

                    lista.add(p);
                }
            }
        }catch (Exception e){
           e.printStackTrace();
        }
        return lista;
    }

    public Integer getIditem() {
        return iditem;
    }

    public void setIditem(Integer iditem) {
        this.iditem = iditem;
    }

    public BigDecimal getPedidoid() {
        return pedidoid;
    }

    public void setPedidoid(BigDecimal pedidoid) {
        this.pedidoid = pedidoid;
    }

    public BigDecimal getCodigoproduto() {
        return codigoproduto;
    }

    public void setCodigoproduto(BigDecimal codigoproduto) {
        this.codigoproduto = codigoproduto;
    }

    public String getNomeproduto() {
        return nomeproduto;
    }

    public void setNomeproduto(String nomeproduto) {
        this.nomeproduto = nomeproduto;
    }

    public BigDecimal getQuantidadeproduto() {
        return quantidadeproduto;
    }

    public void setQuantidadeproduto(BigDecimal quantidadeproduto) {
        this.quantidadeproduto = quantidadeproduto;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public Timestamp getDatahora_separacao() {
        return datahora_separacao;
    }

    public void setDatahora_separacao(Timestamp datahora_separacao) {
        this.datahora_separacao = datahora_separacao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }
}
