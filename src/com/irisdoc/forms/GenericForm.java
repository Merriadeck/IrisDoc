package com.irisdoc.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * Classe mère de variables et fonctions génériques pour les formulaires
 */
public class GenericForm {
    protected Map<String, String> erreurs = new HashMap<String, String>();
    protected String succes = "";
    
    protected static final String FIELD_GLOBAL = "global";
    protected static final String REGEX_SAFEINPUT = "[1-zA-Z0-1@.\\éèêëàâäôöûüù*=+_ ]*";
	
    protected GenericForm() {
    }
    
    /**
     * Retourne la collection d'erreurs générées durant l'utilisation du formulaire
     * @return Une Map contenant les erreurs du formulaire
     */
	public Map<String, String> getErreurs() {
		return erreurs;
	}
	
	/**
	 * Ajoute une entrée dans la map d'erreurs
	 * @param champ Nom de la clé pour laquelle ajouter une erreur
	 * @param message Texte de l'erreur à ajouter
	 */
	public void addErreur(String champ, String message) {
		erreurs.put(champ, message);
	}
	
	public String getSucces() {
		return succes;
	}
	
	public void setSucces(String message) {
		this.succes = message;
	}
	
	/**
	 * Récupère la valeur d'un champ donné dans la requête
	 * @param request Requête HTTP fournie par la servlet
	 * @param field Champ à récupérer
	 * @return Null si le champ n'existe pas, le contenu du champ sinon.
	 */
	protected static String valueOf(HttpServletRequest request, String field) {
        String valeur = request.getParameter(field);
        if (valeur == null || valeur.trim().length() == 0) {
            return null;
        } else {
            return valeur.trim();
        }
    }
	
	protected static Integer valueOfInt(HttpServletRequest request, String field) throws FormException {
		try {
			Integer valeur = Integer.valueOf(request.getParameter(field).trim());
			
			if (valeur == null) {
	            return null;
	        } else {
	            return valeur;
	        }
		} catch (NumberFormatException e) {
			throw new FormException(e);
		}
    }
	
	protected void checkNotNull(Object champ) throws FormException {
		if(champ == null)
			throw new FormException("Champ non renseigné.");
	}
	
	protected void checkSafeString(String champ) throws FormException {
		if(! champ.matches(REGEX_SAFEINPUT))
			throw new FormException("Le champ contient des caractères interdits");
	}
	
	protected void simpleValidation(String champ) throws FormException {
		checkNotNull(champ);
		checkSafeString(champ);
	}
	
}
