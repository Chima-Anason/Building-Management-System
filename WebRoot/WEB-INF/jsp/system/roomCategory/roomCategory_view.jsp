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
								<form action="roomCategory/${msg }.do" name="roomCategoryForm" id="roomCategoryForm" method="post">
									<input type="hidden" name="CATEGORY_ID" id="category_id" value="${pd.CATEGORY_ID }"/>
									<div id="zhongxin" style="padding-top: 13px;">
									<table id="table_report" class="table table-striped table-bordered table-hover">
								
										<c:if test="${null == rpd}">
										
										<c:if test="${fx == 'head'}">
											<input name="CATEGORY_ID" id="category_id" value="${pd.CATEGORY_ID }" type="hidden" />
										</c:if>
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">房间类别编号:</td>
											<td><input type="text" name="CATEGORY_ORDER" id="category_order" value="${null==pd?'无此房间类别编号':pd.CATEGORY_ORDER }" maxlength="32" title="房间类别编号" style="width:98%;" disabled="disabled"/></td>
										</tr>
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">房间类别名称:</td>
											<td><input type="text" name="CATEGORY_NAME" id="CATEGORY_NAME" value="${pd.CATEGORY_NAME }" maxlength="32" title="房间类别名称" onblur="hasN('${pd.CATEGORY_NAME }')" style="width:98%;" disabled="disabled"/></td>
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
		if($("#category_id").val()!=""){
			$("#category_name").attr("readonly","readonly");
			$("#category_name").css("color","gray");
		}
	});
	
	
	
	
	//判断邮箱是否存在
	function hasN(CATEGORY_NAME){
		var CATEGORY_NAME = $.trim($("#CATEGORY_NAME").val());
		$.ajax({
			type: "POST",
			url: '<%=basePath%>roomCategory/hasN.do',
	    	data: {CATEGORY_NAME:CATEGORY_NAME,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" != data.result){
					 $("#CATEGORY_NAME").tips({
							side:3,
				            msg:'邮箱 '+CATEGORY_NAME+' 已存在',
				            bg:'#AE81FF',
				            time:3
				        });
					 $("#CATEGORY_NAME").val('');
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