/* cl.ext.ajax 0.1*/
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
		var defaultLoadInfo = {type:"dotmoveright",place:"body",timeout:500}; // body; box {boxId,height,nothingHtml,nothingHide,dataListLength}
		var defaults = {loadinfo:defaultLoadInfo};
		var $modalLoadHtml={
			"init":function(type,place){
				return '<div class="data-loading data-loading-'+place+'"><div class="loading-bg"></div>'+
					'<div class="loading-center">'+this[type]()+
					'</div></div>';
			},
			"dotmoveright":function() {
				return '<div class="loading-center-absolute loading-dotmoveright-base">'+
					'<div class="loading-dotmoveright dotmoveright1"></div>'+
					'<div class="loading-dotmoveright dotmoveright2" style="left:20px;"></div>'+
					'<div class="loading-dotmoveright dotmoveright3" style="left:40px;"></div>'+
					'<div class="loading-dotmoveright dotmoveright4" style="left:60px;"></div>'+
					'<div class="loading-dotmoveright dotmoveright5" style="left:80px;"></div>'+
					'</div>';
			},
			"lineenlarge":function(){
				return '<div class="loading-center-absolute loading-lineenlarge-base">'+
					'<div class="loading-lineenlarge lineenlarge1"></div>'+
					'<div class="loading-lineenlarge lineenlarge2"></div>'+
					'<div class="loading-lineenlarge lineenlarge3"></div>'+
					'<div class="loading-lineenlarge lineenlarge4"></div>'+
					'<div class="loading-lineenlarge lineenlarge5"></div>'+
					'</div>';
			},
			"doublebounce":function(){
				return '<div class="loading-center-absolute loading-doublebounce-base">'+
					'<div class="loading-doublebounce doublebounce1"></div>'+
					'<div class="loading-doublebounce doublebounce2"></div>'+
					'</div>';
			},
			"bounce":function() {
				return '<div class="loading-center-absolute loading-bounce-base">' +
					'<div class="loading-bounce bounce1"></div>' +
					'<div class="loading-bounce bounce2"></div>' +
					'<div class="loading-bounce bounce3"></div>' +
					'</div>';
			},
			"circle":function(){
				return '<div class="loading-center-absolute loading-circle-base">'+
				'<div class="loading-circle-container circle-container1">'+
				'<div class="loading-circle circle1"></div>'+
				'<div class="loading-circle circle2"></div>'+
				'<div class="loading-circle circle3"></div>'+
				'<div class="loading-circle circle4"></div>'+
				'</div>'+
				'<div class="loading-circle-container circle-container2">'+
				'<div class="loading-circle circle1"></div>'+
				'<div class="loading-circle circle2"></div>'+
				'<div class="loading-circle circle3"></div>'+
				'<div class="loading-circle circle4"></div>'+
				'</div>'+
				'<div class="loading-circle-container circle-container3">'+
				'<div class="loading-circle circle1"></div>'+
				'<div class="loading-circle circle2"></div>'+
				'<div class="loading-circle circle3"></div>'+
				'<div class="loading-circle circle4"></div>'+
				'</div>'+
				'</div>';
			}
		};
		var $modalLoad={
			"box":{
				"show":function(options){
					var _this=$(options.boxId);
					if(_this.length>0){
						var optionBase= {"place":"box"};
						optionBase=$.ExtCommon.initMergeJson(optionBase,options);
						//optionBase.dataListLength=0;
						//optionBase.nothingHide=optionBase.nothingHide?optionBase.nothingHide:false;
						optionBase.nothingHtml=optionBase.nothingHtml?optionBase.nothingHtml:"暂无数据";
						optionBase.height=optionBase.height?optionBase.height:200;
						_this.hide();
						var objParent=_this.closest(".datajsonload");
						var objBox=objParent.find(".datajson-load-content");
						if(objBox.length==0){
							objParent.append('<div class="datajson-load-box" style="display: none;"><div class="datajson-load-content" style="display: block"></div></div>');
							objBox=objParent.find(".datajson-load-content");
						}
						var objLoading=objBox.find(".data-loading");
						if(objLoading.length==0){
							var loadhtml=$modalLoadHtml.init(optionBase.type,optionBase.place);
							objBox.append(loadhtml);
						}else{
						}
						var objNothing=objBox.find(".data-nothing");
						if(objNothing.length==0){
							var nothinghtml='<div class="data-nothing" style="display: none"><div><i class="fa fa-eye-slash"></i><span>'+optionBase.nothingHtml+'</span></div></div>';
							objBox.append(nothinghtml);
						}else{
							objNothing.hide()
						}
						objBox.parent().css({"height":optionBase.height+"px","line-height":optionBase.height+"px"}).show();
						objBox.find(".data-loading").show();
					}
				},
				"hide":function(options,cb){
					var _this=$(options.boxId);
					if(_this.length>0){
						options.nothingHide=typeof options.nothingHide=="boolean"?options.nothingHide:false;
						options.dataListLength=options.dataListLength?options.dataListLength:0;
						var objBox=_this.closest(".datajsonload").find(".datajson-load-box");
						objBox.find(".data-loading").fadeOut();
						if(options.dataListLength>0 || options.nothingHide){
                            objBox.hide();
                            _this.fadeIn();
						}else{
                            objBox.find(".data-nothing").fadeIn();
                            _this.hide();
						}
						if(cb){setTimeout(cb,130)}
					}
				}
			},
			"body":{
				"show":function(options){
					var optionBase= {"place":"body"};
					optionBase=$.ExtCommon.initMergeJson(optionBase,options);
					var bodyLoading=$("#body_loading");
					if(bodyLoading.length==0){
						$("body").append($($modalLoadHtml.init(optionBase.type,optionBase.place)).attr("id","body_loading"));
						bodyLoading=$("#body_loading");
					}
					if(bodyLoading.is(":hidden")){
						bodyLoading.fadeIn(150);
					}else{
						bodyLoading.fadeOut(150);
					}
				},
				"hide":function(options,cb){
					var bodyLoading=$("#body_loading");
					if(bodyLoading.is(":hidden")){
						if(cb)cb()
					}else{
						bodyLoading.fadeOut(150);
						if(cb){setTimeout(cb,130)}
					}
				}
			}
		};
		var $modalCommon={
			getDefaults :function () {
				return defaults
			},
			getOptions : function (options) {
				options=$.ExtCommon.isCheckEmpty(options)?{}:options;
				var optionBase =$.ExtCommon.initMergeJson(this.getDefaults(),options);
				optionBase.isloading=options.loadinfo && typeof options.loadinfo == "object";
				return optionBase
			},
			buttonDisable:function(id,isshow) {
				if (id && id.length > 0)$(id).prop("disabled", isshow);
			},
			loadStart:function(options){
				$modalLoad[options.place].show(options);
			},
			loadEnd:function(options,cb){
				var timeEnd=new Date();
				var timeDiff=timeEnd.getTime()-options.timeStart.getTime();
				var timeout= (timeDiff-options.timeout)>0?0:Math.abs(timeDiff-options.timeout);
				console.log(timeDiff+"..."+options.timeout+"....."+timeout);
				if(timeout>0){
					setTimeout(function () {
						$modalLoad[options.place].hide(options,cb);
					},timeout);
				}else {
					$modalLoad[options.place].hide(options,cb);
				}
			},
			loadAjaxRun:function(selfParent,options) {
				var selfModal=this;
				var timeStart=new Date();
				var actualDefaults = {
					beforeSend:function(){
						var self = this;
						selfModal.buttonDisable(true);
						if(self.isloading){
							self.loadinfo.timeStart=timeStart;
							selfModal.loadStart(self.loadinfo);
						}
					},
					success: function (res) {
						var self = this;
						selfModal.buttonDisable(false);
						if (self.successcallback) {
							var result = selfParent.initResult(res);
							if(self.isloading){
								self.loadinfo.dataListLength=0;
								if(result.data && result.data[self.loadinfo.dataListName]){
									self.loadinfo.dataListLength=result.data[self.loadinfo.dataListName].length
								}
								selfModal.loadEnd(self.loadinfo,function(){
									self.successcallback(result);
								});
							}else{
								self.successcallback(result);
							}
						}
					},
					error:function(req,status,msg){
						var self = this;
						selfModal.buttonDisable(false);
						if (self.successcallback) {
							if (self.isloading) {
								self.loadinfo.dataListLength=0;
								selfModal.loadEnd(self.loadinfo, function () {
									self.successcallback({success: false, msg: msg});
								});
							} else {
								self.successcallback({success: false, msg: msg});
							}
						}
					},
					complete:function(){
					}
				};
				var actualOptions=selfModal.getOptions(options);
				actualOptions= $.ExtCommon.initMergeJson(actualDefaults,actualOptions);
				$.ajax(actualOptions);
			}
		};
		var AjaxExtModal = function (options) {	};
		AjaxExtModal.prototype={
			initGetDefault:function(options){
				return $modalCommon.getOptions(options);
			},
			initLoadStart:function(options) {
				options.timeStart=new Date();
				var optionBase=$.ExtCommon.initMergeJson(defaultLoadInfo,options);
				$modalCommon.loadStart(optionBase);
			},
			initLoadEnd:function(options,cb) {
				var optionBase=$.ExtCommon.initMergeJson(defaultLoadInfo,options);
				$modalCommon.loadEnd(optionBase,cb);
			},
			initResult:function(res) {
				var temp={success:false,msg:"err",data:null};
				if(res){
					if(res.meta) {
						if (res.meta.success) {
							temp.success = true;
							temp.data = res.data;
						} else {
							temp.success = false;
							temp.msg = res.meta.message;
						}
					}else{
						temp.success = true;
						temp.data = res;
					}
				}
				return temp;
			},
			initGet:function(options) {
				var optionBase={type:"GET"};
				var actualOptions=$.ExtCommon.initMergeJson(optionBase,options);
				$modalCommon.loadAjaxRun(this,actualOptions)
			},
			initPost:function(options) {
				var optionBase={type:"POST",contentType: "application/json",dataType: "json"};
				var actualOptions=$.ExtCommon.initMergeJson(optionBase,options);
				$modalCommon.loadAjaxRun(this,actualOptions)
			}
		};
		$.ExtAjax = new AjaxExtModal;
		$.ExtAjax.Constructor = AjaxExtModal;
	})
);