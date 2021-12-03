<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
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
						<form action="buildingCategory/${msg}.do" name="form1" id="form1"  method="post">
						<input type="hidden" name="CATEGORY_ID" id="id" value="${pd.CATEGORY_ID}"/>
						<input name="PARENT_ID" id="parent_id" value="${pd.parent_id }" type="hidden">
							<div id="zhongxin" style="padding-top:13px;">
							<table class="center" style="width:100%;">
								<tr style="text-align: center;">
									<td><input type="text" name="CATEGORY_NAME" id="categoryName" value="${pd.CATEGORY_NAME }" maxlength="32" placeholder="这里输入名称" title="名称" onblur="hasN('${pd.CATEGORY_NAME }')" style="width:99%;"/></td>
								</tr>
								<tr>
									<td style="text-align: center;padding-top:5px;">
										<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
										<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
									</td>
								</tr>
							</table>
							</div>
						</form>
					
					<div id="zhongxin2" class="center" style="display:none"><img src="static/images/jzx.gif" style="width: 50px;" /><br/><h4 class="lighter block green"></h4></div>

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


	<!-- 页面底部js¨ -->
	<%@ include file="../index/foot.jsp"%>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script type="text/javascript">
	top.hangge();
	//保存
	function save(){
		if($("#categoryName").val()==""){
			$("#categoryName").tips({
				side:3,
	            msg:'请输入楼宇类别名称',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#categoryName").focus();
			return false;
		}else{
			$("#categoryName").val($.trim($("#categoryName").val()));
		}
			$("#form1").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
	}
	
	
	
	//判断编码是否存在
	function hasN(CATEGORY_ORDER){
		var CATEGORY_NAME = $("#categoryName").val();
		
		$.ajax({
			type: "POST",
			url: '<%=basePath%>buildingCategory/hasN.do',
	    	data: {CATEGORY_NAME:CATEGORY_NAME,CATEGORY_ORDER:CATEGORY_ORDER,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" != data.result){
					 $("#categoryName").tips({
							side:3,
				            msg:'楼宇类别名'+CATEGORY_NAME+'已存在!',
				            bg:'#AE81FF',
				            time:3
				        });
					  $("#categoryName").val(''); 
				 }
			}
		});
	}
	
	
	</script>
</body>
</html>

