/* cl.ext.given.report 0.1*/
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
        var defaults={};
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
        var TargeExtModal = function(options) {};
        TargeExtModal.prototype = {
            convertJsonToList: function (json) {
                var list = [];
                for (var k in json) {
                    list.push({value: k, name: json[k]})
                }
                return list;
            },
            convertListToJson: function (list) {
                var json = {};
                for (var i = 0; i < list.length; i++) {
                    json[list[i].value] = list[i].name
                }
                return json;
            },
            getValueText: function (type, text, json) {
                var str = "";
                if (json[type] && json[type].length > 0) {
                    var values = $.map(json[type], function (info, i) {
                        return info.name;
                    }).join(",");
                    str = text + "：" + values;
                }
                return str;
            },
            time: {
                getWeekTitle: function () {
                    return {"1": "周一", "2": "周二", "3": "周三", "4": "周四", "5": "周五", "6": "周六", "7": "周日"}
                },
                createInitWeekTimeValue: function () {
                    var weekInfo = {};
                    for (var i = 1; i <= 7; i++) {
                        var hourInfo = [];
                        for (var j = 0; j < 24; j++) {
                            hourInfo.push(0);
                        }
                        weekInfo[i.toString()] = hourInfo;
                    }
                    return weekInfo;
                },
                convertInitWeekTimeValue: function (arr) {
                    var weekInfo = {};
                    $.each(arr, function (i, info) {
                        weekInfo[(i + 1).toString()] = info;
                    });
                    return weekInfo;
                },
                convertSelectWeekTime: function (weekInfo) {
                    var selectWeekValue = {};
                    var listWeekTitle = this.getWeekTitle();
                    for (var w in weekInfo) {
                        var weekValue = weekInfo[w];
                        for (var y = 0; y < weekValue.length; y++) {
                            if (weekValue[y] == 1) {
                                if (!selectWeekValue[w]) {
                                    selectWeekValue[w] = []
                                }
                                selectWeekValue[w].push(y)
                            }
                        }
                    }
                    var selectWeekText = [];
                    for (var t in selectWeekValue) {
                        var weekName = listWeekTitle[t];
                        var weekText = selectWeekValue[t];
                        var ts = "-1";
                        var te = "-1";
                        var arrTimeRange = [];
                        for (var u = 0; u < weekText.length; u++) {
                            var wt = weekText[u];
                            if (ts == "-1") ts = wt;
                            if (u + 1 < weekText.length && wt + 1 == weekText[u + 1]) continue;
                            te = wt;
                            arrTimeRange.push("<span>" + this.getHourInterval(ts, te) + "</span>");
                            ts = "-1";
                            te = "-1";
                        }
                        selectWeekText.push(weekName + "：" + arrTimeRange.join(","))
                    }
                    return {value: weekInfo, selecvalue: selectWeekValue, selecttext: selectWeekText};
                },
                getHourInterval: function (ts, te) {
                    te = te ? te : ts;
                    return this.fillZero(ts) + ":00-" + this.fillZero(te + 1) + ":00";
                },
                fillZero: function (value) {
                    value = value.toString();
                    var temp = "00" + value;
                    return temp.substr(temp.length - 2)
                }
            }
        };
        $.ExtTarge = new TargeExtModal;
        $.ExtTarge.Constructor = TargeExtModal;
        return TargeExtModal;
    })
);