<%@page contentType="application/json" pageEncoding="UTF-8"%>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<json:array>
    <c:forEach var="oggetto" items="${wallsList}">
        <json:object>
            <json:property name="wallid" value="${oggetto.getWallid()}"/>
            <json:property name="owner" value="${oggetto.getOwner()}"/>
            <json:property name="name" value="${oggetto.getName()}"/>
        </json:object>
    </c:forEach>
</json:array>
