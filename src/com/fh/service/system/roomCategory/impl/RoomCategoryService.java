
package com.fh.service.system.roomCategory.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.Category;
import com.fh.service.system.building.BuildingManager;
import com.fh.service.system.buildingCategory.BuildingCategoryManager;
import com.fh.service.system.roomCategory.RoomCategoryManager;
import com.fh.util.PageData;

/** Room
 * @author 
 */
@Service("roomCategoryService")
public class RoomCategoryService implements RoomCategoryManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	
	
	/**Get the data through the room category name
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByCategory_Name(PageData pd)throws Exception{
		return (PageData)dao.findForObject("RoomCategoryMapper.findByCategory_Name", pd);
	}
	
	/**Get the data through the room cat order
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByCategoryOrder(PageData pd)throws Exception{
		return (PageData)dao.findForObject("RoomCategoryMapper.findByCategoryOrder", pd);
	}
	
	/**List all rooms category under PID
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAllRoomCategoryByParentId(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("RoomCategoryMapper.listAllRoomCategoryByParentId", pd);
	}
	
	
	/**room category list 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listRoomCategory(Page page)throws Exception{
		return (List<PageData>) dao.findForList("RoomCategoryMapper.roomCategorylistPage", page);
	}
	
	
	
	/**Get the data from the room category name
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByCategoryName(PageData pd)throws Exception{
		return (PageData)dao.findForObject("RoomCategoryMapper.findByCategoryName", pd);
	}
	
	
	
	/**Get the data by ID
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("RoomCategoryMapper.findById", pd);
	}
	
	/**Save the room category
	 * @param pd
	 * @throws Exception
	 */
	public void saveU(PageData pd)throws Exception{
		dao.save("RoomCategoryMapper.saveU", pd);
	}
	 
	/**Modify the room category
	 * @param pd
	 * @throws Exception
	 */
	public void editU(PageData pd)throws Exception{
		dao.update("RoomCategoryMapper.editU", pd);
	}
	
	/**Delete room category
	 * @param pd
	 * @throws Exception
	 */
	public void deleteU(PageData pd)throws Exception{
		dao.delete("RoomCategoryMapper.deleteU", pd);
	}
	
	/**Batch delete room category
	 * @param CATEGORY_IDS
	 * @throws Exception
	 */
	public void deleteAllU(String[] CATEGORY_IDS)throws Exception{
		dao.delete("RoomCategoryMapper.deleteAllU", CATEGORY_IDS);
	}
	
	/**room category list (all)
	 * @param USER_IDS
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAllRoomCategory(PageData pd)throws Exception{
		return (List<PageData>) dao.findForList("RoomCategoryMapper.listAllRoomCategory", pd);
	}
	
	
	/**Lists of room for location
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Category> listAllRoomsCategory(PageData pd) throws Exception {
		return (List<Category>) dao.findForList("RoomCategoryMapper.listAllRoomsCategory", pd);
	}
	
	
	/**Get the data through the room category name for excel in location
	 * @param string
	 * @return
	 * @throws Exception
	 */
	public String findByCategory_NameForExcel(String string)throws Exception{
		return (String)dao.findForObject("RoomCategoryMapper.findByCategory_NameForExcel", string);
	}
	
}
