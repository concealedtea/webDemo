/* cl.ext.select2 0.1*/
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
        var defaultSelectInfo={dataSource:"json",initvalue:"",data:[],ajax:{dataMode:"all",url:""}};
        var defaults={placeholder: "-请选择-",language: "zh-CN",theme: "bootstrap",minimumResultsForSearch:Infinity,select2Info:defaultSelectInfo};
        var $modalCommon={
            getDefaults :function () {
                return defaults
            },
            getOptions : function (elementData,options) {
                options=$.ExtCommon.isCheckEmpty(options)?{}:options;
                var optionBase =$.ExtCommon.initMergeJson(elementData,options);
                optionBase =$.ExtCommon.initMergeJson(this.getDefaults(),optionBase);
                var tempInfo=optionBase.select2Info;
                if(tempInfo.dataSource=="ajax"){
                    var tempAjaxJson={
                        url: tempInfo.ajax.url,
                        dataType: 'json',
                        type: "GET",
                        delay: 250,
                        cache: true
                    };
                    switch(tempInfo.ajax.dataMode){
                        case "page":
                            tempAjaxJson.data=function(params) {
                                return {
                                    search_Keyword: params.term,
                                    start: params.start,
                                    length: params.length
                                }
                            };
                            tempAjaxJson.processResults=function(params) {
                                var dataJson={};
                                var arr=[];
                                for(var i=0;i<data.length;i++){
                                    arr.push({id:data[i].id,text:data[i].name})
                                }
                                params.start = params.start || 0;
                                params.length = params.length || 30;
                                dataJson.list=arr;
                                dataJson.recordsTotal= data.recordsTotal||0;
                                return {
                                    results: dataJson.list,
                                    pagination: {
                                        more: (params.start+params.length) < data.recordsTotal
                                    }
                                };
                            };
                            break;
                        case "all":
                            tempAjaxJson.data=function(params) {
                                return {
                                    search_keyword: params.term
                                }
                            };
                            tempAjaxJson.processResults=function(params) {
                                var arr=[];
                                for(var i=0;i<data.length;i++){
                                    arr.push({id:data[i].id,text:data[i].name})
                                }
                                return {
                                    results: arr,
                                    pagination: {
                                        more:false
                                    }
                                };
                            };
                            break;
                    }
                    optionBase.ajax=tempAjaxJson;
                    optionBase.minimumInputLength=0;
                }else{
                    if(!$.ExtCommon.isCheckEmpty(tempInfo.data)){
                        optionBase.data=tempInfo.data;
                    }
                }
                return optionBase
            }
        };
        var Select2ExtModal = function(element, options) {
            var self=this;
            self.element = $(element);
            self.optionBase=$modalCommon.getOptions(self.element.data(),options);
            self.element.select2(self.optionBase);
            self.init(self.optionBase.select2Info.initvalue)
        };
        Select2ExtModal.prototype = {
            init:function(value){
                var self=this;
                value=$.ExtCommon.isCheckEmpty(value)?"":value;
                self.element.val(value).trigger("change");
                if(self.initCallback)self.initCallback()
            }
        };
        $.fn.ExtSelect2 = function(options) {
            var dataFun;
            this.each(function() {
                var el = $(this);
                var tempData=el.data('ExtSelect2');
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
                    dataFun = new Select2ExtModal(el, options);
                    el.data('ExtSelect2', dataFun);
                }
            });
            return dataFun;
        };
        return Select2ExtModal;
    })
);
(function() {
    if (jQuery && jQuery.fn && jQuery.fn.select2 && jQuery.fn.select2.amd)
        var e = jQuery.fn.select2.amd;
    return e.define("select2/i18n/zh-CN", [], function() {
        return {
            errorLoading: function() {
                return "无法载入结果。"
            },
            inputTooLong: function(e) {
                var t = e.input.length - e.maximum
                    , n = "请删除" + t + "个字符";
                return n
            },
            inputTooShort: function(e) {
                var t = e.minimum - e.input.length
                    , n = "请再输入至少" + t + "个字符";
                return n
            },
            loadingMore: function() {
                return "载入更多结果…"
            },
            maximumSelected: function(e) {
                var t = "最多只能选择" + e.maximum + "个项目";
                return t
            },
            noResults: function() {
                return "未找到结果"
            },
            searching: function() {
                return "搜索中…"
            }
        }
    }),
    {
        define: e.define,
        require: e.require
    }
})();
