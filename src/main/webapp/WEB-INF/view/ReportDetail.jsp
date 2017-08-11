<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div data-menutype="report">
    <div class="row">
        <div class="col-sm-12">
            <h4 class="page-title">
                <span>数据报告</span>
                <ol class="breadcrumb">
                    <jsp:include page="HomeName.jsp"/>
                    <li>
                        <a href="/report/view.shtml">数据报告</a>
                    </li>
                    <li class="active">${campaign_name}</li>
                </ol>
            </h4>
        </div>
    </div>
    <div class="row">
        <div class="portlet portlet-border">
            <div class="portlet-heading portlet-default">
                <h3 class="portlet-title">
                    <span>${campaign_name}</span>
                    <p class="text-muted">投放时间：${start} - ${end} <span class="m-l-15">已花费：<span class="num-float2">${cost}</span></span></p>
                </h3>
                <div class="pull-right time-range-base" id="">
                    <input type="text" class="form-control time-range" id="time_Range" readonly="readonly"  style="display: none">
                    <input type="text" class="form-control time-range" id="time_Day" readonly="readonly">
                    <i class="fa fa-calendar"></i></div>
                <div class="clearfix"></div>
            </div>
            <div class="panel-collapse collapse in" id="creative-base">
                <div class="portlet-body p-0">
                    <div id="ment_Report" class="side-menu-left">
                        <ul>
                            <li class="text-muted menu-title">基本</li>
                            <li class="has_sub">
                                <a href="javascript:;" class="waves-effect active"><span>趋势</span></a>
                                <ul class="list-unstyled" style="display: block">
                                    <li class="active"><a class="waves-effect chart-active" href="javascript:;" data-chart-type="line" data-chart-keyword="key"  data-table-orderindex="0"  data-select-value="hour">时报</a></li>
                                    <li><a class="waves-effect" href="javascript:;" data-chart-type="line" data-chart-xaxislen="10" data-chart-keyword="key"  data-chart-enddata="true" data-chart-xother="false"  data-table-orderindex="0" data-select-value="day">日报</a></li>
                                </ul>
                            </li>
                            <li class="has_sub">
                                <a href="javascript:;" class="waves-effect" data-chart-type="bar" data-chart-xaxislen="10" data-chart-keyword="value"  data-chart-xother="false"  data-table-orderindex="1" data-select-value="strategy"><span>策略</span> </a>
                            </li>
                            <li class="text-muted menu-title">受众</li>
                            <li class="has_sub">
                                <a href="javascript:;" class="waves-effect" data-chart-type="bar" data-chart-xaxislen="10" data-chart-keyword="key" data-table-orderindex="1" data-select-value="area"><span>地域</span></a>
                            </li>
                            <li class="has_sub">
                                <a href="javascript:;" class="waves-effect" data-chart-type="bar" data-table-orderindex="1" data-chart-keyword="key"  data-select-value="gender"><span>性别</span></a>
                            </li>
                            <li class="has_sub">
                                <a href="javascript:;" class="waves-effect" data-chart-type="bar" data-chart-xaxislen="10" data-chart-keyword="key"   data-table-orderindex="1" data-select-value="age"><span>年龄</span></a>
                            </li>
                        </ul>
                        <div class="clearfix"></div>
                    </div>
                    <div class="content-page-right ">
                        <div class="content-right">
                            <div class="h-40" style="padding-right: 20px">
                                <h3 class="pull-left m-0" id="menu_ReportTitle">基本-趋势-日报</h3>
                                <div id="chart_btns" class="button-list pull-right" style="">
                                    <button class="btn btn-default btn-frame-w1 waves-effect text-right" type="button" value="view_count">印象数</button>
                                    <button class="btn btn-default btn-frame-w1 waves-effect text-right" type="button" value="click_count">点击数</button>
                                    <button class="btn btn-white btn-frame-w1 waves-effect text-right" type="button" value="ctr">点击率</button>
                                    <button class="btn btn-white btn-frame-w1 waves-effect text-right" type="button" value="cost">花费</button>
                                    <button class="btn btn-white btn-frame-w1 waves-effect text-right" type="button" value="ecpc">eCPC</button>
                                    <button class="btn btn-white btn-frame-w1 waves-effect text-right" type="button" value="ecpm">eCPM</button>
                                </div>
                            </div>
                            <div class="datajsonload">
                                <div id="chart_home" style="height:400px;"></div>
                            </div>
                            <%--下载按钮--%>
                            <button class="btn btn-default waves-effect waves-light m-t-10" id="btn_download" >
                                <i class="fa fa-cloud-download"></i>
                                <span>导出报告</span>
                            </button>
                            <div class="table-custom p-t-10">
                                <table class="table table-bordered table-bordered-topbottom dataTable-sorting" id="tb_ListChart" width="100%">
                                    <thead>
                                    <tr>
                                        <th id="th_MenuName">日报</th>
                                        <th>印象数</th>
                                        <th>点击数</th>
                                        <th>点击率(%)</th>
                                        <th>花费(￥)</th>
                                        <th>eCPC(￥)</th>
                                        <th>eCPM(￥)</th>
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
</div>
<script type="text/javascript">
    $(function () {
        var $pageReportDetail = {
            tableReport:null,
            initChartDefault:{chartType:"line",legendName:{ "view_count":"印象数","click_count": "点击数","cost":"花费","ecpm":"eCPM", "ecpc": "eCPC","ctr":"点击率" }},
            infoChartTabsQueue:{count:2,list:["view_count","click_count"]},
            init: function () {
                $("#time_Range").ExtDateTime({infoExt:{type:"range", changeCallback:function(start, end, label){
                        $("#time_Range").trigger('hide.daterangepicker');
                        setTimeout(function(){$pageReportDetail.bindPageData();},100);
                    }}
                }).initDatePickerRange();
                $("#time_Day").ExtDateTime({infoExt:{changeCallback:function(ev){
                        $("#time_Day").trigger('hide.daterangepicker');
                        setTimeout(function(){$pageReportDetail.bindPageData();},100);
                    }}
                }).initDatePicker();
                $.ExtNumeric.init();
                this.bindPageData();
            },
            getAjaxInfo:function(type){
                var url="/report/campaign/daily.shtml?campaign_id=${campaign_id}";
                switch (type){
                    case "day":
                        url="/report/campaign/daily.shtml?campaign_id=${campaign_id}";
                        break;
                    case "hour":
                        url="/report/campaign/hourly.shtml?campaign_id=${campaign_id}";
                        break;
                    case "strategy":
                        url="/report/campaign/strategy.shtml?campaign_id=${campaign_id}";
                        break;
                    case "area":
                        url="/report/campaign/geo.shtml?campaign_id=${campaign_id}";
                        break;
                    case "gender":
                        url="/report/campaign/gender.shtml?campaign_id=${campaign_id}";
                        break;
                    case "age":
                        url="/report/campaign/age.shtml?campaign_id=${campaign_id}";
                        break;
                }
                var arrTime = [];
                if(type=="hour"){
                    var timeDay=$("#time_Day").val();
                    arrTime=[timeDay,timeDay];
                }else{
                    arrTime=$("#time_Range").ExtDateTime("getDateRangeValue");
                }
                return {url:url,para:{start:arrTime[0], end:arrTime[1]}};
            },
            bindPageData:function(){
                var self = this;
                var pageDefault = self.getPageDefaultByHtml();
                var ajaxInfo = self.getAjaxInfo(pageDefault.type);
                $.ExtAjax.initGet({
                    url: ajaxInfo.url, data: ajaxInfo.para,
                    loadinfo:{place:"box",boxId:"#chart_home",height:400,type:"circle",dataListName:"list"},
                    successcallback: function (result) {
                        $.ExtReport.initBind(pageDefault, result, function (resJson) {
                            self.pageData = resJson;
                            self.pageData.infoChartTabsQueue = self.infoChartTabsQueue;
                            self.pageData.tabledata = resJson.original.list.length > 0 ? resJson.original.list : [];
                            self.bindChartTwoVeidoo();
                            self.bindTable({data: self.pageData.tabledata, orderindex: pageDefault.table.orderindex});
                        });
                    }
                });
            },
            bindChartTwoVeidoo:function(){
                var self=this;
                var objChart=$("#chart_home");
                if(self.pageData.original.list.length>0){
                    var chartOption=$.ExtReport.getChartOption(self.pageData);
                    objChart.next().hide();
                    echarts.init(objChart[0],"macarons").setOption(chartOption);
                }else{
                    objChart.next(".datajson-load").show()
                }
            },
            getPageDefaultByHtml:function(){
                var self=this;
                var mentSelectData=$("#ment_Report").find(".chart-active").data();
                var chartSeriesType= $.ExtCommon.isCheckEmpty(mentSelectData.chartType)?self.initChartDefault.chartType :mentSelectData.chartType;
                var chartxAxisLen=$.ExtCommon.isCheckEmpty(mentSelectData.chartXaxislen)?-1:mentSelectData.chartXaxislen;
                var chartXother=$.ExtCommon.isCheckEmpty(mentSelectData.chartXother)?true:mentSelectData.chartXother;
                chartXother=chartxAxisLen==-1?false:chartXother;
                var chartEnddata=$.ExtCommon.isCheckEmpty(mentSelectData.chartEnddata)?false:mentSelectData.chartEnddata;
                var chartKeyword=$.ExtCommon.isCheckEmpty(mentSelectData.chartKeyword)?"key":mentSelectData.chartKeyword;
                var tableOrderIndex=$.ExtCommon.isCheckEmpty(mentSelectData.tableOrderindex)?0:mentSelectData.tableOrderindex;
                var type=$.ExtCommon.isCheckEmpty(mentSelectData.selectValue)?"":mentSelectData.selectValue;
                return {type:type,table:{orderindex:parseInt(tableOrderIndex)},chart:{seriesType:chartSeriesType,xAxisLen:parseInt(chartxAxisLen),xOther:chartXother,endData:chartEnddata,keyword:chartKeyword}};
            },
            bindTable: function (tableJson) {
                var self=this;
                if(self.tableReport) $("#tb_ListChart").ExtDataTable("destroy");
                self.tableReport=$("#tb_ListChart").ExtDataTable({
                    paging: false,
                    serverSide:false,
                    bInfo:false,
                    ordering:true,
                    order: [[ tableJson.orderindex, "desc" ]],
                    columns: [
                        { "data": "key" },
                        { "data": "view_count",sClass:"num-int  text-right td-sorting"},
                        { "data": "click_count",sClass:"num-int text-right td-sorting"},
                        { "data": "ctr",sClass:"num-float2 text-right td-sorting"},
                        { "data": "cost",sClass:"num-float2 text-right td-sorting"},
                        { "data": "ecpc",sClass:"num-float2 text-right td-sorting"},
                        { "data": "ecpm",sClass:"num-float2 text-right td-sorting"}
                    ],
                    infoExt:{data:tableJson.data},
                    createdRow: function (row, data, index) {
                        var keyvalue="";
                        switch(self.pageData.para.type){
                            case "hour":
                                keyvalue=$.ExtTarge.time.getHourInterval(parseInt(data.key));
                                break;
                            case "strategy":
                                keyvalue=data.value+" - "+data.key;
                                break;
                        }
                        if(keyvalue.length>0)  $('td', row).eq(0).html(keyvalue)
                    },
                    drawCallback: function () {
                        if(self.pageData.original.list.length>0) {
                            var totalInfo=self.pageData.original.total;
                            var trTotal = '<tr role="row" class="tr-total"><td class="sorting_1">总计</td><td class=" num-int  text-right td-sorting">' + totalInfo.view_count + '</td><td class=" num-int text-right td-sorting">' + totalInfo.click_count + '</td><td class=" num-float2 text-right td-sorting">' + totalInfo.ctr + '</td><td class=" num-float2 text-right td-sorting">' + totalInfo.cost + '</td><td class=" num-float2 text-right td-sorting">' + totalInfo.ecpc + '</td><td class=" num-float2 text-right td-sorting">' + totalInfo.ecpm + '</td></tr>'
                            $("#tb_ListChart").find("tbody").prepend(trTotal);
                        }
                        $.ExtNumeric.init();
                    }
                });
            }
        };
        $pageReportDetail.init();
        $("#ment_Report").on("click","a",function(){
            var _this=$(this);
            var _thisNext=_this.next();
            if(_thisNext.is("ul.list-unstyled")){
                if(_thisNext.is(":visible")){
                    _thisNext.slideUp();
                }else{
                    _thisNext.slideDown();
                }
            }else{
                if(!_this.hasClass("chart-active")) {
                    var arrTitle=[];
                    arrTitle.push(_this.text());
                    _this.closest("#ment_Report").find(".active,.chart-active").removeClass("active chart-active");
                    var _thisUL = _this.closest("ul");
                    if (_thisUL.hasClass("list-unstyled")) {
                        _this.addClass("chart-active").parent().addClass("active");
                        var thisPrev=_thisUL.prev();
                        thisPrev.addClass("active");
                        arrTitle.push(thisPrev.text());
                        arrTitle.push(_thisUL.parent().prevAll(".menu-title").eq(0).text());
                    } else {
                        _this.addClass("active chart-active");
                        arrTitle.push(_this.parent().prevAll(".menu-title").eq(0).text());
                    }
                    $("#th_MenuName").text(arrTitle[0]);
                    arrTitle.reverse();
                    $("#menu_ReportTitle").text(arrTitle.join("-"));
                    if(_this.data("selectValue")=="hour"){
                        var timeday=$.ExtDateTime.initMomentFormat();
                        $("#time_Range").hide();
                        $("#time_Day").val(timeday).show()
                    }else{
                        $("#time_Range").show();
                        $("#time_Day").hide();
                    }
                    $pageReportDetail.bindPageData();
                }
            }
        });
        $("#chart_btns .btn").on("click",function () {
            var _this=$(this);
            var value=_this.val();
            var arrValue=$.ExtReport.getChartTabsQueue(value,$pageReportDetail.pageData);
            if(arrValue.length>0){
                var chartbtns=$("#chart_btns");
                chartbtns.find(".btn-default").toggleClass("btn-white btn-default");
                $.each(arrValue,function(i,info){
                    chartbtns.find("button[value='"+info+"']").toggleClass("btn-white btn-default");
                });
                $pageReportDetail.bindChartTwoVeidoo();
            }else{
                $.ExtNotify.error("必须保留一个!");
            }
        });
        $("#btn_download").on("click",function () {
            var mentSelectData=$("#ment_Report").find(".chart-active").data();
            var type=$.ExtCommon.isCheckEmpty(mentSelectData.selectValue)?"":mentSelectData.selectValue;
            var arrTime = [];
            if(type=="hour"){
                var timeDay=$("#time_Day").val();
                arrTime=[timeDay,timeDay];
            }else{
                arrTime=$("#time_Range").ExtDateTime("getDateRangeValue");
            }
            var url=""
            switch (type){
                case "day":
                    url="/report/campaign/exportExcel/daily.shtml?campaign_id=${campaign_id}";
                    break;
                case "hour":
                    url="/report/campaign/exportExcel/hourly.shtml?campaign_id=${campaign_id}"
                    break;
                case "strategy":
                    url="/report/campaign/exportExcel/strategy.shtml?campaign_id=${campaign_id}"
                    break;
                case "area":
                    url="/report/campaign/exportExcel/geo.shtml?campaign_id=${campaign_id}"
                    break;
                case "gender":
                    url="/report/campaign/exportExcel/gender.shtml?campaign_id=${campaign_id}"
                    break;
                case "age":
                    url="/report/campaign/exportExcel/age.shtml?campaign_id=${campaign_id}"
                    break;
            }
            url=url+"&start="+arrTime[0]+"&end="+arrTime[1];
//          console.log(url);
            window.location.href = url;

        });
    });
</script>
