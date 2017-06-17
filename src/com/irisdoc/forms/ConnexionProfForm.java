package com.irisdoc.forms;

import javax.servlet.http.HttpServletRequest;

import com.irisdoc.beans.Professeur;
import com.irisdoc.dao.DAOException;
import com.irisdoc.dao.ProfDAO;

public class ConnexionProfForm extends GenericForm {
	private ProfDAO profDAO;
	
	private static final String FIELD_LOGIN = "login";
    private static final String FIELD_PASSWORD = "password";
    
    public ConnexionProfForm(ProfDAO profDAO) {
    	this.profDAO = profDAO;
    }
    
    public Professeur connectUser(HttpServletRequest request) {
    	Professeur prof = null;
    	
    	// Récupération des champs
    	String login = valueOf(request,FIELD_LOGIN);
		String password = valueOf(request,FIELD_PASSWORD);
		
		// Validation des champs
		try {
			simpleValidation(login);
		} catch (FormException e) {
			this.addErreur(FIELD_LOGIN, e.getMessage());
		}
		try{
			validatePassword(password);
		} catch(FormException e) {
			this.addErreur(FIELD_PASSWORD, e.getMessage());
		}
		
		// Si pas d'erreur, récupération des données du commercial à connecter
		if(erreurs.isEmpty()) {
			try {
				if(profDAO.checkPassword(login,password)) {
					prof = profDAO.findProfesseur(login);
				} else
					this.addErreur(FIELD_PASSWORD, "Mot de passe incorrect.");
			} catch(DAOException e) {
				if(e.getMessage().contains("Identifiant introuvable"))
					this.addErreur(FIELD_LOGIN, "Cet identifiant n'existe pas");
				else
					this.addErreur(FIELD_LOGIN, "Erreur imprévue : <br />" + e.getMessage());
			}
		}
		
		return prof;
    }
    
    /**
	 * Vérifie que le mot de passe est renseigné et correspond à la forme attendue
	 * @param password Mot de passe à tester
	 * @throws FormException Exception levée en cas d'erreur sur le mot de passe
	 */
	private void validatePassword(String password) throws FormException {
		checkNotNull(password);
		checkSafeString(password);
		if(password.trim().length() < 8)
			throw new FormException("Le mot de passe doit faire au moins 8 caractères.");
	}
}
