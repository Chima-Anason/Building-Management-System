
package com.fh.controller.system.building;

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
import com.fh.service.system.building.BuildingManager;
import com.fh.service.system.buildingCategory.BuildingCategoryManager;
import com.fh.service.system.category.CategoryManager;
import com.fh.service.system.fhlog.FHlogManager;
import com.fh.service.system.menu.MenuManager;
import com.fh.service.system.role.RoleManager;
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
 * 类名称：BuildingController
 * 创建人：
 * 更新时间：2021年4月29日
 * @version
 */
@Controller
@RequestMapping(value="/building")
public class BuildingController extends BaseController {
	
	String menuUrl = "building/listBuildings.do"; //菜单地址(权限用)
	@Resource(name="buildingService")
	private BuildingManager buildingService;
	@Resource(name="buildingCategoryService")
	private BuildingCategoryManager buildingCategoryService;
	@Resource(name="menuService")
	private MenuManager menuService;
	@Resource(name="fhlogService")
	private FHlogManager FHLOG;
	

	
	/**Display the list of buildings
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/listBuildings")
	public ModelAndView listBuildings(Page page)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	buildingList = buildingService.listBuildings(page);	//列出楼宇列表/Make a list of buildings
//		pd.put("CATEGORY_ID", "01");
		List<Category> categoryList = buildingCategoryService.listAllBuildingsCategory(pd);//列出所有系统楼宇类别/List all building category
		mv.setViewName("system/building/building_list");
		mv.addObject("buildingList", buildingList);
		mv.addObject("categoryList", categoryList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	/**Delete building
	 * @param out
	 * @throws Exception 
	 */
	@RequestMapping(value="/deleteU")
	public void deleteU(PrintWriter out) throws Exception{
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		logBefore(logger, Jurisdiction.getUsername()+"删除user");
		PageData pd = new PageData();
		pd = this.getPageData();
		buildingService.deleteU(pd);
		FHLOG.save(Jurisdiction.getUsername(), "删除系统用户："+pd);
		out.write("success");
		out.close();
	}
	
