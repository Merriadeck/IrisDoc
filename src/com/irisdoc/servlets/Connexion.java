package com.irisdoc.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.irisdoc.beans.Admin;
import com.irisdoc.beans.Classe;
import com.irisdoc.beans.Eleve;
import com.irisdoc.beans.Professeur;
import com.irisdoc.dao.AdminDAO;
import com.irisdoc.dao.ClasseDAO;
import com.irisdoc.dao.DAOFactory;
import com.irisdoc.dao.EleveDAO;
import com.irisdoc.dao.ProfDAO;
import com.irisdoc.forms.ConnexionAdminForm;
import com.irisdoc.forms.ConnexionEleveForm;
import com.irisdoc.forms.ConnexionProfForm;

/**
 * Servlet implementation class Connexion
 */
@WebServlet("/connexion")
public class Connexion extends HttpServlet {
	public EleveDAO eleveDAO = null;
	public ProfDAO profDAO = null;
	public ClasseDAO classeDAO = null;
	public AdminDAO adminDAO = null;
	
	private static final String DAO_FACTORY = "daofactory";
	private static final String ATB_USERSESSION = "current_user";
	private static final String ATB_USERTYPE = "user_type";
	private static final String ATB_CLASSES = "list_classes";
	private static final String ATB_FORM = "form";
	private static final String FIELD_GLOBAL = "global";
	private static final String FIELD_USERTYPE = "userType";
	private static final String PATH_VIEW = "/WEB-INF/connexion.jsp";
	private static final String PATH_NEXT_ELEVE = "/student";
	private static final String PATH_NEXT_PROFESSEUR = "/teacher";
	private static final String PATH_NEXT_ADMIN = "/admin";
	private static final long serialVersionUID = 1L;

	public void init() throws ServletException {
		DAOFactory factory = ((DAOFactory) getServletContext().getAttribute(DAO_FACTORY));
		this.eleveDAO = factory.getEleveDAO();
		this.profDAO = factory.getProfDAO();
		this.classeDAO = factory.getClasseDAO();
		this.adminDAO = factory.getAdminDAO();
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher(PATH_VIEW).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String usertype = request.getParameter(FIELD_USERTYPE);
		session.setAttribute(ATB_USERTYPE, usertype);
		
		if(usertype.equals("eleve")) {
			ConnexionEleveForm form = new ConnexionEleveForm(eleveDAO);
			Eleve eleve = form.connectUser(request);
			
			if(eleve == null)
				form.addErreur(FIELD_GLOBAL, "Echec de la connexion - corriger les erreurs et rééssayer.");
			
			session.setAttribute(ATB_USERSESSION, eleve);
			
			if(form.getErreurs().isEmpty())			
				response.sendRedirect(request.getContextPath() + PATH_NEXT_ELEVE);
			else {
				request.setAttribute(ATB_FORM, form);
				this.getServletContext().getRequestDispatcher(PATH_VIEW).forward(request, response);
			}
		} else if(usertype.equals("professeur")) {
			ConnexionProfForm form = new ConnexionProfForm(profDAO);
			Professeur prof = form.connectUser(request);
			
			if(prof == null)
				form.addErreur(FIELD_GLOBAL, "Echec de la connexion - corriger les erreurs et rééssayer.");
			
			List<Classe> listClasses = classeDAO.listClasses();
			session.setAttribute(ATB_CLASSES, listClasses);
			
			session.setAttribute(ATB_USERSESSION, prof);

			if(form.getErreurs().isEmpty())			
				response.sendRedirect(request.getContextPath() + PATH_NEXT_PROFESSEUR);
			else {
				request.setAttribute(ATB_FORM, form);
				this.getServletContext().getRequestDispatcher(PATH_VIEW).forward(request, response);
			}
		} else if(usertype.equals("administrateur")) {
			ConnexionAdminForm form = new ConnexionAdminForm(adminDAO);
			Admin admin = form.connectUser(request);
			
			if(admin == null)
				form.addErreur(FIELD_GLOBAL, "Echec de la connexion - corriger les erreurs et rééssayer.");
			
			session.setAttribute(ATB_USERSESSION, admin);

			if(form.getErreurs().isEmpty())			
				response.sendRedirect(request.getContextPath() + PATH_NEXT_ADMIN);
			else {
				request.setAttribute(ATB_FORM, form);
				this.getServletContext().getRequestDispatcher(PATH_VIEW).forward(request, response);
			}
		}
		
	}

}
