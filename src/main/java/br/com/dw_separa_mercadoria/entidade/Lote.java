package br.com.dw_separa_mercadoria.entidade;

import java.io.Serializable;
import java.lang.Boolean;
import java.lang.Integer;
import java.lang.String;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name="tblote")
public class Lote implements Serializable {
	   
	@Id
	@GeneratedValue (strategy=GenerationType.IDENTITY)
	private Integer idlote;
	
	@Column(nullable=false,columnDefinition="varchar(100)")
	private String nome;
	
	private Date datalote;
	
	@OneToMany(mappedBy="lote", cascade ={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE },orphanRemoval = true,fetch = FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
    private List<Pedido> pedidos = new ArrayList<>();
	
	private static final long serialVersionUID = 1L;

	public Integer getIdlote() {
		return idlote;
	}

	public void setIdlote(Integer idlote) {
		this.idlote = idlote;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDatalote() {
		return datalote;
	}

	public void setDatalote(Date datalote) {
		this.datalote = datalote;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idlote == null) ? 0 : idlote.hashCode());
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
		Lote other = (Lote) obj;
		if (idlote == null) {
			if (other.idlote != null)
				return false;
		} else if (!idlote.equals(other.idlote))
			return false;
		return true;
	}

}
