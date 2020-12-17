package com.example.dwseparamercadoria.entidades;

import android.util.Log;

import com.example.dwseparamercadoria.banco.DB;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

public class Usuario extends _Default {

    private Integer idusuario;
    private String nome;
    private Boolean situacao;
    private String login;
    private String senha;


    public Usuario(){
        super();
    }

    public Usuario getverificalogin(String login, String senha){
        Log.i("LOGIN",""+login+"-"+senha);

        DB db = new DB();
        Usuario p = new Usuario();

        try {
            ResultSet resultSet = db.select("SELECT * FROM tbusuario where login = '"+login+"' and senha = '"+senha+"'");
            if(resultSet != null){
                while (resultSet.next()){
                    p.setIdusuario(resultSet.getInt("idusuario"));
                    p.setNome(resultSet.getString("nome"));
                    p.setLogin(resultSet.getString("login"));
                    p.setSenha(resultSet.getString("senha"));
                    p.setSituacao(resultSet.getBoolean("situacao"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p;
    }

    public Integer getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Integer idusuario) {
        this.idusuario = idusuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Boolean getSituacao() {
        return situacao;
    }

    public void setSituacao(Boolean situacao) {
        this.situacao = situacao;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
