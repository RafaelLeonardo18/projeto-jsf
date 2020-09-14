package br.com.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.jpa.JPAutil;

public class DaoGenerico <E> {
	private EntityManager entityManager = JPAutil.geEntityManager();
	
	//Método de salvar os dados da entidade
	public void salvar (E entity) {
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		try {
			entityManager.persist(entity);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		}
	}
	
	//Método de consulta
	public E pesquisar (Class <E> entity) {
		Object id = JPAutil.getPrimaryKey(entity);
		E e = entityManager.find(entity, id);
		return e;
	}
	
	//Outro método de consulta passando o id no parâmetro
	public E pesquisar(int id, Class <E> entity) {
		E e = entityManager.find(entity, id);
		return e;
	}
	
	//Atualiza os dados da entidade
	public E atualizar (E entity) {
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		E entidadeNova = entityManager.merge(entity);
		transaction.commit();
		return entidadeNova;
	}
	
	//Exclui um registro da entidade no banco de dados
	public void excluir (E entity) {
		Object id = JPAutil.getPrimaryKey(entity);
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.createNativeQuery("delete from " + entity.getClass().getSimpleName().toLowerCase() 
				                        + " where id = " + id).executeUpdate();
		transaction.commit();
	}
	
	//Lista todos os registros da entidade no banco de dados
	@SuppressWarnings ("unchecked")
	public List<E> listar (Class <E> entity){
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		List <E> lista = entityManager.createQuery("from " + entity.getName()).getResultList();
		transaction.commit();
		return lista;
	}
	
	//Retorna o objeto de manipulação do Hibernate
	public EntityManager geEntityManager() {
		return entityManager;
	}
	
//Fim da classe
}
