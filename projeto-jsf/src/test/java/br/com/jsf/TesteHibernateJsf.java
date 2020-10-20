package br.com.jsf;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Persistence;

import org.junit.Test;

import com.br.service.CepService;
import com.google.gson.Gson;

import br.com.dao.DaoGenerico;
import br.com.entidades.Lancamento;
import br.com.entidades.Logradouro;
import br.com.entidades.Pessoa;
import br.com.jpa.JPAutil;

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
	
	@Test
	public void testaCepService() {
		try {
			URL url = new URL("https://viacep.com.br/ws/84950000/json/");
			URLConnection connection = url.openConnection();
			InputStream inputStream = connection.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
			String cep = "";
			StringBuilder jsonCep = new StringBuilder();
			while ((cep = bufferedReader.readLine()) != null) {
				jsonCep.append(cep);
			}
			Logradouro logradouro = new Gson().fromJson(jsonCep.toString(), Logradouro.class);
			System.out.println(logradouro.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testaPessoaCep() {
		DaoGenerico daoGenerico = new DaoGenerico();
		Logradouro logradouro = CepService.pesquisaCep("84950000");
		logradouro.setTipoLogradouro("Rua");
		logradouro.setLogradouro("Vereador Alcides da Silva Reis");
		logradouro.setNumeroLogradouro(13);
		logradouro.setBairro("Santa Maria");
		logradouro.setNumeroLogradouro(1230);
		daoGenerico.salvar(logradouro);
		Pessoa pessoa = new Pessoa();
		pessoa.setNome("Claudia");
		pessoa.setSobrenome("Medeiros");
		pessoa.setIdade(23);
		pessoa.setSexo("Feminino");
		pessoa.setLogradouro(logradouro);
		daoGenerico.salvar(pessoa);
	}
	
	@Test
	public void pesquisaLogradouro() {
		DaoGenerico <Logradouro> daoGenerico = new DaoGenerico <Logradouro>();
		Logradouro logradouro = CepService.pesquisaCep("84950000");
		try {
			logradouro.setTipoLogradouro("Rua");
			logradouro.setLogradouro("Vereador Alcides da Silva Reis");
			logradouro.setNumeroLogradouro(25);
			logradouro.setBairro("Santa Maria");
			Logradouro aux = (Logradouro) daoGenerico.geEntityManager().createQuery
					("from Logradouro where logradouro = :logradouro and tipologradouro = :tipologradouro "
						 		+ "and cep = :cep and numerologradouro = :numerologradouro and complemento = :complemento "
						 		+ "and bairro = :bairro and localidade = :localidade and uf = :uf")
						 .setParameter("logradouro", logradouro.getLogradouro())
						 .setParameter("tipologradouro", logradouro.getTipoLogradouro())
						 .setParameter("cep", logradouro.getCep())
						 .setParameter("numerologradouro", logradouro.getNumeroLogradouro())
						 .setParameter("complemento", logradouro.getComplemento())
						 .setParameter("bairro", logradouro.getBairro())
						 .setParameter("localidade", logradouro.getLocalidade())
						 .setParameter("uf", logradouro.getUf())
						 .getSingleResult();
			if (aux != null) {
				System.out.println("Endereço já cadastrado");
				System.out.println(aux.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			daoGenerico.salvar(logradouro);
			System.out.println("Endereço salvo com sucesso");
		}
	}

}
