/**
 * 
 */
/**
 * @author Administrator
 *
 */
package com.fh.service.system.room.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.Building;
import com.fh.entity.system.Room;
import com.fh.service.system.building.BuildingManager;
import com.fh.service.system.room.RoomManager;
import com.fh.util.PageData;

/** Building
 * @author fh313596790qq(青苔)
 */
@Service("roomService")
public class RoomService implements RoomManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	
	
	/**Get the data through the building name
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByRoom_Name(PageData pd)throws Exception{
		return (PageData)dao.findForObject("RoomMapper.findByRoom_Name", pd);
	}
	
	/**Get the data through the building name
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByRoomNumber(PageData pd)throws Exception{
		return (PageData)dao.findForObject("RoomMapper.findByRoomNumber", pd);
	}
	
	/**List all buildings under a category
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAllRoomByCategoryId(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("RoomMapper.listAllRoomByCategoryId", pd);
	}
	
	
	/**building list 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listRooms(Page page)throws Exception{
		return (List<PageData>) dao.findForList("RoomMapper.roomlistPage", page);
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
	public PageData findByRoomNo(PageData pd)throws Exception{
		return (PageData)dao.findForObject("RoomMapper.findByRoomNo", pd);
	}
	
	
	
	/**Get the data by ID
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("RoomMapper.findById", pd);
	}
	
	/**Save the building
	 * @param pd
	 * @throws Exception
	 */
	public void saveU(PageData pd)throws Exception{
		dao.save("RoomMapper.saveU", pd);
	}
	 
	/**Modify the building
	 * @param pd
	 * @throws Exception
	 */
	public void editU(PageData pd)throws Exception{
		dao.update("RoomMapper.editU", pd);
	}
	
	/**Delete building
	 * @param pd
	 * @throws Exception
	 */
	public void deleteU(PageData pd)throws Exception{
		dao.delete("RoomMapper.deleteU", pd);
	}
	
	/**Batch delete building
	 * @param USER_IDS
	 * @throws Exception
	 */
	public void deleteAllU(String[] ROOM_IDS)throws Exception{
		dao.delete("RoomMapper.deleteAllU", ROOM_IDS);
	}
	
	/**building list (all)
	 * @param USER_IDS
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData>  listAllRoom(PageData pd)throws Exception{
		return (List<PageData>) dao.findForList("RoomMapper.listAllRoom", pd);
	}
	
	
	/**Lists of room for location
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Room> listAllRooms(PageData pd) throws Exception {
		return (List<Room>) dao.findForList("RoomMapper.listAllRooms", pd);
	}
	
	/**Get the data by ID
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByIdForLocation(PageData pd)throws Exception{
		return (PageData)dao.findForObject("RoomMapper.findByIdForLocation", pd);
	}
	
	/**Lists the sub rooms in this group
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Room> listRoomsByCId(PageData pd) throws Exception {
		return (List<Room>) dao.findForList("RoomMapper.listRoomsByCId", pd);
	}
	
}
