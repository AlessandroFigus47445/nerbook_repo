<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html>
    <form id="LoadWall" name="Submit" method="POST" action="loadwall.html">
        <input type="hidden" name="wallid" value="${sessionScope.loggedWallid}">
        <input type="hidden" name="owner" value="${sessionScope.loggedUsername}">
        <input type="hidden" name="name" value="">
        <input type="hidden" name="isgroup" value=false>
        <input type="submit" name="Submit" value="La mia Bacheca">
    </form>
</html>
