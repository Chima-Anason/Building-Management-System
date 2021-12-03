package com.fh.controller.mobile.head;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.controller.base.BaseController;
import com.fh.service.system.user.UserManager;
import com.fh.service.system.userphoto.UserPhotoManager;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.Tools;

/** 
 * 类名称：MobileHeadController
 * 创建人：FH 313596790Q
 * 修改时间：2018年10月6日
 * @version
 */
@Controller
@RequestMapping(value="/mobilehead")
public class MobileHeadController extends BaseController {
	
	@Resource(name="userService")
	private UserManager userService;	
	@Resource(name="userphotoService")
	private UserPhotoManager userphotoService;
	
	/**获取基本信息
	 * @return
	 */
	@RequestMapping(value="/getNowUser")
	@ResponseBody
	public Object getList() {
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			Session session = Jurisdiction.getSession();
			PageData pds = new PageData();
			pds = (PageData)session.getAttribute(Const.SESSION_userpds);
			if(null == pds){
				pd.put(Const.SESSION_USERNAME, Jurisdiction.getUsername());//当前登录者用户名
				pds = userService.findByUsername(pd);
				session.setAttribute(Const.SESSION_userpds, pds);
			}
			pdList.add(pds);
			map.put("list", pdList);
			PageData pdPhoto = userphotoService.findById(pds);
			map.put("userPhoto", null == pdPhoto?"static/ace/avatars/user.jpg":pdPhoto.getString("PHOTO2"));//用户头像
			String strWEBSOCKET = Tools.readTxtFile(Const.WEBSOCKET);	//读取WEBSOCKET配置
			if(null != strWEBSOCKET && !"".equals(strWEBSOCKET)){
				String strIW[] = strWEBSOCKET.split(",fh,");
				if(strIW.length == 7){
					map.put("wimadress", strIW[0]+":"+strIW[1]);		//即时聊天服务器IP和端口
				}
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		} finally {
			logAfter(logger);
		}
		return AppUtil.returnObject(pd, map);
	}
}


// F-H Q  3-135-9679-0 