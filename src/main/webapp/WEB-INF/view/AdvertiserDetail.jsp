<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="page-advertiser"  data-menutype="advertiser">
    <div class="row">
        <div class="col-sm-12">
            <h4 class="page-title">
                <span class="page-operate-title">新建广告主</span>

                <ol class="breadcrumb">
                    <jsp:include page="HomeName.jsp"/>
                    <li>
                        <a href="/advertiser/list.shtml">广告主管理</a>
                    </li>
                    <li class="active page-operate-title">新建广告主</li>
                </ol>
            </h4>
        </div>
    </div>
    <!-- Main  -->
    <div class="row">
        <form  id="form_AdvertiserDetails"  class="form-parsley" data-parsley-validate>
            <div class="portlet portlet-border">
                <div class="portlet-heading portlet-default">
                    <h3 class="portlet-title text-dark">基本信息</h3>
                    <div class="portlet-widgets"></div>
                    <div class="clearfix"></div>
                </div>
                <div class="panel-collapse collapse in" id="advertiser-base">
                    <div class="portlet-body">
                        <div class="form-horizontal">
                            <div class="form-group">
                                <label class="col-md-2 control-label">广告主名称</label>
                                <div class="col-md-10">
                                    <span class="box-must">·</span><input class="form-control box-input" type="text" placeholder="广告主名称" id="uname" name="uname" value="${advertiser.uname}"  required="" data-parsley-maxlength="100" data-parsley-illegal-value="">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 control-label">公司名称</label>
                                <div class="col-md-10">
                                    <span class="box-must">·</span><input class="form-control box-input" type="text" placeholder="公司名称" id="corporation" name="corporation" value="${advertiser.corporation}"  required="" data-parsley-maxlength="120">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 control-label">服务费</label>
                                <div class="col-md-10">
                                    <div class="input-group w-420 parsley-err-absolute">
                                        <input id="commission" class="form-control" type="text"  required=""  data-parsley-type="integer" value="${advertiser.commission}"  name="commission" data-parsley-range="[0,100]" >
                                        <span class="input-group-addon">%</span>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group" id="pAdvertiserStatus">
                                <label class="col-md-2 control-label">广告主状态</label>
                                <div class="col-md-10">
                                    <label class="control-label" >${advertiser.customerStatus.description}
                                        <c:if test="${cs_status eq 2}">
                                            <i class="fa fa-warning icon-verify" id="i_StatusErrMsg" data-toggle="tooltip" data-container="body" data-placement="right" title="${advertiser.auditMsg}"></i>
                                        </c:if>
                                    </label>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 control-label">营业执照号码</label>
                                <div class="col-md-10">
                                    <span class="box-must">·</span><input  class="form-control box-input" type="text" placeholder="营业执照号码" id="corporationLicence" value="${advertiser.corporationLicence}" name="corporationLicence" required="" data-parsley-maxlength="100">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 control-label">行业分类</label>
                                <div class="col-md-10">
                                    <span class="box-must">·</span>
                                    <input type="text" id="txt_Industry" value="" style="display: none;"  required="" data-placeholder="-行业-" data-initvalue="${advertiser.industryId}" />
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 control-label">网站地址</label>
                                <div class="col-md-10">
                                    <span class="box-must">·</span><input  class="form-control box-input" type="text" placeholder="网站地址" id="siteUrl" value="${advertiser.siteUrl}" name="siteUrl" required="" data-parsley-maxlength="255" data-parsley-urlhttp="">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 control-label">营业执照图</label>
                                <div class="col-md-10">
                                    <div class="box-file-one w-420 parsley-box-file"><span class="box-must">·</span>
                                        <div class="box-file-base">
                                            <input  type="file" class="box-file" data-msgtip-top="点击上传营业执照图片或把图片拖拽至此处"data-msgtip-bottom="（有年检章,支持jpg/png,小于1M）" data-img-size="1024" id="certification" name="certification" data-url="${advertiser.certification}" data-parsley-required="">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 control-label">企业资质证明图</label>
                                <div class="col-md-10">
                                    <div class="box-file-arr w-400" id="qualification"  data-msgtip-top="点击上传企业资质证明图片或把图片拖拽至此处" data-msgtip-bottom="（支持jpg/png,小于1M）"  data-img-size="1024"  data-file-add="3" ></div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 control-label">网站ICP备案截图</label>
                                <div class="col-md-10">
                                    <div class="box-file-one w-420 parsley-box-file"><span class="box-must">·</span>
                                        <div class="box-file-base">
                                            <input  type="file" class="box-file" data-msgtip-top="点击上传网站ICP备案截图或把图片拖拽至此处"data-msgtip-bottom="（支持jpg/png,小于1M）" data-img-size="1024" id="icpImageUrl" name="icpImageUrl" data-url="${advertiser.icpImageUrl}" data-parsley-required="">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="portlet portlet-border">
                <div class="portlet-heading portlet-default">
                    <h3 class="portlet-title text-dark">联系人设置</h3>
                    <div class="portlet-widgets">
                        <a href="#advertiser-material" data-toggle="collapse"><i class="fa fa-minus-round"></i></a>
                    </div>
                    <div class="clearfix"></div>
                </div>
                <div class="panel-collapse collapse in" id="advertiser-material">
                    <div class="portlet-body">
                        <div class="form-horizontal">
                            <div class="form-group">
                                <label class="col-md-2 control-label">姓名</label>
                                <div class="col-md-10">
                                    <input class="form-control box-input" type="text" placeholder="姓名"  id="contactPerson" value="${advertiser.contactPerson}" data-parsley-maxlength="64">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 control-label">手机</label>
                                <div class="col-md-10">
                                    <input class="form-control box-input" type="text" placeholder="手机" name="contactPersonMobile" id="contactPersonMobile" value="${advertiser.contactPersonMobile}" data-parsley-phone="">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 control-label">邮箱</label>
                                <div class="col-md-10">
                                    <input class="form-control box-input" type="email" placeholder="邮箱"  id="contactPersonEmail" value="${advertiser.contactPersonEmail}" data-parsley-maxlength="90">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </form>
        <div class="clearfix button-list-actions">
            <label class="col-md-2 control-label"></label>
            <div class="col-md-10" >
                <button class="btn btn-default btn-large waves-effect waves-light" type="button" id="btn_SubmitAdvertiser">确定 </button>
                <a class="btn btn-graye8 m-l-5 btn-large waves-effect waves-light" type="button" href="/advertiser/list.shtml" >取消 </a>
            </div>
        </div>
    </div>
