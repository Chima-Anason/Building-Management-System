package com.fh.entity.system;

import java.sql.Date;

public class Room {
	
	private String ROOM_ID;	
	private String CATEGORY_ID;			
	private String ROOM_NAME;			
	private String NOTE;		
	private String ROOM_NO;			
	private String LAST_ROOM_NAME;
	private Date CREATED_TIME;
	private Date EDITED_TIME;
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
	public String getROOM_ID() {
		return ROOM_ID;
	}
	public void setROOM_ID(String rOOM_ID) {
		ROOM_ID = rOOM_ID;
	}
	public String getCATEGORY_ID() {
		return CATEGORY_ID;
	}
	public void setCATEGORY_ID(String cATEGORY_ID) {
		CATEGORY_ID = cATEGORY_ID;
	}
	public String getROOM_NAME() {
		return ROOM_NAME;
	}
	public void setROOM_NAME(String rOOM_NAME) {
		ROOM_NAME = rOOM_NAME;
	}
	public String getNOTE() {
		return NOTE;
	}
	public void setNOTE(String nOTE) {
		NOTE = nOTE;
	}
	public String getROOM_NO() {
		return ROOM_NO;
	}
	public void setROOM_NO(String rOOM_NO) {
		ROOM_NO = rOOM_NO;
	}
	public String getLAST_ROOM_NAME() {
		return LAST_ROOM_NAME;
	}
	public void setLAST_ROOM_NAME(String lAST_ROOM_NAME) {
		LAST_ROOM_NAME = lAST_ROOM_NAME;
	}
	
	

}
