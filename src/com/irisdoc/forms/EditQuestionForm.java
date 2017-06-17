package com.irisdoc.forms;

import javax.servlet.http.HttpServletRequest;

import com.irisdoc.beans.Question;
import com.irisdoc.dao.DAOException;
import com.irisdoc.dao.QuestionDAO;

public class EditQuestionForm extends GenericForm {
	private QuestionDAO questionDAO = null;
	
	private static final String FIELD_IDQUESTION = "idQuestion";
	private static final String FIELD_SUJET_QUESTION = "sujetQuestion";
	private static final String FIELD_DESCRIPTION_QUESTION = "descriptionQuestion";
	
	public EditQuestionForm(QuestionDAO questionDAO) {
		this.questionDAO = questionDAO;
	}
	
	public void editQuestion(HttpServletRequest request) {
		Question question = new Question();
		
		// Récupération des champs
		String sujetQuestion = valueOf(request,FIELD_SUJET_QUESTION);
		String descriptionQuestion = valueOf(request,FIELD_DESCRIPTION_QUESTION);
		Integer idQuestion = valueOfInt(request,FIELD_IDQUESTION);
		
		// Validation des champs
		question.setIdentifiant(idQuestion);
		try {
			validateSujet(sujetQuestion);
		} catch (FormException e) {
			this.addErreur(FIELD_SUJET_QUESTION, e.getMessage());
		}
		question.setSujet(sujetQuestion);
		try {
			validateDescription(descriptionQuestion);
		} catch (FormException e) {
			this.addErreur(FIELD_DESCRIPTION_QUESTION, e.getMessage());
		}
		question.setDescription(descriptionQuestion);
		
		// Si pas d'erreurs, modification de la question
		if(erreurs.isEmpty()) {
			try {
				questionDAO.editQuestion(question);
			} catch (DAOException e) {
				this.addErreur(FIELD_GLOBAL, "Erreur imprévue.");
			}
		}
		
	}
	
	private void validateSujet(String sujet) {
		simpleValidation(sujet);
		if(sujet.length() < 1)
			throw new FormException("Veuillez entrer un sujet.");
		if(sujet.length() > 50)
			throw new FormException("La longueur du sujet ne peut excéder 50 caractères.");
	}

	private void validateDescription(String description) {
		checkNotNull(description);
		if(description.length() < 1)
			throw new FormException("Veuillez entrer une description.");
		if(description.length() > 1000)
			throw new FormException("La longueur de la description ne peut excéder 1000 caractères.");
	}
}
