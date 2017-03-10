package cn.agilecode.common.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;


public abstract class DbQueryObjConvertorService implements	IObjConvertorService {
	private static Logger log = Logger.getLogger(DbQueryObjConvertorService.class);
	
	@Autowired
	protected HibernateTemplate hibernateTemplate;
	
	protected String sql;

	protected Map<String,String> keyMaps = new HashMap<String,String>();
	
	@SuppressWarnings("unchecked")
	@Override
	@PostConstruct
	public void initConvertor() {
		setSql();
		Session newSession = null;
		try {
			if(sql==null) {
				throw new Exception("sql not inited, this convertor will not work");
			}
			newSession = hibernateTemplate.getSessionFactory().openSession();
			SQLQuery query = newSession.createSQLQuery(sql);
			List<Object[]> results = query.list();
			for(Object[] r:results) {
				keyMaps.put(r[0].toString(), r[1].toString());
			}
		}catch(Throwable e) {
			log.warn("init convertor faild, the sql is:"+sql);
			e.printStackTrace();
		}finally {
			if(newSession!=null) {
				newSession.close();
			}
		}
	}

	@Override
	public String convertor(String value) {
		if(this.keyMaps.containsKey(value)) {
			return this.keyMaps.get(value);
		}
		return value;
	}

	@Override
	@PreDestroy
	public void cleanConvertor() {
		this.keyMaps.clear();
		this.keyMaps=null;
	}

	public abstract void setSql();

}
