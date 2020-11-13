package br.com.dw_separa_mercadoria.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.dw_separa_mercadoria.msn.FacesMessageUtil;


@Named
@ViewScoped
public class BeanIndex implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Date data_grafico = new Date();
	private Date data_grafico2 = new Date();
	
	
	@PostConstruct
	public void init() {
				
	}
	
	

	
}
