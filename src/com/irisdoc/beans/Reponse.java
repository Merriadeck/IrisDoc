package com.irisdoc.beans;

import java.util.Date;

public class Reponse {
	private Integer idQuestion;
	private Professeur profRepondant;
	private Date dateReponse;
	private String contenu;
	
	public Reponse() {
	}

	public Integer getIdQuestion() {
		return idQuestion;
	}

	public void setIdQuestion(Integer idQuestion) {
		this.idQuestion = idQuestion;
	}

	public Professeur getProfRepondant() {
		return profRepondant;
	}

	public void setProfRepondant(Professeur profRepondant) {
		this.profRepondant = profRepondant;
	}

	public Date getDateReponse() {
		return dateReponse;
	}

	public void setDateReponse(Date dateReponse) {
		this.dateReponse = dateReponse;
	}

	public String getContenu() {
		return contenu;
	}

	public void setContenu(String contenu) {
		this.contenu = contenu;
	}
	
	
}
