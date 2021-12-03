package com.fh.controller.system.position;

import java.io.PrintWriter;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.Position;
import com.fh.service.system.fhlog.FHlogManager;
import com.fh.service.system.position.PositionManager;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.DateUtil;
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
 * 类名称：RoleController 角色权限管理
 * 创建人：FH Q313596790
 * 修改时间：2018年5月6日
 * @version
 */
@Controller
@RequestMapping(value="/position")
public class PositionController extends BaseController {
	
	String menuUrl = "position/list.do"; //菜单地址(权限用)
	@Resource(name="positionService")
	private PositionManager positionService;
	@Resource(name="fhlogService")
	private FHlogManager FHLOG;
	
	
	
	/**Save
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增position");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("POSITION_ID", this.get32UUID());  //generate Position_ID
		pd.put("CREATED_TIME", new Date());      //add new date when adding a new position
		positionService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * Delete
	 * @param POSITION_ID
	 * @param
	 * @throws Exception 
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public Object delete(@RequestParam String POSITION_ID) throws Exception{
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		logBefore(logger, Jurisdiction.getUsername()+"删除position");
		Map<String,String> map = new HashMap<String,String>();
		PageData pd = new PageData();
		pd.put("POSITION_ID", POSITION_ID);
		String errInfo = "success";
		//if(positionService.listSubPositionByParentId(POSITION_ID).size() > 0){//判断是否有子级，是：不允许删除
			//errInfo = "false";
		//}else{
			positionService.delete(pd);	//执行删除
		//}
		map.put("result", errInfo);
		return AppUtil.returnObject(new PageData(), map);
	}
	
	
	
	/**List all the Position table (1st task)
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表position");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");					//search keyword
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		String POSITION_ID = pd.getString("POSITION_ID");
		/*String POSITION_ID = null == pd.get("POSITION_ID")?"":pd.get("POSITION_ID").toString();
		if(null != pd.get("id") && !"".equals(pd.get("id").toString())){
			POSITION_ID = pd.get("id").toString();
		}*/
		//pd.put("POSITION_ID", POSITION_ID);					//上级ID
		page.setPd(pd);
		List<PageData>	varList = positionService.list(page);	//make a list of Position
		List<PageData> positionList = positionService.listAllPositions(pd);
		//pd.put("POSITION_ID", "position.getPOSITION_ID()");
		//mv.addObject("pd", positionService.findById(pd));  //传入上级所有信息
		//pd.put("POSITION_ID",POSITION_ID);
		mv.setViewName("system/position/position_list");
		//mv.addObject("POSITION_ID", "position.getPOSITION_ID()"); 
		//mv.addObject("NAME", "position.getNAME()");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("positionList", positionList);
		mv.addObject("QX",Jurisdiction.getHC());				//按钮权限
		return mv;
	}
	
	/**
	 * 显示列表ztree
	 * @param model
	 * @return
	 *//*
	@RequestMapping(value="/listAllPosition")
	public ModelAndView listAllPosition(Model model,String POSITION_ID)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			JSONArray arr = JSONArray.fromObject(positionService.listAllPosition("0"));
			String json = arr.toString();
			json = json.replaceAll("POSITION_ID", "id").replaceAll("PARENT_ID", "pId").replaceAll("NAME", "name").replaceAll("subPosition", "nodes").replaceAll("hasPosition", "checked").replaceAll("treeurl", "url");
			model.addAttribute("zTreeNodes", json);
			mv.addObject("POSITION_ID",POSITION_ID);
			mv.addObject("pd", pd);	
			mv.setViewName("system/position/position_ztree");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	*/
	
	
	
	/**Edit
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改position");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("EDITED_TIME", new Date());
		positionService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	
	/**Go to the Add new Position page
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//String POSITION_ID = null == pd.get("POSITION_ID")?"":pd.get("POSITION_ID").toString();
		//pd.put("POSITION_ID", POSITION_ID);					//上级ID
		//mv.addObject("pds",positionService.findById(pd));		//传入上级所有信息
		//mv.addObject("POSITION_ID", POSITION_ID);			//传入ID，作为子级ID用
		mv.setViewName("system/position/position_edit");
		mv.addObject("msg", "save");
		return mv;
	}	
	
	 /**go to Edit page
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String POSITION_ID = pd.getString("POSITION_ID");
		pd = positionService.findById(pd);	//find by Id
		mv.addObject("pd", pd);					//put the datas in a view container(EditView page)
		//pd.put("POSITION_ID",pd.get("PARENT_ID").toString());			
		//mv.addObject("pds",positionService.findById(pd));				//传入上级所有信息
		//mv.addObject("POSITION_ID", pd.get("PARENT_ID").toString());	//the parent ID is not useful for now
		//pd.put("POSITION_ID",POSITION_ID);							//复原本ID
		mv.setViewName("system/position/position_edit");
		mv.addObject("msg", "edit");
		return mv;
	}	

	/**判断编码是否存在
	 * @return
	 *//*
	@RequestMapping(value="/hasBianma")
	@ResponseBody
	public Object hasBianma(){
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(positionService.findByBianma(pd) != null){
				errInfo = "error";
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);				//返回结果
		return AppUtil.returnObject(new PageData(), map);
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}*/
	
	
	/**
	 * Batch deletion
	 * @throws Exception 
	 */
	@RequestMapping(value="/deleteAllU")
	@ResponseBody
	public Object deleteAllU() throws Exception {
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		logBefore(logger, Jurisdiction.getUsername()+"批量删除position");
		FHLOG.save(Jurisdiction.getUsername(), "批量删除position");
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String POSITION_IDS = pd.getString("POSITION_IDS");
		if(null != POSITION_IDS && !"".equals(POSITION_IDS)){
			String ArrayPOSITION_IDS[] = POSITION_IDS.split(",");
			positionService.deleteAllU(ArrayPOSITION_IDS);
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
	
	
	
	/**Export Position Data to Excel
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
				String keywords = pd.getString("keywords");				//Keyword search criteria
				if(null != keywords && !"".equals(keywords)){
					pd.put("keywords", keywords.trim());
				}
				/*String CREATED_TIME = pd.getString("CREATED_TIME");	//created time
				String EDITED_TIME = pd.getString("EDITED_TIME");		//edited time
				if(CREATED_TIME != null && !"".equals(CREATED_TIME)){
					pd.put("CREATED_TIME", CREATED_TIME+" 00:00:00");
				}
				if(EDITED_TIME != null && !"".equals(EDITED_TIME)){
					pd.put("EDITED_TIME", EDITED_TIME+" 00:00:00");
				} */
				
				// The dataMap should have a key and value dataMap("Key(titles)","Values(varList)")
				Map<String,Object> dataMap = new HashMap<String,Object>(); 
				List<String> titles = new ArrayList<String>();  // The titles to be displayed on the Excel file
				titles.add("地点名"); 		//1
				titles.add("具体地点");  	//2
				titles.add("负责人");		//3
				titles.add("电话号码");	//4
				titles.add("地址");		//5
				titles.add("创建时间");	//6
				titles.add("最后更新时间");	//7
				dataMap.put("titles", titles); //dataMap("Key(titles)","Values(?)")
				//List<PageData>	positionList = positionService.list(page);
				List<PageData> positionList = positionService.listAllPositions(pd);
				List<PageData> varList = new ArrayList<PageData>();
				for(int i=0;i<positionList.size();i++){
					PageData vpd = new PageData();
					vpd.put("var1", positionList.get(i).getString("NAME"));							//1
					vpd.put("var2", positionList.get(i).getString("BIANMA"));						//2
					vpd.put("var3", positionList.get(i).getString("HEADMAN"));						//3
					vpd.put("var4", positionList.get(i).getString("TEL"));							//4
					vpd.put("var5", positionList.get(i).getString("ADDRESS"));						//5
					vpd.put("var6", String.valueOf(positionList.get(i).get("CREATED_TIME")));		//6
					vpd.put("var7", String.valueOf(positionList.get(i).get("EDITED_TIME")));		//7
					varList.add(vpd);
				}
				dataMap.put("varList", varList); ////dataMap("Key(titles)","Values(varList)")
				ObjectExcelView erv = new ObjectExcelView();					//Perform Excel operations
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
		mv.setViewName("system/position/uploadexcel");
		return mv;
	}
	
	/**Download template
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/downExcel")
	public void downExcel(HttpServletResponse response)throws Exception{
		FileDownload.fileDownload(response, PathUtil.getClasspath() + Const.FILEPATHFILE + "Positions.xls", "c.xls");
	}
	
	/**Reads Data from Excel file and input the data in database and on the view(ModelAndView)
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
			String fileName =  FileUpload.fileUp(file, filePath, "position");							//file name
			List<PageData> listPd = (List)ObjectExcelRead.readExcel(filePath, fileName, 1, 0, 0);		//I changed the 2 to 1 becos the it stores in only position table but it is 2 in UserController because it store in 2 table i.e User and Role table.
			/*I removed this because it adds two rows on the table instead of one */	
//			pd.put("TEL", "");
//			pd.put("ADDRESS", "");
//			pd.put("CREATED_TIME", new Date());
//			pd.put("EDITED_TIME", "0000-00-00 00:00:00");	
			//List<Position> positionList = positionService.listAllPositionsById(pd);
			//pd.put("POSITION_ID", positionList.get(0).getPOSITION_ID());		
			/**
			 * read a file and save to database
			 * var0 :地点名     (column 1)
			 * var1 :具体地点   (column 2)
			 * var2 :负责人     (column 3)
			 */
			for(int i=0;i<listPd.size();i++){		
				pd.put("POSITION_ID", this.get32UUID());        //get32UUID is used to get random number
											
				
				String NAME = listPd.get(i).getString("var0");
				pd.put("NAME", NAME);							//sets Name to column 1
//				if(positionService.findByName(pd) != null){		// why							
//					NAME = listPd.get(i).getString("var0")+Tools.getRandomNum();
//					pd.put("NAME", NAME);
//				}
				
				String BIANMA = listPd.get(i).getString("var1");	//sets Name to column 2
				pd.put("BIANMA", BIANMA);	
//				if(positionService.findByBianma(pd) != null){									
//					BIANMA = listPd.get(i).getString("var1")+Tools.getRandomNum();
//					pd.put("BIANMA", BIANMA);
//				}
				
				
				String HEADMAN = listPd.get(i).getString("var2");	//sets Name to column 3
				pd.put("HEADMAN", HEADMAN);
//				if(positionService.findByHeadman(pd) != null){									
//					HEADMAN = listPd.get(i).getString("var2")+Tools.getRandomNum();
//					pd.put("HEADMAN", HEADMAN);
//				}
				
				
				pd.put("TEL", "");
				pd.put("ADDRESS", "");
				pd.put("CREATED_TIME", new Date());
				
				
				
				positionService.save(pd);  //save it to database
			}
			/*存入数据库操作======================================*/
			mv.addObject("msg","success");
		}
		mv.setViewName("save_result");
		return mv;
	}

	
}