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
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.application.ViewHandler;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
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

import org.hibernate.Hibernate;
import org.omnifaces.util.Ajax;
import org.primefaces.context.PrimeFacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.ReorderEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.expression.SearchExpressionFacade;
import org.primefaces.util.AjaxRequestBuilder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.w3c.dom.html.HTMLInputElement;

import com.lowagie.text.Document;

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
public class BeanListaItems implements Serializable {
	private static final long serialVersionUID = 1L;

	private Pedido pedido = new Pedido();
	private PedidoItem item = new PedidoItem();
	private PedidoItem_leitura itemleitura = new PedidoItem_leitura();
	@Inject
	private ServicoPedido servico;
	@Inject
	private ServicoPedidoItem servicoitem;
	private List<PedidoItem> lista = new ArrayList<>();
	private PedidoItem_leitura pedidoItem_leitura = new PedidoItem_leitura();
	private List<PedidoItem_leitura> listaleitura = new ArrayList<>();
	private String leitura = null;
	private UIInput uileitura = null;
	private ExecutaSom executaSom = new ExecutaSom();

	// tempo
	private String tempo = "";
	private Timer timer;
	private UIInput uitempo = null;
	private boolean pausado = false;

	@PostConstruct
	public void init() {

		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		HttpSession session = (HttpSession) request.getSession();
		if (session.getAttribute("pedidoAux") != null) {

			this.pedido = (Pedido) session.getAttribute("pedidoAux");
			this.lista = this.pedido.getItems();
			Collections.sort(lista, Comparator.comparing(PedidoItem::getNomeproduto));
			session.removeAttribute("pedidoAux");

			// alterar status
			this.pedido.setStatus("SEPARANDO");
			try {
				servico.salvar(this.pedido);
			} catch (Exception e) {
				e.printStackTrace();
			}

			timer = new Timer();
			TimerTask tarefa = new TimerTask() {
				public void run() {
					try {
						if (pausado == false) {
							atualizatempo();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			timer.scheduleAtFixedRate(tarefa, 0, 1000);
		} else {
			FacesMessageUtil.addMensagemError("Recarregue a pagina!");
		}
		TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
	}

	public String encaminha1() {
		int q = 0;
		for (PedidoItem i : lista) {
			if (i.getQuantidadeseparada() != null) {
				q += i.getQuantidadeseparada();
			}
		}
		if (q > 0) {
			this.pedido.setStatus("PAUSADO");
		} else {
			this.pedido.setStatus("ABERTO");
		}
		try {
			servico.salvar(this.pedido);
		} catch (Exception e) {
			e.printStackTrace();
		}

		timer.cancel();

		return "lista_pedido";

	}

	public String encaminha() {
		if (pausado == true) {
			FacesMessageUtil.addMensagemError("Cronometro está PAUSADO, ative para continuar !");
			return "";
		} else {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(true);
			session.setAttribute("pedidoAux", this.pedido);
			session.setAttribute("itemAux", this.item);
			return "lista_itemleitura";
		}

	}

	public void onRowSelect(SelectEvent event) {
		item = (PedidoItem) event.getObject();
		listaleitura = item.getLeituras();
	}

	public void excluir() {
		// listaleitura = item.getLeituras();
		int index = listaleitura.indexOf(itemleitura);
		if (index > -1) {
			listaleitura.remove(index);
		}
		int i = 0;
		for (PedidoItem_leitura il : listaleitura) {
			i += il.getQtde_leitura();
		}
		item.setQuantidadeseparada(i);
		// item.setLeituras(listaleitura);
		focus(":form:paineld3");
	}

	public void leitor(ActionEvent event) {
		GregorianCalendar cal = new GregorianCalendar(TimeZone.getTimeZone("GMT-3") , new Locale("pt_BR"));
		Date data = cal.getTime();
		data.setHours(data.getHours()-3);
		cal.setTime(data);
		// verifica se tempo esta pausado
		if (pausado == false) {
			String ean = "";
			String qtde = "";
			int sobra, c = 0;

			if (leitura.length() >= 13) {
				ean = leitura.substring(0, 13);
			}

			if (leitura.length() == 13) {
				for (PedidoItem i : lista) {
					if (i.getEan().equals(leitura)) {
						c = 1;
						if (i.getQuantidadeseparada() != null) {
							sobra = i.getQuantidadeproduto().intValue() - i.getQuantidadeseparada();
						} else {
							sobra = i.getQuantidadeproduto().intValue();
						}
						if (sobra >= 1) {
							// efetua entrada de 1 unidade
							try {
								listaleitura = i.getLeituras();
								pedidoItem_leitura = new PedidoItem_leitura();
								pedidoItem_leitura.setPedidoitem(i);
								pedidoItem_leitura.setQtde_leitura(1);
								pedidoItem_leitura.setUsuario(usuarioconectado());
								pedidoItem_leitura.setDatahora_separacao(new Timestamp(cal.getTimeInMillis()));
								listaleitura.add(pedidoItem_leitura);
								// i.setLeituras(listaleitura);
								if (i.getQuantidadeseparada() != null) {
									i.setQuantidadeseparada(i.getQuantidadeseparada() + 1);
								} else {
									i.setQuantidadeseparada(1);
								}
								if (i.getQuantidadeseparada() == i.getQuantidadeproduto().intValue()) {
									i.setSeparado("SIM");

								} else {
									i.setSeparado("NAO");
								}
								// servicoitem.salvar(i);
							} catch (Exception e) {
								e.printStackTrace();
							}

						} else {
							FacesMessageUtil.addMensagemWarn("Quantidade inválida ou item ja Finalizado !");
						}
					}
				}
			} else if (leitura.length() == 18) {
				qtde = leitura.substring(13, 18);
				for (PedidoItem i : lista) {
					if (i.getEan().equals(ean)) {
						c = 1;
						if (i.getQuantidadeseparada() != null) {
							sobra = i.getQuantidadeproduto().intValue() - i.getQuantidadeseparada();
						} else {
							sobra = i.getQuantidadeproduto().intValue();
						}
						if (sobra >= Integer.valueOf(qtde)) {
							// efetua entrada de qtde
							try {
								listaleitura = i.getLeituras();
								pedidoItem_leitura = new PedidoItem_leitura();
								pedidoItem_leitura.setPedidoitem(i);
								pedidoItem_leitura.setQtde_leitura(Integer.valueOf(qtde));
								pedidoItem_leitura.setUsuario(usuarioconectado());
								pedidoItem_leitura.setDatahora_separacao(new Timestamp(cal.getTimeInMillis()));
								listaleitura.add(pedidoItem_leitura);
								i.setLeituras(listaleitura);
								if (i.getQuantidadeseparada() != null) {
									i.setQuantidadeseparada(i.getQuantidadeseparada() + Integer.valueOf(qtde));
								} else {
									i.setQuantidadeseparada(Integer.valueOf(qtde));
								}
								if (i.getQuantidadeseparada() == i.getQuantidadeproduto().intValue()) {
									i.setSeparado("SIM");
								} else {
									i.setSeparado("NAO");
								}
								// servicoitem.salvar(i);
							} catch (Exception e) {
								e.printStackTrace();
							}
						} else {
							FacesMessageUtil.addMensagemWarn("Quantidade inválida ou item ja Finalizado !");
						}
					}
				}

			} else {
				FacesMessageUtil.addMensagemWarn("Leitura não compativél");
				c = 1;
			}

			if (c == 0) {
				FacesMessageUtil.addMensagemInfo("Produto não localizado !");
			}
		} else {
			FacesMessageUtil.addMensagemError("Cronometro está PAUSADO, ative para continuar !");
		}
		this.getUileitura().setSubmittedValue("");
		this.setLeitura("");

	}

	public void atualizatempo() {
		long startTime, stopTime = 0;
		if (Objects.nonNull(pedido.getCronometro())) {
			startTime = System.currentTimeMillis() - pedido.getCronometro();
		} else {
			startTime = System.currentTimeMillis();
		}

		stopTime = System.currentTimeMillis() + 1000;
		long elapsedTime = stopTime - startTime;

		pedido.setCronometro(elapsedTime);
		tempo = calcula_duracao(elapsedTime);

	}

	public String calcula_duracao(long ms) {
		long totalSegundos = ms / 1000;
		long totalHoras = totalSegundos / 3600;
		totalSegundos -= (totalHoras * 3600);
		long totalMinutos = totalSegundos / 60;
		totalSegundos -= (totalMinutos * 60);

		return String.format("%02d:%02d:%02d", totalHoras, totalMinutos, totalSegundos);
	}

	public void cronometrar() {
		if (pausado == true) {
			pausado = false;
		} else if (pausado == false) {
			pausado = true;
		}
	}

	public String playpause() {
		focus(":form:paineld3");
		if (pausado == true) {
			return "ui-icon-play";
		} else if (pausado == false) {
			return "ui-icon-pause";
		} else {
			return "";
		}

	}

	public String cor_playpause() {
		if (pausado == true) {
			return "btn-danger";
		} else if (pausado == false) {
			return "btn-primary";
		} else {
			return "";
		}
	}

	public String converte(long ms) {
		int s, m, h = 0;
		String tempoPronto = "";

		s = (int) ms / 1000;
		ms -= s * 1000;
		m = s / 60;
		s -= m * 60;
		h = m / 60;
		m -= h * 60;

		tempoPronto += (h < 10) ? "0" + h : h;
		tempoPronto += (m < 10) ? ":0" + m : ":" + m;
		tempoPronto += (s < 10) ? ":0" + s : ":" + s;
		tempoPronto += (ms < 10) ? ".00" + ms : (ms < 100) ? ".0" + ms : "." + ms;

		return tempoPronto;
	}

	public void focus(String expression) {
		FacesContext context = FacesContext.getCurrentInstance();
		UIComponent forComponent = SearchExpressionFacade.resolveComponent(context,
				UIComponent.getCurrentComponent(context), expression);
		String clientId = forComponent.getClientId();
		RequestContext.getCurrentInstance().execute("PrimeFaces.focus('" + clientId + "');");
	}

	public PedidoItem_leitura getItemleitura() {
		return itemleitura;
	}

	public void setItemleitura(PedidoItem_leitura itemleitura) {
		this.itemleitura = itemleitura;
	}

	public boolean isPausado() {
		return pausado;
	}

	public void setPausado(boolean pausado) {
		this.pausado = pausado;
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

	public String getLeitura() {
		return leitura;
	}

	public void setLeitura(String leitura) {
		this.leitura = leitura;
	}

	public UIInput getUileitura() {
		return uileitura;
	}

	public void setUileitura(UIInput uileitura) {
		this.uileitura = uileitura;
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

	public String getTempo() {
		return tempo;
	}

	public void setTempo(String tempo) {
		this.tempo = tempo;
	}

	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	public UIInput getUitempo() {
		return uitempo;
	}

	public void setUitempo(UIInput uitempo) {
		this.uitempo = uitempo;
	}

	public ExecutaSom getExecutaSom() {
		return executaSom;
	}

	public void setExecutaSom(ExecutaSom executaSom) {
		this.executaSom = executaSom;
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
