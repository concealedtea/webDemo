<%@ page  %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div  data-menutype="finance">
    <div class="row">
        <div class="col-sm-12">
            <h4 class="page-title">
                <span>财务管理</span>
                <ol class="breadcrumb">
                    <jsp:include page="HomeName.jsp"/>
                    <li class="active">财务管理</li>
                </ol>
            </h4>
        </div>
    </div>
    <div class="row">
        <div class="div">
                <div class="<c:choose><c:when test="${isAgency}">col-md-6</c:when><c:otherwise>col-md-4</c:otherwise></c:choose>">
                    <div class="card-box widget-box-1 bg-white">
                        <h4 class="text-dark">现金账户</h4>
                        <h2 class="text-dark text-center">￥<span class="num-float2 counter" id="cash_balance">${balanceCash}</span></h2>
                    </div>
                </div>
                <div class="<c:choose><c:when test="${isAgency}">col-md-6</c:when><c:otherwise>col-md-4</c:otherwise></c:choose>">
                    <div class="card-box widget-box-1 bg-white">
                        <h4 class="text-dark">虚拟账户</h4>
                        <h2 class="text-dark text-center">￥<span class="num-float2 counter" id="virtual_balance">${balanceVirtual}</span></h2>
                    </div>
                </div>
                <c:choose>
                    <c:when test="${isAgency}"></c:when>
                    <c:otherwise>
                        <div class="col-md-4">
                            <div class="card-box widget-box-1 bg-white" id="div_daybudget">
                                <h4 class="text-dark">每日限额</h4>
                                <c:choose>
                                    <c:when test="${customer_status eq '0'}">
                                        <h2 class="text-custom text-center">
                                            ￥<a class="editable editable-click btn-operate" id="btn_DayBudget"  href="javascript:;"><span id="day_budget" class="num-int">${dayBudget}</span></a>
                                        </h2>
                                    </c:when>
                                    <c:otherwise>
                                        <h2 class="text-dark text-center">
                                            ￥<span class="num-int">${dayBudget}</span>
                                        </h2>
                                    </c:otherwise>
                                </c:choose>
                                <input type="hidden" id="hid_uid" value="${uid}" />
                            </div>
                        </div>
                    </c:otherwise>
                </c:choose>
        </div>
        <div class="col-sm-12">
            <div class="card-box">
                <div class="row">
                    <div class="col-lg-12">
                        <div>
                            <div class="form-inline pull-right">
                                <select id="financeType" class="w-110 form-control select2">
                                    <option value="CASH">现金</option>
                                    <option value="VIRTUAL">虚拟金</option>
                                </select>
                                <div class="time-range-base"><input type="text" class="form-control time-range" id="time_Range" readonly="readonly"><i class="fa fa-calendar"></i></div>
                                <button type="button" class="btn btn-default waves-effect waves-light btn-md" id="btn_Search">搜索</button>
                            </div>
                        </div>
                        <div class="table-custom p-t-10">
                            <table class="table table-bordered" id="tb_FinanceList" width="100%">
                                <thead>
                                    <tr>
                                        <th>日期</th>
                                        <th>存入</th>
                                        <th>支出</th>
                                        <th>备注</th>
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
        var $pageFinance = {
            init: function () {
                $("#financeType").ExtSelect2({placeholder:"-交易类型-",select2Info:{initvalue:"CASH"}});
                $("#time_Range").ExtDateTime({infoExt:{type:"range", changeCallback:function(start, end, label){}}}).initDatePickerRange();
                $.ExtNumeric.init();
                this.bindList();
                this.initXEditable()
            },
            bindList: function () {
                var self=this;
                $("#tb_FinanceList").ExtDataTable({
                    columns: [
                        { "data": "trans_time" ,sClass:"w-200","bSortable": false},
                        { "data": "transfer_in",sClass:"w-200 num-float2 text-right ","bSortable": false},
                        { "data": "transfer_out",sClass:"w-200 num-float2 text-right ","bSortable": false},
                        { "data": "description",sClass:"","bSortable": false}
                    ],
                    infoExt:{name:"财务列表",url: "/finance/financeFlow.shtml",ajaxDataPara:function(){
                        var para={};
                        var arrTime =$("#time_Range").ExtDateTime("getDateRangeValue");
                        if(arrTime.length > 0){
                            para.startDate = arrTime[0];
                            para.endDate = arrTime[1];
                        }
                        para.accountType = $("#financeType").val();
                        return para;
                    }},
                    drawCallback: function () {
                        $.ExtNumeric.init();
                    }
                });
            },
            refreshList: function () {
                $("#tb_FinanceList").ExtDataTable("refresh");
            },
            initXEditable: function () {
                $("#btn_DayBudget").ExtXEditable({
                    type: "text",
                    url: "/finance/setDayBudget.shtml",
                    params: function (params) {
                        var newParams = {uid: $("#hid_uid").val()};
                        newParams.day_budget = $.trim(params.value).removesplit();
                        return JSON.stringify(newParams);
                    },
                    initControlOther: function () {
                        $(".editable-container :text").ExtNumeric({numtype:"int",vMax:"99999999"});
                    },
                    successCallback: function (res, newValue) {
                        $("#day_budget").text(newValue.removesplit()).ExtNumeric("refresh");
                    }
                });
            }
        };
        $pageFinance.init();
        $("#btn_Search").on("click",function () {
            $pageFinance.refreshList();
        });
    });


</script>
