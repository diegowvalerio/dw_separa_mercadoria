package com.example.dwseparamercadoria.entidades;

import com.example.dwseparamercadoria.banco.DB;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class Pedido extends _Default {

    private Integer id;
    private BigDecimal pedidoid;
    private BigDecimal codigocliente;
    private String nomecliente;
    private Date datapedido;
    private BigDecimal valortotalpedido;
    private String tipopedido;
    private Integer ordenacao;
    private Integer idlote;

    public Pedido(){
        super();
    }

    public ArrayList<Pedido> getLista(){
        DB db = new DB();
        ArrayList<Pedido> lista = new ArrayList<>();

        try {
            ResultSet resultSet = db.select("SELECT * FROM tbpedido order by idlote, ordenacao ");
            if(resultSet != null){
                while (resultSet.next()){
                    Pedido p = new Pedido();
                    p.setId(resultSet.getInt("id"));
                    p.setPedidoid(resultSet.getBigDecimal("pedidoid"));
                    p.setCodigocliente(resultSet.getBigDecimal("codigocliente"));
                    p.setNomecliente(resultSet.getString("nomecliente"));
                    p.setDatapedido(resultSet.getDate("datapedido"));
                    p.setValortotalpedido(resultSet.getBigDecimal("valortotalpedido"));
                    p.setTipopedido(resultSet.getString("tipopedido"));
                    p.setOrdenacao(resultSet.getInt("ordenacao"));
                    p.setIdlote(resultSet.getInt("idlote"));

                    lista.add(p);
                }
            }
        }catch (Exception e){
            this._mensagem = e.getMessage();
            this._status = false;
        }
        return lista;
    }




    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getPedidoid() {
        return pedidoid;
    }

    public void setPedidoid(BigDecimal pedidoid) {
        this.pedidoid = pedidoid;
    }

    public BigDecimal getCodigocliente() {
        return codigocliente;
    }

    public void setCodigocliente(BigDecimal codigocliente) {
        this.codigocliente = codigocliente;
    }

    public String getNomecliente() {
        return nomecliente;
    }

    public void setNomecliente(String nomecliente) {
        this.nomecliente = nomecliente;
    }

    public Date getDatapedido() {
        return datapedido;
    }

    public void setDatapedido(Date datapedido) {
        this.datapedido = datapedido;
    }

    public BigDecimal getValortotalpedido() {
        return valortotalpedido;
    }

    public void setValortotalpedido(BigDecimal valortotalpedido) {
        this.valortotalpedido = valortotalpedido;
    }

    public String getTipopedido() {
        return tipopedido;
    }

    public void setTipopedido(String tipopedido) {
        this.tipopedido = tipopedido;
    }

    public Integer getOrdenacao() {
        return ordenacao;
    }

    public void setOrdenacao(Integer ordenacao) {
        this.ordenacao = ordenacao;
    }

    public Integer getIdlote() {
        return idlote;
    }

    public void setIdlote(Integer idlote) {
        this.idlote = idlote;
    }

}
