<%-- 
    Document   : descrizione.jsp
    Created on : 6-mag-2017, 11.25.32
    Author     : aless
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Bentrovato in NerdBook</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="author" content="Alessandro Figus">
        <meta name="description" content="NerdBook Login Page ">
        <meta name="keywords" content="HTML, social, network, nerd, nerdbook">
        <link rel="stylesheet" type="text/css" href="css/style.css" media="screen">
    </head>
    
    <body>
        
        <jsp:include page="headerDescrizione.jsp"/>
        
        <div id="content">
            <h1>Bentrovato in NerdBook</h1>
            <h2>Il Social Network per Nerd</h2>
            <h3>Iscriversi è semplice e gratuito e lo sarà sempre</h3>
            <p>Con Nerdbook potrai avere una sobria (molto sobria) esperienza di Social Networking, <br>
                stringere amicizia con altri Nerd (oppure togliergliela), <br>
                iscriverti a Gruppi oppure crearne di tuoi (dalla pagina "Profilo") 
                per stare sempre al passo con le ultime Nerdate.<br>
                Ricorda che ciò che pubblichi nei gruppi comparirà nella Bacheca Personale ad ogni iscritto.</p>
            <h3>COSA?! Non ti sei ancora iscritto!? Cosa Aspetti?! Unisciti a NerdBook!</h3>
            <p> Per iscriversi cliccare su Registrazione (non usate apostrofi nelle stringhe o SKYNET insorgerà!!!)</p>
        </div>
        
        <jsp:include page="footer.jsp"/>
        
    </body>
    
</html>
