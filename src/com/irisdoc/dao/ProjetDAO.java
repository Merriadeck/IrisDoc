package com.irisdoc.dao;

import java.util.List;

import com.irisdoc.beans.Classe;
import com.irisdoc.beans.Professeur;
import com.irisdoc.beans.Projet;

public interface ProjetDAO {
	List<Projet> listByClasse(Classe classe) throws DAOException;
	
	List<Projet> listByProfesseur(Professeur prof) throws DAOException;
	
	void newProjet(Projet projet) throws DAOException;
	
	void editProjet(Projet projet) throws DAOException;
	
	void deleteProjet(Projet projet) throws DAOException;
}
