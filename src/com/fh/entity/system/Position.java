package com.fh.entity.system;

import java.sql.Date;
import java.util.List;

public class Position {
	private String NAME;			//名称
	private String BIANMA;			//编码
	private String PARENT_ID;		//上级ID
	private String HEADMAN;			//负责人
	private String TEL;				//电话
	private	String ADDRESS;			//地点
	private String POSITION_ID;	//主键
	private Date CREATED_TIME;
	private Date EDITED_TIME;
	private String target;
	private Position position;
	private List<Position> subPosition;
	private boolean hasPosition = false;
	private String treeurl;
	private String icon;
	public String getNAME() {
		return NAME;
	}
	public void setNAME(String nAME) {
		NAME = nAME;
	}
	public String getBIANMA() {
		return BIANMA;
	}
	public void setBIANMA(String bIANMA) {
		BIANMA = bIANMA;
	}
	public String getPARENT_ID() {
		return PARENT_ID;
	}
	public void setPARENT_ID(String pARENT_ID) {
		PARENT_ID = pARENT_ID;
	}
	public String getHEADMAN() {
		return HEADMAN;
	}
	public void setHEADMAN(String hEADMAN) {
		HEADMAN = hEADMAN;
	}
	public String getTEL() {
		return TEL;
	}
	public void setTEL(String tEL) {
		TEL = tEL;
	}
	public String getADDRESS() {
		return ADDRESS;
	}
	public void setADDRESS(String aDDRESS) {
		ADDRESS = aDDRESS;
	}
	public String getPOSITION_ID() {
		return POSITION_ID;
	}
	public void setPOSITION_ID(String pOSITION_ID) {
		POSITION_ID = pOSITION_ID;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public Position getPosition() {
		return position;
	}
	public void setPosition(Position position) {
		this.position = position;
	}
	public List<Position> getSubPosition() {
		return subPosition;
	}
	public void setSubPosition(List<Position> subPosition) {
		this.subPosition = subPosition;
	}
	public boolean isHasPosition() {
		return hasPosition;
	}
	public void setHasPosition(boolean hasPosition) {
		this.hasPosition = hasPosition;
	}
	public String getTreeurl() {
		return treeurl;
	}
	public void setTreeurl(String treeurl) {
		this.treeurl = treeurl;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public Date getCREATED_TIME() {
		return CREATED_TIME;
	}
	public void setCREATED_TIME(Date cREATED_TIME) {
		CREATED_TIME = cREATED_TIME;
	}
	public Date getEDITED_TIME() {
		return EDITED_TIME;
	}
	public void setEDITED_TIME(Date eDITED_TIME) {
		EDITED_TIME = eDITED_TIME;
	}
	

}
