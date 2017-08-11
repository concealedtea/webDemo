<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div>
    <div>
        <a class="btn btn-default waves-effect waves-light" href="/creative/detail.shtml?cid=${cid}">新建创意</a>
        <div class="form-inline pull-right">
            <input type="text" class="form-control" id="search_CreativeKeyWord" placeholder="创意ID,创意名称">
            <button type="button" class="btn btn-default waves-effect waves-light btn-md" id="btn_SearchCreative">搜索</button>
        </div>
    </div>
    <div class="table-custom p-t-10">
        <table class="table table-bordered dataTable no-footer" id="tb_ListCreative" width="100%">
            <thead>
            <tr>
                <th>ID</th>
                <th>创意名称</th>
                <th>使用情况</th>
                <th>广告形式</th>
                <th>投放位置</th>
                <th>目标地址</th>
            </tr>
            </thead>
        </table>
    </div>
</div>
<script id="template_creativetable_name" type="text/x-jsrender">
    <a class="btn-operate-name" href="/creative/detail.shtml?creative_id={{:id}}">{{:name}}</a>
    <div class="pull-right dropdown name-icon">
        <a href="javascript:;" class="details-operate" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            <span ><i class="fa fa-cog"></i></span>
        </a>
        <ul class="dropdown-menu animated flipInX w-m100" aria-labelledby="dLabel">
            <li><a class="" href="/creative/detail.shtml?creative_id={{:id}}">编辑</a></li>
            {{if !relation}} <li><a class="btn_delete_creative"  href="javascript:;">删除</a></li>{{/if}}
        </ul>
    </div>
</script>
<script id="template_creativetable_trdetails" type="text/x-jsrender">
    <div class="tr-details">
        <div class="row">
            <div class="col-xs-4">素材</div>
            <div class="col-xs-1">状态</div>
            <div class="col-xs-1">操作</div>
            <div class="col-xs-6"></div>
        </div>
        {{for detail}}
         <div class="row row-tr">
            <div class="col-xs-4">{{:img}}</div>
            <div class="col-xs-1 creative-status">{{:statusName}}</div>
            <div class="col-xs-1">
                {{if operationid==-1}}--{{else}}<a href="javascript:;" class="details-operate editable-click" data-materialid="{{:id}}" data-statusoriginal="{{:status}}" data-statusoperation="{{:operationid}}">{{:operationname}}</a>{{/if}}
            </div>
            <div class="col-xs-6"></div>
        </div>
       {{/for}}
    </div>
