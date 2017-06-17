<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta content="text/html; charset=UTF-8">
		<title>Nouvelle réponse</title>
	</head>

	<body>
		<%@include file="menu_prof.jsp" %>
	
		<form method="post" action="newReponse">
			<fieldset>
				<input type="hidden" id="idQuestion" name="idQuestion" value="${param.idQuestion}" readonly />
				
				<label for="contenuReponse">Contenu de la réponse :</label>
				<input type="text" id="contenuReponse" name="contenuReponse" value="" size="150" maxlength="2000" title="" required />
				<span class="erreur">${form.erreurs['contenuReponse']}</span>
				<br /><br />
				
				<input type="submit" value="Valider" class="valider" />
		        <br />
		        <span class="erreur">${form.erreurs['global']}</span>
			</fieldset>
		</form>
	</body>
</html>