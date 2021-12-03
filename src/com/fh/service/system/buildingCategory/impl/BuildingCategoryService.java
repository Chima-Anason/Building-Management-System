
package com.fh.service.system.buildingCategory.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.Category;
import com.fh.service.system.building.BuildingManager;
import com.fh.service.system.buildingCategory.BuildingCategoryManager;
import com.fh.util.PageData;

/** Building
 * @author 
 */
@Service("buildingCategoryService")
public class BuildingCategoryService implements BuildingCategoryManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	
	
	/**Get the data through the building category name
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByCategory_Name(PageData pd)throws Exception{
		return (PageData)dao.findForObject("BuildingCategoryMapper.findByCategory_Name", pd);
	}
	
	/**Get the data through the building cat order
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByCategoryOrder(PageData pd)throws Exception{
		return (PageData)dao.findForObject("BuildingCategoryMapper.findByCategoryOrder", pd);
	}
	
	/**List all buildings category under PID
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAllBuildingCategoryByParentId(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("BuildingCategoryMapper.listAllBuildingCategoryByParentId", pd);
	}
	
	
	/**building category list 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listBuildingCategory(Page page)throws Exception{
		return (List<PageData>) dao.findForList("BuildingCategoryMapper.buildingCategorylistPage", page);
	}
	
	
	
	/**Get the data from the building category name
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByCategoryName(PageData pd)throws Exception{
		return (PageData)dao.findForObject("BuildingCategoryMapper.findByCategoryName", pd);
	}
	
	
	
	/**Get the data by ID
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("BuildingCategoryMapper.findById", pd);
	}
	
	/**Save the building category
	 * @param pd
	 * @throws Exception
	 */
	public void saveU(PageData pd)throws Exception{
		dao.save("BuildingCategoryMapper.saveU", pd);
	}
	 
	/**Modify the building category
	 * @param pd
	 * @throws Exception
	 */
	public void editU(PageData pd)throws Exception{
		dao.update("BuildingCategoryMapper.editU", pd);
	}
	
	/**Delete building category
	 * @param pd
	 * @throws Exception
	 */
	public void deleteU(PageData pd)throws Exception{
		dao.delete("BuildingCategoryMapper.deleteU", pd);
	}
	
	/**Batch delete building category
	 * @param USER_IDS
	 * @throws Exception
	 */
	public void deleteAllU(String[] CATEGORY_IDS)throws Exception{
		dao.delete("BuildingCategoryMapper.deleteAllU", CATEGORY_IDS);
	}
	
	/**building category list (all)
	 * @param USER_IDS
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAllBuildingCategory(PageData pd)throws Exception{
		return (List<PageData>) dao.findForList("BuildingCategoryMapper.listAllBuildingCategory", pd);
	}
	
	
	/**Lists of building for location
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Category> listAllBuildingsCategory(PageData pd) throws Exception {
		return (List<Category>) dao.findForList("BuildingCategoryMapper.listAllBuildingsCategory", pd);
	}
	
	
	/**Get the data through the building category name for excel in location
	 * @param string
	 * @return
	 * @throws Exception
	 */
	public String findByCategory_NameForExcel(String string)throws Exception{
		return (String)dao.findForObject("BuildingCategoryMapper.findByCategory_NameForExcel", string);
	}
	
	
}
