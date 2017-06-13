package com.mydemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Service
public class BaseService<T>{

	@Autowired
	private Mapper<T> mapper;
	
	public Mapper<T> getMapper() {
		return mapper;
	}


	public void setMapper(Mapper<T> mapper) {
		this.mapper = mapper;
	}


	@Transactional
	public T get(Object o) {
		return mapper.selectByPrimaryKey(o);
	}
	
	@Transactional
	public int save(T o) {
		return mapper.insert(o);
	}
	
	@Transactional
	public int update(T o) {
		return mapper.updateByPrimaryKey(o);
	}
	
	@Transactional
	public List<T> getAllList() {
		return mapper.selectAll();
	}
}
