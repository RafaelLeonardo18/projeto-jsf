package br.com.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAutil {
	private static EntityManagerFactory factory = null;
	
	//Invoca a instância na chamada da classe
	static {
		init();
	}
	
	//Faz a criação da instância do Entity Manager
	public static void init(){
		if (factory == null) {
			factory = Persistence.createEntityManagerFactory("projeto-jsf");
		}
	}
	
	//Retorna o Entity Manager para gerenciar os métodos de persistência
	public static EntityManager geEntityManager() {
		return factory.createEntityManager();
	}
	
	//Retorna a chave primária da entidade
	public static Object getPrimaryKey (Object entity) {
		return factory.getPersistenceUnitUtil().getIdentifier(entity);
	}
}
