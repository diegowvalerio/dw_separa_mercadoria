package br.com.dw_separa_mercadoria.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import br.com.dw_separa_mercadoria.entidade.Modulo;
import br.com.dw_separa_mercadoria.entidade.Usuario;
import br.com.dw_separa_mercadoria.entidade.UsuarioModulo;
import br.com.dw_separa_mercadoria.msn.FacesMessageUtil;
import br.com.dw_separa_mercadoria.servico.ServicoModulo;
import br.com.dw_separa_mercadoria.servico.ServicoUsuario;
import br.com.dw_separa_mercadoria.servico.ServicoUsuarioModulo;


@Named
@ViewScoped
public class BeanUsuarioModulo implements Serializable{
	private static final long serialVersionUID = 1L;

	private UsuarioModulo usuarioModulo = new UsuarioModulo();
	private Usuario usuario = new Usuario();
	private Modulo modulo = new Modulo();
	
	@Inject
	private ServicoUsuario servico;
	private List<UsuarioModulo> usuariomodulos;
	
	@Inject
	private ServicoModulo servico_modulo;
	private List<Modulo> modulos;
	
	@PostConstruct
	public void carregar(){
		modulos = servico_modulo.consultar();
		
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		HttpSession session = (HttpSession) request.getSession();
		this.usuario = (Usuario) session.getAttribute("usuario");
		this.usuariomodulos = this.usuario.getUsuariomodulos();

		session.removeAttribute("usuario");
		
	}
	
	public String salvar(){
		try{
		//usuarioModulo.setUsuario(usuario);	
		servico.salvar(usuario);
		}catch(Exception e){
			if(e.getCause().toString().contains("ConstraintViolationException")){
				FacesMessageUtil.addMensagemError("Registro já existente!");
			}else{
				FacesMessageUtil.addMensagemError(e.getCause().toString());
			}
		}
		return "lista-usuario";
	}

	/*public void excluir() {
		try{
		servico.excluir(usuarioModulo.getIdusuario_modulo());
		}catch(Exception e){
			if(e.getCause().toString().contains("ConstraintViolationException")){
				FacesMessageUtil.addMensagemError("Registro utilizado em outro local!");
			}else{
				FacesMessageUtil.addMensagemError(e.getCause().toString());
			}
		}
		usuariomodulos = servico.consultar();
	}*/
	
	//subgrupo
	
	public void addNovo() {
		if (this.usuario.getLogin() == null) {
			throw new RuntimeException("O Modulo não pode ser nulo");
		} else {
			usuarioModulo = new UsuarioModulo();
			usuarioModulo.setUsuario(usuario);
		}
	}
	
	public void remover() {
		int index = usuariomodulos.indexOf(usuarioModulo);
		if (index > -1) {
			this.usuariomodulos.remove(index);
		}
	}
	
	public void editarsalvar() {

		if (usuarioModulo.getModulo()== null) {
			FacesMessageUtil.addMensagemError("Preencha os dados corretamente");
		} else {
			try {
				int index = usuariomodulos.indexOf(usuarioModulo);
				if (index > -1) {
					usuariomodulos.remove(index);
					usuarioModulo.setUsuario(usuario);
					usuariomodulos.add(index, usuarioModulo);
				} else {
					usuarioModulo.setUsuario(usuario);
					usuariomodulos.add(usuarioModulo);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			usuarioModulo = new UsuarioModulo();
		}
	}

	public UsuarioModulo getUsuarioModulo() {
		return usuarioModulo;
	}

	public void setUsuarioModulo(UsuarioModulo usuarioModulo) {
		this.usuarioModulo = usuarioModulo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Modulo getModulo() {
		return modulo;
	}

	public void setModulo(Modulo modulo) {
		this.modulo = modulo;
	}

	public List<UsuarioModulo> getUsuariomodulos() {
		return usuariomodulos;
	}

	public void setUsuariomodulos(List<UsuarioModulo> usuariomodulos) {
		this.usuariomodulos = usuariomodulos;
	}

	public List<Modulo> getModulos() {
		return modulos;
	}

	public void setModulos(List<Modulo> modulos) {
		this.modulos = modulos;
	}
	
	
		

}
