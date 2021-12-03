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
import com.fh.util.Jurisdiction;
import com.fh.service.fhim.friends.FriendsManager;

/** 
 * 说明：好友管理
 * 创建人：FH Q313596790
 * 修改时间：2018-10-07
 */
@Controller
@RequestMapping(value="/mobilefriends")
public class MobileFriendsController extends BaseController {
	
	@Resource(name="friendsService")
	private FriendsManager friendsService;
	
	/**去添加好友页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd()throws Exception{
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("mobile/im/friends_add");
		return mv;
	}
	
	/**添加好友检索
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
		pd.put("USERNAME", Jurisdiction.getUsername());			//不检索自己
		List<PageData>	varList = friendsService.listAllToSearch(pd);
		mv.setViewName("mobile/im/friends_add");
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
