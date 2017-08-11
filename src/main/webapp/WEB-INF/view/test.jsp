<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div>
    <div id="chart_home" style="height:400px;"></div>
</div>
<script type="text/javascript">
    $(function () {
        $.ajax({
            url:"http://localhost:10000/report/test/data.shtml",
             success:function(data){
                var arrName=[];
                var arrCount=[];
                data.forEach(function(info,i){
                        if(info.name && i<=10){
                         arrName.push(info.name);
                         arrCount.push(info.count);
                        }

                });
                console.log(arrName);
                console.log(arrCount);
                var dataAxis = arrName;
                var data = arrCount;
                var yMax = 500;
                var dataShadow = [];

                for (var i = 0; i < data.length; i++) {
                    dataShadow.push(yMax);
                }

                var option = {
                    color: ['#3398DB'],
                    tooltip : {
                        trigger: 'axis',
                        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                        }
                    },
                    grid: {
                        left: '3%',
                        right: '4%',
                        bottom: '3%',
                        containLabel: true
                    },
                    xAxis : [
                        {
                            type : 'category',
                            data : arrName,
                            axisTick: {
                                alignWithLabel: true
                            }
                        }
                    ],
                    yAxis : [
                        {
                            type : 'value'
                        }
                    ],
                    series : [
                        {
                            name:'直接访问',
                            type:'bar',
                            data:arrCount,
                        }
                    ]
                };


                echarts.init($("#chart_home")[0],"macarons").setOption(option);
             }
        })
    })
    </script>