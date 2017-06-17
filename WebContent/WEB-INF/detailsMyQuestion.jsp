<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta content="text/html; charset=UTF-8">
		<title>Details Question</title>
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
	
		<%-- Recherche de la question correspondant à idQuestion dans la List session, puis stockage en variable  --%>
		<c:forEach items="${sessionScope.list_questions}" var="question" varStatus="status">
			<c:if test="${param.idQuestion == question.identifiant}">
				<c:set var="detailsQuestion" scope="page" value="${sessionScope.list_questions[status.index]}"/>
			</c:if>
		</c:forEach>
		
		<div>
			<table class="pure-table pure-table-bordered">
				<tr>
					<th>Projet associé</th>
					<th>Sujet de la question</th>
				</tr>
				<tr>
					<td><c:out value="${detailsQuestion.nomProjetLie}" default="N/A"/></td>
					<td><c:out value="${detailsQuestion.sujet}" default="N/A"/></td>
				</tr>
			</table>
			<table class="pure-table pure-table-bordered">
				<tr>
					<th>Description de la question</th>
				</tr>
				<tr>
					<td><c:out value="${detailsQuestion.description}" default="N/A"/></td>
				</tr>
			</table>
			<br />
			
			
			<c:choose>
				<c:when test="${empty detailsQuestion.reponse}">
					<table class="pure-table pure-table-bordered">
						<tr>
							<th>Pas de réponse à cette question</th>
						</tr>
					</table>
				</c:when>
				
				<c:otherwise>
					<br />
					<table class="pure-table pure-table-bordered">
						<tr>
							<th colspan=3>Professeur ayant répondu</th>
							<th rowspan=2>Date de la réponse</th>
						</tr>
						<tr>
							<th>Nom</th>
							<th>Mail</th>
							<th>Telephone</th>
						</tr>
						<tr>
							<td><c:out value="${detailsQuestion.reponse.profRepondant.prenom} ${detailsQuestion.reponse.profRepondant.nom}" default="N/A"/></td>
							<td><c:out value="${detailsQuestion.reponse.profRepondant.mail}" default="N/A"/></td>
							<td><c:out value="${detailsQuestion.reponse.profRepondant.telephone}" default="Non renseigné."/></td>
							<td><c:out value="${detailsQuestion.reponse.dateReponse}" default="N/A" /></td>
						</tr>
					</table>
					<br />
					<table class="pure-table pure-table-bordered">
						<tr>
							<th>Contenu de la réponse</th>
						</tr>
						<tr>
							<td><c:out value="${detailsQuestion.reponse.contenu}" default="N/A" /></td>
						</tr>
					</table>
				</c:otherwise>
			</c:choose>
			
			<br />
		</div>