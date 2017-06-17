package com.irisdoc.dao;

import static com.irisdoc.dao.DAOGenericMethods.*;
import com.irisdoc.dao.DAOFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.irisdoc.beans.Classe;
import com.irisdoc.beans.Eleve;
import com.irisdoc.beans.Professeur;
import com.irisdoc.beans.Question;
import com.irisdoc.beans.Reponse;

public class QuestionDAOImpl implements QuestionDAO {
	private DAOFactory factory;
	
	private static final String REQUEST_QUESTION_LIST_BYELEVE = "SELECT * FROM Question LEFT JOIN Repondre ON Question.IdQuestion = Repondre.IdQuestion LEFT JOIN Professeur ON Repondre.LoginProfesseur = Professeur.LoginProfesseur LEFT JOIN Projet ON Question.IdProjet = Projet.IdProjet WHERE loginEleve = ?";
	private static final String REQUEST_QUESTION_LIST_BYPROJET = "SELECT * FROM Question LEFT JOIN Eleve ON Question.LoginEleve = Eleve.LoginEleve LEFT JOIN Repondre ON Question.IdQuestion = Repondre.IdQuestion LEFT JOIN Professeur ON Repondre.LoginProfesseur = Professeur.LoginProfesseur LEFT JOIN Projet ON Question.IdProjet = Projet.IdProjet WHERE Question.IdProjet = ?";
	private static final String REQUEST_QUESTION_LIST_UNANSWERED = "SELECT * FROM Question INNER JOIN Projet ON Question.IdProjet = Projet.IdProjet INNER JOIN Dependre ON Projet.IdProjet = Dependre.IdProjet INNER JOIN Eleve ON Question.LoginEleve = Eleve.LoginEleve WHERE Dependre.LoginProfesseur = ? AND Question.IdQuestion NOT IN (SELECT IdQuestion FROM Repondre)";
	private static final String REQUEST_CREATE_QUESTION = "INSERT INTO Question(SujetQuestion,DescriptionQuestion,LoginEleve,IdProjet) VALUES (?,?,?,?);";
	private static final String REQUEST_UPDATE_QUESTION = "UPDATE Question SET SujetQuestion = ?,DescriptionQuestion = ? WHERE IdQuestion = ?";
	private static final String REQUEST_DELETE_QUESTION = "DELETE FROM Question WHERE IdQuestion = ?";

	public QuestionDAOImpl(DAOFactory factory) {
		this.factory = factory;
	}
	
	@Override
	public List<Question> listByEleve(Eleve eleve) throws DAOException {
		ResultSet set = null;
		PreparedStatement statement = null;
		Connection connexion = null;
		List<Question> listQuestions = new ArrayList<Question>();
		
		try {
			// Récupération d'une connexion et exécution de la requête
			connexion = factory.getConnection();
			statement = makePreparedStatement(connexion,REQUEST_QUESTION_LIST_BYELEVE,false,eleve.getLogin());
			set = statement.executeQuery();
			
			// Récupération et stockage des résultats
			while(set.next())
				listQuestions.add(toBean(set, eleve));
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			closeResource(set,statement,connexion);
		}
		
		return listQuestions;
	}

	@Override
	public List<Question> listByProjet(Integer idProjet) throws DAOException {
		ResultSet set = null;
		PreparedStatement statement = null;
		Connection connexion = null;
		List<Question> listQuestions = new ArrayList<Question>();
		
		try {
			// Récupération d'une connexion et exécution de la requête
			connexion = factory.getConnection();
			statement = makePreparedStatement(connexion,REQUEST_QUESTION_LIST_BYPROJET,false,idProjet);
			set = statement.executeQuery();
			
			// Récupération et stockage des résultats
			while(set.next())
				listQuestions.add(toBean(set));
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			closeResource(set,statement,connexion);
		}
		
		return listQuestions;
	}

	@Override
	public List<Question> listUnanswered(Professeur prof) throws DAOException {
		ResultSet set = null;
		PreparedStatement statement = null;
		Connection connexion = null;
		List<Question> listQuestions = new ArrayList<Question>();
		
		try {
			// Récupération d'une connexion et exécution de la requête
			connexion = factory.getConnection();
			statement = makePreparedStatement(connexion,REQUEST_QUESTION_LIST_UNANSWERED,false,prof.getLogin());
			set = statement.executeQuery();
			
			// Récupération et stockage des résultats
			while(set.next())
				listQuestions.add(toBean(set,prof));
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			closeResource(set,statement,connexion);
		}
		
		return listQuestions;
	}

