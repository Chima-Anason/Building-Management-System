
package com.fh.controller.system.location;

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
import com.fh.entity.system.Headman;
import com.fh.entity.system.Location;
import com.fh.entity.system.Role;
import com.fh.entity.system.Room;
import com.fh.service.system.building.BuildingManager;
import com.fh.service.system.fhlog.FHlogManager;
import com.fh.service.system.headman.HeadmanManager;
import com.fh.service.system.location.LocationManager;
import com.fh.service.system.menu.MenuManager;
import com.fh.service.system.role.RoleManager;
import com.fh.service.system.room.RoomManager;
import com.fh.service.system.roomCategory.RoomCategoryManager;
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
 * 类名称：LocationController
 * 创建人：
 * 更新时间：2021年4月29日
 * @version
 */
@Controller
@RequestMapping(value="/location")
public class LocationController extends BaseController {
	
	String menuUrl = "location/listLocations.do"; //菜单地址(权限用)
	@Resource(name="locationService")
	private LocationManager locationService;
	@Resource(name="buildingService")
	private BuildingManager buildingService;
	@Resource(name="headmanService")
	private HeadmanManager headmanService;
	@Resource(name="roomCategoryService")
	private RoomCategoryManager roomCategoryService;
	@Resource(name="menuService")
	private MenuManager menuService;
	@Resource(name="fhlogService")
	private FHlogManager FHLOG;
	
	
	
	/**Display the list of locations
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/listLocations")
	public ModelAndView listLocations(Page page)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	locationList = locationService.listLocations(page);	//列出楼宇列表/Make a list of locations
		//pd.put("CATEGORY_ID", "01");
		//List<Category> categoryList = categoryService.listAllCategoriesByPId(pd);//列出所有系统楼宇类别/List all building category
		mv.setViewName("system/location/location_list");
		mv.addObject("locationList", locationList);
		//mv.addObject("categoryList", categoryList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	
	
	
	/**Delete location
	 * @param out
	 * @throws Exception 
	 */
	@RequestMapping(value="/deleteU")
	public void deleteU(PrintWriter out) throws Exception{
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		logBefore(logger, Jurisdiction.getUsername()+"删除location");
		PageData pd = new PageData();
		pd = this.getPageData();
		locationService.deleteU(pd);
		FHLOG.save(Jurisdiction.getUsername(), "删除location："+pd);
		out.write("success");
		out.close();
	}
	
	/**Go to adding new location page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goAddU")
	public ModelAndView goAddU()throws Exception{
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//pd.put("CATEGORY_ID", "01");
		//List<Category> categoryList = categoryService.listAllCategoriesByPId(pd);//列出所有楼宇类别/List all building and room category
		List<Building> buildingList = buildingService.listAllBuildings(pd);//列出所有楼宇类别/List all building 
		List<Category> roomCategoryList = roomCategoryService.listAllRoomsCategory(pd);//列出所有楼宇类别/List all room
		List<Headman> headmanList = headmanService.listAllHeadmans(pd);//列出所有楼宇类别/List all headman 
		mv.setViewName("system/location/location_edit");
		mv.addObject("msg", "saveU");
		mv.addObject("pd", pd);
		//mv.addObject("categoryList", categoryList);
		mv.addObject("buildingList", buildingList);
		mv.addObject("roomCategoryList", roomCategoryList);
		mv.addObject("headmanList", headmanList);
		return mv;
	}
	
	/**Save location
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/saveU")
	public ModelAndView saveU() throws Exception{
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		logBefore(logger, Jurisdiction.getUsername()+"新增building");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		
		pd.put("LOCATION_ID", this.get32UUID());	
		pd.put("LAST_LOCATION_NAME", "");	
		pd.put("CREATED_TIME", new Date());      //add current date when adding a new location
		
		List<Location> locationList_s = locationService.listAllLocations(pd);  //loop thru the list of location and get the maximum number of the location number  
		int Max = 0;
		for (int i=0; i < locationList_s.size() ;i++) {
			String LOCATION_ORDER = locationList_s.get(i).getString("LOCATION_ORDER");
			if(null != LOCATION_ORDER && !"".equals(LOCATION_ORDER)){
				int location_order = Integer.parseInt(LOCATION_ORDER); 
				if(location_order>Max){
					Max=location_order;
				}
			else{
				Max = 0;
				}
			}
		}
		//System.out.println("Max value:" + Max);
		int a = ++Max;
		String b = "";
		if(a >= 10){
			b= a + "";
		}else{
			b = "0" + a;
		}
		pd.put("LOCATION_ORDER", b);
		
		
		headmanService.findById(pd);
		String headmanNo = pd.getString("HEADMAN_NO");
		pd.put("HEADMAN_NO", headmanNo); 
		

		
		buildingService.findById(pd);
		String buildingName = pd.getString("BUILDING_NAME");
		pd.put("BUILDING_NAME", buildingName); 
		
		roomCategoryService.findById(pd);
		String roomCatName = pd.getString("CATEGORY_NAME");
		pd.put("CATEGORY_NAME", roomCatName);

		
		PageData p = buildingService.findByIdForLocation(pd);
		String buildingNo = p.getString("BUILDING_NO");
		String categoryOrder = p.getString("CATEGORY_ORDER");
		String building = "01"+categoryOrder+buildingNo;
		
		
	/*	PageData r = roomCategoryService.findById(pd);
		String roomCategoryOrder = r.getString("CATEGORY_ORDER");
		String room = "02"+roomCategoryOrder;*/

