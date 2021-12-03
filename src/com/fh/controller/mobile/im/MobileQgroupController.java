package com.fh.controller.mobile.im;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.util.PageData;
import com.fh.service.fhim.iqgroup.IQgroupManager;
import com.fh.service.fhim.qgroup.QgroupManager;
import com.fh.service.fhim.sysmsg.SysmsgManager;

/** 
 * 说明：群组
 * 创建人：FH Q313596790
 * 修改时间：2018-10-7
 */
@Controller
@RequestMapping(value="/mobileqgroup")
public class MobileQgroupController extends BaseController {
	
	@Resource(name="qgroupService")
	private QgroupManager qgroupService;
	@Resource(name="iqgroupService")
	private IQgroupManager iqgroupService;
	@Resource(name="sysmsgService")
	private SysmsgManager sysmsgService;
	
	/**去添加群页面(好友面板中检索页)
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd()throws Exception{
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("mobile/im/qgroup_add");
		return mv;
	}
	
	/**群检索
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/search")
	public ModelAndView search()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		List<PageData>	varList = qgroupService.searchListAll(pd);
		mv.setViewName("mobile/im/qgroup_add");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		return mv;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
