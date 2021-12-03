
package com.fh.controller.system.roomCategory;

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
import com.fh.service.system.category.CategoryManager;
import com.fh.service.system.fhlog.FHlogManager;
import com.fh.service.system.menu.MenuManager;
import com.fh.service.system.role.RoleManager;
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
 * 类名称：RoomCategoryController
 * 创建人：
 * 更新时间：2021年4月29日
 * @version
 */
@Controller
@RequestMapping(value="/roomCategory")
public class RoomCategoryController extends BaseController {
	
	String menuUrl = "roomCategory/listRoomsCategory.do"; //菜单地址(权限用)
	@Resource(name="roomCategoryService")
	private RoomCategoryManager roomCategoryService;
	@Resource(name="categoryService")
	private CategoryManager categoryService;
	@Resource(name="menuService")
	private MenuManager menuService;
	@Resource(name="fhlogService")
	private FHlogManager FHLOG;
	

	
	/**Display the list of rooms category
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/listRoomsCategory")
	public ModelAndView listRoomscategory(Page page)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	roomCategoryList = roomCategoryService.listRoomCategory(page);	//列出楼宇列表/Get the list of buildings category
		pd.put("PARENT_ID", "02");
		mv.setViewName("system/roomCategory/roomCategory_list");
		mv.addObject("roomCategoryList", roomCategoryList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	/**Delete rooms category
	 * @param out
	 * @throws Exception 
	 */
	@RequestMapping(value="/deleteU")
	public void deleteU(PrintWriter out) throws Exception{
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		logBefore(logger, Jurisdiction.getUsername()+"删除user");
		PageData pd = new PageData();
		pd = this.getPageData();
		roomCategoryService.deleteU(pd);
		FHLOG.save(Jurisdiction.getUsername(), "删除系统用户："+pd);
		out.write("success");
		out.close();
	}
	
