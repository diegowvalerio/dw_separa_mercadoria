package br.com.dw_separa_mercadoria.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.dw_separa_mercadoria.entidade.Usuario;
import br.com.dw_separa_mercadoria.entidade.UsuarioModulo;
import br.com.dw_separa_mercadoria.servico.ServicoUsuario;


@ManagedBean
@SessionScoped
public class BeanControleAcesso implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Usuario usuario = new Usuario();
	@Inject
	private ServicoUsuario servico;
	private List<Usuario> lista = new ArrayList<>();
	
	@PostConstruct
	public void init() { 
		lista = servico.consultar();
		for (Usuario acesso : lista) {
		if (acesso.getLogin().equals(usuarioconectado())){
			usuario = acesso;
			//System.out.println(usuario.getLogin());
		}	
	}
		
	}
	
	public boolean acesso(String tipo){
	boolean t = false;
		for (UsuarioModulo modulo : usuario.getUsuariomodulos()) {
			if (modulo.getModulo().getIdentificacao().equals(tipo)){
				t = true;
			}	
		}
		return t;
	}
	
	/* pegar usuario conectado */
	public String usuarioconectado() {
		String nome;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			nome = ((UserDetails) principal).getUsername();
		} else {
			nome = principal.toString();
		}
		return nome;
	}

}
