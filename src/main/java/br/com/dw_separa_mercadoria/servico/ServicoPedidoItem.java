package br.com.dw_separa_mercadoria.servico;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import br.com.dw_separa_mercadoria.dao.DAOPedidoItem;
import br.com.dw_separa_mercadoria.entidade.PedidoItem;
import br.com.dw_separa_mercadoria.hibernate.Transacao;

@Dependent
public class ServicoPedidoItem implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private DAOPedidoItem dao;
	
	@Transacao
	public void salvar(PedidoItem pedidoItem){
		try {
			if(pedidoItem.getIditem() == null){
				dao.salvar(pedidoItem);
			}else{
				dao.alterar(pedidoItem);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	@Transacao
	public boolean excluir(Integer id){
		return dao.excluir(id);
	}
	
	public List<PedidoItem> consultar(){
		return dao.consultar();
	}

}
