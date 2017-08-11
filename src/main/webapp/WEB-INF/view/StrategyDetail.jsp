<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="page-strategydetail" data-menutype="campaign">
    <div class="row">
        <div class="col-sm-12">
            <h4 class="page-title">
                <span class="page-operate-title">新建策略</span>
                <ol class="breadcrumb">
                    <jsp:include page="HomeName.jsp"/>
                    <li>
                        <a href="/campaign/view.shtml">订单管理</a>
                    </li>
                    <li>
                        <a href="/strategy/view.shtml?tabName=strategy&cid=${cid}">${campaignName}</a>
                    </li>
                    <li class="active page-operate-title">新建策略</li>
                </ol>
            </h4>
        </div>
    </div>
    <div class="row">
        <form  id="form_StrategyDetail" data-parsley-validate="">
            <div class="portlet portlet-border">
                <div class="portlet-heading portlet-default">
                    <h3 class="portlet-title text-dark">基本信息</h3>
                    <div class="portlet-widgets"></div>
                    <div class="clearfix"></div>
                </div>
                <div class="panel-collapse collapse in" id="strategy-base">
                    <div class="portlet-body">
                        <div class="form-horizontal">
                            <div class="form-group">
                                <label class="col-md-2 control-label">策略名称</label>
                                <div class="col-md-10">
                                    <span class="box-must">·</span><input class="form-control box-input" id="txt_SName" type="text" placeholder="策略名称" value="${strategy_name}" name="SName" required="" data-parsley-maxlength="120" data-parsley-illegal-value="">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 control-label">投放时间</label>
                                <div class="col-md-10">
                                    <span class="box-must">·</span>
                                    <div class="d-inline-block w-210">
                                        <input class="form-control w-186" id="txt_StartTime" type="text" placeholder="开始时间" value="${start}" name="StartTime" readonly="readonly" required=""  data-parsley-before-endtime="#txt_EndTime">
                                    </div>
                                    <div class="d-inline-block">
                                        <input  class="form-control d-inline-block w-186" id="txt_EndTime" type="text" placeholder="结束时间" value="${end}" name="EndTime"  readonly="readonly" required="" data-parsley-after-starttime="#txt_StartTime" data-parsley-after-now="">
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 control-label">备注</label>
                                <div class="col-md-10">
                                    <textarea  class="form-control text-area box-input" id="txt_Description" name="Description" rows="4" placeholder="备注">${description}</textarea>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="portlet portlet-border">
                <div class="portlet-heading portlet-default">
                    <h3 class="portlet-title text-dark">购买设置</h3>
                    <div class="portlet-widgets">
                        <a href="#strategy_creative" data-toggle="collapse"><i class="fa fa-minus-round"></i></a>
                    </div>
                    <div class="clearfix"></div>
                </div>
                <div class="panel-collapse collapse in" id="strategy_creative">
                    <div class="portlet-body">
                        <div class="form-horizontal">
                            <div class="form-group">
                                <label class="col-md-2 control-label">创意设置</label>
                                <div class="col-md-10 datajsonload">
                                    <table class="table table-bordered dataTable" id="tb_ListCreative" width="100%">
                                        <thead>
                                            <tr>
                                                <th></th>
                                                <th>创意名称</th>
                                                <th>广告形式</th>
                                                <th>投放位置</th>
                                                <th class="text-center">操作</th>
                                            </tr>
                                        </thead>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="portlet portlet-border">
                <div class="portlet-heading portlet-default">
                    <h3 class="portlet-title text-dark">定向设置</h3>
                    <div class="portlet-widgets">
                        <a href="#strategy_target" data-toggle="collapse"><i class="fa fa-minus-round"></i></a>
                    </div>
                    <div class="clearfix"></div>
                </div>
                <div class="panel-collapse collapse in" id="strategy_target">
                    <div class="portlet-body">
                        <div class="form-horizontal">
                            <div class="form-group">
                                <label class="col-md-2 control-label">定向类型</label>
                                <div class="col-md-10">
                                    <button id="btn_AddTimeTarget" class="btn btn-white waves-effect" type="button"><i class="fa fa-plus m-r-5"></i><span>时间定向</span></button>
                                    <button id="btn_AddGeoTarget" class="btn btn-white waves-effect" type="button"><i class="fa fa-plus m-r-5"></i><span>地域定向</span></button>
                                    <button id="btn_AddAudienceTarget" class="btn btn-white waves-effect" type="button"><i class="fa fa-plus m-r-5"></i><span>人口属性定向</span></button>
                                    <button id="btn_AddDeviceTarget" class="btn btn-white waves-effect" type="button"><i class="fa fa-plus m-r-5"></i><span>移动设备定向</span></button>
                                    <button id="btn_AddInterestTarget" class="btn btn-white waves-effect" type="button"><i class="fa fa-plus m-r-5"></i><span>商业兴趣定向</span></button>
                                    <button id="btn_AddKeyWordTarget" class="btn btn-white waves-effect" type="button"><i class="fa fa-plus m-r-5"></i><span>关键字定向</span></button>
                                </div>
                            </div>
                        </div>
                        <div class="form-horizontal"  style="display: none;">
                            <div class="form-group">
                                <label class="col-md-2 control-label">定向内容</label>
                                <div class="col-md-10">
                                    <input type="hidden" id="hidGeoIds" name="hidGeoIds" value="" />
                                    <input type="hidden" id="hidGeoNames" name="hidGeoNames" />
                                    <table id="tb_ListTarget" class="table table-bordered dataTable" width="100%">
                                        <thead>
                                            <tr>
                                                <th class="w-60">定向功能</th>
                                                <th >定向内容</th>
                                                <th class="w-60 text-center">操作</th>
                                            </thead>
                                            <tbody>
                                            <tr style="display: none;">
                                                <td>时间</td>
                                                <td id="td_Time" class="word-break-all"></td>
                                                <td class="text-center "><a class="btn-operate-name" id="btn_TargetTimeEdit" href="javascript:;">编辑</a><a class="btn-operate-name target-delete" id="btn_TargetTimeDelete" data-targetoperate="hours" href="javascript:;">删除</a></td>
                                            </tr>
                                            <tr style="display: none;">
                                                <td>地域</td>
                                                <td id="td_geo" class="word-break-all"></td>
                                                <td class="text-center "><a class="btn-operate-name" id="btn_TargetGeoEdit" href="javascript:;">编辑</a><a class="btn-operate-name target-delete" id="btn_TargetGeoDelete" data-targetoperate="area" href="javascript:;">删除</a></td>
                                            </tr>
                                            <tr style="display: none;">
                                                <td>人口属性</td>
                                                <td id="td_Audience" class="word-break-all"></td>
                                                <td class="text-center "><a class="btn-operate-name" id="btn_TargetAudienceEdit" href="javascript:;">编辑</a><a class="btn-operate-name target-delete" id="btn_TargetAudienceDelete" data-targetoperate="audience" href="javascript:;">删除</a></td>
                                            </tr>
                                            <tr style="display: none;">
                                                <td>移动设备</td>
                                                <td id="td_Device" class="word-break-all"></td>
                                                <td class="text-center "><a class="btn-operate-name" id="btn_TargetDeviceEdit" href="javascript:;">编辑</a><a class="btn-operate-name target-delete" id="btn_TargetDeviceDelete" data-targetoperate="device" href="javascript:;">删除</a></td>
                                            </tr>
                                            <tr style="display: none;">
                                                <td>商业兴趣</td>
                                                <td id="td_Interest" class="word-break-all"></td>
                                                <td class="text-center "><a class="btn-operate-name" id="btn_TargetInterestEdit" href="javascript:;">编辑</a><a class="btn-operate-name target-delete" id="btn_TargetInterestDelete" data-targetoperate="interest" href="javascript:;">删除</a></td>
                                            </tr>
                                            <tr style="display: none;">
                                                <td>关键字</td>
                                                <td id="td_KeyWord" class="word-break-all"></td>
                                                <td class="text-center "><a class="btn-operate-name" id="btn_TargetKeyWordEdit" href="javascript:;">编辑</a><a class="btn-operate-name target-delete" id="btn_TargetKeyWordDelete" data-targetoperate="keyword" href="javascript:;">删除</a></td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="portlet portlet-border">
                <div class="portlet-heading portlet-default">
                    <h3 class="portlet-title text-dark">出价设置</h3>
                    <div class="portlet-widgets">
                        <a href="#strategy_price" data-toggle="collapse"><i class="fa fa-minus-round"></i></a>
                    </div>
                    <div class="clearfix"></div>
                </div>
                <div class="panel-collapse collapse in" id="strategy_price">
                    <div class="portlet-body">
                        <div class="form-horizontal">
                            <div class="form-group">
                                <label class="col-md-2 control-label">计费方式</label>
                                <div class="col-md-10">
                                    <label class="control-label">CPC</label>
                                </div>
                            </div>
                        </div>
                        <div class="form-horizontal">
                            <div class="form-group">
                                <label class="col-md-2 control-label">广告出价</label>
                                <div class="col-md-10">
                                    <span class="box-must">·</span>
                                    <div class="input-group box-input parsley-err-absolute">
                                        <input type="text" id="txt_Bid" name="Bid" class="form-control num-float2" placeholder="广告出价" value="${bid}"  required="" parsley-special="keyup">
                                        <span class="input-group-addon">元/CPC</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </form>
        <div class="clearfix button-list-actions">
            <label class="col-md-2 control-label"></label>
            <div class="col-md-10">
                <button class="btn btn-default btn-large waves-effect waves-light" type="button" id="btn_StrategySubmit">确定 </button>
                <a class="btn  btn-graye8 m-l-5 btn-large waves-effect waves-light" type="button" href="/strategy/view.shtml?tabName=strategy&cid=${cid}">取消 </a>
            </div>
        </div>
        <jsp:include page="TargeTime.jsp"/>
        <jsp:include page="TargeGeo.jsp"/>
        <jsp:include page="TargeAudience.jsp"/>
        <jsp:include page="TargeDevice.jsp"/>
        <jsp:include page="TargeInterest.jsp"/>
        <jsp:include page="TargeKeyWord.jsp"/>
    </div>
    <div id="Strategy_Bid_Tip" class="tip-dialog-container" style="top: 212px">
        <div class="btn btn-warning waves-effect waves-light tip-dialog-btn">
            <i class="fa fa-openclose"></i>
        </div>
        <div class="tip-dialog-box clearfix">
            <div class="strategy-bid-info">
                <div class="form-group">
                    <label>预计覆盖最大人数</label>
                    <p class="bid-num"><span id="bid_people" class="num-int">0</span></p>
                </div>
                <div class="form-group">
                    <label>预计最大曝光量</label>
                    <p class="bid-num"><span id="bid_view" class="num-int">0</span></p>
                </div>
                <div class="form-group">
                    <label>建议出价范围</label>
                    <p class="bid-num"><span id="bid_minprice" class="num-float2">0</span> ~ <span id="bid_maxprice" class="num-float2">0</span> 元 / CPC</p>
                </div>

            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(function () {
        var $pageStrategyDetail = {
            initCreativeId:null,
            initStrategyId:null,
            isadd:true,
            init: function () {
                $("#form_StrategyDetail").ExtParsley();
                $.ExtNumeric.init();
                $("#txt_StartTime").ExtDateTime({infoExt:{
                    objEndTime:$("#txt_EndTime"),
                    isInitStart:true,
                    onChangStartTime:function(){$("#form_StrategyDetail").ExtParsley().validate("#txt_StartTime");},
                    onChangEndTime:function(){$("#form_StrategyDetail").ExtParsley().validate("#txt_StartTime,#txt_EndTime");}
                }}).initDateStartEnd();
                this.initStrategyBidTip();
                var tempInitData='${strategy}';
                var initFormData=null;
                if(!$.ExtCommon.isCheckEmpty(tempInitData)){
                    var jsonData=$.parseJSON(tempInitData);
                    if(!$.isEmptyObject(jsonData)) initFormData=jsonData;
                    $pageStrategyDetail.isadd=false;
                }
                if(!$.isEmptyObject(initFormData)){
                    $(".page-operate-title").text("编辑策略");
                    $("#form_StrategyDetail").data("strategyinfodata",initFormData);
                    $pageStrategyDetail.initCreativeId=initFormData.creative_id;
                    $pageStrategyDetail.initStrategyId=initFormData.id;
                    this.bindTargeHtml.allTarge(initFormData.target_param)
                }else{
                    $("#form_StrategyDetail").data("strategyinfodata",{});
                    $(".page-operate-title").text("新建策略")
                }
                this.creativeRefresh();
            },
            initStrategyBidTip:function(winScrollTop){
                var top=$("#form_StrategyDetail").offset().top+5;
                if(winScrollTop){
                    top=winScrollTop>top?5:top
                }
                $("#Strategy_Bid_Tip").css({position:top==5?"fixed":"absolute","top":top});
            },
            creativeRefresh:function(){
                var self=this;
                self.bindListCreative(self.initCreativeId,self.initStrategyId);
            },
            bindListCreative: function (creativeid,strategyid) {
                var tempLoadInfo={place:"box",boxId:"#tb_ListCreative",height:200,type:"circle", "nothingHtml":'<span>暂无创意，<a class="" href="/creative/detail.shtml?cid=${cid}" target="_blank">新建创意</a>，后 <a id="btn_CreativeRefresh" href="javascript:;">刷新</a></span>',};
                var self=this;
                if(self.tableCreative) $("#tb_ListCreative").ExtDataTable("destroy");
                self.tableCreative=$("#tb_ListCreative").ExtDataTable({
                    paging: false,
                    bInfo:false,
                    bAutoWidth:false,
                    columns: [
                        {"data":null, sClass: "w-20"},
                        {"data": "name"},
                        {"data": "ad_format", sClass: "w-60"},
                        {"data": "plat_form"},
                        { "data":null,class:"w-40 text-center"}
                    ],
                    infoExt:{name:"创意列表",url:"/creative/list.shtml",ajaxDataPara:function(){
                        $.ExtAjax.initLoadStart(tempLoadInfo);
                        var para={};
                        para.cid = '${cid}';
                        para.length=999;
                        para.filter=true;
                        if(strategyid)para.strategy_id=strategyid;
                        return para;
                    }},
                    createdRow: function (row, data, index) {
                        $('td', row).eq(0).html('<div class="radio radio-custom"><input type="radio" id="creativeid_' + data.id + '" name="creativeid" value="'+data.id+'"><label for="creativeid_' + data.id + '"></label></div>');
                        $('td', row).eq(1).html('<a class="creative-name btn-operate-name"  href="/creative/detail.shtml?creative_id=' + data.id + '" target="_blank">' + data.name + '</a>');
                        $('td', row).eq(4).html('<a class="creative-name btn-operate-name"  href="/creative/detail.shtml?creative_id=' + data.id + '" target="_blank">编辑</a>');
                    },
                    drawCallback: function () {
                        console.log("111")
                    },
                    initComplete:function(tb,json){
                        if(json.data.length>0) {
                            if ($.ExtCommon.isCheckEmpty(creativeid)) {
                                $("#tb_ListCreative :radio:eq(0)").prop("checked", true);
                            } else {
                                $("#tb_ListCreative :radio[value='" + creativeid + "']").prop("checked", true);
                            }
                            self.getBidData();
                        }else{
                        }
                        var columnindex=self.isadd?4:0;
                        var hidecolumn =  $("#tb_ListCreative").ExtDataTable("getTabelInfo").column(columnindex);
                        hidecolumn.visible(false);
                        tempLoadInfo.dataListLength=json.data.length;
                        $.ExtAjax.initLoadEnd(tempLoadInfo);
                    }
                });
            },
            tbTarget:$("#tb_ListTarget"),
            bindTargeHtml:{
                getDataByHml:function(){
                    var targetParam={};
                    var tbTargetData=$("#tb_ListTarget").data();
                    if(tbTargetData.time){
                        targetParam.hours=[];
                        for(var kw in tbTargetData.time.value){
                            targetParam.hours.push(tbTargetData.time.value[kw]);
                        }
                    }
                    if(tbTargetData.area){
                        if(tbTargetData.area)targetParam.area=$.ExtTarge.convertListToJson(tbTargetData.area);
                    }
                    if(tbTargetData.audience){
                        if(tbTargetData.audience.age)targetParam.age=tbTargetData.audience.age;
                        if(tbTargetData.audience.gender)targetParam.gender= $.ExtTarge.convertListToJson(tbTargetData.audience.gender);
                        if(tbTargetData.audience.education)targetParam.education=$.ExtTarge.convertListToJson(tbTargetData.audience.education);
                        if(tbTargetData.audience.marriage)targetParam.marriage=$.ExtTarge.convertListToJson(tbTargetData.audience.marriage);
                    }
                    if(tbTargetData.device){
                        if(tbTargetData.device.connection)targetParam.connection=$.ExtTarge.convertListToJson(tbTargetData.device.connection);
                        if(tbTargetData.device.os)targetParam.os=$.ExtTarge.convertListToJson(tbTargetData.device.os);
                        if(tbTargetData.device.telcom)targetParam.telcom=$.ExtTarge.convertListToJson(tbTargetData.device.telcom);
                    }
                    if(tbTargetData.interest){
                        targetParam.interest=$.ExtTarge.convertListToJson(tbTargetData.interest);
                    }
                    if(tbTargetData.keyword){
                        targetParam.keyword=tbTargetData.keyword;
                    }
                    return targetParam;
                },
                showTable:function(){
                    if($pageStrategyDetail.tbTarget.find("tbody tr.trshow").length>0){
                        $pageStrategyDetail.tbTarget.closest(".form-horizontal").show();
                    }else{
                        $pageStrategyDetail.tbTarget.closest(".form-horizontal").hide();
                    }
                },
                allTarge:function(json){
                    if(json.hours){
                        var weekInfo=$.ExtTarge.time.convertInitWeekTimeValue(json.hours);
                        var hoursJsonValue=$.ExtTarge.time.convertSelectWeekTime(weekInfo);
                        this.hours(hoursJsonValue,false)
                    }
                    if(json.area){
                        var areaJsonValue=$.ExtTarge.convertJsonToList(json.area);
                        this.area(areaJsonValue,false)
                    }
                    var audienceJson={};
                    if(json.gender){
                        audienceJson.gender=$.ExtTarge.convertJsonToList(json.gender);
                    }
                    if(json.age){
                        audienceJson.age=json.age
                    }
                    if(json.education){
                        audienceJson.education=$.ExtTarge.convertJsonToList(json.education);
                    }
                    if(json.marriage){
                        audienceJson.marriage=$.ExtTarge.convertJsonToList(json.marriage);
                    }
                    if(!$.isEmptyObject(audienceJson)){
                        this.audience(audienceJson,false)
                    }
                    var deviceJson={};
                    if(json.os){
                        deviceJson.os=$.ExtTarge.convertJsonToList(json.os);
                    }
                    if(json.connection){
                        deviceJson.connection=$.ExtTarge.convertJsonToList(json.connection);
                    }
                    if(json.telcom){
                        deviceJson.telcom=$.ExtTarge.convertJsonToList(json.telcom);
                    }
                    if(!$.isEmptyObject(deviceJson)){
                        this.device(deviceJson,false)
                    }
                    if(json.interest){
                        var interestJsonValue=$.ExtTarge.convertJsonToList(json.interest);
                        this.interest(interestJsonValue,false)
                    }
                    if(json.keyword){
                        this.keyword(json.keyword,false)
                    }
                    this.showTable();
                },
                hours:function(json,isShowBid){
                    var strHtml="";
                    if($.isEmptyObject(json)){
                        $pageStrategyDetail.tbTarget.removeData("time");
                    }else{
                        $pageStrategyDetail.tbTarget.data("time",json);
                        if(json.selecttext.length>0) {
                            strHtml = $.map(json.selecttext, function (info, i) {
                                return "<p>" + info + "</p>";
                            }).join("");
                        }
                    }
                    this.bindTdHtml("#td_Time",strHtml);
                    if(isShowBid)  $pageStrategyDetail.getBidData();
                },
                area:function(json,isShowBid){
                    var strHtml="";
                    if($.isEmptyObject(json)){
                        $pageStrategyDetail.tbTarget.removeData("area");
                    }else {
                        $pageStrategyDetail.tbTarget.data("area", json);
                        strHtml = $.map(json, function (info, i) {
                            return info.name;
                        }).join(",");
                    }
                    this.bindTdHtml("#td_geo",strHtml);
                    if(isShowBid)  $pageStrategyDetail.getBidData();
                },
                audience:function(json,isShowBid){
                    var strHtml="";
                    if($.isEmptyObject(json)){
                        $pageStrategyDetail.tbTarget.removeData("audience");
                    }else {
                        $pageStrategyDetail.tbTarget.data("audience", json);
                        var arrHtml = [];
                        var genderhtml = $.ExtTarge.getValueText("gender", "性别", json);
                        var educationhtml = $.ExtTarge.getValueText("education", "学历", json);
                        var marriagehtml = $.ExtTarge.getValueText("marriage", "婚恋", json);
                        if (genderhtml.length > 0) arrHtml.push(genderhtml);
                        if (educationhtml.length > 0) arrHtml.push(educationhtml);
                        if (marriagehtml.length > 0) arrHtml.push(marriagehtml);
                        if (json.age && json.age.length > 0) arrHtml.push("年龄：" + json.age + "岁");
                        strHtml=arrHtml.join("；")
                    }
                    this.bindTdHtml("#td_Audience",strHtml);
                    if(isShowBid)  $pageStrategyDetail.getBidData();
                },
                device:function(json,isShowBid){
                    var strHtml="";
                    if($.isEmptyObject(json)){
                        $pageStrategyDetail.tbTarget.removeData("device");
                    }else {
                        $pageStrategyDetail.tbTarget.data("device", json);
                        var arrHtml = [];
                        var oshtml = $.ExtTarge.getValueText("os", "操作系统", json);
                        var connectionhtml = $.ExtTarge.getValueText("connection", "联网方式", json);
                        var telcomhtml = $.ExtTarge.getValueText("telcom", "运营商", json);
                        if (oshtml.length > 0) arrHtml.push(oshtml);
                        if (connectionhtml.length > 0) arrHtml.push(connectionhtml);
                        if (telcomhtml.length > 0) arrHtml.push(telcomhtml);
                        strHtml=arrHtml.join("；")
                    }
                    this.bindTdHtml("#td_Device",strHtml);
                    if(isShowBid)  $pageStrategyDetail.getBidData();
                },
                interest:function(json,isShowBid){
                    var strHtml="";
                    if($.isEmptyObject(json)){
                        $pageStrategyDetail.tbTarget.removeData("interest");
                    }else {
                        $pageStrategyDetail.tbTarget.data("interest", json);
                        strHtml = $.map(json, function (info, i) {
                            return info.name;
                        }).join(",");
                    }
                    this.bindTdHtml("#td_Interest",strHtml);
                    if(isShowBid)  $pageStrategyDetail.getBidData();
                },
                keyword:function(json,isShowBid) {
                    var strHtml = "";
                    if ($.isEmptyObject(json)) {
                        $pageStrategyDetail.tbTarget.removeData("keyword");
                    } else {
                        $pageStrategyDetail.tbTarget.data("keyword", json);
                        strHtml = json;
                    }
                    this.bindTdHtml("#td_KeyWord", strHtml);
                    if (isShowBid)  $pageStrategyDetail.getBidData();
                },
                bindTdHtml:function(id,strhtml){
                    if(strhtml.length>0){
                        $(id).html(strhtml).parent().addClass("trshow").show();
                    }else{
                        $(id).html("").parent().removeClass("trshow").hide();
                    }
                    this.showTable();
                }
            },
            getBidData:function(){
                var jsonData=$pageStrategyDetail.getFromDataByHtml();
                $.ExtAjax.initPost({url: "/strategy/utility.shtml", data: JSON.stringify(jsonData),
                    success: function (res) {
                        var result = $.ExtAjax.initResult(res);
                        if (result.success) {
                            var obj=$("#Strategy_Bid_Tip");
                            $("#bid_people").text(res.data.user) //预计覆盖最大人数
                            $("#bid_view").text(res.data.exposure) //预计最大曝光量
                            $("#bid_minprice").text(res.data.min_bid) //建议出价范围 min
                            $("#bid_maxprice").text(res.data.max_bid) //建议出价范围 max
                            if(!obj.find(".tip-dialog-btn").hasClass("open")){
                                $(".tip-dialog-container .tip-dialog-btn").trigger("click");
                            }
                            obj.find(".num-int,.num-float2").ExtNumeric("refresh");
                        } else {
                            $.ExtNotify.error(result.msg);
                        }
                    }
                });
            },
            getFromDataByHtml:function(){
                var jsonData={cid:'${cid}'};
                var sname = $.trim($("#txt_SName").val());
                var start = $.trim($("#txt_StartTime").val());
                var end = $.trim($("#txt_EndTime").val());
                var description = $.trim($("#txt_Description").val());
                var bid = $.trim($("#txt_Bid").val()).removesplit();
                var creative_id=$.trim($(":radio[name='creativeid']:checked").val());
                var targetParam=$pageStrategyDetail.bindTargeHtml.getDataByHml();
                if(sname.length>0) jsonData.sname=sname;
                if(description.length>0) jsonData.description=description;
                if(start.length>0) jsonData.start=start+" 00:00:00";
                if(end.length>0) jsonData.end=end+" 00:00:00";
                if(bid.length>0) jsonData.bid=bid;
                if(creative_id.length>0) jsonData.creative_id=creative_id;
                if(!$.isEmptyObject(targetParam)) jsonData.target_param=targetParam;
                return jsonData;
            }
        };
        $pageStrategyDetail.init();
        $("#txt_Bid").on("change", function () {
            $pageStrategyDetail.getBidData();
        });
        $('#tb_ListCreative').on( 'click', 'tbody tr', function (e) {
            var _this=$(this);
            if(!$(e.target).hasClass("creative-name")&& _this.closest("tr").find(":radio").length>0){
                _this.find(":radio[name='creativeid']").prop("checked", true);
                $pageStrategyDetail.getBidData();
            }
        });
        function checkParsleyFrom(){
            return $("#form_StrategyDetail").ExtParsley("validate");
        }
        $("#btn_StrategySubmit").on("click", function () {
            if (checkParsleyFrom()) {
                var ajaxdata=$pageStrategyDetail.getFromDataByHtml();
                var formData=$("#form_StrategyDetail").data("strategyinfodata");
                var url="/strategy/create.shtml";
                if($.isEmptyObject(formData)){
                    url="/strategy/create.shtml";
                }else{
                    url="/strategy/edit.shtml";
                    ajaxdata.id=formData.id
                }
                $.ExtAjax.initPost({
                    disableid:"#btn_StrategySubmit",
                    loadinfo:{place:"body"},
                    url: url,
                    data: JSON.stringify(ajaxdata),
                    successcallback:function(res){
                        if(res.success) {
                            $.ExtNotify.success("保存成功");
                            window.location.href="/strategy/view.shtml?tabName=strategy&cid="+'${cid}';
                        }else{
                            $.ExtNotify.error(res.msg);
                        }
                    }
                });
            }
        });
        $("#btn_AddTimeTarget,#btn_TargetTimeEdit").on("click", function (e) {
            if ($.ExtCommon.isCheckEmpty($pageTargeTime.isCbSubmit)) {
                $pageTargeTime.isCbSubmit=true;
                $pageTargeTime.cbSubmit = function (json) {
                    $pageStrategyDetail.bindTargeHtml.hours(json,true);
                }
            }
            $pageTargeTime.show($pageStrategyDetail.tbTarget.data("time"));
        });
        $("#tb_ListTarget .target-delete").on("click", function (e) {
            var type=$(this).data("targetoperate");
            $pageStrategyDetail.bindTargeHtml[type]({},true);
        });
        $("#btn_AddGeoTarget,#btn_TargetGeoEdit").on("click", function (e) {
            if ($.ExtCommon.isCheckEmpty($pageTargeGeo.isCbSubmit)) {
                $pageTargeGeo.isCbSubmit=true;
                $pageTargeGeo.cbSubmit = function (json) {
                    $pageStrategyDetail.bindTargeHtml.area(json,true);
                }
            }
            $pageTargeGeo.initshow($pageStrategyDetail.tbTarget.data("area"));
        });
        $("#btn_AddAudienceTarget,#btn_TargetAudienceEdit").on("click", function (e) {
            if ($.ExtCommon.isCheckEmpty($pageTargeAudience.isCbSubmit)) {
                $pageTargeAudience.isCbSubmit=true;
                $pageTargeAudience.cbSubmit = function (json) {
                    $pageStrategyDetail.bindTargeHtml.audience(json,true);
                }
            }
            $pageTargeAudience.show($pageStrategyDetail.tbTarget.data("audience"));
        });
        $("#btn_AddInterestTarget,#btn_TargetInterestEdit").on("click", function (e) {
            if ($.ExtCommon.isCheckEmpty($pageTargeInterest.isCbSubmit)) {
                $pageTargeInterest.isCbSubmit=true;
                $pageTargeInterest.cbSubmit = function (json) {
                    $pageStrategyDetail.bindTargeHtml.interest(json,true);
                }
            }
            $pageTargeInterest.initshow($pageStrategyDetail.tbTarget.data("interest"));
        });
        $("#btn_AddKeyWordTarget,#btn_TargetKeyWordEdit").on("click", function (e) {
            if ($.ExtCommon.isCheckEmpty($pageTargeKeyWord.isCbSubmit)) {
                $pageTargeKeyWord.isCbSubmit=true;
                $pageTargeKeyWord.cbSubmit = function (json) {
                    $pageStrategyDetail.bindTargeHtml.keyword(json,true);
                }
            }
            $pageTargeKeyWord.show($pageStrategyDetail.tbTarget.data("keyword"));
        });
        $("#btn_AddDeviceTarget,#btn_TargetDeviceEdit").on("click", function (e) {
            if ($.ExtCommon.isCheckEmpty($pageTargeDevice.isCbSubmit)) {
                $pageTargeDevice.isCbSubmit=true;
                $pageTargeDevice.cbSubmit = function (json) {
                    $pageStrategyDetail.bindTargeHtml.device(json,true);
                }
            }
            $pageTargeDevice.show($pageStrategyDetail.tbTarget.data("device"))
        });
        $(window).scroll(function () {
            $pageStrategyDetail.initStrategyBidTip($(window).scrollTop());
        });

        $(".tip-dialog-container .tip-dialog-btn").on("click", function (e) {
            $(this).toggleClass("open").next().toggleClass("open");
        });
        $("#btn_CreativeRefresh").on("click", function () {
            $pageStrategyDetail.creativeRefresh()
        })
    });
</script>