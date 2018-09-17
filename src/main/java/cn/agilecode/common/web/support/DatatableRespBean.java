package cn.agilecode.common.web.support;

import java.util.List;
import java.util.Map;

import cn.agilecode.common.model.Idenfitier;

//http://datatables.club/manual/server-side.html
/**
 * 
名称			类型		描述
draw			integerJS 	必要。上面提到了，Datatables发送的draw是多少那么服务器就返回多少。 这里注意，作者出于安全的考虑，强烈要求把这个转换为整形，即数字后再返回，而不是纯粹的接受然后返回，这是 为了防止跨站脚本（XSS）攻击。 
recordsTotal	integerJS 	必要。即没有过滤的记录数（数据库里总共记录数） 
recordsFiltered	integerJS 	必要。过滤后的记录数（如果有接收到前台的过滤条件，则返回的是过滤后的记录数） 
data			arrayJS 	必要。表中中需要显示的数据。这是一个对象数组，也可以只是数组，区别在于 纯数组前台就不需要用 columns绑定数据，会自动按照顺序去显示 ，而对象数组则需要使用 columns绑定数据才能正常显示。 注意这个 data的名称可以由 ajaxOption 
 								ajax.dataSrcOption columns.dataSrc 1不定时一讲 columns.dataSrc 2不定时一讲 控制 
error			stringJS 	可选。你可以定义一个错误来描述服务器出了问题后的友好提示 
 * @author lenovo
 *
 */
public class DatatableRespBean {

	private int draw;
	private int recordsTotal;
	private int recordsFiltered;
	private List<Map<String, Object>> data;
	private String error;
	
	public int getDraw() {
		return draw;
	}
	public void setDraw(int draw) {
		this.draw = draw;
	}
	public int getRecordsTotal() {
		return recordsTotal;
	}
	public void setRecordsTotal(int recordsTotal) {
		this.recordsTotal = recordsTotal;
	}
	public int getRecordsFiltered() {
		return recordsFiltered;
	}
	public void setRecordsFiltered(int recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public List<Map<String, Object>> getData() {
		return data;
	}
	public void setData(List<Map<String, Object>> data) {
		this.data = data;
	}
}
