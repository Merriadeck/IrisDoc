package com.irisdoc.beans;

import java.util.Date;

public class Projet {
	private Integer identifiant;
	private String nom;
	private Date dateLimite;
	private String enonce;
	private Professeur responsable;
	private Classe classeCible;
	
	public Projet() {
	}

	public Integer getIdentifiant() {
		return identifiant;
	}

	public void setIdentifiant(Integer identifiant) {
		this.identifiant = identifiant;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Date getDateLimite() {
		return dateLimite;
	}

	public void setDateLimite(Date dateLimite) {
		this.dateLimite = dateLimite;
	}

	public String getEnonce() {
		return enonce;
	}

	public void setEnonce(String enonce) {
		this.enonce = enonce;
	}

	public Professeur getResponsable() {
		return responsable;
	}

	public void setResponsable(Professeur responsable) {
		this.responsable = responsable;
	}

	public Classe getClasseCible() {
		return classeCible;
	}

	public void setClasseCible(Classe classeCible) {
		this.classeCible = classeCible;
	}
	
	
}
