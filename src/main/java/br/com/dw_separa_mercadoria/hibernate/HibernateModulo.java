package br.com.dw_separa_mercadoria.hibernate;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import br.com.dw_separa_mercadoria.dao.DAOModulo;
import br.com.dw_separa_mercadoria.entidade.Modulo;
import br.com.dw_separa_mercadoria.hibernate.generico.DAOGenericoHibernate;

@Dependent
public class HibernateModulo extends DAOGenericoHibernate<Modulo> implements DAOModulo,Serializable {
	private static final long serialVersionUID = 1L;
	
	public HibernateModulo(){
		super(Modulo.class);
	}


}
