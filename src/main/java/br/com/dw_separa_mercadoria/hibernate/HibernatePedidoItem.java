package br.com.dw_separa_mercadoria.hibernate;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import br.com.dw_separa_mercadoria.dao.DAOPedidoItem;
import br.com.dw_separa_mercadoria.entidade.PedidoItem;
import br.com.dw_separa_mercadoria.hibernate.generico.DAOGenericoHibernate;

@Dependent
public class HibernatePedidoItem extends DAOGenericoHibernate<PedidoItem> implements DAOPedidoItem,Serializable {
	private static final long serialVersionUID = 1L;
	
	public HibernatePedidoItem(){
		super(PedidoItem.class);
	}


}
