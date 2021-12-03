package com.fh.controller.system.category;

import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.Menu;
import com.fh.entity.system.Category;
import com.fh.service.system.fhlog.FHlogManager;
import com.fh.service.system.menu.MenuManager;
import com.fh.service.system.category.CategoryManager;
import com.fh.util.AppUtil;
import com.fh.util.DateUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.RightsHelper;
import com.fh.util.Tools;

/** 
 * 类名称：CategoryController 分类权限管理
 * 创建人：
 * 修改时间：2021年4月26日
 * @version
 */
@Controller
@RequestMapping(value="/category")
public class CategoryController extends BaseController {
	
	String menuUrl = "category.do"; //菜单地址(权限用)/Menu address (for permissions)
	@Resource(name="menuService")
	private MenuManager menuService;
	@Resource(name="categoryService")
	private CategoryManager categoryService;
	@Resource(name="fhlogService")
	private FHlogManager FHLOG;
	
	/** 进入权限首页/Access the access front page
	 * @param 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping
	public ModelAndView list(Page page)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(pd.getString("CATEGORY_ID") == null || "".equals(pd.getString("CATEGORY_ID").trim())){
				pd.put("CATEGORY_ID", "01");										//第一组类别是默认列出的/The first set of category is listed by default 
			}
			/*PageData fpd = new PageData();
			fpd.put("CATEGORY_ID", "02");*/
			
			page.setPd(pd);
			
			
			List<PageData>  categoryList = categoryService.categoryListWindow(page);		//列出组(页面横向排列的一级组)
			//List<Category> categoryList_z = categoryService.listAllCategoriesByPId(pd);		//列出此组下架类别
			pd = categoryService.findObjectById(pd);							//取得点击的类别组(横排的)
			mv.addObject("pd", pd);
			mv.addObject("categoryList_z", categoryList);
			//mv.addObject("categoryList_z", categoryList_z);
			mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
			mv.setViewName("system/category/category_list");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**去新增页面/ Go to the Add page
	 * @param 
	 * @return
	 */
	@RequestMapping(value="/toAdd")
	public ModelAndView toAdd(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			mv.addObject("msg", "add");
			mv.setViewName("system/category/category_edit");
			mv.addObject("pd", pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**保存新增类别/ Save new Category
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public ModelAndView add()throws Exception{
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限/ Check permission
		logBefore(logger, Jurisdiction.getUsername()+"新增类别");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			
			String parent_id = pd.getString("PARENT_ID");		//父类别 id/ Parent category Id
			pd.put("CATEGORY_ID", parent_id);	
			
			
				List<Category> categoryList_z = categoryService.listAllCategoriesByPId(pd);  //loop thru the list by parent id and add 1 then assign it to category order 
				int total = 0;
				for (int i=0; i < categoryList_z.size() ;i++) {
					++total;
				}	  
			pd.put("CATEGORY_ORDER", ++total);
		
			pd.put("CATEGORY_ID", this.get32UUID());	//ID 主键
			
				
			if(null == categoryService.getCategoryByCategory_Name(pd)){	//判断类别名是否存在 / To determine if a category name already exists
				categoryService.add(pd);					//执行保存
				FHLOG.save(Jurisdiction.getUsername(), "新增类别:"+pd.getString("Category_NAME"));
				mv.addObject("msg","success");
			}else{
				mv.addObject("msg","failed");
			}

			
		} catch(Exception e){
			logger.error(e.toString(), e);
			mv.addObject("msg","failed");
		}
		mv.setViewName("save_result");
		return mv;
	}
	
	/**请求编辑/ Request Edit
	 * @param CATEGORY_ID
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/toEdit")
	public ModelAndView toEdit( String CATEGORY_ID )throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			pd.put("CATEGORY_ID", CATEGORY_ID);
			pd = categoryService.findObjectById(pd);
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
			mv.setViewName("system/category/category_edit");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**保存修改/ save Edit
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit()throws Exception{
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		logBefore(logger, Jurisdiction.getUsername()+"修改分类");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			categoryService.edit(pd);
			FHLOG.save(Jurisdiction.getUsername(), "修改分类:"+pd.getString("CATEGORY_NAME"));
			mv.addObject("msg","success");
		} catch(Exception e){
			logger.error(e.toString(), e);
			mv.addObject("msg","failed");
		}
		mv.setViewName("save_result");
		return mv;
	}
	


	/**删除类别/ Delete category
	 * @param ROLE_ID
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public Object deleteCategory(@RequestParam String CATEGORY_ID)throws Exception{
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限/ check permission
		logBefore(logger, Jurisdiction.getUsername()+"删除类别");
		Map<String,String> map = new HashMap<String,String>();
		PageData pd = new PageData();
		String errInfo = "";
		try{
			pd.put("CATEGORY_ID", CATEGORY_ID);
			List<Category> categoryList_z = categoryService.listAllCategoriesByPId(pd);		//列出此部门的所有下级/List all the sub category in this part
			if(categoryList_z.size() > 0){
				errInfo = "false";											//下级有数据时，删除失败/The deletion failed when the sub category has data
			//}else{
				//List<PageData> buildinglist = buildingService.listAllBuildingByCategoryId(pd);			//此类别下的楼宇
				//List<PageData> roomlist = appuserService.listAllRoomByCategoryId(pd);	//此类别下的房间
				//if(buildinglist.size() > 0 || roomlist.size() > 0){						//此类别已被使用就不能删除
					//errInfo = "false2";
				}else{
				categoryService.deleteCategoryById(CATEGORY_ID);	//执行删除
				FHLOG.save(Jurisdiction.getUsername(), "删除类别ID为:"+CATEGORY_ID);
				errInfo = "success";
				//}
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);
		return AppUtil.returnObject(new PageData(), map);
	}
	
	
	
	/** 选择分类(弹窗选择用)
	 * @param 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/categoryListWindow")
	public ModelAndView categoryListWindow(Page page)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");					//关键词检索条件/Keyword search criteria
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData> categoryList = categoryService.categoryListWindow(page);//列出所有分类
		mv.addObject("pd", pd);
		mv.addObject("categoryList", categoryList);
		mv.setViewName("system/category/window_category_list");
		return mv;
	}
	
}