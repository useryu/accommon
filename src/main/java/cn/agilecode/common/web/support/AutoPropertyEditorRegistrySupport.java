package cn.agilecode.common.web.support;

import org.springframework.beans.PropertyEditorRegistrySupport;

public class AutoPropertyEditorRegistrySupport extends
		PropertyEditorRegistrySupport {

	public AutoPropertyEditorRegistrySupport() {
		super();
		super.registerDefaultEditors();
		super.registerCustomEditor(java.util.Date.class, new DateEditor());
	}
	
}
