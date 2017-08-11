<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="page-advertiser" data-menutype="advertiser">
    <div class="row">
        <div class="col-sm-12">
            <h4 class="page-title">
                <span>广告主管理</span>
                <ol class="breadcrumb">
                    <jsp:include page="HomeName.jsp"/>
                    <li class="active">广告主管理</li>
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
                            <a class="btn btn-default waves-effect waves-light" id="btn_AddAdvertiser" href="/advertiser/detail.shtml">新建广告主</a>
                            <div class="form-inline pull-right" id="div_Search">
                                <input type="text" class="form-control w-250" id="search_keyword" placeholder="广告主ID，广告主名称">
                                <button type="button" class="btn btn-default waves-effect waves-light btn-md" id="btn_Search">搜索</button>
                            </div>
                        </div>
                        <div class="table-custom p-t-10">
                            <table class="table table-bordered" id="tb_AdvertiserList" width="100%">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>广告主名称</th>
                                        <th>状态</th>
                                        <th>服务费(%)</th>
                                        <th>账户余额(￥)</th>
                                        <th>今日花费(￥)</th>
                                        <th>当前限额(￥)</th>
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

    <!-- dialog -->
    <div class="modal fade" id="FundsIn" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" style="">
        <div class="modal-dialog" role="document" style="margin-top: 180px;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h4 class="modal-title">转入资金</h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal" id="form_AdvertiserFinance" data-parsley-validate>
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
                        <div class="form-group root_advertiser">
                            <label class="col-md-3 control-label" id="label_balance">代理商账户余额</label>
                            <div class="col-md-9 p-t-7"><span id="balance_count" class="num-float2">0</span></div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label" id="label_account">转入账户</label>
                            <div class="col-md-9 p-t-7" id="advertiser_Name"></div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">服务费</label>
                            <div class="col-md-9 p-t-7" ><span id="advertiser_Commission"></span>%</div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label" id="label_virtual_amount">溢价转入金额</label>
                            <div class="col-md-9">
                                <span class="box-must">·</span>
                                <input class="form-control w-186 num-int" id="txt_virtual_amount" type="text" name="virtual_amount"  required=""  data-parsley-num-multiple=""  parsley-special="keyup">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label"  id="label_amount">实际转入金额</label>
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
</div>
<script type="text/javascript">
    $(function () {
        var $pageAdvertiserList = {
            init: function () {
                $("#form_AdvertiserFinance").ExtParsley();
                this.bindList()
            },
            bindList: function () {
                var self=this;
                $("#tb_AdvertiserList").ExtDataTable({
                    columns:[
                        {"data": "uid", sClass: "w-90", "bSortable": false},
                        {"data": "uname",sClass: "", "bSortable": false},
                        {"data": "customerStatus",  sClass: "w-100 text-center p-r-30", "bSortable": false},
                        {"data": "commission",  sClass: "w-100 text-right", "bSortable": false},
                        {"data": "balance",sClass: "num-float2 w-100 text-right", "bSortable": false},
                        {"data": "dayCost", sClass: "num-float2 w-100 text-right", "bSortable": false},
                        {"data": "dayBudget", sClass: "w-100 text-right","bSortable": false},
                        {"data": null,sClass: "w-140 text-center", "bSortable": false}
                    ],
                    infoExt:{name:"广告主列表",url:"/agency/advertiserList.shtml",ajaxDataPara:function(){
                        var para={};
                        var keyword = $.trim($("#search_keyword").val());
                        if (!$.ExtCommon.isCheckEmpty(keyword)) {para.search_keyword = keyword;}
                        return para;
                    }},
                    createdRow: function (row, data, index) {
                        $('td', row).eq(1).attr("id", "advertiser_" + data.uid);
                        self.bindTableTd(row, data)
                    },
                    drawCallback: function () {
                        $.ExtNumeric.init();
                        self.initXEditable();
                        $("[data-toggle='tooltip']").tooltip();
                    }
                });
            },
            refreshList: function () {
                $("#tb_AdvertiserList").ExtDataTable("refresh");
            },
            bindTableTd:function(row,data){
                $('td', row).eq(1).html('<a class="btn-operate-name" href="/advertiser/detail.shtml?uid='+data.uid+'">'+data.uname+'</a> ');
                if(data.status == 2){
                    $('td', row).eq(2).html(data.customerStatus+'<i class="fa fa-warning icon-verify pull-right name-icon" data-toggle="tooltip" data-container="body" data-placement="right" title="'+data.audit_msg+'"></i>');
                }
                $('td', row).eq(3).html(data.commission + "%");
                $('td', row).eq(4).html(data.balance);
                var td7html='<a href="/report/user.shtml?uid='+data.uid+'" target="_blank"  class="btn-operate">报告</a>';
                if(data.status == 0){
                    $('td', row).eq(6).html('<a class="editable editable-click editable-init" data-operatetype="dayBudget"  href="javascript:;"><span class="num-int">' + data.dayBudget + '</span></a>');
                    $('td', row).eq(7).html('<a href="javascript:;" name="a_in" class="a_funds btn-operate">转入</a><a href="javascript:;" class="a_funds btn-operate" name="a_out">转出</a>'+td7html);
                }else{
                    $('td', row).eq(6).html('<span class="num-int">' + data.dayBudget + '</span>');
                    $('td', row).eq(7).html(td7html);
                }
            },
            initXEditable: function () {
                $("#tb_AdvertiserList .editable-click.editable-init").each(function () {
                    var _this = $(this);
                    _this.removeClass("editable-init");
                    var _thisData = _this.data();
                    var row =$("#tb_AdvertiserList").ExtDataTable().getRowInfo(_this.closest('tr'));
                    var rowData=row.data();
                    var operatetype = _this.attr("data-operatetype");
                    if (operatetype == "dayBudget") {
                        _this.ExtXEditable({
                            type: "text",
                            url: "/finance/setDayBudget.shtml",
                            params: function (params) {
                                var newParams = {uid: rowData.uid};
                                newParams.day_budget = $.trim(params.value).removesplit();
                                return JSON.stringify(newParams);
                            },
                            initControlOther: function () {
                                $(".editable-container :text").ExtNumeric({numtype:"int",vMax:"99999999"});
                            },
                            successCallback: function (res, newValue) {
                                row.data().daybudget = newValue.removesplit();
                            }
                        });
                    }
                });
            },
            setBalanceCount: function (value) {
                $("#balance_count").text(value).ExtNumeric("refresh")
            },
            shoAdvertiserMoney: function (json) {
                $("#cash_funds").prop("checked", true);
                $("#txt_virtual_amount,#txt_amount").val("");
                $("#advertiser_Name").html(json.uname);
                $("#advertiser_Commission").html(json.commission);
                this.setBalanceCount(json.moneyCash);
                $("#label_account").html(json.accountTitle);
                $("#label_balance").html(json.balancetTitle);
                $("#label_virtual_amount").html(json.titleVirtualAmount);
                $("#label_amount").html(json.titleAmount);
                $("#form_AdvertiserFinance").ExtParsley().reset(true);
                $("#FundsIn").data("info", json).modal("show").find(".modal-title").html(json.dialogTitle);
            },
            checkParsleyFrom: function (json) {
                return $("#form_AdvertiserFinance").ExtParsley("validate");
            }
        };
        $pageAdvertiserList.init();
        $("#btn_Search").on("click", function () {
            $pageAdvertiserList.refreshList();
        });
        $("#search_keyword").off("keydown").on("keydown",function(event){
            if(event.keyCode == 13) {
                $pageAdvertiserList.refreshList();
            }
        });
        //转入转出资金按钮触发
        $('#tb_AdvertiserList').on('click', '.a_funds', function () {
            var type = $(this).attr("name");
            var tr = $(this).closest('tr');
            var trData =$("#tb_AdvertiserList").ExtDataTable().getRowInfo(tr).data();
            var tempDataInfo= $.ExtCommon.getJsonClone(trData);
            tempDataInfo.moneytype=type;
            if(type == "a_in"){
                $.ExtAjax.initGet({url: "/agency/getAgencyInfoById.shtml",
                    success:function(res) {
                        var result = $.ExtAjax.initResult(res);
                        if(result.success) {
                            var json = result.data;
                            tempDataInfo.accountTitle="转入账户";
                            tempDataInfo.balancetTitle="代理商账户余额";
                            tempDataInfo.titleAmount="实际转入金额";
                            tempDataInfo.dialogTitle="转入资金";
                            tempDataInfo.dialogTitle="转入资金";
                            tempDataInfo.moneyCash=json.balance_cash;
                            tempDataInfo.moneyVirtual=json.balance_virtual;
                            $pageAdvertiserList.shoAdvertiserMoney(tempDataInfo);
                        }else{
                            $.ExtNotify.error(result.msg);
                        }
                    }
                });
            }else{
                $.ExtAjax.initGet({url: "/agency/getAdvertiserBalanceInfo.shtml", data: {uid: trData.uid},
                    success:function(res) {
                        var result = $.ExtAjax.initResult(res);
                        if(result.success) {
                            var json = result.data;
                            tempDataInfo.accountTitle="转出账户";
                            tempDataInfo.balancetTitle="广告主账户余额";
                            tempDataInfo.titleVirtualAmount="溢价转出金额";
                            tempDataInfo.titleAmount="实际转出金额";
                            tempDataInfo.dialogTitle="转出资金";
                            tempDataInfo.moneyCash=json.balance_cash;
                            tempDataInfo.moneyVirtual=json.balance_virtual;
                            $pageAdvertiserList.shoAdvertiserMoney(tempDataInfo);
                        }else{
                            $.ExtNotify.error(result.msg);
                        }
                    }
                });
            }
        });
        $(":radio[name='input_funds']").on("change", function () {
            var value=$(this).val();
            var objData= $("#FundsIn").data("info");
            var balanceCount=value=="CASH"?objData.moneyCash:objData.moneyVirtual;
            $pageAdvertiserList.setBalanceCount(balanceCount);
            $pageAdvertiserList.checkParsleyFrom()
        });
        $("#txt_virtual_amount").on("change", function () {
            var virtual= $.trim($(this).val()).removesplit();
            var dialogData=$("#FundsIn").data("info");
            var amount=virtual;
            if(dialogData.commission && dialogData.commission.toString()!="0"){
                amount=parseInt(virtual)*(100-parseInt(dialogData.commission))/100
            }
            $("#txt_amount").val(amount).ExtNumeric("refresh");
            $pageAdvertiserList.checkParsleyFrom()
        });
        $("#btn_Funds_In").on("click", function () {
            if($pageAdvertiserList.checkParsleyFrom()){
                var dialogData=$("#FundsIn").data("info");
                var uid = dialogData.uid;
                var uname=dialogData.uname;
                var accountType = $(":radio[name='input_funds']:checked").val();
                var transType = dialogData.moneytype=="a_in"? "TRANSFER_IN" : "TRANSFER_OUT";
                var virtualAmount = $.trim($("#txt_virtual_amount").val()).removesplit();
                var amount = $.trim($("#txt_amount").val()).removesplit();
                $("#FundsIn").modal("hide");
                $.ExtAjax.initPost({
                    disableid:"#btn_Funds_In",
                    loadinfo:{place:"body"},
                    url:"/agency/tradeFlow.shtml",
                    //url:"/resources/data/CreativeDetail-Save.json",
                    data:JSON.stringify({uid: uid, uname:uname,account_type: accountType, trans_type:transType,virtual_amount:virtualAmount,amount:amount}),
                    successcallback:function(res){
                        if(res.success) {
                            var info = res.data;
                            var tr=$("#advertiser_"+info.uid).parent();
                            var row =$("#tb_AdvertiserList").ExtDataTable().getRowInfo(tr);
                            row.data()["balance"]=info.balance;
                            $pageAdvertiserList.bindTableTd(tr,  row.data());
                            tr.find(".num-float2,.num-int").ExtNumeric("refresh");
                            $pageAdvertiserList.initXEditable();
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
