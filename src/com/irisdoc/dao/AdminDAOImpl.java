package com.irisdoc.dao;

import static com.irisdoc.dao.DAOGenericMethods.closeResource;
import static com.irisdoc.dao.DAOGenericMethods.makePreparedStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.irisdoc.beans.Admin;

public class AdminDAOImpl implements AdminDAO {
	private DAOFactory factory;
	
	private static final String REQUEST_FIND_ADMIN = "SELECT * FROM Administrateur WHERE loginAdmin = ?";
	private static final String REQUEST_GET_PASSWORD = "SELECT passAdmin FROM Administrateur WHERE loginAdmin = ?";
	
	public AdminDAOImpl(DAOFactory factory) {
		this.factory = factory;
	}

	@Override
	public Admin findAdmin(String loginAdmin) throws DAOException {
		ResultSet set = null;
		PreparedStatement statement = null;
		Connection connexion = null;
		Admin admin = null;
		
		try {
			// Récupération d'une connexion et exécution de la requête
			connexion = factory.getConnection();
			statement = makePreparedStatement(connexion,REQUEST_FIND_ADMIN,false,loginAdmin);
			set = statement.executeQuery();
			
			// Récupération et stockage des résultats dans l'objet retourné
			if(set.next())
				admin = toBean(set);
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			closeResource(set,statement,connexion);
		}
	
		return admin;
	}

	@Override
	public boolean checkPassword(String loginAdmin, String password) throws DAOException {
		ResultSet set = null;
		PreparedStatement statement = null;
		Connection connexion = null;
		
		password = hashPassword(password,loginAdmin);
		
		try {
			// Récupération d'une connexion et exécution de la requête
			connexion = factory.getConnection();
			statement = makePreparedStatement(connexion,REQUEST_GET_PASSWORD,false,loginAdmin);
			set = statement.executeQuery();
			
			// Récupération du résultat et vérification de la correspondance des mots de passe
			if(set.next()) {
				return password.equals(set.getString("passAdmin"));
			} else
				throw new DAOException("Erreur - Identifiant introuvable.");
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			closeResource(set,statement,connexion);
		} 
	}
	
	private Admin toBean(ResultSet set) throws SQLException {
		Admin admin = new Admin();
		admin.setLogin(set.getString("loginAdmin"));
		
		return admin;
	}
	
	private static String hashPassword(String password, String login) {
		password = password + password.length() + login;
		password = Hashing.sha512().hashString(password, Charsets.UTF_8).toString();
		return password;
	}

}
