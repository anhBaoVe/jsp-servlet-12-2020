package com.laptrinhjavaweb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.laptrinhjavaweb.model.NewModel;

public class NewMapper implements RowMapper<NewModel> {

	@Override
	public NewModel mapRow(ResultSet rs) {
		try {
			NewModel news = new NewModel();
			if (rs.getLong("id") != 0) {
				news.setId(rs.getLong("id"));
			}
			if (rs.getString("title") != null) {
				news.setTitle(rs.getString("title"));
			}
			if (rs.getString("content") != null) {
				news.setContent(rs.getString("content"));
			}
			if (rs.getLong("categoryid") != 0) {
				news.setCategoryId(rs.getLong("categoryid"));
			}
			if (rs.getString("shortdescription") != null) {
				news.setShortDescription(rs.getString("shortdescription"));
			}
			if (rs.getString("thumbnail") != null) {
				news.setThumbnail(rs.getString("thumbnail"));
			}
			if (rs.getString("createdby") != null) {
				news.setCreateBy(rs.getString("createdby"));
			}
			if (rs.getTimestamp("createddate") != null) {
				news.setCreatedDate(rs.getTimestamp("createddate"));
			}
			if (rs.getString("modifiedby") != null) {
				news.setModifiedBy(rs.getString("modifiedby"));
			}
			if (rs.getTimestamp("modifieddate") != null) {
				news.setModifiedDate(rs.getTimestamp("modifieddate"));
			}

			return news;
		} catch (SQLException e) {
			return null;
		}

	}

}
