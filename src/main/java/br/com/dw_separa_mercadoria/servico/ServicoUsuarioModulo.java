package br.com.dw_separa_mercadoria.servico;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import br.com.dw_separa_mercadoria.dao.DAOUsuarioModulo;
import br.com.dw_separa_mercadoria.entidade.UsuarioModulo;
import br.com.dw_separa_mercadoria.hibernate.Transacao;

@Dependent
public class ServicoUsuarioModulo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private DAOUsuarioModulo dao;
	
	@Transacao
	public void salvar(UsuarioModulo usuario){
		try {
			if(usuario.getIdusuario_modulo() == null){
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
	
	public List<UsuarioModulo> consultar(){
		return dao.consultar();
	}
}
