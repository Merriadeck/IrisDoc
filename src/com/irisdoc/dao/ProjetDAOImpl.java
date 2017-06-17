package com.irisdoc.dao;

import static com.irisdoc.dao.DAOGenericMethods.*;
import com.irisdoc.dao.DAOFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.irisdoc.beans.Classe;
import com.irisdoc.beans.Niveau;
import com.irisdoc.beans.Professeur;
import com.irisdoc.beans.Projet;

public class ProjetDAOImpl implements ProjetDAO {
	private DAOFactory factory;
	
	private static final String REQUEST_PROJET_LIST_BYCLASSE = "SELECT * FROM Projet INNER JOIN Classe ON Projet.IdClasse = Classe.IdClasse INNER JOIN Niveau ON Classe.IdNiveau = Niveau.IdNiveau INNER JOIN Professeur ON Projet.IdResponsable = Professeur.LoginProfesseur WHERE Classe.IdClasse = ?";
	private static final String REQUEST_PROJET_LIST_BYPROFESSEUR = "SELECT * FROM Projet INNER JOIN Classe ON Projet.IdClasse = Classe.IdClasse INNER JOIN Niveau ON Classe.IdNiveau = Niveau.IdNiveau WHERE IdResponsable = ?";
	private static final String REQUEST_CREATE_PROJET = "INSERT INTO Projet(NomProjet,DateLimiteProjet,EnonceProjet,IdResponsable,IdClasse) VALUES (?,?,?,?,?);";
	private static final String REQUEST_UPDATE_PROJET = "UPDATE Projet SET NomProjet = ?,DateLimiteProjet = ?,EnonceProjet = ?,IdResponsable = ?,IdClasse = ? WHERE IdProjet = ?";
	private static final String REQUEST_DELETE_PROJET = "DELETE FROM Projet WHERE IdProjet = ?";

	public ProjetDAOImpl(DAOFactory factory) {
		this.factory = factory;
	}
	
	@Override
	public List<Projet> listByClasse(Classe classe) throws DAOException {
		ResultSet set = null;
		PreparedStatement statement = null;
		Connection connexion = null;
		List<Projet> listProjets = new ArrayList<Projet>();
		
		try {
			// Récupération d'une connexion et exécution de la requête
			connexion = factory.getConnection();
			statement = makePreparedStatement(connexion,REQUEST_PROJET_LIST_BYCLASSE,false,classe.getIdentifiant());
			set = statement.executeQuery();
			
			// Récupération et stockage des résultats
			while(set.next())
				listProjets.add(toBean(set));
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			closeResource(set,statement,connexion);
		}
		
		return listProjets;
	}

	@Override
	public List<Projet> listByProfesseur(Professeur prof) throws DAOException {
		ResultSet set = null;
		PreparedStatement statement = null;
		Connection connexion = null;
		List<Projet> listProjets = new ArrayList<Projet>();
		
		try {
			// Récupération d'une connexion et exécution de la requête
			connexion = factory.getConnection();
			statement = makePreparedStatement(connexion,REQUEST_PROJET_LIST_BYPROFESSEUR,false,prof.getLogin());
			set = statement.executeQuery();
			
			// Récupération et stockage des résultats
			while(set.next())
				listProjets.add(toBean(set,prof));
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			closeResource(set,statement,connexion);
		}
		
		return listProjets;
	}

	@Override
	public void newProjet(Projet projet) throws DAOException {
		Connection connexion = null;
		PreparedStatement statement = null;
		ResultSet idGenere = null;
		int retour = 0;
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date = dateFormat.format(projet.getDateLimite());
		
		try {
			// Récupération d'une connexion et exécution de la requête
			connexion = factory.getConnection();
			statement = makePreparedStatement(connexion,REQUEST_CREATE_PROJET,true,projet.getNom(),date,projet.getEnonce(),projet.getResponsable().getLogin(),projet.getClasseCible().getIdentifiant());
			retour = statement.executeUpdate();
			
			// Vérification que le retour de la requête est correct
			if(retour == 0)
				throw new DAOException("Erreur - Le projet n'a pu être ajouté.");
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			closeResource(idGenere,statement,connexion);
		}
	}

