package br.com.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;

import br.com.entidades.Pessoa;
import br.com.jpa.JPAutil;

@WebFilter (urlPatterns = {"/index.xhtml", "/Lancamento.xhtml", "/Teste01.xhtml"})
public class FilterAutenticacao implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
						 throws IOException, ServletException {
		HttpServletRequest requisicao = (HttpServletRequest) request;
		HttpSession sessao = requisicao.getSession();
		//Página de login do sistema
		String autenticacao = requisicao.getContextPath();
		//Página da requisição
		String url = requisicao.getRequestURI();
		Pessoa pessoa = (Pessoa) sessao.getAttribute("usuario");
		if (pessoa == null && !url.equals(autenticacao)) {
			HttpServletResponse resposta = (HttpServletResponse) response;
			resposta.sendRedirect(autenticacao);
			return;
		} else {
			chain.doFilter(request, response);
		}		
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		JPAutil.geEntityManager();
	}

}
