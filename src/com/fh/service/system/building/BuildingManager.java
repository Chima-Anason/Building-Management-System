
package com.fh.service.system.building;

import java.util.List;

import com.fh.entity.Page;
import com.fh.entity.system.Building;
import com.fh.entity.system.Category;
import com.fh.util.PageData;


/** building interface class
 * 
 */
public interface BuildingManager {
	
	/**Get the data through the building name
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByBuilding_Name(PageData pd)throws Exception;
	
	/**Get the data through the building number
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByBuildingNumber(PageData pd)throws Exception;
	
	/**List all buildings under a category 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listAllBuildingByCategoryId(PageData pd) throws Exception;

	
	/**building list 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listBuildings(Page page)throws Exception;
	
	/**User list (popover selection)
	 * @param page
	 * @return
	 * @throws Exception
	 */
	//public List<PageData> listUsersBystaff(Page page)throws Exception;
	
	/**Get the data from the building No
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByBuildingName(PageData pd)throws Exception;
	
	/**Get the data by ID
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**Save the building
	 * @param pd
	 * @throws Exception
	 */
	public void saveU(PageData pd)throws Exception;
	
	/**Modify the building
	 * @param pd
	 * @throws Exception
	 */
	public void editU(PageData pd)throws Exception;
	
	
	
	/**Delete building
	 * @param pd
	 * @throws Exception
	 */
	public void deleteU(PageData pd)throws Exception;
	
	/**Batch delete building
	 * @param BUILDING_IDS
	 * @throws Exception
	 */
	public void deleteAllU(String[] BUILDING_IDS)throws Exception;
	
	/**building list (all)
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listAllBuilding(PageData pd)throws Exception;
	
	
	/**Lists the building for location
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<Building> listAllBuildings(PageData pd) throws Exception;
	
	
	
	/**Get the data by ID
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByIdForLocation(PageData pd)throws Exception;
	
	
	

	/**Lists the sub buildings in this group
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<Building> listBuildingsByCId(PageData pd) throws Exception;
	
	
	/**Get the data through the building name for excel
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public String findByBuilding_NameForExcel(String string)throws Exception;
	
}
