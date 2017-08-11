<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="/resources/image/favicon.ico" rel="bookmark" type="image/x-icon" />
    <link href="/resources/image/favicon.ico" rel="icon" type="image/x-icon" />
    <link href="/resources/image/favicon.ico" rel="shortcut icon" type="image/x-icon" />
    <title><tiles:getAsString name="title" ignore="true"/></title>
    <tiles:insertAttribute name="header"/>
</head>
<body>
    <tiles:insertAttribute name="menu"/>
    <div class="wrapper">
        <div class="container">
            <tiles:insertAttribute name="body"/>
           <%-- <tiles:insertAttribute name="footer"/>--%>
        </div>
        <a href="javascript:;" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
            <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
        </a>
    </div>
</body>
</html>
