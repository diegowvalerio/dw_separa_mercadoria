package br.com.dw_separa_mercadoria.dao.generico;

import java.util.Date;
import java.util.List;

import br.com.dw_separa_mercadoria.entidade.Pedido;


public interface DAOGenerico<E> {
	public E salvar(E e);
	public E alterar(E e);
	public boolean excluir(Integer id);
	public E consultar(Integer id);
	public List<E> consultar();	
	
	public List<Pedido> consultapedidoseven(String codigo);
	
}
