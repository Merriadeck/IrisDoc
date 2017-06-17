package com.irisdoc.beans;

public class Question {
	private Integer identifiant;
	private String sujet;
	private String description;
	private Eleve poseurQuestion;
	private Integer projetLie;
	private String nomProjetLie;
	private Reponse reponse;
	
	public Question() {
	}

	public Integer getIdentifiant() {
		return identifiant;
	}

	public void setIdentifiant(Integer identifiant) {
		this.identifiant = identifiant;
	}

	public String getSujet() {
		return sujet;
	}

	public void setSujet(String sujet) {
		this.sujet = sujet;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Eleve getPoseurQuestion() {
		return poseurQuestion;
	}

	public void setPoseurQuestion(Eleve poseurQuestion) {
		this.poseurQuestion = poseurQuestion;
	}

	public Integer getProjetLie() {
		return projetLie;
	}

	public void setProjetLie(Integer projetLie) {
		this.projetLie = projetLie;
	}

	public String getNomProjetLie() {
		return nomProjetLie;
	}

	public void setNomProjetLie(String nomProjetLie) {
		this.nomProjetLie = nomProjetLie;
	}

	public Reponse getReponse() {
		return reponse;
	}

	public void setReponse(Reponse reponse) {
		this.reponse = reponse;
	}
	
	
}
