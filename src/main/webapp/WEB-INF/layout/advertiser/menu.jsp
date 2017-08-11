<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<header id="topnav">
    <div class="topbar-main">
        <div class="container" style="position: relative;">
            <span class="logo-top"></span>
            <div class="logo">
                <a href="javascript:;" class="logo"  style="cursor: default;"><span>胜效通DSP</span></a>
            </div>
            <div class="menu-extras">
                <ul class="nav navbar-nav pull-right">
                    <li class="">
                        <a href="javascript:;"><i class="fa fa-user" style="font-size: 20px;padding-right: 5px;"></i><span id="span_userName">${sessionScope.USER_INFO_SESSION.userName}</span></a>
                    </li>
                    <li class="">
                        <a href="/logout.shtml">
                            <i class="fa fa-power-off logout"></i>
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </div>
    <div class="navbar-custom">
        <div class="container">
            <div id="navigation">
                <ul class="navigation-menu">
                    <li class="has-submenu"><a id="menu_index" href="/index.shtml"><i class="fa fa-dashboard"></i>首页</a></li>
                    <c:choose>
                        <c:when test="${sessionScope.USER_INFO_SESSION.inLieyingAccount}">
                        </c:when>
                        <c:otherwise>
                            <li class="has-submenu"><a id="menu_campaign" href="/campaign/view.shtml"><i class="fa fa-align-justify"></i>订单管理</a></li>
                        </c:otherwise>
                    </c:choose>
                    <li class="has-submenu"><a id="menu_report" href="/report/view.shtml"><i class="fa fa-bar-chart"></i>数据报告</a></li>
                    <li class="has-submenu"><a id="menu_advertiser" href="/advertiser/detail.shtml"><i class="fa fa-user"></i>广告主信息</a></li>
                    <li class="has-submenu"><a id="menu_finance" href="/finance/view.shtml"><i class="fa fa-money"></i>财务管理</a></li>
                </ul>
            </div>
        </div>
    </div>
</header>