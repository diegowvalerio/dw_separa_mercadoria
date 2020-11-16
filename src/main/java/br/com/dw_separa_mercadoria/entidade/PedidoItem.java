package br.com.dw_separa_mercadoria.entidade;

import java.io.Serializable;
import java.lang.String;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name="tbpedidoitem")
public class PedidoItem implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue (strategy=GenerationType.IDENTITY)
	private Integer iditem;
	
	@Column(nullable=true, columnDefinition="numeric(19,0)")
	private BigDecimal pedidoid;
	
	@Column(nullable=true, columnDefinition="numeric(19,0)")
	private BigDecimal codigoproduto;
	
	private String nomeproduto;
	private BigDecimal quantidadeproduto; 	
	
	@Column(columnDefinition="bytea")
	private byte[] imagem;
	
	private String localizacao;
	
	private Timestamp datahora_separacao;
	
	@ManyToOne
	@JoinColumn(name = "id" , referencedColumnName="id" )
	private Pedido pedido;
		

	public PedidoItem() {
		super();
	}
	

	public Integer getIditem() {
		return iditem;
	}


	public void setIditem(Integer iditem) {
		this.iditem = iditem;
	}


	public BigDecimal getPedidoid() {
		return pedidoid;
	}


	public void setPedidoid(BigDecimal pedidoid) {
		this.pedidoid = pedidoid;
	}


	public Pedido getPedido() {
		return pedido;
	}


	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}


	public BigDecimal getCodigoproduto() {
		return codigoproduto;
	}

	public void setCodigoproduto(BigDecimal codigoproduto) {
		this.codigoproduto = codigoproduto;
	}

	public String getNomeproduto() {
		return nomeproduto;
	}

	public void setNomeproduto(String nomeproduto) {
		this.nomeproduto = nomeproduto;
	}

	public BigDecimal getQuantidadeproduto() {
		return quantidadeproduto;
	}

	public void setQuantidadeproduto(BigDecimal quantidadeproduto) {
		this.quantidadeproduto = quantidadeproduto;
	}

	public byte[] getImagem() {
		return imagem;
	}

	public void setImagem(byte[] imagem) {
		this.imagem = imagem;
	}


	public String getLocalizacao() {
		return localizacao;
	}


	public void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}


	public Timestamp getDatahora_separacao() {
		return datahora_separacao;
	}


	public void setDatahora_separacao(Timestamp datahora_separacao) {
		this.datahora_separacao = datahora_separacao;
	}
	
	
}
