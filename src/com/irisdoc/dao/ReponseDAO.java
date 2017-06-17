package com.irisdoc.dao;

import com.irisdoc.beans.Reponse;

public interface ReponseDAO {
	void newReponse(Reponse reponse, Integer idQuestion) throws DAOException;
	
	void deleteReponse(Integer idQuestion) throws DAOException;
}
