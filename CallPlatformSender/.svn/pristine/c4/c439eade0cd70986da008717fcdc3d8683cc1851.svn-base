package util.pageUtil;

public abstract class ShowColumnCard {
	
	private ShowItem[] items;

	public abstract ShowItem[] initItems();

	public ShowItem[] getShowItems() {
		if(null == items)
			items = initItems();
		return items;
	}

	public void setShowItems(ShowItem[] items) {
		this.items = items;
	}

	public abstract ShowItem[] getImgItems();

	public abstract ShowItem[] getTopItems();
	
}
