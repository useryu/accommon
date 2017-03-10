package cn.agilecode.common.service;

import java.io.Serializable;
import java.util.List;

import cn.agilecode.common.AppException;
import cn.agilecode.common.pagination.PageContext;

public interface IBaseService<B extends java.io.Serializable, PK extends java.io.Serializable> {
    
    public List<B> getAll();

    public B getById(PK id);

    public Serializable save(B obj) throws AppException;

    public void update(B obj) throws AppException;

    public void saveOrUpdate(B obj) throws AppException;

    public void delete(B obj) throws AppException;
    
    public void delById(PK id) throws AppException;

    public Long getCountAll();

    public Long getCountByExample(B example);

    public PageContext<B> getPageOfDataAll(PageContext<B> pageInfo);
	
    public PageContext<B> getPageOfDataByExample(B example, PageContext<B> pageInfo);

    public List<B> getByExample(B example);
    
    public B findByExample(B example);
}
