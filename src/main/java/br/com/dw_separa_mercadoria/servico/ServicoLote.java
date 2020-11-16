package br.com.dw_separa_mercadoria.servico;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import br.com.dw_separa_mercadoria.dao.DAOLote;
import br.com.dw_separa_mercadoria.entidade.Lote;
import br.com.dw_separa_mercadoria.hibernate.Transacao;

@Dependent
public class ServicoLote implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private DAOLote dao;
	
	@Transacao
	public void salvar(Lote lote){
		try {
			if(lote.getIdlote() == null){
				dao.salvar(lote);
			}else{
				dao.alterar(lote);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	@Transacao
	public boolean excluir(Integer id){
		return dao.excluir(id);
	}
	
	public List<Lote> consultar(){
		return dao.consultar();
	}

}
