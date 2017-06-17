<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta content="text/html; charset=UTF-8">
		<title>Menu Professeur</title>
		<link rel="stylesheet" href="http://yui.yahooapis.com/pure/0.6.0/pure-min.css">
	</head>

	<body>
		<br />
		<table align="right" class="pure-table pure-table-horizontal">
			<tr>
				<th>Connecté en tant que :</th>
				<td><c:out value="${sessionScope.current_user.login}" /> (<c:out value="${sessionScope.current_user.prenom} ${sessionScope.current_user.nom}"/>)</td>
			</tr>
			<tr>
				<td><a href="<c:url value="/teacher" />">Retour à l'accueil</a></td>
				<td><a href="<c:url value="/deconnexion" />">Déconnexion</a></td>
			</tr>
			<tr>
				<th rowspan=2>Projets</th>
				<td><a href="<c:url value="/myProjets_prof" />">Afficher les devoirs disponibles</a></td>
			</tr>
			<tr>
				<td><a href="<c:url value="/newProjet" />">Ajouter un devoir</a></td>
			</tr>
			<tr>
				<th rowspan=1>Questions</th>
				<td><a href="<c:url value="/pendingQuestions" />">Afficher les questions sans réponse</a></td>
			</tr>
		</table>
		
	</body>
</html>