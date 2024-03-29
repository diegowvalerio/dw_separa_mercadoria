package br.com.dw_separa_mercadoria.entidade;

import java.io.Serializable;
import java.lang.String;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


@Entity
@Table(name="tbpedido")
public class Pedido implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue (strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable=true, columnDefinition="numeric(19,0)")
	private BigDecimal pedidoid;
	
	@Column(nullable=true, columnDefinition="numeric(19,0)")
	private BigDecimal codigocliente;
	
	private String nomecliente;
	private Date datapedido;
	private BigDecimal valortotalpedido;
	private String tipopedido;
	
	private Integer ordenacao;
	
	private String status;
	
	@ManyToOne
	@JoinColumn(name = "idlote" , referencedColumnName="idlote" )
	private Lote lote;
	
	@OneToMany(mappedBy="pedido", cascade ={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE },orphanRemoval = true,fetch = FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
    private List<PedidoItem> items = new ArrayList<>();
	
	@Column(nullable=true)
	private long cronometro;
		
	@Temporal(TemporalType.TIMESTAMP)
	private Date cronometro_site;

	public Pedido() {
		super();
	}

	public BigDecimal getCodigocliente() {
		return codigocliente;
	}

	public void setCodigocliente(BigDecimal codigocliente) {
		this.codigocliente = codigocliente;
	}

	public String getNomecliente() {
		return nomecliente;
	}

	public void setNomecliente(String nomecliente) {
		this.nomecliente = nomecliente;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getPedidoid() {
		return pedidoid;
	}

	public void setPedidoid(BigDecimal pedidoid) {
		this.pedidoid = pedidoid;
	}

	public List<PedidoItem> getItems() {
		return items;
	}

	public void setItems(List<PedidoItem> items) {
		this.items = items;
	}

	public Date getDatapedido() {
		return datapedido;
	}

	public void setDatapedido(Date datapedido) {
		this.datapedido = datapedido;
	}

	public BigDecimal getValortotalpedido() {
		return valortotalpedido;
	}

	public void setValortotalpedido(BigDecimal valortotalpedido) {
		this.valortotalpedido = valortotalpedido;
	}

	public String getTipopedido() {
		return tipopedido;
	}

	public void setTipopedido(String tipopedido) {
		this.tipopedido = tipopedido;
	}

	public Integer getOrdenacao() {
		return ordenacao;
	}

	public void setOrdenacao(Integer ordenacao) {
		this.ordenacao = ordenacao;
	}

	public Lote getLote() {
		return lote;
	}

	public void setLote(Lote lote) {
		this.lote = lote;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getCronometro() {
		return cronometro;
	}

	public void setCronometro(long cronometro) {
		this.cronometro = cronometro;
	}

	public Date getCronometro_site() {
		return cronometro_site;
	}

	public void setCronometro_site(Date cronometro_site) {
		this.cronometro_site = cronometro_site;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pedidoid == null) ? 0 : pedidoid.hashCode());
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
		Pedido other = (Pedido) obj;
		if (pedidoid == null) {
			if (other.pedidoid != null)
				return false;
		} else if (!pedidoid.equals(other.pedidoid))
			return false;
		return true;
	}



}
