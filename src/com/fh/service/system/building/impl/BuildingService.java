
package com.fh.service.system.building.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.Building;
import com.fh.entity.system.Category;
import com.fh.service.system.building.BuildingManager;
import com.fh.util.PageData;

/** Building
 * @author 
 */
@Service("buildingService")
public class BuildingService implements BuildingManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	
	
	/**Get the data through the building name
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByBuilding_Name(PageData pd)throws Exception{
		return (PageData)dao.findForObject("BuildingMapper.findByBuilding_Name", pd);
	}
	
	/**Get the data through the building name
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByBuildingNumber(PageData pd)throws Exception{
		return (PageData)dao.findForObject("BuildingMapper.findByBuildingNumber", pd);
	}
	
	/**List all buildings under a category
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAllBuildingByCategoryId(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("BuildingMapper.listAllBuildingByCategoryId", pd);
	}
	
	
	/**building list 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listBuildings(Page page)throws Exception{
		return (List<PageData>) dao.findForList("BuildingMapper.buildinglistPage", page);
	}
	
	/**User list (popover selection)
	 * @param page
	 * @return
	 * @throws Exception
	 */
//	@SuppressWarnings("unchecked")
//	public List<PageData> listUsersBystaff(Page page)throws Exception{
//		return (List<PageData>) dao.findForList("BuildingMapper.buildingBystafflistPage", page);
//	}
	
	/**Get the data from the building No
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByBuildingName(PageData pd)throws Exception{
		return (PageData)dao.findForObject("BuildingMapper.findByBuildingName", pd);
	}
	
	
	
	/**Get the data by ID
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("BuildingMapper.findById", pd);
	}
	
	/**Save the building
	 * @param pd
	 * @throws Exception
	 */
	public void saveU(PageData pd)throws Exception{
		dao.save("BuildingMapper.saveU", pd);
	}
	 
	/**Modify the building
	 * @param pd
	 * @throws Exception
	 */
	public void editU(PageData pd)throws Exception{
		dao.update("BuildingMapper.editU", pd);
	}
	
	/**Delete building
	 * @param pd
	 * @throws Exception
	 */
	public void deleteU(PageData pd)throws Exception{
		dao.delete("BuildingMapper.deleteU", pd);
	}
	
	/**Batch delete building
	 * @param USER_IDS
	 * @throws Exception
	 */
	public void deleteAllU(String[] BUILDING_IDS)throws Exception{
		dao.delete("BuildingMapper.deleteAllU", BUILDING_IDS);
	}
	
	/**building list (all)
	 * @param USER_IDS
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAllBuilding(PageData pd)throws Exception{
		return (List<PageData>) dao.findForList("BuildingMapper.listAllBuilding", pd);
	}
	
	
	/**Lists of building for location
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Building> listAllBuildings(PageData pd) throws Exception {
		return (List<Building>) dao.findForList("BuildingMapper.listAllBuildings", pd);
	}
	
	
	
	/**Get the data by ID
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByIdForLocation(PageData pd)throws Exception{
		return (PageData)dao.findForObject("BuildingMapper.findByIdForLocation", pd);
	}
	
	
	/**Lists the sub categories in this group
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Building> listBuildingsByCId(PageData pd) throws Exception {
		return (List<Building>) dao.findForList("BuildingMapper.listBuildingsByCId", pd);
	}
	
	
	/**Get the data through the building name for excel in location
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public String findByBuilding_NameForExcel(String string)throws Exception{
		return (String)dao.findForObject("BuildingMapper.findByBuilding_NameForExcel", string);
	}
	
	
}
