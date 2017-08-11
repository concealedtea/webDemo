<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="page-campaign"  data-menutype="campaign">
    <div class="row">
        <div class="col-sm-12">
            <h4 class="page-title">
                <span>订单管理</span>
                <ol class="breadcrumb">
                    <jsp:include page="HomeName.jsp"/>
                    <li class="active">订单管理</li>
                </ol>
            </h4>
        </div>
    </div>
    <!-- Main -->
    <div class="row">
        <div class="col-sm-12">
            <div class="card-box">
                <div class="row">
                    <div class="col-lg-12">
                        <div>
                            <button class="btn btn-default waves-effect waves-light" id="btn_AddCampaign">新建订单</button>
                            <div class="form-inline pull-right" id="div_Search">
                                <select id="search_advertiser" class="w-200 form-control select2 root_advertiser"></select>
                                <select id="search_status" class="w-90 form-control select2"></select>
                                <input type="text" class="form-control" id="search_keyword" placeholder="订单ID,订单名称">
                                <button type="button" class="btn btn-default waves-effect waves-light btn-md" id="btn_Search">搜索</button>
                            </div>
                        </div>
                        <div class="table-custom p-t-10">
                            <table class="table table-bordered m-0" id="tb_CampaignList" width="100%">
                                <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>订单名称</th>
                                    <th>投放时间</th>
                                    <th>状态</th>
                                    <th>每日限额</th>
                                    <th>投放速度</th>
                                    <th>关联策略</th>
                                </tr>
                                </thead>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="modal fade" id="CampaignDetails" role="dialog" aria-labelledby="myModalLabel" style="">
            <div class="modal-dialog" role="document" style=" margin-top: 180px;">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                        <h4 class="modal-title">新建订单</h4>
                    </div>
                    <div class="modal-body">
                        <form class="form-horizontal" id="form_CampaignDetails" data-parsley-validate>
                            <div class="form-group">
                                <label class="col-md-2 control-label">订单名称</label>
                                <div class="col-md-10"><span class="box-must">·</span><input class="form-control box-input" type="text" id="txt_CName" placeholder="订单名称" name="CName" required="" data-parsley-maxlength="120" data-parsley-illegal-value=""></div>
                            </div>
                            <div class="form-group root_advertiser">
                                <label class="col-md-2 control-label">广告主</label>
                                <div class="col-md-10"><span class="box-must">·</span><select id="ddl_advertiser" class="w-400 form-control select2"  required=""  style="width: 400px"></select></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 control-label">投放时间</label>
                                <div class="col-md-10">
                                    <span class="box-must">·</span>
                                    <div class="d-inline-block w-210">
                                        <input class="form-control w-186" id="txt_StartTime" type="text" placeholder="开始时间" name="StartTime" readonly="readonly" required=""  data-parsley-before-endtime="#txt_EndTime" data-parsley-after-now="">
                                    </div>
                                    <div class="d-inline-block">
                                        <input  class="form-control d-inline-block w-186" id="txt_EndTime" type="text" placeholder="结束时间" name="EndTime"  readonly="readonly" required="" data-parsley-after-starttime="#txt_StartTime" data-parsley-after-now="">
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 control-label text-right ">每日限额</label>
                                <div class="col-md-10"><span class="box-must">·</span><input class="form-control w-186 num-int" id="txt_Day_Budget" type="text" placeholder="每日限额" name="DayBudget" required="" parsley-special="keyup" ></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 control-label">投放速度</label>
                                <div class="col-md-10" id="html_SpeedMode"></div>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-graye8 waves-effect" data-dismiss="modal">关闭</button>
                        <button type="button" class="btn btn-default waves-effect waves-light" id="btn_CampaignSubmit">确定</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script id="template_name" type="text/x-jsrender">
    <a class="btn-operate-name" href="/strategy/view.shtml?cid={{:id}}">{{:name}}</a>
    <div class="pull-right dropdown name-icon">
        <a href="javascript:;" class="details-operate" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            <span ><i class="fa fa-cog"></i></span>
        </a>
        <ul class="dropdown-menu animated flipInX w-m100" aria-labelledby="dLabel">
            <li><a class="" href="/strategy/view.shtml?tabName=strategy&cid={{:id}}">策略管理</a></li>
            <li><a class="" href="/strategy/view.shtml?tabName=creative&cid={{:id}}">创意管理</a></li>
            <li class="divider"></li>
            <li><a class="edit-campaign" href="javascript:;" campaignid="{{:id}}">编辑</a></li>
        </ul>
    </div>
