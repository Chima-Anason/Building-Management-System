
package com.fh.controller.system.room;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.Building;
import com.fh.entity.system.Category;
import com.fh.entity.system.Role;
import com.fh.entity.system.Room;
import com.fh.service.system.category.CategoryManager;
import com.fh.service.system.fhlog.FHlogManager;
import com.fh.service.system.menu.MenuManager;
import com.fh.service.system.role.RoleManager;
import com.fh.service.system.room.RoomManager;
import com.fh.service.system.user.UserManager;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.FileDownload;
import com.fh.util.FileUpload;
import com.fh.util.GetPinyin;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelRead;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.PathUtil;
import com.fh.util.Tools;

/** 
 * 类名称：RoomController
 * 创建人：FH fh313596790qq(青苔)
 * 更新时间：2021年4月29日
 * @version
 */
@Controller
@RequestMapping(value="/room")
public class RoomController extends BaseController {
	
	String menuUrl = "room/listRooms.do"; //菜单地址(权限用)
	@Resource(name="roomService")
	private RoomManager roomService;
	@Resource(name="categoryService")
	private CategoryManager categoryService;
	@Resource(name="menuService")
	private MenuManager menuService;
	@Resource(name="fhlogService")
	private FHlogManager FHLOG;
	
	private Room room;
	
	/**Display the list of rooms
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/listRooms")
	public ModelAndView listRooms(Page page)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	roomList = roomService.listRooms(page);	//列出楼宇列表/Make a list of rooms
		pd.put("CATEGORY_ID", "02");
		List<Category> categoryList = categoryService.listAllCategoriesByPId(pd);//列出所有系统楼宇类别/List all room category
		mv.setViewName("system/room/room_list");
		mv.addObject("roomList", roomList);
		mv.addObject("categoryList", categoryList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	/**Delete room
	 * @param out
	 * @throws Exception 
	 */
	@RequestMapping(value="/deleteU")
	public void deleteU(PrintWriter out) throws Exception{
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		logBefore(logger, Jurisdiction.getUsername()+"删除user");
		PageData pd = new PageData();
		pd = this.getPageData();
		roomService.deleteU(pd);
		FHLOG.save(Jurisdiction.getUsername(), "删除系统用户："+pd);
		out.write("success");
		out.close();
	}
	
	/**Go to adding new room page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goAddU")
	public ModelAndView goAddU()throws Exception{
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("CATEGORY_ID", "02");
		List<Category> categoryList = categoryService.listAllCategoriesByPId(pd);//列出所有楼宇类别/List all room category
		mv.setViewName("system/room/room_edit");
		mv.addObject("msg", "saveU");
		mv.addObject("pd", pd);
		mv.addObject("categoryList", categoryList);
		return mv;
	}
	
	/**Save room
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/saveU")
	public ModelAndView saveU() throws Exception{
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		logBefore(logger, Jurisdiction.getUsername()+"新增room");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
//		String p = pd.getString("CATEGORY_ID");		//父类别 id/ Parent category Id
//		pd.put("ROOM_ID", p);
		
		List<Room> roomList_z = roomService.listRoomsByCId(pd);  //loop thru the list by category id and add 1 then assign it to building No 
		int total = 0;
		for (int i=0; i < roomList_z.size() ;i++) {
			++total;
		}
		int a = ++total;
		pd.put("ROOM_NO", "0"+a);
		
		pd.put("ROOM_ID", this.get32UUID());
		pd.put("LAST_ROOM_NAME", "");
		pd.put("CREATED_TIME", new Date());      //add current date when adding a new room
		if(roomService.findByRoom_Name(pd) == null || "".equals(roomService.findByRoom_Name(pd))){	//Determine if the room name exists
			roomService.saveU(pd); 					//执行保存
			FHLOG.save(Jurisdiction.getUsername(), "新增楼宇："+pd.getString("ROOM_NAME"));
			mv.addObject("msg","success");
		}else{
			mv.addObject("msg","failed");
		}
		mv.setViewName("save_result");
		return mv;
	}
	

	
	/**To determine if the room not exists
	 * @return
	 */
	@RequestMapping(value="/hasN")
	@ResponseBody
	public Object hasE(){
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(roomService.findByRoomNo(pd) != null){
				errInfo = "error";
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);				//返回结果
		return AppUtil.returnObject(new PageData(), map);
	}
	
	
	
