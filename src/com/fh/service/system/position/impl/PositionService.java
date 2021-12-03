package com.fh.service.system.position.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.Position;
import com.fh.entity.system.Role;
import com.fh.service.system.position.PositionManager;
import com.fh.util.PageData;
import com.fh.util.Tools;


/** 系统用户
 * @author fh313596790qq(青苔)
 */
@Service("positionService")
public class PositionService implements PositionManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	@Override
	public void save(PageData pd) throws Exception {
		dao.save("PositionMapper.save", pd);
		
	}

	@Override
	public void delete(PageData pd) throws Exception {
		dao.delete("PositionMapper.delete", pd);
		
	}

	@Override
	public void edit(PageData pd) throws Exception {
		dao.update("PositionMapper.edit", pd);
		
	}

	@Override
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>)dao.findForList("PositionMapper.datalistPage", page);
	}

	@Override
	public PageData findById(PageData pd) throws Exception {
		return (PageData)dao.findForObject("PositionMapper.findById", pd);
	}
	

	@Override
	public PageData findByBianma(PageData pd) throws Exception {
		return (PageData)dao.findForObject("PositionMapper.findByBianma", pd);
	}

	@Override
	public List<Position> listSubPositionByParentId(String parentId)
			throws Exception {
		return (List<Position>) dao.findForList("PositionMapper.listSubPositionByParentId", parentId);
	}
	
	public List<Position> listAllPosition(String parentId) throws Exception {
		List<Position> positionList = this.listSubPositionByParentId(parentId);
		for(Position depar : positionList){
			depar.setTreeurl("position/list.do?POSITION_ID="+depar.getPOSITION_ID());
			depar.setSubPosition(this.listAllPosition(depar.getPOSITION_ID()));
			depar.setTarget("treeFrame");
			depar.setIcon("static/images/user.gif");
	}
	return positionList;
}
	
	@SuppressWarnings("unchecked")
	public List<PageData>[] listAllbyPd(String parentId,List<PageData> zpositionPdList) throws Exception {
		List<Position> positionList = this.listSubPositionByParentId(parentId);
		List<PageData> positionPdList = new ArrayList<PageData>();
		for(Position depar : positionList){
			PageData pd = new PageData();
			pd.put("id", depar.getPOSITION_ID());
			pd.put("parentId", depar.getPARENT_ID());
			pd.put("name", depar.getNAME());
			pd.put("icon", "static/images/user.gif");
			positionPdList.add(pd);
			zpositionPdList.add(pd);
		}
		List<PageData>[] arrayDep = new List[2];
		arrayDep[0] = zpositionPdList;
		arrayDep[1] = positionPdList;
		return arrayDep;
	}

	@Override
	public List<PageData> listAllPositionToSelect(String parentId,
			List<PageData> zpositionPdList) throws Exception {
		List<PageData>[] arrayDep = this.listAllbyPd(parentId,zpositionPdList);
		List<PageData> positionPdList = arrayDep[1];
		for(PageData pd : positionPdList){
			this.listAllPositionToSelect(pd.getString("id"),arrayDep[0]);
		}
		return arrayDep[0];
	}

	@Override
	public String getPosition_IDS(String POSITION_ID) throws Exception {
		POSITION_ID = Tools.notEmpty(POSITION_ID)?POSITION_ID:"0";
		List<PageData> zpositionPdList = new ArrayList<PageData>();
		zpositionPdList = this.listAllPositionToSelect(POSITION_ID,zpositionPdList);
		StringBuffer sb = new StringBuffer();
		sb.append("(");
		for(PageData dpd : zpositionPdList){
			sb.append("'");
			sb.append(dpd.getString("id"));
			sb.append("'");
			sb.append(",");
		}
		sb.append("'fh')");
		return sb.toString();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<PageData> listAllPositions(PageData pd)throws Exception{
		return (List<PageData>) dao.findForList("PositionMapper.listAllPositions", pd);
	}
	
	@SuppressWarnings("unchecked")
	public List<Position> listAllPositionsById(PageData pd) throws Exception {
		return (List<Position>) dao.findForList("PositionMapper.listAllPositionsById", pd);
	}
	

	public PageData findByName(PageData pd)throws Exception{
		return (PageData)dao.findForObject("PositionMapper.findByName", pd);
	}
	

	public PageData findByHeadman(PageData pd)throws Exception{
		return (PageData)dao.findForObject("PositionMapper.findByHeadman", pd);
	}
	
	
	public void deleteAllU(String[] POSITION_IDS)throws Exception{
		dao.delete("PositionMapper.deleteAllU", POSITION_IDS);
	}
	
	
}
