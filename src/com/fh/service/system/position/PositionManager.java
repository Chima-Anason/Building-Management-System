package com.fh.service.system.position;

import java.util.List;

import com.fh.entity.Page;
import com.fh.entity.system.Position;
import com.fh.entity.system.Role;
import com.fh.util.PageData;


/** 
 * @author fh313596790qq(青苔)
 */
public interface PositionManager {
	
	
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
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;

	/**通过编码获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByBianma(PageData pd)throws Exception;
	
	/**
	 * 通过ID获取其子级列表
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	public List<Position> listSubPositionByParentId(String parentId) throws Exception;
	
	/**
	 * 获取所有数据并填充每条数据的子级列表(递归处理)
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	public List<Position> listAllPosition(String parentId) throws Exception;
	
	/**
	 * 获取所有数据并填充每条数据的子级列表(递归处理)下拉ztree用
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listAllPositionToSelect(String parentId, List<PageData> zpositionPdList) throws Exception;
	
	/**获取某个部门所有下级部门ID(返回拼接字符串 in的形式)
	 * @param POSITION_ID
	 * @return
	 * @throws Exception
	 */
	public String getPosition_IDS(String POSITION_ID) throws Exception;
	
	
	
	public List<PageData> listAllPositions(PageData pd)throws Exception;
	
	public List<Position> listAllPositionsById(PageData pd) throws Exception;
	
	public PageData findByName(PageData pd)throws Exception;
	
	
	public PageData findByHeadman(PageData pd)throws Exception;
	
	public void deleteAllU(String[] POSITION_IDS)throws Exception;
	
	

}
