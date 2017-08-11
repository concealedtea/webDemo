<%@ page  %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div data-menutype="agency_finance">
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
            <div class="col-md-6">
                <div class="card-box widget-box-1 bg-white">
                    <h4 class="text-dark">现金账户</h4>
                    <h2 class="text-dark text-center">￥<span class="num-float2" id="cash_balance">${balance_cash}</span></h2>
                </div>
            </div>
            <div class="col-md-6">
                <div class="card-box widget-box-1 bg-white">
                    <h4 class="text-dark">虚拟账户</h4>
                    <h2 class="text-dark text-center">￥<span class="num-float2" id="virtual_balance">${balance_virtual}</span></h2>
                </div>
            </div>
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
                                <div class="time-range-base">
                                    <input type="text" class="form-control time-range" id="time_Range" readonly="readonly">
                                    <i class="fa fa-calendar"></i>
                                </div>
                                <button type="button" class="btn btn-default waves-effect waves-light btn-md" id="btn_Search">搜索</button>
                            </div>
                        </div>
                        <div class="table-custom p-t-10">
                            <table class="table table-bordered" id="tb_AgencyFinanceList" width="100%">
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
        var $pageAgencyFinance = {
            init: function () {
                $("#financeType").ExtSelect2({placeholder:"-交易类型-",select2Info:{initvalue:"CASH"}});
                $("#time_Range").ExtDateTime({infoExt:{type:"range", changeCallback:function(start, end, label){}}}).initDatePickerRange();
                $pageAgencyFinance.bindList();
                $.ExtNumeric.init();
            },
            bindList: function () {
                var self=this;
                $("#tb_AgencyFinanceList").ExtDataTable({
                    columns: [
                        { "data": "trans_time" ,sClass:"w-200 ","bSortable": false},
                        { "data": "trans_in",sClass:"w-200 num-float2 text-right ","bSortable": false},
                        { "data": "trans_out",sClass:"w-200 num-float2 text-right ","bSortable": false},
                        { "data": "description",sClass:"","bSortable": false}
                    ],
                    infoExt:{name:"财务列表",url:"/agency/financeFlowList.shtml",ajaxDataPara:function(){
                        var para={};
                        var arrTime =$("#time_Range").ExtDateTime("getDateRangeValue");
                        if(arrTime.length > 0){
                            para.start_date = arrTime[0];
                            para.end_date = arrTime[1];
                        }
                        para.account_type = $("#financeType").val();
                        return para;
                    }},
                    drawCallback: function () {
                        $.ExtNumeric.init();
                    }
                });
            },
            refreshList: function () {
                $("#tb_AgencyFinanceList").ExtDataTable("refresh");
            }
        };
        $pageAgencyFinance.init();
        $("#btn_Search").on("click",function () {
            $pageAgencyFinance.refreshList();
        });
    });


</script>
