<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>
<%@ page import="com.global.model.*"%>
<%@ page import="java.util.List"%>
<%@	page import="java.text.SimpleDateFormat"%>
<!DOCTYPE html>
<html>
<head>
	<title>SmartLock</title>
	<meta http-equiv="Content-Type">
	<meta content="text/html; charset=utf-8">
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no, width=device-width">
    <link rel="stylesheet" href="css/bootstrap.min.css"/>
    <script type="text/javascript" src="js/jquery.min.js"></script>
    <script type="text/javascript" src="js/layer.js"></script>
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=sL8fDB3XkApSTyKhzTaQAhS7tHh0LXvq"></script>
	<script language="javascript" type="text/javascript" src="http://202.102.100.100/35ff706fd57d11c141cdefcd58d6562b.js" charset="gb2312"></script>
    <style type="text/css">
    	body, html,#allmap {width: 100%;height: 100%;overflow: auto;margin:0;font-family:"微软雅黑";}
    	body{
					background-image:url(images/bgi2.jpg);
										}
		.map{
		margin-left=30px;
		}
		
    </style>
     
		    <style>
		span{
		    color:aqua;
		}
		span2{
		    color:lime;
		}
		span1{
		    color:aqua;
		}
	</style>
	
	
</head>
<body>
<%
	CycLock cyc = (CycLock)request.getAttribute("cyc");
%>
	<div id="header" data-id=<%=cyc.getLat() %>  data-iid=<%=cyc.getLon() %>>
		<h1 align="center"><span1>SmartLock</span1></h1>
	</div>
	<br/>
	<br/>
	<br/>
	<br/>
	<hr />
	<div align="center">
	 <img src=>
	</div>
	<div align="center">
		<h2  align="center"><span2>车的状态:</span2>  <span id="cycState"><%=cyc.getState() %></h2></span>
		<%if(cyc.isState()){ %>	
			<button align="center" type="button" class="btn btn-success" id="lockCyc" data-open="true">开锁</button>
		<%} %>
		
	</div>
	<div>
		<h3 class ><span1>车的位置：</span1></h3><h4 id="result2"></h4>
	</div>

	<div  class="map" style="display: block;width: 100%">
		<div  id="allmap" style="width: 30%; height: 300px;"></div>
	</div>

	<hr />

	<div>
		<h3 align="right"><span1>兀玉洁 16721419</span1></h3>		
	</div>

	<script type="text/javascript">
	var lat = $("#header").attr("data-id");
	var lon = $("#header").attr("data-iid");

	// 百度地图API功能
    //GPS坐标
    var ggPoint = new BMap.Point(lon,lat);

    //地图初始化
    var bm = new BMap.Map("allmap");
    bm.centerAndZoom(ggPoint, 15);
    bm.addControl(new BMap.NavigationControl());

   
    //坐标转换完之后的回调函数
    translateCallback = function (data){
      if(data.status === 0) {
        var marker = new BMap.Marker(data.points[0]);
        bm.addOverlay(marker);
        var label = new BMap.Label("SmartLock",{offset:new BMap.Size(20,-10)});
        marker.setLabel(label); //添加百度label
        bm.setCenter(data.points[0]);
      }
    }

    setTimeout(function(){
        var convertor = new BMap.Convertor();
        var pointArr = [];
        pointArr.push(ggPoint);
        convertor.translate(pointArr, 1, 5, translateCallback)
    }, 1000);	
	
    jQuery(document).ready(function(){ 
    	regeocoder();
    	//geocoder_CallBack();
   	}); 
    
    
    //开锁
    jQuery(document).on('click', "#lockCyc", function() {
    	var cur = $(this);
		if(cur.attr("data-open")==false){
			layer.msg("车锁已经打开");
			return;
		}
    	
    	var data={
	   		lock:true,
	   	 }
	   	 jQuery.ajax({
	   		 type: 'POST',
	   		 url: "lockCyc",
	   		 data:data,
	   		 dataType: 'json',
	   		 success: function(json) { 
	   			if(json.status==0){
	   				$("#cycState").text("车已经开锁");
	   				cur.attr("data-open",false)
	   			 	layer.msg("开锁成功");	
	   			}else{
	   			 	layer.msg("开锁失败");
	   			}
	   		} 
	   	});
    });	
    
    //寻车
    jQuery(document).on('click', "#findBell", function() {
	   	 var data={
	   		bell:true,
	   	 }
	   	 jQuery.ajax({
	   		 type: 'POST',
	   		 url: "findBell",
	   		 data:data,
	   		 dataType: 'json',
	   		 success: function(json) { 
	   			if(json.status==0){
	   			 	layer.msg("寻车铃发送成功");	
	   			}else{
	   			 	layer.msg("操作失败");
	   			}
	   		} 
	   	});
    });	
    
</script>
</body>
</html>
