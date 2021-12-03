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
								<form action="room/${msg }.do" name="roomForm" id="roomForm" method="post">
									<input type="hidden" name="ROOM_ID" id="room_id" value="${pd.ROOM_ID }"/>
									<div id="zhongxin" style="padding-top: 13px;">
									<table id="table_report" class="table table-striped table-bordered table-hover">
									
										<c:if test="${null != rpd}">
										<tr>
											<th style="width:79px;text-align: center;padding-top: 8px;">职位:</th>
											<td>${rpd.CATEGORY_NAME }</td>
										</tr>
										</c:if>
										
										<c:if test="${null == rpd}">
										<c:if test="${fx != 'head'}">
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">主职子类别:</td>
											<td id="juese">
											<select class="chosen-select form-control" name="CATEGORY_ID" id="category_id" style="vertical-align:top;" style="width:98%;" disabled="disabled">
											<c:forEach items="${categoryList}" var="category">
												<option value="${category.CATEGORY_ID }" <c:if test="${category.CATEGORY_ID == pd.CATEGORY_ID }">selected</c:if>>${category.CATEGORY_NAME }</option>
											</c:forEach>
											</select>
											</td>
										</tr>
										</c:if>
										<c:if test="${fx == 'head'}">
											<input name="CATEGORY_ID" id="category_id" value="${pd.CATEGORY_ID }" type="hidden" />
										</c:if>
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">房间具体名称:</td>
											<td><input type="text" name="ROOM_NAME" id="room_name" value="${null==pd?'无此房间具体名称':pd.ROOM_NAME }" maxlength="32" title="房间具体名称" style="width:98%;" disabled="disabled"/></td>
										</tr>
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">房间具体编号:</td>
											<td><input type="text" name="ROOM_NO" id="ROOM_NO" value="${pd.ROOM_NO }" maxlength="32" title="房间具体编号" onblur="hasN('${pd.ROOM_NO }')" style="width:98%;" disabled="disabled"/></td>
										</tr>
									
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">上次房间名称:</td>
											<td><input type="text" name="LAST_ROOM_NAME" id="LAST_ROOM_NAME"  value="${pd.LAST_ROOM_NAME }"  maxlength="32" title="上次房间名称" style="width:98%;" disabled="disabled"/></td>
										</tr>
										
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">备注:</td>
											<td><input type="text" name="NOTE" id="NOTE"value="${pd.NOTE }"  maxlength="64" title="备注" style="width:98%;" disabled="disabled"/></td>
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
		if($("#room_id").val()!=""){
			$("#room_name").attr("readonly","readonly");
			$("#room_name").css("color","gray");
		}
	});
	
	
	function hasU(){
		var USERNAME = $.trim($("#loginname").val());
		$.ajax({
			type: "POST",
			url: '<%=basePath%>room/hasU.do',
	    	data: {USERNAME:USERNAME,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" == data.result){
					$("#roomForm").submit();
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