package com.irisdoc.dao;

import static com.irisdoc.dao.DAOGenericMethods.closeResource;
import static com.irisdoc.dao.DAOGenericMethods.makePreparedStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.irisdoc.beans.Reponse;

public class ReponseDAOImpl implements ReponseDAO {
	private DAOFactory factory;
	
	private static final String REQUEST_CREATE_REPONSE = "INSERT INTO Repondre VALUES (?,?,?,?)";
	private static final String REQUEST_DELETE_REPONSE = "DELETE FROM Repondre WHERE IdQuestion = ?";
	
	public ReponseDAOImpl(DAOFactory factory) {
		this.factory = factory;
	}
	
	@Override
	public void newReponse(Reponse reponse, Integer idQuestion) throws DAOException {
		Connection connexion = null;
		PreparedStatement statement = null;
		ResultSet idGenere = null;
		int retour = 0;
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date = dateFormat.format(reponse.getDateReponse());
		
		try {
			// Récupération d'une connexion et exécution de la requête
			connexion = factory.getConnection();
			statement = makePreparedStatement(connexion,REQUEST_CREATE_REPONSE,true,idQuestion,reponse.getProfRepondant().getLogin(),date,reponse.getContenu());
			retour = statement.executeUpdate();
			
			// Vérification que le retour de la requête est correct
			if(retour == 0)
				throw new DAOException("Erreur - La réponse n'a pu être ajoutée.");
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			closeResource(idGenere,statement,connexion);
		}
	}

	@Override
	public void deleteReponse(Integer idQuestion) throws DAOException {
		Connection connexion = null;
		PreparedStatement statement = null;
		int retour = 0;
		
		try {
			// Récupération d'une connexion et exécution de la requête
			connexion = factory.getConnection();
			statement = makePreparedStatement(connexion,REQUEST_DELETE_REPONSE,false,idQuestion);
			retour = statement.executeUpdate();

			// Vérification que l'indication de retour de la requête est correcte
			if(retour == 0)
				throw new DAOException("Erreur - Aucune réponse n'a été supprimée.");
			else if(retour > 1)
				throw new DAOException("Erreur grave - Plus d'une réponse a été supprimée !");
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			closeResource(statement,connexion);
		}	
	}

}
