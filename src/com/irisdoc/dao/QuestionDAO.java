package com.irisdoc.dao;

import java.util.List;

import com.irisdoc.beans.Eleve;
import com.irisdoc.beans.Professeur;
import com.irisdoc.beans.Question;

public interface QuestionDAO {
	List<Question> listByEleve(Eleve eleve) throws DAOException;
	
	List<Question> listByProjet(Integer idProjet) throws DAOException;
	
	List<Question> listUnanswered(Professeur prof) throws DAOException;
	
	void newQuestion(Question question) throws DAOException;
	
	void editQuestion(Question question) throws DAOException;
	
	void deleteQuestion (Integer idQuestion) throws DAOException;
}
