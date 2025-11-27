package br.com.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import br.com.entidades.Pessoa;
import br.com.jpautil.JPAUtil;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@WebFilter(urlPatterns = { "/*" })
public class FilterAutenticacao implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		
			HttpServletRequest req = (HttpServletRequest) request;
			HttpSession session = req.getSession();

			Pessoa usuarioLogado = (Pessoa) session.getAttribute("login");
			String urlParaAutenticar = req.getServletPath();/* Url que esta sendo acessada */

			/* Validar se esta logado senão redireciona para a tela de login */
			if (!urlParaAutenticar.equalsIgnoreCase("index.jsf") && usuarioLogado == null) {/* Nao esta logado */

				RequestDispatcher redireciona = request.getRequestDispatcher("/index.jsf");
				request.setAttribute("msg", "Por favor realize o login!");
				redireciona.forward(request, response);


				return; /* Para a execução e redireciona para o login */

			} else {
				chain.doFilter(request, response);
			}
			
		}
	

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		Filter.super.init(filterConfig);

		JPAUtil.getEntityManager();
	}

}
