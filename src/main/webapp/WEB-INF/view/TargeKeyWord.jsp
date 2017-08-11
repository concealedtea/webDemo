<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="modal fade" id="KeyWordTarget" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" style="">
    <div class="modal-dialog modal-lg" role="document" style=" margin-top: 180px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title">关键字定向</h4>
            </div>
            <div class="modal-body">
                <textarea  class="form-control text-area" id="txt_KeyWordTarget" rows="7" placeholder="多个关键字以逗号分隔，最多可设置150个关键字，每个关键字不超过30个字符" name="TargetKeyWord"></textarea>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-graye8 waves-effect" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-default waves-effect waves-light" id="btn_SubmitKeyWord">确定</button>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    var $pageTargeKeyWord;
    $(function () {
        $pageTargeKeyWord = {
            cbSubmit:null,
            init:function(){

            },
            show: function (jsonData) {
                $("#txt_KeyWordTarget").val($.ExtCommon.isCheckEmpty(jsonData)?"":jsonData);
                $("#KeyWordTarget").modal("show");
            },
            hide: function () {
                $("#KeyWordTarget").modal("hide");
            }
        };
        $pageTargeKeyWord.init();
        $("#txt_KeyWordTarget").off("keydown").on("keydown",function(event){
            if(event.keyCode == 13) {
                if(window.event)
                    window.event.returnValue = false;
                else
                    event.preventDefault();//for firefox
            }
        });
        $("#btn_SubmitKeyWord").off("click").on("click",function(){
            var errmsg="";
            var value= $.trim($("#txt_KeyWordTarget").val());
            var arrValue=value.replace(/，/g, ',').split(",");
            if(arrValue.length>150){
                errmsg = "关键字个数不能超过150个";
            }else {
                $.each(arrValue, function (i, info) {
                    var len = $.ExtCommon.getStringLen(info);
                    if (len == 0) {
                        errmsg = "关键字不能为空";
                        return false
                    } else if (len > 30) {
                        errmsg = "每个关键字不超过30个字符";
                        return false
                    }
                });
            }
            if(errmsg.length==0){
                $pageTargeKeyWord.hide();
                if($pageTargeKeyWord.cbSubmit)$pageTargeKeyWord.cbSubmit(value);
            }else{
                $.ExtNotify.error(errmsg);
            }

        });
    })
</script>