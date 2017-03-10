package cn.agilecode.common.service;

import java.io.Serializable;
import java.util.List;

import cn.agilecode.common.AppException;
import cn.agilecode.common.dao.IBaseDao;
import cn.agilecode.common.pagination.PageContext;

public abstract class BaseService<M extends java.io.Serializable, PK extends java.io.Serializable>
		implements IBaseService<M, PK> {

	protected IBaseDao<M, PK> baseDao;

	public void setBaseDao(IBaseDao<M, PK> baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public List<M> getAll() {
		return baseDao.getAll();
	}

	@Override
	public M getById(PK id) {
		return baseDao.getById(id);
	}

	@Override
	public Serializable save(M obj) throws AppException{
		return baseDao.save(obj);
	}

	@Override
	public void update(M obj) throws AppException{
		baseDao.update(obj);
	}

	@Override
	public void saveOrUpdate(M obj) throws AppException{
		baseDao.saveOrUpdate(obj);
	}

	@Override
	public void delete(M obj) throws AppException{
		baseDao.delete(obj);
	}

	@Override
	public void delById(PK id) throws AppException{
		baseDao.delById(id);
	}

	@Override
	public Long getCountAll() {
		return baseDao.getCountAll();
	}

	@Override
	public Long getCountByExample(M example) {
		return baseDao.getCountByExample(example);
	}

	@Override
	public PageContext<M> getPageOfDataAll(PageContext<M> pageInfo) {
		return baseDao.getPageOfDataAll(pageInfo);
	}

	@Override
	public PageContext<M> getPageOfDataByExample(M example,
			PageContext<M> pageInfo) {
		return baseDao.getPageOfDataByExample(example, pageInfo);
	}

	@Override
	public List<M> getByExample(M example) {
		return baseDao.getByExample(example);
	}
	
	@Override
	public M findByExample(M example) {
		return baseDao.getFirstByExample(example);
	}
	
}
