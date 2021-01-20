package com.laptrinhjavaweb.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.laptrinhjavaweb.dao.GenericDAO;
import com.laptrinhjavaweb.mapper.RowMapper;

public class AbstractDAO<T> implements GenericDAO<T> {

	// kết nối tới mql bằng jdbc
	public Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/jspservletjdbc";
			return DriverManager.getConnection(url, "root", "root");
		} catch (ClassNotFoundException | SQLException e) {
			return null;
		}
	}

	@Override
	public <T> List<T> query(String sql, RowMapper<T> rowMapper, Object... paramaters) {
		List<T> result = new ArrayList<T>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = getConnection();
			statement = connection.prepareStatement(sql);
			setParamater(statement,paramaters);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				result.add(rowMapper.mapRow(resultSet));
			}
			return result;
		} catch (Exception e) {
			return null;
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (resultSet != null) {
					resultSet.close();
				}
			} catch (SQLException e) {
				return null;
			}
		}

	}

	private void setParamater(PreparedStatement statement, Object... paramaters) {
		for(int i =0;i<paramaters.length;++i) {
			try {
				Object paramater=new Object();
				paramater=paramaters[i];
				int index=i+1;
				if(paramater instanceof Long) {
					statement.setLong(index, (long) paramater);
				}else if(paramater instanceof String) {
					statement.setString(index, (String)paramater);
				}else if(paramater instanceof Timestamp) {
					statement.setTimestamp(index, (Timestamp) paramater);
				}else if(paramater ==null) {
					statement.setNull(index, Types.NULL);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
	}

	@Override
	public void update(String sql, Object... paramaters) {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = getConnection();
			statement = connection.prepareStatement(sql);
			connection.setAutoCommit(false);
			setParamater(statement, paramaters);
			statement.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			if(connection!=null) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}finally {
			try {
				if (connection != null) {
					connection.close();
				}
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public Long insert(String sql, Object... paramaters) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Long id= null;
		try {
			connection = getConnection();
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			setParamater(statement, paramaters);
			statement.executeUpdate();
			resultSet = statement.getGeneratedKeys();
			if(resultSet.next()) {
				id = resultSet.getLong(1);
			}
			connection.commit();
			return id;
		} catch (SQLException e) {
			if(connection!=null) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					return null;
				}
			}
		}finally {
			try {
				if (connection != null) {
					connection.close();
				}
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				return null;
			}
		}
		return null;
	}

}
