<%@ page  %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div data-menutype="index">
    <div class="page-index">
        <div class="row">
            <div class="col-sm-12">
                <h4 class="page-title pull-left">首页</h4>
                <div class="pull-right">
                    <select id="search_time" class="w-110 form-control select2">
                        <option value="0">今天</option>
                        <option value="1">昨天</option>
                        <option value="2">过去7天</option>
                        <option value="3">过去30天</option>
                    </select>
                </div>
            </div>
        </div>
        <div class="row" id="home_TopTotal">
            <div class="col-md-6 col-lg-3">
                <div class="widget-bg-color-icon card-box">
                    <div class="bg-icon bg-icon-info pull-left">
                        <i class="fa fa-eye text-info"></i>
                    </div>
                    <div class="text-right">
                        <h3 class="text-dark"><b class="counter num-int" id="home_viewCount">0</b></h3>
                        <p class="text-muted">展示</p>
                    </div>
                    <div class="clearfix"></div>
                </div>
            </div>

            <div class="col-md-6 col-lg-3">
                <div class="widget-bg-color-icon card-box">
                    <div class="bg-icon bg-icon-pink pull-left">
                        <i class="fa fa-hand-o-up text-pink"></i>
                    </div>
                    <div class="text-right">
                        <h3 class="text-dark"><b class="counter num-int" id="home_clickCount">0</b></h3>
                        <p class="text-muted">点击</p>
                    </div>
                    <div class="clearfix"></div>
                </div>
            </div>

            <div class="col-md-6 col-lg-3">
                <div class="widget-bg-color-icon card-box">
                    <div class="bg-icon bg-icon-purple pull-left">
                        <i class="fa fa-signal  text-purple"></i>
                    </div>
                    <div class="text-right">
                        <h3 class="text-dark"><b class="counter num-float2" id="home_ctr">0</b>%</h3>
                        <p class="text-muted">点击率</p>
                    </div>
                    <div class="clearfix"></div>
                </div>
            </div>

            <div class="col-md-6 col-lg-3">
                <div class="widget-bg-color-icon card-box">
                    <div class="bg-icon bg-icon-success pull-left">
                        <i class="fa fa-jpy text-success"></i>
                    </div>
                    <div class="text-right">
                        <h3 class="text-dark"><b class="counter num-float2" id="home_cost">0</b></h3>
                        <p class="text-muted">花费</p>
                    </div>
                    <div class="clearfix"></div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-12">
                <div class="card-box">
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="h-40">
                                <div id="chart_btns" class="button-list pull-right" style="">
                                    <button class="btn btn-default btn-frame-w1 waves-effect text-right" type="button"value="view_count">展现次数</button>
                                    <button class="btn btn-default btn-frame-w1 waves-effect text-right" type="button" value="click_count">点击次数</button>
                                    <button class="btn btn-white btn-frame-w1 waves-effect text-right" type="button" value="ctr">点击率</button>
                                    <button class="btn btn-white btn-frame-w1 waves-effect text-right" type="button"  value="ecpc">平均点击价格</button>
                                    <button class="btn btn-white btn-frame-w1 waves-effect text-right" type="button" value="ecpm">千次展现价格</button>
                                    <button class="btn btn-white btn-frame-w1 waves-effect text-right" type="button" value="cost">消耗</button>
                                </div>
                            </div>
                            <div class="datajsonload">
                                <div id="chart_home" style="height:400px;"></div>
                            </div>
                            <div class="table-custom p-t-10">
                                <table class="table table-bordered table-bordered-topbottom dataTable-sorting" id="tb_ListTimeChart" width="100%">
                                    <thead>
                                    <tr>
                                        <th>时间</th>
                                        <th>展现次数</th>
                                        <th>点击次数</th>
                                        <th>点击率</th>
                                        <th>平均点击价格</th>
                                        <th>千次展现价格</th>
                                        <th>消耗</th>
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
    <!-- Audience -->
    <div class="row">
        <div class="col-md-6">
            <div class="widget-bg-color-icon card-box">
                <div>性别展示分布</div>
                <div class="position-r">
                    <div id="chart_gender" style="height: 400px;"></div>
                    <div class="datajson-load h-line-400"><div class="data-nothing"><i class="fa fa-eye-slash"></i>暂无数据</div></div>
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <div class="widget-bg-color-icon card-box">
                <div>年龄展示分布</div>
                <div class="position-r">
                    <div id="chart_age"  style="height: 400px;"></div>
                    <div class="datajson-load h-line-400"><div class="data-nothing"><i class="fa fa-eye-slash"></i>暂无数据</div></div>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="widget-bg-color-icon card-box">
                <div>省级地域展示分布</div>
                <div id="chart_arear"  style="height:600px"></div>
                <div class="datajson-load h-line-600"><div class="data-nothing"><i class="fa fa-eye-slash"></i>暂无数据</div></div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="widget-bg-color-icon card-box">
               <div class="form-inline pull-right h-40">
                   <div class="radio radio-custom radio-inline">
                       <input type="radio" id="rb_ChartArea" name="rbChart" checked value="area">
                       <label for="rb_ChartArea">地域</label>
                   </div>
                   <div class="radio radio-custom radio-inline">
                       <input type="radio" id="rb_ChartGender" name="rbChart" value="gender">
                       <label for="rb_ChartGender">性别</label>
                   </div>
                   <div class="radio radio-custom radio-inline">
                       <input type="radio" id="rb_ChartAge" name="rbChart" value="age">
                       <label for="rb_ChartAge">年龄</label>
                   </div>
               </div>
                <div class="table-custom p-t-10">
                    <table class="table table-bordered table-bordered-topbottom dataTable-sorting" id="tb_ListOtherChart" width="100%">
                        <thead>
                            <tr>
                                <th id="th_MenuName">地域</th>
                                <th>展现次数</th>
                                <th>点击次数</th>
                                <th>点击率</th>
                                <th>平均点击价格</th>
                                <th>千次展现价格</th>
                                <th>消耗</th>
                            </tr>
                        </thead>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(function () {
        var $pageIndex = {
            tableReport:null,
            initChartDefault:{chartType:"line",legendName:{ "view_count":"展现次数","click_count": "点击次数","cost":"消耗","ecpm":"千次展现价格", "ecpc": "平均点击价格","ctr":"点击率" }},
            infoChartTabsQueue:{count:2,list:["view_count","click_count"]},
            init: function () {
                $("#search_time").ExtSelect2({placeholder:"-时间-",select2Info:{initvalue:"2"}});
                this.bindPageData();
            },
            bindPageData:function(){
                var self=this;
                var pageDefault=self.getPageDefaultByHtml();
                var dateType=$("#search_time").val();
                var url="/report/index.shtml";
                $.ExtAjax.initGet({
                    url: url, data: {date_type:dateType},
                    loadinfo:{place:"box",boxId:"#chart_home",height:400,type:"circle",dataListName:"list"},
                    successcallback: function (result) {
                        $.ExtReport.initBind(pageDefault, result, function (resJson) {
                            self.pageData=resJson;
                            self.pageData.infoChartTabsQueue=self.infoChartTabsQueue;
                            self.pageData.tabledata=resJson.original.list.length==0?[]:resJson.original.list;
                            self.bindTopTotal(resJson.original.total);
                            self.bindChartTwoVeidoo();
                            self.bindTimeTable({data:self.pageData.tabledata,orderindex:pageDefault.table.orderindex});
                            self.bindChartBase("gender",resJson.original.gender);
                            self.bindChartBase("age",resJson.original.age);
                            self.bindCharArea(resJson.original.area);
                            var dataTypeOther = $(":radio[name='rbChart']:checked").val();
                            self.bindOtherTable({data:resJson.original[dataTypeOther],orderindex:1})
                        });
                    }
                });
            },
            getPageDefaultByHtml:function(){
                var self=this;
                var dateType=$("#search_time").val();
                var type=dateType=="0" || dateType=="1"?"hour":"day";
                var chartSeriesType="line";
                var chartxAxisLen=type=="hour"?-1:10;
                var chartXother=false;
                var chartEnddata=type=="day";
                var tableOrderIndex=0;
                return {type:type,table:{orderindex:parseInt(tableOrderIndex)},chart:{seriesType:chartSeriesType,xAxisLen:parseInt(chartxAxisLen),xOther:chartXother,endData:chartEnddata,keyword:"key"}};
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
            bindTopTotal:function(json){
                var objcounter=$("#home_TopTotal").find(".counter");
                $("#home_viewCount").text(json.view_count?json.view_count:0);
                $("#home_clickCount").text(json.click_count?json.click_count:0);
                $("#home_cost").text(json.cost?json.cost:0);
                $("#home_ctr").text(json.ctr?json.ctr:0);
                objcounter.ExtNumeric("refresh");
                objcounter.counterUp({delay: 15, time: 500});
            },
            bindChartBase:function(type,list){
                var objChart=$("#chart_"+type);
                if(list.length>0){
                    var optionBase={};
                    var arrOption=[];
                    switch(type){
                        case "gender":
                            optionBase={tooltip : {trigger: 'item'},legend: {data: []},series : [{name: '展示',type: 'pie',radius : '55%',data:[]}]};
                            arrOption=$.ExtReport.getChartListToOptionInfo(list,"view_count");
                            break;
                        case "age":
                            optionBase={tooltip : {trigger: 'item'},legend: {data: []},series : [{name: '展示',type: 'pie',radius : '55%',data:[]}]};
                            arrOption=$.ExtReport.getChartListToOptionInfo(list,"view_count");
                            break;
                    }
                    optionBase.legend.data=arrOption.legend;
                    optionBase.series[0].data=arrOption.seriesData;
                    objChart.next().hide();
                    echarts.init(objChart[0],"macarons").setOption(optionBase);
                }else{
                    objChart.next(".datajson-load").show()
                }
            },
            bindCharArea:function(list){
                var optionBase={
                    tooltip: {trigger: 'item'},
                    legend: {data:['展示']},
                    visualMap: {min: 0,max: 1,left: 'left',top: 'bottom',text: ['高','低'],calculable: true},
                    series: [{name: '展示',type: 'map',mapType: 'china',roam: false,label: {normal: {show: true},emphasis: {show: true}},data:[]}]
                };
                var arrOption=$.ExtReport.getChartListToOptionInfo(list,"view_count");
                optionBase.legend.data=arrOption.legend;
                optionBase.series[0].data=arrOption.seriesData;
                //optionBase.visualMap.max=arrOption.maxValue>0?(arrOption.sortArr.length>5?arrOption.sortArr[4]:arrOption.sortArr[arrOption.sortArr.length-1]):1;
                optionBase.visualMap.max=arrOption.maxValue>0?arrOption.sortArr[0]:1;
                console.log(arrOption);
                echarts.init($("#chart_arear")[0],"macarons").setOption(optionBase);
            },
            bindTimeTable: function (tableJson) {
                var self=this;
                if(self.tableTimeReport) $("#tb_ListTimeChart").ExtDataTable("destroy");
                self.tableTimeReport=$("#tb_ListTimeChart").ExtDataTable({
                    paging: false,
                    serverSide:false,
                    bInfo:false,
                    ordering:true,
                    order: [[ tableJson.orderindex, "desc" ]],
                    columns: [
                        { "data": "key" },
                        { "data": "view_count",sClass:"num-int text-right td-sorting"},
                        { "data": "click_count",sClass:"num-int text-right td-sorting"},
                        { "data": "ctr",sClass:"num-float2 text-right td-sorting"},
                        { "data": "ecpc",sClass:"num-float2 text-right td-sorting"},
                        { "data": "ecpm",sClass:"num-float2 text-right td-sorting"},
                        { "data": "cost",sClass:"num-float2 text-right td-sorting"}
                    ],
                    infoExt:{data:tableJson.data},
                    createdRow: function (row, data, index) {
                        var keyvalue="";
                        switch(self.pageData.para.type){
                            case "hour":
                                keyvalue=$.ExtTarge.time.getHourInterval(parseInt(data.key));
                                break;
                        }
                        if(keyvalue.length>0)  $('td', row).eq(0).html(keyvalue);
                        $('td', row).eq(3).html(data.ctr+"%")
                    },
                    drawCallback: function () {
                        if(self.pageData.original.list.length>0) {
                            var totalInfo=self.pageData.original.total;
                            var trTotal = '<tr role="row" class="tr-total"><td class="sorting_1">总计</td><td class=" num-int  text-right td-sorting">' + totalInfo.view_count + '</td><td class=" num-int text-right td-sorting">' + totalInfo.click_count + '</td><td class=" num-float2 text-right td-sorting">' + totalInfo.ctr + '%</td><td class=" num-float2 text-right td-sorting">' + totalInfo.ecpc + '</td><td class=" num-float2 text-right td-sorting">' + totalInfo.ecpm + '</td><td class=" num-float2 text-right td-sorting">' + totalInfo.cost + '</td></tr>'
                            $("#tb_ListTimeChart").find("tbody").prepend(trTotal);
                        }
                        $.ExtNumeric.init();
                    }
                });
            },
            bindOtherTable: function (tableJson) {
                var self=this;
                if(self.tableOtherReport) $("#tb_ListOtherChart").ExtDataTable("destroy");
                self.tableOtherReport=$("#tb_ListOtherChart").ExtDataTable({
                    paging: false,
                    serverSide:false,
                    bInfo:false,
                    ordering:true,
                    order: [[ tableJson.orderindex, "desc" ]],
                    columns: [
                        { "data": "key" },
                        { "data": "view_count",sClass:"num-int text-right td-sorting"},
                        { "data": "click_count",sClass:"num-int text-right td-sorting"},
                        { "data": "ctr",sClass:"num-float2 text-right td-sorting"},
                        { "data": "ecpc",sClass:"num-float2 text-right td-sorting"},
                        { "data": "ecpm",sClass:"num-float2 text-right td-sorting"},
                        { "data": "cost",sClass:"num-float2 text-right td-sorting"}
                    ],
                    infoExt:{data:tableJson.data},
                    createdRow: function (row, data, index) {
                        $('td', row).eq(3).html(data.ctr+"%")
                    },
                    drawCallback: function () {
                        $.ExtNumeric.init();
                    }
                });
            }
        };
        $pageIndex.init();
        $("#chart_btns .btn").on("click",function () {
            var _this=$(this);
            var value=_this.val();
            var arrValue=$.ExtReport.getChartTabsQueue(value,$pageIndex.pageData);
            if(arrValue.length>0){
                var chartbtns=$("#chart_btns");
                chartbtns.find(".btn-default").toggleClass("btn-white btn-default");
                $.each(arrValue,function(i,info){
                    chartbtns.find("button[value='"+info+"']").toggleClass("btn-white btn-default");
                });
                $pageIndex.infoChartTabsQueue.list=arrValue;
                $pageIndex.bindChartTwoVeidoo();
            }else{
                $.ExtNotify.error("必须保留一个!");
            }
        });
        $("#search_time").on("change",function () {
            $pageIndex.bindPageData();
        });
        $(":radio[name='rbChart']").on("change", function () {
            var _this=$(this);
            var value=_this.val();
            var title= $.trim(_this.next().text());
            $("#th_MenuName").text(title);
            $pageIndex.bindOtherTable({data:$pageIndex.pageData.original[value],orderindex:1})
        });
    });
</script>