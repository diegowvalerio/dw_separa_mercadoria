package br.com.dw_separa_mercadoria.fabrica;

import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Qualifier;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;


@ApplicationScoped
public class EntityManagerProducerSeven implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//conexao com sige
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
	@Qualifier
	public @interface Corporativo {
	}
	
	@PersistenceUnit(unitName = "seven")
	private EntityManagerFactory sevenfactory;
	
	@Produces 
	@RequestScoped
	@Corporativo
	public EntityManager createSigeEntityManager(){
		return sevenfactory.createEntityManager();
	}
	//fim


}
