/* cl.ext.given.firstrun 0.1*/
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
		"use strict";
		$(function () {
			//点击按钮效果
			if(typeof Waves !="undefined") Waves.init();
			//返回顶部
			var upScroll = $("#btn-scroll-up");
			$(window).scroll(function () {
				if ($(window).scrollTop() > 100) {
					upScroll.addClass("display");
				} else {
					if (upScroll.hasClass("display")) {
						upScroll.removeClass("display")
					}
				}
			});
			upScroll.on("click", function () {
				$('html,body').animate({scrollTop: '0px'}, 300);
			});
			//菜单 active
			var showMenuName=$('[data-menutype]').data("menutype");
			$("#navigation .has-submenu").removeClass("active");
			$("#menu_"+showMenuName).parent().addClass("active")
		});
		Number.prototype.toDecimal2=function() {
			if (this) {
				return parseFloat(parseInt(this * 100) / 100)
			} else {
				return this;
			}
		};

		String.prototype.removesplit=function(str) {
			if (this) {
				return this.replace(/,/g, '');
			} else {
				return this;
			}
		};
		Array.prototype.arrclone=function() {
			if (this) {
				return this.slice(0);
			} else {
				return this;
			}
		}
	})
);