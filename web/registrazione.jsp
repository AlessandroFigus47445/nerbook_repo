<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title> Bacheca NerdBook </title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="author" content="Alessandro Figus">
        <meta name="description" content="NerdBook Registration Page">
        <meta name="keywords" content="HTML, social, network, nerd, nerdbook">
        <link rel="stylesheet" type="text/css" href="css/style.css" media="screen">
    </head>
    
    <body>
        
        <div id="header">
        <header>
    
            <div id="logo">
                <img src="img/NerdBookLogo1.png" alt="NerdBook" height="100" width="132">
            </div>

            <div id="navbar">
                <ul>
                    <li> <form action="descrizione.jsp"> <input type="submit" value="Informazioni"> </form> </li>
                    <li> <form action="login.jsp"> <input type="submit" value="Login"> </form> </li>
                </ul>
            </div>

        </header>
        </div>
    
        <div id="content">
            <div id="registrationform">
            <h3>Compila il Form per registrarti</h3>   
            <form method="POST" id="NewProfile" name="Submit" action="newprofile.html"> 
                <label for="Username"> Username </label> 
                <input type="text" value="" name="Username"> <br>
                <label for="Password"> Password </label>
                <input type="password" name="Password1" id="Password" value=""> <br>
                <label for="Password"> Conferma Password </label>
                <input type="password" name="Password2" id="Password" value=""> <br>
                <label for="Firstname"> Nome </label>
                <input type="text" name="Firstname" id="Firstname" value=""> <br>
                <label for="Lastname"> Cognome </label>
                <input type="text" name="Lastname" id="Lastname" value=""> <br>
                <label for="Birthday"> Data di Nascita </label>
                <input type="text" name="Birthday" id="Birthday" value="YYYY-MM-GG"> <br>
                <textarea style="resize:none" rows="10" cols="50" maxlength="500" name="Motto" form="NewProfile">Inserisci una frase di presentazione (niente apostrofi!)</textarea> <br>
                <input type="submit" class="submit" value="Registrami" name="Submit">
                <input type="reset" class="reset" value="Annulla">
            </form>    
            
            <c:if test="${param['newProfile']==false}">
                <p class="error"> Errore nella registrazione, riprova assicurandoti che:<br>
                    1) Le password inserite combacino <br> 2) La data di nascita sia scritta in formato YYYY-MM-DD <br>
                    3) Non ci siano apostrofi nella tua frase di presentazione <br> 4) Se hai rispettato i precedenti punti, scegli un altro Username</p>
            </c:if>
            </div>
        </div>
        
        <jsp:include page="footer.jsp"/>
    
    </body>
</html>
