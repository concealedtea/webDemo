<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div data-menutype="campaign">
    <div class="row">
        <div class="col-sm-12">
            <h4 class="page-title">
                <span class="page-operate-title">新建创意</span>
                <ol class="breadcrumb">
                    <jsp:include page="HomeName.jsp"/>
                    <li>
                        <a href="/campaign/view.shtml">订单管理</a>
                    </li>
                    <li>
                        <a href="/strategy/view.shtml?tabName=creative&cid=${campaign.cid}">${campaign.cname}</a>
                    </li>
                    <li class="active page-operate-title">新建创意</li>
                </ol>
            </h4>
        </div>
    </div>
    <div class="row">
        <form  id="form_CreativeDetails" class="form-parsley" data-parsley-validate>
            <div class="portlet portlet-border">
                <div class="portlet-heading portlet-default">
                    <h3 class="portlet-title text-dark">基本信息</h3>
                    <div class="portlet-widgets"></div>
                    <div class="clearfix"></div>
                </div>
                <div class="panel-collapse collapse in" id="creative-base">
                    <div class="portlet-body">
                        <div class="form-horizontal">
                            <div class="form-group">
                                <label class="col-md-2 control-label">创意名称</label>
                                <div class="col-md-10"><span class="box-must">·</span><input class="form-control box-input" type="text" id="txt_Name" placeholder="创意名称" name="Name" value="${creative.creativeName}" required="" data-parsley-maxlength="60" data-parsley-illegal-value=""></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 control-label">目标地址</label>
                                <div class="col-md-10"><span class="box-must">·</span><input  class="form-control box-input" type="text" id="txt_DestUrl" placeholder="目标地址" name="DestUrl" value="${creative.destUrl}" required="" data-parsley-maxlength="1000" data-parsley-urlhttp=""></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 control-label">备注</label>
                                <div class="col-md-10 parsley-err-absolute">
                                    <textarea  class="form-control text-area box-input" id="txt_Remark" rows="4" placeholder="备注" name="Remark" data-parsley-maxlength="1000">${creative.remark}</textarea>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="portlet portlet-border">
                <div class="portlet-heading portlet-default">
                    <h3 class="portlet-title text-dark">素材设置</h3>
                    <div class="portlet-widgets">
                        <a href="#creative-material" data-toggle="collapse"><i class="fa fa-minus-round"></i></a>
                    </div>
                    <div class="clearfix"></div>
                </div>
                <div class="panel-collapse collapse in" id="creative-material">
                    <div class="portlet-body">
                        <div class="form-horizontal">
                            <div class="form-group">
                                <label class="col-md-2 control-label">投放位置</label>
                                <div class="col-md-10">
                                    <div id="radio_Material" style="padding-top: 3px">
                                        <div id="material_Platform">
                                        </div>
                                        <div class="panel-table" style="">
                                            <div class="row row-th">
                                                <div class="row-td col-xs-1 radiobase"></div>
                                                <div class="row-td col-xs-3">广告形式规格</div>
                                                <div class="row-td col-xs-8">广告位置描述</div>
                                            </div>
                                            <div class="panel-group" id="paneltable_Material">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </form>
        <div class="clearfix button-list-actions">
            <label class="col-md-2 control-label"></label>
            <div class="col-md-10">
                <button class="btn btn-default btn-large waves-effect waves-light" type="button" id="btn_SubmitCreative">确定 </button>
                <a class="btn btn-graye8 m-l-5 btn-large waves-effect waves-light" type="button" href="/strategy/view.shtml?tabName=creative&cid=${campaign.cid}">取消 </a>
            </div>
        </div>
    </div>
</div>
<script id="template_Material_Platform" type="text/x-jsrender">
        <div class="radio radio-custom radio-inline">
        <input type="radio" id="rd_platform{{:platform_id}}" value="{{:platform_id}}" name="Platform">
        <label for="rd_platform{{:platform_id}}">{{:platform}}</label>
    </div>
