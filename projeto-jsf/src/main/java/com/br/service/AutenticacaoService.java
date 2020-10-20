package com.br.service;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import br.com.dao.DaoGenerico;
import br.com.entidades.Pessoa;

/**************************************************************************************************
 * Descrição: classe de serviço que executa a autenticação e saida do usuário no sistema
 * Autor: Rafael Leonardo de Lima
 * Data: 12/10/2020
 * ***********************************************************************************************/

public class AutenticacaoService {
	public static String autenticar(Pessoa pessoa) {
		DaoGenerico <Pessoa> daoGenerico = new DaoGenerico<Pessoa>();
		String url = null;
		try {
			Pessoa pessoaUser = (Pessoa) daoGenerico.geEntityManager().createQuery
					("from Pessoa where login = :login and senha = :senha")
					.setParameter("login", pessoa.getLogin())
					.setParameter("senha", pessoa.getSenha())
					.getSingleResult();
			if (pessoaUser != null) {
				FacesContext context = FacesContext.getCurrentInstance();
				ExternalContext externalContext = context.getExternalContext();
				externalContext.getSessionMap().put("usuario", pessoaUser);
				url = "index.jsf";
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage message = new FacesMessage("Usuário ou senha incorretos!");
			context.addMessage(null, message);
			url = "Login.jsf";
		}
		return url;
	}
	
	public static String logout() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		externalContext.getSessionMap().clear();
		HttpServletRequest servletRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		servletRequest.getSession().invalidate();
		return "Login.jsf";
	}
}
