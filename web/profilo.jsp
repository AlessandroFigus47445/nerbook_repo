<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

    <head>
        <title> Profilo NerdBook </title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="author" content="Alessandro Figus">
        <meta name="description" content="NerdBook Profile Page ">
        <meta name="keywords" content="HTML, social, network, nerd, nerdbook">
        <link rel="stylesheet" type="text/css" href="css/style.css" media="screen">
    </head>

    <body>
  
    
        <jsp:include page="headerProfilo.jsp"/>
    
    
        <div id="content">
        <c:choose>
            <c:when test="${sessionScope['utenteLoggedIn']==null}">
                <p class="error"> Non autenticato </p>
            </c:when>
        
            <c:when test="${sessionScope['utenteLoggedIn']==false}">
                <p class="error"> Autenticazione fallita </p>
            </c:when>
      
            <c:otherwise>
                <h3>Rivedi le tue informazioni personali</h3>
                <form method="POST" id="EditProfile" name="Submit" action="editprofile.html"> <!-- Richiama la servlet EditProfile.java-->
          
                    <input type="hidden" value="${sessionScope.loggedUserid}" name="Userid">
                    <c:out value="Username: ${sessionScope.loggedUsername}"/> <br>
                    <input type="hidden" value="${sessionScope.loggedUsername}" name="Username">
                    <label for="Password"> Password </label>
                    <input type="password" name="Password1" id="Password" value="<c:out value="${sessionScope.loggedPassword}"/>"> <br>
                    <label for="Password"> Conferma Password </label>
                    <input type="password" name="Password2" id="Password" value="<c:out value="${sessionScope.loggedPassword}"/>"> <br>
                    <label for="Firstname"> Nome </label>
                    <input type="text" name="Firstname" id="Firstname" value="<c:out value="${sessionScope.loggedFirstname}"/>"> <br>
                    <label for="Lastname"> Cognome </label>
                    <input type="text" name="Lastname" id="Lastname" value="<c:out value="${sessionScope.loggedLastname}"/>"> <br>
                    <label for="Birthday"> Data di Nascita </label>
                    <input type="text" name="Birthday" id="Birthday" value="<c:out value="${sessionScope.loggedBirthday}"/>"> <br>
                    <textarea style="resize:none" rows="10" cols="50" maxlength="500" name="Motto" form="EditProfile"><c:out value="${sessionScope.loggedMotto}"/></textarea> <br>
          
                    <c:choose>
                        <c:when test="${sessionScope.loggedIsadmin==true}">
                            <input type="checkbox" name="Isadmin" checked="checked"> Admin (Ogni abuso verrà punito!) <br>
                        </c:when>        
                        <c:when test="${sessionScope.loggedIsadmin==false}">
                            <input type="checkbox" name="Isadmin"> Admin (Ogni abuso verrà punito!) <br>
                        </c:when>
                    </c:choose>
                
                    <input type="submit" class="submit" value="SALVA le mie informazioni" name="Submit">
                    <input type="reset" class="reset" value="REIMPOSTA come prima">
                </form>    
          
                
                <c:if test="${param['profiloModificato']==true}">
                    <p class="error"> Modifiche apportate con successo! </p>
                </c:if>
        
                <c:if test="${param['profiloModificato']==false}">
                    <p class="error"> Modifiche NON apportate, assicurarsi che:<br>
                                  1) I campi password che hai inserito coincidano<br>
                                  2) La Data di Nascita sia scritta in formato YYYY-MM-DD</p>
                </c:if>
            </c:otherwise>
        </c:choose>
                
        <div id="CreateGroup">
            <form method="POST" id="CreateGroup" name="Submit" action="groupmanager.html">
                <input type="hidden" name="command" value="create">
                <input type="hidden" name="owner" value="${sessionScope.loggedUsername}">
                <h3>Crea un nuovo Gruppo NerdBook</h3>
        
                <input type="text" name="name" id="Firstname" value="Nome nuovo gruppo">
                <input type="submit" name="Submit" value="CREA">                    
            </form>
        </div>
    </div> 
    
    <jsp:include page="footer.jsp"/>

    </body>
</html>