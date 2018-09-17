package cn.agilecode.common.pagination;

import java.util.List;

import cn.agilecode.common.Constants;

/**
 * 查询返回所有记录的分页实现.
 * 
 * @version 1.0, 2010-4-3
 */
public class PageContext<E> {

	private int start = 0;// 当前起始记录
	private int pageSize = Constants.DEFAULT_PAGE_SIZE;// 每页包含的记录数
	private int totalSize;
	private List<E> items;// 每次只包含当前页数据
	private String sortColumn;
	private boolean sortAsc = false;
	private String searchString; //全文检索关键字
	private String advSearchJson; //高级检索对象

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

	public List<E> getItems() {
		return items;
	}

	public void setItems(List<E> items) {
		this.items = items;
	}

	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}

	public boolean isSortAsc() {
		return sortAsc;
	}

	public void setSortAsc(boolean sortAsc) {
		this.sortAsc = sortAsc;
	}

	public int getMaxQueryResults() {
		return this.pageSize;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public String getAdvSearchJson() {
		return advSearchJson;
	}

	public void setAdvSearchJson(String advSearchJson) {
		this.advSearchJson = advSearchJson;
	}

}
