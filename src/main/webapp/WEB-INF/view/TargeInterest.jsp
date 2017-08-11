<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="modal fade" id="InterestTarget" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" style="">
    <div class="modal-dialog modal-lg" role="document" style=" margin-top: 180px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title">商业兴趣定向</h4>
            </div>
            <div class="modal-body">
                <div class="pull-right geo_block">
                    <div style="margin-bottom:7px">已选择</div>
                    <div class="geo_border geo_border_color">
                        <ul id="interest_selected" class="tree-selected">
                        </ul>
                    </div>
                </div>
                <div class="pull-left geo_block">
                    <div style="margin-bottom:2px">
                        <input type="text" class="geo_border_color geo_text" id="interest_search" placeholder="请输入商业兴趣名称" />
                    </div>
                    <div class="geo_border geo_border_color">
                        <div id="checkTreeInterest"></div>
                    </div>
                </div>

                <div class="clearfix"></div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-graye8 waves-effect" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-default waves-effect waves-light" id="btn_SubmitInterestCommit">确定</button>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    var $pageTargeInterest;
    $(function () {
        $pageTargeInterest = {
            cbSubmit:null,
            selectIds:null,
            initshow: function (json) {
                if(json){
                    this.selectIds= $.map(json,function(info,i){
                        return info.value;
                    }).join(",");
                }else{
                    this.selectIds=""
                }

                GetJstreeData();
                $("#InterestTarget").modal("show");
            },
            hide: function () {
                $("#InterestTarget").modal("hide");
            }
        };
        $("#btn_SubmitInterestCommit").off("click").on("click",function(){
            /*var id = "";
            var name = "";
            $("#geo_selected li").each(function(){
                id += $(this).attr("id").split('_')[1] + ",";
                name += $(this).text() + "，"
            });
            if(id.length > 0) {
                id = id.substring(0, id.length - 1);
            }
            if(name.length > 0) {
                name = name.substring(0, name.length - 1);
            }*/
            var selectValuelist= $.map($("#interest_selected li"),function(obj,i){
                return {name:obj.textContent,value:obj.id.split('_')[1]}
            });
            //$("#checkTreeInterest").jstree().destroy();
            $("#interest_selected").html("");
            $pageTargeInterest.hide();
            if($pageTargeInterest.cbSubmit)$pageTargeInterest.cbSubmit(selectValuelist);
        });
        $('#InterestTarget').on('hidden.bs.modal', function (e) {
            $("#checkTreeInterest").jstree().destroy();
        });
        //jstree interest target start
        //获取jstree数据
        function GetJstreeData(key){
            //$("#checkTreeInterest").jstree().destroy();
            var para = {};
            if(!$.ExtCommon.isCheckEmpty(key)){
                para = {key: key}
            }
            $.ExtAjax.initGet({url:"/target/interest.shtml",data:para,
                success:function(res){
                    InitJstreeData(res)
                }
            });
        }
        //初始化jstree数据
        function InitJstreeData(data){
            $('#checkTreeInterest').jstree({
                'core' : {
                    "themes": {
                        "responsive": false
                    },
                    "cache": false,
                    'data' :data
                },
                "types" : {
                    "default" : {
                        "icon" : false
                    }
                },
                'plugins' : ['types','checkbox','search']
            });
            $('#checkTreeInterest').off("ready.jstree").on("ready.jstree", function (e, data) {
                InitjstreeEven();
                //初始化tree选中状态
                if($pageTargeInterest.selectIds && $pageTargeInterest.selectIds.length > 0) {
                    var tempArrIds=$pageTargeInterest.selectIds.split(',');
                    var tempAjax = $('#checkTreeInterest').jstree();
                    tempAjax.select_node(tempArrIds);
                    $("#interest_selected").find("li").each(function(i,obj){
                        var objThis=$(obj);
                        var id=objThis.attr("id").replace("li_","");
                        if(tempArrIds.indexOf(id)==-1){
                            objThis.empty().remove();
                        }
                    })
                }else{
                    $("#interest_selected").find("li").empty().remove();
                }
                //回车后快速搜索
                $("#interest_search").off("keydown").on("keydown",function(event){
                    if(event.keyCode == 13) {
                        var searchKey = $.trim($("#interest_search").val());
                        if(searchKey.length > 0){
                            /*searchKey = searchKey.replace("，", ",");
                             $("#checkTreeInterest a[id$='_anchor']").each(function(){
                             var _this = $(this);
                             var text = $.trim(_this.text());
                             $.each(searchKey.split(','), function (i, item) {
                             if(text.indexOf(item) > -1){
                             _this.addClass("jstree-selected");
                             }
                             });
                             })*/
                            $('#checkTreeInterest').jstree(true).search(searchKey)
                        }
                    }
                });
            });
        }
        //初始化tree事件
        function InitjstreeEven(){
            $('#checkTreeInterest').off("changed.jstree").on("changed.jstree", function (e, data) {
                var id = data.node.id;
                var text = data.node.text;
                if(data.action == "deselect_node"){
                    if(data.node.parent == "#") {
                        if($("#li_" + id).length > 0){
                            $("#li_" + id).remove();
                        }
                        $.each(data.node.children, function (i, item) {
                            if($("#li_" + item).length == 0){
                                if($("#li_" + item).length > 0){
                                    $("#li_" + item).remove();
                                }
                            }
                        });
                    }
                    else {
                        if($("#li_" + id).length > 0){
                            $("#li_" + id).remove();
                        }
                        else {
                            var parent_id = data.node.parent;
                            var parent_obj = data.instance.get_node(parent_id);
                            if($("#li_" + parent_id).length > 0){
                                $("#li_" + parent_id).remove();
                            }
                            $.each(parent_obj.children, function (i, item) {
                                if($("#li_" + item).length == 0){
                                    var child = data.instance.get_node(item);
                                    if(item != id) {
                                        $("#interest_selected").append("<li id=\"li_" + child.id + "\">" + child.text + "<i class='fa fa-close pull-right' onclick='removeSelectInterest("+ child.id +", this)'></i></li>");
                                    }
                                }
                            });

                        }
                    }
                    //$("#li_" + id).remove();
                }
                if(data.action == "select_node"){
                    if(data.node.parent == "#") {
                        if(data.node.children.length > 0){
                            $.each(data.node.children, function (i, item) {
                                if($("#li_" + item).length > 0){
                                    $("#li_" + item).remove();
                                }
                            })
                        }
                        if($("#li_" + id).length == 0) {
                            $("#interest_selected").append("<li id=\"li_" + id + "\">" + text + "<i class='fa fa-close pull-right' onclick='removeSelectInterest("+ id +", this)'></i></li>");
                        }
                    }
                    else {
                        var parent_id = data.node.parent;
                        var parent_obj = data.instance.get_node(parent_id);
                        var length = parent_obj.children.length;
                        var count = 0;
                        $.each(parent_obj.children, function (i, item) {
                            if($("#li_" + item).length > 0){
                                count ++;
                            }
                        });
                        if(count == length - 1){
                            $.each(parent_obj.children, function (i, item) {
                                if($("#li_" + item).length > 0){
                                    $("#li_" + item).remove();
                                }
                            });
                            if($("#li_" + parent_obj.id).length == 0){
                                $("#interest_selected").append("<li id=\"li_" + parent_obj.id + "\">" + parent_obj.text + "<i class='fa fa-close pull-right' onclick='removeSelectInterest("+ parent_obj.id +", this)'></i></li>");
                            }
                        }
                        else {
                            if($("#li_" + id).length == 0){
                                $("#interest_selected").append("<li id=\"li_" + id + "\">" + text + "<i class='fa fa-close pull-right' onclick='removeSelectInterest("+ id +", this)'></i></li>");
                            }
                        }
                    }
                }
            })
        }


    })
    //移除选中的地域
    function removeSelectInterest(geoid, obj){
        var tempAjax = $('#checkTreeInterest').jstree();
        tempAjax.deselect_node(geoid);
        $(obj).parent().remove();
    }
    //搜索地域
    function searchInterest(){
        var key = '';
        $('#checkTreeInterest').jstree().destroy();
        GetJstreeData(key)
    }
</script>