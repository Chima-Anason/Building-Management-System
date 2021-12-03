
package com.fh.service.system.roomCategory;

import java.util.List;

import com.fh.entity.Page;
import com.fh.entity.system.Category;
import com.fh.util.PageData;


/** Room category interface class
 * 
 */
public interface RoomCategoryManager {
	
	/**Get the data through the Room category name
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
	
	/**List all Room category by parent Id 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listAllRoomCategoryByParentId(PageData pd) throws Exception;

	
	/**Room category list 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listRoomCategory(Page page)throws Exception;
	
	
	
	/**Get the data from the Room category No
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
	
	/**Save the Room category
	 * @param pd
	 * @throws Exception
	 */
	public void saveU(PageData pd)throws Exception;
	
	/**Modify the Room
	 * @param pd
	 * @throws Exception
	 */
	public void editU(PageData pd)throws Exception;
	
	
	
	/**Delete Room category
	 * @param pd
	 * @throws Exception
	 */
	public void deleteU(PageData pd)throws Exception;
	
	/**Batch delete Room category
	 * @param CATEGORY_IDS
	 * @throws Exception
	 */
	public void deleteAllU(String[] CATEGORY_IDS)throws Exception;
	
	/**Room category list (all)
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listAllRoomCategory(PageData pd)throws Exception;
	
	
	/**Lists the Room for location
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<Category> listAllRoomsCategory(PageData pd) throws Exception;
	
	/**Get the data through the room category name for excel
	 * @param string
	 * @return
	 * @throws Exception
	 */
	public String findByCategory_NameForExcel(String string)throws Exception;
	

}
