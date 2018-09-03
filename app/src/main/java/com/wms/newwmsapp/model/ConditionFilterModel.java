package com.wms.newwmsapp.model;

import java.io.Serializable;

public class ConditionFilterModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6299533174906651158L;
	public String Name;
	public String Value;
	public String Filter;
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getValue() {
		return Value;
	}
	public void setValue(String value) {
		Value = value;
	}
	public String getFilter() {
		return Filter;
	}
	public void setFilter(String filter) {
		Filter = filter;
	}
}
