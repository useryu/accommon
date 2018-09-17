package cn.agilecode.common.dao;

import org.hibernate.Session;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import cn.agilecode.common.pagination.PageContext;
import cn.agilecode.common.util.StrKit;
import cn.agilecode.common.web.support.AdvSearchCondition;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

public abstract class BaseHibernateDao<T extends java.io.Serializable, PK extends java.io.Serializable>
		implements IBaseDao<T, PK> {

	protected Logger logger = LoggerFactory.getLogger(BaseHibernateDao.class);
	
	@Autowired
	protected HibernateTemplate hibernateTemplate;
	private Class<T> entityClass;
	private String defaultOrder = "id";
	private boolean defaultOrderAsc = true;

	@SuppressWarnings("unchecked")
	public BaseHibernateDao() {
		this.entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
	}

	protected Session getCurrentSession() {
		SessionFactory sessionFactory = hibernateTemplate.getSessionFactory();
		Session currentSession = sessionFactory.getCurrentSession();
		return currentSession;
	}

	public T getNewInstance() {
		try {
			return getQueryClass().newInstance();
		} catch (InstantiationException e) {
			throw new RuntimeException("Error creating new instance of : " + getQueryClass().getName(), e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("Error creating new instance of : " + getQueryClass().getName(), e);
		}
	}

	protected Criteria getBaseCriteria() {
		return getCurrentSession().createCriteria(getQueryClass());
	}

	@SuppressWarnings("unchecked")
	public List<T> getAll() {
		return addDefaultOrder(getBaseCriteria()).list();
	}

	@SuppressWarnings("unchecked")
	public T getById(PK id) {
		T entity = (T) getCurrentSession().get(entityClass, id);
		return entity;
	}

	public Serializable save(T obj) {
		Serializable id = getCurrentSession().save(obj);
		return id;
	}

	public void update(T obj) {
		getCurrentSession().update(obj);
	}

	public void merge(T obj) {
		getCurrentSession().merge(obj);
	}

	public void saveOrUpdate(T obj) {
		getCurrentSession().saveOrUpdate(obj);
	}

	public void delete(T obj) {
		getCurrentSession().delete(obj);
	}

	public void delById(PK id) {
		Object entity = this.getById(id);
		getCurrentSession().delete(entity);
	}

	public Long getCountAll() {
		return getCount(getBaseCriteria());
	}

	public Long getCountByExample(T example) {
		return getCount(getExampleCriteria(example));
	}

	protected Long getCount(Criteria criteria) {
		criteria.setProjection(Projections.rowCount());

		Long result = (Long) criteria.uniqueResult();
		if (result == null) {
			return 0L;
		} else {
			return result;
		}
	}

	public Criteria createCriteria(Criterion... criterions) {
		Criteria criteria = this.getBaseCriteria();
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}

	protected Criteria getExampleCriteria(T example) {
		Example hibExample = Example.create(example);
		hibExample.excludeZeroes();
		return getBaseCriteria().add(hibExample);
	}

	public PageContext<T> getPageOfDataAll(PageContext<T> pageInfo) {
		return getPageOfData(getBaseCriteria(), pageInfo);
	}

	@SuppressWarnings("unchecked")
	public List<T> queryByCriteria(Criterion criterion) {
		return this.getBaseCriteria().add(criterion).list();
	}

	@SuppressWarnings("unchecked")
	public List<T> queryByCriterias(Criterion... criterions) {
		Criteria criteria = this.getBaseCriteria();
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria.list();
	}

	public PageContext<T> getPageOfDataByExample(T example, PageContext<T> pageInfo) {
		return getPageOfData(getExampleCriteria(example), pageInfo);
	}

	@SuppressWarnings("unchecked")
	public PageContext<T> getPageOfData(Criteria criteria, PageContext<T> pageInfo) {
		pageInfo.setItems(addPaginationInfo(criteria, pageInfo).list());
		return pageInfo;
	}

	protected Long getTotalNumber(Criteria criteria) {
		Projection rowCountProjection = Projections.rowCount();
		Criteria projections = criteria.setProjection(rowCountProjection);
		Object totalSizeObj = projections.uniqueResult();
		Long totalSize = ((Long) totalSizeObj);
		return totalSize;
	}
	
	protected Long getTotalNumber() {
		return getTotalNumber(this.getBaseCriteria());
	}
	
	protected Criteria addPaginationInfo(Criteria criteria, PageContext<T> pageInfo) {
		// 保存总记录数
		Projection rowCountProjection = Projections.rowCount();
		Criteria projections = criteria.setProjection(rowCountProjection);
		Object totalSizeObj = projections.uniqueResult();
		Integer totalSize = ((Long) totalSizeObj).intValue();
		pageInfo.setTotalSize(totalSize);
		criteria.setProjection(null);
		criteria.setResultTransformer(Criteria.ROOT_ENTITY);
		
		String column = pageInfo.getSortColumn();
		if (StrKit.isBlank(column)) {
			addDefaultOrder(criteria);
		} else {
			boolean sortAsc = pageInfo.isSortAsc();
			addOrder(criteria, column, sortAsc);
		}
		
		criteria.setFirstResult(pageInfo.getStart());
		criteria.setMaxResults(pageInfo.getMaxQueryResults());
		
		return criteria;
	}

	protected Criteria addOrder(Criteria criteria, String column, boolean sortAsc) {
		if (sortAsc) {
			criteria.addOrder(Order.asc(column));
		} else {
			criteria.addOrder(Order.desc(column));
		}

		return criteria;
	}

	protected Criteria addDefaultOrder(Criteria criteria) {
		return addOrder(criteria, getDefaultOrder(), isDefaultOrderAsc());
	}

	public T getFirstByExample(T example) {
		List<T> list = this.getByExample(example);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<T> getByExample(T example) {
		Criteria crit = getExampleCriteria(example);
		addDefaultOrder(crit);

		return crit.list();
	}

	public Class<T> getQueryClass() {
		return entityClass;
	}

	public void setQueryClass(Class<T> queryClass) {
		this.entityClass = queryClass;
	}

	public String getDefaultOrder() {
		return defaultOrder;
	}

	public void setDefaultOrder(String defaultOrder) {
		this.defaultOrder = defaultOrder;
	}

	public boolean isDefaultOrderAsc() {
		return defaultOrderAsc;
	}

	public void setDefaultOrderAsc(boolean defaultOrderAsc) {
		this.defaultOrderAsc = defaultOrderAsc;
	}

	@SuppressWarnings("unchecked")
	public T findUniqueBy(String propertyName, Object value) {
		Criterion criterion = Restrictions.eq(propertyName, value);
		return (T) createCriteria(criterion).uniqueResult();
	}

	/**
	 * 按属性查找对象列表, 匹配方式为相等
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public List<T> findBy(String propertyName, Object value) {
		Criterion criterion = Restrictions.eq(propertyName, value);
		return this.queryByCriteria(criterion);
	}

	@SuppressWarnings("unchecked")
	public T findUnique(Criterion... criterions) {
		return (T) createCriteria(criterions).uniqueResult();
	}
	
	public int batchExecute(String hql, Map<String, Object> values) {
		return createQuery(hql, values).executeUpdate();
	}
	
	public int batchExecute(String hql, Object... values) {
		return createQuery(hql, values).executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	public <X> X findUnique(String hql, Map<String, Object> values) {
		return (X) createQuery(hql, values).uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public <X> X findUnique(String hql, Object... values) {
		return (X) createQuery(hql, values).uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public <X> List<X> find(String hql, Map<String, Object> values) {
		return createQuery(hql, values).list();
	}
	
	@SuppressWarnings("unchecked")
	public <X> List<X> find(String hql, Object... values) {
		return createQuery(hql, values).list();
	}
	
	public List<T> findByIds(List<?> ids) {
		return this.queryByCriteria(Restrictions.in(getIdName(), ids));
	}
	
	public void initEntity(T entity) {
		Hibernate.initialize(entity);
	}

	public void initEntity(List<T> entityList) {
		for (T entity : entityList) {
			Hibernate.initialize(entity);
		}
	}

	private String getIdName() {
		ClassMetadata meta = this.hibernateTemplate.getSessionFactory().getClassMetadata(entityClass);
		return meta.getIdentifierPropertyName();
	}

	/**
	 * 根据查询 HQL 与参数列表创建 Query 对象
	 * 
	 * @param queryString
	 * @param values
	 * @return
	 */
	public Query createQuery(String queryString, Map<String, Object> values) {
		Query query = getCurrentSession().createQuery(queryString);

		if (values != null) {
			query.setProperties(values);
		}

		return query;
	}

	/**
	 * 根据查询 HQL 与参数列表创建 Query 对象
	 * 
	 * @param queryString
	 * @param values
	 *            : 数来那个可变的参数, 按顺序绑定
	 * @return
	 */
	public Query createQuery(String queryString, Object... values) {
		Query query = getCurrentSession().createQuery(queryString);

		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}

		return query;
	}
	
	public AdvSearchCondition[] parseAdvSearchJson(String advSearchJson) {
		ObjectMapper mapper = new ObjectMapper();  
		AdvSearchCondition[] conditionList = null;
		try {
			conditionList = mapper.readValue(advSearchJson, AdvSearchCondition[].class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conditionList;
	}
	
}