		PageData r = roomCategoryService.findById(pd);
		String room = "";
		if(r!=null){
			String roomCategoryOrder = r.getString("CATEGORY_ORDER");
			room = "02"+roomCategoryOrder;
		}else{
			room = "0000";
		}
		
		
		List<Location> locationList_z = locationService.listAllLocations(pd);  //loop thru the list by category id and add 1 then assign it to building No 
		int total = 00000;
		for (int i=0; i < locationList_z.size() ;i++) {
			++total;
		}
		int c = ++total;
		String d = "";
		if(c >= 10000){
			d= c + "";
		}else if(c>=1000){
			d = "0" + c;
		}else if(c>=100){
			d = "00" + c;
		}else if(c>=10){
			d = "000" + c;
		}else {
			d = "0000" + c;
		}
		pd.put("LOCATION_NO", building+room+d);
			

		
		if(locationService.findByLocation_Name(pd) == null || "".equals(locationService.findByLocation_Name(pd) )){	//Determine if the building name exists
	
				locationService.saveU(pd); 					//执行保存
				FHLOG.save(Jurisdiction.getUsername(), "新增楼宇："+pd.getString("LOCATION_NAME"));
				mv.addObject("msg","success");
			
		}else{
			mv.addObject("msg","failed");
		}
		
		
		mv.setViewName("save_result");
		return mv;
	}
	

	
	/**To determine if the location no exists
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
			if(locationService.findByLocationName(pd)!= null){
				errInfo = "error";
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);				//返回结果
		return AppUtil.returnObject(new PageData(), map);
	}
	
	
	
	/**To modify the location page (system location list modification)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goEditU")
	public ModelAndView goEditU() throws Exception{
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
//		List<Category> roomategoryList = categoryService.listAllCategoriesByPId(pd);//列出所有楼宇类别/List all building and room category
		List<Building> buildingList = buildingService.listAllBuildings(pd);//列出所有楼宇类别/List all building 
		List<Category> roomCategoryList = roomCategoryService.listAllRoomsCategory(pd);//列出所有楼宇类别/List all room
		List<Headman> headmanList = headmanService.listAllHeadmans(pd);//列出所有楼宇类别/List all headman
		mv.addObject("fx", "location");
		pd = locationService.findById(pd);								//根据ID读取

		mv.setViewName("system/location/location_edit");
		mv.addObject("msg", "editU");
		mv.addObject("pd", pd);
//		mv.addObject("categoryList", categoryList);
		mv.addObject("buildingList", buildingList);
		mv.addObject("roomCategoryList", roomCategoryList);
		mv.addObject("headmanList", headmanList);
		return mv;
	}
	
	/**To modify the building page (individual changes)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goEditMyU")
	public ModelAndView goEditMyU() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.addObject("fx", "head");
		//pd.put("CATEGORY_ID", "01");
//		List<Category> categoryList = categoryService.listAllCategoriesByPId(pd);//列出所有楼宇类别/List all building and room category
		List<Building> buildingList = buildingService.listAllBuildings(pd);//列出所有楼宇类别/List all building 
		List<Category> roomList = roomCategoryService.listAllRoomsCategory(pd);//列出所有楼宇类别/List all room
		List<Headman> headmanList = headmanService.listAllHeadmans(pd);//列出所有楼宇类别/List all headman
		pd.put("LOCATION_NAME", Jurisdiction.getUsername());
		pd = locationService.findById(pd);						//根据楼宇编号读取
		mv.setViewName("system/location/location_edit");
		mv.addObject("msg", "editU");
		mv.addObject("pd", pd);
//		mv.addObject("categoryList", categoryList);
		mv.addObject("buildingList", buildingList);
		mv.addObject("roomList", roomList);
		mv.addObject("headmanList", headmanList);
		return mv;
	}
	
	/**View the building
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/view")
	public ModelAndView view() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String BUILDING_NAME = pd.getString("BUILDING_NAME");
		
		//List<Category> categoryList = categoryService.listAllCategoriesByPId(pd);//列出所有楼宇类别/List all building and room category
		List<Building> buildingList = buildingService.listAllBuildings(pd);//列出所有楼宇类别/List all building 
		List<Category> roomCatList = roomCategoryService.listAllRoomsCategory(pd);//列出所有楼宇类别/List all room
		List<Headman> headmanList = headmanService.listAllHeadmans(pd);//列出所有楼宇类别/List all headman
		
		pd = locationService.findByLocation_Name(pd);						//根据ID读取
	
		/*if(null == pd){
			PageData rpd = new PageData();
			rpd.put("BUILDING_NAME", BUILDING_NAME);							//楼宇名查不到数据时就把数据当作类别的编码去查询类别表(工作流的待办人物，查看代办人资料时用到)
			rpd = buildingService.findByBuilding_Name(rpd);
			mv.addObject("rpd", rpd);
		}*/
		mv.setViewName("system/location/location_view");
		mv.addObject("msg", "editU");
		mv.addObject("pd", pd);
		//mv.addObject("categoryList", categoryList);
		mv.addObject("buildingList", buildingList);
		mv.addObject("roomCatList", roomCatList);
		mv.addObject("headmanList", headmanList);
		return mv;
	}
	

	/**
	 * Modify the building
	 */
	@RequestMapping(value="/editU")
	public ModelAndView editU() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改location");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("EDITED_TIME", new Date());    //add current date to EDITED_TIME when editing a building
		
		
		PageData a = locationService.findLocation_NameById(pd);
		String oldLocationName = a.getString("LOCATION_NAME");
			pd.put("LAST_LOCATION_NAME", oldLocationName);

