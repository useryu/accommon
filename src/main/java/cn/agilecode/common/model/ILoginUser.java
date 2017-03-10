package cn.agilecode.common.model;

public interface ILoginUser {

	void setId(long parseLong);

	void setAccount(String username);
	
	String getAccount();

}
