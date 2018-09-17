package cn.agilecode.common.model;

import java.lang.reflect.Field;

import org.apache.commons.lang.builder.ToStringBuilder;

import cn.agilecode.common.web.support.DatatableReqBean;


public abstract class AbstractModel implements java.io.Serializable {
    
    private static final long serialVersionUID = 2035013017939483936L;

    public static String[][] getHeadersFromModel(@SuppressWarnings("rawtypes") Class modelClass) {
		Field[] fields = modelClass.getDeclaredFields();
		int excludeFieldNum = 0;
		for(Field field:fields) {
			if(!field.isAnnotationPresent(ShowInList.class)) {
				excludeFieldNum++;
			}
		}
		String[][] showFields = new String[fields.length-excludeFieldNum][2];
		int showFieldNum = 0;
		for(Field field:fields) {
			if(field.isAnnotationPresent(ShowInList.class)) {
				ShowInList showInListAnno = field.getAnnotation(ShowInList.class);
				int curIndex = showFieldNum++;
				showFields[curIndex][0]=showInListAnno.chname();
				showFields[curIndex][1]=field.getName();
			}
		}
		return showFields;
	}
    

    public static String getSourColumnFromModel(@SuppressWarnings("rawtypes") Class modelClass, DatatableReqBean datatableReqBean) {
		Field[] fields = modelClass.getDeclaredFields();
		int excludeFieldNum = 0;
		for(Field field:fields) {
			if(!field.isAnnotationPresent(ShowInList.class)) {
				excludeFieldNum++;
			}
		}
		Field[] showFields = new Field[fields.length-excludeFieldNum];
		int showFieldNum = 0;
		for(Field field:fields) {
			if(field.isAnnotationPresent(ShowInList.class)) {
				showFields[showFieldNum++]=field;
			}
		}
		int clientNum = Integer.parseInt(datatableReqBean.getOrderColumn());
		if(clientNum>=showFields.length) {
			return null;
		}
		return showFields[clientNum].getName();
	}

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
