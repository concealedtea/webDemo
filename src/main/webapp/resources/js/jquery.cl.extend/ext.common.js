/* cl.ext.comm 0.1*/
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
		var defaults = {};
		var $modalCommon={
		};
		var CommonExtModal = function (options) {	};
		CommonExtModal.prototype= {
			getStringLen: function (str) {
				var realLength = 0;
				var len = str.length;
				var charCode = -1;
				for (var i = 0; i < len; i++) {
					charCode = str.charCodeAt(i);
					if (charCode >= 0 && charCode <= 128) {
						realLength += 1;
					} else {
						realLength += 3;
					}
				}
				return realLength;
			},
			initMergeJson: function (option1, option2) {
				var target = {};
				for (var k in option1) {
					target[k] = option1[k]
				}
				for (var m in option2) {
					if (typeof option2[m] == "object") {
						if (target[m]) {
							if ($.isArray(option2[m])) {
								target[m] = option2[m]
							} else {
								target[m] = this.initMergeJson(target[m], option2[m])
							}
						} else {
							target[m] = option2[m]
						}
					} else {
						target[m] = option2[m]
					}
				}
				return target;
			},
			initRemoveJson: function (option1, arrRemove) {
				var target = {};
				for (var k in option1) {
					if (arrRemove.indexOf(k) == -1)target[k] = option1[k]
				}
				return target;
			},
			getArrUnique: function (arr) {
				var result = [], hash = {};
				for (var i = 0, elem; (elem = arr[i]) != null; i++) {
					if (!hash[elem]) {
						result.push(elem);
						hash[elem] = true;
					}
				}
				return result;
			},
			getJsonClone: function (json) {
				var newjson = {};
				for (var k in json) {
					newjson[k] = json[k];
				}
				return newjson;
			},
			isCheckEmpty: function (obj) {
				var b = typeof obj == "undefined" || obj == null;
				if (!b) {
					var t = $.type(obj);
					switch (t) {
						case "string":
							b = obj.length == 0;
							break;
						case "number":
							b = obj.toString().length == 0;
							break;
						case "array":
							b = obj.length == 0;
							break;
						case "object":
							b = $.isEmptyObject(obj);
							break;
						case "boolean":
							b = obj;
							break;
						default:
							if(obj.length) b = obj.length == 0;
					}
				}
				return b;
			},
			getQueryString:function(name) {
				var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
				var r = window.location.search.substr(1).match(reg);
				if (r != null) return unescape(r[2]); return null;
			},
			isCheckString: function (obj) {
				return typeof obj == "string";
			}
		};
		$.ExtCommon = new CommonExtModal;
		$.ExtCommon.Constructor = CommonExtModal;
	})
);