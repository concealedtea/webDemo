<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="modal fade" id="TimeTarget" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" style="">
    <div class="modal-dialog modal-lg" role="document" style=" margin-top: 180px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title">时间定向</h4>
            </div>
            <div class="modal-body">
                <div class="time_tools">
                    <table class="quick_picker">
                        <tbody>
                        <tr>
                            <td data-time-index="0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23"
                                data-time-row="1,2,3,4,5">
                                <a>工作日</a>
                            </td>
                            <td data-time-index="0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23"
                                data-time-row="6,7">
                                <a>周末</a>
                            </td>
                            <td data-time-index="0,1,2,3,4,5,6,7" data-time-row="1,2,3,4,5,6,7">
                                <a>凌晨</a>
                            </td>
                            <td data-time-index="8,9,10,11" data-time-row="1,2,3,4,5,6,7">
                                <a>上午</a>
                            </td>
                            <td data-time-index="12,13,14" data-time-row="1,2,3,4,5,6,7">
                                <a>中午</a>
                            </td>
                            <td data-time-index="15,16,17" data-time-row="1,2,3,4,5,6,7">
                                <a>下午</a>
                            </td>
                            <td data-time-index="18,19" data-time-row="1,2,3,4,5,6,7">
                                <a>傍晚</a>
                            </td>
                            <td data-time-index="20,21,22,23" data-time-row="1,2,3,4,5,6,7">
                                <a>晚上</a>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                    <ul class="select_tools">
                        <li id="chooseAll" class="ng-binding">全选</li>
                        <li id="clearAll" class="ng-binding">清空</li>
                    </ul>
                </div>
                <table id="TimePicker">
                    <colgroup>
                        <col width="80px;">
                    </colgroup>
                    <thead>
                    <tr data-row="0">
                        <th data-index="-1"><span></span></th>
                        <th data-index="0" style="font-weight:normal;"><span>0</span></th>
                        <th data-index="1" style="font-weight:normal;"><span>1</span></th>
                        <th data-index="2" style="font-weight:normal;"><span>2</span></th>
                        <th data-index="3" style="font-weight:normal;"><span>3</span></th>
                        <th data-index="4" style="font-weight:normal;"><span>4</span></th>
                        <th data-index="5" style="font-weight:normal;"><span>5</span></th>
                        <th data-index="6" style="font-weight:normal;"><span>6</span></th>
                        <th data-index="7" style="font-weight:normal;"><span>7</span></th>
                        <th style="width:10px;height:30px !important;"></th>
                        <th data-index="8" style="font-weight:normal;"><span>8</span></th>
                        <th data-index="9" style="font-weight:normal;"><span>9</span></th>
                        <th data-index="10" style="font-weight:normal;"><span>10</span></th>
                        <th data-index="11" style="font-weight:normal;"><span>11</span></th>
                        <th style="width:10px;height:30px !important;"></th>
                        <th data-index="12" style="font-weight:normal;"><span>12</span></th>
                        <th data-index="13" style="font-weight:normal;"><span>13</span></th>
                        <th data-index="14" style="font-weight:normal;"><span>14</span></th>
                        <th style="width:10px;height:30px !important;"></th>
                        <th data-index="15" style="font-weight:normal;"><span>15</span></th>
                        <th data-index="16" style="font-weight:normal;"><span>16</span></th>
                        <th data-index="17" style="font-weight:normal;"><span>17</span></th>
                        <th style="width:10px;height:30px !important;"></th>
                        <th data-index="18" style="font-weight:normal;"><span>18</span></th>
                        <th data-index="19" style="font-weight:normal;"><span>19</span></th>
                        <th style="width:10px;height:30px !important;"></th>
                        <th data-index="20" style="font-weight:normal;"><span>20</span></th>
                        <th data-index="21" style="font-weight:normal;"><span>21</span></th>
                        <th data-index="22" style="font-weight:normal;"><span>22</span></th>
                        <th data-index="23" style="font-weight:normal;"><span>23</span></th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr data-row="1">
                        <td class="text-left"><span>周一</span></td>
                        <td data-index="0" data-row="1" class="time time_normal"><i></i></td>
                        <td data-index="1" data-row="1" class="time time_normal"><i></i></td>
                        <td data-index="2" data-row="1" class="time time_normal"><i></i></td>
                        <td data-index="3" data-row="1" class="time time_normal"><i></i></td>
                        <td data-index="4" data-row="1" class="time time_normal"><i></i></td>
                        <td data-index="5" data-row="1" class="time time_normal"><i></i></td>
                        <td data-index="6" data-row="1" class="time time_normal"><i></i></td>
                        <td data-index="7" data-row="1" class="time time_normal"><i></i></td>
                        <th style="width:10px;height:30px !important;"></th>
                        <td data-index="8" data-row="1" class="time time_normal"><i></i></td>
                        <td data-index="9" data-row="1" class="time time_normal"><i></i></td>
                        <td data-index="10" data-row="1" class="time time_normal"><i></i></td>
                        <td data-index="11" data-row="1" class="time time_normal"><i></i></td>
                        <th style="width:10px;height:30px !important;"></th>
                        <td data-index="12" data-row="1" class="time time_normal"><i></i></td>
                        <td data-index="13" data-row="1" class="time time_normal"><i></i></td>
                        <td data-index="14" data-row="1" class="time time_normal"><i></i></td>
                        <th style="width:10px;height:30px !important;"></th>
                        <td data-index="15" data-row="1" class="time time_normal"><i></i></td>
                        <td data-index="16" data-row="1" class="time time_normal"><i></i></td>
                        <td data-index="17" data-row="1" class="time time_normal"><i></i></td>
                        <th style="width:10px;height:30px !important;"></th>
                        <td data-index="18" data-row="1" class="time time_normal"><i></i></td>
                        <td data-index="19" data-row="1" class="time time_normal"><i></i></td>
                        <th style="width:10px;height:30px !important;"></th>
                        <td data-index="20" data-row="1" class="time time_normal"><i></i></td>
                        <td data-index="21" data-row="1" class="time time_normal"><i></i></td>
                        <td data-index="22" data-row="1" class="time time_normal"><i></i></td>
                        <td data-index="23" data-row="1" class="time time_normal"><i></i></td>
                    </tr>
                    <tr data-row="2">
                        <td class="text-left"><span>周二</span></td>
                        <td data-index="0" data-row="2" class="time time_normal"><i></i></td>
                        <td data-index="1" data-row="2" class="time time_normal"><i></i></td>
                        <td data-index="2" data-row="2" class="time time_normal"><i></i></td>
                        <td data-index="3" data-row="2" class="time time_normal"><i></i></td>
                        <td data-index="4" data-row="2" class="time time_normal"><i></i></td>
                        <td data-index="5" data-row="2" class="time time_normal"><i></i></td>
                        <td data-index="6" data-row="2" class="time time_normal"><i></i></td>
                        <td data-index="7" data-row="2" class="time time_normal"><i></i></td>
                        <th style="width:10px;height:30px !important;"></th>
                        <td data-index="8" data-row="2" class="time time_normal"><i></i></td>
                        <td data-index="9" data-row="2" class="time time_normal"><i></i></td>
                        <td data-index="10" data-row="2" class="time time_normal"><i></i></td>
                        <td data-index="11" data-row="2" class="time time_normal"><i></i></td>
                        <th style="width:10px;height:30px !important;"></th>
                        <td data-index="12" data-row="2" class="time time_normal"><i></i></td>
                        <td data-index="13" data-row="2" class="time time_normal"><i></i></td>
                        <td data-index="14" data-row="2" class="time time_normal"><i></i></td>
                        <th style="width:10px;height:30px !important;"></th>
                        <td data-index="15" data-row="2" class="time time_normal"><i></i></td>
                        <td data-index="16" data-row="2" class="time time_normal"><i></i></td>
                        <td data-index="17" data-row="2" class="time time_normal"><i></i></td>
                        <th style="width:10px;height:30px !important;"></th>
                        <td data-index="18" data-row="2" class="time time_normal"><i></i></td>
                        <td data-index="19" data-row="2" class="time time_normal"><i></i></td>
                        <th style="width:10px;height:30px !important;"></th>
                        <td data-index="20" data-row="2" class="time time_normal"><i></i></td>
                        <td data-index="21" data-row="2" class="time time_normal"><i></i></td>
                        <td data-index="22" data-row="2" class="time time_normal"><i></i></td>
                        <td data-index="23" data-row="2" class="time time_normal"><i></i></td>
                    </tr>
                    <tr data-row="3">
                        <td class="text-left"><span>周三</span></td>
                        <td data-index="0" data-row="3" class="time time_normal"><i></i></td>
                        <td data-index="1" data-row="3" class="time time_normal"><i></i></td>
                        <td data-index="2" data-row="3" class="time time_normal"><i></i></td>
                        <td data-index="3" data-row="3" class="time time_normal"><i></i></td>
                        <td data-index="4" data-row="3" class="time time_normal"><i></i></td>
                        <td data-index="5" data-row="3" class="time time_normal"><i></i></td>
                        <td data-index="6" data-row="3" class="time time_normal"><i></i></td>
                        <td data-index="7" data-row="3" class="time time_normal"><i></i></td>
                        <th style="width:10px;height:30px !important;"></th>
                        <td data-index="8" data-row="3" class="time time_normal"><i></i></td>
                        <td data-index="9" data-row="3" class="time time_normal"><i></i></td>
                        <td data-index="10" data-row="3" class="time time_normal"><i></i></td>
                        <td data-index="11" data-row="3" class="time time_normal"><i></i></td>
                        <th style="width:10px;height:30px !important;"></th>
                        <td data-index="12" data-row="3" class="time time_normal"><i></i></td>
                        <td data-index="13" data-row="3" class="time time_normal"><i></i></td>
                        <td data-index="14" data-row="3" class="time time_normal"><i></i></td>
                        <th style="width:10px;height:30px !important;"></th>
                        <td data-index="15" data-row="3" class="time time_normal"><i></i></td>
                        <td data-index="16" data-row="3" class="time time_normal"><i></i></td>
                        <td data-index="17" data-row="3" class="time time_normal"><i></i></td>
                        <th style="width:10px;height:30px !important;"></th>
                        <td data-index="18" data-row="3" class="time time_normal"><i></i></td>
                        <td data-index="19" data-row="3" class="time time_normal"><i></i></td>
                        <th style="width:10px;height:30px !important;"></th>
                        <td data-index="20" data-row="3" class="time time_normal"><i></i></td>
                        <td data-index="21" data-row="3" class="time time_normal"><i></i></td>
                        <td data-index="22" data-row="3" class="time time_normal"><i></i></td>
                        <td data-index="23" data-row="3" class="time time_normal"><i></i></td>
                    </tr>
                    <tr data-row="4">
                        <td class="text-left"><span>周四</span></td>
                        <td data-index="0" data-row="4" class="time time_normal"><i></i></td>
                        <td data-index="1" data-row="4" class="time time_normal"><i></i></td>
                        <td data-index="2" data-row="4" class="time time_normal"><i></i></td>
                        <td data-index="3" data-row="4" class="time time_normal"><i></i></td>
                        <td data-index="4" data-row="4" class="time time_normal"><i></i></td>
                        <td data-index="5" data-row="4" class="time time_normal"><i></i></td>
                        <td data-index="6" data-row="4" class="time time_normal"><i></i></td>
                        <td data-index="7" data-row="4" class="time time_normal"><i></i></td>
                        <th style="width:10px;height:30px !important;"></th>
                        <td data-index="8" data-row="4" class="time time_normal"><i></i></td>
                        <td data-index="9" data-row="4" class="time time_normal"><i></i></td>
                        <td data-index="10" data-row="4" class="time time_normal"><i></i></td>
                        <td data-index="11" data-row="4" class="time time_normal"><i></i></td>
                        <th style="width:10px;height:30px !important;"></th>
                        <td data-index="12" data-row="4" class="time time_normal"><i></i></td>
                        <td data-index="13" data-row="4" class="time time_normal"><i></i></td>
                        <td data-index="14" data-row="4" class="time time_normal"><i></i></td>
                        <th style="width:10px;height:30px !important;"></th>
                        <td data-index="15" data-row="4" class="time time_normal"><i></i></td>
                        <td data-index="16" data-row="4" class="time time_normal"><i></i></td>
                        <td data-index="17" data-row="4" class="time time_normal"><i></i></td>
                        <th style="width:10px;height:30px !important;"></th>
                        <td data-index="18" data-row="4" class="time time_normal"><i></i></td>
                        <td data-index="19" data-row="4" class="time time_normal"><i></i></td>
                        <th style="width:10px;height:30px !important;"></th>
                        <td data-index="20" data-row="4" class="time time_normal"><i></i></td>
                        <td data-index="21" data-row="4" class="time time_normal"><i></i></td>
                        <td data-index="22" data-row="4" class="time time_normal"><i></i></td>
                        <td data-index="23" data-row="4" class="time time_normal"><i></i></td>
                    </tr>
                    <tr data-row="5">
                        <td class="text-left"><span>周五</span></td>
                        <td data-index="0" data-row="5" class="time time_normal"><i></i></td>
                        <td data-index="1" data-row="5" class="time time_normal"><i></i></td>
                        <td data-index="2" data-row="5" class="time time_normal"><i></i></td>
                        <td data-index="3" data-row="5" class="time time_normal"><i></i></td>
                        <td data-index="4" data-row="5" class="time time_normal"><i></i></td>
                        <td data-index="5" data-row="5" class="time time_normal"><i></i></td>
                        <td data-index="6" data-row="5" class="time time_normal"><i></i></td>
                        <td data-index="7" data-row="5" class="time time_normal"><i></i></td>
                        <th style="width:10px;height:30px !important;"></th>
                        <td data-index="8" data-row="5" class="time time_normal"><i></i></td>
                        <td data-index="9" data-row="5" class="time time_normal"><i></i></td>
                        <td data-index="10" data-row="5" class="time time_normal"><i></i></td>
                        <td data-index="11" data-row="5" class="time time_normal"><i></i></td>
                        <th style="width:10px;height:30px !important;"></th>
                        <td data-index="12" data-row="5" class="time time_normal"><i></i></td>
                        <td data-index="13" data-row="5" class="time time_normal"><i></i></td>
                        <td data-index="14" data-row="5" class="time time_normal"><i></i></td>
                        <th style="width:10px;height:30px !important;"></th>
                        <td data-index="15" data-row="5" class="time time_normal"><i></i></td>
                        <td data-index="16" data-row="5" class="time time_normal"><i></i></td>
                        <td data-index="17" data-row="5" class="time time_normal"><i></i></td>
                        <th style="width:10px;height:30px !important;"></th>
                        <td data-index="18" data-row="5" class="time time_normal"><i></i></td>
                        <td data-index="19" data-row="5" class="time time_normal"><i></i></td>
                        <th style="width:10px;height:30px !important;"></th>
                        <td data-index="20" data-row="5" class="time time_normal"><i></i></td>
                        <td data-index="21" data-row="5" class="time time_normal"><i></i></td>
                        <td data-index="22" data-row="5" class="time time_normal"><i></i></td>
                        <td data-index="23" data-row="5" class="time time_normal"><i></i></td>
                    </tr>
                    <tr data-row="6">
                        <td class="text-left"><span>周六</span></td>
                        <td data-index="0" data-row="6" class="time time_normal"><i></i></td>
                        <td data-index="1" data-row="6" class="time time_normal"><i></i></td>
                        <td data-index="2" data-row="6" class="time time_normal"><i></i></td>
                        <td data-index="3" data-row="6" class="time time_normal"><i></i></td>
                        <td data-index="4" data-row="6" class="time time_normal"><i></i></td>
                        <td data-index="5" data-row="6" class="time time_normal"><i></i></td>
                        <td data-index="6" data-row="6" class="time time_normal"><i></i></td>
                        <td data-index="7" data-row="6" class="time time_normal"><i></i></td>
                        <th style="width:10px;height:30px !important;"></th>
                        <td data-index="8" data-row="6" class="time time_normal"><i></i></td>
                        <td data-index="9" data-row="6" class="time time_normal"><i></i></td>
                        <td data-index="10" data-row="6" class="time time_normal"><i></i></td>
                        <td data-index="11" data-row="6" class="time time_normal"><i></i></td>
                        <th style="width:10px;height:30px !important;"></th>
                        <td data-index="12" data-row="6" class="time time_normal"><i></i></td>
                        <td data-index="13" data-row="6" class="time time_normal"><i></i></td>
                        <td data-index="14" data-row="6" class="time time_normal"><i></i></td>
                        <th style="width:10px;height:30px !important;"></th>
                        <td data-index="15" data-row="6" class="time time_normal"><i></i></td>
                        <td data-index="16" data-row="6" class="time time_normal"><i></i></td>
                        <td data-index="17" data-row="6" class="time time_normal"><i></i></td>
                        <th style="width:10px;height:30px !important;"></th>
                        <td data-index="18" data-row="6" class="time time_normal"><i></i></td>
                        <td data-index="19" data-row="6" class="time time_normal"><i></i></td>
                        <th style="width:10px;height:30px !important;"></th>
                        <td data-index="20" data-row="6" class="time time_normal"><i></i></td>
                        <td data-index="21" data-row="6" class="time time_normal"><i></i></td>
                        <td data-index="22" data-row="6" class="time time_normal"><i></i></td>
                        <td data-index="23" data-row="6" class="time time_normal"><i></i></td>
                    </tr>
                    <tr data-row="7">
                        <td class="text-left"><span>周日</span></td>
                        <td data-index="0" data-row="7" class="time time_normal"><i></i></td>
                        <td data-index="1" data-row="7" class="time time_normal"><i></i></td>
                        <td data-index="2" data-row="7" class="time time_normal"><i></i></td>
                        <td data-index="3" data-row="7" class="time time_normal"><i></i></td>
                        <td data-index="4" data-row="7" class="time time_normal"><i></i></td>
                        <td data-index="5" data-row="7" class="time time_normal"><i></i></td>
                        <td data-index="6" data-row="7" class="time time_normal"><i></i></td>
                        <td data-index="7" data-row="7" class="time time_normal"><i></i></td>
                        <th style="width:10px;height:30px !important;"></th>
                        <td data-index="8" data-row="7" class="time time_normal"><i></i></td>
                        <td data-index="9" data-row="7" class="time time_normal"><i></i></td>
                        <td data-index="10" data-row="7" class="time time_normal"><i></i></td>
                        <td data-index="11" data-row="7" class="time time_normal"><i></i></td>
                        <th style="width:10px;height:30px !important;"></th>
                        <td data-index="12" data-row="7" class="time time_normal"><i></i></td>
                        <td data-index="13" data-row="7" class="time time_normal"><i></i></td>
                        <td data-index="14" data-row="7" class="time time_normal"><i></i></td>
                        <th style="width:10px;height:30px !important;"></th>
                        <td data-index="15" data-row="7" class="time time_normal"><i></i></td>
                        <td data-index="16" data-row="7" class="time time_normal"><i></i></td>
                        <td data-index="17" data-row="7" class="time time_normal"><i></i></td>
                        <th style="width:10px;height:30px !important;"></th>
                        <td data-index="18" data-row="7" class="time time_normal"><i></i></td>
                        <td data-index="19" data-row="7" class="time time_normal"><i></i></td>
                        <th style="width:10px;height:30px !important;"></th>
                        <td data-index="20" data-row="7" class="time time_normal"><i></i></td>
                        <td data-index="21" data-row="7" class="time time_normal"><i></i></td>
                        <td data-index="22" data-row="7" class="time time_normal"><i></i></td>
                        <td data-index="23" data-row="7" class="time time_normal"><i></i></td>
                    </tr>
                    </tbody>
                </table>
                <div id="tips" class="ng-binding">
                    <span class="time time_normal time_opted"><i></i></span><span>已定向</span> &nbsp;&nbsp;
                    <span class="time time_normal"><i></i></span><span>未定向</span>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-graye8 waves-effect" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-default waves-effect waves-light" id="btn_SubmitTime">确定</button>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    var $pageTargeTime;
    $(function () {
        var timeDialog=$("#TimeTarget");
        $pageTargeTime = {
            init:function(){
                $pageTargeTime.listWeekName = $.ExtTarge.time.getWeekTitle();
            },
            show: function (json) {
                var obj=$("#TimePicker").find(".time");
                obj.removeClass("time_opted");
                if(!$.isEmptyObject(json)){
                    $.each(obj,function(i,info){
                        var _this=$(info);
                        var _thisData=_this.data();
                        if(json.selecvalue[_thisData.row] && json.selecvalue[_thisData.row].indexOf(parseInt(_thisData.index))>-1) _this.addClass("time_opted")
                    });
                }

                $("#TimeTarget").modal("show");
            },
            hide: function () {
                $("#TimeTarget").modal("hide");
            }
        };
        $pageTargeTime.init();
        $("#btn_SubmitTime").off("click").on("click",function(){
            var weekInfo=$.ExtTarge.time.createInitWeekTimeValue();
            timeDialog.find("td.time_opted").each(function(){
                var thisData=$(this).data();
                var row=thisData.row.toString();
                var column=parseInt(thisData.index);
                weekInfo[row][column]=1
            });
            var  jsonDada=  $.ExtTarge.time.convertSelectWeekTime(weekInfo);
            $pageTargeTime.hide();
            if($pageTargeTime.cbSubmit)$pageTargeTime.cbSubmit(jsonDada);

        });
        $("#TimeTarget .quick_picker a").click(function () {
            var index = $(this).parent().attr("data-time-index");
            var row = $(this).parent().attr("data-time-row");
            var flag = true;
            $.each(index.split(','), function (i, indexitem) {
                $.each(row.split(','), function (j, rowitem) {
                    if (!$("td[data-row=" + rowitem + "][data-index=" + indexitem + "]").hasClass("time_opted")) {
                        flag = false;
                    }
                })
            });
            $.each(index.split(','), function (i, indexitem) {
                $.each(row.split(','), function (j, rowitem) {
                    if (flag) {
                        $("td[data-row=" + rowitem + "][data-index=" + indexitem + "]").removeClass("time_opted");
                    }
                    else {
                        $("td[data-row=" + rowitem + "][data-index=" + indexitem + "]").addClass("time_opted");
                    }
                })
            });

        });
        $("#TimeTarget .quick_picker a").hover(function () {
            var index = $(this).parent().attr("data-time-index");
            var row = $(this).parent().attr("data-time-row");
            $.each(index.split(','), function (i, indexitem) {
                $.each(row.split(','), function (j, rowitem) {
                    $("td[data-row=" + rowitem + "][data-index=" + indexitem + "]").toggleClass("time_hover");
                })
            })

        });
        $("#TimeTarget #TimePicker th").hover(function () {
            if ($(this).attr("data-index") != -1) {
                $("td[data-index=" + $(this).attr("data-index") + "]").toggleClass("time_hover");
            }
        });
        $("#TimeTarget #TimePicker th").click(function () {
            if ($(this).attr("data-index") != -1) {
                var count = 0;
                $.each($("td[data-index=" + $(this).attr("data-index") + "]"), function (i, item) {
                    if ($(item).hasClass("time_opted")) {
                        count++;
                    }
                })
                if (count == 7) {
                    $("td[data-index=" + $(this).attr("data-index") + "]").removeClass("time_opted");
                }
                else {
                    $("td[data-index=" + $(this).attr("data-index") + "]").addClass("time_opted");
                }
            }
        });
        $("#TimeTarget #TimePicker td").hover(function () {
            if ($(this).hasClass("text-left")) {
                var row = $(this).parent().attr("data-row");
                $("td[data-row=" + row + "]").toggleClass("time_hover");
            }
            else {
                var index = $(this).attr("data-index");
                var row = $(this).attr("data-row");
                $("td[data-row=" + row + "]").toggleClass("time_hover");
                $("td[data-index=" + index + "]").toggleClass("time_hover");
                $(this).toggleClass("time_hover");
            }
        });
        $("#TimeTarget #TimePicker td").click(function () {
            if ($(this).hasClass("text-left")) {
                var row = $(this).parent().attr("data-row");
                var count = 0;
                $.each($("td[data-row=" + row + "]"), function (i, item) {
                    if ($(item).hasClass("time_opted")) {
                        count++;
                    }
                })

                if (count == 24) {
                    $("td[data-row=" + row + "]").removeClass("time_opted");
                }
                else {
                    $("td[data-row=" + row + "]").addClass("time_opted");
                }
            }
            else {
                if ($(this).hasClass("time_opted")) {
                    $(this).removeClass("time_opted");
                }
                else {
                    $(this).addClass("time_opted");
                }
            }
        });
        $("#TimeTarget #chooseAll").click(function () {
            for (var i = 1; i <= 7; i++) {
                $("#TimePicker td[data-row=" + i + "]").removeClass("time_opted");
                $("#TimePicker td[data-row=" + i + "]").addClass("time_opted");
            }
        });
        $("#TimeTarget #clearAll").click(function () {
            for (var i = 1; i <= 7; i++) {
                $("#TimePicker td[data-row=" + i + "]").removeClass("time_opted");
            }
        });
    })
</script>