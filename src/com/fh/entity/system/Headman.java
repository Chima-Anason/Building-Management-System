package com.fh.entity.system;

import java.sql.Date;

public class Headman {

	private String HEADMAN_ID;
	private String HEADMAN_NO;
	private String HEADMAN_NAME;			//负责人姓名		
	private Date CREATED_TIME;             //创建时间
	private Date EDITED_TIME;
	public String getHEADMAN_ID() {
		return HEADMAN_ID;
	}
	public void setHEADMAN_ID(String hEADMAN_ID) {
		HEADMAN_ID = hEADMAN_ID;
	}
	public String getHEADMAN_NAME() {
		return HEADMAN_NAME;
	}
	public void setHEADMAN_NAME(String hEADMAN_NAME) {
		HEADMAN_NAME = hEADMAN_NAME;
	}
	public Date getCREATED_TIME() {
		return CREATED_TIME;
	}
	public void setCREATED_TIME(Date cREATED_TIME) {
		CREATED_TIME = cREATED_TIME;
	}
	public String getHEADMAN_NO() {
		return HEADMAN_NO;
	}
	public void setHEADMAN_NO(String hEADMAN_NO) {
		HEADMAN_NO = hEADMAN_NO;
	}
	public Date getEDITED_TIME() {
		return EDITED_TIME;
	}
	public void setEDITED_TIME(Date eDITED_TIME) {
		EDITED_TIME = eDITED_TIME;
	}


}