	/**To modify the room page (system room list modification)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goEditU")
	public ModelAndView goEditU() throws Exception{
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//if("1".equals(pd.getString("USER_ID"))){return null;}		//不能修改admin用户
		pd.put("CATEGORY_ID", "02");
		List<Category> categoryList = categoryService.listAllCategoriesByPId(pd);	//列出所有楼宇类别/List all room category
		mv.addObject("fx", "room");
		pd = roomService.findById(pd);								//根据ID读取
//		List<Role> froleList = new  ArrayList<Role>();				//存放副职角色
//		String ROLE_IDS = pd.getString("ROLE_IDS");					//副职角色ID
//		if(Tools.notEmpty(ROLE_IDS)){
//			String arryROLE_ID[] = ROLE_IDS.split(",fh,");
//			for(int i=0;i<roleList.size();i++){
//				Role role = roleList.get(i);
//				String roleId = role.getROLE_ID();
//				for(int n=0;n<arryROLE_ID.length;n++){
//					if(arryROLE_ID[n].equals(roleId)){
//						role.setRIGHTS("1");	//此时的目的是为了修改用户信息上，能看到副职角色都有哪些
//						break;
//					}
//				}
//				froleList.add(role);
//			}
//		}else{
//			froleList = roleList;
//		}
		mv.setViewName("system/room/room_edit");
		mv.addObject("msg", "editU");
		mv.addObject("pd", pd);
		mv.addObject("categoryList", categoryList);
	//	mv.addObject("froleList", froleList);
		return mv;
	}
	
	/**To modify the room page (individual changes)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goEditMyU")
	public ModelAndView goEditMyU() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.addObject("fx", "head");
		pd.put("CATEGORY_ID", "02");
		List<Category> categoryList = categoryService.listAllCategoriesByPId(pd);	//列出所有楼宇类别/List all room category
		pd.put("ROOM_NAME", Jurisdiction.getUsername());
		pd = roomService.findByRoomNumber(pd);						//根据楼宇编号读取
		mv.setViewName("system/room/room_edit");
		mv.addObject("msg", "editU");
		mv.addObject("pd", pd);
		mv.addObject("categoryList", categoryList);
		return mv;
	}
	
	/**View the room
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/view")
	public ModelAndView view() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String ROOM_NAME = pd.getString("ROOM_NAME");
		//if("admin".equals(USERNAME)){return null;}	//不能查看admin用户
		pd.put("CATEGORY_ID", "02");
		List<Category> categoryList = categoryService.listAllCategoriesByPId(pd);	//列出所有楼宇类别/List all room category
		pd = roomService.findByRoom_Name(pd);						//根据ID读取
		if(null == pd){
			PageData rpd = new PageData();
			rpd.put("CATEGORY_ORDER", ROOM_NAME);							//楼宇名查不到数据时就把数据当作类别的编码去查询类别表(工作流的待办人物，查看代办人资料时用到)
			rpd = categoryService.getCategoryByCategory_order(rpd);
			mv.addObject("rpd", rpd);
		}
		mv.setViewName("system/room/room_view");
		mv.addObject("msg", "editU");
		mv.addObject("pd", pd);
		mv.addObject("categoryList", categoryList);
		return mv;
	}
	
//	/**去修改用户页面(在线管理页面打开)
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping(value="/goEditUfromOnline")
//	public ModelAndView goEditUfromOnline() throws Exception{
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		pd = this.getPageData();
//		if("admin".equals(pd.getString("USERNAME"))){return null;}	//不能查看admin用户
//		pd.put("ROLE_ID", "1");
//		List<Role> roleList = roleService.listAllRolesByPId(pd);	//列出所有系统用户角色
//		pd = userService.findByUsername(pd);						//根据ID读取
//		List<Role> froleList = new  ArrayList<Role>();				//存放副职角色
//		String ROLE_IDS = pd.getString("ROLE_IDS");					//副职角色ID
//		if(Tools.notEmpty(ROLE_IDS)){
//			String arryROLE_ID[] = ROLE_IDS.split(",fh,");
//			for(int i=0;i<roleList.size();i++){
//				Role role = roleList.get(i);
//				String roleId = role.getROLE_ID();
//				for(int n=0;n<arryROLE_ID.length;n++){
//					if(arryROLE_ID[n].equals(roleId)){
//						role.setRIGHTS("1");	//此时的目的是为了修改用户信息上，能看到副职角色都有哪些
//						break;
//					}
//				}
//				froleList.add(role);
//			}
//		}else{
//			froleList = roleList;
//		}
//		mv.setViewName("system/user/user_edit");
//		mv.addObject("msg", "editU");
//		mv.addObject("pd", pd);
//		mv.addObject("roleList", roleList);
//		mv.addObject("froleList", froleList);
//		return mv;
//	}
	
	/**
	 * Modify the room
	 */
	@RequestMapping(value="/editU")
	public ModelAndView editU() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改room");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		pd.put("EDITED_TIME", new Date());    //add current date to EDITED_TIME when editing a room
		
