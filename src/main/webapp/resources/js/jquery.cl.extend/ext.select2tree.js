/* cl.ext.select2tree 0.1*/
(
    function (factory) {
        if (typeof define === 'function' && define.amd) {
            define(['jquery'], factory);
        } else if (typeof module === 'object' && module.exports) {
            module.exports = function( root, jQuery ) {
                if ( jQuery === undefined ) {
                    if ( typeof window !== 'undefined' ) {
                        jQuery = require('jquery');
                    }
                    else {
                        jQuery = require('jquery')(root);
                    }
                }
                factory(jQuery);
                return jQuery;
            };
        } else {
            factory(jQuery);
        }
    }(
    function ($){
        var defaultTree={"core" : {"themes": {"responsive": false},"cache": false,'data' :[]},"types" : {"default" : {"icon" : false}},'plugins' : ['types','search']};
        var defaults={showload:false,url:"",data:[],treeinfo:defaultTree,placeholder:"-请选择-",initvalue:"",initCallback:null,changeCallback:null};
        var $modalCommon={
            getDefaults :function () {
                defaults.loadinfo=$.ExtAjax.initGetDefault({});
                return defaults
            },
            getOptions : function (elementData,options) {
                options=$.ExtCommon.isCheckEmpty(options)?{}:options;
                var optionBase=$.ExtCommon.initMergeJson(elementData, options);
                optionBase =$.ExtCommon.initMergeJson(this.getDefaults(),optionBase);
                return optionBase
            },
            getData:function(options,parasearch,cb){
                var ajxaOption={url:options.url,data:{}};
                if(parasearch && parasearch.length>0){ajxaOption.data.push({search_keyword:parasearch})}
                if(options.showload){
                    ajxaOption.success=function(res){
                        if(cb) cb(res)
                    }
                }else{
                    ajxaOption.success=function(res){
                        if(cb) cb(res)
                    }
                }
                $.ExtAjax.initGet(ajxaOption);
            },
            initSelect2Tree:function(option){
                var self=this;
                option.treeinfo.core.data=option.data.length>0?option.data:[];
                $modalContainer.tree.jstree(option.treeinfo).off("select_node.jstree").on('select_node.jstree', function (e, data) {
                    if(data.node.parent!="#" ||data.node.children.length==0){
                        self.setTreeSelectName(option,data.node.id,data.node.text);
                        self.hide();
                    }
                });
                if(option.initvalue && option.initvalue.toString().length>0){
                    var text="";
                    $.each(option.data,function(i,info){
                        if(info.id==option.initvalue) {
                            text=info.text;
                            return false;
                        }
                    });
                    self.setTreeSelectName(option,option.initvalue,text)
                }
                self.bindTreeEvent();
                if(option.initCallback){option.initCallback($modalContainer.element)}
            },
            setTreeSelectNode:function(id){
                var objTree=$modalContainer.tree.jstree();
                objTree.close_all();
                objTree.deselect_all();
                objTree.clear_search();
                objTree.select_node(id,false,false);
            },
            setTreeSelectName:function(option,id,text){
                var sid=$modalContainer.btnSelect2Tree.data("treenodeid");
                if(sid!=id){
                    $modalContainer.btnSelect2Tree.data("treenodeid",id).find(".select2-selection__rendered").text(text);
                    $modalContainer.element.val(id.toString());
                    if(option.changeCallback){option.changeCallback($modalContainer.element,id,text)}
                }
            },
            show:function(){
                $modalContainer.btnSelect2Tree.addClass("select2-container--open");
                $modalContainer.dialogSelect2Tree.addClass("select2-container--open");
                $modalContainer.bgSelect2Tree.show();
            },
            hide:function(){
                $modalContainer.btnSelect2Tree.removeClass("select2-container--open");
                $modalContainer.dialogSelect2Tree.removeClass("select2-container--open");
                $modalContainer.bgSelect2Tree.hide();
            },
            searchTreeKey:function(value){
                var searchKey= $.trim(value);
                if(searchKey.length>0){
                    $modalContainer.tree.jstree(true).search(searchKey)
                }
            },
            bindTreeEvent:function(){
                var self=this;
                $modalContainer.btnSelect2Tree.on("click",function(){
                    var _this=$(this);
                    if(_this.hasClass("select2-container--open")){
                        $modalContainer.txtKeyWord.val("");
                        self.hide();
                    }else{
                        self.setTreeSelectNode(_this.data("treenodeid"));
                        var objdropdown=$modalContainer.dialogSelect2Tree.find(".select2-tree");
                        var objclick=$modalContainer.dialogSelect2Tree.find(".jstree-clicked");
                        objdropdown.scrollTop(0);
                        if(objclick.length>0){
                            var objclickedTop= objclick.eq(0).position().top;
                            objdropdown.scrollTop(objclickedTop>50?objclickedTop-40:0);
                        }
                        self.show()
                    }
                });
                $modalContainer.dialogSelect2Tree.off("click.dialogmodal.close").on("click.dialogmodal.close", function(e){
                    if(e.currentTarget== e.target){self.hide();}
                });
                $modalContainer.bgSelect2Tree.off("click").on("click", function(e){
                    self.hide();
                });
                $modalContainer.btnSearch.off("click").on("click", function(e){
                    self.searchTreeKey($modalContainer.txtKeyWord.val());
                });
                $modalContainer.txtKeyWord.off("keyup").on("keyup", function (event) {
                    if (event.keyCode == 13) {
                        self.searchTreeKey($(this).val());
                    }
                });
            },
            remove:function(){
                $modalContainer.btnSelect2Tree.off("click");
                $modalContainer.dialogSelect2Tree.off("click.dialogmodal.close");
                $modalContainer.bgSelect2Tree.off("click");
                $modalContainer.btnSearch.off("click");
                $modalContainer.txtKeyWord.off("keyup");
                $modalContainer.parent.append($modalContainer.element);
                $modalContainer.tree=null;
                $modalContainer.txtKeyWord=null;
                $modalContainer.btnSearch=null;
                $modalContainer.bgSelect2Tree=null;
                $modalContainer.btnSelect2Tree=null;
                $modalContainer.dialogSelect2Tree=null;
            }
        };
        var $modalContainer={
            element:null,
            tree:null,
            txtKeyWord:null,
            btnSearch:null,
            bgSelect2Tree:null,
            btnSelect2Tree: null,
            dialogSelect2Tree:null,
            parent:null
        };
        var Select2TreeExtModal = function(element, options) {
            var self=this;
            self.element = $(element);
            self.optionBase=$modalCommon.getOptions(self.element.data(),options);
            var $templateHtml = $('<div class="select2-treedialog" style="position: relative;">'+
                '<span class="select2tree-bg"></span>'+
                '<span class="select2 select2-container select2-container--bootstrap select2-container--below w-400 select2tree-btn">'+
                    '<span class="selection">'+
                        '<span class="select2-selection select2-selection--single">'+
                            '<span class="select2-selection__rendered" ><span class="select2-selection__placeholder">'+self.optionBase.placeholder+'</span></span>'+
                            '<span class="select2-selection__arrow"><b role="presentation"></b></span>'+
                        '</span>'+
                    '</span>'+
                    '<span class="dropdown-wrapper"></span>'+
                '</span>'+
                '<span class="select2-container select2-container--bootstrap select2tree-dialog" style="">'+
                    '<span class="select2-dropdown select2-dropdown--below w-400">'+
                        '<span class="select2-search select2-search--dropdown">'+
                            '<input class="select2-search__field select2tree-keyword" type="text">'+
                            '<i class="fa fa-search select2tree-search"></i>'+
                        '</span>'+
                        '<span class="select2-results">'+
                            '<div class="select2-tree">'+
                                '<div class="select2tree-list"></div>'+
                            '</div>'+
                        '</span>'+
                    '</span>'+
                '</span>'+
                '</div>');
            self.element.parent().append($templateHtml);
            self.element.prependTo($templateHtml);
            self.container=$templateHtml;
            $modalContainer.element=this.element;
            $modalContainer.tree=this.container.find(".select2tree-list");
            $modalContainer.txtKeyWord=this.container.find(".select2tree-keyword");
            $modalContainer.btnSearch=this.container.find(".select2tree-search");
            $modalContainer.bgSelect2Tree=this.container.find(".select2tree-bg");
            $modalContainer.btnSelect2Tree=this.container.find(".select2tree-btn");
            $modalContainer.dialogSelect2Tree=this.container.find(".select2tree-dialog");
            $modalContainer.parent=this.container.parent();
            if(self.optionBase.url){
                $modalCommon.getData(self.optionBase,null,function(json){
                    self.optionBase.data=json;
                    $modalCommon.initSelect2Tree(self.optionBase)
                })
            }else if(self.optionBase.data){
                $modalCommon.initSelect2Tree(self.optionBase)
            }
        };
        Select2TreeExtModal.prototype = {
            "destroy":function(){
                if (!this.element) {
                    return;
                }
                $modalCommon.remove();
                this.element.removeData("ExtSelect2Tree");
                this.container.empty().remove();
            }
        };
        $.fn.ExtSelect2Tree = function(options) {
            this.each(function() {
                var el = $(this);
                if (el.data('ExtSelect2Tree')){
                    if(typeof  options== "string"){
                        if(el.data('ExtSelect2Tree')[options]) el.data('ExtSelect2Tree')[options]()
                    }
                }else {
                    el.data('ExtSelect2Tree', new Select2TreeExtModal(el, options));
                }
            });
            return this;
        };
        return Select2TreeExtModal;
    })
);
