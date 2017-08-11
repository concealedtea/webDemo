<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="page-user"  data-menutype="user">
    <div class="row">
        <div class="col-sm-12">
            <h4 class="page-title">
                <span>用户管理</span>
                <ol class="breadcrumb">
                    <jsp:include page="HomeName.jsp"/>
                    <li class="active">用户管理</li>
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
                            <button class="btn btn-default waves-effect waves-light" id="btn_AddUser">新建用户</button>
                            <div class="form-inline pull-right" id="div_Search">
                                <input type="text" class="form-control" id="search_keyword" placeholder="用户ID,用户名称">
                                <button type="button" class="btn btn-default waves-effect waves-light btn-md" id="btn_Search">搜索</button>
                            </div>
                        </div>
                        <div class="table-custom p-t-10">
                            <table class="table table-bordered m-0" id="tb_UserList" width="100%">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>用户名</th>
                                        <th>角色</th>
                                        <th>状态</th>
                                    </tr>
                                </thead>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="modal fade" id="UserDetails" role="dialog" aria-labelledby="myModalLabel" style="">
            <div class="modal-dialog" role="document" style=" margin-top: 180px;">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                        <h4 class="modal-title">新建用户</h4>
                    </div>
                    <div class="modal-body">
                        <form class="form-horizontal form-parsley" id="form_UserDetails" data-parsley-validate>
                            <div class="form-group">
                                <label class="col-md-2 control-label">用户名称</label>
                                <div class="col-md-10"><span class="box-must">·</span><input class="form-control box-input" type="text" id="txt_Name" placeholder="3-16个字符，支持英文字母，数字，下划线" name="name" required="" data-parsley-pattern="\w{3,16}"></div>
                            </div>
                            <div class="form-group panel-user-add">
                                <label class="col-md-2 control-label">密码</label>
                                <div class="col-md-10"><span class="box-must">·</span><input class="form-control box-input" type="password" id="txt_Password" placeholder="6-16个字符，支持英文字母，数字，下划线" name="password" required="" data-parsley-pattern="\w{6,16}"></div>
                            </div>
                            <div class="form-group panel-user-add">
                                <label class="col-md-2 control-label">重复密码</label>
                                <div class="col-md-10"><span class="box-must">·</span><input class="form-control box-input" type="password" id="txt_PasswordAgain" placeholder="重复密码" name="passwordagain" required="" data-parsley-pattern="\w{6,16}" data-parsley-equalto="#txt_Password"></div>
                            </div>
                            <div class="form-group panel-user-edit">
                                <label class="col-md-2 control-label">密码</label>
                                <div class="col-md-10" style="display: block;padding-top: 7px"><a class="" id="btn_PasswordEdit" href="javascript:;" style="text-decoration: underline;">修改密码</a></div>
                                <div class="col-md-10" style="display: none;"><span class="box-must">·</span><input class="form-control box-input" style="width:340px;" type="password" id="txt_PasswordEidt" placeholder="6-16个字符，支持英文字母，数字，下划线" name="password"  data-parsley-pattern="\w{6,16}"><a id="btn_PasswordCancel" class="" href="javascript:;" style="text-decoration: underline;padding-left: 5px;">取消修改</a></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 control-label">状态</label>
                                <div class="col-md-10">
                                    <div class="radio radio-custom radio-inline" style="margin-right: 14px;"><input type="radio" name="status" id="rd_status1" value="1"><label for="rd_status1">启用</label></div>
                                    <div class="radio radio-custom radio-inline"><input type="radio" name="status" id="rd_status-1" value="-1"><label for="rd_status-1">停用</label></div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 control-label">所属角色</label>
                                <div class="col-md-10">
                                    <div class="radio radio-custom radio-inline"><input type="radio" name="user_role" id="rd_user_roleagency" value="agency"><label for="rd_user_roleagency">代理商</label></div>
                                    <div class="radio radio-custom radio-inline"><input type="radio" name="user_role" id="rd_user_roleadvertiser" value="advertiser"><label for="rd_user_roleadvertiser">广告主</label></div>
                                </div>
                            </div>
                            <div class="form-group root_advertiser" id="panel_advertiser">
                                <label class="col-md-2 control-label">广告主</label>
                                <div class="col-md-10"><span class="box-must">·</span><select id="ddl_advertiser" class="w-400 form-control select2"  required=""  style="width: 400px"></select></div>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-graye8 waves-effect" data-dismiss="modal">关闭</button>
                        <button type="button" class="btn btn-default waves-effect waves-light" id="btn_UserSubmit">确定</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(function () {
        var $pageUserList = {
            init: function () {
                var self = this;
                self.bindList();
                self.bindAdvertiserData();
                $("#form_UserDetails").ExtParsley();
            },
            bindAdvertiserData: function () {
                $.ExtAjax.initGet({url:"/advertiser/tree.shtml",data:{},
                    success:function(res){
                        var listAdvertiser=[];
                        for (var i = 0; i < res.length; i++) {
                            var info=res[i];
                            listAdvertiser.push({id:info.id,text:info.name});
                        }
                        $("#ddl_advertiser").ExtSelect2({placeholder:"-广告主-",minimumResultsForSearch:1,allowClear: true,select2Info:{data:listAdvertiser}});
                    }
                });
            },
            bindList: function () {
                var self=this;
                $("#tb_UserList").ExtDataTable({
                    columns: [
                        {"data": "id", sClass: "w-100","bSortable": false},
                        {"data": "user_name",sClass: "","bSortable": false},
                        {"data": null,  sClass: "w-400", "bSortable": false},
                        {"data": "status",sClass: "w-80", "bSortable": false}
                    ],
                    infoExt:{name:"用户列表",url:"/user/list.shtml",ajaxDataPara:function(){
                        var para={};
                        var keyword = $.trim($("#search_keyword").val());
                        if (!$.ExtCommon.isCheckEmpty(keyword)) {para.search_keyword = keyword;}
                        return para;
                    }},
                    createdRow: function (row, data, index) {
                        self.bindTableTd(row,data)
                    },
                    drawCallback: function () {
                    }
                });
            },
            refreshList: function () {
                $("#tb_UserList").ExtDataTable("refresh")
            },
            bindTableTd:function(row,data){
                $('td', row).eq(1).attr("id","userid_"+data.id).html('<a class="btn-operate-name edit-user" href="javascript:;">'+data.user_name+'</a>');
                $('td', row).eq(2).html(data.user_role.toLowerCase()=="agency"?"代理商":"广告主 - "+data.uname+"("+data.uid+")");
                $('td', row).eq(3).html(data.status=="1"?"启用":"停用");
            },
            showUserDetails: function (json) {
                $("#txt_Name").val(json["user_name"]).prop("disabled", !json.isadd);
                $("#txt_Password,#txt_PasswordAgain").val("").prop("disabled", !json.isadd);
                $(":radio[name='status'][value='"+json.status+"']").prop("checked", true);
                $(":radio[name='user_role'][value='"+json["user_role"].toLowerCase()+"']").prop({"checked":true}).trigger("change");
                $("#ddl_advertiser").val(json.uid.toString()).trigger("change").prop("disabled", !json.isadd);
                $("#form_UserDetails").ExtParsley("reset");
                if(json.isadd){
                    $(":radio[name='user_role']").prop("disabled", false).parent().removeClass("check-disabled");
                    $(".panel-user-add").show();
                    $(".panel-user-edit").hide()
                }else{
                    $(":radio[name='user_role']").prop("disabled", true).parent().addClass("check-disabled");
                    $(".panel-user-add").hide();
                    $(".panel-user-edit").show();
                    $("#btn_PasswordCancel").trigger("click")
                }
                $("#UserDetails").data("originalData",json).modal("show").find(".modal-title").text(json.modal_title);
            },
            checkParsleyFrom:function (json) {
                var userrole = $.trim($(":radio[name='user_role']:checked").val());
                if(userrole=="agency"){
                    $("#ddl_advertiser").removeAttr("required")
                }else{
                    $("#ddl_advertiser").attr("required","")
                }
                if(json.isadd){
                    $(".panel-user-add input[required]").attr("required","")
                }else{
                    $(".panel-user-add input[required]").removeAttr("required")
                }
                return $("#form_UserDetails").ExtParsley("validate");
            }
        };
        $pageUserList.init();
        $("#search_keyword").on("keydown",function(event){
            if(event.keyCode == 13) {
                $pageUserList.refreshList();
            }
        });
        $("#btn_Search").on("click", function () {
            $pageUserList.refreshList();
        });
        $("#btn_AddUser").on("click", function (e) {
            var json={"user_name":"","user_role":"agency","status":1,"uid":"","modal_title":"新建用户",isadd:true};
            $pageUserList.showUserDetails(json)
        });
        $(":radio[name='user_role']").on("change", function (e) {
            var value=$(this).val();
            if(value=="agency"){
                $("#panel_advertiser").hide()
            }else{
                $("#panel_advertiser").show();
            }
        });
        $("#ddl_advertiser").on("change", function (e) {
            $("#form_UserDetails").ExtParsley().validate("#ddl_advertiser");
        });
        $("#btn_PasswordEdit").on("click", function () {
            $(this).parent().hide().next().show().find(".box-input").attr("required","").val();
        });
        $("#btn_PasswordCancel").on("click", function () {
            $(this).prevAll(".box-input").val("").removeAttr("required").parent().hide().prev().show();
        });
        $('#tb_UserList').on('click', '.edit-user', function () {
            var tr = $(this).closest('tr');
            var trData = $("#tb_UserList").ExtDataTable().getRowInfo(tr).data();
            $.ExtAjax.initGet({url:"/user/read.shtml",data:{id:trData.id},
                success:function(res){
                    var result=$.ExtAjax.initResult(res);
                    if(result.success){
                        var json=result.data;
                        json.modal_title="编辑用户";
                        json.isadd=false;
                        $pageUserList.showUserDetails(json);
                    }else{
                        $.ExtNotify.error(result.msg);
                    }
                }
            });
        });
        $("#btn_UserSubmit").on("click", function () {
            var originalData=$("#UserDetails").data("originalData");
            if ($pageUserList.checkParsleyFrom(originalData)) {
                var name = $.trim($("#txt_Name").val());
                var password = $.trim($("#txt_Password").val());
                var status = $.trim($(":radio[name='status']:checked").val());
                var userrole = $.trim($(":radio[name='user_role']:checked").val()).toLowerCase();
                var tempjson = {user_name: name, status: status,user_role:userrole};
                var uid = $("#ddl_advertiser").val();
                if(userrole!="agency"){tempjson.uid=uid}
                var url="";
                if(originalData.isadd){
                    url="/user/create.shtml";
                    tempjson.password=password
                }else{
                    url="/user/update.shtml";
                    tempjson.id=originalData.id;
                    var editPwd=$("#txt_PasswordEidt");
                    if(editPwd.is(":visible")){
                        tempjson.password=$.trim(editPwd.val());
                    }
                }
                $("#UserDetails").modal("hide");
                console.log(tempjson);
                $.ExtAjax.initPost({
                    disableid:"#btn_UserSubmit",
                    loadinfo:{place:"body"},
                    url:url,
                    data:JSON.stringify(tempjson),
                    successcallback:function(res){
                        if(res.success) {
                            var info = res.data;
                            if(originalData.isadd){
                                $pageUserList.refreshList();
                            }else{
                                var tr=$("#userid_"+info.id).parent();
                                var row = $("#tb_UserList").ExtDataTable().getRowInfo(tr);
                                for(var i in info){
                                    if(row.data()[i])row.data()[i]=info[i]
                                }
                                $pageUserList.bindTableTd(tr, info);
                            }
                            $.ExtNotify.success("保存成功");
                        }else{
                            $.ExtNotify.error(res.msg);
                            $("#UserDetails").modal("show");
                        }
                    }
                });
            }
        });
    });
</script>
