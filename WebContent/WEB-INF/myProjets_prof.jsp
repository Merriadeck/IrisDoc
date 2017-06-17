<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta content="text/html; charset=UTF-8">
		<title>Devoirs en cours</title>
	</head>
	

	<body>
		<%@include file="menu_prof.jsp" %>
	
		<div>
			<c:choose>
				<c:when test="${empty sessionScope.list_projets}">
					<p class="erreur">Aucun projet en cours.</p>
				</c:when>
				
				<c:otherwise>
					<table class="pure-table pure-table-horizontal">
						<tr>
							<th>N°</th>
							<th>Nom du projet</th>
							<th>Date limite</th>
							<th class="details">Détails</th>
							<th class="question">Afficher les questions</th>
							<th class="modifier">Modifier le projet</th>
							<th class="supprimer">Supprimer le projet</th>
						</tr>
						<c:forEach items="${sessionScope.list_projets}" var="projet" varStatus="status">
							<tr>
								<td><c:out value="${status.count}" /></td>
								<td><c:out value="${projet.nom}" /></td>
								<td><c:out value="${projet.dateLimite}" /></td>
								<td class="details">
									<a href="<c:url value="/detailsProjet" ><c:param name="idProjet" value="${projet.identifiant}" /></c:url>">
										<img src="${pageContext.request.contextPath}/static/details.png" alt="Details" />
									</a>						
								</td>
								<td class="question">
									<a href="<c:url value="/listQuestions" ><c:param name="idProjet" value="${projet.identifiant}" /></c:url>">
										<img src="${pageContext.request.contextPath}/static/contenu.png" alt="Questions" />
									</a>						
								</td>
								<td class="modifier">
									<a href="<c:url value="/editProjet" ><c:param name="idProjet" value="${projet.identifiant}" /></c:url>">
										<img src="${pageContext.request.contextPath}/static/modifier.png" alt="Modifier" />
									</a>						
								</td>
								<td class="supprimer">
									<a href="<c:url value="/deleteProjet" ><c:param name="idProjet" value="${projet.identifiant}" /></c:url>">
										<img src="${pageContext.request.contextPath}/static/supprimer.png" alt="Supprimer" />
									</a>						
								</td>
							</tr>
						</c:forEach>
					</table>
				</c:otherwise>		
			</c:choose>
		</div>
	
	
	</body>
</html>