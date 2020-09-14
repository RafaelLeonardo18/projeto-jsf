package br.com.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import br.com.dao.DaoGenerico;
import br.com.entidades.Lancamento;
import br.com.entidades.Pessoa;

@ViewScoped
@ManagedBean (name = "lancamentoBean")
public class LancamentoBean {
	private Lancamento lancamento = new Lancamento();
	private DaoGenerico <Lancamento> daoGenerico = new DaoGenerico <Lancamento>();
	private List <Lancamento> lancamentos = new ArrayList<Lancamento>();
	
/**************************************************************************************
 * Métodos de persistência de dados
 * ***********************************************************************************/
	
	public String salvar() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		Pessoa pessoaUser = (Pessoa) externalContext.getSessionMap().get("usuario");
		lancamento.setUsuario(pessoaUser);
		daoGenerico.salvar(lancamento);
		lancamento = new Lancamento();
		listar();
		return "";
	}
	
	//Lista todos os lançamentos realizados pdelo usuário ativo
	@SuppressWarnings("unchecked")
	@PostConstruct
	private void listar() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		Pessoa pessoaUser = (Pessoa) externalContext.getSessionMap().get("usuario");
		this.lancamentos = (List<Lancamento>) daoGenerico.geEntityManager().createQuery
						   ("from Lancamento where usuario_id = :id")
						   .setParameter("id", pessoaUser.getId())
						   .getResultList();
	}
	
	public String excluir() {
		daoGenerico.excluir(lancamento);
		lancamento = new Lancamento();
		listar();
		return "";
	}
	
/*
 * ************************************************************************************
 */
	
	//Setters e Getters
	public void setLancamento(Lancamento lancamento) {
		this.lancamento = lancamento;
	}
	
	public Lancamento getLancamento() {
		return lancamento;
	}
	
	public void setDaoGenerico(DaoGenerico<Lancamento> daoGenerico) {
		this.daoGenerico = daoGenerico;
	}
	
	public DaoGenerico<Lancamento> getDaoGenerico(){
		return daoGenerico;
	}
	
	public void setLancamentos(List<Lancamento> lancamentos) {
		this.lancamentos = lancamentos;
	}
	
	public List <Lancamento> getLancamentos(){
		return lancamentos;
	}
	
	
//Fim da classe
}