</script>
<script id="template_Material_AdSpace" type="text/x-jsrender">
    <div class="panel panel-default" data-adspace-id="{{:id}}">
        <div class="panel-heading">
            <div class="row row-tr collapse-title collapsed" id="collapse_title_{{:id}}"  data-toggle="collapse"  data-parent="#paneltable_Material" data-target="#collapse_AdSpace_{{:id}}" aria-expanded="false">
                <div class="row-td col-xs-1">
                    <div class="radio radio-custom"><input type="radio" id="rd_AdSpace_{{:id}}" name="AdSpace" value="{{:id}}"><label for="rd_AdSpace_{{:id}}"></label></div>
                </div>
                <div class="row-td col-xs-3">{{:ad_format}}{{if w>0 && h>0}}({{:w}}x{{:h}}){{/if}}</div>
                <div class="row-td col-xs-8">{{:description}}</div>
            </div>
        </div>
        <div id="collapse_AdSpace_{{:id}}" class="panel-collapse collapse collapse-detail">
            <div class="panel-body">
                <div class="img-demo">
                    <p class="text-left">素材示例</p>
                    <p class="">
                        {{for imageDemos}}<img src="{{:url}}">{{/for}}
                    </p>
                </div>
                {{for specifics}}
                <div class="collapse-detail-tr" id="collapse_detail_{{:id}}">
                    <p>上传素材{{:num}}</p>
                    <div class="form-horizontal">
                        {{for listInput}}
                        <div class="form-group">
                            <label class="col-md-2 control-label">{{:label}}</label>
                            <div class="col-md-10"><span class="box-must">·</span>
                                {{if type=="text"}}
                                <input class="form-control box-input" type="text" placeholder="{{:label}}" name="{{:name}}" data-input-minlen="{{:minlen}}" data-input-maxlen="{{:maxlen}}">
                                {{else type=="file"}}
                                 <div class="box-input">
                                    <input class="box-file"  type="file" name="{{:name}}"  data-msgtip-top="点击上传创意图片或把图片拖拽至此处" data-msgtip-bottom="（支持jpg/png,{{:width}}x{{:height}},小于{{:size}}kb）"data-img-size="{{:size}}" data-img-width="{{:width}}" data-img-height="{{:height}}">
                                </div>
                                {{/if}}
                            </div>
                        </div>
                        {{/for}}

                    </div>
                </div>
                {{/for}}
            </div>
        </div>
    </div>
