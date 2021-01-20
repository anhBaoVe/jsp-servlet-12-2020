package com.laptrinhjavaweb.dao;

import java.util.List;

import com.laptrinhjavaweb.mapper.RowMapper;

public interface GenericDAO<T> {
	<T> List<T> query(String sql,RowMapper<T> rowMapper,Object...paramaters);//Object...paramaters):multi param
	void update (String sql, Object...paramaters);
	Long insert(String sql,Object...paramaters);
}
