package br.com.dw_separa_mercadoria.entidade;

import java.io.Serializable;
import java.lang.String;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name="tbusuario")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue (strategy=GenerationType.IDENTITY)
	private Integer idusuario;
	@Column(nullable=false,columnDefinition="varchar(250)")
	private String nome;
	@Column(nullable=false) 
	@Temporal(TemporalType.DATE)
	private Date dtcadastro;
	@Column(nullable=false, columnDefinition= "BOOLEAN DEFAULT TRUE")
	private Boolean situacao;
	
	@Column(nullable=false, columnDefinition="varchar(10)")
	private String login;
	@Column(nullable=false, columnDefinition="varchar(100)")
	private String senha;
	
	@OneToMany(mappedBy="usuario", cascade ={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE },orphanRemoval = true,fetch = FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
    private List<UsuarioModulo> usuariomodulos = new ArrayList<>();
		

	public Usuario() {
		super();
	}   
	
	public Integer getIdusuario() {
		return idusuario;
	}

	public void setIdusuario(Integer idusuario) {
		this.idusuario = idusuario;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}   
	public String getSenha() {
		return this.senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Date getDtcadastro() {
		return dtcadastro;
	}
	public void setDtcadastro(Date dtcadastro) {
		this.dtcadastro = dtcadastro;
	}
	public Boolean getSituacao() {
		return situacao;
	}
	public void setSituacao(Boolean situacao) {
		this.situacao = situacao;
	}

	public List<UsuarioModulo> getUsuariomodulos() {
		return usuariomodulos;
	}

	public void setUsuariomodulos(List<UsuarioModulo> usuariomodulos) {
		this.usuariomodulos = usuariomodulos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idusuario == null) ? 0 : idusuario.hashCode());
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
		Usuario other = (Usuario) obj;
		if (idusuario == null) {
			if (other.idusuario != null)
				return false;
		} else if (!idusuario.equals(other.idusuario))
			return false;
		return true;
	}

   
}
