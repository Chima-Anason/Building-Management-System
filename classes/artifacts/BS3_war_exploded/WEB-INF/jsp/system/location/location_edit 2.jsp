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
									<textarea style="display: none;" name="BUILDING_IDS" id="BUILDING_IDS" >${pd.BUILDING_IDS }</textarea>
									<div id="zhongxin" style="padding-top: 13px;">
									<table id="table_report" class="table table-striped table-bordered table-hover">

										<c:if test="${fx != 'head'}">
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">楼宇:</td>
											<td id="juese">
											<select class="chosen-select form-control" name="BUILDING_ID" id="building_id" data-placeholder="请选择楼宇" style="vertical-align:top;" style="width:98%;" >
											<option value=""></option>
											<c:forEach items="${buildingList}" var="building">
												<option value="${building.BUILDING_ID }" <c:if test="${building.BUILDING_ID == pd.BUILDING_ID }">selected</c:if>>${building.BUILDING_NAME }</option>
											</c:forEach>
											</select>
											</td>
										</tr>
										</c:if>
										<c:if test="${fx == 'head'}">
											<input name="BUILDING_ID" id="building_id" value="${pd.BUILDING_ID }" type="hidden" />
										</c:if>
										
										
										<c:if test="${fx != 'head'}">
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">用途:</td>
											<td id="juese1">
											<select class="chosen-select form-control" name="CATEGORY_ID" id="category_id" data-placeholder="请选择用途" style="vertical-align:top;" style="width:98%;" >
											<option value=""></option>
											<c:forEach items="${roomCategoryList}" var="roomCat">
												<option value="${roomCat.CATEGORY_ID }" <c:if test="${roomCat.CATEGORY_ID == pd.CATEGORY_ID }">selected</c:if>>${roomCat.CATEGORY_NAME }</option>
											</c:forEach>
											</select>
											</td>
										</tr>
										</c:if>
										<c:if test="${fx == 'head'}">
											<input name="CATEGORY_ID" id="category_id" value="${pd.CATEGORY_ID }" type="hidden" />
										</c:if>
										
										
										
										
										<c:if test="${fx != 'head'}">
										<tr>
											
											<%-- <td><a class="btn btn-xs btn-info" title="负责人" onclick="xuanTp('HEADMAN_ID');">选择负责人 </a></td>
											<td><input type="text" disabled="disabled" name="HEADMAN_NAME" id="HEADMAN_NAME" value="${pd.HEADMAN_NAME }" maxlength="64" placeholder="负责人" title="负责人" style="width:98%;"/></td>
											 --%>
											
									
  												<td style="width:79px;text-align: right;padding-top: 13px;">负责人:</td>
  												<td id="juese2"><input type="text" class="form-control" name="HEADMAN_NAME" id="HEADMAN_NAME" value="${pd.HEADMAN_NAME}" maxlength="200" placeholder="这里输入负责人" title="负责人" aria-label="HEADMAN_NAME" aria-describedby="HEADMAN_ID"></td>
  												<td ><button class="btn btn-outline-secondary" type="button" name="HEADMAN_ID" id="HEADMAN_ID"  onclick="xuanTp('HEADMAN_NAME');">选择负责人</button></td>
  												
  												
  												<!-- <input type="text" class="form-control" placeholder="Recipient's username" aria-label="Recipient's username" aria-describedby="button-addon2">
  												<button class="btn btn-outline-secondary" type="button" id="button-addon2">Button</button> -->
											
											<%-- <td id="juese">
											<select class="chosen-select form-control" name="HEADMAN_ID" id="headman_id" data-placeholder="负责人" style="vertical-align:top;" style="width:98%;" >
											<option value=""></option>
											<c:forEach items="${headmanList}" var="headman">
												<option value="${headman.HEADMAN_ID }" <c:if test="${headman.HEADMAN_ID == pd.HEADMAN_ID }">selected</c:if>>${headman.HEADMAN_NAME }</option>
											</c:forEach>
											</select>
											</td> --%>
										</tr>
										</c:if>
										<c:if test="${fx == 'head'}">
											<input name="HEADMAN_ID" id="headman_id" value="${pd.HEADMAN_ID }" type="hidden" />
										</c:if>
										
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">房间:</td>
											<td><input type="text" name="ROOM_NAME" id="ROOM_NAME"value="${pd.ROOM_NAME }" placeholder="这里输入房间" maxlength="64" title="房间" style="width:98%;"/></td>
										</tr>
										
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">地点名称:</td>
											<td><input type="text" name="LOCATION_NAME" id="LOCATION_NAME" value="${pd.LOCATION_NAME }" maxlength="64" placeholder="这里输入地点名称" title="地点名称" onblur="hasN('${pd.LOCATION_NAME }')" style="width:98%;"/></td>
											
										</tr>
										
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">备注:</td>
											<td><input type="text" name="NOTE" id="NOTE"value="${pd.NOTE }" placeholder="这里输入备注" maxlength="64" title="备注" style="width:98%;"/></td>
										</tr>
										<tr>
											<td style="text-align: center;" colspan="10">
												<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
												<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
											</td>
										</tr>
									</table>
									</div>
									<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green"></h4></div>
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
		 $('#form-field-select-4').addClass('tag-input-style');
		  /* if($("#LOCATION_NAME").val()!=""){
			 $("#LOCATION_NAME").attr("readonly","readonly");
			$("#LOCATION_NAME").css("color","gray"); 
		}  
		 */
		 
		
	});
	//保存
	function save(){
		   if($("#building_id").val()==""){
			$("#juese").tips({
				side:3,
	            msg:'选择楼宇',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#building_id").focus();
			return false;
		}  
		
		
		 if($("#category_id").val()==""){
			$("#juese1").tips({
				side:3,
	            msg:'选择用途',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#category_id").focus();
			return false;
		}  
		
		 if($("#HEADMAN_NAME").val()==""){
			$("#juese2").tips({
				side:3,
	            msg:'选择负责人',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#HEADMAN_ID").focus();
			return false;
		} 
		
		
		
		if($("#LOCATION_NAME").val()=="" ){
			$("#LOCATION_NAME").tips({
				side:3,
	            msg:'输入地点名称',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#LOCATION_NAME").focus();
			$("#LOCATION_NAME").val('');
			$("#LOCATION_NAME").css("background-color","white");
			return false;
		}else{
			$("#LOCATION_NAME").val(jQuery.trim($('#LOCATION_NAME').val()));
		}
		
		
			
			$("#locationForm").submit();
			
			$("#zhongxin").hide();
			
			$("#zhongxin2").show();
		
	}
	
	
	
	//选择图片
	function xuanTp(ID){
		 top.jzts();
		 var diag = new top.Dialog();
		 diag.Drag=true;
		 diag.Title ="选择负责人";
		 diag.URL = '<%=basePath%>headman/listfortc.do';
		 diag.Width = 860;
		 diag.Height = 680;
		 diag.CancelEvent = function(){ //关闭事件
			 $("#"+ID).val(diag.innerFrame.contentWindow.document.getElementById('xzvalue').value);
			 diag.close();
		 };
		 diag.show();
		 
		
	}
	
	//判断用户名是否存在
	function hasU(){
		var LOCATION_NAME = $.trim($("#location_name").val());
		$.ajax({
			type: "POST",
			url: '<%=basePath%>location/hasN.do',
	    	data: {LOCATION_NAME:LOCATION_NAME,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" == data.result){
					$("#locationForm").submit();
					$("#zhongxin").hide();
					$("#zhongxin2").show();
				 }else{
					$("#location_name").css("background-color","#D16E6C");
					setTimeout("$('#location_name').val('地点名已存在!')",500);
				 }
			}
		});
	}
	
	
	
	//判断编码是否存在
	function hasN(LOCATION_NO){
		var LOCATION_NAME = $("#LOCATION_NAME").val();
		$.ajax({
			type: "POST",
			url: '<%=basePath%>location/hasN.do',
	    	data: {LOCATION_NAME:LOCATION_NAME,LOCATION_NO:LOCATION_NO,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" != data.result){
					 $("#LOCATION_NAME").tips({
							side:3,
				            msg:'地点名'+LOCATION_NAME+'已存在!',
				            bg:'#AE81FF',
				            time:3
				        });
					 $("#LOCATION_NAME").val('');
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
	
	//移除副职角色
	function removeBuildingId(BUILDING_ID){
		var OBUILDING_IDS = $("#BUILDING_IDS");
		var BUILDING_IDS = OBUILDING_IDS.val();
		BUILDING_IDS = BUILDING_IDS.replace(BUILDING_ID+",fh,","");
		OBUILDING_IDS.val(BUILDING_IDS);
	}
	//添加副职角色
	function addBuildingId(BUILDING_ID){
		var OBUILDING_IDS = $("#BUILDING_IDS");
		var BUILDING_IDS = OBUILDING_IDS.val();
		if(!isContains(BUILDING_IDS,BUILDING_ID)){
			BUILDING_IDS = BUILDING_IDS + BUILDING_ID + ",fh,";
			OBUILDING_IDS.val(BUILDING_IDS);
		}
	}
	
	//移除副职角色
	function removeCategoryId(CATEGORY_ID){
		var OCATEGORY_IDS = $("#CATEGORY_IDS");
		var CATEGORY_IDS = OCATEGORY_IDS.val();
		CATEGORY_IDS = CATEGORY_IDS.replace(CATEGORY_ID+",fh,","");
		OCATEGORY_IDS.val(CATEGORY_IDS);
	}
	//添加副职角色
	function addCategoryId(CATEGORY_ID){
		var OCATEGORY_IDS = $("#CATEGORY_IDS");
		var CATEGORY_IDS = OCATEGORY_IDS.val();
		if(!isContains(CATEGORY_IDS,CATEGORY_ID)){
			CATEGORY_IDS = CATEGORY_IDS + CATEGORY_ID + ",fh,";
			OCATEGORY_IDS.val(CATEGORY_IDS);
		}
	}
	
	//移除副职角色
	function removeHeadmanId(HEADMAN_ID){
		var OHEADMAN_IDS = $("#HEADMAN_IDS");
		var HEADMAN_IDS = OHEADMAN_IDS.val();
		HEADMAN_IDS = HEADMAN_IDS.replace(HEADMAN_ID+",fh,","");
		OHEADMAN_IDS.val(HEADMAN_IDS);
	}
	//添加副职角色
	function addHeadmanId(HEADMAN_ID){
		var OHEADMAN_IDS = $("#HEADMAN_IDS");
		var HEADMAN_IDS = OHEADMAN_IDS.val();
		if(!isContains(HEADMAN_IDS,HEADMAN_ID)){
			HEADMAN_IDS = HEADMAN_IDS + HEADMAN_ID + ",fh,";
			OHEADMAN_IDS.val(HEADMAN_IDS);
		}
	}
	
		//移除副职角色
	function removeLocationId(LOCATION_ID){
		var OLOCATION_IDS = $("#LOCATION_IDS");
		var LOCATION_IDS = OLOCATION_IDS.val();
		LOCATION_IDS = LOCATION_IDS.replace(LOCATION_ID+",fh,","");
		OLOCATION_IDS.val(LOCATION_IDS);
	}
	//添加副职角色
	function addLocationId(LOCATION_ID){
		var OLOCATION_IDS = $("#LOCATION_IDS");
		var LOCATION_IDS = OLOCATION_IDS.val();
		if(!isContains(LOCATION_IDS,LOACTION_ID)){
			LOCATION_IDS = LOCATION_IDS + LOCATION_ID + ",fh,";
			OLOCATION_IDS.val(LOCATION_IDS);
		}
	}
	
	function isContains(str, substr) {
	     return str.indexOf(substr) >= 0;
	 }
</script>
</html>