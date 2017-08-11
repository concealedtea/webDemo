<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="page-agency" data-menutype="agency">
    <div class="row">
        <div class="col-sm-12">
            <h4 class="page-title">
                <span>代理商管理</span>
                <ol class="breadcrumb">
                    <jsp:include page="HomeName.jsp"/>
                    <li class="active">代理商管理</li>
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
                            <a class="btn btn-default waves-effect waves-light" id="btn_AddAgency" href="#">新建代理商</a>
                            <div class="form-inline pull-right" id="div_Search">
                                <input type="text" class="form-control w-250" id="search_keyword" placeholder="代理商ID，代理商名称">
                                <button type="button" class="btn btn-default waves-effect waves-light btn-md" id="btn_Search">搜索</button>
                            </div>
                        </div>
                        <div class="table-custom p-t-10">
                            <table class="table table-bordered" id="tb_AgencyList" width="100%">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>代理商名称</th>
                                        <th>状态</th>
                                        <th>服务费(%)</th>
                                        <th>账户余额(￥)</th>
                                        <th>关联广告主</th>
                                        <th>操作</th>
                                    </tr>
                                </thead>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <%--转入/转出资金页面--%>
    <div class="modal fade" id="dialog_in" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" style="">
        <div class="modal-dialog" role="document" style="margin-top: 180px;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h4 class="modal-title" id="label_title">转入资金</h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal" id="form_Finance" data-parsley-validate>
                        <div class="form-group">
                            <label class="col-md-3 control-label">资金账户类型</label>
                            <div class="col-md-9 p-t-2">
                                <div class="radio radio-custom radio-inline">
                                    <input type="radio" id="cash_funds" name="input_funds" value="CASH">
                                    <label for="cash_funds">现金</label>
                                </div>
                                <div class="radio radio-custom radio-inline">
                                    <input type="radio" id="virtual_funds" name="input_funds" value="VIRTUAL">
                                    <label for="virtual_funds">虚拟金</label>
                                </div>
                            </div>
                        </div>
                        <div class="form-group root_agency">
                            <label class="col-md-3 control-label" id="label_balance">代理商账户余额</label>
                            <div class="col-md-9 p-t-7"><span id="balance_count" class="num-float2"></span></div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label" id="label_account">转入账户</label>
                            <div class="col-md-9 p-t-7" id="agency_Name"></div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">服务费</label>
                            <div class="col-md-9 p-t-7" ><span id="agency_Commission"></span>%</div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label" id="label_virtual_amount">溢价转入金额</label>
                            <div class="col-md-9">
                                <span class="box-must">·</span>
                                <input class="form-control w-186 num-int" id="txt_virtual_amount" type="text" name="virtual_amount"  required="" data-parsley-num-multiple=""  parsley-special="keyup">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label" id="label_amount">实际转入金额</label>
                            <div class="col-md-9">
                                <input class="form-control w-186 num-int" id="txt_amount" disabled="disabled" type="text" name="amount" data-parsley-before-balance="#balance_count" required="">
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-graye8 waves-effect" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-default waves-effect waves-light" id="btn_Funds_In">确定</button>
                </div>
            </div>
        </div>
    </div>
    <!-- 新建代理商页面 -->
    <div class="modal fade" id="AgentDetails" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" style="">
        <div class="modal-dialog" role="document" style="margin-top: 180px;">
            <div class="modal-content w-650">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h4 class="modal-title" id="Agent_Details_title">新建代理商</h4>
                </div>
                <div class="modal-body">
                    <form class="form-parsley" id="form_AgentDetails" data-parsley-validate="" autocomplete="off">
                        <div class="form-horizontal">
                            <div class="form-group">
                                <label class="col-md-2 control-label">代理商名称</label>
                                <div class="col-md-10">
                                    <span class="box-must">·</span>
                                    <input class="form-control box-input" id="txt_Name" type="text" name="Name"  placeholder="代理商名称" required=""  data-parsley-maxlength="120" data-parsley-illegal-value="">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 control-label">状态</label>
                                <div class="col-md-10 p-t-2">
                                    <div class="radio radio-custom radio-inline">
                                        <input type="radio" id="rb_Status1" name="rbStatus" checked value="1">
                                        <label for="rb_Status1">有效</label>
                                    </div>
                                    <div class="radio radio-custom radio-inline">
                                        <input type="radio" id="rb_Status0" name="rbStatus" value="-1">
                                        <label for="rb_Status0">无效</label>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 control-label">服务费</label>
                                <div class="col-md-10">
                                    <div class="input-group w-420 parsley-err-absolute">
                                        <input id="txt_commission" class="form-control" type="text"  required=""  data-parsley-type="integer" value="0"  name="commission" data-parsley-range="[0,100]" >
                                        <span class="input-group-addon">%</span>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 control-label">联系人姓名</label>
                                <div class="col-md-10">
                                    <input class="form-control box-input" id="txt_ContactName" type="text"  name="ContactName"  placeholder="联系人姓名">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 control-label">手机</label>
                                <div class="col-md-10">
                                    <input class="form-control box-input " id="txt_PhoneNo" type="text" name="PhoneNo"  placeholder="手机" data-parsley-phone="">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 control-label">邮箱</label>
                                <div class="col-md-10">
                                    <input class="form-control box-input" id="txt_Email" type="email" name="Email"  placeholder="邮箱">
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-graye8 waves-effect" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn-default waves-effect waves-light" id="btn_SubmitAgencyDetails">确定</button>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(function () {
        var $pageAgencyList = {
            init: function () {
                $("#form_Finance").ExtParsley();
                $("#form_AgentDetails").ExtParsley();
                this.bindList()
            },
            bindList: function () {
                var self=this;
                $("#tb_AgencyList").ExtDataTable({
                    columns: [
                        {"data": "id", sClass: "w-90", "bSortable": false},
                        {"data": "name", sClass: "", "bSortable": false},
                        {"data": "status_name", sClass: "w-100", "bSortable": false},
                        {"data": "commission", sClass: "w-100 text-right", "bSortable": false},
                        {"data": null, sClass: "num-float2 w-100 text-right ", "bSortable": false},
                        {"data": "advertiserNumber", sClass: "w-100 text-right", "bSortable": false},
                        {"data": null, sClass: "w-200 text-center", "bSortable": false}
                    ],
                    infoExt:{name:"代理商列表",url: "/agency/agencyList.shtml",ajaxDataPara:function(){
                        var para={};
                        var keyword = $.trim($("#search_keyword").val());
                        if (!$.ExtCommon.isCheckEmpty(keyword)) {para.search_keyword = keyword;}
                        return para;
                    }},
                    createdRow: function (row, data, index) {
                        $('td', row).eq(1).attr("id", "agency_" + data.id);
                        self.bindTableTd(row, data)
                    },
                    drawCallback: function () {
                        $.ExtNumeric.init();
                    }
                });
            },
            refreshList: function () {
                $("#tb_AgencyList").ExtDataTable("refresh");
            },
            bindTableTd: function (row, data) {
                $('td', row).eq(1).html('<a class="btn-operate-name edit_agency" href="javascript:;">' + data.name + '</a> ');
                $('td', row).eq(2).html(data.status_name);
                $('td', row).eq(3).html(data.commission + "%");
                $('td', row).eq(4).html(data.balance_cash + data.balance_virtual);
                $('td', row).eq(5).html(data.advertiserNumber + "个");
                if (data.status_name == "有效") {
                    $('td', row).eq(6).html('<a href="javascript:;" class="a_funds btn-operate"  name="a_in" >转入资金</a><a href="javascript:;" class="a_funds btn-operate"  name="a_out"  >转出资金</a>');
                }
                else {
                    //<a href="javascript:;" class="btn-operate" >模拟登陆</a>
                    $('td', row).eq(6).html('');
                }
            },
            setBalanceCount: function (value) {
                $("#balance_count").text(value).ExtNumeric("refresh")
            },
            showAgencyDetails: function (json) {
                $("#txt_Name").val(json.name);
                $(":radio[name='rbStatus'][value='" + json.status + "']").prop("checked", true);
                $("#txt_commission").val(json.commission);
                $("#txt_ContactName").val(json.contact_name);
                $("#txt_PhoneNo").val(json.phone_no);
                $("#txt_Email").val(json.email);
                $("#form_AgentDetails").ExtParsley("reset");
                $("#AgentDetails").data("originalData", json).modal("show").find(".modal-title").html(json.modal_title);
            },
            checkParsleyFromDetails: function () {
                return $("#form_AgentDetails").ExtParsley("validate");
            },
            showAgencyMoney: function (json) {
                $("#cash_funds").prop("checked", true);
                $("#txt_commission").prop("disabled", !json.isadd);
                $("#txt_virtual_amount,#txt_amount").val("");
                $("#agency_Name").html(json.name);
                $("#agency_Commission").html(json.commission);
                $pageAgencyList.setBalanceCount(json.moneyCash);
                $("#label_account").html(json.accountTitle);
                $("#label_balance").html(json.balancetTitle);
                $("#label_virtual_amount").html(json.titleVirtualAmount);
                $("#label_amount").html(json.titleAmount);
                $("#form_Finance").ExtParsley().reset(true);
                $("#dialog_in").data("info", json).modal("show").find(".modal-title").html(json.dialogTitle);
            },
            checkParsleyFromMoney: function () {
                return $("#form_Finance").ExtParsley("validate");
            }
        };
        $pageAgencyList.init();
        //搜索
        $("#btn_Search").on("click", function () {
            $pageAgencyList.refreshList();
        });
        $("#search_keyword").off("keydown").on("keydown",function(event){
            if(event.keyCode == 13)$pageAgencyList.refreshList();
        });

        ///新增/编辑
        $("#btn_AddAgency").on("click", function () {
            $("#txt_commission").prop("disabled", false);
            var json={name:"",status:"1",commission:"0",contactName:"",phoneNo:"","email":"","modal_title":"新建代理商",isadd:true};
            $pageAgencyList.showAgencyDetails(json)
        });
        $('#tb_AgencyList').on('click', '.edit_agency', function () {
            $("#txt_commission").prop("disabled", true);
            var tr = $(this).closest('tr');
            var trData =$("#tb_AgencyList").ExtDataTable().getRowInfo(tr).data();
            $.ExtAjax.initGet({url:"/agency/getAgencyOne.shtml",data:{id:trData.id},
                success:function(res){
                    var result=$.ExtAjax.initResult(res);
                    if(result.success){
                        var json={name:"",status:"1",commission:"",contact_name:"",phone_no:"","email":"","modal_title":"编辑代理商",isadd:false};
                        var tempJson=result.data;
                        for(var o in tempJson){json[o]=tempJson[o];}
                        $pageAgencyList.showAgencyDetails(json)
                    }else{
                        $.ExtNotify.error(result.msg);
                    }
                }
            });
        });
        // 新增/编辑页面 提交按钮
        $("#btn_SubmitAgencyDetails").on("click", function () {
            var originalData=$("#AgentDetails").data("originalData");
            if ($pageAgencyList.checkParsleyFromDetails()) {
                var name = $.trim($("#txt_Name").val());
                var contactName = $.trim($("#txt_ContactName").val());
                var commission  = $.trim($("#txt_commission").val());
                var phoneNo = $.trim($("#txt_PhoneNo").val());
                var email = $.trim($("#txt_Email").val()).removesplit();
                var status = $.trim($(":radio[name='rbStatus']:checked").val());
                var tempjson = {name: name,status:status,contact_name: contactName, phone_no: phoneNo, email: email};
                var url="";
                if(originalData.isadd){
                    url="/agency/addAgency.shtml";
                    tempjson.commission=commission
                }else{
                    url="/agency/updateAgency.shtml";
                    tempjson.id=originalData.id
                }
                $("#AgentDetails").modal("hide");
                $.ExtAjax.initPost({
                    disableid:"#btn_SubmitAgencyDetails",
                    loadinfo:{place:"body"},
                    url:url,
                    data:JSON.stringify(tempjson),
                    successcallback:function(res){
                        if(res.success) {
                            var info = res.data;
                            if(originalData.isadd){
                                $pageAgencyList.refreshList();
                            }else{
                                var tr=$("#agency_"+info.id).parent();
                                var row = $("#tb_AgencyList").ExtDataTable().getRowInfo(tr);
                                row.data()["name"]=info.name;
                                row.data()["status"]=info.status;
                                row.data()["status_name"]=info.status==1?"有效":"无效";
                                $pageAgencyList.bindTableTd(tr,  row.data());
                                tr.find(".num-float2,.num-int").ExtNumeric("refresh");
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

        //转入转出资金按钮触发
        $('#tb_AgencyList').on('click', '.a_funds', function () {
            var type = $(this).attr("name");
            var tr = $(this).closest('tr');
            var trData =$("#tb_AgencyList").ExtDataTable().getRowInfo(tr).data();
            var tempDataInfo= $.ExtCommon.getJsonClone(trData);
            tempDataInfo.moneytype=type;
           if(type == "a_in"){
               $.ExtAjax.initGet({url: "/agency/getBaseAccountBalance.shtml", data: {},
                   success:function(res) {
                       var result = $.ExtAjax.initResult(res);
                       if(result.success) {
                           var json = result.data;
                           tempDataInfo.accountTitle="转入账户";
                           tempDataInfo.balancetTitle="猎鹰账户余额";
                           tempDataInfo.titleVirtualAmount="溢价转入金额";
                           tempDataInfo.titleAmount="实际转入金额";
                           tempDataInfo.dialogTitle="转入资金";
                           tempDataInfo.moneyCash=json.balance_cash;
                           tempDataInfo.moneyVirtual=json.balance_virtual;
                           $pageAgencyList.showAgencyMoney(tempDataInfo)
                       }else{
                           $.ExtNotify.error(result.msg);
                       }
                   }
               });
           }else{
               tempDataInfo.accountTitle="转出账户";
               tempDataInfo.balancetTitle="代理商账户余额";
               tempDataInfo.titleVirtualAmount="溢价转出金额";
               tempDataInfo.titleAmount="实际转出金额";
               tempDataInfo.dialogTitle="转出资金";
               tempDataInfo.moneyCash=tempDataInfo.balance_cash;
               tempDataInfo.moneyVirtual=tempDataInfo.balance_virtual;
               $pageAgencyList.showAgencyMoney(tempDataInfo)
           }
        });
        $(":radio[name='input_funds']").on("change", function () {
            var value=$(this).val();
            var objData= $("#dialog_in").data("info");
            var balanceCount=value=="CASH"?objData.moneyCash:objData.moneyVirtual;
            $pageAgencyList.setBalanceCount(balanceCount);
            $pageAgencyList.checkParsleyFromMoney()
        });
        $("#txt_virtual_amount").on("change", function () {
            var virtual= $.trim($(this).val()).removesplit();
            var dialogData=$("#dialog_in").data("info");
            var amount=virtual;
            if(dialogData.commission && dialogData.commission.toString()!="0"){
                amount=parseInt(virtual)*(100-parseInt(dialogData.commission))/100
            }
            $("#txt_amount").val(amount).ExtNumeric("refresh");
            $pageAgencyList.checkParsleyFromMoney()
        });
        //转入转出资金post提交
        $("#btn_Funds_In").on("click", function () {
            if($pageAgencyList.checkParsleyFromMoney()){
                var dialogData=$("#dialog_in").data("info");
                var id = dialogData.id;
                var financeType = $(":radio[name='input_funds']:checked").val();
                var transType = dialogData.moneytype=="a_in"? "TRANSFER_IN" : "TRANSFER_OUT";
                var virtualAmount = $.trim($("#txt_virtual_amount").val()).removesplit();
                var amount = $.trim($("#txt_amount").val()).removesplit();
                $("#dialog_in").modal("hide");
                $.ExtAjax.initPost({
                    disableid:"#btn_Funds_In",
                    loadinfo:{place:"body"},
                    url:"/agency/tradeBalanceAgency.shtml",
                    data:JSON.stringify({id: id, finance_type: financeType, trans_type:transType,virtual_amount:virtualAmount,amount:amount}),
                    successcallback:function(res){
                        if(res.success) {
                            var info = res.data;
                            var tr=$("#agency_"+info.id).parent();
                            var row =$("#tb_AgencyList").ExtDataTable().getRowInfo(tr);
                            row.data()["balance_cash"]=info.balance_cash;
                            row.data()["balance_virtual"]=info.balance_virtual;
                            $pageAgencyList.bindTableTd(tr,  row.data());
                            tr.find(".num-float2,.num-int").ExtNumeric("refresh");
                            $.ExtNotify.success("保存成功");
                        }else{
                            $.ExtNotify.error(res.msg);
                            $("#dialog_in").modal("show");
                        }
                    }
                });
            }
        });
    });


</script>
