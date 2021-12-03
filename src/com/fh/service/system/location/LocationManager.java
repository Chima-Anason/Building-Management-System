
package com.fh.service.system.location;

import java.util.List;

import com.fh.entity.Page;
import com.fh.entity.system.Category;
import com.fh.entity.system.Location;
import com.fh.util.PageData;


/** location interface class
 * 
 */
public interface LocationManager {
	
	/**Get the data through the location name
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByLocation_Name(PageData pd)throws Exception;
	
	/**Get the data through the building number
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByLocationNo(PageData pd)throws Exception;
	
	
	

	/**List all locations under a building
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listAllLocationByBuildingId(PageData pd) throws Exception;


	/**List all locations under a room
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listAllLocationByRoomId(PageData pd) throws Exception;
	/**location list 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listLocations(Page page)throws Exception;
	
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
	public PageData findByLocationName(PageData pd)throws Exception;
	
	/**Get the data by ID
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**Save the location
	 * @param pd
	 * @throws Exception
	 */
	public void saveU(PageData pd)throws Exception;
	
	/**Modify the location
	 * @param pd
	 * @throws Exception
	 */
	public void editU(PageData pd)throws Exception;
	
	
	
	/**Delete location
	 * @param pd
	 * @throws Exception
	 */
	public void deleteU(PageData pd)throws Exception;
	
	/**Batch delete location
	 * @param LOCATION_IDS
	 * @throws Exception
	 */
	public void deleteAllU(String[] LOCATION_IDS)throws Exception;
	
	/**building list (all)
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listAllLocation(PageData pd)throws Exception;
	
	
	/**Get the data by ID
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findLocation_NameById(PageData pd)throws Exception;
	
	/**Lists the building for location
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<Location> listAllLocations(PageData pd) throws Exception;
	
	
	
}
