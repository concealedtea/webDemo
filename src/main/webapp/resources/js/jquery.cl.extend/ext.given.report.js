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
        var ReportExtModal = function(options) {};
        ReportExtModal.prototype = {
            initChartDefault: {
                chartType: "line",
                legendName: {
                    "view_count": "印象数",
                    "click_count": "点击数",
                    "cost": "花费",
                    "ecpm": "eCPM",
                    "ecpc": "eCPC",
                    "ctr": "点击率"
                }
            },
            initBind: function (pageOption, result, cb) {
                var self = this;
                self.pageData = {};
                self.pageData.para = pageOption;
                if (result.success) {
                    var json = result.data;
                    self.pageData.original = json;
                    self.pageData.chartdata = json.list.length == 0 ? {} : self.structChartDataListToJson(json.list, {
                        name: pageOption.chart.keyword,
                        nameformat: pageOption.type,
                        len: pageOption.chart.xAxisLen,
                        showOther: pageOption.chart.xOther,
                        isEndData: pageOption.chart.endData
                    }, self.initChartDefault.legendName)
                } else {
                    self.pageData.original = {list: []};
                    self.pageData.chartdata = {};
                }
                if (cb)cb(self.pageData)

            },
            getChartTabsQueue: function (value, json) {
                var info = json.infoChartTabsQueue;
                var list = info.list.arrclone();
                value = value.toString();
                var idx = list.indexOf(value);
                if (idx > -1) {
                    list.splice(idx, 1)
                } else {
                    list.push(value);
                    if (list.length > info.count) {
                        list.splice(0, 1)
                    }
                }
                if (list.length > 0)json.infoChartTabsQueue.list = list;
                return list;
            },
            structChartDataListToJson: function (list, xConfig, yConfig) {
                var xInfo = {name: [], allname: []};
                var yInfo = {};
                var otherInfo = {};
                var dataInfo = [];
                var yArrKey = [];
                for (var yc in yConfig) {
                    yInfo[yc] = {name: yConfig[yc], data: [], alldata: []};
                    yArrKey.push(yc);
                    otherInfo[yc] = 0;
                }
                var xi = 0;
                var xlen = list.length;
                if (xConfig.len > 0) {
                    if (xConfig.isEndData) {
                        xi = list.length > xConfig.len ? list.length - xConfig.len : 0;
                        xlen = list.length;
                    } else {
                        xi = 0;
                        xlen = list.length > xConfig.len ? xConfig.len : list.length
                    }
                }

                $.each(list, function (i, info) {
                    if (i >= xi && i < xlen) {
                        dataInfo.push(info);
                    }
                    for (var k in info) {
                        var value = info[k];
                        if (k == xConfig.name) {
                            xInfo.allname.push(value);
                            if (i >= xi && i < xlen) {
                                var nameValue = value;
                                switch (xConfig.nameformat) {
                                    case "day":
                                        nameValue = $.ExtDateTime.initMomentFormat({value: value, format: "MM.DD"});
                                        break;
                                }
                                xInfo.name.push(nameValue);
                            }
                        }
                        if (yArrKey.indexOf(k) > -1) {
                            yInfo[k].alldata.push(value);
                            if (i >= xi && i < xlen) {
                                yInfo[k].data.push(value)
                            } else {
                                otherInfo[k] += info[k];
                            }
                        }
                    }
                });
                switch (xConfig.nameformat) {
                    case "day":
                        dataInfo = dataInfo.reverse();
                        xInfo.allname=xInfo.allname.reverse();
                        xInfo.name=xInfo.name.reverse();
                        for(var k in  yInfo){
                            yInfo[k].alldata=yInfo[k].alldata.reverse();
                            yInfo[k].data=yInfo[k].data.reverse();
                        }
                        break;
                }
                return {x: xInfo, y: yInfo, data: dataInfo, otherInfo: otherInfo};
            },
            getChartOption: function (json) {
                var self = this;
                var arrJsonKey = self.getCharSeriesKey(json);
                var optionChart = self.getCompositeChartInfo(arrJsonKey, json.chartdata);
                var chartBaseInfo = self.optionChartBase();
                chartBaseInfo.legend.data = optionChart.legendData;
                chartBaseInfo.xAxis.data = optionChart.xNames;
                chartBaseInfo.yAxis = optionChart.yaxis;
                chartBaseInfo.series = optionChart.series;
                return chartBaseInfo;
            },
            getCharSeriesKey: function (json) {
                var self = this;
                var paraTypeTopLevel = ["day", "hour"];
                var arrNameLevel = ["view_count", "click_count", "cost", "ecpm", "ecpc", "ctr"];
                var paraType = json.para.type;
                var chartConfig = json.para.chart;
                var arrlist = json.infoChartTabsQueue.list;
                var arrKey = $.map(arrlist, function (name, i) {
                    var type = chartConfig.seriesType;
                    return {name: name, type: type, len: chartConfig.xAxisLen, isShowOther: chartConfig.xOther}
                });
                if (paraTypeTopLevel.indexOf(paraType) == -1 && arrKey.length == 2) {
                    var k1 = arrNameLevel.indexOf(arrKey[0].name);
                    var k2 = arrNameLevel.indexOf(arrKey[1].name);
                    if (k1 > k2) {
                        arrKey[0].type = "line";
                        arrKey[1].type = "bar";
                    } else {
                        arrKey[0].type = "bar";
                        arrKey[1].type = "line"
                    }
                }
                return arrKey
            },
            getCompositeChartInfo: function (arrjson, datajson) {
                var self = this;
                var legendData = [];
                var series = [];
                var yaxis = [];
                var xNames = [""];
                $.each(arrjson, function (i, arr) {
                    if ($.isEmptyObject(datajson)) {
                        var tempEmptyName = self.initChartDefault.legendName[arr.name];
                        legendData.push(tempEmptyName);
                        var tempEmptyData = [];
                        for (var a = 0; a < arr.len; a++) tempEmptyData.push(0);
                        if (arr.isShowOther) tempEmptyData.push(0);
                        series.push({name: tempEmptyName, type: arr.type, yAxisIndex: i, data: tempEmptyData});
                        yaxis.push({type: 'value', min: 0, max: 1, interval: 1});
                    } else {
                        var info = datajson.y[arr.name];
                        var tempName = info.name;
                        legendData.push(tempName);
                        var tempData = info.data.arrclone();
                        if (arr.isShowOther && arr.len < info.alldata.length) tempData.push(datajson.otherInfo[arr.name].toDecimal2());
                        series.push({name: tempName, type: arr.type, yAxisIndex: i, data: tempData});
                        var maxratio = i == 1 ? 1.5 : 1;
                        var infoAxis = self.calculateChartScale(tempData, 5, maxratio);
                        yaxis.push({type: 'value', min: 0, max: infoAxis.max, interval: infoAxis.interval});
                    }
                });
                if (!$.isEmptyObject(datajson)) {
                    xNames = datajson.x.name.arrclone();
                    if (arrjson[0].isShowOther && arrjson[0].len < datajson.x.allname.length) xNames.push("其他");
                }
                return {legendData: legendData, series: series, yaxis: yaxis, xNames: xNames}
            },
            calculateChartScale: function (arrData, space, maxratio) {
                space = parseInt(space && space > 0 ? space : 5);
                var tempMax = Math.max.apply(null, arrData);
                var tempInterval = maxratio > 1 ? tempMax * maxratio / space : tempMax / space;
                var interval = Math.ceil(tempInterval);
                var max = 1;
                if (tempInterval > 1) {
                    interval = interval + parseInt(tempInterval / 100) * 2;
                } else {
                    interval = (Math.ceil(tempInterval * 100) + 1) / 100
                }
                max = space * interval;
                return {max: max, interval: interval}
            },
            optionChartBase: function () {
                return {
                    title: {text: ''},
                    tooltip: {trigger: 'axis'},
                    legend: {data: []},
                    grid: {left: '1%', right: '3%', bottom: '2%', containLabel: true},
                    xAxis: {type: 'category', boundaryGap: true, splitLine: {show: false}, data: []},
                    yAxis: [],
                    series: []
                }
            },
            getChartListToOptionInfo: function (list, valueKey) {
                var legend = [];
                var seriesData = [];
                var arrValue = [0];
                $.each(list, function (i, info) {
                    legend.push(info.key);
                    seriesData.push({value: info[valueKey], name: info.key});
                    arrValue.push(parseInt(info[valueKey]));
                });
                var maxValue = Math.max.apply(null, arrValue);
                var sortArr = arrValue.arrclone().sort(function (a, b) {
                    return b - a;
                });
                return {legend: legend, seriesData: seriesData, maxValue: maxValue, oldArr: arrValue, sortArr: sortArr}
            }
        };
        $.ExtReport = new ReportExtModal;
        $.ExtReport.Constructor = ReportExtModal;
        return ReportExtModal;
    })
);