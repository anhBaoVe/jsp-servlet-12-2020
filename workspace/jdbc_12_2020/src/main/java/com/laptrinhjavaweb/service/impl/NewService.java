package com.laptrinhjavaweb.service.impl;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

import javax.inject.Inject;

import com.laptrinhjavaweb.dao.impl.NewDAO;
import com.laptrinhjavaweb.model.NewModel;
import com.laptrinhjavaweb.service.INewService;

public class NewService implements INewService {
	
	@Inject
	private NewDAO newDao;

	@Override
	public List<NewModel> findByCategoryId(Long categoryId) {
		return newDao.findByCategoryId( categoryId);
	}

	@Override
	public NewModel save(NewModel newModel) {
		newModel.setCreateBy("pdc");
		newModel.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		Long newId = newDao.save(newModel);
		return newDao.findOne(newId);
	}



	@Override
	public NewModel update(NewModel updateNew) {
		NewModel oldNew = newDao.findOne(updateNew.getId());
		updateNew.setCreateBy(oldNew.getCreateBy());
		updateNew.setCreatedDate(oldNew.getCreatedDate());
		updateNew.setModifiedBy(updateNew.getModifiedBy());
		updateNew.setModifiedDate(new Timestamp(System.currentTimeMillis()));
		newDao.update(updateNew);
		return newDao.findOne(updateNew.getId());
	}

	@Override
	public void delete(long[] ids) {
		//1. Delete comment
		//2. Delte news
		for (long id : ids) {
			newDao.delete(id);
		}
	}

}