</div>
<script id="template_file_base" type="text/x-jsrender">
     <div class="box-file-base">
        <input type="file" class="box-file init-file file-not" data-msgtip-top="{{:msgtipTop}}" data-msgtip-bottom="{{:msgtipBottom}}" data-img-size="{{:imgSize}}" data-url="{{:url}}" data-file-add="{{:fileAdd}}" >
    </div>
</script>
<script type="text/javascript">
    $(function () {
        var $pageAdvertiser = {
            init: function () {
                $("#form_AdvertiserDetails").ExtParsley();
                $("#i_StatusErrMsg").tooltip();
                if('${advertiser.uid}'.length>0){
                    $("#commission").prop("disabled", true);
                    $(".page-operate-title").text("编辑广告主");
                    $("#corporation").attr({disabled:"disabled"});
                }else{
                    $(".page-operate-title").text("新建广告主");
                    $("#pAdvertiserStatus").empty().remove()
                }
                var strQualification='${advertiser.qualificationStr}';
                $("#qualification").data("arrUrl",$.parseJSON(strQualification.length>0?strQualification:'{}'));
                $(".page-advertiser .box-file-one .box-file").each(function(){
                    $pageAdvertiser.inituploadimg($(this))
                });
                $(".page-advertiser .box-file-arr").each(function(){
                    var _this=$(this);
                    var _thisData=_this.data();
                    var arrUrl=[];
                    var _thisDataOne = $.ExtCommon.getJsonClone(_thisData);
                    if(_thisData.arrUrl.toString().length>0 && !$.isEmptyObject(_thisData.arrUrl)) {
                        for (var k in _thisData.arrUrl) {
                            var urlone = _thisData.arrUrl[k];
                            arrUrl.push(urlone);
                            _thisDataOne.url = urlone;
                            _this.append($("#template_file_base").render(_thisDataOne))
                        }
                    }
                    if(parseInt(_thisData.fileAdd)>arrUrl.length ){
                        _thisDataOne.url="";
                        _this.append($("#template_file_base").render(_thisDataOne))
                    }
                    _this.find(".box-file").each(function(){$pageAdvertiser.inituploadimg($(this))});
                });
                $("#txt_Industry").ExtSelect2Tree({
                    url:"/target/industry.shtml",
                    initCallback:function(element){
                        element.parent().addClass("parsley-err-absolute")
                    },
                    changeCallback:function(element,value,text){
                        $("#form_AdvertiserDetails").ExtParsley().validate(element);
                    }
                });
            },
            inituploadimg:function(obj) {
                var _this = obj;
                var id=_this.attr("id");
                var thisdata = _this.data();
                var msgtop = thisdata.msgtipTop;
                var msgbottom = thisdata.msgtipBottom;
                var size = thisdata.imgSize && thisdata.imgSize.toString().length > 0 ? thisdata.imgSize : false;
                var imgurl =thisdata.url && thisdata.url.toString().length > 0 ? thisdata.url : false;
                var fileaddcount=thisdata.fileAdd && thisdata.fileAdd.toString().length > 0 ? thisdata.fileAdd : 1;
                if(imgurl){
                    _this.removeClass("file-not");
                }
                _this.removeClass("init-file");
                _this.ace_file_input({
                    'style': 'well',
                    'btn_choose': msgtop,
                    'bottom_msgtip': msgbottom,
                    'btn_change': null,
                    'no_icon': 'ace-icon fa fa-picture-o',
                    'allowExt': ["jpeg", "jpg", "png", "gif", "bmp"],
                    'allowMime': ["image/jpg", "image/jpeg", "image/png", "image/gif", "image/bmp"],
                    'droppable': true,
                    'thumbnail': 'fit',
                    "maxSize": size,
                    "imgSrcUrl": imgurl,
                    "showFileName": false,
                    "ajaxCallback": function (file, imgbase64, cb) {
                        $.ExtAjax.initPost({
                            url: "/advertiser/upload.shtml",
                            contentType: "application/x-www-form-urlencoded",
                            data: {
                                imageStr: imgbase64,
                                width: "100",
                                height: "100"
                            },
                            success: function (res) {
                                var result = $.ExtAjax.initResult(res);
                                if (result.success) {
                                    var inputFile= file.$element;
                                    file.$element.data("url", result.data).removeClass("file-not");
                                    $("#form_AdvertiserDetails").ExtParsley().validate(_this);
                                    var arrFile=inputFile.closest(".box-file-arr");
                                    if(fileaddcount>1 && arrFile.find(".file-not").length==0 && arrFile.children(".box-file-base").length<fileaddcount){
                                        var newfiledata={msgtipTop:msgtop,msgtipBottom:msgbottom,imgSize:size,url:"",fileAdd:fileaddcount};
                                        arrFile.append($("#template_file_base").render(newfiledata));
                                        $pageAdvertiser.inituploadimg(arrFile.find(".init-file"))
                                    }
                                    if (cb) cb();
                                } else {
                                    $.ExtNotify.error(result.msg);
                                }
                            }
                        });
                    },
                    "before_remove": function (obj) {
                        var _thisMoveFile=obj.siblings(".box-file");
                        var filebase=_thisMoveFile.closest(".box-file-base");
                        var fileparents= filebase.parent();
                        _thisMoveFile.removeData("url").removeAttr("data-url");
                        if(fileaddcount>1){
                            var filejson=$.ExtCommon.getJsonClone(_thisMoveFile.data());
                            filebase.empty().remove();
                            if(fileparents.find(".file-not").length==0){
                                fileparents.append($("#template_file_base").render(filejson));
                                $pageAdvertiser.inituploadimg(fileparents.find(".init-file"))
                            }
                        }else{
                            _thisMoveFile.addClass("file-not")
                        }
                        return true
                    },
                    "checkCallback": function (obj, err) {
                        if (err.length > 0) {
                            $.ExtNotify.error(err.join("<br />"));
                        }
                    }
                });
            },
            checkParsleyFrom:function () {
                $(".page-advertiser .parsley-box-file.box-file-one .box-file").each(function () {
                    var _this = $(this);
                    var imgurl = _this.data("url");
                    if ($.ExtCommon.isCheckEmpty(imgurl)) {
                        _this.attr({"data-parsley-required": ""})
                    } else {
                        _this.removeAttr("data-parsley-required")
                    }
                });
                $(".page-advertiser .parsley-box-file.box-file-arr").each(function () {
                    var bcheck = true;
                    var files = $(this).find(".box-file");
                    files.each(function () {
                        var imgurl = $(this).data("url");
                        if (!$.ExtCommon.isCheckEmpty(imgurl)) {
                            bcheck = false;
                            return false;
                        }
                    });
                    if (bcheck) {
                        files.attr({"data-parsley-required": ""})
                    } else {
                        files.removeAttr("data-parsley-required")
                    }
                });
                return $("#form_AdvertiserDetails").ExtParsley("validate");
            }
        };
        $pageAdvertiser.init();
//        新建 编辑广告主页面提交按钮
        $("#btn_SubmitAdvertiser").on("click", function(e){
            if ($pageAdvertiser.checkParsleyFrom()) {
                var uname = $.trim($("#uname").val());
                var corporation = $.trim($("#corporation").val());
                var commission = $.trim($("#commission").val());
                var customerStatusName = $.trim($("#customerStatus").val());
                var corporationLicence = $.trim($("#corporationLicence").val());
                var industryId = $("#txt_Industry").val();
                var siteUrl = $.trim($("#siteUrl").val());
                var certification = $.trim($("#certification").data("url"));

                var qualification={};
                $("#qualification").find(".box-file").each(function(i,obj){
                    var imgurl=$(obj).data("url");
                    if(!$.ExtCommon.isCheckEmpty(imgurl)){
                        qualification["qualificationurl"+(i+1)]=imgurl;
                    }
                });
                var icpImageUrl = $.trim($("#icpImageUrl").data("url"));

                var contactPerson = $.trim($("#contactPerson").val());
                var contactPersonMobile = $.trim($("#contactPersonMobile").val());
                var contactPersonEmail = $.trim($("#contactPersonEmail").val());
                var ajaxdata = {
                    uname:uname,
                    corporation:corporation,
                    commission:commission,
                    customerStatusName:customerStatusName,
                    corporationLicence:corporationLicence,
                    industryId:industryId,
                    siteUrl:siteUrl,
                    certification:certification,
                    qualification:qualification,
                    icpImageUrl:icpImageUrl,
                    contact_person:contactPerson,
                    contactPersonMobile:contactPersonMobile,
                    contact_person_email:contactPersonEmail
                };
                var formData = $("#form_AdvertiserDetails").data("advertiserinfo" );

                var uid='${advertiser.uid}';
                var url = "/advertiser/create.shtml";
                if(uid.length>0){
                    url = "/advertiser/edit.shtml";
                    ajaxdata.uid =uid
                } else {
                    url = "/advertiser/create.shtml";
                }
                $.ExtAjax.initPost({
                    disableid:"#btn_SubmitAdvertiser",
                    loadinfo:{place:"body"},
                    url: url,
                    data: JSON.stringify(ajaxdata),
                    successcallback: function (res) {
                        if (res.success) {
                            $.ExtNotify.success("保存成功!");
                            window.location.href="/advertiser/list.shtml";
                        } else {
                            $.ExtNotify.error(res.msg);
                        }
                    }
                });
            }
        });


    });
</script>