</script>
<script type="text/javascript">
    var $pageCreativeList;
    $(function () {
        $pageCreativeList = {
            tablecreative:null,
            initOption: { id: "-99", text: "全部" },
            initStatusByCreative: [{ id: "1", text: "启用",showid:["6"] },{ id: "6", text: "暂停",showid:["0","1"] }],
            init: function () {
                $pageCreativeList.bindListCreative();
            },
            bindListCreative: function () {
                var self=this;
                self.tablecreative=$("#tb_ListCreative").ExtDataTable({
                    columns: [
                        { "data": "id" ,sClass:"w-90","bSortable": false},
                        { "data": "name",sClass:"p-r-30 w-min-300","bSortable": false},
                        { "data": "relation",sClass:"w-80","bSortable": false},
                        { "data": "ad_format",sClass:"w-80","bSortable": false},
                        { "data": "plat_form",sClass:"w-250","bSortable": false},
                        { "data": "dest_url",sClass:"","bSortable": false}
                    ],
                    infoExt:{name:"创意列表",url:"/creative/list.shtml",ajaxDataPara:function(){
                        var para={};
                        var keyword = $.trim($("#search_CreativeKeyWord").val());
                        if (!$.ExtCommon.isCheckEmpty(keyword)) {para.search_keyword = keyword;}
                        para.cid = '${cid}';
                        return para;
                    }},
                    createdRow: function (row, data, index) {
                        $('td', row).eq(0).html('<span>'+data.id+'</span><div class="pull-right"><a class="details-control details-operate"  href="javascript:;"><i class="fa updown"></i></a></div>');
                        $('td', row).eq(1).html($("#template_creativetable_name").render({name:data.name,id:data.id,relation:data.relation}));
                        if(data.relation) {
                            $('td', row).eq(2).html("已绑定策略");
                        } else {
                            $('td', row).eq(2).html("未绑定策略");
                        }
                        $('td', row).eq(4).html('<div class="ellipsis" title="'+data.plat_form+'">'+data.plat_form+'</div>');
                    },
                    drawCallback: function () {
                    }
                });
            },
            refreshCreativeList: function () {
                $("#tb_ListCreative").ExtDataTable("refresh");
            }
        };
        ///创意搜索
        $("#btn_SearchCreative").on('click',  function () {
            $pageCreativeList.refreshCreativeList()
        });
        $("#search_CreativeKeyWord").on("keydown",function(event){
            if(event.keyCode == 13) {
                $pageCreativeList.refreshCreativeList();
            }
        });
        $('#tb_ListCreative').on('click', '.btn_delete_creative', function () {
            var tr = $(this).closest('tr');
            var rowData=$("#tb_ListCreative").ExtDataTable().getRowInfo(tr).data();
            $.ExtAjax.initGet({url:"/creative/delete.shtml",data:{creative_id:rowData.id},
                success:function(res){
                    var result=$.ExtAjax.initResult(res);
                    if(result.success) {
                        $pageCreativeList.refreshCreativeList();
                    } else {
                        $.ExtNotify.error(result.msg);
                    }
                }
            })
        });
        //创意展开详情
        $('#tb_ListCreative').on('click', '.details-control', function () {
            var tr = $(this).closest('tr');
            var row=$("#tb_ListCreative").ExtDataTable().getRowInfo(tr);
            if ( row.child.isShown()) {
                $("#tb_ListCreative").ExtDataTable().hideTrDetailHtml(tr,row);
            }
            else {
                var tempRowData=row.data();
                if($.ExtCommon.isCheckEmpty(tempRowData.detailData)){
                    tempRowData.detailData=[];
                    $.ExtAjax.initGet({url:"/creative/materialPreview.shtml",data:{creative_id:tempRowData.id},
                        success:function(res){
                            var result=$.ExtAjax.initResult(res);
                            if(result.success) {
                                var tempDataList = [];
                                for (var i = 0; i < res.length; i++) {
                                    var info = res[i];
                                    var statusStr = info.status.toString();
                                    var infoOperation = {id: -1, text: "--"};
                                    for (var j = 0; j < $pageCreativeList.initStatusByCreative.length; j++) {
                                        if ($pageCreativeList.initStatusByCreative[j].showid.indexOf(statusStr) > -1) {
                                            infoOperation = $pageCreativeList.initStatusByCreative[j];
                                            break;
                                        }
                                    }
                                    info.operationid = infoOperation.id;
                                    info.operationname = infoOperation.text;
                                    tempDataList.push(info);
                                }
                                var dataJson = {creative_id: tempRowData.id, detail: tempDataList};
                                $("#tb_ListCreative").ExtDataTable().bindTrDetailHtml(tr, row, $("#template_creativetable_trdetails").render(dataJson));
                                tempRowData.detailData.push(dataJson);
                            } else {
                                $.ExtNotify.error(result.msg);
                            }
                        }
                    });
                }else {
                    $("#tb_ListCreative").ExtDataTable().bindTrDetailHtml(tr, row, $("#template_creativetable_trdetails").render(tempRowData.detailData));
                }
            }
        });
        $('#tb_ListCreative').on('click', '.tr-details a[data-statusoperation]', function () {
            var _this=$(this);
            var material_status=_this.data().statusoriginal;
            var material_id = _this.data().materialid;
            var tr = _this.closest('tr');
            var datajson={id:material_id,status:material_status};
            $.ExtAjax.initPost({
                url:"/creative/material/statusChange.shtml",
                data:JSON.stringify(datajson),
                success: function(res){
                    var result=$.ExtAjax.initResult(res);
                    if(result.success) {
                        var status = res.data["status"];
                        var status_name = res.data["status_name"];
                        var statusStr = status.toString();
                        _this.closest("div.row-tr").find(".creative-status").text(status_name);
                        var infoOperation={id:-1,text:"--"};
                        for(var j=0;j<$pageCreativeList.initStatusByCreative.length;j++){
                            if($pageCreativeList.initStatusByCreative[j].showid.indexOf(statusStr)>-1){
                                infoOperation=$pageCreativeList.initStatusByCreative[j];
                                break;
                            }
                        }
                        var tempHtml = infoOperation.id == -1 ? '--' : '<a href="javascript:;" class="details-operate editable-click" data-materialid="'+material_id+'" data-statusoriginal="'+status+'" data-statusoperation="'+infoOperation.id+'">'+infoOperation.text+'</a>'
                        _this.parent().html(tempHtml);
                        $.ExtNotify.success("状态修改成功");
                    } else {
                        $.ExtNotify.error(result.msg);
                    }
                }
            });
        });
    });

</script>