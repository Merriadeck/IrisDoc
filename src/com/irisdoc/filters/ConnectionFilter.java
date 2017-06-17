package com.irisdoc.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Filtre imposant la connexion à l'entrée sur l'application
 */
public class ConnectionFilter implements Filter {

	private static final String ATB_USER = "current_user";
	private static final String PATH_RESOURCES = "/static/";
	private static final String PATH_CONNECT = "/connexion";
	
	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        
        // Vérification de l'encodage
        if(request.getCharacterEncoding() == null)
        	request.setCharacterEncoding("UTF-8");

        // Exceptions sur le filtre
        String path = request.getRequestURI().substring(request.getContextPath().length());
        if(path.startsWith(PATH_RESOURCES) || path.startsWith(PATH_CONNECT)) {
            chain.doFilter(request, response);
            return;
        }

        // Récupération de la session depuis la requête
        HttpSession session = request.getSession();

        // Traitement et redirection éventuelle
        if(session.getAttribute(ATB_USER) == null)
        	request.getRequestDispatcher(PATH_CONNECT).forward(request, response);
        else
        	chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		
	}

}
