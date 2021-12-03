/**
 * 
 */
/**
 * @author Administrator
 *
 */
package com.fh.service.system.room;

import java.util.List;

import com.fh.entity.Page;
import com.fh.entity.system.Building;
import com.fh.entity.system.Room;
import com.fh.util.PageData;

/** building interface class
 * 
 */
public interface RoomManager {
	
	/**Get the data through the room name
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByRoom_Name(PageData pd)throws Exception;
	
	/**Get the data through the room number
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByRoomNumber(PageData pd)throws Exception;
	
	/**List all rooms under a category 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listAllRoomByCategoryId(PageData pd) throws Exception;

	
	/**room list 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listRooms(Page page)throws Exception;
	
	
	
	/**Get the data from the room No
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByRoomNo(PageData pd)throws Exception;
	
	/**Get the data by ID
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**Save the room
	 * @param pd
	 * @throws Exception
	 */
	public void saveU(PageData pd)throws Exception;
	
	/**Modify the room
	 * @param pd
	 * @throws Exception
	 */
	public void editU(PageData pd)throws Exception;
	
	
	
	/**Delete room
	 * @param pd
	 * @throws Exception
	 */
	public void deleteU(PageData pd)throws Exception;
	
	/**Batch delete room
	 * @param ROOM_IDS
	 * @throws Exception
	 */
	public void deleteAllU(String[] ROOM_IDS)throws Exception;
	
	/**room list (all)
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listAllRoom(PageData pd)throws Exception;
	
	
	/**Lists the room for location
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<Room> listAllRooms(PageData pd) throws Exception;
	
	

	/**Get the data by ID
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByIdForLocation(PageData pd)throws Exception;
	
	
	/**Lists the sub rooms in this group
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<Room> listRoomsByCId(PageData pd) throws Exception;
	
}