//			headmanService.findById(pd);
//			String headmanName = pd.getString("HEADMAN_NAME");
//			pd.put("HEADMAN_NAME", headmanName); 
//			
//
//			
//			buildingService.findById(pd);
//			String buildingName = pd.getString("BUILDING_NAME");
//			pd.put("BUILDING_NAME", buildingName); 
//			
//			roomCategoryService.findById(pd);
//			String roomCatName = pd.getString("CATEGORY_NAME");
//			pd.put("CATEGORY_NAME", roomCatName);
	
		if(!Jurisdiction.getUsername().equals(pd.getString("LOCATION_NAME"))){		//如果当前登录用户修改用户资料提交的用户名非本人
    		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}  //校验权限 判断当前操作者有无用户管理查看权限
			if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限判断当前操作者有无用户管理修改权限
			if("admin".equals(pd.getString("USERNAME")) && !"admin".equals(Jurisdiction.getUsername())){return null;}	//非admin用户不能修改admin
		}else{	//如果当前登录用户修改用户资料提交的用户名是本人，则不能修改本人的角色ID
			pd.put("BUILDING_ID", locationService.findByLocation_Name(pd).getString("BUILDING_ID")); //对类别ID还原本人类别ID
			pd.put("CATEGORY_ID", locationService.findByLocation_Name(pd).getString("CATEGORY_ID")); //对类别ID还原本人类别ID
			pd.put("HEADMAN_ID", locationService.findByLocation_Name(pd).getString("BUILDING_ID")); //对类别ID还原本人类别ID
			
		}
		
		
		locationService.editU(pd);	//执行修改
		FHLOG.save(Jurisdiction.getUsername(), "修改location："+pd.getString("LOCATION_NAME"));
		mv.addObject("msg","success");
		/*if(locationService.findByLocation_Name(pd) == null || "".equals(locationService.findByLocation_Name(pd) )){	//Determine if the building name exists
			locationService.editU(pd);	//执行修改
			FHLOG.save(Jurisdiction.getUsername(), "修改location："+pd.getString("LOCATION_NAME"));
			mv.addObject("msg","success");
		
		}else{
			mv.addObject("msg","failed");
		}*/
		
		
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除location");
		FHLOG.save(Jurisdiction.getUsername(), "批量删除location");
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String LOCATION_IDS = pd.getString("LOCATION_IDS");
		if(null != LOCATION_IDS && !"".equals(LOCATION_IDS)){
			String ArrayLOCATION_IDS[] = LOCATION_IDS.split(",");
			locationService.deleteAllU(ArrayLOCATION_IDS);
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
	
	/**Export location information to Excel
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
				titles.add("具体地点名称");			//1
				titles.add("地点编号");			//2
				titles.add("楼宇名称"); 		//3
				titles.add("房间名称");  		//4
				titles.add("用途");  		//5
				titles.add("上次修改的地点名称");			//6
				titles.add("负责工号");			//7
				dataMap.put("titles", titles);
				List<PageData> locationList = locationService.listAllLocation(pd);
				List<PageData> varList = new ArrayList<PageData>();
				for(int i=0;i<locationList.size();i++){
					PageData vpd = new PageData();
					vpd.put("var1", locationList.get(i).getString("LOCATION_NAME"));			//1
					vpd.put("var2", locationList.get(i).getString("LOCATION_ORDER"));			//2
					vpd.put("var3", locationList.get(i).getString("BUILDING_NAME"));		//3
					vpd.put("var4", locationList.get(i).getString("ROOM_NAME"));		//4
					vpd.put("var5", locationList.get(i).getString("CATEGORY_NAME"));		//5
					vpd.put("var6", locationList.get(i).getString("LAST_LOCATION_NAME"));	//6
					vpd.put("var7", locationList.get(i).getString("HEADMAN_NO"));		//7
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
		mv.setViewName("system/location/uploadexcel");
		return mv;
	}
	
	/**Download template
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/downExcel")
	public void downExcel(HttpServletResponse response)throws Exception{
		FileDownload.fileDownload(response, PathUtil.getClasspath() + Const.FILEPATHFILE + "location.xls", "Locations.xls");
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
			String fileName =  FileUpload.fileUp(file, filePath, "location");							//file name
			List<PageData> listPd = (List)ObjectExcelRead.readExcel(filePath, fileName, 1, 0, 0);		//执行读EXCEL操作,读出的数据导入List 2:从第3行开始；0:从第A列开始；0:第0个sheet
			/*存入数据库操作======================================*/
			
			
			
				
			//List<Category> categoryList = categoryService.listAllCategoriesByPId(pd);//列出所有楼宇类别/List all building and room category
			
/*			List<Building> buildingList = buildingService.listAllBuildings(pd);///List all building 
			pd.put("BUILDING_ID", buildingList.get(0).getBUILDING_ID());		
			List<Category> roomList = roomCategoryService.listAllRoomsCategory(pd);///List all room
			pd.put("CATEGORY_ID", roomList.get(0).getCATEGORY_ID());		
			List<Headman> headmanList = headmanService.listAllHeadmans(pd);//List all headman
			pd.put("HEADMAN_ID", headmanList.get(0).getHEADMAN_ID());	*/	
			
			/**
			 * read a file and save to database
			 * var0 :具体地点名称     (column 1)
			 * var1 :楼宇名称   (column 2)
			 * var2 :房间名称    (column 3)
			 * var3 :用途    (column 4)
			 * var4 :负责工号    (column 5)
			 */
			for(int i=0;i<listPd.size();i++){		
				pd.put("LOCATION_ID", this.get32UUID());        //generate random id
				
				
				List<Location> locationList_s = locationService.listAllLocations(pd);  //loop thru the list of location and get the maximum number of the location number  
				int Max = 0;
				for (int k=0; k < locationList_s.size() ;k++) {
					String LOCATION_ORDER = locationList_s.get(k).getString("LOCATION_ORDER");
					if(null != LOCATION_ORDER && !"".equals(LOCATION_ORDER)){
						int location_order = Integer.parseInt(LOCATION_ORDER); 
						if(location_order>Max){
							Max=location_order;
						}
					else{
						Max = 0;
						}
					}
				}
				//System.out.println("Max value:" + Max);
				int a = ++Max;
				String b = "";
				if(a >= 10){
					b= a + "";
				}else{
					b = "0" + a;
				}
				pd.put("LOCATION_ORDER", b);
				
				pd.put("LAST_LOCATION_NAME", "");
				
											
				
				String LOCATION_NAME = listPd.get(i).getString("var0");
				pd.put("LOCATION_NAME", LOCATION_NAME);							//sets LOCATION_NAME to column 1

				
				
				String BUILDING_NAME = listPd.get(i).getString("var1");//sets BUILDING_NAME to column 2
				String building_id = buildingService.findByBuilding_NameForExcel(BUILDING_NAME);
				pd.put("BUILDING_ID", building_id);
					
				
				String ROOM_NAME = listPd.get(i).getString("var2");	//sets ROOM_NAME to column 3
				pd.put("ROOM_NAME", ROOM_NAME);
				
				
				String ROOM_CATEGORY_NAME = listPd.get(i).getString("var3");//sets ROOM_CATEGORY_NAME to column 4
				String category_id = roomCategoryService.findByCategory_NameForExcel(ROOM_CATEGORY_NAME);
				pd.put("CATEGORY_ID", category_id);
				
				
				String HEADMAN_NO = listPd.get(i).getString("var4");	//sets HEADMAN_NAME to column 3
				pd.put("HEADMAN_NO", HEADMAN_NO);
				
				
				pd.put("CREATED_TIME", new Date());
				
				
				
				
				
				PageData p = buildingService.findByIdForLocation(pd);
				String buildingNo = p.getString("BUILDING_NO");
				String categoryOrder = p.getString("CATEGORY_ORDER");
				String building = "01"+categoryOrder+buildingNo;
				
				
				PageData r = roomCategoryService.findById(pd);
				String roomCategoryOrder = r.getString("CATEGORY_ORDER");
				String room = "02"+roomCategoryOrder;
				
				
				List<Location> locationList_z = locationService.listAllLocations(pd);  //loop thru the list by category id and add 1 then assign it to building No 
				int total = 00000;
				for (int j=0; j < locationList_z.size() ;j++) {
					++total;
				}
				int c = ++total;
				String d = "";
				if(c >= 10000){
					d= c + "";
				}else if(c>=1000){
					d = "0" + c;
				}else if(c>=100){
					d = "00" + c;
				}else if(c>=10){
					d = "000" + c;
				}else {
					d = "0000" + c;
				}
				pd.put("LOCATION_NO", building+room+d);
				
				
				
				
				if(locationService.findByLocation_Name(pd) == null || "".equals(locationService.findByLocation_Name(pd) )){	//Determine if the building name exists
					
					locationService.saveU(pd); 				
					mv.addObject("msg","success");
				
				}else{
					mv.addObject("msg","failed");
				}
			
			}

		}
		mv.setViewName("save_result");
		return mv;
	}

	
	
	
}
