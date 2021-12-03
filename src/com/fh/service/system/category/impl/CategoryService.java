package com.fh.service.system.category.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.Category;
import com.fh.service.system.category.CategoryManager;
import com.fh.util.PageData;

/**	Category
 * @author Chima
 */
@Service("categoryService")
public class CategoryService implements CategoryManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**Lists the sub categories in this group
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Category> listAllCategoriesByPId(PageData pd) throws Exception {
		return (List<Category>) dao.findForList("CategoryMapper.listAllCategoriesByPId", pd);
	}
	
	/**Find by ID
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findObjectById(PageData pd) throws Exception {
		return (PageData)dao.findForObject("CategoryMapper.findObjectById", pd);
	}
	
	
	
	/**category list 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listCategories(Page page)throws Exception{
		return (List<PageData>) dao.findForList("CategoryMapper.categorylistPage", page);
	}
	
	/**Find by Category_order
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getCategoryByCategory_order(PageData pd) throws Exception{
		return (PageData)dao.findForObject("CategoryMapper.getCategoryByCategory_order", pd);
	}
	
	
	/**Find by Category_Name
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getCategoryByCategory_Name(PageData pd) throws Exception{
		return (PageData)dao.findForObject("CategoryMapper.getCategoryByCategory_Name", pd);
	}
	
	
	/**Create new Category
	 * @param pd
	 * @throws Exception
	 */
	public void add(PageData pd) throws Exception {
		dao.save("CategoryMapper.insert", pd);
	}
	
	/**Modify
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd) throws Exception {
		dao.update("CategoryMapper.edit", pd);
	}
	
	/**Delete Category
	 * @param Category_ID
	 * @throws Exception
	 */
	public void deleteCategoryById(String Category_ID) throws Exception {
		dao.delete("CategoryMapper.deleteCategoryById", Category_ID);
	}
	
	
	/**Find by ID
	 * @param Category_ID
	 * @return
	 * @throws Exception
	 */
	public Category getCategoryById(String Category_ID) throws Exception {
		return (Category) dao.findForObject("CategoryMapper.getCategoryById", Category_ID);
	}
	
	/**Gets the list of categories by an array of category ids
	 * @param arryROLE_ID
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Category> getCategoryByArryCATEGORY_ID(String[] arryCATEGORY_ID)throws Exception{
		return (List<Category>) dao.findForList("CategoryMapper.listAllCategoriesByArryCATEGORY_ID", arryCATEGORY_ID);
	}
	
	/**List of categories (for Popup Selection)
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> categoryListWindow(Page page)throws Exception{
		return (List<PageData>) dao.findForList("CategoryMapper.categoryWindowlistPage", page);
	}
	
	/**Get total category 
	 * @param pd
	 * @throws Exception
	 */
	public String getMaxCategory(String value)throws Exception{
		return (String)dao.findForObject("CategoryMapper.getMaxCategory", value);
	}

}
