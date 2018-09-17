package cn.agilecode.common.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public abstract class KeyValueObjConvertorService implements IObjConvertorService{

	
	protected Map<String,String> keyMaps = new HashMap<String,String>();

	@Override
	@PostConstruct
	public void initConvertor() {
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

	public void setKeyMaps(Map<String, String> keyMaps) {
		this.keyMaps = keyMaps;
	}
}
