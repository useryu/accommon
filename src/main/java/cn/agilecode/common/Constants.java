package cn.agilecode.common;

public class Constants {
	
	/**
	 * 用户分页个数
	 */
	public static final int DEFAULT_PAGE_SIZE = 10;
	
	/**
	 * -1 - 未删除标识 sql数据库 int类型的默认值为0， 当0作为参数的值进行查询时，该字段将不会被当做查询条件的一部分，故把标识改成-1
	 */
	public static final int NOT_DELETED = -1; 
	
	/**
	 * 1 - 删除标识
	 */	
	public static final int IS_DELETED = 1;
	
	/**
	 * -1 - 代表作品标识
	 */		
	public static final int IS_REPRESENT = -1;
	
	/**
	 * 1 - 非代表作品标识
	 */		
	public static final int NOT_REPRESENT = 1;
	
	/**
	 * 1 - 代表艺术家封面图片在首页展示
	 */
	public static final int IS_SHOWININDEX = 1;
	
	/**
	 * -1 - 代表艺术家封面图片不在首页展示
	 */
	public static final int NOT_SHOWININDEX = -1;

	/**
	 * 0 - 学习研究-全部
	 */
	public static final int XXYJ_XUEXIYANJIU= 0;	
	
	/**
	 * 1 - 学习研究-学术研讨
	 */
	public static final int XXYJ_XUESHUYANTAO = 1;
	
	/**
	 * 2 - 学习研究-版画史论
	 */
	public static final int XXYJ_BANHUASHILUN = 2;
	
	/**
	 * 3 - 学习研究-版画知识
	 */
	public static final int XXYJ_BANHUAZHISHI = 3;
	
	/**
	 * 4 - 学习研究-视野观点
	 */
	public static final int XXYJ_SHIYEGUANDIAN = 4;	
	
	
	/**
	 * 5 - 本地资讯
	 */
	public static final int LOCAL_NEWES = 5;
	
	/**
	 * 6 - 国内资讯
	 */
	public static final int DOMESTIC_NEWES = 6;
	
	/**
	 * 7 - 国际 资讯
	 */
	public static final int INTERNATIONAL_NEWS = 7;
	
	/**
	 * 8 - 头条 资讯
	 */
	public static final int TOUTIAO_NEWS = 8;
	
	/**
	 * 1 - 资讯中文标识
	 */
	public static final int NEWS_ZH = 1;
	
	/**
	 * 2 - 资讯英文标识
	 */
	public static final int NEWS_EN = 2;
	
	/**
	 * 国际化语言标识 中文 “zh”
	 */
	public static final String LANGUAGE_CHINESE = "zh";
	
	/**
	 * 国际化语言标识 中文 “cn” 此常量只在双年展报名时使用慎用
	 */
	public static final String LANGUAGE_CHINESE_CN = "cn";	
	
	/**
	 * 国际化语言标识 英文 “en”
	 */
	public static final String LANGUAGE_ENGLISH = "en";	
	
	/**
	 * 审核状态通过
	 */
	public static final int AUDIT_TONGGUO = 1;	
	/**
	 * 审核状态不通过
	 */
	public static final int AUDIT_BUTONGGUO = -1;
	/**
	 * 房屋分配待分配
	 */
	public static final int ASSIGNED_DAIFENPEI = -1;	
	/**
	 * 房屋分配待已分配（待入住）
	 */
	public static final int ASSIGNED_YIFENPEI = 1;
	/**
	 * 房屋已入住
	 */
	public static final int ASSIGNED_YIRUZHU = 2;
	/**
	 * 房屋退房
	 */
	public static final int ASSIGNED_CKECKOUT = 3;
	
	/**
	 * 性别--男
	 */
	public static final int BOY = 1;
	/**
	 * 性别--女
	 */
	public static final int GIRL = 2;
	
	/**
	 * 录入状态
	 */
	public static final String  ENTERING = "-1";
	/**
	 * 移交状态
	 */
	public static final String  TRANSFER = "1";
	/**
	 * 交接状态（已交接）
	 */
	public static final String  HAND = "2";
	/**
	 * 画作类型--艺术家
	 */
	public static final String  paintType_art = "1";
	/**
	 * 画作类型--捐赠
	 */
	public static final String  paintType_contribution = "2";
	/**
	 * 画作类型--其他
	 */
	public static final String  paintType_other  = "3";
	
	
	/**
	 * 版画类型--木板区
	 */
	public static final int Board_area = 1;	
	/**
	 * 版画类型--铜板区
	 */
	public static final int Copper_plate_area = 2;
	/**
	 * 版画类型--丝网区
	 */
	public static final int Wire_mesh_area = 3;
	/**
	 * 版画类型--石板区
	 */
	public static final int The_area = 4;
	
	/**
	 * 设备异动状态-封存中
	 */
	public static final int EQUIPMENT_SEALED = -1;
	
	/**
	 * 设备异动状态-使用中
	 */	
	public static final int EQUIPMENT_USED = 1;
	
	/**
	 * 设备检修状态-正常
	 */		
	public static final int EQUIPMENT_NORMAL = -1;

	/**
	 * 设备检修状态-检修中
	 */			
	public static final int EQUIPMENT_OVERHAUL = 1;

	/**
	 * 设备检修状态-故障
	 */			
	public static final int EQUIPMENT_HITCH = 2;
}
