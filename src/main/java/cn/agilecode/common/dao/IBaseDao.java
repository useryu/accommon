package cn.agilecode.common.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;

import cn.agilecode.common.pagination.PageContext;

public interface IBaseDao<T , PK extends java.io.Serializable> {
	
    public T getNewInstance();

    public List<T> getAll();

    public T getById(PK id);

    public Serializable save(T obj);

    public void update(T obj);

    public void merge(T obj);
    
    public void saveOrUpdate(T obj);

    public void delete(T obj);

    public Long getCountAll();

    public Long getCountByExample(T example);

    public PageContext<T> getPageOfDataAll(PageContext<T> pageInfo);
    
    public PageContext<T> getPageOfData(Criteria criteria, PageContext<T> pageInfo);
	
    public PageContext<T> getPageOfDataByExample(T example, PageContext<T> pageInfo);

    public List<T> getByExample(T example);
    
    public T getFirstByExample(T example);

	public void delById(PK id);
	
	public T findUniqueBy(String propertyName, Object value);
	
	public List<T> findBy(String propertyName, Object value);
	
	public T findUnique(Criterion... criterions);
	
	public int batchExecute(String hql, Object... values);
	
	public int batchExecute(String hql, Map<String, Object> values);
	
	public <X> X findUnique(String hql, Map<String, Object> values);
	
	public <X> X findUnique(String hql, Object... values);
	
	public <X> List<X> find(String hql, Map<String, Object> values);
	
	public <X> List<X> find(String hql, Object... values);
	
	public List<T> findByIds(List<?> ids);
	
	public void initEntity(T entity);

	public void initEntity(List<T> entityList);
	
}
