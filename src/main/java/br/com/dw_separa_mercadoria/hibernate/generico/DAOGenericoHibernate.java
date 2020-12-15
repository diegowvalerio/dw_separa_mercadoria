package br.com.dw_separa_mercadoria.hibernate.generico;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import br.com.dw_separa_mercadoria.dao.generico.DAOGenerico;
import br.com.dw_separa_mercadoria.entidade.Pedido;
import br.com.dw_separa_mercadoria.entidade.PedidoItem;
import br.com.dw_separa_mercadoria.fabrica.EntityManagerProducerSeven.Corporativo;

public class DAOGenericoHibernate<E> implements DAOGenerico<E>, Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	protected EntityManager manager;
	private Class classeEntidade;

	// seven
	@Inject
	@Corporativo
	protected EntityManager managerSeven;

	public DAOGenericoHibernate(Class classeEntidade) {
		this.classeEntidade = classeEntidade;
	}

	@Override
	public E salvar(E e) {
		manager.persist(e);
		return e;
	}

	@Override
	public E alterar(E e) {
		return manager.merge(e);
	}

	@Override
	public boolean excluir(Integer id) {
		E e = consultar(id);
		manager.remove(e);
		return true;
	}

	@Override
	public E consultar(Integer id) {
		return (E) manager.find(classeEntidade, id);
	}

	@Override
	public List<E> consultar() {
		return manager.createQuery("from " + classeEntidade.getSimpleName()).getResultList();
	}
	
	//busca pedido do Seven e itens que estão na fase de conferencia com reserva de itens total
	public List<Pedido> consultapedidoseven(String codigo){
		List<Pedido> list = new ArrayList<>();
	
		javax.persistence.Query query = (javax.persistence.Query) managerSeven.createNativeQuery(
			"SELECT "
			+ " PV.PEDIDOVENDAID PEDIDO, "
			+ " P.CADCFTVID CLIENTE, "
			+ " C.APELIDO_CADCFTV NOMECLIENTE, "
			+ " P.DT_PEDIDOVENDA, "
			+ " P.VL_TOTALPROD_PEDIDOVENDA, "
			+ " tp.DESC_TIPO_PEDIDO "
			+ " "
			+ " FROM V_PEDIDOVENDA_CONFERENCIA PV"
			+ " INNER JOIN PEDIDOVENDA P ON P.PEDIDOVENDAID = PV.PEDIDOVENDAID "
			+ " INNER JOIN CADCFTV C ON C.CADCFTVID = P.CADCFTVID "
			+ " inner join TIPO_PEDIDO tp on tp.TIPOPEDIDOID = P.TIPOPEDIDOID "
			+ " WHERE PV.QT_TOTAL_ENTREGA = PV.QT_TOTAL "
			+ " AND P.STATUS_PEDIDOVENDA = 'ABERTO' "
			+ " AND P.ROTEIROID = 6 "
			+ " AND P.PEDIDOVENDAID = '"+ codigo +"'" );
		
		List<Object[]> lista = query.getResultList();
		
		for (Object[] row : lista) {
			
			Pedido pedido = new Pedido();	
			
			pedido.setPedidoid((BigDecimal) row[0]);
			pedido.setCodigocliente((BigDecimal) row[1]);
			pedido.setNomecliente((String) row[2]);
			pedido.setDatapedido((Date) row[3]);
			pedido.setValortotalpedido((BigDecimal) row[4]);
			pedido.setTipopedido((String) row[5]);
			
			//adicionar itens do pedido 
			
			javax.persistence.Query query2 = (javax.persistence.Query) managerSeven.createNativeQuery(
					" select "
					+ " it.pedidovendaid, "
					+ " it.produtoid, "
					+ " pr.NOME_PRODUTO, "
					+ " it.QT_PEDIDOVENDA_ITEM, "
					+ " img.IMAGEM_PRODUTO, "
					+ " loc.DS_LOCALIZACAO, "
					+ " pr.REFERENCIA_ORIGINAL_PRODUTO "
					+ " from pedidovenda_item it "
					+ " inner join produto pr on pr.produtoid = it.produtoid "
					+ " left join PRODUTO_IMAGEM img on img.produtoid = it.produtoid "
					+ " left join ALMOXARIFADO_PRODUTO al on al.PRODUTOID = pr.produtoid and al.ALMOXARIFADOID = 1 "
					+ " left join LOCALIZACAOPRODUTO loc on loc.LOCALIZACAOPRODUTOID = al.LOCALIZACAOPRODUTOID "
					+ " where it.pedidovendaid = '"+ pedido.getPedidoid().toString() +"' ");
			List<Object[]> lista2 = query2.getResultList();
			List<PedidoItem> list2 = new ArrayList<>();
			
			for (Object[] row2 : lista2) {
				PedidoItem item = new PedidoItem();
				Blob img = (Blob) row2[4];
				
				
				item.setPedido(pedido);
				item.setPedidoid(pedido.getPedidoid());
				item.setCodigoproduto((BigDecimal) row2[1]);
				item.setNomeproduto((String) row2[2]);
				item.setQuantidadeproduto((BigDecimal) row2[3]);
				if (img != null) {
					try {
						byte[] bytes = img.getBytes(1, (int) img.length());
						item.setImagem(bytes);
						//System.out.println("ok");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				item.setLocalizacao((String) row2[5]);
				item.setEan((String) row2[6]);
				item.setSeparado("NAO");
				
				list2.add(item);
			}
			
			pedido.setItems(list2);
			list.add(pedido);
		}
		
		return list;
	}
	

	public Integer consultapedido_existe(BigDecimal pedidoid) {
		Integer t = 0;
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(classeEntidade);

		criteria.add(Restrictions.eq("pedidoid", pedidoid));
		List<E> l = criteria.list();
		if (l.size()>0) {
			t = 1;
		}
		
		return t;
	}
	
}


