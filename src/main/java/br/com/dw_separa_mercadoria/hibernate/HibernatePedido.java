package br.com.dw_separa_mercadoria.hibernate;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import br.com.dw_separa_mercadoria.dao.DAOPedido;
import br.com.dw_separa_mercadoria.entidade.Pedido;
import br.com.dw_separa_mercadoria.hibernate.generico.DAOGenericoHibernate;

@Dependent
public class HibernatePedido extends DAOGenericoHibernate<Pedido> implements DAOPedido,Serializable {
	private static final long serialVersionUID = 1L;
	
	public HibernatePedido(){
		super(Pedido.class);
	}


}
