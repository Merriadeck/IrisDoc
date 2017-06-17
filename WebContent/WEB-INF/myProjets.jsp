<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta content="text/html; charset=UTF-8">
		<title>Devoirs disponibles</title>
	</head>
	

	<body>
		<%@include file="menu_eleve.jsp" %>
	
		<div>
			<c:choose>
				<c:when test="${empty sessionScope.list_projets}">
					<p class="erreur">Aucun projet disponible.</p>
				</c:when>
				
				<c:otherwise>
					<table class="pure-table pure-table-horizontal">
						<tr>
							<th>N°</th>
							<th>Nom du projet</th>
							<th>Date limite</th>
							<th>Professeur responsable</th>
							<th class="details">Détails</th>
							<th class="question">Poser une question</th>
							<th class="rendu">Effectuer un rendu</th>
						</tr>
						<c:forEach items="${sessionScope.list_projets}" var="projet" varStatus="status">
							<tr>
								<td><c:out value="${status.count}" /></td>
								<td><c:out value="${projet.nom}" /></td>
								<td><c:out value="${projet.dateLimite}" /></td>
								<td><c:out value="${projet.responsable.nom} ${projet.responsable.prenom}" /></td>
								<td class="details">
									<a href="<c:url value="/detailsProjet" ><c:param name="idProjet" value="${projet.identifiant}" /></c:url>">
										<img src="${pageContext.request.contextPath}/static/details.png" alt="Details" />
									</a>						
								</td>	
								<td class="question">
									<a href="<c:url value="/newQuestion" ><c:param name="idProjet" value="${projet.identifiant}" /></c:url>">
										<img src="${pageContext.request.contextPath}/static/assigner.png" alt="Question" />
									</a>
								</td>
								<td class="rendu">
									<a href="mailto:<c:out value="${projet.responsable.mail}" />?subject=Rendu '<c:out value="${projet.nom}"/>' - ${sessionScope.current_user.nom} ${sessionScope.current_user.prenom}&body=Bonjour %0D%0A%0D%0AVous trouverez ci-joint le rendu pour le projet <c:out value="${projet.nom}"/>.%0D%0A%0D%0ACordialement, %0D%0A ${sessionScope.current_user.prenom} ${sessionScope.current_user.nom} ">
										<img src="${pageContext.request.contextPath}/static/contenu.png" alt="Rendu" />
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