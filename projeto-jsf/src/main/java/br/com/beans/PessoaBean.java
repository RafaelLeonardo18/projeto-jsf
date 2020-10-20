package br.com.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import com.br.service.AutenticacaoService;
import com.br.service.CepService;
import br.com.dao.DaoGenerico;
import br.com.entidades.Logradouro;
import br.com.entidades.Pessoa;

@ViewScoped
@ManagedBean (name = "pessoaBean")

public class PessoaBean {
	private Pessoa pessoa = new Pessoa();
	private Logradouro logradouro = new Logradouro();
	private DaoGenerico <Pessoa> daoPessoa = new DaoGenerico <Pessoa> ();
	private DaoGenerico <Logradouro> daoLogradouro = new DaoGenerico <Logradouro> ();
	private List <Pessoa> pessoas = new ArrayList <Pessoa>();
/*
* **********************************************************************************
* Métodos de persistência de dados
* **********************************************************************************
*/	
	//Método de salvar a pessoa
	public String salvar() {
		daoLogradouro.salvar(logradouro);
		pessoa.setLogradouro(logradouro);
		daoPessoa.salvar(pessoa);
		pessoa = new Pessoa();
		logradouro = new Logradouro();
		listar();
		exibeMensagem("Usuário cadastrado com sucesso!");
		return "";
	}
	
	//Método de alteração de um usuário
	public String alterar() {
		daoLogradouro.atualizar(logradouro);
		pessoa.setLogradouro(logradouro);
		daoPessoa.atualizar(pessoa);
		pessoa = new Pessoa();
		logradouro = new Logradouro();
		listar();
		return "";
	}
	
	//Método de excluir a pessoa
	public String excluir() {
		daoPessoa.excluir(pessoa);
		pessoa = new Pessoa();
		listar();
		return "";
	}
	
	//Método de listar as pessoas cadastradas
	@PostConstruct
	private void listar() {
		this.pessoas = daoPessoa.listar(Pessoa.class);
	}

/*
 **************************************************************************************
 * Métodos de validação
 **************************************************************************************
 */
	
	//Método de validação do usuário do sistema
	public String validarUsuario () {
		return AutenticacaoService.autenticar(pessoa);
	}
	
	//Faz o logout do usuário
	public String logout() {
		return AutenticacaoService.logout();
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
	
	//Método usado para limpar os dados do formulário
	public String novaPessoa() {
		pessoa = new Pessoa();
		logradouro = new Logradouro();
		return "";
	}
	
	//Método de pesquisa de cep via web service
	public void pesquisaCep(AjaxBehaviorEvent event) {
		logradouro = CepService.pesquisaCep(logradouro.getCep());
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

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

	public Logradouro getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(Logradouro logradouro) {
		this.logradouro = logradouro;
	}

	public DaoGenerico<Pessoa> getDaoPessoa() {
		return daoPessoa;
	}

	public void setDaoPessoa(DaoGenerico<Pessoa> daoPessoa) {
		this.daoPessoa = daoPessoa;
	}

	public DaoGenerico<Logradouro> getDaoLogradouro() {
		return daoLogradouro;
	}

	public void setDaoLogradouro(DaoGenerico<Logradouro> daoLogradouro) {
		this.daoLogradouro = daoLogradouro;
	}

/*
 * *********************************************************************************
 */
	
//Fim da classe	
}