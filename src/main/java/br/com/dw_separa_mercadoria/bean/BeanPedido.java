package br.com.dw_separa_mercadoria.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.RequestScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.dw_separa_mercadoria.entidade.Pedido;
import br.com.dw_separa_mercadoria.msn.FacesMessageUtil;
import br.com.dw_separa_mercadoria.servico.ServicoPedido;

@Named
@ViewScoped
public class BeanPedido implements Serializable {
	private static final long serialVersionUID = 1L;

	private Pedido pedido = new Pedido();
	@Inject
	private ServicoPedido servico;
	private List<Pedido> lista = new ArrayList<>();
	private List<Pedido> lista2 = new ArrayList<>();

	private String pedidofiltrado = "";

	@PostConstruct
	public void init() {

	}

	public void salvar() {
		if (lista2.size() > 0) {
			for (Pedido p : lista2) {
				try {
					servico.salvar(p);
				} catch (Exception e) {
					if (e.getCause().toString().contains("ConstraintViolationException")) {
						FacesMessageUtil
								.addMensagemError("Registro já existente! Não foi possível realizar a operação.");
					} else {
						FacesMessageUtil.addMensagemError(e.getCause().toString());
					}
				}
			}
		}
	}

	public void filtrar() {
		if (pedidofiltrado.equals("")) {

		} else {
			lista.clear();
			// BigDecimal p = new BigDecimal(pedidofiltrado);
			lista = servico.consultapedidoseven(pedidofiltrado);
			if (lista.size() > 0) {
				pedido = lista.get(0);
			}
			// lista2.addAll(lista);
		}

	}

	public void addpedido() {

		if (!lista2.contains(pedido)) {
			lista2.add(pedido);
			pedidofiltrado = "";
		}

		lista.clear();
	}

	public void limpalista() {
		lista.clear();
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public List<Pedido> getLista() {
		return lista;
	}

	public void setLista(List<Pedido> lista) {
		this.lista = lista;
	}

	public String getPedidofiltrado() {
		return pedidofiltrado;
	}

	public void setPedidofiltrado(String pedidofiltrado) {
		this.pedidofiltrado = pedidofiltrado;
	}

	public List<Pedido> getLista2() {
		return lista2;
	}

	public void setLista2(List<Pedido> lista2) {
		this.lista2 = lista2;
	}

	/* pegar usuario conectado */
	public String usuarioconectado() {
		String nome;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			nome = ((UserDetails) principal).getUsername();
		} else {
			nome = principal.toString();
		}
		// System.out.println(nome);
		return nome;
	}

}
