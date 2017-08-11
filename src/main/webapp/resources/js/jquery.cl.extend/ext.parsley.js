/* cl.ext.parsley 0.1*/
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
        var defaults={};
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
        var ParsleyExtModal = function(element, options) {
            var self=this;
            self.isStartParsley=false;
            self.element = $(element);
            self.optionBase=$modalCommon.getOptions(self.element.data(),options);
            self.element.parsley()
                .on('field:error', function(e) {
                    var parent=e.$element.parent();
                    parent.find(".fa-exclamation-circle").empty().remove();
                    var tempArr=$.map(e.validationResult,function(info){
                        return window.Parsley.getErrorMessage(info.assert);
                    });
                    var tempI='<i class="parsley-error-icon fa fa-exclamation-circle" data-toggle="tooltip" data-placement="left"></i>';
                    parent.append(tempI);
                    //parent.find(".fa-exclamation-circle").tooltip();
                    parent.find(".fa-exclamation-circle").tooltip({"html": true, "title": tempArr.join("<br />")})
                })
                .on('form:submit', function(a,b,c,d) {
                    return false;
                });
            self.element.find("[parsley-special]").each(function(){
                var e=$(this).attr("parsley-special");
                $(this).off(e).on(e,function(){
                    self.validate($(this))
                });
            });
        };
        ParsleyExtModal.prototype = {
            "validate":function(parsleyObj) {
                var b=true;
                var obj=null;
                if($.ExtCommon.isCheckEmpty(parsleyObj)){
                    this.reset();
                    this.isStartParsley=true;
                    obj=this.element;
                }else{
                    obj=$.ExtCommon.isCheckString(parsleyObj)?$(parsleyObj):parsleyObj;
                    if(this.element.find(obj).length==0) obj=null;
                }
                if(obj){
                    if(this.isStartParsley){
                        obj.each(function(){
                            b=$(this).parsley().validate();
                        });
                    }
                }else{
                    b=false
                }
                return b;
            },
            "reset":function(start){
                this.isStartParsley= start && typeof start=="boolean" ?start:false;
                this.element.parsley().reset();
                this.element.find(".parsley-error-icon").empty().remove();
            }
        };

        $.fn.ExtParsley = function(options) {
            var dataFun;
            this.each(function() {
                var el = $(this);
                if(el.is("form")) {
                    if (el.data('ExtParsley')) {
                        if (typeof  options == "string") {
                            if (el.data('ExtParsley')[options]) {
                                dataFun = el.data('ExtParsley')[options]()
                            } else {
                                dataFun = el.data('ExtParsley')
                            }
                        } else {
                            dataFun = el.data('ExtParsley')
                        }
                    } else {
                        dataFun = new ParsleyExtModal(el, options);
                        el.data('ExtParsley', dataFun);
                    }
                }
            });
            return dataFun;
        };
        //$.fn.datepicker.Constructor = Datepicker;
        var parsleyValidatorAdd=function(){
            window.Parsley.addValidator('numMultiple', {
                validateString: function(value, num) {
                    num= $.ExtCommon.isCheckEmpty(num)?100:parseInt(num);
                    value=$.trim(value.removesplit());
                    if(value.length>0){
                        value=parseInt(value);
                        return value%num==0
                    }else{
                        return true;
                    }
                },
                messages: {
                    "zh-cn":"数字必须为百的整数倍"
                }
            });
            window.Parsley.addValidator('minGreater', {
                validateString: function(value, requirement) {
                    value=$.trim(value.removesplit());
                    if(value.length>0){
                        return  parseFloat(value)>parseFloat(requirement);
                    }else{
                        return true;
                    }
                },
                messages: {
                    "zh-cn":"输入值请大于 %s"
                }
            });
            window.Parsley.addValidator('stringlen', {
                validateString: function(value, requirement) {
                    var len=$.ExtCommon.getStringLen(value);
                    return len<=parseInt(requirement);
                },
                messages: {
                    "zh-cn":"长度不能超过%s个英文字符"
                }
            });
            window.Parsley.addValidator('numrange', {
                requirementType: ['integer', 'integer'],
                validateString: function(value, min,max) {
                    var valueF=parseFloat(value.replace(/,/g, ''));
                    return valueF>=parseFloat(min) && valueF<=parseFloat(max);
                },
                messages: {
                    "zh-cn":"输入值应该在 %s 到 %s 之间"
                }
            });
            window.Parsley.addValidator('beforeBalance', {
                validateString: function(value, requirement) {
                    var balance = $(requirement).text().removesplit(",");
                    var valueF = parseFloat(value.removesplit(","));
                    return valueF <= balance;
                },
                messages: {
                    "zh-cn":"转账金额不能大于账户余额"
                }
            });
            window.Parsley.addValidator('afterNow', {
                validateString: function(value, requirement) {
                    return moment(value).diff(moment().format("YYYY-MM-DD"))>=0;
                },
                messages: {
                    "zh-cn":"该时间不能小于当前时间"
                }
            });
            window.Parsley.addValidator('beforeEndtime', {
                validateString: function(value, requirement,aa) {
                    var timeValue=$(requirement).val();
                    if(timeValue.length>0){
                        return moment(value).diff(moment($(requirement).val()).format("YYYY-MM-DD"))<=0;
                    }else{
                        return true;
                    }
                },
                messages: {
                    "zh-cn":"该时间不能大于结束时间"
                }
            });
            window.Parsley.addValidator('afterStarttime', {
                requirementType: 'string',
                validateString: function(value, requirement) {
                    var timeValue=$(requirement).val();
                    if(timeValue.length>0){
                        return moment(value).diff(moment($(requirement).val()).format("YYYY-MM-DD"))>=0;
                    }else{
                        return true;
                    }
                },
                messages: {
                    "zh-cn":"该时间不能小于开始时间"
                }
            });
            window.Parsley.addValidator('phone', {
                requirementType: 'string',
                validateString: function(value) {
                    value=$.trim(value);
                    if(value.length>0){
                        return (/^1\d{10}$/).test(value);
                    }else{
                        return true;
                    }
                },
                messages: {
                    "zh-cn":"手机号不正确"
                }
            });
            window.Parsley.addValidator('urlhttp', {
                requirementType: 'string',
                validateString: function(value) {
                    value= $.trim(value);
                    if(value.length>0){
                        var regurl=new RegExp("^" +
                            "(?:(?:(http|https)?)://)" +
                            "(?:\\S+(?::\\S*)?@)?" + "(?:" +
                            "(?:[1-9]\\d?|1\\d\\d|2[01]\\d|22[0-3])" + "(?:\\.(?:1?\\d{1,2}|2[0-4]\\d|25[0-5])){2}" + "(?:\\.(?:[1-9]\\d?|1\\d\\d|2[0-4]\\d|25[0-4]))" + "|" +
                            '(?:(?:[a-z\\u00a1-\\uffff0-9]-*)*[a-z\\u00a1-\\uffff0-9]+)' +
                            '(?:\\.(?:[a-z\\u00a1-\\uffff0-9]-*)*[a-z\\u00a1-\\uffff0-9]+)*' +
                            '(?:\\.(?:[a-z\\u00a1-\\uffff]{2,}))' + ")" +
                            "(?::\\d{2,5})?" +
                            "(?:/\\S*)?" + "$", 'g');
                        return regurl.test(value);
                    }else{
                        return true;
                    }
                },
                messages: {
                    "zh-cn":"Url格式不正确"
                }
            });
            window.Parsley.addValidator('illegalValue', {
                validateString: function(value) {
                    value= $.trim(value);
                    if(value.length>0){
                        return value.indexOf("/")==-1 && value.indexOf("\\")==-1
                    }else{
                        return true;
                    }
                },
                messages: {
                    "zh-cn":"包含非法字符( \\ /)"
                }
            });
        };
        parsleyValidatorAdd();
        Parsley.addMessages('zh-cn', {
            defaultMessage: "不正确的值",
            type: {
                email:        "请输入一个有效的电子邮箱地址",
                url:          "请输入一个有效的链接",
                number:       "请输入正确的数字",
                integer:      "请输入正确的整数",
                digits:       "请输入正确的号码",
                alphanum:     "请输入字母或数字"
            },
            notblank:       "请输入值",
            required:       "此项不能为空",
            pattern:        "格式不正确",
            min:            "输入值请大于或等于 %s",
            max:            "输入值请小于或等于 %s",
            range:          "输入值应该在 %s 到 %s 之间",
            minlength:      "请输入至少 %s 个字符",
            maxlength:      "请输入至多 %s 个字符",
            length:         "字符长度应该在 %s 到 %s 之间",
            mincheck:       "请至少选择 %s 个选项",
            maxcheck:       "请选择不超过 %s 个选项",
            check:          "请选择 %s 到 %s 个选项",
            equalto:        "两次输入值不同"
        });
        Parsley.setLocale('zh-cn');
        return ParsleyExtModal;
    })
);
