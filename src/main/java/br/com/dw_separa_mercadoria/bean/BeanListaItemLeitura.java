package br.com.dw_separa_mercadoria.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.application.ViewHandler;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIInput;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.omnifaces.util.Ajax;
import org.primefaces.context.PrimeFacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.ReorderEvent;
import org.primefaces.util.AjaxRequestBuilder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.w3c.dom.html.HTMLInputElement;

import br.com.dw_separa_mercadoria.entidade.Lote;
import br.com.dw_separa_mercadoria.entidade.Pedido;
import br.com.dw_separa_mercadoria.entidade.PedidoItem;
import br.com.dw_separa_mercadoria.entidade.PedidoItem_leitura;
import br.com.dw_separa_mercadoria.msn.ExecutaSom;
import br.com.dw_separa_mercadoria.msn.FacesMessageUtil;
import br.com.dw_separa_mercadoria.servico.ServicoLote;
import br.com.dw_separa_mercadoria.servico.ServicoPedido;
import br.com.dw_separa_mercadoria.servico.ServicoPedidoItem;

@Named
@ViewScoped
public class BeanListaItemLeitura implements Serializable {
	private static final long serialVersionUID = 1L;

	private Pedido pedido = new Pedido();
	private PedidoItem item = new PedidoItem();
	private PedidoItem_leitura itemleitura = new PedidoItem_leitura();
	@Inject
	private ServicoPedido servico;
	@Inject
	private ServicoPedidoItem servicoitem;
	private List<PedidoItem> lista = new ArrayList<>();
	private PedidoItem_leitura pedidoItem_leitura ;
	private List<PedidoItem_leitura> listaleitura = new ArrayList<>();



	@PostConstruct
	public void init() {

		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		HttpSession session = (HttpSession) request.getSession();
		this.pedido = (Pedido) session.getAttribute("pedidoAux");
		this.lista = this.pedido.getItems();
		Collections.sort(lista, Comparator.comparing(PedidoItem::getNomeproduto));
		session.removeAttribute("pedidoAux");
		
		//this.item = (PedidoItem) session.getAttribute("itemAux");
		int i = this.lista.indexOf((PedidoItem) session.getAttribute("itemAux"));
		//this.item = new PedidoItem();
		this.item = this.lista.get(i);
		this.listaleitura = new ArrayList<>();
		this.listaleitura = this.item.getLeituras();
		session.removeAttribute("itemAux");
		
	}

	public String encaminha1() {
		
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(true);
		session.setAttribute("pedidoAux", this.pedido);

		return "lista_pedidoitem";

	}
	
	public void excluir() {
		//listaleitura = item.getLeituras();
		int index = listaleitura.indexOf(itemleitura);
		if (index > -1) {
			listaleitura.remove(index);
		}
		item.setQuantidadeseparada(item.getQuantidadeseparada()-itemleitura.getQtde_leitura());
		item.setLeituras(listaleitura);
	}

	
	public PedidoItem_leitura getItemleitura() {
		return itemleitura;
	}

	public void setItemleitura(PedidoItem_leitura itemleitura) {
		this.itemleitura = itemleitura;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public List<PedidoItem> getLista() {
		return lista;
	}

	public void setLista(List<PedidoItem> lista) {
		this.lista = lista;
	}

	public PedidoItem getItem() {
		return item;
	}

	public void setItem(PedidoItem item) {
		this.item = item;
	}

	public List<PedidoItem_leitura> getListaleitura() {
		return listaleitura;
	}

	public void setListaleitura(List<PedidoItem_leitura> listaleitura) {
		this.listaleitura = listaleitura;
	}

	public PedidoItem_leitura getPedidoItem_leitura() {
		return pedidoItem_leitura;
	}

	public void setPedidoItem_leitura(PedidoItem_leitura pedidoItem_leitura) {
		this.pedidoItem_leitura = pedidoItem_leitura;
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
