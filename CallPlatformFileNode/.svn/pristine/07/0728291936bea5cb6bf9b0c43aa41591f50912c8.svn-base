package util.pageUtil;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
public abstract class Page {

	private int columnIndex = 0;
	private Map<String, ShowColumn> showColumnMap = new HashMap<String, ShowColumn>();

	public Map<String, ShowColumn> getShowColumnsMap() {
		return this.showColumnMap;
	}
	
	public ShowColumn getShowColumn(String columnName){
		return this.showColumnMap.get(columnName);
	}

	public void setShowColumnsMap(Map<String, ShowColumn> showColumnMap) {
		this.showColumnMap = showColumnMap;
	}

	public void addShowColumn(String key, ShowColumn column) {
		column.setName(key);
		this.showColumnMap.put(key, column);
		column.setComIndex(columnIndex++);
	}

	public void loadData() {
		ShowColumn showColumn = null;
		Long timer = null;
		Collection<ShowColumn> col = showColumnMap.values();
		for (Iterator<ShowColumn> it = col.iterator(); it.hasNext();) {
			showColumn = (ShowColumn) it.next();
			timer = System.currentTimeMillis();
			showColumn.loadItems();
			//
		}
	}
	
	public String getHtml(String columnName){
		String html = "";
		ShowColumn column = this.getShowColumn(columnName);
		if(null != column){
			html = column.getOutputHtml();
		}
		return html;
	}
	
	public String getScript(String columnName){
		String script = "";
		ShowColumn column = this.getShowColumn(columnName);
		if(null != column){
			script = column.getOutputScript();
		}
		return script;
	}

}
