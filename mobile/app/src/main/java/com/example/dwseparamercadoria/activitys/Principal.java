package com.example.dwseparamercadoria.activitys;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dwseparamercadoria.R;
import com.example.dwseparamercadoria.activitys.Pedidos;
import com.example.dwseparamercadoria.entidades.Usuario;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class Principal extends AppCompatActivity {
    Usuario usuario ;
    EditText login;
    EditText senha;
    Button btnentrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.editlogin);
        senha = findViewById(R.id.editsenha);
        btnentrar = findViewById(R.id.btnentrar);
    }


    public void entrar(View view){
        if(login.getText().toString().equals("") || senha.getText().toString().equals("")){
            Toast.makeText(this, "Informe Login / Senha", Toast.LENGTH_SHORT).show();
        }else{
            usuario = new Usuario().getverificalogin(login.getText().toString(),senha.getText().toString());
            if(usuario.getLogin() != null){
                if(usuario.getSituacao() == true){
                    Intent intent = new Intent(this, Tela.class);
                    intent.putExtra("usuario", usuario.getLogin().toString());
                    startActivity(intent);
                    //limpa CAMPO SENHA
                    senha.setText("");
                }else{
                    Toast.makeText(this, "Usuário inativo !", Toast.LENGTH_SHORT).show();
                }

            }else{
                Toast.makeText(this, "Login ou Senha inválidos", Toast.LENGTH_SHORT).show();
            }
        }

    }
}