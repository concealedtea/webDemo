/* cl.ext.notify 0.1*/
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
        var defaults={
            style: 'metro',
            showAnimation: "show",
            showDuration: 0,
            hideDuration: 0,
            autoHide: true,
            clickToHide: true
        };
        var defaultIcon={
            "error":"fa fa-exclamation",
            "warning":"fa fa-warning",
            "success":"fa fa-check",
            "custom":"md md-album",
            "info":"fa fa-question",
            "default":"fa fa-adjust"
        };
        var $modalCommon= {
            getDefaults :function () {
                return defaults
            },
            getOptions : function (options) {
                var optionBase =$.ExtCommon.initMergeJson(this.getDefaults(),options);
                return optionBase
            },
            initNotify: function (style, position, title, text) {
                var icon=defaultIcon[style]?defaultIcon[style]:defaultIcon["default"];
                var optionBase=this.getOptions({ className: style, globalPosition: position});
                $.notify({title: title, text: text, image: "<i class='" + icon + "'></i>"},optionBase);
            },
            autoHideNotify: function (style, position, title, text) {
                var icon=defaultIcon[style]?defaultIcon[style]:defaultIcon["default"];
                var optionBase=this.getOptions({ className: style, globalPosition: position,autoHideDelay: 5000});
                $.notify({title: title, text: text, image: "<i class='" + icon + "'></i>"},optionBase);
            },
            confirm : function(style,position, title) {
                var icon=defaultIcon[style]?defaultIcon[style]:defaultIcon["default"];
                var optionBase=this.getOptions({ className: style, globalPosition: position,autoHide: false, clickToHide: false});
                $.notify({title: title,
                    text: 'Are you sure you want to do nothing?<div class="clearfix"></div><br><a class="btn btn-sm btn-white yes">Yes</a> <a class="btn btn-sm btn-danger no">No</a>',
                    image: "<i class='" + icon + "'></i>"
                }, optionBase);
                $(document).off("click", '.notifyjs-metro-base .no').on('click', '.notifyjs-metro-base .no', function () {
                    $(this).trigger('notify-hide');
                });
                $(document).off("click", '.notifyjs-metro-base .yes').on('click', '.notifyjs-metro-base .yes', function () {
                    $(this).trigger('notify-hide');
                });
            }
        };
        var NotifyExtModal = function() {};
        NotifyExtModal.prototype = {
            run: function (style, text) {
                $modalCommon.initNotify(style, 'top right', '', text)
            },
            success: function (text) {
                this.run("success", text)
            },
            warning: function (text) {
                this.run("warning", text)
            },
            error: function (text) {
                this.run("error", text)
            },
            "destroy": function () {
                if ($(".notifyjs-hidable").length > 0) {
                    $(".notifyjs-hidable").trigger("notify-hide")
                }
            }
        };
        $.notify.addStyle("metro", {
            html:"<div>" +
            "<div class='image' data-notify-html='image'/>" +
            "<div class='text-wrapper'>" +
            "<div class='title' data-notify-html='title'/>" +
            "<div class='text' data-notify-html='text'/>" +
            "</div>" +
            "</div>",
            classes: {
                default: {
                    "color": "#fafafa !important",
                    "background-color": "#ABB7B7",
                    "border": "1px solid #ABB7B7"
                },
                error: {
                    "color": "#fafafa !important",
                    "background-color": "#f05050",
                    "border": "1px solid #ef5350"
                },
                custom: {
                    "color": "#fafafa !important",
                    "background-color": "#5fbeaa",
                    "border": "1px solid #5fbeaa"
                },
                success: {
                    "color": "#fafafa !important",
                    "background-color": "#81c868",
                    "border": "1px solid #33b86c"
                },
                info: {
                    "color": "#fafafa !important",
                    "background-color": "#34d3eb",
                    "border": "1px solid #29b6f6"
                },
                warning: {
                    "color": "#fafafa !important",
                    "background-color": "#ffbd4a",
                    "border": "1px solid #ffd740"
                },
                black: {
                    "color": "#fafafa !important",
                    "background-color": "#4c5667",
                    "border": "1px solid #212121"
                },
                white: {
                    "background-color": "#e6eaed",
                    "border": "1px solid #ddd"
                }
            }
        });
        $.ExtNotify = new NotifyExtModal;
        $.ExtNotify.Constructor = NotifyExtModal;
    })
);
