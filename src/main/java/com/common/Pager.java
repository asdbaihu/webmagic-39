package com.common;

import java.io.Serializable;
import java.util.List;



/**
 * 分页对象
 * @author
 *
 * @param <T> 返回集合成员类型
 */
public class Pager<T> implements Serializable{

	private static final long serialVersionUID = 2144446255527259094L;

	public static final Integer MAX_PAGE_SIZE = 5000;// 每页最大记录数限制
	
	private Boolean usePager=true;//是否启用分页 默认启用
	private int offset = 0;// 当前偏移量
	private int limit = 50;// 每页记录数
	
	private String order;// 排序方式
	private String sort;// 排序字段名
	
	private String realSort;//正是字段名 solr需要用

	private Long total;// 总记录数
	private List<T> rows;// 返回结果
	

	public int getOffset() {
		return offset;
	}


	public void setOffset(int offset) {
		this.offset = offset;
	}


	public int getLimit() {
		return limit;
	}


	public void setLimit(int limit) {
		if(limit>MAX_PAGE_SIZE){
			limit=MAX_PAGE_SIZE;
		}
		this.limit = limit;
	}


	public String getSort() {
		StringBuilder result = new StringBuilder();
	    if (sort != null && sort.length() > 0) {
	        // 将第一个字符处理成大写
	        result.append(sort.substring(0, 1).toUpperCase());
	        // 循环处理其余字符
	        for (int i = 1; i < sort.length(); i++) {
	            String s = sort.substring(i, i + 1);
	            // 在大写字母前添加下划线
	            if (s.equals(s.toUpperCase()) && !Character.isDigit(s.charAt(0))&&Character.isLetter(s.charAt(0))) {
	                result.append("_");
	            }
	            // 其他字符直接转成大写
	            result.append(s.toUpperCase());
	        }
	    }else{
	    	return null;
	    }
	    return result.toString().toLowerCase();
	}

	public String getRealSort() {
	    return realSort;
	}
	
	public void setRealSort(String realSort){
		this.realSort = realSort;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public void setSort(String sort) {
		this.sort = sort;
		if(this.realSort==null||"".equals(this.realSort)||this.realSort.isEmpty()){
			this.realSort = sort;
		}
	}

	public Boolean getUsePager() {
		return usePager;
	}

	public void setUsePager(Boolean usePager) {
		this.usePager = usePager;
	}
}