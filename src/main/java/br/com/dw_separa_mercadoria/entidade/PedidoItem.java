package br.com.dw_separa_mercadoria.entidade;

import java.io.Serializable;
import java.lang.String;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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
	
	@Column(nullable=true, columnDefinition="numeric(19,0)")
	private BigDecimal quantidadeproduto; 	
	
	@Column(columnDefinition="bytea")
	private byte[] imagem;
	
	private String localizacao;
	
	private Timestamp datahora_separacao;
	
	private String separado;
	
	private Integer quantidadeseparada;
	
	public String getSeparado() {
		return separado;
	}


	public void setSeparado(String separado) {
		this.separado = separado;
	}


	private String ean;
	
	@ManyToOne
	@JoinColumn(name = "id" , referencedColumnName="id" )
	private Pedido pedido;
	
	@OneToMany(mappedBy="pedidoitem", cascade ={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE },orphanRemoval = true,fetch = FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
    private List<PedidoItem_leitura> leituras = new ArrayList<>();
		

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


	public String getEan() {
		return ean;
	}


	public void setEan(String ean) {
		this.ean = ean;
	}


	public List<PedidoItem_leitura> getLeituras() {
		return leituras;
	}


	public void setLeituras(List<PedidoItem_leitura> leituras) {
		this.leituras = leituras;
	}


	public Integer getQuantidadeseparada() {
		return quantidadeseparada;
	}


	public void setQuantidadeseparada(Integer quantidadeseparada) {
		this.quantidadeseparada = quantidadeseparada;
	}
	
	
}
