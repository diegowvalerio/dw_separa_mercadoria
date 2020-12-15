package br.com.dw_separa_mercadoria.entidade;

import java.io.Serializable;
import java.lang.String;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name="tbpedidoitem_leitura")
public class PedidoItem_leitura implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue (strategy=GenerationType.IDENTITY)
	private Integer idleitura;
	
	private Integer qtde_leitura;
	
	private Timestamp datahora_separacao;
	
	@ManyToOne
	@JoinColumn(name = "iditem" , referencedColumnName="iditem" )
	private PedidoItem pedidoitem;
		

	public PedidoItem_leitura() {
		super();
	}


	public Integer getIdleitura() {
		return idleitura;
	}


	public void setIdleitura(Integer idleitura) {
		this.idleitura = idleitura;
	}


	public Integer getQtde_leitura() {
		return qtde_leitura;
	}


	public void setQtde_leitura(Integer qtde_leitura) {
		this.qtde_leitura = qtde_leitura;
	}


	public Timestamp getDatahora_separacao() {
		return datahora_separacao;
	}


	public void setDatahora_separacao(Timestamp datahora_separacao) {
		this.datahora_separacao = datahora_separacao;
	}


	public PedidoItem getPedidoitem() {
		return pedidoitem;
	}


	public void setPedidoitem(PedidoItem pedidoitem) {
		this.pedidoitem = pedidoitem;
	}
	

	
}
