package br.com.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
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
		exibeMensagem("Usuário cadastrado com sucesso!");
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
				url = "index.jsf";
			}
		} catch (Exception e) {
			e.printStackTrace();
			exibeMensagem("Usuário ou Senha incorretos");
			url = "Login.jsf";
		}
		return url;
	}
	
	//Libera a visualização de certos componentes de acordo com o perfil do usuário
	public boolean acesso(String acesso) {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		Pessoa pessoaUser = (Pessoa) externalContext.getSessionMap().get("usuario");
		return pessoaUser.getPerfil().equals(acesso);
	}
	
	//Exibe mensgaens na tela para o usuário
	public void exibeMensagem(String msg) {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage(msg);
		context.addMessage(null, message);
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

/*
 * *********************************************************************************
 */
	
//Fim da classe	
}