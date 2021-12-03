package com.fh.entity.system;

/**
 *
 * Class name：Building
 * Class description：
 * Author Unit：
 * Contact：
 * created time：2021年4月26日
 * @version 1.0
 */

import java.sql.Date;
import java.util.List;

public class Building {

	private String BUILDING_ID;
	private String CATEGORY_ID;
	private String BUILDING_NAME;
	private String NOTE;
	private String BUILDING_NO;
	private String LAST_BUILDING_NAME;
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
	public String getBUILDING_ID() {
		return BUILDING_ID;
	}
	public void setBUILDING_ID(String bUILDING_ID) {
		BUILDING_ID = bUILDING_ID;
	}
	public String getCATEGORY_ID() {
		return CATEGORY_ID;
	}
	public void setCATEGORY_ID(String cATEGORY_ID) {
		CATEGORY_ID = cATEGORY_ID;
	}
	public String getBUILDING_NAME() {
		return BUILDING_NAME;
	}
	public void setBUILDING_NAME(String bUILDING_NAME) {
		BUILDING_NAME = bUILDING_NAME;
	}
	public String getNOTE() {
		return NOTE;
	}
	public void setNOTE(String nOTE) {
		NOTE = nOTE;
	}
	public String getBUILDING_NO() {
		return BUILDING_NO;
	}
	public void setBUILDING_NO(String bUILDING_NO) {
		BUILDING_NO = bUILDING_NO;
	}
	public String getLAST_BUILDING_NAME() {
		return LAST_BUILDING_NAME;
	}
	public void setLAST_BUILDING_NAME(String lAST_BUILDING_NAME) {
		LAST_BUILDING_NAME = lAST_BUILDING_NAME;
	}
	public String getString(String string) {

		return BUILDING_NO;
	}




}
