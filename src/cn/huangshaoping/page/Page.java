package cn.huangshaoping.page;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页page
 * @author Administrator
 *
 */
public class Page<T> {
	
	/** 当前页号：首页为0 */
	private int pageNo;
	
	/** 每页记录数 */
	private int pageSize;
	
	/** 总记录数 */
	private int totalSize;
	
	/** 总页数 */
	private int totalPage;
	
	/** 记录集合 */
	private List<T> recordList;
	
	public Page() {
		
	}
	
	/**
	 * 对一页数据做分页
	 * @param recordList	一页数据list
	 * @param pageSize
	 * @param pageNo
	 * @param totalSize
	 */
	public Page(List<T> recordList, int pageSize, int pageNo, int totalSize) {
		
		if(recordList == null) {
			this.recordList = new ArrayList<T>();
			this.pageNo = 0;
			this.totalPage = 0;
			this.totalSize = 0;
			return;
		}
		
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.recordList = recordList;
		this.totalSize = totalSize;
		
		if(this.pageSize == 0)
		{
			this.pageSize = 1;
		}
		
		if(this.totalSize == 0)
		{
			this.totalSize = this.recordList != null ? this.recordList.size() : 0;
		}
		
		this.totalPage = ( this.totalSize - 1 ) / this.pageSize + 1;
		
	}
	
	/**
	 * 对所有数据做分页
	 * @param recordList	所有记录list
	 * @param pageSize
	 * @param pageNo
	 */
	public Page(List<T> recordList, int pageSize, int pageNo) {
		
		if(recordList == null) {
			this.recordList = new ArrayList<T>();
			this.pageNo = 0;
			this.totalPage = 0;
			this.totalSize = 0;
			return;
		}
		
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		
		if(this.pageSize <= 0)
		{
			this.pageSize = 10;
		}
		
		this.totalSize = recordList.size();
		
		int fromIndex = pageNo * pageSize;
		int sumIndex = fromIndex + pageSize;
		int toIndex = sumIndex > this.totalSize ? this.totalSize : sumIndex;
		
		// 获取当前页结果集list
		this.recordList = recordList.subList(fromIndex, toIndex);
		
		this.totalPage = ( this.totalSize - 1 ) / this.pageSize + 1;
		
	}
	
	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

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

	public List<T> getRecordList() {
		return recordList;
	}

	public void setRecordList(List<T> recordList) {
		this.recordList = recordList;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	
	
	
}
