package br.com.dw_separa_mercadoria.hibernate;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import br.com.dw_separa_mercadoria.dao.DAOUsuario;
import br.com.dw_separa_mercadoria.entidade.Usuario;
import br.com.dw_separa_mercadoria.hibernate.generico.DAOGenericoHibernate;

@Dependent
public class HibernateUsuario extends DAOGenericoHibernate<Usuario> implements DAOUsuario,Serializable {
	private static final long serialVersionUID = 1L;
	
	public HibernateUsuario(){
		super(Usuario.class);
	}

}
