/* cl.ext.datetime 0.1*/
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
        var defaultExtInfo={type:"date",changeCallback:null}; //type: date|dateStartEnd|range
        var defaults={infoExt:defaultExtInfo};
        var $modalCommon={
            getDefaults :function () {
                return defaults
            },
            getOptions : function (elementData,options) {
                options=$.ExtCommon.isCheckEmpty(options)?{}:options;
                var optionBase=$.ExtCommon.initMergeJson(elementData,options);
                optionBase =$.ExtCommon.initMergeJson(this.getDefaults(),optionBase);
                return optionBase
            },
            getOptionsData:function(options){
                var tempOption={format:"yyyy-mm-dd",language: "zh-CN",autoclose:true};
                return $.ExtCommon.initMergeJson(tempOption,options);
            },
            getOptionsRange:function(options){
                var tempOption={
                    startDate: moment().subtract(7, 'days'),
                    endDate:  moment().subtract(1, 'days'),
                    opens: "left",
                    locale:{"format":"YYYY-MM-DD","separator":" ~ ","applyLabel":"确定","cancelLabel":"取消","customRangeLabel":"自定义"},
                    alwaysShowCalendars:true,
                    applyClass:'btn-default',
                    cancelClass: 'btn-graye8',
                    ranges: {
                        '今天': [moment(), moment()],
                        '昨天': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
                        '过去7天': [moment().subtract(7, 'days'), moment().subtract(1, 'days')],
                        '过去30天': [moment().subtract(30, 'days'), moment().subtract(1, 'days')],
                        '本月': [moment().startOf('month'), moment().endOf('month')],
                        '上月': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
                    }
                };
                return $.ExtCommon.initMergeJson(tempOption,options);
            }
        };
        var DateTimeExtModal = function(element, options) {
            if(element && options) {
                var self = this;
                self.element = $(element);
                var optionBase = $modalCommon.getOptions(self.element.data(), options);
                var infoExt = optionBase.infoExt;
                switch (infoExt.type) {
                    case "date":
                    case "dateStartEnd":
                        self.optionBase = $modalCommon.getOptionsData(optionBase);
                        break;
                    case "range":
                        self.optionBase = $modalCommon.getOptionsRange(optionBase);
                        break;
                }
            }
        };
        DateTimeExtModal.prototype = {
            initDatePicker: function (element, options) {
                var self = this;
                var tempObj = null;
                var tempOption = {};
                if (element) {
                    tempObj = element;
                    tempOption = $modalCommon.getOptions({}, options);
                    tempOption = $modalCommon.getOptionsData(tempOption);
                } else {
                    tempObj = element ? element : self.element;
                    tempOption = self.optionBase;
                }
                if (tempObj.val().length == 0)tempObj.val(this.initMomentFormat());
                tempObj.datepicker(tempOption).off("changeDate").on("changeDate", function (ev) {
                    if (tempOption.infoExt.changeCallback)tempOption.infoExt.changeCallback(ev)
                });
            },
            initDateStartEnd: function () {
                var self = this;
                var infoExt = self.optionBase.infoExt;
                var objStartTime = self.element;
                var objEndTime = infoExt.objEndTime;
                if (!$.ExtCommon.isCheckEmpty(objStartTime) && !$.ExtCommon.isCheckEmpty(objEndTime)) {
                    var valueStartTime = objStartTime.val();
                    var valueEndTime = objEndTime.val();
                    if ($.ExtCommon.isCheckEmpty(infoExt.isInitStart)) infoExt.isInitStart = true;
                    objStartTime.off("changeDate").datepicker("destroy");
                    if (infoExt.isInitStart) {
                        if (valueStartTime.length == 0) {
                            valueStartTime = self.initMomentFormat();
                            objStartTime.val(valueStartTime);
                        }
                        var optionStart = {
                            startDate: valueStartTime.length > 0 && moment(valueStartTime).isBefore(new Date()) ? valueStartTime : new Date(),
                            infoExt: {
                                changeCallback: function (ev) {
                                    objEndTime.datepicker('setStartDate', ev.date);
                                    objEndTime.focus();
                                    if (infoExt.onChangStartTime)infoExt.onChangStartTime();
                                }
                            }
                        };
                        self.initDatePicker(objStartTime, optionStart);
                    }
                    if (valueEndTime.length == 0) {
                        valueEndTime = self.initMomentAdd(1, "months");
                        objEndTime.val(valueEndTime);
                    }
                    var optionEnd = {
                        startDate: valueEndTime.length > 0 && moment(valueEndTime).isBefore(new Date()) ? valueEndTime : new Date(),
                        infoExt: {
                            changeCallback: function (ev) {
                                if (infoExt.onChangEndTime)infoExt.onChangEndTime();
                            }
                        }
                    };
                    objEndTime.off("changeDate").datepicker("destroy");
                    self.initDatePicker(objEndTime, optionEnd);
                }
            },
            initDatePickerRange: function () {
                var self = this;
                self.element.daterangepicker(self.optionBase, function (start, end, label) {
                    if (self.optionBase.infoExt.changeCallback)self.optionBase.infoExt.changeCallback(start, end, label)
                });
            },
            getDateRangeValue: function (timeId) {
                var obj = timeId ? $(timeId) : this.element;
                var arr = [];
                var value = $.trim(obj.val());
                if (value.length > 0 && value.indexOf("~") > -1) {
                    var arrtemp = value.split("~");
                    if (arrtemp.length > 0) arr.push($.trim(arrtemp[0]));
                    if (arrtemp.length > 1) arr.push($.trim(arrtemp[1]));
                }
                return arr;
            },
            destroy: function () {
            },
            momentDefault: "YYYY-MM-DD",
            initMomentFormat: function (options) {
                var optionBase = {value: "", format: this.momentDefault};
                options = $.ExtCommon.isCheckEmpty(options) ? {} : options;
                if (typeof options == "string") {
                    optionBase.value = options;
                } else {
                    optionBase = $.ExtCommon.initMergeJson(optionBase, options)
                }
                var strTime = "";
                if (optionBase.value.length == 0) {
                    strTime = moment().format(optionBase.format);
                } else {
                    optionBase.value = optionBase.value.replace(/[/]/g, '-');
                    strTime = moment(optionBase.value).format(optionBase.format);
                }
                return strTime;
            },
            initMomentAdd: function (num, type) {
                return moment().add(num, type).format(this.momentDefault);
            }
        };
        $.fn.ExtDateTime = function(options) {
            var dataFun;
            this.each(function() {
                var el = $(this);
                var tempData=el.data('ExtDateTime');
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
                    dataFun = new DateTimeExtModal(el, options);
                    el.data('ExtDateTime', dataFun);
                }
            });
            return dataFun;
        };
        $.ExtDateTime = new DateTimeExtModal;
        $.ExtDateTime.Constructor = DateTimeExtModal;
        return DateTimeExtModal;
    })
);