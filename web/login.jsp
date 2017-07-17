<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>NerdBook Login</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="author" content="Alessandro Figus">
        <meta name="description" content="NerdBook Login Page">
        <meta name="keywords" content="HTML, social, network, nerd, nerdbook">
        <link rel="stylesheet" type="text/css" href="css/style.css" media="screen">
    </head>
    
    <body>
        
        <jsp:include page="headerLogin.jsp"/>
        
        <div id="content">
            <h1>NerdBook Login</h1>
            <div id="loginform">
                <form method="POST" id="LoginForm" name="Submit" action="login.html"> <!-- Richiama la servlet Login.Java -->
                    <label for="Username"> Username </label>
                    <input type="text" name="Username" id="Username" value=""> <br>
                    <label for="Password"> Password </label>
                    <input type="password" name="Password" id="Password" value=""><br>
        
                    <input type="submit" class="submit" value="Login" name="Submit">
                    <input type="reset" class="reset">
                </form>
        
                <c:if test="${param['autenticazioneFallita']==true}">
                    <p class="error"> Login fallito: riprovare. </p>
                </c:if>
            
                <c:if test="${param['newProfile']==true}">
                    <p class="error"> Nuovo Profilo Creato, procedere al Login </p>
                </c:if>
            </div>    
        </div>
        
        <jsp:include page="footer.jsp"/>
        
    </body>
</html>
