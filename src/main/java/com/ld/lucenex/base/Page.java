
package com.ld.lucenex.base;
import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: Page
 * @Description: 分页
 * @author: Myzhang  
 * @date: 2018年5月25日 下午1:58:58
 * @param <T>
 */
public class Page<T> implements Serializable {
	
	private static final long serialVersionUID = -5395997221963176643L;
	
	private List<T> list;				// list result of this page
	private int pageNumber;				// page number
	private int pageSize;				// result amount of this page
	private int totalPage;				// total page
	private int totalRow;				// total row
	
	public static <T> Page<T> newPage(int pageNumber,int pageSize){
		return new Page<>(pageNumber, pageSize);
	}
	
	public Page(int pageNumber, int pageSize) {
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
	}
	
	/**
	 * Constructor.
	 * @param list the list of paginate result
	 * @param pageNumber the page number
	 * @param pageSize the page size
	 * @param totalPage the total page of paginate
	 * @param totalRow the total row of paginate
	 */
	public Page(List<T> list, int pageNumber, int pageSize,int totalRow) {
		int totalPage = (int) (totalRow / pageSize);
		if (totalRow % pageSize != 0) {
			totalPage++;
		}
		this.list = list;
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.totalPage = totalPage;
		this.totalRow = totalRow;
	}
	/**
	 * @param totalRow 要设置的 totalRow
	 */
	public void setTotalRow(int totalRow) {
		this.totalRow = totalRow;
		int totalPage = (int) (totalRow / pageSize);
		if (totalRow % pageSize != 0) {
			totalPage++;
		}
		this.totalPage = totalPage;
	}
	/**
	 * @param list 要设置的 list
	 */
	public void setList(List<T> list) {
		this.list = list;
	}
	
	public Page() {
		
	}
	
	/**
	 * Return list of this page.
	 */
	public List<T> getList() {
		return list;
	}
	
	/**
	 * Return page number.
	 */
	public int getPageNumber() {
		return pageNumber == 0 ?1 : pageNumber;
	}
	
	/**
	 * Return page size.
	 */
	public int getPageSize() {
		return pageSize == 0 ? 10 : pageSize;
	}
	
	/**
	 * Return total page.
	 */
	public int getTotalPage() {
		return totalPage;
	}
	
	/**
	 * Return total row.
	 */
	public int getTotalRow() {
		return totalRow;
	}
	
	public boolean isFirstPage() {
		return pageNumber == 1;
	}
	
	public boolean isLastPage() {
		return pageNumber >= totalPage;
	}
	
	public int getPageNum() {
		return (pageNumber-1) * pageSize;
	}
	
	public String toString() {
		StringBuilder msg = new StringBuilder();
		msg.append("pageNumber : ").append(pageNumber);
		msg.append("\npageSize : ").append(pageSize);
		msg.append("\ntotalPage : ").append(totalPage);
		msg.append("\ntotalRow : ").append(totalRow);
		return msg.toString();
	}
}

