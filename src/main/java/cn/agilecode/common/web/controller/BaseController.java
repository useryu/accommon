package cn.agilecode.common.web.controller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import cn.agilecode.common.model.ModelListConvertor;
import cn.agilecode.common.model.ShowInList;
import cn.agilecode.common.pagination.PageContext;
import cn.agilecode.common.service.IConvertorService;
import cn.agilecode.common.service.IObjConvertorService;
import cn.agilecode.common.web.support.DateEditor;
public abstract class BaseController implements ApplicationContextAware {
	
	protected static final Logger logger = Logger.getLogger(BaseController.class);
	
	protected ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public Object getBean(String beanName) {
		return applicationContext.getBean(beanName);
	}

	public <T> T getBean(String beanName, Class<T> clazz) {
		return applicationContext.getBean(beanName, clazz);
	}
	
	protected static final String SERVER_ALERT_KEY = "serverAlert";
	
	@Autowired
	protected IConvertorService convertorService;

	protected void setAlert(HttpServletRequest req, String value) {
		logger.debug(value);
		this.setAttr(req, SERVER_ALERT_KEY, value);
	}
	
	protected void setMenuActiveToCurrentPath(HttpServletRequest req) {
		this.setMenuActive(req, null);
	}
	
	protected void setMenuActive(HttpServletRequest req, String value) {
		String servletPath = value==null?req.getServletPath():value;
		this.setAttr(req, "activeMenuIndex", req.getContextPath()+servletPath);
	}
	
	protected void setAttr(HttpServletRequest req, String name, Object value) {
		req.setAttribute(name, value);
	}

	protected String getAttr(HttpServletRequest req, String name, String defaultValue) {
		String value = req.getParameter(name);
		return value==null?defaultValue:value;
	}
	protected List<Map<String, Object>> genTableDataFromModel(@SuppressWarnings("rawtypes") PageContext pageInfo, HttpServletRequest req, String modelFiledName) throws Exception {
		return this.genTableDataFromModelWithActions(pageInfo, modelFiledName, req);
	}

	/**
	 * @deprecated
	 * @param pageInfo
	 * @param modelFiledName
	 * @param req
	 * @param actions
	 * @param actionLables
	 * @param includeColumnNames
	 * @return
	 * @throws Exception
	 */
	protected List<String[]> genTableDataFromModelWithActionsAndSpecificProperties(@SuppressWarnings("rawtypes") PageContext pageInfo, String modelFiledName, HttpServletRequest req, String[] actions, String[] actionLables, List<String> includeColumnNames) throws Exception {
		List<String[]> datas = new ArrayList<String[]>();
		for(Object model:pageInfo.getItems()) {
			Field[] fields = model.getClass().getDeclaredFields();
			String[] data = new String[includeColumnNames.size()+actions.length];
			String id = "";
			int index = 0;
			for(Field field:fields) {
				if(field.getName().equalsIgnoreCase("id")) {
					id = BeanUtils.getProperty(model, field.getName());
				}
				if(!includeColumnNames.contains(field.getName())) {
					continue;
				}
				String value = BeanUtils.getProperty(model, field.getName());
				ShowInList showInList = field.getAnnotation(ShowInList.class);
				if(showInList!=null) {
					if(StringUtils.isNotBlank(showInList.property())) {
						value = BeanUtils.getProperty(model, field.getName()+"."+showInList.property());
					}
					if(showInList.needConvert()) {
						String convertor = modelFiledName+"."+field.getName();
						value = convertorService.convertor(convertor, value);
					}
				}
				data[index++] = value==null?"":value;
			}
			String contextPath = req.getContextPath();
			for(int actionIndex=0;actionIndex<actions.length;actionIndex++) {
				data[index++]="<a class=\"edit\" href=\""+contextPath+"/"+modelFiledName+"/"+actions[actionIndex]+"/"+id +"\">"+actionLables[actionIndex]+"</a>";
			}
			datas.add(data);
		}
		return datas;
	}
	
	protected List<Map<String,Object>> genTableDataFromModelWithActions(@SuppressWarnings("rawtypes") PageContext pageInfo, String modelFiledName, HttpServletRequest req) throws Exception {
		List<Map<String,Object>> datas = new ArrayList<Map<String,Object>>();
		for(Object model:pageInfo.getItems()) {
			Field[] fields = model.getClass().getDeclaredFields();
			int excludeFieldNum = 0;
			for(Field field:fields) {
				if(!field.isAnnotationPresent(ShowInList.class)) {
					excludeFieldNum++;
				}
			}
			Map<String,Object> data = new HashMap<String,Object>(fields.length-excludeFieldNum);
			String id = "";
			for(Field field:fields) {
				if(field.getName().equalsIgnoreCase("id")) {
					id = BeanUtils.getProperty(model, field.getName());
				}
				if(!field.isAnnotationPresent(ShowInList.class)) {
					continue;
				}
				ShowInList showInList = field.getAnnotation(ShowInList.class);
				String value = BeanUtils.getProperty(model, field.getName());
				if(StringUtils.isNotBlank(showInList.property())) {
					value = BeanUtils.getProperty(model, field.getName()+"."+showInList.property());
				}
				if(showInList.needConvert()) {
					if(field.isAnnotationPresent(ModelListConvertor.class)) {
						ModelListConvertor c = field.getAnnotation(ModelListConvertor.class);
						String convertorBeanId = c.beanId();
						try {
							IObjConvertorService objConvertor = this.getBean(convertorBeanId, IObjConvertorService.class);
							value = objConvertor.convertor(value);
						}catch(Throwable e) {
							logger.warn("Field's convert not found definition, use Message convertor, field:"+field);
							String convertor = modelFiledName+"."+field.getName();
							value = convertorService.convertor(convertor, value);
						}
					} else {
						String convertor = modelFiledName+"."+field.getName();
						value = convertorService.convertor(convertor, value);
					}
				}
				value=value==null?"":value;
				data.put(field.getName(), value);
			}
			data.put("id", id);
			datas.add(data);
		}
		return datas;
	}

	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DateEditor());
	}
}