	/**Go to adding new rooms category page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goAddU")
	public ModelAndView goAddU()throws Exception{
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("system/roomCategory/roomCategory_edit");
		mv.addObject("msg", "saveU");
		mv.addObject("pd", pd);
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
	
		
		
		
		List<Category> roomCategoryList_z = roomCategoryService.listAllRoomsCategory(pd);  //loop thru the list by category id and get the maximum number of the Room category number  
		int Max = 0;
		for (int i=0; i < roomCategoryList_z.size() ;i++) {
			String CATEGORY_ORDER = roomCategoryList_z.get(i).getString("CATEGORY_ORDER");
			if(null != CATEGORY_ORDER && !"".equals(CATEGORY_ORDER)){
				int cat_order = Integer.parseInt(CATEGORY_ORDER); 
				if(cat_order>Max){
					Max=cat_order;
				}
			else{
				Max = 0;
				}
			}
		}
		//System.out.println("Max value" + Max);
		int a = ++Max;
		String b = "";
		if(a >= 10){
			b= a + "";
		}else{
			b = "0" + a;
		}
		pd.put("CATEGORY_ORDER", b);
		
		/*List<Category> roomCategoryList_z = roomCategoryService.listAllRoomsCategory(pd);  //loop thru the list by category id and add 1 then assign it to building No 
		int total = 0;
		for (int i=0; i < roomCategoryList_z.size() ;i++) {
			++total;
		}
		int a = ++total;
		String b = "";
		if(a >= 10){
			b= a + "";
		}else{
			b = "0" + a;
		}
		pd.put("CATEGORY_ORDER", b);*/
		pd.put("PARENT_ID", "02");
		
		
		pd.put("CATEGORY_ID", this.get32UUID());		
//		pd.put("CREATED_TIME", new Date());      //add current date when adding a new building
		
		
		if(roomCategoryService.findByCategory_Name(pd) == null || "".equals(roomCategoryService.findByCategory_Name(pd))){	//Determine if the building name exists
			roomCategoryService.saveU(pd); 					//执行保存
			FHLOG.save(Jurisdiction.getUsername(), "新增楼宇："+pd.getString("CATEGORY_NAME"));
			mv.addObject("msg","success");
		}else{
			mv.addObject("msg","failed");
		}
		mv.setViewName("save_result");
		return mv;
	}
	

	
	/**To determine if the room category no exists
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
			if(roomCategoryService.findByCategoryName(pd) != null){
				errInfo = "error";
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);				//返回结果
		return AppUtil.returnObject(new PageData(), map);
	}
	
	
	
	/**To modify the room category page (system room category list modification)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goEditU")
	public ModelAndView goEditU() throws Exception{
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.addObject("fx", "roomCategory");
		pd = roomCategoryService.findById(pd);								//根据ID读取
		

		mv.setViewName("system/roomCategory/roomCategory_edit");
		mv.addObject("msg", "editU");
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**To modify the room category page (individual changes)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goEditMyU")
	public ModelAndView goEditMyU() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.addObject("fx", "head");
		pd.put("CATEGORY_NAME", Jurisdiction.getUsername());
		pd = roomCategoryService.findByCategoryOrder(pd);						//根据楼宇编号读取
		mv.setViewName("system/roomCategory/roomCategory_edit");
		mv.addObject("msg", "editU");
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**View the room category
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/view")
	public ModelAndView view() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
//		String CATEGORY_NAME = pd.getString("CATEGORY_NAME");
		pd = roomCategoryService.findByCategory_Name(pd);						//根据ID读取
		mv.setViewName("system/roomCategory/roomCategory_view");
		mv.addObject("msg", "editU");
		mv.addObject("pd", pd);
		return mv;
	}
	

	
	/**
	 * Modify the room
	 */
	@RequestMapping(value="/editU")
	public ModelAndView editU() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改building");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
	
		if(!Jurisdiction.getUsername().equals(pd.getString("CATEGORY_NAME"))){		//如果当前登录用户修改用户资料提交的用户名非本人
			if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}  //校验权限 判断当前操作者有无用户管理查看权限
			if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限判断当前操作者有无用户管理修改权限
			//if("admin".equals(pd.getString("USERNAME")) && !"admin".equals(Jurisdiction.getUsername())){return null;}	//非admin用户不能修改admin
		}else{	//如果当前登录用户修改用户资料提交的用户名是本人，则不能修改本人的角色ID
			pd.put("PARENT_ID", roomCategoryService.findByCategoryOrder(pd).getString("PARENT_ID")); //对类别ID还原本人类别ID
			
		}
		
		roomCategoryService.editU(pd);					
		FHLOG.save(Jurisdiction.getUsername(), "修改roomCategory："+pd.getString("CATEGORY_NAME"));
		mv.addObject("msg","success");
		/*if(roomCategoryService.findByCategory_Name(pd) == null || "".equals(roomCategoryService.findByCategory_Name(pd))){	//Determine if the building name exists
			roomCategoryService.editU(pd);					
			FHLOG.save(Jurisdiction.getUsername(), "修改roomCategory："+pd.getString("CATEGORY_NAME"));
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除roomCategory");
		FHLOG.save(Jurisdiction.getUsername(), "批量删除roomCategory");
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String CATEGORY_IDS = pd.getString("CATEGORY_IDS");
		if(null != CATEGORY_IDS && !"".equals(CATEGORY_IDS)){
			String ArrayCATEGORY_IDS[] = CATEGORY_IDS.split(",");
			roomCategoryService.deleteAllU(ArrayCATEGORY_IDS);
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
	
	/**Export room category information to Excel
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
				titles.add("房间类别名称"); 		//1
				titles.add("房间类别编号");  		//2
		
				dataMap.put("titles", titles);
				List<PageData> roomCatList = roomCategoryService.listAllRoomCategory(pd);
				List<PageData> varList = new ArrayList<PageData>();
				for(int i=0;i<roomCatList.size();i++){
					PageData vpd = new PageData();
					vpd.put("var1", roomCatList.get(i).getString("CATEGORY_NAME"));		//1
					vpd.put("var2", roomCatList.get(i).getString("CATEGORY_ORDER"));		//2
					
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
		mv.setViewName("system/roomCategory/uploadexcel");
		return mv;
	}
	
	/**Download template
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/downExcel")
	public void downExcel(HttpServletResponse response)throws Exception{
		FileDownload.fileDownload(response, PathUtil.getClasspath() + Const.FILEPATHFILE + "roomCategory.xls", "RoomsCategory.xls");
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
			String fileName =  FileUpload.fileUp(file, filePath, "roomCategory");							//file name
			List<PageData> listPd = (List)ObjectExcelRead.readExcel(filePath, fileName, 1, 0, 0);		//执行读EXCEL操作,读出的数据导入List 2:从第3行开始；0:从第A列开始；0:第0个sheet
			/*存入数据库操作======================================*/
			
			/**
			 * read a file and save to database
			 * var0 :房间类别名称     (column 1)
			 * 
			 */
			for(int i=0;i<listPd.size();i++){		
				pd.put("CATEGORY_ID", this.get32UUID());        //generate random id
				
				
				List<Category> roomCategoryList_z = roomCategoryService.listAllRoomsCategory(pd);  //loop thru the list and get the maximum number of the Room category number  
				int Max = 0;
				for (int j=0; j < roomCategoryList_z.size() ;j++) {
					String CATEGORY_ORDER = roomCategoryList_z.get(j).getString("CATEGORY_ORDER");
					if(null != CATEGORY_ORDER && !"".equals(CATEGORY_ORDER)){
						int cat_order = Integer.parseInt(CATEGORY_ORDER); 
						if(cat_order>Max){
							Max=cat_order;
						}
					else{
						Max = 0;
						}
					}
				}
				//System.out.println("Max value" + Max);
				int a = ++Max;
				String b = "";
				if(a >= 10){
					b= a + "";
				}else{
					b = "0" + a;
				}
				pd.put("CATEGORY_ORDER", b);
											
				pd.put("PARENT_ID", "02");
				
				
				String CATEGORY_NAME = listPd.get(i).getString("var0");
				pd.put("CATEGORY_NAME", CATEGORY_NAME);							//sets BUILDING_NAME to column 1


				if(roomCategoryService.findByCategory_Name(pd) == null || "".equals(roomCategoryService.findByCategory_Name(pd))){	//Determine if the building name exists
					
					roomCategoryService.saveU(pd);  //save it to database 				
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
