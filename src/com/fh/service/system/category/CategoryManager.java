package com.fh.service.system.category;

import java.util.List;

import com.fh.entity.Page;
import com.fh.entity.system.Category;
import com.fh.util.PageData;

/**	Category
 * 
 */
public interface CategoryManager {
	
	/**Lists the sub categories in this group
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<Category> listAllCategoriesByPId(PageData pd) throws Exception;
	
	/**Find by ID
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findObjectById(PageData pd) throws Exception;
	
	
	
	/**category list 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listCategories(Page page)throws Exception;
	
	
	/**Find by Category_order
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getCategoryByCategory_order(PageData pd) throws Exception;
	
	
	/**Find by Category_Name
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getCategoryByCategory_Name(PageData pd) throws Exception;
	
	
	/**Create new Category
	 * @param pd
	 * @throws Exception
	 */
	public void add(PageData pd) throws Exception;
	
	/**Modify
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd) throws Exception;
	
	/**Delete Category
	 * @param Category_ID
	 * @throws Exception
	 */
	public void deleteCategoryById(String Category_ID) throws Exception;
	

	/**Find by ID
	 * @param Category_ID
	 * @return
	 * @throws Exception
	 */
	public Category getCategoryById(String Category_ID) throws Exception;
	
	/**Gets the list of categories by an array of category ids
	 * @param arryCATEGORY_ID
	 * @throws Exception
	 */
	public List<Category> getCategoryByArryCATEGORY_ID(String[] arryCATEGORY_ID)throws Exception;
	
	/**List of categories (for Popup Selection)
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> categoryListWindow(Page page)throws Exception;
	
	
	/**Get the total category
	 * @param pd
	 * @throws Exception
	 */
	public String getMaxCategory(String value)throws Exception;
	

}
