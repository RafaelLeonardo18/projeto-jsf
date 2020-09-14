package br.com.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import br.com.dao.DaoGenerico;
import br.com.entidades.Pessoa;

@ViewScoped
@ManagedBean (name = "pessoaBean")

public class PessoaBean {
	private Pessoa pessoa = new Pessoa();
	private DaoGenerico <Pessoa> daoGenerico = new DaoGenerico <Pessoa> ();
	private List <Pessoa> pessoas = new ArrayList <Pessoa>();
	private String mensagem;
	
/*
* **********************************************************************************
* Métodos de persistência de dados
* **********************************************************************************
*/	
	//Método de salvar a pessoa
	public String salvar() {
		daoGenerico.salvar(pessoa);
		pessoa = new Pessoa();
		listar();
		return "";
	}
	
	//Método de alteração de um usuário
	public String alterar() {
		daoGenerico.atualizar(pessoa);
		pessoa = new Pessoa();
		listar();
		return "";
	}
	
	//Método de excluir a pessoa
	public String excluir() {
		daoGenerico.excluir(pessoa);
		pessoa = new Pessoa();
		listar();
		return "";
	}
	
	//Método de listar as pessoas cadastradas
	@PostConstruct
	private void listar() {
		this.pessoas = daoGenerico.listar(Pessoa.class);
	}

/*
 **************************************************************************************
 * Métodos de validação
 **************************************************************************************
 */
	
	//Método de validação do usuário do sistema
	public String validarUsuario () {
		String url = null;
		try {
			Pessoa pessoaUser = (Pessoa) daoGenerico.geEntityManager().createNamedQuery("login")
					.setParameter("login", this.pessoa.getLogin())
					.setParameter("senha", this.pessoa.getSenha())
					.getSingleResult();
			if (pessoaUser != null) {
				FacesContext context = FacesContext.getCurrentInstance();
				ExternalContext externalContext = context.getExternalContext();
				externalContext.getSessionMap().put("usuario", pessoaUser);
				this.setMensagem("");
				url = "index.jsf";
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.setMensagem("Usuário ou senha incorretos");
			url = "Login.jsf";
		}
		return url;
	}
	
	public boolean acesso(String acesso) {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		Pessoa pessoaUser = (Pessoa) externalContext.getSessionMap().get("usuario");
		return pessoaUser.getPerfil().equals(acesso);
	}
	
/*
 * *********************************************************************************
 */

	//Setters e Getters
	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public DaoGenerico<Pessoa> getDaoGenerico() {
		return daoGenerico;
	}

	public void setDaoGenerico(DaoGenerico<Pessoa> daoGenerico) {
		this.daoGenerico = daoGenerico;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}
	
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
	public String getMensagem() {
		return mensagem;
	}

/*
 * *********************************************************************************
 */
	
//Fim da classe	
}