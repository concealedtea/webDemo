<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="page-strategy" data-menutype="campaign">
    <div class="row" >
        <div class="col-sm-12">
            <h4 class="page-title">
                <span class="showpagetitle"></span>
                <ol class="breadcrumb">
                    <jsp:include page="HomeName.jsp"/>
                    <li>
                        <a href="/campaign/view.shtml">订单管理</a>
                    </li>
                    <li class="active">${campaignName}</li>
                    <li class="active showpagetitle"></li>
                </ol>
            </h4>
        </div>
    </div>
    <!-- Main  -->
    <div class="row">
        <div class="portlet portlet-border">
            <div class="portlet-heading portlet-default">
                <h3 class="portlet-title">订单-${campaignName} <a class="btn-operate-name" href="/report/detail.shtml?campaign_id=${cid}" target="_blank"><i class="fa fa-bar-chart"></i></a></h3>
                <h3 class="portlet-title text-dark pull-right" >实际花费与流量 <a class="btn-operate-name" id="btn_CampaignToday" data-timetype="today" href="javascript:;">今日</a>/<a class="btn-operate-name" id="btn_CampaignYesterday"  data-timetype="yesterday"  href="javascript:;">昨日</a></h3>
                <div class="clearfix"></div>
            </div>
            <div class="panel-collapse collapse in" id="panel_CampaignReport">
                <div class="portlet-body">
                    <div class="row">
                        <div class="col-lg-4 p-t-l-20">
                            <div class="row m-0">
                                <div class="col-md-6 p-l-r-5">
                                    <div class="card-box widget-box-1 bg-white">
                                        <h4 class="text-dark">印象数</h4>
                                        <h2 class="text-dark text-center"><span class="num-int counter" id="campaign_viewCount">0</span></h2>
                                    </div>
                                </div>
                                <div class="col-md-6 p-l-r-5">
                                    <div class="card-box widget-box-1 bg-white">
                                        <h4 class="text-dark">点击数</h4>
                                        <h2 class="text-dark text-center"><span class="num-int counter" id="campaign_clickCount">0</span></h2>
                                    </div>
                                </div>
                                <div class="col-md-6 p-l-r-5">
                                    <div class="card-box widget-box-1 bg-white">
                                        <h4 class="text-dark">花费</h4>
                                        <h2 class="text-dark text-center"><span class="num-float2 counter" id="campaign_cost">0</span></h2>
                                    </div>
                                </div>
                                <div class="col-md-6 p-l-r-5">
                                    <div class="card-box widget-box-1 bg-white">
                                        <h4 class="text-dark">平均点击价格</h4>
                                        <h2 class="text-dark text-center"><span class="num-float2 counter" id="campaign_ecpc">0</span></h2>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-8 p-l-0 h-220">
                            <div class="" id="chart_strategy" style="height: 100%"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="portlet portlet-border">
            <div class="panel-collapse collapse in">
                <div class="portlet-body">
                    <div class="row">
                        <div class="col-lg-12 tabs-strategy">
                            <ul class="nav nav-tabs tabs" id="tabs_list" style="width: 200px">
                                <li class="tab">
                                    <a href="#tab_strategy" data-tabpagetype="strategy" data-toggle="tab" aria-expanded="false" <c:if test="${tabName eq 'strategy'}">class="active"</c:if>>
                                        <span class="">策略管理</span>
                                    </a>
                                </li>
                                <li class="tab">
                                    <a href="#tab_creative" data-tabpagetype="creative" data-toggle="tab" aria-expanded="false" <c:if test="${tabName eq 'creative'}">class="active"</c:if>>
                                        <span class="">创意管理</span>
                                    </a>
                                </li>
                            </ul>
                            <div class="tab-content">
                                <div class="tab-pane" id="tab_strategy">
                                    <jsp:include page="StrategyList.jsp"/>
                                </div>
                                <div class="tab-pane" id="tab_creative">
                                    <jsp:include page="CreativeList.jsp"/>
                                </div>
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
        var $pageCampaignInfo = {
            initReportName: { "view_count":"印象数","click_count": "点击数", "ecpc": "平均点击价格","cost":"花费","ctr":"点击率" },
            optionChartBase :{
                tooltip: {
                    trigger: 'axis'
                },
                grid: {
                    left: '1%',
                    right: '1%',
                    top: '25',
                    bottom: '10',
                    containLabel: true
                },
                legend: {
                    data:["印象数",'点击']
                },
                xAxis: [
                    {
                        type: 'category',
                        data: ["0",'1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19','20','21','22','23'],
                        splitLine:{interval:0}
                    }
                ],
                yAxis: [
                    {
                        type: 'value'
                    },
                    {
                        type: 'value',
                        min:0,
                        max:300,
                        interval:50
                    }
                ],
                series: [
                    {
                        name:'印象数',
                        type:'bar',
                        yAxisIndex: 0,
                        data:[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
                    },
                    {
                        name:'点击',
                        type:'line',
                        yAxisIndex: 1,
                        data:[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
                    }
                ]
            },
            isReportShowIng:true,
            init: function () {
                this.initCampaignReport($("#btn_CampaignToday"));
                $("#tabs_list").ExtTabsVelocity();
                <c:choose>
                    <c:when test="${tabName == 'strategy'}">
                        $(".showpagetitle").text("策略管理");
                        $("#tab_strategy").addClass("active");
                        $pageStrategyList.init();
                    </c:when>
                    <c:otherwise>
                        $(".showpagetitle").text("创意管理");
                        $("#tab_creative").addClass("active");
                        $pageCreativeList.init();
                    </c:otherwise>
                </c:choose>
            },
            ///顶部报表
            initCampaignReport:function(_this){
                if(!_this.hasClass("active")){
                    if($pageCampaignInfo.isReportShowIng){
                        $pageCampaignInfo.isReportShowIng=false;
                        $.ExtNotify.destroy();
                        var timetype=_this.attr("data-timetype");
                        var reportinfo=$("#panel_CampaignReport").data("reportinfo");
                        reportinfo=reportinfo?reportinfo:{};
                        $("#btn_CampaignToday,#btn_CampaignYesterday").removeClass("");
                        _this.parent().find(".active").removeClass("active");
                        _this.addClass("active");
                        if($.isEmptyObject(reportinfo[timetype])){
                            $pageCampaignInfo.getDataCampaignReport(timetype,function(resDataJson){
                                $pageCampaignInfo.bindCampaignReport(resDataJson);
                            });
                        }else{
                            $pageCampaignInfo.bindCampaignReport(reportinfo[timetype]);
                        }
                    }else{
                        $.ExtNotify.warning("操作过于频繁，请稍后查询");
                    }
                }
            },
            bindCampaignReport:function(json){
                var p=$("#panel_CampaignReport");
                $("#campaign_viewCount").text(json.viewCount?json.viewCount:0);
                $("#campaign_clickCount").text(json.clickCount?json.clickCount:0);
                $("#campaign_cost").text(json.cost?json.cost:0);
                $("#campaign_ecpc").text(json.ecpc?json.ecpc:0);
                p.find(".counter").ExtNumeric("refresh");
                p.find(".counter").counterUp({delay: 10, time: 500});
                var arrName=[{name:"view_count",type:"bar"},{name:"click_count",type:"line"}];
                var optionChart=json.hour
                    ?$pageCampaignInfo.getCompositeChartInfo(arrName,json.hour)
                    :$pageCampaignInfo.initCompositeChartInfo(arrName,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]);
                $pageCampaignInfo.optionChartBase.legend.data=optionChart.legendData;
                $pageCampaignInfo.optionChartBase.yAxis=optionChart.axis;
                $pageCampaignInfo.optionChartBase.series=optionChart.series;
                echarts.init($("#chart_strategy")[0],"macarons").setOption($pageCampaignInfo.optionChartBase);
                setTimeout(function(){
                    $pageCampaignInfo.isReportShowIng=true;
                },1000)
            },
            initCompositeChartInfo:function(arrjson,datajson){
                var legendData=[];
                var series=[];
                var axis=[];
                $.each(arrjson,function(i,info){
                    var tempname=$pageCampaignInfo.initReportName[info.name];
                    legendData.push(tempname);
                    series.push({name:tempname, type:info.type, yAxisIndex: i, data:datajson});
                    axis.push({type: 'value', min:0,max:1,interval:1});
                });
                return {legendData:legendData,series:series,axis:axis}
            },
            getCompositeChartInfo:function(arrjson,datajson){
                ///arrjson {name:"view_count",type:"bar"}
                var legendData=[];
                var series=[];
                var axis=[];
                $.each(arrjson,function(i,info){
                    var tempname=$pageCampaignInfo.initReportName[info.name];
                    var hourJson=datajson[info.name];
                    if(hourJson){
                        legendData.push(tempname);
                        series.push({name:tempname, type:info.type, yAxisIndex: i, data:hourJson});
                        var maxratio=i==1?1.5:1;
                        var infoAxis =$pageCampaignInfo.calculateChartScale(hourJson,5,maxratio);
                        axis.push({type: 'value', min:0,max:infoAxis.max,interval:infoAxis.interval});
                    }
                });
                return {legendData:legendData,series:series,axis:axis}
            },
            calculateChartScale:function(arrData,space,maxratio){
                /*var tempMax=Math.max.apply(null, arrData);
                var tempInterval=tempMax/space;
                var interval=Math.ceil(tempInterval);
                if(tempInterval>1){
                    //避免触顶 增加高度
                    interval=interval+ parseInt(tempInterval/100)*2
                }
                var max=space*interval;
                return {max:max,interval:interval}*/
                space=parseInt(space && space>0 ?space:5);
                var tempMax=Math.max.apply(null, arrData);
                var tempInterval=maxratio>1?tempMax*maxratio/space:tempMax/space;
                var interval=Math.ceil(tempInterval);
                var max=1;
                if(tempInterval>1){
                    interval=interval+ parseInt(tempInterval/100)*2;
                }else{
                    interval=(Math.ceil(tempInterval*100)+1)/100
                }
                max=space*interval;
                return {max:max,interval:interval}
            },
            getDataCampaignReport:function(timetype,cb){
                var para={"campaign_id":'${cid}',"date_type":timetype=="today"?0:1};
                $.ExtAjax.initGet({url:"/report/campaign/single.shtml",data:para,
                    success:function(res){
                        var result=$.ExtAjax.initResult(res);
                        var dataJson={};
                        if(result.success){
                            dataJson=result.data;
                            dataJson.id=para.campaign_id;
                            dataJson.existdata=true;
                            if(dataJson.ctr){dataJson.ctrx100=(dataJson.ctr*100).toDecimal2()}
                            if(dataJson.hour){
                                if(dataJson.hour.ctr){dataJson.hour.ctrx100=$.map(dataJson.hour.ctr,function(info,i){return info?(info*100).toDecimal2():0})}
                                if(dataJson.hour.ecpc){dataJson.hour.ecpcf2=$.map(dataJson.hour.ecpc,function(info,i){return info?(info).toDecimal2():0})}
                            }
                        }else{
                            dataJson={id:para.campaign_id,existdata:false};
                        }
                        var reportinfo=$("#panel_CampaignReport").data("reportinfo");
                        reportinfo=reportinfo?reportinfo:{};
                        reportinfo[timetype]=dataJson;
                        $("#panel_CampaignReport").data("reportinfo",reportinfo);
                        if(cb){cb(dataJson)}
                    }
                });
            }
        };
        $pageCampaignInfo.init();
        ////顶部报表
        $("#btn_CampaignToday,#btn_CampaignYesterday").on('click',function (){
            $pageCampaignInfo.initCampaignReport($(this))
        });
        ///tabs 切换
        $('#tabs_list a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
            var type=$(this).data().tabpagetype;
            var text=$(this).text();
            $(".showpagetitle").text(text);
            if(type=="strategy" && $pageStrategyList.tablestrategy==null){
                $pageStrategyList.init();
            }else if(type=="creative" && $pageCreativeList.tablecreative==null){
                $pageCreativeList.init()
            }
        });

    });
</script>
