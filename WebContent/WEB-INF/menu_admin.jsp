<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta content="text/html; charset=UTF-8">
		<title>Menu Administrateur</title>
		<link rel="stylesheet" href="http://yui.yahooapis.com/pure/0.6.0/pure-min.css">
	</head>

	<body>
		<br />
		<table align="right" class="pure-table pure-table-horizontal">
			<tr>
				<th>Connecté en tant que :</th>
				<td><c:out value="${sessionScope.current_user.login}" /></td>
			</tr>
			<tr>
				<td><a href="<c:url value="/admin" />">Retour à l'accueil</a></td>
				<td><a href="<c:url value="/deconnexion" />">Déconnexion</a></td>
			</tr>
			<tr>
				<th rowspan=2>Professeurs</th>
				<td><a href="<c:url value="/listProfesseurs" />">Afficher les professeurs actifs</a></td>
			</tr>
			<tr>
				<td><a href="<c:url value="/newProfesseur" />">Ajouter un professeur</a></td>
			</tr>
			<tr>
				<th rowspan=2>Elèves</th>
				<td><a href="<c:url value="/listEleves" />">Afficher les élèves actifs</a></td>
			</tr>
			<tr>
				<td><a href="<c:url value="/newEleve" />">Ajouter un élève</a></td>
			</tr>
			<tr>
				<th rowspan=3>Classes</th>
				<td><a href="<c:url value="/listClasses" />">Afficher les classes disponibles</a></td>
			</tr>
			<tr>
				<td><a href="<c:url value="/newClasse" />">Ajouter une classe</a></td>
			</tr>
			<tr>
				<td><a href="<c:url value="/newNiveau" />">Ajouter une niveau</a></td>
			</tr>
		</table>
		
	</body>
</html>