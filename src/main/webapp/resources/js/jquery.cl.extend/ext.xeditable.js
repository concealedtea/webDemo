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
        var defaultSelectInfo={};
        var defaults={type:"text", mode: "popup", toggle: "click", placement: "top", pk: 1, ajaxOptions: {type: 'post', contentType: "application/json",dataType: "json"},
            error: function (response, newValue) {
                $.ExtNotify.error(response.responseText);
            }
        };
        var $modalCommon={
            getDefaults :function () {
                return defaults
            },
            getOptions : function (elementData,options) {
                options = $.ExtCommon.isCheckEmpty(options) ? {} : options;
                var optionBase = $.ExtCommon.initMergeJson(elementData, options);
                optionBase = $.ExtCommon.initMergeJson(this.getDefaults(), optionBase);
                optionBase.validate = function (value) {
                    var strValue = $.trim(value).removesplit();
                    var msg = "";
                    if (strValue == '') {
                        msg = "请填写此字段"
                    } else {
                        if (optionBase.validateExt) {
                            msg=optionBase.validateExt(strValue)
                        }
                    }
                    return msg;
                };
                optionBase.success=function(res, newValue){
                    var result=$.ExtAjax.initResult(res);
                    var resMsg;
                    if(result.success){
                        if(optionBase.successCallback)optionBase.successCallback(result,newValue);
                        $.ExtNotify.success("成功");
                        resMsg=true
                    }else{
                        resMsg=result.msg;
                        //$.ExtNotify.error(result.msg);
                    }
                    //return result.success
                    return resMsg
                };
                return optionBase;
            }
        };
        var XEditableExtModal = function(element, options) {
            var self=this;
            self.element = $(element);
            self.optionBase=$modalCommon.getOptions(self.element.data(),options);
            self.init();
        };
        XEditableExtModal.prototype = {
            init:function(){
                this.element.editable(this.optionBase);
            },
            destroy:function(){
                this.element.editable("destroy");
                this.element.removeData("ExtXEditable");
            },
            refresh:function(sourceData){
                this.element.editable("destroy");
                if(this.optionBase.type=="select")this.optionBase.source=sourceData;
                this.init();
            }
        };
        $.fn.ExtXEditable = function(options) {
            var dataFun;
            this.each(function() {
                var el = $(this);
                var tempData=el.data('ExtXEditable');
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
                    dataFun = new XEditableExtModal(el, options);
                    el.data('ExtXEditable', dataFun);
                }
            });
            return dataFun;
        };
        return XEditableExtModal;
    })
);
