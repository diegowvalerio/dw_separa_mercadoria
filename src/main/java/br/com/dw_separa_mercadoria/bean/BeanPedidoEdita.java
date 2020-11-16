package br.com.dw_separa_mercadoria.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.event.ReorderEvent;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.dw_separa_mercadoria.entidade.Lote;
import br.com.dw_separa_mercadoria.entidade.Pedido;
import br.com.dw_separa_mercadoria.msn.FacesMessageUtil;
import br.com.dw_separa_mercadoria.servico.ServicoLote;
import br.com.dw_separa_mercadoria.servico.ServicoPedido;

@Named
@ViewScoped
public class BeanPedidoEdita implements Serializable {
	private static final long serialVersionUID = 1L;

	private Lote lote = new Lote();
	private Pedido pedido = new Pedido();
	@Inject
	private ServicoPedido servico;
	@Inject
	private ServicoLote servicolote;

	private List<Lote> listalote = new ArrayList<>();
	private List<Pedido> lista = new ArrayList<>();
	private List<Pedido> lista2 = new ArrayList<>();

	private String pedidofiltrado = "";

	@PostConstruct
	public void init() {

		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		HttpSession session = (HttpSession) request.getSession();
		this.lote = (Lote) session.getAttribute("loteAux");

		this.lista2 = this.lote.getPedidos();

		session.removeAttribute("loteAux");

	}

	public String salvar() {

		try {
			servicolote.salvar(lote);
		} catch (Exception e) {
			if (e.getCause().toString().contains("ConstraintViolationException")) {
				FacesMessageUtil.addMensagemError("Registro já existente! Não foi possível realizar a operação.");
			} else {
				FacesMessageUtil.addMensagemError(e.getCause().toString());
			}
		}

		return "lista_lote";
	}

	public void filtrar() {
		if (pedidofiltrado.equals("")) {

		} else {
			lista.clear();
			// BigDecimal p = new BigDecimal(pedidofiltrado);
			lista = servico.consultapedidoseven(pedidofiltrado);
			if (lista.size() > 0) {
				pedido = lista.get(0);

				if (servico.consultapedido_existe(pedido.getPedidoid()) == 1) {
					FacesMessageUtil.addMensagemWarn("Pedido ja está em outro lote");
					lista.clear();
				}
			}
			// lista2.addAll(lista);
		}

	}

	public void addpedido() {

		if (!lista2.contains(pedido)) {
			pedido.setLote(lote);
			lista2.add(pedido);

			pedidofiltrado = "";
		}

		atualizaordem();

		lista.clear();
	}

	public void limpalista() {
		lista.clear();
	}

	public void removepedido() {
		int index = lista2.indexOf(pedido);
		if (index > -1) {
			this.lista2.remove(index);
		}
		atualizaordem();
	}

	public void onRowReorder(ReorderEvent event) {
		atualizaordem();
	}

	public void atualizaordem() {
		for (Pedido p : lista2) {
			int index = lista2.indexOf(p);
			p.setOrdenacao(index);
		}
	}

	/* editar cadastro */
	public String encaminha() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(true);
		session.setAttribute("loteAux", this.lote);

		return "edita-lote";
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

	public Lote getLote() {
		return lote;
	}

	public void setLote(Lote lote) {
		this.lote = lote;
	}

	public List<Lote> getListalote() {
		return listalote;
	}

	public void setListalote(List<Lote> listalote) {
		this.listalote = listalote;
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
