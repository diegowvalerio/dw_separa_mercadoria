package br.com.dw_separa_mercadoria.hibernate;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import br.com.dw_separa_mercadoria.dao.DAOUsuarioModulo;
import br.com.dw_separa_mercadoria.entidade.UsuarioModulo;
import br.com.dw_separa_mercadoria.hibernate.generico.DAOGenericoHibernate;

@Dependent
public class HibernateUsuarioModulo extends DAOGenericoHibernate<UsuarioModulo> implements DAOUsuarioModulo,Serializable {
	private static final long serialVersionUID = 1L;
	
	public HibernateUsuarioModulo(){
		super(UsuarioModulo.class);
	}


}
