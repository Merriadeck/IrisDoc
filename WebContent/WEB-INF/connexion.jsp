<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta content="text/html; charset=UTF-8">
		<title>Connexion</title>
	</head>

	<body>
		<form method="post" action="connexion">
			<fieldset>
				<legend>Connexion</legend><br />
				
				<label for="userType">Connexion en tant que :</label>
				<select name="userType">
					<option value="eleve">Elève</option>
					<option value="professeur">Professeur</option>
					<!-- <option value="administrateur">Administrateur</option> -->
				</select>
				<br /><br />
			
				<label for="login">Login :</label>
				<input type="text" id="login" name="login" value="" size="30" maxlength="50" title="Format : xxx.xxxxxx" placeholder="Identifiant" required />
				<span class="erreur">${form.erreurs['login']}</span>
				<br /><br />
				
				<label for="password">Mot de passe :</label>
				<input type="password" id="password" name="password" value="" size="30" maxlength="" placeholder="********" required />
				<span class="erreur">${form.erreurs['password']}</span>
				<br /><br />
				
				<input type="submit" value="Validation" class="sansLabel" />
		        <br />
		                
		        <p>${form.erreurs['global']}</p>
		
			</fieldset>
		</form>
	</body>
</html>