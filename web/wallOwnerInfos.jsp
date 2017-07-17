<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <div id="ownerinfos">
    <table id="wallownerinfos">
        <tbody>
            <c:choose>
                <c:when test="${sessionScope.visitedIsgroup==true}">
                    <tr> <td> <h3>Gruppo ${sessionScope.visitedName}</h3></td> </tr>
                </c:when>
                <c:when test="${sessionScope.visitedIsgroup==false}">
                    <tr> <td> <h3>Bacheca Personale</h3></td> </tr>
                </c:when>
            </c:choose>
            
            <tr> <td> <jsp:include page="deleteGroupButton.jsp"/> </td></tr>
            
            <c:choose>
                <c:when test="${sessionScope.visitedIsadmin==true}">
                    <tr> <td> Admin ${sessionScope.visitedUsername} </td> </tr>
                </c:when>
                <c:when test="${sessionScope.loggedIsadmin==false}">
                    <tr> <td> Utente ${sessionScope.visitedUsername} </td> </tr>
                </c:when>
            </c:choose>
            
            <tr> <td> ${sessionScope.visitedFirstname} ${sessionScope.visitedLastname}</td> </tr>
            <tr> <td> ${sessionScope.visitedBirthday} </td> </tr>
            <tr> <td> ${sessionScope.visitedMotto} </td> </tr>
        </tbody>
    </table>
    </div>
</html>
