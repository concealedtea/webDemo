/* cl.ext.numeric 0.1*/
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
        var defaults={numtype:"float2"};// float2|| int
        var $modalCommon={
            getDefaults :function () {
                return defaults
            },
            getOptions : function (elementData,options) {
                options=$.ExtCommon.isCheckEmpty(options)?{}:options;
                var optionBase =$.ExtCommon.initMergeJson(elementData,options);
                optionBase =$.ExtCommon.initMergeJson(this.getDefaults(),optionBase);
                return optionBase
            }
        };
        var NumericExtModal = function(element, options) {
            var self=this;
            self.element = $(element);
            self.optionBase=$modalCommon.getOptions(self.element.data(),options);
            if(self.element.hasClass("num-int")) self.optionBase.numtype="int";
            if(self.element.hasClass("num-float2")) self.optionBase.numtype="float2";
            self.init()
        };
        NumericExtModal.prototype = {
            init:function(){
                var self=this;
                if(self.element.length>0) {
                    switch(self.optionBase.numtype){
                        case "float2":
                            self.float2();
                            break;
                        case "int":
                            self.int();
                            break;

                    }
                }else{
                    $(".num-int,.num-float2").ExtNumeric();
                }
            },
            float2:function(){
                this.element.autoNumeric();
            },
            int:function(){
                var self=this;
                var option={ mDec: 0};
                option=$.ExtCommon.initMergeJson(self.optionBase,option);
                self.element.autoNumeric("init", option);
            },
            destroy:function(){
                this.element.autoNumeric("destroy");
                this.element.removeData("ExtNumeric");
            },
            refresh:function(){
                this.element.autoNumeric("destroy");
                this.init();
            }
        };
        $.fn.ExtNumeric = function(options) {
            var dataFun;
            this.each(function() {
                var el = $(this);
                var tempData=el.data('ExtNumeric');
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
                    dataFun = new NumericExtModal(el, options);
                    el.data('ExtNumeric', dataFun);
                }
            });
            return dataFun;
        };
        $.ExtNumeric = new NumericExtModal;
        $.ExtNumeric.Constructor = NumericExtModal;
        return NumericExtModal;
    })
);
