package com.fh.entity.system;

/**
 *
 * Class name：Building and Room Category
 * Class description：
 * Author Unit：
 * Contact：
 * created time：2021年4月26日
 * @version 1.0
 */

public class Category {

	private String CATEGORY_ID;		//ID
	private String CATEGORY_NAME;	//名称
	private String PARENT_ID;	//上级ID
	private String CATEGORY_ORDER;		//编号
	public String getCATEGORY_ID() {
		return CATEGORY_ID;
	}
	public void setCATEGORY_ID(String cATEGORY_ID) {
		CATEGORY_ID = cATEGORY_ID;
	}
	public String getCATEGORY_NAME() {
		return CATEGORY_NAME;
	}
	public void setCATEGORY_NAME(String cATEGORY_NAME) {
		CATEGORY_NAME = cATEGORY_NAME;
	}
	public String getPARENT_ID() {
		return PARENT_ID;
	}
	public void setPARENT_ID(String pARENT_ID) {
		PARENT_ID = pARENT_ID;
	}
	public String getCATEGORY_ORDER() {
		return CATEGORY_ORDER;
	}
	public void setCATEGORY_ORDER(String cATEGORY_ORDER) {
		CATEGORY_ORDER = cATEGORY_ORDER;
	}
	public String getString(String string) {
		return CATEGORY_ORDER;
	}



}
