package com.eform.util;

import java.util.List;
import java.util.Map;

public class DataTables {

	private Integer sEcho;
	
	private String sSearch;
	private List<String> projections;
    private Map<String, Object> filters;
	
	private List<String> mDataProp;
	
	private Integer iSortingCols;
    private List<Integer> iSortCol;
    private List<String> sSortDir;
    
	private Integer iDisplayStart;
	private Integer iDisplayLength;
	
	private Long iTotalRecords;
	private Long iTotalDisplayRecords;
	
	private List aaData;
	
	public DataTables extract(List aaData, Long iTotalRecords) {
		this.aaData = aaData;
		this.iTotalRecords = this.iTotalDisplayRecords = iTotalRecords;
		
		return this;
	}
	
	public Integer getsEcho() {
		return sEcho;
	}
	public void setsEcho(Integer sEcho) {
		this.sEcho = sEcho;
	}
	public String getsSearch() {
		return sSearch;
	}
	public void setsSearch(String sSearch) {
		this.sSearch = sSearch;
	}
    public List<String> getProjections() {
		return projections;
	}
	public void setProjections(List<String> projections) {
		this.projections = projections;
	}
	public Map<String, Object> getFilters() {
        return filters;
    }
    public void setFilters(Map<String, Object> filters) {
        this.filters = filters;
    }
	public Integer getiDisplayStart() {
		return iDisplayStart;
	}
	public void setiDisplayStart(Integer iDisplayStart) {
		this.iDisplayStart = iDisplayStart;
	}
	public Integer getiDisplayLength() {
		return iDisplayLength;
	}
	public void setiDisplayLength(Integer iDisplayLength) {
		this.iDisplayLength = iDisplayLength;
	}
	public Long getiTotalRecords() {
		return iTotalRecords;
	}
	public void setiTotalRecords(Long iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}
	public Long getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}
	public void setiTotalDisplayRecords(Long iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}
	public List getAaData() {
		return aaData;
	}
	public void setAaData(List aaData) {
		this.aaData = aaData;
	}

	public List<String> getmDataProp() {
		return mDataProp;
	}

	public void setmDataProp(List<String> mDataProp) {
		this.mDataProp = mDataProp;
	}

	public Integer getiSortingCols() {
		return iSortingCols;
	}

	public void setiSortingCols(Integer iSortingCols) {
		this.iSortingCols = iSortingCols;
	}

	public List<Integer> getiSortCol() {
		return iSortCol;
	}

	public void setiSortCol(List<Integer> iSortCol) {
		this.iSortCol = iSortCol;
	}

	public List<String> getsSortDir() {
		return sSortDir;
	}

	public void setsSortDir(List<String> sSortDir) {
		this.sSortDir = sSortDir;
	}
	
}