		PageData room = roomService.findById(pd);
		String roomName = room.getString("ROOM_NAME");
		
		if(roomName != pd.getString("ROOM_NAME")){
			pd.put("LAST_ROOM_NAME", roomName);
		}	
		
		if(!Jurisdiction.getUsername().equals(pd.getString("ROOM_NAME"))){		//如果当前登录用户修改用户资料提交的用户名非本人
			if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}  //校验权限 判断当前操作者有无用户管理查看权限
			if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限判断当前操作者有无用户管理修改权限
			//if("admin".equals(pd.getString("USERNAME")) && !"admin".equals(Jurisdiction.getUsername())){return null;}	//非admin用户不能修改admin
		}else{	//如果当前登录用户修改用户资料提交的用户名是本人，则不能修改本人的角色ID
			pd.put("CATEGORY_ID", roomService.findByRoomNumber(pd).getString("CATEGORY_ID")); //对类别ID还原本人类别ID
			//pd.put("ROLE_IDS", userService.findByUsername(pd).getString("ROLE_IDS")); //对角色ID还原本人副职角色ID
		}
		
		//NOTE: Test here
//				String a = building.getBUILDING_NAME();
//				pd.put("LAST_BUILDING_NAME", a);
		/*if( a != pd.getString("BUILDING_NAME")){
			building.setLAST_BUILDING_NAME(a);
		}*/
		roomService.editU(pd);	//执行修改
		FHLOG.save(Jurisdiction.getUsername(), "修改room："+pd.getString("ROOM_NAME"));
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * Batch deletion
	 * @throws Exception 
	 */
	@RequestMapping(value="/deleteAllU")
	@ResponseBody
	public Object deleteAllU() throws Exception {
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		logBefore(logger, Jurisdiction.getUsername()+"批量删除room");
		FHLOG.save(Jurisdiction.getUsername(), "批量删除room");
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String ROOM_IDS = pd.getString("ROOM_IDS");
		if(null != ROOM_IDS && !"".equals(ROOM_IDS)){
			String ArrayROOM_IDS[] = ROOM_IDS.split(",");
			roomService.deleteAllU(ArrayROOM_IDS);
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
	
	/**Export room information to Excel
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel() throws Exception{
		FHLOG.save(Jurisdiction.getUsername(), "导出用户信息到EXCEL");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			if(Jurisdiction.buttonJurisdiction(menuUrl, "cha")){
				String keywords = pd.getString("keywords");				//关键词检索条件
				if(null != keywords && !"".equals(keywords)){
					pd.put("keywords", keywords.trim());
				}
				
				//create a file and add data from frontend to the Excel file
				
				Map<String,Object> dataMap = new HashMap<String,Object>();
				List<String> titles = new ArrayList<String>();
				titles.add("房间具体名称"); 		//1
				//titles.add("房间具体编号");  		//
				titles.add("子类别");			//2
				titles.add("上次房间名称");			//3
				titles.add("使用单位或具体位置");			//4
				dataMap.put("titles", titles);
				List<PageData> roomList = roomService.listAllRoom(pd);
				List<PageData> varList = new ArrayList<PageData>();
				for(int i=0;i<roomList.size();i++){
					PageData vpd = new PageData();
					vpd.put("var1", roomList.get(i).getString("ROOM_NAME"));		//1
					//vpd.put("var2", roomList.get(i).getString("BUILDING_NO"));		//2
					vpd.put("var2", roomList.get(i).getString("CATEGORY_NAME"));			//3
					vpd.put("var3", roomList.get(i).getString("LAST_BUILDING_NAME"));	//4
					vpd.put("var4", roomList.get(i).getString("NOTE"));		//5
					varList.add(vpd);
				}
				dataMap.put("varList", varList);
				ObjectExcelView erv = new ObjectExcelView();					//执行excel操作
				mv = new ModelAndView(erv,dataMap);
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**Open the Upload Excel page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goUploadExcel")
	public ModelAndView goUploadExcel()throws Exception{
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("system/room/uploadexcel");
		return mv;
	}
	
	/**Download template
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/downExcel")
	public void downExcel(HttpServletResponse response)throws Exception{
		FileDownload.fileDownload(response, PathUtil.getClasspath() + Const.FILEPATHFILE + "room.xls", "Rooms.xls");
	}
	
	/**Import from EXCEL to database
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/readExcel")
	public ModelAndView readExcel(
			@RequestParam(value="excel",required=false) MultipartFile file
			) throws Exception{
		FHLOG.save(Jurisdiction.getUsername(), "从EXCEL导入到数据库");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;}
		if (null != file && !file.isEmpty()) {
			String filePath = PathUtil.getClasspath() + Const.FILEPATHFILE;								//path name
			String fileName =  FileUpload.fileUp(file, filePath, "room");							//file name
			List<PageData> listPd = (List)ObjectExcelRead.readExcel(filePath, fileName, 1, 0, 0);		//执行读EXCEL操作,读出的数据导入List 2:从第3行开始；0:从第A列开始；0:第0个sheet
			/*存入数据库操作======================================*/
			
			pd.put("LAST_ROOM_NAME", "");
			pd.put("CATEGORY_ID", "02");
				
			List<Category> categoryList = categoryService.listAllCategoriesByPId(pd);
			pd.put("CATEGORY_ID", categoryList.get(0).getCATEGORY_ID());		//设置CATEGORY_ID为随便第一个
			/**
			 * read a file and save to database
			 * var0 :房间具体名称     (column 1)
			 * var1 :房间具体编号   (column 2)
			 * var2 :使用单位或具体位置    (column 3)
			 * 
			 */
			for(int i=0;i<listPd.size();i++){		
				pd.put("ROOM_ID", this.get32UUID());        //generate random id
											
				
				String ROOM_NAME = listPd.get(i).getString("var0");
				pd.put("ROOM_NAME", ROOM_NAME);							//sets ROOM_NAME to column 1

				
				String ROOM_NO = listPd.get(i).getString("var1");	//sets ROOM_NO to column 2
				pd.put("ROOM_NO", ROOM_NO);	

				
				
//				String CATEGORY_NAME = listPd.get(i).getString("var2");	//sets CATEGORY_NAME to column 3
//				pd.put("CATEGORY_NAME", CATEGORY_NAME);
				
				
				
				String NOTE = listPd.get(i).getString("var2");	//sets NOTE to column 3
				pd.put("NOTE", NOTE);
				
				
				
				
//				pd.put("CREATED_TIME", new Date());
				
				
				
				roomService.saveU(pd);  //save it to database
			}
			/*存入数据库操作======================================*/
			mv.addObject("msg","success");
		}
		mv.setViewName("save_result");
		return mv;
	}

	
	/**显示用户列表(弹窗选择用)
	 * @param page
	 * @return
	 * @throws Exception
	 *//*
	@RequestMapping(value="/listUsersForWindow")
	public ModelAndView listUsersForWindow(Page page)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		String lastLoginStart = pd.getString("lastLoginStart");	//开始时间
		String lastLoginEnd = pd.getString("lastLoginEnd");		//结束时间
		if(lastLoginStart != null && !"".equals(lastLoginStart)){
			pd.put("lastLoginStart", lastLoginStart+" 00:00:00");
		}
		if(lastLoginEnd != null && !"".equals(lastLoginEnd)){
			pd.put("lastLoginEnd", lastLoginEnd+" 00:00:00");
		} 
		page.setPd(pd);
		List<PageData>	userList = userService.listUsersBystaff(page);	//列出用户列表(弹窗选择用)
		pd.put("ROLE_ID", "1");
		List<Role> roleList = roleService.listAllRolesByPId(pd);//列出所有系统用户角色
		mv.setViewName("system/user/window_user_list");
		mv.addObject("userList", userList);
		mv.addObject("roleList", roleList);
		mv.addObject("pd", pd);
		return mv;
	}
	*/
	
}
