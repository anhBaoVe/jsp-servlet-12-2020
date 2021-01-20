package com.laptrinhjavaweb.dao.impl;

import java.util.List;

import com.laptrinhjavaweb.dao.INewDAO;
import com.laptrinhjavaweb.mapper.NewMapper;
import com.laptrinhjavaweb.model.NewModel;

public class NewDAO extends AbstractDAO<NewModel> implements INewDAO {
	@Override
	public List<NewModel> findByCategoryId(Long categoryId) {
		String sql = "SELECT * FROM jspservletjdbc.news WHERE categoryid = ?";
		return query(sql, new NewMapper(), categoryId);
	}

	@Override
	public Long save(NewModel newModel) {
		StringBuilder sql = new StringBuilder( "INSERT INTO news(title,content,categoryId,thumbnail,shortdescription, " );
		sql.append("createddate,createdby,modifieddate,modifiedby)");
		sql.append("VALUES (?,?,?,?,?,?,?,?,?)");
		return insert(sql.toString(), newModel.getTitle(), newModel.getContent(), newModel.getCategoryId(),
				newModel.getThumbnail(),newModel.getShortDescription(),newModel.getCreatedDate(),
				newModel.getCreateBy(),newModel.getModifiedDate(),newModel.getModifiedBy());
	}

	@Override
	public void update(NewModel updateNew) {
		StringBuilder sql = new StringBuilder("UPDATE news SET title = ?, thumbnail= ?, ");
		sql.append("shortdescription = ?, content= ?,categoryid = ?, ");
		sql.append(" modifieddate=?,modifiedby=? WHERE id = ?");
		update(sql.toString(), updateNew.getTitle(), updateNew.getThumbnail(), updateNew.getShortDescription(),
				updateNew.getContent(), updateNew.getCategoryId(),
				updateNew.getModifiedDate(),updateNew.getModifiedBy() ,updateNew.getId());
	}

	@Override
	public void delete(long id) {
		String sql = "DELETE FROM news WHERE id = ?";
		update(sql, id);
	}

	@Override
	public NewModel findOne(Long id) {
		String sql = "SELECT * FROM jspservletjdbc.news WHERE id = ?";
		List<NewModel> news = query(sql, new NewMapper(), id);
		return news.isEmpty() ? null : news.get(0);
	}

}
