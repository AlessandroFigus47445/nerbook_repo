<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <c:choose>
        
        <c:when test="${sessionScope.visitedIsgroup==true}">
            <c:choose>
                <c:when test="${sessionScope.property==true}">
                    <p>Questo Gruppo è TUO</p>
                    <jsp:include page="newPostForm.jsp"/>        
                </c:when>
                <c:otherwise>
                    <c:choose>
                        <c:when test="${sessionScope.subscription==true}">
                            <form id="FriendScription" name="Submit" method="POST" action="friendscription.html">
                                <label for="Submit"> SEI ISCRITTO a questo Gruppo &nbsp &nbsp &nbsp</label>
                                <input type="hidden" name="visitedWallid" value="${sessionScope.visitedWallid}">
                                <input type="hidden" name="command" value="unsubscribe">
                                <input type="submit" name="Submit" value="CANCELLAMI">
                            </form>
                            <jsp:include page="newPostForm.jsp"/>
                        </c:when>
                        <c:when test="${sessionScope.subscription==false}">
                            <form id="FriendScription" name="Submit" method="POST" action="friendscription.html">
                                <label for="Submit"> NON SEI ISCRITTO a questo Gruppo &nbsp &nbsp &nbsp</label>
                                <input type="hidden" name="visitedWallid" value="${sessionScope.visitedWallid}">
                                <input type="hidden" name="command" value="subscribe">
                                <input type="submit" name="Submit" value="ISCRIVIMI">
                            </form>
                        </c:when>
                    </c:choose>
                </c:otherwise>
            </c:choose>    
        </c:when>
                    
        <c:when test="${sessionScope.visitedIsgroup==false}">
            <c:choose>
                <c:when test="${sessionScope.property==true}">
                    <p>Questa è la TUA Bacheca</p>
                    <jsp:include page="newPostForm.jsp"/>
                </c:when>
                <c:otherwise>
                    <c:choose>
                        <c:when test="${sessionScope.friendship==true}">
                            <form id="FriendScription" name="Submit" method="POST" action="friendscription.html">
                                <label for="Submit"> SEI AMICO di questo Utente &nbsp &nbsp &nbsp</label>
                                <input type="hidden" name="visitedUser" value="${sessionScope.visitedUsername}">
                                <input type="hidden" name="command" value="breakfriendship">
                                <input type="submit" name="Submit" value="ANNULLA AMICIZIA">
                            </form>
                            <jsp:include page="newPostForm.jsp"/>
                        </c:when>
                        <c:when test="${sessionScope.friendship==false}">
                            <form id="FriendScription" name="Submit" method="POST" action="friendscription.html">
                                <label for="Submit"> NON SEI AMICO di questo Utente &nbsp &nbsp &nbsp</label>
                                <input type="hidden" name="visitedUser" value="${sessionScope.visitedUsername}">
                                <input type="hidden" name="command" value="makefriendship">
                                <input type="submit" name="Submit" value="STRINGI AMICIZIA">
                            </form>
                        </c:when>
                    </c:choose>
                </c:otherwise>
            </c:choose>   
        </c:when>
    </c:choose>
   
</html>
