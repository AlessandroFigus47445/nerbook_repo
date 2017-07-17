<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<html>
    
    <head>
        <title> Bacheca NerdBook </title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="author" content="Alessandro Figus">
        <meta name="description" content="NerdBook Wall Page">
        <meta name="keywords" content="HTML, social, network, nerd, nerdbook">
        <link rel="stylesheet" type="text/css" href="css/style.css" media="screen">
    </head>

    <body>
  
        <jsp:include page="headerBacheca.jsp"/>
    
        <div id="content">    
            <c:choose>
                <c:when test="${sessionScope['utenteLoggedIn']==null}">
                    <p class="error"> Non autenticato </p>
                </c:when>
            
                <c:when test="${sessionScope['utenteLoggedIn']==false}">
                    <p class="error"> Autenticazione fallita </p>
                </c:when>
              
                <c:otherwise>
                
                    <jsp:include page="wallOwnerInfos.jsp"/>
                    
                    <div id="poststuff">
                        
                        <jsp:include page="wallActions.jsp"/>
             
                        <jsp:include page="listaPost.jsp"/>
                        
                    </div>
                        
                </c:otherwise>
            </c:choose>
        </div>
    
        <jsp:include page="footer.jsp"/>

    </body>
</html>
