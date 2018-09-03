package com.wms.newwmsapp.model;

import java.io.Serializable;
import java.util.List;

public class ConditionModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4726244709107386723L;
	public List<ConditionFilterModel> Filters;
	public List<SortingMode> Sorting;
	public List<ConditionFilterModel> getFilters() {
		return Filters;
	}
	public void setFilters(List<ConditionFilterModel> filters) {
		Filters = filters;
	}
	public List<SortingMode> getSorting() {
		return Sorting;
	}
	public void setSorting(List<SortingMode> sorting) {
		Sorting = sorting;
	}
}
