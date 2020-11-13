package br.com.dw_separa_mercadoria.servico;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import br.com.dw_separa_mercadoria.dao.DAOUsuario;
import br.com.dw_separa_mercadoria.entidade.Usuario;
import br.com.dw_separa_mercadoria.hibernate.Transacao;

@Dependent
public class ServicoUsuario implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private DAOUsuario dao;
	
	@Transacao
	public void salvar(Usuario usuario){
		try {
			if(usuario.getIdusuario() == null){
				dao.salvar(usuario);
			}else{
				dao.alterar(usuario);
			}
					
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Transacao
	public boolean excluir(Integer id){
		return dao.excluir(id);
	}
	
	public List<Usuario> consultar(){
		return dao.consultar();
	}
}
