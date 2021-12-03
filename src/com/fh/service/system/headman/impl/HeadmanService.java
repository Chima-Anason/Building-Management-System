package com.fh.service.system.headman.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.Building;
import com.fh.entity.system.Headman;
import com.fh.util.PageData;
import com.fh.service.system.fhbutton.FhbuttonManager;
import com.fh.service.system.headman.HeadmanManager;

/** 
 * 说明： headman 
 * 创建人：
 * @version
 */
@Service("headmanService")
public class HeadmanService implements HeadmanManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	
	
	/**Get the data through the headman name
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByHeadman_Name(PageData pd)throws Exception{
		return (PageData)dao.findForObject("HeadmanMapper.findByHeadman_Name", pd);
	}
	
	
	/**Get the data from the headman No
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByHeadmanNo(PageData pd)throws Exception{
		return (PageData)dao.findForObject("HeadmanMapper.findByHeadmanNo", pd);
	}
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("HeadmanMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("HeadmanMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("HeadmanMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("HeadmanMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("HeadmanMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("HeadmanMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("HeadmanMapper.deleteAll", ArrayDATA_IDS);
	}
	
	
	
	/**Lists of headman for location
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Headman> listAllHeadmans(PageData pd) throws Exception {
		return (List<Headman>) dao.findForList("HeadmanMapper.listAllHeadmans", pd);
	}
	
	
}

