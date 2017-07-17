<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    
    <c:choose>
        <c:when test="${sessionScope.loggedIsadmin==true}">
            <c:choose>
                <c:when test="${sessionScope.visitedIsgroup==true}">
                    <form method="POST" id="CancelGroup" name="Submit" action="groupmanager.html">
                        <input type="hidden" name="command" value="delete">
                        <input type="hidden" name="actor" value="${sessionScope.loggedUsername}">
                        <input type="hidden" name="owner" value="${sessionScope.visitedUsername}">
                        <input type="hidden" name="wallid" value="${sessionScope.visitedWallid}">
                        <input type="hidden" name="isadmin" value="${sessionScope.loggedIsadmin}">
                        <input type="submit" name="Submit" value="ELIMINA gruppo">                    
                    </form>
                </c:when>
            </c:choose>
        </c:when>
        <c:otherwise>
            <c:choose>
                <c:when test="${sessionScope.property==true}">
                    <c:choose>
                        <c:when test="${sessionScope.visitedIsgroup==true}">
                            <form method="POST" id="CancelGroup" name="Submit" action="groupmanager.html">
                                <input type="hidden" name="command" value="delete">
                                <input type="hidden" name="actor" value="${sessionScope.loggedUsername}">
                                <input type="hidden" name="owner" value="${sessionScope.visitedUsername}">
                                <input type="hidden" name="wallid" value="${sessionScope.visitedWallid}">
                                <input type="hidden" name="isadmin" value="${sessionScope.loggedIsadmin}">
                                <input type="submit" name="Submit" value="ELIMINA gruppo">                    
                            </form>
                        </c:when>
                    </c:choose>
                </c:when>
            </c:choose>
        </c:otherwise>
    </c:choose>
                
</html>
