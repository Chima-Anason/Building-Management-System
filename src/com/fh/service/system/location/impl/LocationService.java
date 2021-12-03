
package com.fh.service.system.location.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.Category;
import com.fh.entity.system.Location;
import com.fh.service.system.location.LocationManager;
import com.fh.util.PageData;

/** Location
 * @author 
 */
@Service("locationService")
public class LocationService implements LocationManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	
	
	/**Get the data through the location name
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByLocation_Name(PageData pd)throws Exception{
		return (PageData)dao.findForObject("LocationMapper.findByLocation_Name", pd);
	}
	
	/**Get the data through the building no
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByLocationNo(PageData pd)throws Exception{
		return (PageData)dao.findForObject("LocationMapper.findByLocationNo", pd);
	}
	
	
	
	
	/**List all locations under a building
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAllLocationByBuildingId(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("LocationMapper.listAllLocationByBuildingId", pd);
	}
	
	
	/**List all locations under a room
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAllLocationByRoomId(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("LocationMapper.listAllLocationByCategoryId", pd);
	}
	
	
	/**location list 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listLocations(Page page)throws Exception{
		return (List<PageData>) dao.findForList("LocationMapper.locationlistPage", page);
	}
	

	
	/**Get the data from the building No
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByLocationName(PageData pd)throws Exception{
		return (PageData)dao.findForObject("LocationMapper.findByLocationName", pd);
	}
	
	
	
	/**Get the data by ID
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("LocationMapper.findById", pd);
	}
	
	/**Save the location
	 * @param pd
	 * @throws Exception
	 */
	public void saveU(PageData pd)throws Exception{
		dao.save("LocationMapper.saveU", pd);
	}
	 
	/**Modify the location
	 * @param pd
	 * @throws Exception
	 */
	public void editU(PageData pd)throws Exception{
		dao.update("LocationMapper.editU", pd);
	}
	
	/**Delete location
	 * @param pd
	 * @throws Exception
	 */
	public void deleteU(PageData pd)throws Exception{
		dao.delete("LocationMapper.deleteU", pd);
	}
	
	/**Batch delete location
	 * @param LOCATION_IDS
	 * @throws Exception
	 */
	public void deleteAllU(String[] LOCATION_IDS)throws Exception{
		dao.delete("LocationMapper.deleteAllU", LOCATION_IDS);
	}
	
	/**location list (all)
	 * @param USER_IDS
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAllLocation(PageData pd)throws Exception{
		return (List<PageData>) dao.findForList("LocationMapper.listAllLocation", pd);
	}
	

	/**Get the data by ID
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findLocation_NameById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("LocationMapper.findLocation_NameById", pd);
	}
	
	/**Lists of location
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Location> listAllLocations(PageData pd) throws Exception {
		return (List<Location>) dao.findForList("LocationMapper.listAllLocations", pd);
	}
}
