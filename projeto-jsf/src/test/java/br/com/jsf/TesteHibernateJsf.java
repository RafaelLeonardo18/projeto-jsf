package br.com.jsf;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Persistence;

import org.junit.Test;

import br.com.dao.DaoGenerico;
import br.com.entidades.Lancamento;
import br.com.entidades.Pessoa;

public class TesteHibernateJsf {

	@Test
	public void test() {
		Persistence.createEntityManagerFactory("projeto-jsf");
	}
	
	@Test
	public void listar() {
		DaoGenerico <Pessoa> daoGenerico = new DaoGenerico<Pessoa>();
		List <Pessoa> lista = daoGenerico.listar(Pessoa.class);
		for (Pessoa pessoa : lista) {
			System.out.println(pessoa.toString());
		}
	}
	
	@Test
	public void salvarPessoa() {
		DaoGenerico <Pessoa> daoGenerico = new DaoGenerico <Pessoa>();
		Pessoa pessoa = new Pessoa();
		pessoa.setIdade(1);
		pessoa.setNome("Gabriel");
		pessoa.setSobrenome("Medeiros");
		daoGenerico.salvar(pessoa);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void listarLancamento() {
		List <Lancamento> lancamentos = new ArrayList<Lancamento>();
		DaoGenerico <Lancamento> daoGenerico = new DaoGenerico <Lancamento>();
		lancamentos = daoGenerico.geEntityManager().createQuery
							("from Lancamento where usuario_id = :id")
							.setParameter("id", 29)
							.getResultList();
		System.out.println(lancamentos.toString());
	}

}
