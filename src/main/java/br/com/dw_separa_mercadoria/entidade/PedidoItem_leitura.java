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
	
	private String usuario;
	
	@ManyToOne(optional=true)
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


	public String getUsuario() {
		return usuario;
	}


	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idleitura == null) ? 0 : idleitura.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PedidoItem_leitura other = (PedidoItem_leitura) obj;
		if (idleitura == null) {
			if (other.idleitura != null)
				return false;
		} else if (!idleitura.equals(other.idleitura))
			return false;
		return true;
	}
	

	
}
