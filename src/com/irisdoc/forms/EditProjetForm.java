package com.irisdoc.forms;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.irisdoc.beans.Classe;
import com.irisdoc.beans.Professeur;
import com.irisdoc.beans.Projet;
import com.irisdoc.dao.DAOException;
import com.irisdoc.dao.ProjetDAO;

public class EditProjetForm extends GenericForm {
	private ProjetDAO projetDAO = null;
	
	private static final String ATB_USERSESSION = "current_user";
	private static final String FIELD_IDENTIFIANT = "idProjet";
	private static final String FIELD_NOM = "nomProjet";
	private static final String FIELD_DATELIMITE = "dateLimiteProjet";
	private static final String FIELD_ENONCE = "enonceProjet";
	private static final String FIELD_CLASSE = "classeConcernee";
	
	public EditProjetForm(ProjetDAO projetDAO) {
		this.projetDAO = projetDAO;
	}
	
	public void editProjet(HttpServletRequest request) {
		Projet projet = new Projet();
		
		// Récupération des champs
		Professeur prof = (Professeur) request.getAttribute(ATB_USERSESSION);
		Integer identifiant = valueOfInt(request,FIELD_IDENTIFIANT);
		String nom = valueOf(request,FIELD_NOM);
		String dateString = valueOf(request,FIELD_DATELIMITE);
		String enonce = valueOf(request,FIELD_ENONCE);
		Integer idClasse = valueOfInt(request,FIELD_CLASSE);
		
		// Validation des champs
		projet.setResponsable(prof);
		projet.setIdentifiant(identifiant);
		try {
			validateNom(nom);
		} catch (FormException e) {
			this.addErreur(FIELD_NOM, e.getMessage());
		}
		projet.setNom(nom);
		try {
			validateEnonce(enonce);
		} catch (FormException e) {
			this.addErreur(FIELD_ENONCE, e.getMessage());
		}
		projet.setEnonce(enonce);
		
		// Conversion et vérification de la date
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = dateFormat.parse(dateString);
		} catch (ParseException e1) {
			this.addErreur(FIELD_DATELIMITE, "Erreur de lecture de la date : vérifier le format.");
		}
		try {
			validateDate(date);
		} catch (FormException e) {
			this.addErreur(FIELD_DATELIMITE, e.getMessage());
		}
		projet.setDateLimite(date);
		
		// Stockage d'un objet Classe partiel pour le stockage (seul l'identifiant est requis)
		Classe classe = new Classe();
		classe.setIdentifiant(idClasse);
		projet.setClasseCible(classe);
		
		// Si pas d'erreur, création du nouveau projet
		if(erreurs.isEmpty()) {
			try {
				projetDAO.editProjet(projet);
			} catch (DAOException e) {
				this.addErreur(FIELD_GLOBAL, "Erreur imprévue.");
			}
		}
	}
	
	private void validateNom(String nom) {
		simpleValidation(nom);
		if(nom.length() < 1)
			throw new FormException("Veuillez entrer un nom.");
		if(nom.length() > 100)
			throw new FormException("La longueur du nom ne peut excéder 100 caractères.");
	}

	private void validateEnonce(String enonce) {
		checkNotNull(enonce);
		if(enonce.length() < 1)
			throw new FormException("Veuillez entrer un énoncé.");
		if(enonce.length() > 5000)
			throw new FormException("La longueur de l'énoncé ne peut excéder 5000 caractères.");
	}
	
	private void validateDate(Date date) {
		Date now = new Date();
		if(date.before(now))
			throw new FormException("La date ne peut être antérieure au prochain jour.");
	}
}
