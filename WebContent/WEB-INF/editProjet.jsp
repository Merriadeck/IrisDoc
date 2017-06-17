<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta content="text/html; charset=UTF-8">
		<title>Modifier le projet</title>
	</head>

	<body>
		<%@include file="menu_prof.jsp" %>
		
		<%-- Recherche du projet correspondant à idQuestion dans la List session, puis stockage en variable  --%>
		<c:forEach items="${sessionScope.list_projets}" var="projet" varStatus="status">
			<c:if test="${projet.identifiant == param.idProjet}">
				<c:set var="infosProjet" scope="page" value="${sessionScope.list_projets[status.index]}"/>
			</c:if>
		</c:forEach>
	
		<form method="post" action="editProjet">
			<fieldset>
				<input type="hidden" id="idProjet" name="idProjet" value="${infosProjet.identifiant}" readonly />
				
				<label for="nomProjet">Nom du projet :</label>
				<input type="text" id="nomProjet" name="nomProjet" value="${infosProjet.nom}" size="30" maxlength="100" title="" required />
				<span class="erreur">${form.erreurs['nomProjet']}</span>
				<br /><br />
				
				<label for="dateLimiteProjet">Date limite de rendu :</label>
				<input type="date" id="dateLimite" name="dateLimiteProjet" value="${infosProjet.dateLimite}" required />
				<span class="erreur">${form.erreurs['dateLimiteProjet']}</span>
				<br /><br />
				
				<label for="classeConcernee">Classe concernée :</label>
				<select name="classeConcernee">
					<c:forEach items="${sessionScope.list_classes}" var="classe">
						<option value="${classe.identifiant}" <c:if test="${classe.identifiant == infosProjet.classeCible.identifiant}">selected</c:if> >${classe.niveau.label}${classe.label}</option>
					</c:forEach>
				</select>
				<span class="erreur">${form.erreurs['classeConcernee']}</span>
				<br /><br />
				
				<label for="enonceProjet">Enoncé du projet :</label>
				<input type="text" id="enonceProjet" name="enonceProjet" value="${infosProjet.enonce}" size="150" maxlength="5000" title="" required />
				<span class="erreur">${form.erreurs['enonceProjet']}</span>
				<br /><br />
				
				<input type="submit" value="Valider" class="valider" />
		        <br />
		        <span class="erreur">${form.erreurs['global']}</span>
			</fieldset>
		</form>
	</body>
</html>