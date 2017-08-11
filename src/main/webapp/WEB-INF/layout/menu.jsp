<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<header id="topnav">
    <div class="topbar-main">
        <div class="container" style="position: relative;">
            <span class="logo-top"></span>
            <div class="logo">
                <a href="javascript:;" class="logo"  style="cursor: default;"><span>胜效通 DSP</span></a>
            </div>
            <div class="menu-extras">
                <ul class="nav navbar-nav pull-right">
                    <li class="">
                        <a href="javascript:;"><i class="fa fa-user" style="font-size: 20px"></i><span id="span_userName">${sessionScope.USER_INFO_SESSION.userName}</span></a>
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
                    <li class="has-submenu"><a id="menu_index" href="/index.jsp" style="padding-left: 0px;"><i class="fa fa-dashboard"></i>首页</a></li>
                    <li class="has-submenu"><a id="menu_campaign" href="/campaign/view.shtml"><i class="fa fa-align-justify"></i>订单管理</a></li>
                    <li class="has-submenu"><a id="menu_report" href="/report/view.shtml"><i class="fa fa-bar-chart"></i>数据报告</a></li>
                    <li class="has-submenu"><a id="menu_advertiser" href="/advertiser/view.shtml"><i class="fa fa-user"></i>账户中心</a></li>
                    <li class="has-submenu"><a id="menu_finance" href="/finance/view.shtml"><i class="fa fa-money"></i>财务管理</a></li>
                    <li class="has-submenu"><a id="menu_advertiserlist" href="/advertiser/list.shtml"><i class="fa fa-user"></i>广告主管理</a></li>
                </ul>
            </div>
        </div>
    </div>
</header>