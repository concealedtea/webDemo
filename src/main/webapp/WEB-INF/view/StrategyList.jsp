<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div>
    <div>
        <a class="btn btn-default waves-effect waves-light" href="/strategy/detail.shtml?cid=${cid}">新建策略</a>
        <div class="form-inline pull-right">
            <select id="search_StrategyStatus" class="w-130 form-control select2"></select>
            <input type="text" class="form-control" id="search_StrategyKeyWord" placeholder="策略ID,策略名称">
            <button type="button" class="btn btn-default waves-effect waves-light btn-md" id="btn_SearchStrategy">搜索</button>
        </div>
    </div>
    <div class="table-custom p-t-10">
        <table class="table table-bordered" id="tb_ListStrategy" width="100%">
            <thead>
            <tr>
                <th>ID</th>
                <th>策略名称</th>
                <th>状态</th>
                <th>出价</th>
                <th>投放位置</th>
                <th>创意</th>
                <th>定向</th>
            </tr>
            </thead>
        </table>
    </div>
</div>

<script id="template_strategytable_name" type="text/x-jsrender">
    <a class="btn-operate-name" href="/strategy/detail.shtml?strategyid={{:id}}">{{:name}}</a>
    <div class="pull-right dropdown name-icon">
        <a href="javascript:;" class="details-operate" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            <span ><i class="fa fa-cog"></i></span>
        </a>
        <ul class="dropdown-menu animated flipInX w-m100" aria-labelledby="dLabel">
            <li><a class="edit-campaign" href="/strategy/detail.shtml?strategyid={{:id}}">编辑</a></li>
        </ul>
    </div>
</script>
<script id="template_strategytable_trdetails" type="text/x-jsrender">
    <div class="tr-details">
        <div class="row">
            <div class="col-xs-3" >实际花费与流量 <span class="details-operate" data-timetype="today">今日</span>/<span class="details-operate" data-timetype="yesterday">昨日</span></div>
            <div class="col-xs-1 details-operate" data-reporttype="view_count">印象数</div>
            <div class="col-xs-1 details-operate" data-reporttype="click_count">点击数</div>
            <div class="col-xs-1 details-operate" data-reporttype="ctrx100">点击率</div>
            <div class="col-xs-1 details-operate" data-reporttype="cost">花费</div>
            <div class="col-xs-2 details-operate" data-reporttype="ecpcf2">平均点击价格</div>
            <div class="col-xs-4"></div>
        </div>
        <div class="row p-t-15">{{include tmpl="#template_strategytable_trdetails_data"/}}</div>
    </div>
</script>
<script id="template_strategytable_trdetails_data" type="text/x-jsrender">
    {{if existdata}}
    <div class="col-xs-3"><div id="sparkline_{{:id}}"></div></div>
    <div class="col-xs-1 num-int">{{:viewCount}}</div>
    <div class="col-xs-1 num-int">{{:clickCount}}</div>
    <div class="col-xs-1"><span class="num-float2">{{:ctr}}</span>%</div>
    <div class="col-xs-1 num-float2">{{:cost}}</div>
    <div class="col-xs-2 num-float2">{{:ecpc}}</div>
    <div class="col-xs-4"></div>
    {{else}}暂无数据{{/if}}
</script>

