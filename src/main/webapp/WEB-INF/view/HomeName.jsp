<%@ page  %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:if test="${sessionScope.USER_INFO_SESSION.userRole eq 'ADVERTISER'}">
    <li>
        <a href="/">首页</a>
    </li>
</c:if>
