package cn.agilecode.common.web.support;

//http://datatables.club/manual/server-side.html
/**
 * 
名称						类型		描述
draw						integerJS 	绘制计数器。这个是用来确保Ajax从服务器返回的是对应的（Ajax是异步的，因此返回的顺序是不确定的）。 要求在服务器接收到此参数后再返回（具体看下面） 
start						integerJS 	第一条数据的起始位置，比如0代表第一条数据 
length						integerJS 	告诉服务器每页显示的条数，这个数字会等于返回的 data集合的记录数，可能会大于因为服务器可能没有那么多数据。这个也可能是-1，代表需要返回全部数据(尽管这个和服务器处理的理念有点违背) 
search[value]				stringJS 	全局的搜索条件，条件会应用到每一列（ searchable需要设置为 true ） 
search[regex]				booleanJS 	如果为 true代表全局搜索的值是作为正则表达式处理，为 false则不是。 注意：通常在服务器模式下对于大数据不执行这样的正则表达式，但这都是自己决定的 
order[i][column]			integerJS 	告诉后台那些列是需要排序的。 i是一个数组索引，对应的是 columns数组，从0开始 
order[i][dir]				stringJS 	告诉后台列排序的方式， desc 是降序 asc升序 
columns[i][data]			stringJS 	columns 绑定的数据源，由 columns.dataOption 定义。 
columns[i][name]			stringJS 	columns 的名字，由 columns.nameOption 定义。 
columns[i][searchable]		booleanJS 	标记列是否能被搜索,为true代表可以，否则不可以，这个是由 columns.searchableOption 控制 
columns[i][orderable]		booleanJS 	标记列是否能排序,为 true代表可以，否则不可以，这个是由 columns.orderableOption 控制 
columns[i][search][value]	stringJS 	标记具体列的搜索条件 
columns[i][search][regex]	booleanJS 	特定列的搜索条件是否视为正则表达式， 如果为 true代表搜索的值是作为正则表达式处理，为 false则不是。 注意：通常在服务器模式下对于大数据不执行这样的正则表达式，但这都是自己决定的 

 * @author lenovo
 *
 */
public class DatatableReqBean {

	private int draw;
	private int start;
	private int length;
	private String searchValue;
	private boolean searchRegex;
	private String orderColumn;
	private String orderDir;
	private String advSearch;//高级检索，客户端会生成一个JSON对象字串，在后台要解析成对象
	
	public int getDraw() {
		return draw;
	}
	public void setDraw(int draw) {
		this.draw = draw;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public String getSearchValue() {
		return searchValue;
	}
	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}
	public boolean isSearchRegex() {
		return searchRegex;
	}
	public void setSearchRegex(boolean searchRegex) {
		this.searchRegex = searchRegex;
	}
	public String getOrderDir() {
		return orderDir;
	}
	public void setOrderDir(String orderDir) {
		this.orderDir = orderDir;
	}
	public String getOrderColumn() {
		return orderColumn;
	}
	public void setOrderColumn(String orderColumn) {
		this.orderColumn = orderColumn;
	}
	public String getAdvSearch() {
		return advSearch;
	}
	public void setAdvSearch(String advSearch) {
		this.advSearch = advSearch;
	}
	@Override
	public String toString() {
		return "DatatableReqBean [draw=" + draw + ", start=" + start + ", length=" + length + ", searchValue="
				+ searchValue + ", searchRegex=" + searchRegex + ", orderColumn=" + orderColumn + ", orderDir="
				+ orderDir + ", advSearch=" + advSearch + "]";
	}

	
}
