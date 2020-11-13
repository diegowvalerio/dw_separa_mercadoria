package br.com.dw_separa_mercadoria.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.dw_separa_mercadoria.entidade.Usuario;
import br.com.dw_separa_mercadoria.msn.FacesMessageUtil;
import br.com.dw_separa_mercadoria.servico.ServicoUsuario;

@Named
@ViewScoped
public class BeanListaUsuario implements Serializable{
	private static final long serialVersionUID = 1L;

	private Usuario usuario = new Usuario();
	@Inject
	private ServicoUsuario servico;
	private List<Usuario> lista;
	private Date data;
	
	@PostConstruct
	public void carregar(){
		lista = servico.consultar();
	}	
	
	public String salvar(){
		try{
		servico.salvar(usuario);
		}catch(Exception e){
			if(e.getCause().toString().contains("ConstraintViolationException")){
				FacesMessageUtil.addMensagemError("Registro já existente! ");
			}else{
				FacesMessageUtil.addMensagemError(e.getCause().toString());
			}
		}
		lista = servico.consultar();
		return "lista-usuario";
	}
	
	public void excluir(){
		try{
		servico.excluir(usuario.getIdusuario());
		}catch(Exception e){
			if(e.getCause().toString().contains("ConstraintViolationException")){
				FacesMessageUtil.addMensagemError("Registro utilizado em outro local! Não foi possível realizar a operação.");
			}else{
				FacesMessageUtil.addMensagemError(e.getCause().toString());
		}
	}
		lista = servico.consultar();
	}
	
	public String encaminha() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(true);
		session.setAttribute("usuario", this.usuario);

		return "usuariomodulo";
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getLista() {
		return lista;
	}

	public void setLista(List<Usuario> lista) {
		this.lista = lista;
	}
		

}
