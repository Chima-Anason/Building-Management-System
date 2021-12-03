
package com.fh.service.system.buildingCategory;

import java.util.List;

import com.fh.entity.Page;
import com.fh.entity.system.Category;
import com.fh.util.PageData;


/** building category interface class
 * 
 */
public interface BuildingCategoryManager {
	
	/**Get the data through the building category name
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByCategory_Name(PageData pd)throws Exception;
	
	
	/**Get the data through the category number
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByCategoryOrder(PageData pd)throws Exception;
	
	/**List all building category by parent Id 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listAllBuildingCategoryByParentId(PageData pd) throws Exception;

	
	/**building category list 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listBuildingCategory(Page page)throws Exception;
	
	
	
	/**Get the data from the building category No
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByCategoryName(PageData pd)throws Exception;
	
	/**Get the data by ID
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**Save the building category
	 * @param pd
	 * @throws Exception
	 */
	public void saveU(PageData pd)throws Exception;
	
	/**Modify the building
	 * @param pd
	 * @throws Exception
	 */
	public void editU(PageData pd)throws Exception;
	
	
	
	/**Delete building category
	 * @param pd
	 * @throws Exception
	 */
	public void deleteU(PageData pd)throws Exception;
	
	/**Batch delete building category
	 * @param BUILDING_IDS
	 * @throws Exception
	 */
	public void deleteAllU(String[] CATEGORY_IDS)throws Exception;
	
	/**building category list (all)
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listAllBuildingCategory(PageData pd)throws Exception;
	
	
	/**Lists the building for location
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<Category> listAllBuildingsCategory(PageData pd) throws Exception;
	
	
	/**Get the data through the building category name for excel
	 * @param string
	 * @return
	 * @throws Exception
	 */
	public String findByCategory_NameForExcel(String string)throws Exception;
	

}