</script>
<script type="text/javascript">
    $(function () {
        var $pageCreativeDetail = {
            init: function () {
                $("#form_CreativeDetails").ExtParsley();
                var tempInitData='${creativeData}';
                var initFormData=null;
                if(!$.ExtCommon.isCheckEmpty(tempInitData)){
                    var jsonData=$.parseJSON(tempInitData);
                    if(!$.isEmptyObject(jsonData)) initFormData=jsonData;
                }
                if(!$.isEmptyObject(initFormData)){
                    $(".page-operate-title").text("编辑创意");
                    $("#form_CreativeDetails").data("creativeinfo",initFormData);
                }else{
                    $("#form_CreativeDetails").data("creativeinfo",{});
                    $(".page-operate-title").text("新建创意")
                }
                this.getDataCreativeAdSpace(function(){
                    var formData=$("#form_CreativeDetails").data("creativeinfo");
                    var valuePlatform="";
                    var valueAdSpace;
                    if(!$.ExtCommon.isCheckEmpty(formData.adSpacePositionId)){
                        $.each($pageCreativeDetail.adSpaceOriginal,function(i,info){
                            if(info.id==formData.adSpacePositionId) valuePlatform=info.platform_id
                        });
                        valueAdSpace=formData.adSpacePositionId;
                    }else{
                        valuePlatform=$pageCreativeDetail.listPlatform[0].platform_id;
                    }
                    $(":radio[name='Platform'][value='"+valuePlatform+"']").prop("checked", true);
                    if(!$.isEmptyObject(formData)){
                        $pageCreativeDetail.changeAdSpace(valuePlatform,valueAdSpace,false);
                        if(formData.strategyId){
                            $(":radio[name='Platform']").prop("disabled", true).parent().addClass("check-disabled");
                            $(":radio[name='AdSpace']").prop("disabled", true).parent().addClass("check-disabled").closest(".collapse-title").addClass("check-disabled");
                        }
                        if(formData.materials && formData.materials.length>0){
                            var _thisAdSpace=$("#collapse_AdSpace_"+formData.adSpacePositionId);
                            $.each(formData.materials,function(i,info){
                                var _thisDetail=_thisAdSpace.find("#collapse_detail_"+info.crt_size);
                                _thisDetail.data("materialid",info.id);
                                if(info.title)_thisDetail.find(":text[name=title]").val(info.title);
                                if(info.description)_thisDetail.find(":text[name=description]").val(info.description);
                                if(info.imageUrl)_thisDetail.find(":file[name=imageUrl]").data("url",info.imageUrl);
                                if(info.image2Url)_thisDetail.find(":file[name=image2Url]").data("url",info.image2Url);
                            });
                        }
                        $pageCreativeDetail.inituploadimg();
                    }else{
                        $pageCreativeDetail.changeAdSpace(valuePlatform,valueAdSpace,true);
                    }
                });
            },
            getDataCreativeAdSpace:function(cb){
                $.ExtAjax.initGet({url:"/common/adSpacePosition.shtml",data:{},
                    success:function(res){
                        $pageCreativeDetail.adSpaceOriginal=res;
                        var getDataAdSpacePart=function(list,field){
                            var part = {};
                            var result=[];
                            for( var i = 0; i < list.length; i++) {
                                var info=list[i];
                                var tempKey=[];
                                var tempjson={};
                                for(var f=0;f<field.length;f++){
                                    var key=field[f];
                                    tempjson[key]=info[key];
                                }
                                tempKey.push(info[field[0]]);
                                var strKey=tempKey.join(",");
                                info.w=0;
                                info.h=0;
                                for(var s=0;s< info.specifics.length;s++) {
                                    var sinfo = info.specifics[s];
                                    if(s==0){
                                        if(sinfo.width>0) info.w=sinfo.width
                                        if(sinfo.height>0) info.h=sinfo.height
                                    }
                                    sinfo.listInput = [];
                                    if (sinfo.title) {
                                        var tempArrTitle = sinfo.title.split(",");
                                        sinfo.listInput.push({type: "text", label: "标题", name: "title", minlen: tempArrTitle.length == 1 ? 0 : tempArrTitle[0], maxlen: tempArrTitle.length == 2 ? tempArrTitle[1] : tempArrTitle[0]})
                                    }
                                    if (sinfo.description) {
                                        var tempArrDescription = sinfo.description.split(",");
                                        sinfo.listInput.push({type: "text", label: "描述", name: "description", minlen: tempArrDescription.length == 1 ? 0 : tempArrDescription[0], maxlen: tempArrDescription.length == 2 ? tempArrDescription[1] : tempArrDescription[0]});
                                    }
                                    if (sinfo.imageUrl) {
                                        sinfo.listInput.push({type: "file", label: "图片"+(sinfo.image2Url?"一":""), name: "imageUrl", width: sinfo.imageUrl.width, height: sinfo.imageUrl.height, size: sinfo.imageUrl.size})
                                    }
                                    if (sinfo.image2Url) {
                                        sinfo.listInput.push({type: "file", label: "图片二", name: "image2Url", width: sinfo.image2Url.width, height: sinfo.image2Url.height, size: sinfo.image2Url.size})
                                    }
                                    sinfo.num=info.specifics.length==1?"":s+1;
                                }
                                if(part[strKey] == undefined){
                                    part[strKey]=[];
                                    part[strKey].push(info);
                                    result.push(tempjson)
                                }else{
                                    part[strKey].push(info);
                                }
                            }
                            return {part:part,result:result};
                        };
                        var tempAdSpace=getDataAdSpacePart(res,["platform_id","platform"]);
                        $pageCreativeDetail.adSpacePart=tempAdSpace.part;
                        $pageCreativeDetail.listPlatform= tempAdSpace.result;
                        $("#material_Platform").html($("#template_Material_Platform").render($pageCreativeDetail.listPlatform));
                        if(cb)cb()
                    }
                });
            },
            changeAdSpace:function(platform,adspace,isInitUpload){
                var list=$pageCreativeDetail.adSpacePart[platform];
                adspace= $.ExtCommon.isCheckEmpty(adspace)?list[0].id:adspace;
                isInitUpload= $.ExtCommon.isCheckEmpty(isInitUpload)?true:isInitUpload;
                $("#paneltable_Material").html($("#template_Material_AdSpace").render(list));
                if(isInitUpload) $pageCreativeDetail.inituploadimg();
                $("#collapse_title_"+adspace).trigger("click");

            },
            checkedAdSpace:function(obj){
                var p= obj.closest(".panel");
                if(p.hasClass("active")){
                    if(p.hasClass("opend")){
                        p.removeClass("opend")
                    }else{
                        p.addClass("opend")
                    }
                }else{
                    p.siblings(".active").removeClass("active opend");
                    p.addClass("active opend").find(":radio").prop("checked", true);
                }
            },
            inituploadimg:function(){
                $("#paneltable_Material .box-file").each(function(){
                    var _this=$(this);
                    var thisdata=_this.data();
                    var msgtop=thisdata.msgtipTop;
                    var msgbottom=thisdata.msgtipBottom;
                    var width=thisdata.imgWidth;
                    var height=thisdata.imgHeight;
                    var size=thisdata.imgSize.toString().length>0?thisdata.imgSize:false;
                    var imgurl=thisdata.url;
                    _this.ace_file_input({
                        'style':'well',
                        'btn_choose':msgtop,
                        'bottom_msgtip':msgbottom,
                        'btn_change':null,
                        'no_icon':'ace-icon fa fa-picture-o',
                        'allowExt': ["jpeg", "jpg", "png", "gif" , "bmp"],
                        'allowMime': ["image/jpg", "image/jpeg", "image/png", "image/gif", "image/bmp"],
                        'droppable':true,
                        'thumbnail':'fit',
                        "maxSize":size,
                        "imgSrcUrl":imgurl,
                        "showFileName":false,
                        "whType":"less",
                        "sizeWidth":parseInt(width),
                        "sizeHeight":parseInt(height),
                        "ajaxCallback":function(file,imgbase64,cb){
                            $.ExtAjax.initPost({
                                url:"/creative/upload.shtml",
                                contentType: "application/x-www-form-urlencoded",
                                data:{imageStr:imgbase64,width:width,height:height},
                                success:function(res){
                                    var result=$.ExtAjax.initResult(res);
                                    if(result.success){
                                        file.$element.data("url",result.data);
                                        $("#form_CreativeDetails").ExtParsley().validate(_this);
                                        if(cb) cb();
                                    }else{
                                        $.ExtNotify.error(result.msg);
                                    }

                                }
                            });
                        },
                        "before_remove":function(obj){
                            obj.siblings(".box-file").removeData("url");
                            return true
                        },
                        "checkCallback":function(obj,err){
                            if(err.length>0){
                                $.ExtNotify.error(err.join("<br />"));
                            }
                        }
                    })
                });
            }
        };
        $pageCreativeDetail.init();
        $('#material_Platform').on('change', ':radio[name="Platform"]', function () {
            var value=$(this).val();
            $pageCreativeDetail.changeAdSpace(value,null,true);
        });
        $('#paneltable_Material').on('click', '.collapse-title', function () {
            var _this=$(this);
            if(!_this.hasClass("check-disabled")){
                $pageCreativeDetail.checkedAdSpace(_this);
                return true
            }else{
                return false;
            }
        });
        function checkParsleyFrom(){
            var checkAdSpaceMsg=checkAdSpaceValue();
            if(checkAdSpaceMsg.length>0)  $.ExtNotify.error(checkAdSpaceMsg);
            return $("#form_CreativeDetails").ExtParsley("validate") && checkAdSpaceMsg.length==0;
        }
        $("#btn_SubmitCreative").on("click", function (e) {
            if (checkParsleyFrom()) {
                var name = $.trim($("#txt_Name").val());
                var destUrl = $.trim($("#txt_DestUrl").val());
                var remark = $.trim($("#txt_Remark").val());
                var dataAdSpaceInfo=getAdSpaceInfo();
                var adSpacePositionId=dataAdSpaceInfo.adspaceid;
                var listAdSpace=$.map(dataAdSpaceInfo.listValues,function(m){
                    if(!m.ischeck){
                        var temp={crt_size: m.id};
                        if(m.materialid)temp["id"]=m.materialid;
                        if(m.title)temp["title"]=m.title;
                        if(m.description)temp["description"]=m.description;
                        if(m.imageUrl)temp["imageUrl"]=m.imageUrl;
                        if(m.image2Url)temp["image2Url"]=m.image2Url;
                        return temp;
                    }
                });
                var ajaxdata={name:name,destUrl:destUrl,remark:remark,adSpacePositionId:adSpacePositionId,materials:listAdSpace};
                ajaxdata.cid='${campaign.cid}';
                var formData=$("#form_CreativeDetails").data("creativeinfo");
                var url="/creative/create.shtml";
                if($.isEmptyObject(formData)){
                    url="/creative/create.shtml";
                }else{
                    url="/creative/edit.shtml";
                    ajaxdata.id=formData.creativeId
                }
                $.ExtAjax.initPost({
                    disableid:"#btn_SubmitCreative",
                    loadinfo:{place:"body"},
                    url:url,
                    data:JSON.stringify(ajaxdata),
                    successcallback:function(res){
                        if(res.success) {
                            $.ExtNotify.success("保存成功!");
                            window.location.href="/strategy/view.shtml?tabName=creative&cid=${campaign.cid}"
                        }else{
                            $.ExtNotify.error(res.msg);
                        }
                    }
                });
            }
        });
        function getAdSpaceInfo(){
            var adspaceid= $(":radio[name='AdSpace']:checked").val();
            var platform=$(":radio[name='Platform']:checked").val();
            var panelData=$pageCreativeDetail.adSpacePart[platform];
            var info=null;
            $.each(panelData,function(i,p){
                if(p.id==adspaceid) info=p;
            });
            var listValues=[];
            $("#paneltable_Material >.panel.active .collapse-detail-tr").each(function(i){
                var _this=$(this);
                var _thisData=_this.data();
                var specifics=info.specifics[i];
                var inputList=specifics.listInput;
                var inputJson={};
                var emptycount=0;
                var existvaluecount=0;
                for(var key =0;key<inputList.length;key++){
                    var infokey=inputList[key];
                    if(infokey.type=="text"){
                        inputJson[infokey.name]=_this.find("[name='"+infokey.name+"']").eq(0).val();
                    }else if(infokey.type=="file"){
                        inputJson[infokey.name]=_this.find("[name='"+infokey.name+"']").eq(0).data("url");
                    }
                    if($.ExtCommon.isCheckEmpty(inputJson[infokey.name])){
                        emptycount++
                    } else{
                        existvaluecount++
                    }
                }
                if(_thisData.materialid)  inputJson.materialid=_thisData.materialid;
                inputJson.id=specifics.id;
                inputJson.num=specifics.num;
                var ischeck=inputList.length==emptycount;
                inputJson["ischeck"]=ischeck;
                inputJson["isexistvalue"]=existvaluecount>0;
                listValues.push(inputJson);
            });
            return {adspaceid:adspaceid,platform:platform,info:info,listValues:listValues};
        }
        function checkAdSpaceValue(){
            var msg="";
            $("#paneltable_Material .parsley-check").each(function(){
                var _this=$(this);
                if(!$.ExtCommon.isCheckEmpty(_this.attr("data-parsley-required"))) _this.removeAttr("data-parsley-required");
                if(!$.ExtCommon.isCheckEmpty(_this.attr("data-parsley-length"))) _this.removeAttr("data-parsley-length");
                if(!$.ExtCommon.isCheckEmpty(_this.attr("data-parsley-maxlength"))) _this.removeAttr("data-parsley-maxlength");
            });
            var p= $("#paneltable_Material >.panel.active");
            if(p.length>0){
                var detailTr=p.find(".collapse-detail-tr");
                var adSpaceInfo=getAdSpaceInfo();
                var listValues=adSpaceInfo.listValues;
                var info=adSpaceInfo.info;
                var checkListValue=$.map(listValues,function(m){
                    if(!m.ischeck) return {id: m.id};
                });
                if(checkListValue.length==0){
                    if(info.specifics.length==1){
                        checkListValue.push({id:info.specifics[0].id});
                    }else{

                    }
                }
                if(checkListValue.length>0){
                    $.each(checkListValue,function(n,m){
                        for(var i=0;i<info.specifics.length;i++){
                            var specific=info.specifics[i];
                            if(specific.id== m.id){
                                var _this=detailTr.eq(i);
                                var inputList=specific.listInput;
                                for(var c =0;c<inputList.length;c++){
                                    var control=inputList[c];
                                    if(control.type=="text"){
                                        var temptext={"data-parsley-required":""};
                                        if(control.minlen>0){
                                            temptext["data-parsley-length"]="["+control.minlen+","+control.maxlen+"]";
                                        }else{
                                            temptext["data-parsley-maxlength"]=control.maxlen;
                                        }
                                        _this.find("[name='"+control.name+"']").eq(0).attr(temptext).addClass("parsley-check");
                                    }else if(control.type=="file"){
                                        var _thisFile=_this.find("[name='"+control.name+"']").eq(0);
                                        var _thisFileData=_thisFile.data("url");
                                        if($.ExtCommon.isCheckEmpty(_thisFileData)){
                                            _this.find("[name='"+control.name+"']").eq(0).attr({"data-parsley-required":""}).addClass("parsley-check");
                                        }
                                    }
                                }
                            }
                        }
                    });
                }else{
                    msg="请完善一个上传素材"
                }
            }else{
                msg="请选择广告位置"
            }
            return msg
        }
    });
</script>