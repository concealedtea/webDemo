/* cl.ext.tabsvelocity 0.1   velocity */
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
        var TabsExtModal = function(element, options) {
            var self = this;
            self.element = $(element);
            self.optionBase = $modalCommon.getOptions(self.element.data(), options);
            var $this = self.element, window_width = $(window).width();
            //$this.width('100%');
            // Set Tab Width for each tab
            var $num_tabs = $(this).children('li').length;
            //$this.children('li').each(function() {
            //	$(this).width((100/$num_tabs)+'%');
            //});
            var $active, $content, $links = $this.find('li.tab a'),
                $tabs_width = $this.width(),
                $tab_width = $this.find('li').first().outerWidth(),
                $index = 0;
            $active = $($links.filter('[href="' + location.hash + '"]'));

            if ($active.length === 0) {
                $active = $this.find('li.tab a.active').first();
            }
            if ($active.length === 0) {
                $active = $this.find('li.tab a').first();
            }

            $active.addClass('active');
            $index = $links.index($active);
            if ($index < 0) {
                $index = 0;
            }
            $content = $($active[0].hash);
            $this.append('<div class="indicator"></div>');
            var $indicator = $this.find('.indicator');
            if ($this.is(":visible")) {
                $indicator.css({"right": $tabs_width - (($index + 1) * $tab_width)});
                $indicator.css({"left": $index * $tab_width});
            }
            $(window).resize(function () {
                $tabs_width = $this.width();
                $tab_width = $this.find('li').first().outerWidth();
                if ($index < 0) {
                    $index = 0;
                }
                if ($tab_width !== 0 && $tabs_width !== 0) {
                    $indicator.css({"right": $tabs_width - (($index + 1) * $tab_width)});
                    $indicator.css({"left": $index * $tab_width});
                }
            });
            $links.not($active).each(function () {
                $(this.hash).hide();
            });
            $this.on('click', 'a', function (e) {
                $tabs_width = $this.width();
                $tab_width = $this.find('li').first().outerWidth();
                $active.removeClass('active');
                $content.hide();
                $active = $(this);
                $content = $(this.hash);
                $links = $this.find('li.tab a');
                $active.addClass('active');
                var $prev_index = $index;
                $index = $links.index($(this));
                if ($index < 0) {
                    $index = 0;
                }
                $content.show();
                if (($index - $prev_index) >= 0) {
                    $indicator.velocity({"right": $tabs_width - (($index + 1) * $tab_width)}, {
                        duration: 300,
                        queue: false,
                        easing: 'easeOutQuad'
                    });
                    $indicator.velocity({"left": $index * $tab_width}, {
                        duration: 300,
                        queue: false,
                        easing: 'easeOutQuad',
                        delay: 90
                    });

                }
                else {
                    $indicator.velocity({"left": $index * $tab_width}, {
                        duration: 300,
                        queue: false,
                        easing: 'easeOutQuad'
                    });
                    $indicator.velocity({"right": $tabs_width - (($index + 1) * $tab_width)}, {
                        duration: 300,
                        queue: false,
                        easing: 'easeOutQuad',
                        delay: 90
                    });
                }
                e.preventDefault();
            });
        };
        TabsExtModal.prototype = {
            selectTabs: function (id) {
                this.find('a[href="#' + id + '"]').trigger('click');
            }
        };
        $.fn.ExtTabsVelocity = function(options) {
            var dataFun;
            this.each(function() {
                var el = $(this);
                var tempData=el.data('ExtTabsVelocity');
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
                    dataFun = new TabsExtModal(el, options);
                    el.data('ExtTabsVelocity', dataFun);
                }
            });
            return dataFun;
        };
        return TabsExtModal;
    })
);
