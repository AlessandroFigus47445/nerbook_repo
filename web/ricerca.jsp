<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<html>
    <head>
        <title> Ricerca NerdBook </title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="author" content="Alessandro Figus">
        <meta name="description" content="NerdBook Search Page ">
        <meta name="keywords" content="HTML, social, network, nerd, nerdbook">
        <link rel="stylesheet" type="text/css" href="css/style.css" media="screen">
        <!-- Script -->
        <script type="text/javascript" src="js/jquery-2.2.4.min.js"></script>
        <script type="text/javascript" src="js/script.js"></script>
    </head>
    
    <body>
        <jsp:include page="headerRicerca.jsp"/>
        
        <div id="content">
            <h1>Cerca Utente/Gruppo</h1>
        
            <div id="BarraRicerca">
                <label for="Ricerca"> Nome Utente/Gruppo </label>
                <input type="text" id="Ricerca"/>
            </div>
    
            <p id="searchError">
                Nessun Utente/Gruppo trovato.
            </p>
        
            <div id="searchtable">
                <table id="items">
       
                </table>
            </div>
        </div>
       
        <jsp:include page="footer.jsp"/>

    </body>
</html>