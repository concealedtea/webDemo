<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div data-menutype="report">
    <div class="row">
        <div class="col-sm-12">
            <h4 class="page-title">
                <span>数据报告</span>
                <ol class="breadcrumb">
                    <jsp:include page="HomeName.jsp"/>
                    <li class="active">数据报告</li>
                </ol>
            </h4>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-12">
            <div class="card-box">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="h-40">
                            <div class="form-inline pull-right">
                                <select id="search_advertiser" class="w-200 form-control select2 root_advertiser"></select>
                                <div class="time-range-base"><input type="text" class="form-control time-range" id="time_Range" readonly="readonly"><i class="fa fa-calendar"></i></div>
                                <input type="text" class="form-control" id="search_keyword" placeholder="订单ID,订单名称">
                                <button type="button" class="btn btn-default waves-effect waves-light btn-md" id="btn_Search">搜索</button>
                            </div>
                        </div>
                        <div class="table-custom p-t-10 datajsonload">
                            <table class="table table-bordered" id="tb_ReportList" width="100%">
                                <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>订单名称</th>
                                    <th>投放时间</th>
                                    <th>印象数</th>
                                    <th>点击数</th>
                                    <th>点击率(%)</th>
                                    <th>花费(￥)</th>
                                    <th>eCPC(￥)</th>
                                    <th>eCPM(￥)</th>
                                    <th>广告主</th>
                                </tr>
                                </thead>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(function () {
        var $pageReportList = {
            tableReport:null,
            initOption: {id: "-99", text: "全部"},
            init: function () {
                <c:choose>
                    <c:when test="${sessionScope.USER_INFO_SESSION.userRole eq 'AGENCY'}">
                $pageReportList.bindAdvertiserData();
                    </c:when>
                    <c:otherwise>
                $(".root_advertiser").empty().remove();
                    </c:otherwise>
                </c:choose>
                $("#time_Range").ExtDateTime({infoExt:{type:"range", changeCallback:function(start, end, label){}}}).initDatePickerRange();
                $pageReportList.bindList();
            },
            bindAdvertiserData: function () {
                $.ExtAjax.initGet({url:"/advertiser/tree.shtml",data:{},
                    success:function(res){
                        var listAdvertiser=[];
                        for (var i = 0; i < res.length; i++) {
                            var info=res[i];
                            listAdvertiser.push({id:info.id,text:info.name});
                        }
                        listAdvertiser.unshift($pageReportList.initOption);
                        $("#search_advertiser").ExtSelect2({placeholder:"-广告主-",minimumResultsForSearch:1,select2Info:{data:listAdvertiser}});
                    }
                });
            },
            bindList: function () {
                $("#tb_ReportList").ExtDataTable({
                    columns: [
                        { "data": "cid" ,sClass:"w-70","bSortable": false},
                        { "data": "cname","bSortable": false},
                        { "data": "delivery_time",sClass:"w-180","bSortable": false},
                        { "data": "view_count",sClass:"w-100 text-right","bSortable": false},
                        { "data": "click_count",sClass:"w-100 text-right","bSortable": false},
                        { "data": "click_rate",sClass:"w-90 text-right","bSortable": false},
                        { "data": "cost",sClass:"w-100 text-right","bSortable": false},
                        { "data": "cpc",sClass:"w-90 text-right","bSortable": false},
                        { "data": "cpm",sClass:"w-90 text-right","bSortable": false},
                        { "data": null,sClass:"","bSortable": false}
                    ],
                    infoExt:{name:"报表列表",url:"/report/campaign/dailyList.shtml",ajaxDataPara:function(){
                        var para={};
                        var keyword = $.trim($("#search_keyword").val());
                        if (!$.ExtCommon.isCheckEmpty(keyword)) {para.search_keyword = keyword;}
                        var arrTime=$("#time_Range").ExtDateTime("getDateRangeValue");
                        if(arrTime.length>0){
                            para.startDate = arrTime[0];
                            para.endDate = arrTime[1];
                        }
                        var advertiser = $("#search_advertiser").val();
                        if ( !$.ExtCommon.isCheckEmpty(advertiser) && advertiser.toString() != "-99") {para.uid = advertiser;}
                        return para;
                    }},
                    createdRow: function (row, data, index) {
                        $('td', row).eq(1).html(' <a class="btn-operate-name" href="/report/detail.shtml?campaign_id='+data.cid+'">'+data.cname+'</a>');
                        $('td', row).eq(9).html(data.uname?data.uname:"");
                    },
                    drawCallback: function () {
                        $.ExtNumeric.init();
                        <c:choose>
                        <c:when test="${sessionScope.USER_INFO_SESSION.userRole eq 'AGENCY'}">
                        </c:when>
                        <c:otherwise>
                        $("#tb_ReportList").ExtDataTable("getTabelInfo").column(9).visible(false);
                        </c:otherwise>
                        </c:choose>
                    }
                });
            },
            refreshList: function () {
                $("#tb_ReportList").ExtDataTable("refresh");
            }
        };
        $pageReportList.init();
        $("#btn_Search").on("click",function () {
            $pageReportList.refreshList();
        });
        $("#search_keyword").on("keydown",function(event){
            if(event.keyCode == 13) {
                $pageReportList.refreshList();
            }
        });
    });
</script>