	@Override
	public void newQuestion(Question question) throws DAOException {
		Connection connexion = null;
		PreparedStatement statement = null;
		ResultSet idGenere = null;
		int retour = 0;
		
		try {
			// Récupération d'une connexion et exécution de la requête
			connexion = factory.getConnection();
			statement = makePreparedStatement(connexion,REQUEST_CREATE_QUESTION,true,question.getSujet(),question.getDescription(),question.getPoseurQuestion().getLogin(),question.getProjetLie());
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
	public void editQuestion(Question question) throws DAOException {
		Connection connexion = null;
		PreparedStatement statement = null;
		int retour = 0;
		
		try {
			// Récupération d'une connexion et exécution de la requéte
			connexion = factory.getConnection();
			statement = makePreparedStatement(connexion,REQUEST_UPDATE_QUESTION,false,question.getSujet(),question.getDescription(),question.getIdentifiant());
			retour = statement.executeUpdate();
			
			// Vérification que l'indication de retour de la requête est correcte
			if(retour == 0)
				throw new DAOException("Erreur - Aucune question n'a été mise à jour.");
			else if(retour > 1)
				throw new DAOException("Erreur grave - Plus d'une question à été mise à jour !");
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			closeResource(statement,connexion);
		}
	}

	@Override
	public void deleteQuestion(Integer idQuestion) throws DAOException {
		Connection connexion = null;
		PreparedStatement statement = null;
		int retour = 0;
		
		try {
			// Récupération d'une connexion et exécution de la requête
			connexion = factory.getConnection();
			statement = makePreparedStatement(connexion,REQUEST_DELETE_QUESTION,false,idQuestion);
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
	
	private Question toBean(ResultSet set, Eleve eleve) throws SQLException {
		Professeur prof = new Professeur();
		prof.setLogin(set.getString("LoginProfesseur"));
		prof.setMail(set.getString("MailProfesseur"));
		prof.setNom(set.getString("NomProfesseur"));
		prof.setPrenom(set.getString("PrenomProfesseur"));
		prof.setMail(set.getString("MailProfesseur"));
		prof.setTelephone(set.getString("TelProfesseur"));
		
		Reponse reponse = new Reponse();
		reponse.setProfRepondant(prof);
		reponse.setIdQuestion(set.getInt("IdQuestion"));
		reponse.setDateReponse(set.getDate("TimestampReponse"));
		reponse.setContenu(set.getString("ContenuReponse"));
		
		Question question = new Question();
		question.setPoseurQuestion(eleve);
		question.setIdentifiant(set.getInt("IdQuestion"));
		question.setSujet(set.getString("SujetQuestion"));
		question.setDescription(set.getString("DescriptionQuestion"));
		question.setNomProjetLie(set.getString("NomProjet"));
		question.setProjetLie(set.getInt("IdProjet"));
		if(reponse.getContenu() != null)
			question.setReponse(reponse);
		else
			question.setReponse(null);
		
		return question;
	}
	
	private Question toBean(ResultSet set) throws SQLException {
		Professeur prof = new Professeur();
		prof.setLogin(set.getString("LoginProfesseur"));
		prof.setMail(set.getString("MailProfesseur"));
		prof.setNom(set.getString("NomProfesseur"));
		prof.setPrenom(set.getString("PrenomProfesseur"));
		prof.setMail(set.getString("MailProfesseur"));
		prof.setTelephone(set.getString("TelProfesseur"));
		
		Reponse reponse = new Reponse();
		reponse.setProfRepondant(prof);
		reponse.setIdQuestion(set.getInt("IdQuestion"));
		reponse.setDateReponse(set.getDate("TimestampReponse"));
		reponse.setContenu(set.getString("ContenuReponse"));
		
		Classe classe = new Classe();
		classe.setIdentifiant(set.getInt("IdClasse"));
		
		Eleve eleve = new Eleve();
		eleve.setLogin(set.getString("LoginEleve"));
		eleve.setMail(set.getString("MailEleve"));
		eleve.setNom(set.getString("NomEleve"));
		eleve.setPrenom(set.getString("PrenomEleve"));
		eleve.setClasse(classe);
		
		Question question = new Question();
		question.setPoseurQuestion(eleve);
		question.setIdentifiant(set.getInt("IdQuestion"));
		question.setSujet(set.getString("SujetQuestion"));
		question.setDescription(set.getString("DescriptionQuestion"));
		question.setNomProjetLie(set.getString("NomProjet"));
		question.setProjetLie(set.getInt("IdProjet"));
		if(reponse.getContenu() != null)
			question.setReponse(reponse);
		else
			question.setReponse(null);
		
		return question;
	}
	
	private Question toBean(ResultSet set, Professeur prof) throws SQLException {
		Classe classe = new Classe();
		classe.setIdentifiant(set.getInt("IdClasse"));
		
		Eleve eleve = new Eleve();
		eleve.setLogin(set.getString("LoginEleve"));
		eleve.setMail(set.getString("MailEleve"));
		eleve.setNom(set.getString("NomEleve"));
		eleve.setPrenom(set.getString("PrenomEleve"));
		eleve.setClasse(classe);
		
		Question question = new Question();
		question.setPoseurQuestion(eleve);
		question.setIdentifiant(set.getInt("IdQuestion"));
		question.setSujet(set.getString("SujetQuestion"));
		question.setDescription(set.getString("DescriptionQuestion"));
		question.setNomProjetLie(set.getString("NomProjet"));
		question.setProjetLie(set.getInt("IdProjet"));
		question.setReponse(null);
		
		return question;
	}
}