	/**Go to adding new building page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goAddU")
	public ModelAndView goAddU()throws Exception{
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
//		pd.put("CATEGORY_ID", "01");
		List<Category> categoryList = buildingCategoryService.listAllBuildingsCategory(pd);//列出所有楼宇类别/List all building category
		mv.setViewName("system/building/building_edit");
		mv.addObject("msg", "saveU");
		mv.addObject("pd", pd);
		mv.addObject("categoryList", categoryList);
		return mv;
	}
	
	/**Save building
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
		//String p = pd.getString("CATEGORY_ID");		//父类别 id/ Parent category Id
		//pd.put("BUILDING_ID", p);
		
		
		List<Building> buildingList_z = buildingService.listBuildingsByCId(pd);  //loop thru the list by category id and get the maximum number of the Room category number  
		int Max = 0;
		for (int i=0; i < buildingList_z.size() ;i++) {
			String BUILDING_NO = buildingList_z.get(i).getString("BUILDING_NO");
			if(null != BUILDING_NO && !"".equals(BUILDING_NO)){
				int building_no = Integer.parseInt(BUILDING_NO); 
				if(building_no>Max){
					Max=building_no;
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
		pd.put("BUILDING_NO", b);
		/*List<Building> buildingList_z = buildingService.listBuildingsByCId(pd);  //loop thru the list by category id and add 1 then assign it to building No 
		int total = 0;
		for (int i=0; i < buildingList_z.size() ;i++) {
			++total;
		}
		int a = ++total;
		String b = "";
		if(a >= 10){
			b= a + "";
		}else{
			b = "0" + a;
		}
		pd.put("BUILDING_NO", b);*/
		
		
		pd.put("BUILDING_ID", this.get32UUID());	
		pd.put("LAST_BUILDING_NAME", "");	
		pd.put("CREATED_TIME", new Date());      //add current date when adding a new building
		
		
		if(buildingService.findByBuilding_Name(pd) == null || "".equals(buildingService.findByBuilding_Name(pd))){	//Determine if the building name exists
			buildingService.saveU(pd); 					//执行保存
			FHLOG.save(Jurisdiction.getUsername(), "新增楼宇："+pd.getString("BUILDING_NAME"));
			mv.addObject("msg","success");
		}else{
			mv.addObject("msg","failed");
		}
		mv.setViewName("save_result");
		return mv;
	}
	

	
	/**To determine if the building no exists
	 * @return
	 */
	@RequestMapping(value="/hasN")
	@ResponseBody
	public Object hasN(){
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(buildingService.findByBuildingName(pd) != null){
				errInfo = "error";
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);				//返回结果
		return AppUtil.returnObject(new PageData(), map);
	}
	
	
	
	/**To modify the building page (system building list modification)
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
//		pd.put("CATEGORY_ID", "01");
		List<Category> categoryList = buildingCategoryService.listAllBuildingsCategory(pd);	//列出所有楼宇类别/List all building category
		mv.addObject("fx", "building");
		pd = buildingService.findById(pd);								//根据ID读取
		

		mv.setViewName("system/building/building_edit");
		mv.addObject("msg", "editU");
		mv.addObject("pd", pd);
		mv.addObject("categoryList", categoryList);
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
//		pd.put("CATEGORY_ID", "01");
		List<Category> categoryList = buildingCategoryService.listAllBuildingsCategory(pd);	//列出所有楼宇类别/List all building category
		pd.put("BUILDING_NAME", Jurisdiction.getUsername());
		pd = buildingService.findByBuildingNumber(pd);						//根据楼宇编号读取
		mv.setViewName("system/building/building_edit");
		mv.addObject("msg", "editU");
		mv.addObject("pd", pd);
		mv.addObject("categoryList", categoryList);
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
		//if("admin".equals(USERNAME)){return null;}	//不能查看admin用户
//		pd.put("CATEGORY_ID", "01");
		List<Category> categoryList = buildingCategoryService.listAllBuildingsCategory(pd);	//列出所有楼宇类别/List all building category
		pd = buildingService.findByBuilding_Name(pd);						//根据ID读取
		/*if(null == pd){
			PageData rpd = new PageData();
			rpd.put("CATEGORY_ORDER", BUILDING_NAME);							//楼宇名查不到数据时就把数据当作类别的编码去查询类别表(工作流的待办人物，查看代办人资料时用到)
			rpd = categoryService.getCategoryByCategory_order(rpd);
			mv.addObject("rpd", rpd);
		}*/
		mv.setViewName("system/building/building_view");
		mv.addObject("msg", "editU");
		mv.addObject("pd", pd);
		mv.addObject("categoryList", categoryList);
		return mv;
	}
	

	
	/**
	 * Modify the building
	 */
	@RequestMapping(value="/editU")
	public ModelAndView editU() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改building");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("EDITED_TIME", new Date());    //add current date to EDITED_TIME when editing a building
		
		PageData building = buildingService.findById(pd);
		String buildingName = building.getString("BUILDING_NAME");
		pd.put("LAST_BUILDING_NAME", buildingName);
		
			
		
		if(!Jurisdiction.getUsername().equals(pd.getString("BUILDING_NAME"))){		//如果当前登录用户修改用户资料提交的用户名非本人
			if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}  //校验权限 判断当前操作者有无用户管理查看权限
			if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限判断当前操作者有无用户管理修改权限
			//if("admin".equals(pd.getString("USERNAME")) && !"admin".equals(Jurisdiction.getUsername())){return null;}	//非admin用户不能修改admin
		}else{	//如果当前登录用户修改用户资料提交的用户名是本人，则不能修改本人的角色ID
			pd.put("CATEGORY_ID", buildingService.findByBuildingNumber(pd).getString("CATEGORY_ID")); //对类别ID还原本人类别ID
			//pd.put("ROLE_IDS", userService.findByUsername(pd).getString("ROLE_IDS")); //对角色ID还原本人副职角色ID
		}
		
		buildingService.editU(pd);	//执行修改				//执行保存
		FHLOG.save(Jurisdiction.getUsername(), "修改building："+pd.getString("BUILDING_NAME"));
		mv.addObject("msg","success");
		/*if(buildingService.findByBuilding_Name(pd) == null || "".equals(buildingService.findByBuilding_Name(pd))){	//Determine if the building name exists
			buildingService.editU(pd);	//执行修改				//执行保存
			FHLOG.save(Jurisdiction.getUsername(), "修改building："+pd.getString("BUILDING_NAME"));
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除building");
		FHLOG.save(Jurisdiction.getUsername(), "批量删除building");
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String BUILDING_IDS = pd.getString("BUILDING_IDS");
		if(null != BUILDING_IDS && !"".equals(BUILDING_IDS)){
			String ArrayBUILDING_IDS[] = BUILDING_IDS.split(",");
			buildingService.deleteAllU(ArrayBUILDING_IDS);
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
	
	/**Export building information to Excel
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
				titles.add("楼宇具体名称"); 		//1
				titles.add("楼宇具体编号");  		//2
				titles.add("子类别");			//3
				titles.add("上次楼宇名称");			//4
				titles.add("使用单位或具体位置");			//5
				dataMap.put("titles", titles);
				List<PageData> buildingList = buildingService.listAllBuilding(pd);
				List<PageData> varList = new ArrayList<PageData>();
				for(int i=0;i<buildingList.size();i++){
					PageData vpd = new PageData();
					vpd.put("var1", buildingList.get(i).getString("BUILDING_NAME"));		//1
					vpd.put("var2", buildingList.get(i).getString("BUILDING_NO"));		//2
					vpd.put("var3", buildingList.get(i).getString("CATEGORY_NAME"));			//3
					vpd.put("var4", buildingList.get(i).getString("LAST_BUILDING_NAME"));	//4
					vpd.put("var5", buildingList.get(i).getString("NOTE"));		//5
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
		mv.setViewName("system/building/uploadexcel");
		return mv;
	}
	
	/**Download template
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/downExcel")
	public void downExcel(HttpServletResponse response)throws Exception{
		FileDownload.fileDownload(response, PathUtil.getClasspath() + Const.FILEPATHFILE + "building.xls", "Buildings.xls");
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
			String fileName =  FileUpload.fileUp(file, filePath, "building");							//file name
			List<PageData> listPd = (List)ObjectExcelRead.readExcel(filePath, fileName, 1, 0, 0);		//执行读EXCEL操作,读出的数据导入List 2:从第3行开始；0:从第A列开始；0:第0个sheet
			/*存入数据库操作======================================*/
			
			
