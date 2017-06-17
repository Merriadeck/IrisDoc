<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta content="text/html; charset=UTF-8">
		<title>Details Projet</title>
	</head>

	<body>
		<c:choose>
			<c:when test="${sessionScope.user_type == 'eleve'}">
				<%@include file="menu_eleve.jsp" %>
			</c:when>
			<c:otherwise>
				<%@include file="menu_prof.jsp" %>
			</c:otherwise>
		</c:choose>
	
		<%-- Recherche du projet correspondant à idProjet dans la List session, puis stockage en variable  --%>
		<c:forEach items="${sessionScope.list_projets}" var="projet" varStatus="status">
			<c:if test="${param.idProjet == projet.identifiant}">
				<c:set var="detailsProjet" scope="page" value="${sessionScope.list_projets[status.index]}"/>
			</c:if>
		</c:forEach>
		
		<div>
			<table class="pure-table pure-table-bordered">
				<tr>
					<th>Nom du projet</th>
					<th>Date limite de rendu</th>
				</tr>
				<tr>
					<td><c:out value="${detailsProjet.nom}" default="N/A"/></td>
					<td><c:out value="${detailsProjet.dateLimite}" default="N/A"/></td>
				</tr>
			</table>
			<br />
			
			<c:if test="${sessionScope.user_type == 'eleve'}">
				<table class="pure-table pure-table-bordered">
					<tr>
						<th colspan=3>Professeur responsable</th>
					</tr>
					<tr>
						<th>Nom</th>
						<th>Mail</th>
						<th>Telephone</th>
					</tr>
					<tr>
						<td><c:out value="${detailsProjet.responsable.prenom} ${detailsProjet.responsable.nom}" default="N/A"/></td>
						<td><c:out value="${detailsProjet.responsable.mail}" default="N/A"/></td>
						<td><c:out value="${detailsProjet.responsable.telephone}" default="Non renseigné."/></td>
					</tr>			
				</table>
				<br />
			</c:if>
			
			<table class="pure-table pure-table-bordered">
				<tr>
					<th colspan=1>Enoncé du projet</th>
				</tr>
				<tr>
					<td><c:out value="${detailsProjet.enonce}" default="N/A"/></td>
				</tr>			
			</table>
		
		
		</div>