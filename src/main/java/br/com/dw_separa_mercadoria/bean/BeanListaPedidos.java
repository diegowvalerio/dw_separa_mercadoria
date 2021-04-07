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
import javax.servlet.http.HttpSession;

import org.primefaces.event.ReorderEvent;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.dw_separa_mercadoria.entidade.Lote;
import br.com.dw_separa_mercadoria.entidade.Pedido;
import br.com.dw_separa_mercadoria.entidade.PedidoItem;
import br.com.dw_separa_mercadoria.msn.FacesMessageUtil;
import br.com.dw_separa_mercadoria.servico.ServicoLote;
import br.com.dw_separa_mercadoria.servico.ServicoPedido;

@Named
@ViewScoped
public class BeanListaPedidos implements Serializable {
	private static final long serialVersionUID = 1L;

	//<meta http-equiv='refresh' content='10;url=lista_pedido.xhtml'/>
	
	
	private Pedido pedido = new Pedido();
	@Inject
	private ServicoPedido servico;
	private List<Pedido> lista = new ArrayList<>();
	private int posicao = -1;

	@PostConstruct
	public void init() {
		this.lista = servico.consultarpedidos();
		
		for(Pedido p : lista){
            if(p.getStatus().equals("ABERTO") && posicao == -1){
                posicao = lista.indexOf(p);
                //Log.i("posição",""+posicaocorreta);
            }
        }
		//System.out.println("posicao: "+posicao);
	}
	
	public String encaminha() {
		if(lista.indexOf(this.pedido) == posicao || this.pedido.getStatus().equals("PAUSADO"))  {
			
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(true);
			session.setAttribute("pedidoAux", this.pedido);

			return "lista_pedidoitem";
		}else {
			FacesMessageUtil.addMensagemInfo("Seleção Inválida, siga a sequência ou continue os pedidos pausados");
			return "lista_pedido";
		}
		
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

	public int getPosicao() {
		return posicao;
	}

	public void setPosicao(int posicao) {
		this.posicao = posicao;
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
