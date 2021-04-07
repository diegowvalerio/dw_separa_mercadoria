package br.com.dw_separa_mercadoria.servico;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import br.com.dw_separa_mercadoria.dao.DAOPedido;
import br.com.dw_separa_mercadoria.entidade.Pedido;
import br.com.dw_separa_mercadoria.hibernate.Transacao;

@Dependent
public class ServicoPedido implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private DAOPedido dao;
	
	@Transacao
	public void salvar(Pedido pedido){
		try {
			if(pedido.getId() == null){
				dao.salvar(pedido);
			}else{
				dao.alterar(pedido);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<Pedido> consultapedidoseven(String codigo){
		return dao.consultapedidoseven(codigo);
	}
	
	public Integer consultapedido_existe(BigDecimal pedidoid) {
		return dao.consultapedido_existe(pedidoid);
	}
	
	public List<Pedido> consultarpedidos(){
		return dao.consultarpedidos();
	}
	
	
	@Transacao
	public boolean excluir(Integer id){
		return dao.excluir(id);
	}
	
	public Pedido consultar(Integer id)
	{
		return dao.consultar(id);
	}
	
	public List<Pedido> consultar(){
		return dao.consultar();
	}

}