	@Override
	public void editProjet(Projet projet) throws DAOException {
		Connection connexion = null;
		PreparedStatement statement = null;
		int retour = 0;
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date = dateFormat.format(projet.getDateLimite());
		
		try {
			// Récupération d'une connexion et exécution de la requéte
			connexion = factory.getConnection();
			statement = makePreparedStatement(connexion,REQUEST_UPDATE_PROJET,false,projet.getNom(),date,projet.getEnonce(),projet.getResponsable().getLogin(),projet.getClasseCible().getIdentifiant(),projet.getIdentifiant());
			retour = statement.executeUpdate();
			
			// Vérification que l'indication de retour de la requête est correcte
			if(retour == 0)
				throw new DAOException("Erreur - Aucun projet n'a été mis à jour.");
			else if(retour > 1)
				throw new DAOException("Erreur grave - Plus d'un projet à été mis à jour !");
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			closeResource(statement,connexion);
		}
	}

	@Override
	public void deleteProjet(Projet projet) throws DAOException {
		Connection connexion = null;
		PreparedStatement statement = null;
		int retour = 0;
		
		try {
			// Récupération d'une connexion et exécution de la requête
			connexion = factory.getConnection();
			statement = makePreparedStatement(connexion,REQUEST_DELETE_PROJET,false,projet.getIdentifiant());
			retour = statement.executeUpdate();

			// Vérification que l'indication de retour de la requête est correcte
			if(retour == 0)
				throw new DAOException("Erreur - Aucun projet n'a été supprimé.");
			else if(retour > 1)
				throw new DAOException("Erreur grave - Plus d'un projet a été supprimé !");
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			closeResource(statement,connexion);
		}
	}
	
	private Projet toBean(ResultSet set) throws SQLException {
		Niveau niveau = new Niveau();
		niveau.setIdentifiant(set.getInt("IdNiveau"));
		niveau.setLabel(set.getString("LabelNiveau"));
		
		Classe classe = new Classe();
		classe.setIdentifiant(set.getInt("IdClasse"));
		classe.setLabel(set.getString("LabelClasse"));
		classe.setNiveau(niveau);
		
		Professeur prof = new Professeur();
		prof.setLogin(set.getString("LoginProfesseur"));
		prof.setMail(set.getString("MailProfesseur"));
		prof.setNom(set.getString("NomProfesseur"));
		prof.setPrenom(set.getString("PrenomProfesseur"));
		prof.setMail(set.getString("MailProfesseur"));
		prof.setTelephone(set.getString("TelProfesseur"));
		
		Projet projet = new Projet();
		projet.setClasseCible(classe);
		projet.setDateLimite(set.getDate("DateLimiteProjet"));
		projet.setEnonce(set.getString("EnonceProjet"));
		projet.setIdentifiant(set.getInt("IdProjet"));
		projet.setNom(set.getString("NomProjet"));
		projet.setResponsable(prof);
		
		return projet;	
	}
	
	private Projet toBean(ResultSet set, Professeur prof) throws SQLException {
		Niveau niveau = new Niveau();
		niveau.setIdentifiant(set.getInt("IdNiveau"));
		niveau.setLabel(set.getString("LabelNiveau"));
		
		Classe classe = new Classe();
		classe.setIdentifiant(set.getInt("IdClasse"));
		classe.setLabel(set.getString("LabelClasse"));
		classe.setNiveau(niveau);
		
		Projet projet = new Projet();
		projet.setClasseCible(classe);
		projet.setDateLimite(set.getDate("DateLimiteProjet"));
		projet.setEnonce(set.getString("EnonceProjet"));
		projet.setIdentifiant(set.getInt("IdProjet"));
		projet.setNom(set.getString("NomProjet"));
		projet.setResponsable(prof);
		
		return projet;	
	}

}
