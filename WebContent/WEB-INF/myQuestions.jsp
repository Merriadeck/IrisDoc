<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta content="text/html; charset=UTF-8">
		<title>Mes questions</title>
	</head>
	

	<body>
		<%@include file="menu_eleve.jsp" %>
	
		<div>
			<c:choose>
				<c:when test="${empty sessionScope.list_questions}">
					<p class="erreur">Aucune question active.</p>
				</c:when>
				
				<c:otherwise>
					<table class="pure-table pure-table-horizontal">
						<tr>
							<th>N°</th>
							<th>Sujet Question</th>
							<th>Projet concerné</th>
							<th>Possède une réponse</th>
							<th class="details">Détails</th>
							<th class="modifier">Modifier la question</th>
							<th class="supprimer">Supprimer la question</th>
						</tr>
						<c:forEach items="${sessionScope.list_questions}" var="question" varStatus="status">
							<tr>
								<td><c:out value="${status.count}" /></td>
								<td><c:out value="${question.sujet}" /></td>
								<td><c:out value="${question.nomProjetLie}" /></td>
								<td>
									<c:choose>
										<c:when test="${empty question.reponse}">
											Non
										</c:when>
										<c:otherwise>
											Oui
										</c:otherwise>
									</c:choose>
								</td>
								<td class="details">
									<a href="<c:url value="/detailsMyQuestion" ><c:param name="idQuestion" value="${question.identifiant}" /></c:url>">
										<img src="${pageContext.request.contextPath}/static/details.png" alt="Details" />
									</a>						
								</td>
								<c:choose>
									<c:when test="${empty question.reponse}">
										<td class="details">
											<a href="<c:url value="/editQuestion" ><c:param name="idQuestion" value="${question.identifiant}" /></c:url>">
												<img src="${pageContext.request.contextPath}/static/modifier.png" alt="Modifier" />
											</a>						
										</td>
									</c:when>
									<c:otherwise>
										<td></td>
									</c:otherwise>
								</c:choose>
								
								<td class="details">
									<a href="<c:url value="/deleteQuestion" ><c:param name="idQuestion" value="${question.identifiant}" /></c:url>">
										<img src="${pageContext.request.contextPath}/static/supprimer.png" alt="Supprimer" />
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