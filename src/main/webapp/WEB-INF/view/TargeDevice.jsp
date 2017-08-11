<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="modal fade" id="DeviceTarget" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" style="">
    <div class="modal-dialog modal-lg" role="document" style=" margin-top: 180px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title">移动设备定向</h4>
            </div>
            <div class="modal-body">
                <div class="form-horizontal">
                    <div class="form-group">
                        <label class="col-md-2 control-label">操作系统</label>
                        <div class="col-md-10">
                            <div class="row">
                                <div class="col-sm-1"><input  id="b_os" name="cbos" type="checkbox" data-plugin="switchery" data-change-operate="#b_os_detail"/></div>
                                <div class="col-sm-11 collapse" id="b_os_detail"></div>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-2 control-label">联网方式</label>
                        <div class="col-md-10">
                            <div class="row">
                                <div class="col-sm-1"><input id="b_connection" name="cbConnection" type="checkbox" data-plugin="switchery" data-change-operate="#b_connection_detail"/></div>
                                <div class="col-sm-11 collapse" id="b_connection_detail"></div>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-2 control-label">运营商</label>
                        <div class="col-md-10">
                            <div class="row">
                                <div class="col-sm-1"><input id="b_telcom" name="telcom" type="checkbox" data-plugin="switchery" data-change-operate="#b_telcom_detail"/></div>
                                <div class="col-sm-11 collapse" id="b_telcom_detail"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-graye8 waves-effect" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-default waves-effect waves-light" id="btn_SubmitDevice">确定</button>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    var $pageTargeDevice;
    $(function () {
        $pageTargeDevice = {
            cbSubmit:null,
            init:function(){
                $("#DeviceTarget").find('[data-plugin="switchery"]').ExtSwitchery({
                    changeCallback:function(element,bChecked){
                        var operateId=element.data("changeOperate");
                        if(bChecked){
                            $(operateId).stop().fadeIn();
                        }else{
                            $(operateId).stop().fadeOut();
                        }
                    }
                });
                this.bindEnumHtml();
            },
            bindEnumHtml:function(){
                $.ExtAjax.initGet({url:"/common/os.shtml",data:{}, success:function(res){$pageTargeDevice.bindEnumHtmlData("os",res)}});
                $.ExtAjax.initGet({url:"/common/connection.shtml",data:{}, success:function(res){$pageTargeDevice.bindEnumHtmlData("connection",res)}});
                $.ExtAjax.initGet({url:"/common/telcom.shtml",data:{}, success:function(res){$pageTargeDevice.bindEnumHtmlData("telcom",res)}});
            },
            bindEnumHtmlData:function(type,list){
                if(list && list.length>0){
                    var tempHtml= $.map(list,function(info,i){
                        return '<div class="col-xs-3 checkbox checkbox-custom"><input type="checkbox" id="cb_'+type+'_'+info.value+'" value="'+info.value+'" name="rd'+type+'"><label for="cb_'+type+'_'+info.value+'">'+info.name+'</label></div>';
                    }).join("");
                    $("#b_"+type+"_detail").html(tempHtml);
                }else{
                    $("#b_"+type+"_detail").html("数据暂无");
                }
            },
            setInitSelectValue:function(type,_thisDialog,jsonData){
                if(jsonData[type] && jsonData[type].length>0){
                    var educationIds=$.map(jsonData[type],function(info){
                        return "#cb_"+type+"_"+info.value;
                    }).join(",");
                    _thisDialog.find(educationIds).prop("checked",true);
                    _thisDialog.find("#b_"+type).prop("checked",false).trigger("click");
                }else{
                    _thisDialog.find("#b_"+type).prop("checked",true).trigger("click");
                }
            },
            show: function (jsonData) {
                var _thisDialog=$("#DeviceTarget");
                if(!$.isEmptyObject(jsonData)){
                    this.setInitSelectValue("os",_thisDialog,jsonData);
                    this.setInitSelectValue("connection",_thisDialog,jsonData);
                    this.setInitSelectValue("telcom",_thisDialog,jsonData);
                }else{
                    _thisDialog.find("#b_os").prop("checked",true).trigger("click");
                    _thisDialog.find("#b_connection").prop("checked",true).trigger("click");
                    _thisDialog.find("#b_telcom").prop("checked",true).trigger("click");
                }
                $("#DeviceTarget").modal("show");
            },
            hide: function () {
                $("#DeviceTarget").modal("hide");
            }
        };
        $pageTargeDevice.init();
        function getDeviceTargetValue(type,text,objBase){
            var errmsg="";
            var arr=[];
            var b=objBase.find("#b_"+type).prop("checked");
            var obj=$(":checkbox[name=rd"+type+"]:checked");
            if(b){
                if(obj.length>0){
                    arr= $.map(obj,function(info,i){
                        var objThis=$(info);
                        return {name:objThis.next().text(),value:objThis.val()}
                    });
                }else{
                    errmsg="请至少选择一种"+text
                }
            }
           return {success:arr.length>0,list:arr,err:errmsg};
        }
        $("#btn_SubmitDevice").off("click").on("click",function(){
            var errmsg="";
            var jsonDada={};
            var _thisDialog=$("#DeviceTarget");
            var osInfo=getDeviceTargetValue("os","操作系统",_thisDialog);
            var connectionInfo=getDeviceTargetValue("connection","联网方式",_thisDialog);
            var telcomInfo=getDeviceTargetValue("telcom","运营商",_thisDialog);
            if(osInfo.success){
                jsonDada.os=osInfo.list;
            }else{
                errmsg=osInfo.err;
            }
            if(connectionInfo.success){
                jsonDada.connection=connectionInfo.list;
            }else{
                errmsg=connectionInfo.err;
            }
            if(telcomInfo.success){
                jsonDada.telcom=telcomInfo.list;
            }else{
                errmsg=telcomInfo.err;
            }
            if(errmsg.length==0){
                $pageTargeDevice.hide();
                if($pageTargeDevice.cbSubmit)$pageTargeDevice.cbSubmit(jsonDada);
            }else{
                $.ExtNotify.error(errmsg);
            }

        });
    })
</script>