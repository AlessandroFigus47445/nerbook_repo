<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <div id="header">
    <header>
    
        <div id="logo">
            <img src="img/NerdBookLogo1.png" alt="NerdBook" height="100" width="132">
        </div>
        
        <div id="loggedinfos">
            <ul>
                <c:choose>
                    <c:when test="${sessionScope.loggedIsadmin==true}">
                        <li> Ciao Admin ${sessionScope.loggedUsername}!</li>
                    </c:when>
        
                    <c:when test="${sessionScope.loggedIsadmin==false}">
                        <li> Ciao Utente ${sessionScope.loggedUsername}!</li>
                    </c:when>
                </c:choose>                 
        
                <li>
                    <form method="POST" id="Logout" name="Submit" action="logout.html">
                        <input type="submit" class="submit" value="Logout" name="Submit">
                    </form>
                </li>
            </ul>
        </div>   
        
        <div id="navbar">
            <ul>
                <li> <jsp:include page="personalWallButton.jsp"/> </li>
                <li> <form action="profilo.jsp"> <input type="submit" value="Profilo"> </form> </li>
            </ul>
        </div>
        
    </header>
    </div>
    
</html>
