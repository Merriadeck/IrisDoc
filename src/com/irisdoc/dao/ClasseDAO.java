package com.irisdoc.dao;

import java.util.List;

import com.irisdoc.beans.Classe;

public interface ClasseDAO {
	List<Classe> listClasses() throws DAOException;
	
	void addClasse(Classe classe) throws DAOException;
	
	void deleteClasse(Integer idClasse) throws DAOException;
}
