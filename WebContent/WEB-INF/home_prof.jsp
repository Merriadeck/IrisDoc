<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta content="text/html; charset=UTF-8">
		<title>Accueil Professeur</title>
		<link rel="stylesheet" href="http://yui.yahooapis.com/pure/0.6.0/pure-min.css">
	</head>

	<body>
		<%@include file="menu_prof.jsp" %>
		
		<br /><br /><br />
		
		<table align="center">
			<tr>
				<th colspan=2>Accueil professeur - Bonjour ${sessionScope.current_user.prenom} ${sessionScope.current_user.nom}</th>
			</tr>
			<tr>
				<td><c:out value="Connecté en tant que ${sessionScope.current_user.login}" /></td>
			</tr>
			<tr>
				<th>Message : </th>
				<td> ${requestScope.message}</td>
			</tr>		
		</table>
	</body>
</html>