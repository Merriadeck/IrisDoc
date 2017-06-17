<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta content="text/html; charset=UTF-8">
		<title>Questions sans réponse</title>
	</head>
	

	<body>
		<%@include file="menu_prof.jsp" %>
	
		<div>
			<c:choose>
				<c:when test="${empty sessionScope.list_questions}">
					<p class="erreur">Aucune question sans réponse.</p>
				</c:when>
				
				<c:otherwise>
					<table class="pure-table pure-table-horizontal">
						<tr>
							<th>N°</th>
							<th>Projet concerné</th>
							<th>Sujet de la question</th>
							<th>Eleve ayant posé la question</th>
							<th class="details">Détails</th>
							<th class="repondre">Répondre à la question</th>
						</tr>
						<c:forEach items="${sessionScope.list_questions}" var="question" varStatus="status">
							<tr>
								<td><c:out value="${status.count}" /></td>
								<td><c:out value="${question.nomProjetLie}" /></td>
								<td><c:out value="${question.sujet}" /></td>
								<td><c:out value="${question.poseurQuestion.prenom} ${question.poseurQuestion.nom}" /></td>
								<td class="details">
									<a href="<c:url value="/detailsMyQuestion" ><c:param name="idQuestion" value="${question.identifiant}" /></c:url>">
										<img src="${pageContext.request.contextPath}/static/details.png" alt="Details" />
									</a>
								</td>
								<td class="répondre">
									<a href="<c:url value="/newReponse" ><c:param name="idQuestion" value="${question.identifiant}" /></c:url>">
										<img src="${pageContext.request.contextPath}/static/modifier.png" alt="Répondre" />
									</a>
								</td>
							</tr>
						</c:forEach>
					</table>
				</c:otherwise>
			</c:choose>
			
			<br />
			<p>${form.erreurs['global']}</p>
		    <p>${form.succes}</p>
		</div>
	
	
	</body>
</html>