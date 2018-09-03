package com.wms.newwmsapp.model;

import java.io.Serializable;

public class FilterModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4328389179917703043L;
	public ConditionModel Condition;
	public PageFilterModel PageFilter;
	public ConditionModel getCondition() {
		return Condition;
	}
	public void setCondition(ConditionModel condition) {
		Condition = condition;
	}
	public PageFilterModel getPageFilter() {
		return PageFilter;
	}
	public void setPageFilter(PageFilterModel pageFilter) {
		PageFilter = pageFilter;
	}
}
