package cn.agilecode.common;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.lang.StringUtils;

public class CommonUtil {
	/**
	 * 随机获取UUID字符串(无中划线)
	 * 
	 * @return UUID字符串
	 */
	public static String getUUID() {
		String uuid = UUID.randomUUID().toString();
		return uuid.substring(0, 8) + uuid.substring(9, 13) + uuid.substring(14, 18) + uuid.substring(19, 23) + uuid.substring(24);
	}
	
	/**
	 * 随机获取字符串
	 * 
	 * @param length
	 *            随机字符串长度
	 * 
	 * @return 随机字符串
	 */
	public static String getRandomString(int length) {
		if (length <= 0) {
			return "";
		}
		char[] randomChar = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a', 's', 'd',
				'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b', 'n', 'm' };
		Random random = new Random();
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			stringBuffer.append(randomChar[Math.abs(random.nextInt()) % randomChar.length]);
		}
		return stringBuffer.toString();
	}

	/**
	 * 根据指定长度 分隔字符串
	 * 
	 * @param str
	 *            需要处理的字符串
	 * @param length
	 *            分隔长度
	 * 
	 * @return 字符串集合
	 */
	public static List<String> splitString(String str, int length) {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < str.length(); i += length) {
			int endIndex = i + length;
			if (endIndex <= str.length()) {
				list.add(str.substring(i, i + length));
			} else {
				list.add(str.substring(i, str.length() - 1));
			}
		}
		return list;
	}

	/**
	 * 将字符串List转化为字符串，以分隔符间隔.
	 * 
	 * @param list
	 *            需要处理的List.
	 *            
	 * @param separator
	 *            分隔符.
	 * 
	 * @return 转化后的字符串
	 */
	public static String toString(List<String> list, String separator) {
		StringBuffer stringBuffer = new StringBuffer();
		for (String str : list) {
			stringBuffer.append(separator + str);
		}
		stringBuffer.deleteCharAt(0);
		return stringBuffer.toString();
	}

	public static String getUser() {
		return null;
	}
	
	/**
	 * 获得指定长度的随机字母
	 * 
	 * @param len
	 *            指定的字符串长度
	 * @param isUpperCase
	 *            是否转换为大写
	 * @return 返回随机字符串
	 */
	public static String getRandomNO(int len, boolean isUpperCase) {
		final int maxCount = 26;
		int i;
		int count = 0;
		StringBuffer sb = new StringBuffer("");
		Random random = new Random();

		char chars[] = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
				'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
				'x', 'y', 'z' };

		while (count < len) {
			i = Math.abs(random.nextInt(maxCount));
			if (i >= 0 && i < chars.length) {
				sb.append(chars[i]);
				count++;
			}
		}

		return isUpperCase ? sb.toString().toUpperCase() : sb.toString();
	}
	
	/**
	 * 根据日期(如2012-9-21)来进行截取
	 * @param date
	 * @return str={2012,9,21};
	 */
	public static String[] getYearMonthDay(String date){
		String[] str = date.split("-");
		return str;
	}

	/**
	 * 检验用户名
	 * 不能包含特殊字符，规则如下
	 * @param name
	 * @return
	 */
	public static boolean specialName(String name) {
		// 全文匹配
		String[] name_have = new String[] { "ruanko", "admin", "broadengate",
				"null", "ruanku", "fuck" };
		// 开头匹配
		String[] name_begin = new String[] {};
		// 结尾匹配
		String[] name_last = new String[] { "111" };
		// 精确匹配
		String[] name_exact = new String[] { "renchenwen", "shuohao789",
				"meinvkan", "fxcm-chinese", "waiyy111", "xiangnian369",
				"liujueqiang", "longseo1" };

		// 校验字符串是否含有敏感字
		for (int i = 0; i < name_have.length; i++) {
			if (name.indexOf(name_have[i]) != -1) {
				return false;
			}
		}

		// 校验字符串是否以敏感字开头
		for (int i = 0; i < name_begin.length; i++) {
			if (StringUtils.startsWith(name,name_begin[i])) {
				return false;
			}
		}

		// 校验字符串是否以敏感字结尾
		for (int i = 0; i < name_last.length; i++) {
			if (StringUtils.endsWith(name,name_last[i])) {
				return false;
			}
		}

		// 精确校验
		for (int i = 0; i < name_exact.length; i++) {
			if (name.equals(name_exact[i])) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 转换单位大小
	 */
	public static String getSizeType(double size) 
	{
		String sizeType = null;
		if(size == 0.0){
			return "0KB";
		}
		if(size <= 1024)
		{
			size = size/1024;
			BigDecimal BD =  new BigDecimal(size);  
			size = BD.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();  
			sizeType = String.valueOf(size)+"KB";
		}
		if(size > 1024  && size <= 1048576)
		{
			size = size/1024;
			BigDecimal BD =  new BigDecimal(size);  
			size = BD.setScale(0,   BigDecimal.ROUND_HALF_UP).doubleValue();  
			int sizeInt = (int) size;
			sizeType = String.valueOf(sizeInt)+"KB";
		}
		else if( size > 1048577 && size <= 1073741824)
		{
			size = size/1048576;
			BigDecimal BD =  new BigDecimal(size);  
			size = BD.setScale(0,   BigDecimal.ROUND_HALF_UP).doubleValue();  
			int sizeInt = (int) size;
			sizeType = String.valueOf(sizeInt)+"MB";
		}
		return sizeType;
	}
	
	/**
	 * 时长转换
	 * @return
	 */
	public static String getTotalTime(String totalTime) {
		if(StringUtils.isEmpty(totalTime)){
			return "0";
		}
		if(totalTime.indexOf(":")==-1){
			return totalTime;
		}
		String sp []=totalTime.split(":");
		int time=0;
		try{
			int hover=Integer.parseInt(sp[0]);
			int mu=Integer.parseInt(sp[1]);
			int se=Integer.parseInt(sp[2]);
			time+=hover*3600;
			time+=mu*60;
			time+=se;
		}catch(Exception e){
			e.printStackTrace();
			return "0";
		}
		return String.valueOf(time);
	}
	
	
	// 过滤特殊字符   
	 public static String StringFilter(String   str)   throws   PatternSyntaxException   {      
         String regEx="[`~!@#$%^&*()+=|{}':;',//[//][\\\\]-×÷≠.<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、≤≥≡≌≈±∽∵∴∞°Ωϖω∫ρμλθγβα•π%∝!℃ ③②①∉∈⊇⊃⊆⊂∀∧￢∅∪∩⌒↓↑|☉∠⊥△ξ∃∁？ ]";   
         Pattern   p   =   Pattern.compile(regEx);      
         Matcher   m   =   p.matcher(str);      
         return   m.replaceAll("").trim();      
         }     
	 
	 public static String[] StringSplit(String str) {    
		 if(null!=str){
			 String regEx="[`~!@#$%^&*()+=|{}':;',//[//][\\\\]-×÷≠.<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、≤≥≡≌≈±∽∵∴∞°Ωϖω∫ρμλθγβα•π%∝!℃ ③②①∉∈⊇⊃⊆⊂∀∧￢∅∪∩⌒↓↑|☉∠⊥△ξ∃∁？ ]";
			 return str.split(regEx);
		 }
		 return null;    
         }    
	  /**
	  　　* 过滤，并组合过滤后的字符串
	  　　*/
	 public static  String getStr(String context){
		 String result = context;
		 String[] newStr =  {"sqrt","root","frac","begin","sum","overline","widehat","overrightarrow","→","left","array","end","right","{l}"};
		 for(int i=0;i<newStr.length;i++){
			 result = result.replace(newStr[i], "");
		 }
		 return result;
	 }

	 
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	public static  List getKeyList(String content){
		 List list = new ArrayList();
		 for(int i=0;i<content.length();i++){
			 list.add(content.substring(i, i+1));
		 }
		 return list;
	 }

	 /**
	  * 尝试将字符串转化为Long型，若遇到不能转换(如输入字符串为null、空字符串或者包含数字之外的字符)，则返回null
	  * @param str 待转换的字符串
	  * @return
	  */
	 public static synchronized Long parseLong(String str){
		 if(StringUtils.isNotBlank(str)){
			 try {
					return Long.parseLong(str.trim());
				} catch (Exception e) {
					return null;
				}
		 }
		 return null;
	 }
			 
	/**
	 * 生成订单号(cid+日期+时间+毫秒位移)
	 * @param args
	 */
	public synchronized static String getOrderNum() {
		class Inner 
		{
			public String getDoubleDigit(int number) {
				return (number<10) ? ("0"+number)
						: String.valueOf(number);
			}
			
			public String getThreeDigit(int number) {
				String str;
				if(number<10) {
					str = "00"+number;
				} else if(number<100) {
					str = "0"+number;
				} else {
					str = String.valueOf(number);
				}
				return str;
			}
		}
		Inner inner = new Inner();
		StringBuffer sb = new StringBuffer();
		Calendar now = Calendar.getInstance();
		String strYear = String.valueOf(now.get(Calendar.YEAR)).substring(2, 4);
		String strMonth = inner.getDoubleDigit(now.get(Calendar.MONTH)+1);
		String strDate = inner.getDoubleDigit(now.get(Calendar.DATE));
		String strHour = inner.getDoubleDigit(now.get(Calendar.HOUR_OF_DAY));
		String strMinute = inner.getDoubleDigit(now.get(Calendar.MINUTE));
		String strSecond = inner.getDoubleDigit(now.get(Calendar.SECOND));
		String strMilli = inner.getThreeDigit(now.get(Calendar.MILLISECOND));
		return sb.append(strYear).append(strMonth).append(strDate).append(strHour).append(strMinute).append(strSecond).append(strMilli).toString();
	}
	
	/**
	 * 截取描述中包含的HTML代码
	 * @param htmlStr
	 * @return
	 */
    public static String delHTMLTag(String htmlStr){
    	if(StringUtils.isEmpty(htmlStr)){
    		return "";
    	}
        String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式
        String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式
        String regEx_html="<[^>]+>"; //定义HTML标签的正则表达式
        Pattern p_script=Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE);
        Matcher m_script=p_script.matcher(htmlStr);
        htmlStr=m_script.replaceAll(""); //过滤script标签
        Pattern p_style=Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE);
        Matcher m_style=p_style.matcher(htmlStr);
        htmlStr=m_style.replaceAll(""); //过滤style标签
        Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);
        Matcher m_html=p_html.matcher(htmlStr);
        htmlStr=m_html.replaceAll(""); //过滤html标签
        htmlStr=htmlStr.replaceAll("&nbsp;",""); //过滤html标签
       return htmlStr.trim(); //返回文本字符串
    }
    
		

	/**
	 * strDate yyyy-MM-dd hh:mm:ss  to Date
	 * @param vikiEndDate
	 * @return
	 */
	public static Date strDateToDate(String ss) {
		if(StringUtils.isNotBlank(ss)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date dd = null;
			try {
				dd = sdf.parse(ss);
			} catch (Exception e) {
				dd = null;
			}
			return dd;
		}else
		{
			return null;
		}
	}
    
		
	public static String stripNonValidXMLCharacters(String in) {
        StringBuffer out = new StringBuffer(); 
        char current; 

        if (in == null || ("".equals(in))) return "";
        for (int i = 0; i < in.length(); i++) {
            current = in.charAt(i); 
            if ((current == 0x9) ||
                (current == 0xA) ||
                (current == 0xD) ||
                ((current >= 0x20) && (current <= 0xD7FF)) ||
                ((current >= 0xE000) && (current <= 0xFFFD)) ||
                ((current >= 0x10000) && (current <= 0x10FFFF)))
                out.append(current);
        }
        return out.toString();
    }
	
	/**
	 * 获取选项
	 * @return
	 */
	public static StringBuffer[] getOptions() 
	{
		StringBuffer[] strs = {new StringBuffer("A．"),new StringBuffer("B．"),new StringBuffer("C．"),new StringBuffer("D．"),new StringBuffer("E．")};
		return strs;
	}

	public static String subStringTaskLink(String taskLink) 
	{
		return taskLink.substring(taskLink.indexOf("!")+1, taskLink.lastIndexOf(".action"));
	}  
	
	/**
	 * 判断str是否为null或空字符串，若是，则返回空字符串，否则返回str.trim()
	 * @param str
	 * @return
	 */
	public static String objToStr(String str){
		if (null == str || "".equals(str)) {
			return "";
		} 
		else {
			return str.trim();
		}
		
	}
}
