package com.fh.service.system.headman;

import java.util.List;

import com.fh.entity.Page;
import com.fh.entity.system.Building;
import com.fh.entity.system.Headman;
import com.fh.util.PageData;

/** 
 * 说明：headman interface
 * 创建人：
 * @version
 */
public interface HeadmanManager{
	
	
	/**Get the data through the headman name
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByHeadman_Name(PageData pd)throws Exception;
	
	
	
	/**Get the data from the headman No
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByHeadmanNo(PageData pd)throws Exception;
	

	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception;
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd)throws Exception;
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	
	
	/**Lists the headman for location
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<Headman> listAllHeadmans(PageData pd) throws Exception;
	
}

