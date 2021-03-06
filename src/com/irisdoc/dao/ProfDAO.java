package com.irisdoc.dao;

import com.irisdoc.beans.Professeur;

public interface ProfDAO {
	void addProf(Professeur professeur, String password) throws DAOException;
	
	void editProf(Professeur professeur) throws DAOException;
	
	void changePassword(String loginProfesseur, String newPassword) throws DAOException;
	
	void deleteProf(String loginProfesseur) throws DAOException;
	
	Professeur findProfesseur(String loginProfesseur) throws DAOException;
	
	boolean checkPassword(String loginProfesseur, String password) throws DAOException;
}
