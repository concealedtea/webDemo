/* cl.ext.datatable 0.1*/
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
        var defaultExtInfo={name:"",data:[],url:"",ajaxDataPara:null};
        var defaultLanguage={
            "processing": "玩命加载中...",
            "lengthMenu": "显示 _MENU_ 项结果",
            "zeroRecords": "没有匹配结果",
            "info": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
            "infoEmpty": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
            "infoFiltered": "(由 _MAX_ 项结果过滤)",
            "infoPostFix": "",
            "search": "搜索:",
            "url": "",
            "paginate": {
                "first":    "首页",
                "previous": "上页",
                "next":     "下页",
                "last":     "末页"
            }
        };
        var defaults={
            pageLength:10,
            lengthChange:false,
            searching: false,
            ordering:false,
            paging: true,
            serverSide: true,
            pagingType: "full_numbers",
            language:defaultLanguage,
            infoExt:defaultExtInfo
        };
        var $modalCommon={
            getDefaults :function () {
                return defaults
            },
            getOptions : function (elementData,options) {
                options=$.ExtCommon.isCheckEmpty(options)?{}:options;
                var optionBase=$.ExtCommon.initMergeJson(elementData,options);
                optionBase =$.ExtCommon.initMergeJson(this.getDefaults(),optionBase);
                return optionBase
            }
        };
        var DataTableExtModal = function(element, options) {
            var self=this;
            self.element = $(element);
            self.optionBase=$modalCommon.getOptions(self.element.data(),options);
            var infoExt=self.optionBase.infoExt;
            if(!$.ExtCommon.isCheckEmpty(infoExt.url)){
                self.optionBase.ajax={
                    "url":infoExt.url,
                    type: "POST",
                    contentType: "application/json",
                    dataType: "json",
                    data: function (d) {
                        self.convertPara(d);
                        if(infoExt.ajaxDataPara){
                           var tempPara=infoExt.ajaxDataPara();
                            d= $.ExtCommon.initMergeJson(d,tempPara);
                        }
                        return JSON.stringify(d);
                    },
                    "successCallback": self.successCallback(infoExt.name)
                }
            }else{
                self.optionBase.data=infoExt.data;
            }
            self.tableInfo=self.element.DataTable(self.optionBase);
        };
        DataTableExtModal.prototype = {
            convertPara: function (d) {
                if (d.order.length > 0) {
                    d.orderby = d.order[0].dir;
                    var columnIndex = d.order[0].column;
                    d.ordercolumns = d.columns[columnIndex].data
                }
                d.columns = null;
                d.order = null;
                d.search = null;
            },
            successCallback: function (tablename) {
                return function (json, cb) {
                    var resjson = {data: []};
                    if (json.meta) {
                        var result = $.ExtAjax.initResult(json);
                        if (result.success) {
                            resjson = result.data;
                        } else {
                            resjson = {"recordsTotal": 0, "recordsFiltered": 0, data: []};
                            $.ExtNotify.error(tablename + "：" + result.msg);
                        }
                    } else {
                        resjson = json
                    }
                    if (cb)cb(resjson)
                }
            },
            bindTrDetailHtml: function (tr, row, htmlDetail, cbBefore, cbAfter) {
                row.child(htmlDetail).show();
                if (cbBefore && typeof cbBefore == "function") {
                    cbBefore()
                }
                tr.addClass('shown');
                var trnext = tr.next();
                trnext.hide().children("td").hide();
                trnext.show();
                trnext.children("td").slideDown(200, function () {
                    trnext.find("div").slideDown(300);
                    if (cbAfter && typeof cbAfter == "function") {
                        cbAfter()
                    }
                });
            },
            hideTrDetailHtml: function (tr, row, cbAfter) {
                var trnext = tr.next();
                trnext.find("div").slideUp(300, function () {
                    trnext.children("td").slideUp(100, function () {
                        row.child.hide();
                        tr.removeClass('shown');
                        if (cbAfter && typeof cbAfter == "function") {
                            cbAfter()
                        }
                    });
                });
            },
            getTabelInfo:function(){
                return this.tableInfo;
            },
            getRowInfo:function(row){
               return this.tableInfo.row(row);
            },
            refresh:function(){
                this.tableInfo.ajax.reload();
            },
            destroy:function(){
                this.tableInfo.destroy();
                this.element.removeData("ExtDataTable");
            }
        };
        $.fn.ExtDataTable = function(options) {
            var dataFun;
            this.each(function() {
                var el = $(this);
                var tempData=el.data('ExtDataTable');
                if (tempData) {
                    if (typeof  options == "string") {
                        if (tempData[options]) {
                            dataFun =tempData[options]()
                        } else {
                            dataFun = tempData
                        }
                    } else {
                        dataFun = tempData
                    }
                } else {
                    dataFun = new DataTableExtModal(el, options);
                    el.data('ExtDataTable', dataFun);
                }
            });
            return dataFun;
        };
        return DataTableExtModal;
    })
);