<script type="text/javascript">
    var $pageStrategyList;
    $(function () {
        $pageStrategyList = {
            tablestrategy:null,
            initOption: { id: "-99", text: "全部" },
            initStatusByStrategy: [{ id: "1", text: "启用",showid:["6"] },{ id: "6", text: "暂停",showid:["0","1"] }],
            init: function () {
                this.bindSearchStatusByStrategy(function(){
                    $pageStrategyList.bindListStrategy();
                });
            },
            bindSearchStatusByStrategy: function (cb) {
                $.ExtAjax.initGet({url:"/strategy/status.shtml",data:{},
                    success:function(res){
                        $pageStrategyList.listStrategyStatus = [];
                        for (var i = 0; i < res.length; i++) {
                            var info=res[i];
                            $pageStrategyList.listStrategyStatus.push({id:info.id,text:info.name});
                        }
                        $pageStrategyList.listStrategyStatus.unshift($pageStrategyList.initOption);
                        $("#search_StrategyStatus").ExtSelect2({placeholder:"-状态-",select2Info:{data:$pageStrategyList.listStrategyStatus}});
                        if (cb) {cb()}
                    }
                });
            },
            bindListStrategy: function () {
                var self=this;
                self.tablestrategy=$("#tb_ListStrategy").ExtDataTable({
                    columns: [
                        { "data": "sid" ,sClass:"w-90","bSortable": false},
                        { "data": "sname",sClass:"p-r-30","bSortable": false},
                        { "data": "status",sClass:"w-80 text-center p-r-30","bSortable": false},
                        { "data": "bid",sClass:"w-60 text-right","bSortable": false},
                        { "data": "bid",sClass:"w-250","bSortable": false},
                        { "data": "creative",sClass:"w-250","bSortable": false},
                        { "data": null,sClass:"w-150 text-center","bSortable": false}
                    ],
                    infoExt:{name:"策略列表",url:"/strategy/list.shtml",ajaxDataPara:function(){
                        var para={};
                        var keyword = $.trim($("#search_StrategyKeyWord").val());
                        if (!$.ExtCommon.isCheckEmpty(keyword)) {para.search_keyword = keyword;}
                        var status=$("#search_StrategyStatus").val();
                        if(!$.ExtCommon.isCheckEmpty(status) && status.toString()!="-1"){para.search_status=status;}
                        para.campaign_id = ${cid};
                        return para;
                    }},
                    createdRow: function (row, data, index) {
                        $('td', row).eq(0).html('<span>'+data.sid+'</span><div class="pull-right"><a class="details-control details-operate"  href="javascript:;"><i class="fa updown"></i></a> </div>');
                        $('td', row).eq(1).html($("#template_strategytable_name").render({name:data.sname,id:data.sid}));
                        var tempjsonStatus=self.bindStrategyStatusOperation(data.status);
                        var tempStatusMsg=data.status == 4?'<i class="fa fa-warning icon-verify  pull-right name-icon status-msg-warning" data-toggle="tooltip" data-container="body"  data-placement="right" title="'+data.audit_msg+'"></i>':"";
                        if(tempjsonStatus.list.length>0){
                            $('td', row).eq(2).html('<a class="editable editable-click editable-init"  data-operatetype="status" href="javascript:;">'+tempjsonStatus.statusname+'</a>'+tempStatusMsg);
                            $('td', row).eq(2).find(".editable").data({list:tempjsonStatus.list,strategyid:data.sid});
                        }else{
                            $('td', row).eq(2).html(tempjsonStatus.statusname+tempStatusMsg)
                        }
                        /*if(data.status == 4){
                            $('td', row).eq(2).html("审核不通过"+'<i class="fa fa-warning icon-verify" data-toggle="tooltip" data-placement="right" title="'+data.audit_msg+'"></i>');
                        }*/
                        $('td', row).eq(3).html('<a class="editable editable-click editable-init" data-operatetype="price"  href="javascript:;"><span class="num-float2">'+data.bid+'</span></a>');
                        $('td', row).eq(3).find(".editable").data({strategyid:data.sid});
                        var strPlatform=data.strategy_platform? (data.strategy_platform.name+" - "+ data.ad_space_position.description):"";
                        $('td', row).eq(4).html('<div class="ellipsis" title="'+strPlatform+'">'+strPlatform+'</div>');
                        var strCreative=data.creative?(data.creative.id+(data.creative.creative_name?(" - "+ data.creative.creative_name):"")):"";
                        $('td', row).eq(5).html('<div class="ellipsis" title="'+strCreative+'">'+strCreative+'</div>');
                        var targetJson={};
                        if(data.target && data.target.targetValue && data.target.targetValue.length>0){
                            targetJson=$.parseJSON(data.target.targetValue);
                            self.getTargeHtml($('td', row).eq(6),targetJson);
                            $('td', row).eq(6).html($pageStrategyList.getTargeHtml($('td', row).eq(6),targetJson));
                        }
                    },
                    drawCallback: function () {
                        $.ExtNumeric.init();
                        self.initStrategyXEditable();
                        $("#tb_ListStrategy").find(".inittooltip").each(function(){
                            var _this=$(this);
                            var strHtml=_this.data("showtitle");
                            _this.removeClass("inittooltip");
                            _this.tooltip({"placement": "left", "html": true, "title": strHtml})
                        });
                        $("#tb_ListStrategy").find(".status-msg-warning").tooltip();
                    }
                });
            },
            refreshStrategyList: function () {
                $("#tb_ListStrategy").ExtDataTable("refresh");
            },
            getTargeHtml:function(obj,json){
                var div=$("<div>");
                if(json.hours){
                    var weekInfo=$.ExtTarge.time.convertInitWeekTimeValue(json.hours);
                    var hoursJsonValue=$.ExtTarge.time.convertSelectWeekTime(weekInfo);
                    var  hoursHtml=hoursJsonValue.selecttext.length>0?$.map(hoursJsonValue.selecttext, function (info, i) {return "<p>" + info + "</p>";}).join(""):"";
                    var aWeek=$('<a class="btn-operate-name targe targe-time inittooltip" data-toggle="tooltip">时间</a>').data({"datainfo":hoursJsonValue,"showtitle":hoursHtml});
                    div.append(aWeek);
                }
                if(json.area){
                    var areaJsonValue=$.ExtTarge.convertJsonToList(json.area);
                    var areaHtml =areaJsonValue.length>0? $.map(areaJsonValue, function (info, i) {return "<span>"+info.name+"</span>";}).join(","):"";
                    var aArea=$('<a class="btn-operate-name targe targe-area inittooltip" data-toggle="tooltip">地域</a>').data({"datainfo":areaJsonValue,"showtitle":"<p>"+areaHtml+"</p>"});
                    div.append(aArea);
                }
                var audienceJson={};
                if(json.gender){audienceJson.gender=$.ExtTarge.convertJsonToList(json.gender);}
                if(json.age){audienceJson.age=json.age}
                if(json.education){audienceJson.education=$.ExtTarge.convertJsonToList(json.education);}
                if(json.marriage){audienceJson.marriage=$.ExtTarge.convertJsonToList(json.marriage);}
                if(!$.isEmptyObject(audienceJson)){
                    var audienceHtml="";
                    if(audienceJson.gender)audienceHtml+="<p>"+$.ExtTarge.getValueText("gender", "性别", audienceJson)+"</p>";
                    if(audienceJson.age)audienceHtml+="<p>年龄：" + audienceJson.age + "岁</p>";
                    if(audienceJson.education)audienceHtml+="<p>"+$.ExtTarge.getValueText("education", "学历", audienceJson)+"</p>";
                    if(audienceJson.marriage)audienceHtml+="<p>"+$.ExtTarge.getValueText("marriage", "婚恋", audienceJson)+"</p>";
                    var aAudience=$('<a class="btn-operate-name targe targe-audience inittooltip" data-toggle="tooltip">人口</a>').data({"datainfo":audienceJson,"showtitle":"<p>"+audienceHtml+"</p>"});
                    div.append(aAudience);
                }
                var deviceJson={};
                if(json.os){deviceJson.os=$.ExtTarge.convertJsonToList(json.os);}
                if(json.connection){deviceJson.connection=$.ExtTarge.convertJsonToList(json.connection);}
                if(json.telcom){deviceJson.telcom=$.ExtTarge.convertJsonToList(json.telcom);}
                if(!$.isEmptyObject(deviceJson)){
                    var deviceHtml="";
                    if(deviceJson.os)deviceHtml+="<p>"+$.ExtTarge.getValueText("os", "系统", deviceJson)+"</p>";
                    if(deviceJson.connection)deviceHtml+="<p>"+$.ExtTarge.getValueText("connection", "联网", deviceJson)+"</p>";
                    if(deviceJson.telcom)deviceHtml+="<p>"+$.ExtTarge.getValueText("telcom", "运营商", deviceJson)+"</p>";
                    var aDevice=$('<a class="btn-operate-name targe targe-device inittooltip" data-toggle="tooltip">设备</a>').data({"datainfo":deviceJson,"showtitle":"<p>"+deviceHtml+"</p>"});
                    div.append(aDevice);
                }
                if(json.interest){
                    var interestJsonValue=$.ExtTarge.convertJsonToList(json.interest);
                    var interestHtml =interestJsonValue.length>0? $.map(interestJsonValue, function (info, i) {return "<span>"+info.name+"</span>";}).join(","):"";
                    var aInterest=$('<a class="btn-operate-name targe targe-interest inittooltip" data-toggle="tooltip">兴趣</a>').data({"datainfo":interestJsonValue,"showtitle":"<p>"+interestHtml+"</p>"});
                    div.append(aInterest);
                }
                if(json.keyword){
                    var aKeyword=$('<a class="btn-operate-name targe targe-keyword inittooltip" data-toggle="tooltip">关键字</a>').data({"datainfo":json.keyword,"showtitle":"<p>"+json.keyword+"</p>"});
                    div.append(aKeyword);
                }
                return div;
            },
            bindStrategyStatusOperation:function(status){
                var tempstatus=[];
                var statusname="";
                status=status==null?"":status.toString();
                for(var i=0;i<$pageStrategyList.listStrategyStatus.length;i++){
                    var info=$pageStrategyList.listStrategyStatus[i];
                    if(info.id.toString()==status){statusname=info.text;}

                }
                for(var j=0;j<$pageStrategyList.initStatusByStrategy.length;j++){
                    var infoOperation=$pageStrategyList.initStatusByStrategy[j];
                    if(infoOperation.showid.indexOf(status)>-1){
                        tempstatus.push({value:infoOperation.id,text:infoOperation.text});
                    }
                }
                return  {statusname:statusname,status:status,list:tempstatus}
            },
            initStrategyXEditable:function() {
                $("#tb_ListStrategy .editable-click.editable-init").each(function () {
                    var _this = $(this);
                    _this.removeClass("editable-init");
                    var _thisData = _this.data();
                    var row = $("#tb_ListStrategy").ExtDataTable().getRowInfo(_this.closest('tr'));
                    var operatetype = _this.attr("data-operatetype");
                    if (operatetype == "price") {
                        _this.ExtXEditable({
                            type: "text",
                            url:  "/strategy/edit.shtml",
                            params: function (params) {
                                var newParams = {id: _thisData.strategyid};
                                newParams.bid = $.trim(params.value).removesplit();
                                return JSON.stringify(newParams);
                            },
                            initControlOther: function () {
                                $(".editable-container :text").ExtNumeric();
                            },
                            successCallback: function (res, newValue) {
                                row.data().price = newValue.removesplit();
                            }
                        });
                    } else if (operatetype == "status") {
                        _this.ExtXEditable({
                            source : _thisData.list,
                            type: "select",
                            url: "/strategy/edit.shtml",
                            params: function (params) {
                                var newParams = {id: _thisData.strategyid};
                                newParams.status = $.trim(params.value);
                                return JSON.stringify(newParams);
                            },
                            initControlOther: function () {
                                _this.siblings(".editable-container").find("select").ExtSelect2({select2Info:{initvalue:_thisData.list[0].value}});
                            },
                            successCallback: function (res, newValue) {
                                var tempjsonStatus = $pageStrategyList.bindStrategyStatusOperation(result.data.status);
                                _this.text(tempjsonStatus.statusname).data("list", tempjsonStatus.list);
                                _this.ExtXEditable().refresh(tempjsonStatus.list);
                                row.data().status = result.data.status;
                            }
                        });
                    }
                });
            },
            initSparkLine:function(id,arrJson){
                $(id).sparkline(arrJson, {type: 'bar', height: '30', barWidth: '5', barSpacing: '3', barColor: '#5fbeaa'});
            }
        };
        ////策略展开详情
        $('#tb_ListStrategy').on('click', '.details-control', function () {
            var tr = $(this).closest('tr');
            var row = $("#tb_ListStrategy").ExtDataTable().getRowInfo(tr);
            if ( row.child.isShown()) {
                $("#tb_ListStrategy").ExtDataTable().hideTrDetailHtml(tr,row);
            }
            else {//默认显示今天
                var tempRowData=row.data();
                if(typeof row.data().listReport=="undefined"){
                    row.data().listReport=[];
                    var para={strategy_id:tempRowData.sid,date_type:0};
                    getDataStrategyTrDetail(para,function(resDataJson){
                        bindTrDetailHtmlByStrategy("today",tr,row,resDataJson);
                    });
                }else{
                    var tempDataJson=null;
                    for(var i=0;i<tempRowData.listReport.length;i++){
                        var info=tempRowData.listReport[i];
                        if(info.active){tempDataJson=info;}
                    }
                    if(tempDataJson!=null){
                        bindTrDetailHtmlByStrategy(tempDataJson.name,tr,row,tempDataJson.data);
                    }
                }
            }
        });
        function  bindTrDetailHtmlByStrategy(timetype,tr,row,dataJson){
            var temphtml=$("#template_strategytable_trdetails").render(dataJson);
            $("#tb_ListStrategy").ExtDataTable().bindTrDetailHtml(tr,row,temphtml,function(){
                initShowTrDetail(tr.next(),timetype,dataJson);
            },function(){
                setTrDetailDataByStrategy(timetype,row,dataJson);
            });
        }
        //// 策略详情数据查询
        function  getDataStrategyTrDetail(para,cb){
            $.ExtAjax.initGet({url:"/report/strategy/single.shtml",data:para,
                success:function(res){
                    var result=$.ExtAjax.initResult(res);
                    if(result.success){
                        var dataJson=result.data;
                        dataJson.id=para.strategy_id;
                        dataJson.existdata=true;
                        if(dataJson.ctr){dataJson.ctrx100=(dataJson.ctr).toDecimal2()}
                        if(dataJson.hour){
                            if(dataJson.hour.ctr){dataJson.hour.ctrx100=$.map(dataJson.hour.ctr,function(info,i){return info?(info).toDecimal2():0})}
                            if(dataJson.hour.ecpc){dataJson.hour.ecpcf2=$.map(dataJson.hour.ecpc,function(info,i){return info?(info).toDecimal2():0})}
                        }
                        if(cb){cb(dataJson)}
                    }else{
                        if(cb){cb({id:para.strategy_id,existdata:false})}
                    }
                }
            });
        }
        function initShowTrDetail(nextTr,timetype,dataJson){
            if(dataJson.existdata) {$pageStrategyList.initSparkLine("#sparkline_" + dataJson.id, dataJson.hour["view_count"]);}
            var tempTr = nextTr.find(".tr-details");
            tempTr.find(".details-operate").removeClass("active");
            tempTr.find("[data-timetype='" + timetype + "'],[data-reporttype='view_count']").addClass("active");
            $.ExtNumeric.init();
        }
        function setTrDetailDataByStrategy(timetype,row,dataJson){
            var isexist=false;
            if(row.data().listReport.length>0){
                for(var i=0;i<row.data().listReport.length;i++){
                    var info=row.data().listReport[i];
                    info.active=false;
                    if(info.name==timetype){
                        info.active=true;
                        isexist=true;
                    }
                }
            }
            if(!isexist){
                row.data().listReport.push({name:timetype,data:dataJson,active:true});
            }
        }
        ////策略 详情 今日/昨日切换
        $('#tb_ListStrategy').on('click', '.tr-details .details-operate[data-timetype]', function () {
            var _this=$(this);
            if(!_this.hasClass("active")){
                var tr = _this.closest('tr').prev();
                var row =$("#tb_ListStrategy").ExtDataTable().getRowInfo(tr);
                var tempRowData=row.data();
                var timetype=_this.attr("data-timetype");
                var tempDataJson=null;
                for(var i=0;i<tempRowData.listReport.length;i++){
                    var info=tempRowData.listReport[i];
                    if(info.name==timetype){tempDataJson=info;}
                }
                if(tempDataJson==null){
                    var para={strategy_id:tempRowData.sid,date_type:timetype=="today"?0:1};
                    getDataStrategyTrDetail(para,function(resDataJson){
                        bindTrDetailStrategyTimeChange(_this,row,timetype,resDataJson);
                    });
                }else{
                    bindTrDetailStrategyTimeChange(_this,row,timetype,tempDataJson.data)
                }
            }
        });
        function bindTrDetailStrategyTimeChange(obj,row,timetype,dataJson){
            var temphtml=$("#template_strategytable_trdetails_data").render(dataJson);
            var tr=obj.closest(".row");
            tr.next().html(temphtml);
            initShowTrDetail(tr.closest(".tr-details").parent(),timetype,dataJson);
            setTrDetailDataByStrategy(timetype,row,dataJson);
        }
        ////策略 详情 印象数/点击数等数据 切换
        $('#tb_ListStrategy').on('click', '.tr-details .details-operate[data-reporttype]', function () {
            var _this=$(this);
            if(!_this.hasClass("active")){
                var tr = _this.closest('tr');
                var row =$("#tb_ListStrategy").ExtDataTable().getRowInfo(tr.prev());
                var tempRowData=row.data();
                var timetype=tr.find(".details-operate.active[data-timetype]").attr("data-timetype");
                var reporttype=_this.attr("data-reporttype");
                var tempDataJson=null;
                for(var i=0;i<tempRowData.listReport.length;i++){
                    var info=tempRowData.listReport[i];
                    if(info.name==timetype){
                        tempDataJson=info;
                    }
                }
                if(tempDataJson!=null && tempDataJson.data.existdata){$pageStrategyList.initSparkLine("#sparkline_"+tempDataJson.data.id,tempDataJson.data.hour[reporttype]);}
                _this.parent().find(".details-operate[data-reporttype]").removeClass("active");
                _this.addClass("active")
            }
        });
        ///策略搜索
        $("#btn_SearchStrategy").on('click',  function () {
            $pageStrategyList.refreshStrategyList()
        });
        $("#search_StrategyKeyWord").on("keydown",function(event){
            if(event.keyCode == 13) {
                $pageStrategyList.refreshStrategyList();
            }
        });
    });
</script>
