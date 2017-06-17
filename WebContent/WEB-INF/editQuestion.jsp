<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta content="text/html; charset=UTF-8">
		<title>Nouvelle question</title>
	</head>

	<body>
		<%@include file="menu_eleve.jsp" %>
		
		<%-- Recherche de la question à modifier dans la List session, puis stockage en variable  --%>
		<c:forEach items="${sessionScope.list_questions}" var="question" varStatus="status">
			<c:if test="${question.identifiant == param.idQuestion}">
				<c:set var="infosQuestion" scope="page" value="${sessionScope.list_questions[status.index]}"/>
			</c:if>
		</c:forEach>
	
		<form method="post" action="editQuestion">
			<fieldset>
				<input type="hidden" id="idQuestion" name="idQuestion" value="${infosQuestion.identifiant}" readonly />				
				
				<label for="sujetQuestion">Sujet de la question :</label>
				<input type="text" id="sujetQuestion" name="sujetQuestion" value="${infosQuestion.sujet}" size="30" maxlength="50" title="" required />
				<span class="erreur">${form.erreurs['sujetQuestion']}</span>
				<br /><br />
				
				<label for="descriptionQuestion">Description détaillée de la question :</label>
				<input type="text" id="descriptionQuestion" name="descriptionQuestion" value="${infosQuestion.description}" size="150" maxlength="1000" title="" required />
				<span class="erreur">${form.erreurs['descriptionQuestion']}</span>
				<br /><br />
				
				<input type="submit" value="Valider" class="valider" />
		        <br />
			</fieldset>
		</form>
	</body>
</html>