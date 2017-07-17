<%-- 
    Document   : listaPost
    Created on : 16-giu-2017, 10.31.08
    Author     : aless
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    
    <div id="postlist">
    <table id="postlist">
        
        <thead><tr><td><h3>Post in questa bacheca</h3></td></tr></thead>
        
        <tbody>
            <c:forEach var="item" items="${listaPost}">
            
            <tr> <td> Autore: ${item.getAuthor()} </td> </tr>
            <tr> <td> Pubblicazione: ${item.getDated()} </td> </tr>
            <tr> <td> Messaggio: <br> ${item.getMsg()} </td> </tr>
            <tr> <td> Link Allegato: <br> <a href="http://${item.getUrl()}">${item.getUrl()}</a> </td> </tr>
            <tr> <td>
                    <form method="POST" id="DeletePost" name="Submit" action="postmanager.html"> 
                        <input type="hidden" name="command" value="delete">
                        <input type="hidden" name="postid" value="${item.getPostid()}">
                        <input type="hidden" name="wallid" value="${sessionScope.visitedWallid}">  
                        <input type="hidden" name="isgroup" value="${sessionScope.visitedIsgroup}">
                        <input type="hidden" name="isadmin" value="${sessionScope.loggedIsadmin}">
                        <input type="hidden" name="property" value="${sessionScope.property}">
                        <input type="submit" class="submit" value="Elimina questo Post" name="Submit">
                    </form>
                 </td> </tr>
            <tr><td>-------------------------</td></tr>
            
            </c:forEach>
        </tbody>
        
    </table>
    </div>
    
</html>
