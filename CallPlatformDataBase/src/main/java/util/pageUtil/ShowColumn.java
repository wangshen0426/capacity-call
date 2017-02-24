package util.pageUtil;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ShowColumn {
	
	private ShowColumnLayout layout;
	private Map<String,ShowColumnCard> showColumnCards;
	private int comIndex = 0;
	private String outputHTML = null;
	private String outputScript = null;
	private String[] cssFiles = null;
	private String name;
    
	public void clearCache() {
		outputHTML = null;
		outputScript = null;
		cssFiles = null;
	}

	public ShowColumn(ShowColumnLayout layout) {
		layout.setShowColumn(this);
		this.layout = layout;
		showColumnCards = new HashMap<String,ShowColumnCard>();
	}
	
	public ShowColumn(String name,ShowColumnLayout layout){
		this(layout);
		this.setName(name);
	}

	public void setComIndex(int index) {
		comIndex = index;
	}
	
	public int getComIndex(){
		return this.comIndex;
	}
	
	public ShowColumnCard getShowColumnCard(String columnName){
		return this.showColumnCards.get(columnName);
	}

	public Map<String,ShowColumnCard> getShowColumnCards(){
		return this.showColumnCards;
	}
	
	public void addShowColumnCard(String key,ShowColumnCard showColumnCard){
		this.showColumnCards.put(key,showColumnCard);
	}

	public void loadItems() {
		//clearCache();
		absLoadItems();
	}

	private void absLoadItems(){
		ShowColumnCard showColumnCard = null;
		Collection<ShowColumnCard> col = showColumnCards.values();
		
		for(Iterator<ShowColumnCard> it = col.iterator();it.hasNext();){
			showColumnCard = (ShowColumnCard)it.next();
			showColumnCard.setShowItems(showColumnCard.initItems());
		}
		outputHTML = layout.getOutputHtml();
		outputScript = layout.getOutputScript();
	}

	public String getOutputHtml() {
		return outputHTML != null ? outputHTML : (outputHTML = layout.getOutputHtml());
	}
	
	public void setOutputHtml(String outputHTML){
		this.outputHTML = outputHTML;
	}

	public String getOutputScript() {
		return outputScript != null ? outputScript:layout.getOutputScript();
	}
	
	public void setOutputScript(String outputScript){
		this.outputScript = outputScript;
	}

	public String[] getCssFiles() {
		return cssFiles != null ? cssFiles:layout.getCssFiles();
	}
	
	public void setCssFiles(String[] cssFiles){
		this.cssFiles = cssFiles;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
