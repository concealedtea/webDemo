/* cl.ext.switchery 0.1*/
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
        var defaultExtInfo={};
        var defaults={color:"#5fbeaa",size:"small",infoExt:defaultExtInfo};
        var $modalCommon= {
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
        var SwitcheryExtModal = function(element, options) {
            var self=this;
            self.element = $(element);
            self.optionBase=$modalCommon.getOptions(self.element.data(),options);
            new Switchery(self.element[0], self.optionBase);
            self.element.off("change").on("change",function(){
                var bChecked=self.element.prop("checked");
                if(self.optionBase.changeCallback) self.optionBase.changeCallback(self.element,bChecked);
            })
        };
        SwitcheryExtModal.prototype = {

        };
        $.fn.ExtSwitchery = function(options) {
            var dataFun;
            this.each(function() {
                var el = $(this);
                var tempData=el.data('ExtSwitchery');
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
                    dataFun = new SwitcheryExtModal(el, options);
                    el.data('ExtSwitchery', dataFun);
                }
            });
            return dataFun;
        };
        return SwitcheryExtModal;
    })
);
