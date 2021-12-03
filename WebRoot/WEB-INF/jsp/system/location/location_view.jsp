<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<!-- 下拉框 -->
<link rel="stylesheet" href="static/ace/css/chosen.css" />
<!-- jsp文件头和头部 -->
<%@ include file="../index/top.jsp"%>
</head>
<body class="no-skin">
	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container">
		<!-- /section:basics/sidebar -->
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<div class="row">
						<div class="col-xs-12">
								<form action="location/${msg }.do" name="locationForm" id="locationForm" method="post">
									<input type="hidden" name="LOCATION_ID" id="location_id" value="${pd.LOCATION_ID }"/>
									<div id="zhongxin" style="padding-top: 13px;">
									<table id="table_report" class="table table-striped table-bordered table-hover">
									
										
										
										
										
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">楼宇名:</td>
											<td id="juese">
											<select class="chosen-select form-control" name="BUILDING_ID" id="building_id" style="vertical-align:top;" style="width:98%;" disabled="disabled">
											<c:forEach items="${buildingList}" var="building">
												<option value="${building.BUILDING_ID }" <c:if test="${building.BUILDING_ID == pd.BUILDING_ID }">selected</c:if>>${building.BUILDING_NAME }</option>
											</c:forEach>
											</select>
											</td>
										</tr>
										<c:if test="${fx == 'head'}">
											<input name="LOCATION_ID" id="location_id" value="${pd.LOCATION_ID }" type="hidden" />
										</c:if>
										
										<c:if test="${fx != 'head'}">
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">房间名:</td>
											<td id="juese">
											<select class="chosen-select form-control" name="CATEGORY_ID" id="category_id" style="vertical-align:top;" style="width:98%;" disabled="disabled">
											<c:forEach items="${roomCatList}" var="roomCat">
												<option value="${roomCat.CATEGORY_ID }" <c:if test="${roomCat.CATEGORY_ID == pd.CATEGORY_ID }">selected</c:if>>${roomCat.CATEGORY_NAME }</option>
											</c:forEach>
											</select>
											</td>
										</tr>
										<c:if test="${fx == 'head'}">
											<input name="LOCATION_ID" id="location_id" value="${pd.LOCATION_ID }" type="hidden" />
										</c:if>
										
										<c:if test="${fx != 'head'}">
										<tr>
											
											<td id="juese"><button class="btn btn-outline-secondary" type="button" name="HEADMAN_ID" id="HEADMAN_ID"   onclick="xuanTp('HEADMAN_NAME');" disabled="disabled">选择负责人</button></td>
  											<td><input type="text" name="HEADMAN_NAME" id="HEADMAN_NAME" value="${pd.HEADMAN_NAME}" maxlength="200" placeholder="这里输入负责人" title="负责人" disabled="disabled"></td>
  												
										</tr>
										</c:if>
										<c:if test="${fx == 'head'}">
											<input name="LOCATION_ID" id="location_id" value="${pd.LOCATION_ID }" type="hidden" />
										</c:if>
										
										
										
										
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">地点编号:</td>
											<td><input type="text" name="LOCATION_NO" id="location_no" value="${null==pd?'无此地点编号':pd.LOCATION_NO }" maxlength="32" title="地点编号" style="width:98%;" disabled="disabled"/></td>
										</tr>
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">房间名称:</td>
											<td><input type="text" name="ROOM_NAME" id="room_name" value="${null==pd?'无此房间名称':pd.ROOM_NAME }" maxlength="32" title="房间名称" style="width:98%;" disabled="disabled"/></td>
										</tr>
									
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">地点名称:</td>
											<td><input type="text" name="LOCATION_NAME" id="LOCATION_NAME"  value="${pd.LOCATION_NAME }"  maxlength="32" title="地点名称" style="width:98%;" disabled="disabled"/></td>
										</tr>
										
										
						
										</c:if>
									</table>
									</div>
								</form>
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
				</div>
				<!-- /.page-content -->
			</div>
		</div>
		<!-- /.main-content -->
	</div>
	<!-- /.main-container -->
	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="../index/foot.jsp"%>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!-- inline scripts related to this page -->
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
</body>
<script type="text/javascript">
	$(top.hangge());
	$(document).ready(function(){
		if($("#location_id").val()!=""){
			$("#location_name").attr("readonly","readonly");
			$("#location_name").css("color","gray");
		}
	});
	
	
	function hasU(){
		var USERNAME = $.trim($("#loginname").val());
		$.ajax({
			type: "POST",
			url: '<%=basePath%>user/hasU.do',
	    	data: {USERNAME:USERNAME,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" == data.result){
					$("#userForm").submit();
					$("#zhongxin").hide();
					$("#zhongxin2").show();
				 }else{
					$("#loginname").css("background-color","#D16E6C");
					setTimeout("$('#loginname').val('此用户名已存在!')",500);
				 }
			}
		});
	}
	
	//判断邮箱是否存在
	function hasE(USERNAME){
		var EMAIL = $.trim($("#EMAIL").val());
		$.ajax({
			type: "POST",
			url: '<%=basePath%>user/hasE.do',
	    	data: {EMAIL:EMAIL,USERNAME:USERNAME,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" != data.result){
					 $("#EMAIL").tips({
							side:3,
				            msg:'邮箱 '+EMAIL+' 已存在',
				            bg:'#AE81FF',
				            time:3
				        });
					 $("#EMAIL").val('');
				 }
			}
		});
	} 
	
	$(function() {
		//下拉框
		if(!ace.vars['touch']) {
			$('.chosen-select').chosen({allow_single_deselect:true}); 
			$(window)
			.off('resize.chosen')
			.on('resize.chosen', function() {
				$('.chosen-select').each(function() {
					 var $this = $(this);
					 $this.next().css({'width': $this.parent().width()});
				});
			}).trigger('resize.chosen');
			$(document).on('settings.ace.chosen', function(e, event_name, event_val) {
				if(event_name != 'sidebar_collapsed') return;
				$('.chosen-select').each(function() {
					 var $this = $(this);
					 $this.next().css({'width': $this.parent().width()});
				});
			});
			$('#chosen-multiple-style .btn').on('click', function(e){
				var target = $(this).find('input[type=radio]');
				var which = parseInt(target.val());
				if(which == 2) $('#form-field-select-4').addClass('tag-input-style');
				 else $('#form-field-select-4').removeClass('tag-input-style');
			});
		}
	});
</script>
</html>