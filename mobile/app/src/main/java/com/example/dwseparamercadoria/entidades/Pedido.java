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
    private String status;
    private Integer idlote;
    private long cronometro;


    public Pedido(){
        super();
    }

    public ArrayList<Pedido> getLista(){
        DB db = new DB();
        ArrayList<Pedido> lista = new ArrayList<>();

        try {
            ResultSet resultSet = db.select("SELECT * FROM tbpedido where status in ('ABERTO','PAUSADO') order by idlote, ordenacao ");
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
                    p.setStatus(resultSet.getString("status"));
                    p.setIdlote(resultSet.getInt("idlote"));
                    p.setCronometro(resultSet.getLong("cronometro"));
                    lista.add(p);
                }
            }
        }catch (Exception e){
            this._mensagem = e.getMessage();
            this._status = false;
        }
        return lista;
    }

    public ArrayList<Pedido> getLista_Separando(){
        DB db = new DB();
        ArrayList<Pedido> lista = new ArrayList<>();

        try {
            ResultSet resultSet = db.select("SELECT * FROM tbpedido where status in ('SEPARANDO') order by idlote, ordenacao ");
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
                    p.setStatus(resultSet.getString("status"));
                    p.setIdlote(resultSet.getInt("idlote"));
                    p.setCronometro(resultSet.getLong("cronometro"));
                    lista.add(p);
                }
            }
        }catch (Exception e){
            this._mensagem = e.getMessage();
            this._status = false;
        }
        return lista;
    }

    public ArrayList<Pedido> getLista_Separandos(){
        DB db = new DB();
        ArrayList<Pedido> lista = new ArrayList<>();

        try {
            ResultSet resultSet = db.select("SELECT * FROM tbpedido where status in ('SEPARADO') order by idlote, ordenacao ");
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
                    p.setStatus(resultSet.getString("status"));
                    p.setIdlote(resultSet.getInt("idlote"));
                    p.setCronometro(resultSet.getLong("cronometro"));
                    lista.add(p);
                }
            }
        }catch (Exception e){
            this._mensagem = e.getMessage();
            this._status = false;
        }
        return lista;
    }

    public void alterar_status(Integer id, String status){
        String comando ="";
        comando = ("update tbpedido set status = '"+status+"' where id = '"+id+"' ;");
        DB db = new DB();
        db.execute(comando);

    }

    public void alterar_cronometro(Integer id, Long tempo){
        String comando ="";
        comando = ("update tbpedido set cronometro = '"+tempo+"' where id = '"+id+"' ;");
        DB db = new DB();
        db.execute(comando);

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getCronometro() {
        return cronometro;
    }

    public void setCronometro(long cronometro) {
        this.cronometro = cronometro;
    }
}