//			pd.put("CATEGORY_ID", "01");
				
			/*List<Category> categoryList = buildingCategoryService.listAllBuildingsCategory(pd);
			pd.put("CATEGORY_ID", categoryList.get(0).getCATEGORY_ID());	*/	//设置CATEGORY_ID为随便第一个
			/**
			 * read a file and save to database
			 * var0 :楼宇具体名称     (column 1)
			 * var1 :使用单位或具体位置    (column 2)
			 * 
			 */
			for(int i=0;i<listPd.size();i++){		
				pd.put("BUILDING_ID", this.get32UUID());        //generate random id
				
				pd.put("LAST_BUILDING_NAME", "");
				pd.put("CREATED_TIME", new Date());      //add current date when adding a new building
											
				
				String BUILDING_NAME = listPd.get(i).getString("var0");
				pd.put("BUILDING_NAME", BUILDING_NAME);							//sets BUILDING_NAME to column 1

				
			
				String NOTE = listPd.get(i).getString("var1");	//sets NOTE to column 3
				pd.put("NOTE", NOTE);
				
				
				String CATEGORY_NAME = listPd.get(i).getString("var2");
				String category_id = buildingCategoryService.findByCategory_NameForExcel(CATEGORY_NAME);
				pd.put("CATEGORY_ID", category_id);
				
				
				List<Building> buildingList_z = buildingService.listBuildingsByCId(pd);  //loop thru the list by category id and get the maximum number of the Room category number  
				int Max = 0;
				for (int j=0; j < buildingList_z.size() ;j++) {
					String BUILDING_NO = buildingList_z.get(j).getString("BUILDING_NO");
					if(null != BUILDING_NO && !"".equals(BUILDING_NO)){
						int building_no = Integer.parseInt(BUILDING_NO); 
						if(building_no>Max){
							Max=building_no;
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
				pd.put("BUILDING_NO", b);
				
				

				buildingService.saveU(pd);  //save it to database
			}
			/*存入数据库操作======================================*/
			mv.addObject("msg","success");
		}
		mv.setViewName("save_result");
		return mv;
	}

	
	
	
}
