<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="modal fade page-audience" id="AudienceTarget" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" style="">
    <div class="modal-dialog modal-lg" role="document" style=" margin-top: 180px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title">人口属性定向</h4>
            </div>
            <div class="modal-body">
                <div class="form-horizontal">
                    <div class="form-group">
                        <label class="col-md-2 control-label">性别</label>
                        <div class="col-md-10">
                            <div class="row">
                                <div class="col-sm-1"><input  id="b_gender" name="bgender" type="checkbox" data-plugin="switchery" data-change-operate="#b_gender_detail"/></div>
                                <div class="col-sm-11 collapse" id="b_gender_detail"></div>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-2 control-label">学历</label>
                        <div class="col-md-10">
                            <div class="row">
                                <div class="col-sm-1"><input id="b_education" name="beducation" type="checkbox" data-plugin="switchery" data-change-operate="#b_education_detail"/></div>
                                <div class="col-sm-11 collapse" id="b_education_detail"></div>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-2 control-label">婚恋</label>
                        <div class="col-md-10">
                            <div class="row">
                                <div class="col-sm-1"><input id="b_marriage" name="marriage" type="checkbox" data-plugin="switchery" data-change-operate="#b_marriage_detail"/></div>
                                <div class="col-sm-11 collapse" id="b_marriage_detail"></div>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-2 control-label">年龄</label>
                        <div class="col-md-10">
                            <div class="row">
                                <div class="col-sm-1"><input id="b_age" name="bage" type="checkbox" data-plugin="switchery" data-change-operate="#b_age_detail"/></div>
                                <div class="col-sm-11 collapse" id="b_age_detail"><div class="col-xs-10 p-0 "><input type="text" id="txt_age"></div></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-graye8 waves-effect" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-default waves-effect waves-light" id="btn_SubmitAudience">确定</button>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    var $pageTargeAudience;
    $(function () {
        $pageTargeAudience = {
            initRangeSliderSeparator:"~",
            cbSubmit:null,
            init:function(){
                $("#txt_age").ionRangeSlider({type: "double", grid: true, min: 5, max: 60, from: 5, to: 60, "input_values_separator":$pageTargeAudience.initRangeSliderSeparator});
               $("#AudienceTarget").find('[data-plugin="switchery"]').ExtSwitchery({
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
                $.ExtAjax.initGet({url:"/common/gender.shtml",data:{},
                    success:function(res){
                        if(res && res.length>0){
                           var tempHtml= $.map(res,function(info,i){
                               return '<div class="col-xs-2 radio radio-custom"><input type="radio" id="rd_gender_'+info.value+'" value="'+info.value+'" name="rdgender"><label for="rd_gender_'+info.value+'">'+info.name+'</label></div>'
                           }).join("");
                           $("#b_gender_detail").html(tempHtml);
                        }else{
                            $("#b_gender_detail").html("数据暂无");
                        }
                    }
                });
                $.ExtAjax.initGet({url:"/common/education.shtml",data:{},
                    success:function(res){
                        if(res && res.length>0){
                            var tempHtml= $.map(res,function(info,i){
                                return '<div class="col-xs-2 checkbox checkbox-custom"><input type="checkbox" id="cb_education_'+info.value+'" value="'+info.value+'" name="cbeducation"><label for="cb_education_'+info.value+'">'+info.name+'</label> </div>';
                            }).join("");
                            $("#b_education_detail").html(tempHtml);
                        }else{
                            $("#b_education_detail").html("数据暂无");
                        }
                    }
                });
                $.ExtAjax.initGet({url:"/common/marriage.shtml",data:{},
                    success:function(res){
                        if(res && res.length>0){
                            var tempHtml= $.map(res,function(info,i){
                                return '<div class="col-xs-2 checkbox checkbox-custom"><input type="checkbox" id="cb_marriage_'+info.value+'" value="'+info.value+'" name="cbmarriage"><label for="cb_marriage_'+info.value+'">'+info.name+'</label> </div>';
                            }).join("");
                            $("#b_marriage_detail").html(tempHtml);
                        }else{
                            $("#b_marriage_detail").html("数据暂无");
                        }
                    }
                });
            },
            show: function (jsonData) {
                var _thisDialog=$("#AudienceTarget");
                if(!$.isEmptyObject(jsonData)){
                    var arrAge;
                    if(jsonData.age && (arrAge=jsonData.age.split($pageTargeAudience.initRangeSliderSeparator)).length==2){
                        _thisDialog.find("#txt_age").data("ionRangeSlider").update({from: arrAge[0], to: arrAge[1]});
                        _thisDialog.find("#b_age").prop("checked",false).trigger("click");
                    }else{
                        _thisDialog.find("#b_age").prop("checked",true).trigger("click");
                    }
                    if(jsonData.gender && jsonData.gender.length>0){
                        _thisDialog.find("#rd_gender_"+jsonData.gender[0].value).prop("checked",true);
                        _thisDialog.find("#b_gender").prop("checked",false).trigger("click");
                    }else{
                        _thisDialog.find("#b_gender").prop("checked",true).trigger("click");
                    }
                    if(jsonData.education && jsonData.education.length>0){
                        var educationIds=$.map(jsonData.education,function(info){
                            return "#cb_education_"+info.value;
                        }).join(",");
                        _thisDialog.find(educationIds).prop("checked",true);
                        _thisDialog.find("#b_education").prop("checked",false).trigger("click");
                    }else{
                        _thisDialog.find("#b_education").prop("checked",true).trigger("click");
                    }
                    if(jsonData.marriage && jsonData.marriage.length>0){
                        var marriageIds=$.map(jsonData.marriage,function(info){
                            return "#cb_marriage_"+info.value;
                        }).join(",");
                        _thisDialog.find(marriageIds).prop("checked",true);
                        _thisDialog.find("#b_marriage").prop("checked",false).trigger("click");
                    }else{
                        _thisDialog.find("#b_marriage").prop("checked",true).trigger("click");
                    }
                }else{
                    _thisDialog.find("#b_age").prop("checked",true).trigger("click");
                    _thisDialog.find("#b_gender").prop("checked",true).trigger("click");
                    _thisDialog.find("#b_education").prop("checked",true).trigger("click");
                    _thisDialog.find("#b_marriage").prop("checked",true).trigger("click");
                }
                $("#AudienceTarget").modal("show");
            },
            hide: function () {
                $("#AudienceTarget").modal("hide");
            }
        };
        $pageTargeAudience.init();
        $("#btn_SubmitAudience").off("click").on("click",function(){
            var errmsg="";
            var jsonDada={};
            var _thisDialog=$("#AudienceTarget");
            var bGender=_thisDialog.find("#b_gender").prop("checked");
            var objgender=$(":radio[name=rdgender]:checked");
            if(bGender){
                if(objgender.length>0){
                    jsonDada.gender=[{name:objgender.next().text(),value:objgender.val()}];
                }else{
                    errmsg="请选择性别"
                }
            }
            var bEducation=_thisDialog.find("#b_education").prop("checked");
            var objEducation=$(":checkbox[name=cbeducation]:checked");
            if(bEducation){
                if( objEducation.length>0){
                    jsonDada.education= $.map(objEducation,function(obj,i){
                        var objThis=$(obj);
                        return {name:objThis.next().text(),value:objThis.val()}
                    });
                }else{
                    errmsg="请至少选择一种学历"
                }
            }

            var bMarriage=_thisDialog.find("#b_marriage").prop("checked");
            var objMarriage=$(":checkbox[name=cbmarriage]:checked");
            if(bMarriage){
                if( objMarriage.length>0){
                    jsonDada.marriage= $.map(objMarriage,function(obj,i){
                        var objThis=$(obj);
                        return {name:objThis.next().text(),value:objThis.val()}
                    });
                }else{
                    errmsg="请至少选择一种婚恋"
                }
            }
            var bAge=_thisDialog.find("#b_age").prop("checked");
            if(bAge){
                jsonDada.age=_thisDialog.find("#txt_age").val();
            }
            if(errmsg.length==0){
                $pageTargeAudience.hide();
                if($pageTargeAudience.cbSubmit)$pageTargeAudience.cbSubmit(jsonDada);
            }else{
                $.ExtNotify.error(errmsg);
            }

        });
    })
</script>