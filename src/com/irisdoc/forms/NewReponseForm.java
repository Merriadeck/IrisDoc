package com.irisdoc.forms;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.irisdoc.beans.Professeur;
import com.irisdoc.beans.Reponse;
import com.irisdoc.dao.DAOException;
import com.irisdoc.dao.ReponseDAO;

public class NewReponseForm extends GenericForm {
	private ReponseDAO reponseDAO = null;
	
	private static final String ATB_USERSESSION = "current_user";
	private static final String FIELD_CONTENU = "contenuReponse";
	private static final String FIELD_IDQUESTION = "idQuestion";
	
	public NewReponseForm(ReponseDAO reponseDAO) {
		this.reponseDAO = reponseDAO;
	}
	
	public void newReponse(HttpServletRequest request) {
		Reponse reponse = new Reponse();
		
		// Récupération des champs
		Professeur prof = (Professeur) request.getAttribute(ATB_USERSESSION);
		Date date = new Date();
		String contenu = valueOf(request,FIELD_CONTENU);
		Integer idQuestion = valueOfInt(request,FIELD_IDQUESTION);
		
		// Validation des champs
		reponse.setProfRepondant(prof);
		reponse.setDateReponse(date);
		try {
			validateContenu(contenu);
		} catch (FormException e) {
			this.addErreur(FIELD_CONTENU, e.getMessage());
		}
		reponse.setContenu(contenu);
		
		// Si pas d'erreur, création de la nouvelle réponse
		if(erreurs.isEmpty()) {
			try {
				reponseDAO.newReponse(reponse, idQuestion);
			} catch (DAOException e) {
				this.addErreur(FIELD_GLOBAL, "Erreur imprévue.");
			}
		}
	}
	
	private void validateContenu(String contenu) {
		checkNotNull(contenu);
		if(contenu.length() < 1)
			throw new FormException("Veuillez entrer une réponse.");
		if(contenu.length() > 2000)
			throw new FormException("La longueur de la réponse ne peut excéder 2000 caractères.");
	}

}