</script>
<script id="template_detail_speedmode" type="text/x-jsrender">
     <div class="radio radio-custom"><input type="radio" name="SpeedMode" id="rd_SpeedMode{{:#index}}" value="{{:id}}"><label for="rd_SpeedMode{{:#index}}">{{:name}}</label></div>
</script>
<script type="text/javascript">
    $(function () {
        var $pageCampaignList = {
            tableCampaign_columns:[
                {"data": "cid", sClass: "w-90","bSortable": false},
                {"data": "cname",sClass: "p-r-30","bSortable": false},
                {"data": "start",  sClass: "w-200", "bSortable": false},
                {"data": "status",sClass: "w-60", "bSortable": false},
                {"data": "day_budget", sClass: "w-70 text-right", "bSortable": false},
                {"data": "speed_mode", sClass: "w-70","bSortable": false},
                {"data": "strategy_count",sClass: "w-80", "bSortable": false}
            ],
            initOption: {id: "-99", text: "全部"},
            initStatusOperate: [{ id: "1", text: "启用",showid:["3"] },{ id: "3", text: "暂停",showid:["0","1"] }],
            init: function () {
                var self = this;
                <c:choose>
                    <c:when test="${sessionScope.USER_INFO_SESSION.userRole eq 'AGENCY'}">
                self.bindAdvertiserData();
                self.tableCampaign_columns.push({"data": "uname", "bSortable": false});
                $("#tb_CampaignList thead tr").append("<th>广告主</th>");
                    </c:when>
                    <c:otherwise>
                $(".root_advertiser").empty().remove();
                    </c:otherwise>
                </c:choose>
                self.bindSearchStatus(function () {
                    self.bindSpeedMode(function(){
                        self.bindList();
                    });
                });
                $("#form_CampaignDetails").ExtParsley();
                //$.ExtNumeric.init();
            },
            bindAdvertiserData: function () {
                $.ExtAjax.initGet({url:"/advertiser/tree.shtml",data:{},
                    success:function(res){
                        var listAdvertiser=[];
                        for (var i = 0; i < res.length; i++) {
                            var info=res[i];
                            listAdvertiser.push({id:info.id,text:info.name});
                        }
                        var listAdvertiserAdd=listAdvertiser.arrclone();
                        listAdvertiser.unshift($pageCampaignList.initOption);
                        $("#search_advertiser").ExtSelect2({placeholder:"-广告主-",minimumResultsForSearch:1,select2Info:{data:listAdvertiser}});
                        $("#ddl_advertiser").ExtSelect2({placeholder:"-广告主-",minimumResultsForSearch:1,allowClear: true,select2Info:{data:listAdvertiserAdd}});
                    }
                });
            },
            bindSearchStatus: function (cb) {
                $.ExtAjax.initGet({url:"/common/status.shtml",data:{},
                    success:function(res){
                        $pageCampaignList.listStatus = [];
                        for (var i = 0; i < res.length; i++) {
                            var info=res[i];
                            $pageCampaignList.listStatus.push({id:info.id,text:info.name});
                        }
                        $pageCampaignList.listStatus.unshift($pageCampaignList.initOption);
                        $("#search_status").ExtSelect2({placeholder:"-状态-",select2Info:{data:$pageCampaignList.listStatus}});
                        if (cb) {cb()}
                    }
                });
            },
            bindSpeedMode:function(cb){
                $.ExtAjax.initGet({url:"/common/speedMode.shtml",data:{},
                    success:function(res){
                        $pageCampaignList.listSpeedMode = [];
                        if(res && res.length>0){
                            $pageCampaignList.listSpeedMode=res;
                            $("#html_SpeedMode").html($("#template_detail_speedmode").render($pageCampaignList.listSpeedMode))
                        }
                        if (cb) {cb()}
                    }
                });
            },
            bindList: function () {
                var self=this;
                $("#tb_CampaignList").ExtDataTable({
                    columns: self.tableCampaign_columns,
                    infoExt:{name:"订单列表",url:"/campaign/list.shtml",ajaxDataPara:function(){
                        var para={};
                        var keyword = $.trim($("#search_keyword").val());
                        if (!$.ExtCommon.isCheckEmpty(keyword)) {para.search_keyword = keyword;}
                        var status = $("#search_status").val();
                        if (!$.ExtCommon.isCheckEmpty(status) && status.toString() != "-99") {para.search_status = status;}
                        var advertiser = $("#search_advertiser").val();
                        if (!$.ExtCommon.isCheckEmpty(advertiser) && advertiser.toString() != "-99") {para.search_advertiser = advertiser;}
                        return para;
                    }},
                    createdRow: function (row, data, index) {
                        self.bindTableTd(row, data)
                    },
                    drawCallback: function () {
                        $.ExtNumeric.init();
                        self.initXEditable();
                    }
                });
            },
            refreshList: function () {
                $("#tb_CampaignList").ExtDataTable("refresh")
            },
            bindTableTd:function(row,data){
                $('td', row).eq(1).attr("id","campaignid_"+data.cid).html($("#template_name").render({name: data.cname, id: data.cid}));
                $('td', row).eq(2).html($.ExtDateTime.initMomentFormat(data.start) + " ~ " + $.ExtDateTime.initMomentFormat(data.end));
                var tempjsonStatus = $pageCampaignList.bindTableColumnByStatus(data.cid, data.status);
                if (tempjsonStatus.list.length > 0) {
                    $('td', row).eq(3).html('<a class="editable editable-click editable-init btn-operate-name"  data-operatetype="status" href="javascript:;">' + tempjsonStatus.statusname + '</a>');
                    $('td', row).eq(3).find(".editable").data({list: tempjsonStatus.list, campaignid: tempjsonStatus.campaignid});
                } else {
                    $('td', row).eq(3).html(tempjsonStatus.statusname)
                }
                $('td', row).eq(4).html('<a class="editable editable-click editable-init btn-operate-name" data-operatetype="daybudget"  href="javascript:;"><span class="num-int">' + data.day_budget + '</span></a>');
                $('td', row).eq(4).find(".editable").data({campaignid: tempjsonStatus.campaignid});
                $('td', row).eq(5).html($pageCampaignList.getSpeedModeName(data.speed_mode));
                $('td', row).eq(6).html(data.strategy_count + "个策略");
            },
            initXEditable: function () {
                $("#tb_CampaignList .editable-click.editable-init").each(function () {
                    var _this = $(this);
                    _this.removeClass("editable-init");
                    var _thisData = _this.data();
                    var row =$("#tb_CampaignList").ExtDataTable().getRowInfo(_this.closest('tr'));
                    var operatetype = _this.attr("data-operatetype");
                    if (operatetype == "daybudget") {
                        _this.ExtXEditable({
                            type: "text",
                            url: "/campaign/edit.shtml",
                            params: function (params) {
                                var newParams = {cid: _thisData.campaignid};
                                newParams.day_budget = $.trim(params.value).removesplit();
                                return JSON.stringify(newParams);
                            },
                            initControlOther: function () {
                                $(".editable-container :text").ExtNumeric({numtype: "int",vMax:"9999999"});
                            },
                            successCallback: function (res, newValue) {
                                row.data().daybudget = newValue.removesplit();
                            }
                        });
                    } else if (operatetype == "status") {
                        _this.ExtXEditable({
                            source : _thisData.list,
                            type: "select",
                            url: "/campaign/edit.shtml",
                            params: function (params) {
                                var newParams = {cid: _thisData.campaignid};
                                newParams.status = $.trim(params.value);
                                return JSON.stringify(newParams);
                            },
                            initControlOther: function () {
                                _this.siblings(".editable-container").find("select").ExtSelect2({select2Info:{initvalue:_thisData.list[0].value}});
                            },
                            successCallback: function (res, newValue) {
                                var tempjsonStatus = $pageCampaignList.bindTableColumnByStatus(_thisData.campaignid, res.data.status);
                                _this.text(tempjsonStatus.statusname).data("list", tempjsonStatus.list);
                                _this.ExtXEditable().refresh(tempjsonStatus.list);
                                row.data().status = newValue;
                            }
                        });
                    }
                });
            },
            bindTableColumnByStatus: function (campaignid, status) {
                var tempstatus=[];
                var statusname="";
                status=status==null?"":status.toString();
                for(var i=0;i<$pageCampaignList.listStatus.length;i++){
                    var info=$pageCampaignList.listStatus[i];
                    if(info.id.toString()==status){
                        statusname=info.text;
                        break;
                    }
                }
                for(var j=0;j<$pageCampaignList.initStatusOperate.length;j++){
                    var infoOperate=$pageCampaignList.initStatusOperate[j];
                    if(infoOperate.showid.indexOf(status)>-1){
                        tempstatus.push({value:infoOperate.id,text:infoOperate.text});
                    }
                }
                return {campaignid: campaignid, statusname: statusname, status: status, list: tempstatus}
            },
            getSpeedModeName: function (speedmode) {
                var name="";
                speedmode=speedmode==null?"":speedmode.toString();
                for(var i=0;i<$pageCampaignList.listSpeedMode.length;i++){
                    var info=$pageCampaignList.listSpeedMode[i];
                    if(info.id.toString()==speedmode){
                        name=info.name;
                        break;
                    }
                }
                return name
            },
            showCampaignDetails: function (json) {
                $("#txt_StartTime").val(json.start.length==0?"":$.ExtDateTime.initMomentFormat(json.start));
                $("#txt_EndTime").val(json.end.length==0?"":$.ExtDateTime.initMomentFormat(json.end));
                $("#txt_StartTime").ExtDateTime({infoExt:{
                    objEndTime:$("#txt_EndTime"),
                    isInitStart:json.isShowStartTime,
                    onChangStartTime:function(){$("#form_CampaignDetails").ExtParsley().validate("#txt_StartTime");},
                    onChangEndTime:function(){$("#form_CampaignDetails").ExtParsley().validate("#txt_StartTime,#txt_EndTime");}
                }}).initDateStartEnd();
                $("#txt_CName").val(json.cname);
                $("#txt_Day_Budget").val(json.day_budget).ExtNumeric("refresh")
                $(":radio[name='SpeedMode'][value='"+json.speed_mode+"']").prop("checked", true);
                $("#ddl_advertiser").val(json.uid.toString()).trigger("change");
                $("#form_CampaignDetails").ExtParsley("reset");
                $("#CampaignDetails").data("originalData",json).modal("show").find(".modal-title").text(json.modal_title);

            },
            checkParsleyFrom:function (json) {
                var originalData = $("#CampaignDetails").data("originalData");
                var startTime = $("#txt_StartTime");
                if (startTime.val() == $.ExtDateTime.initMomentFormat(originalData.start)) {
                    startTime.removeAttr("data-parsley-after-now")
                } else {
                    startTime.attr({"data-parsley-after-now": ""})
                }
                var endTime = $("#txt_EndTime");
                if (endTime.val() == $.ExtDateTime.initMomentFormat(originalData.end)) {
                    endTime.removeAttr("data-parsley-after-now");

                } else {
                    endTime.attr({"data-parsley-after-now": ""})
                }
                return $("#form_CampaignDetails").ExtParsley("validate");
            }
        };
        $pageCampaignList.init();
        $("#search_keyword").on("keydown",function(event){
            if(event.keyCode == 13) {
                $pageCampaignList.refreshList();
            }
        });
        $("#btn_Search").on("click", function () {
            $pageCampaignList.refreshList();
        });
        $("#btn_AddCampaign").on("click", function (e) {
            var json={start:"",end:"",isShowStartTime:true,cname:"","day_budget":"","speed_mode":0,"uid":"","uname":"","modal_title":"新建订单",isadd:true};
            $pageCampaignList.showCampaignDetails(json)
        });
        $("#btn_AddTimeTarget").on("click", function (e) {
            $pageCampaignList.showTimeTarget()
        });
        $("#ddl_advertiser").on("change", function (e) {
            $("#form_CampaignDetails").ExtParsley().validate("#ddl_advertiser");
        });
        $('#tb_CampaignList').on('click', '.edit-campaign', function () {
            var tr = $(this).closest('tr');
            var trData = $("#tb_CampaignList").ExtDataTable().getRowInfo(tr).data();
            $.ExtAjax.initGet({url:"/campaign/read.shtml",data:{cid:trData.cid},
                success:function(res){
                    var result=$.ExtAjax.initResult(res);
                    if(result.success){
                        var json=result.data;
                        json.isShowStartTime=true;
                        json.modal_title="编辑订单";
                        json.isadd=false;
                        $pageCampaignList.showCampaignDetails(json);
                    }else{
                        $.ExtNotify.error(result.msg);
                    }
                }
            });
        });
        $("#btn_CampaignSubmit").on("click", function () {
            var originalData=$("#CampaignDetails").data("originalData");
            if ($pageCampaignList.checkParsleyFrom(originalData)) {
                var cname = $.trim($("#txt_CName").val());
                var starttime = $.trim($("#txt_StartTime").val());
                var endtime = $.trim($("#txt_EndTime").val());
                var day_budget = $.trim($("#txt_Day_Budget").val()).removesplit();
                var speed_mode = $.trim($(":radio[name='SpeedMode']:checked").val());
                var uid = $("#ddl_advertiser").val();
                var tempjson = {cname: cname, start: starttime+" 00:00:00", end: endtime+" 00:00:00", day_budget: day_budget, speed_mode: speed_mode};
                if(uid){tempjson.uid=uid;}
                var url="";
                if(originalData.isadd){
                    url="/campaign/create.shtml"
                }else{
                    url="/campaign/edit.shtml";
                    tempjson.cid=originalData.cid
                }
                $("#CampaignDetails").modal("hide");
                $.ExtAjax.initPost({
                    disableid:"#btn_CampaignSubmit",
                    loadinfo:{place:"body"},
                    url:url,
                    data:JSON.stringify(tempjson),
                    successcallback:function(res){
                        if(res.success) {
                            var info = res.data;
                            if(originalData.isadd){
                                $pageCampaignList.refreshList();
                            }else{
                                var tr=$("#campaignid_"+info.cid).parent();
                                var row = $("#tb_CampaignList").ExtDataTable().getRowInfo(tr);
                                for(var i in info){
                                    if(row.data()[i])row.data()[i]=info[i]
                                }
                                $pageCampaignList.bindTableTd(tr, info);
                                tr.find(".num-float2,.num-int").ExtNumeric("refresh");
                                $pageCampaignList.initXEditable();
                            }
                            $.ExtNotify.success("保存成功");
                        }else{
                            $.ExtNotify.error(res.msg);
                            $("#CampaignDetails").modal("show");
                        }
                    }
                });
            }
        });
    });
</script>
