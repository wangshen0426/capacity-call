package util.pageUtil;

import java.util.HashMap;
import java.util.Map;

public class ShowItem {

	private String cateCode;//所属类别Id
	private String cateTitle;//所属类别名称
	private String code;//id
	private String title;//显示title
	private String url;
	private boolean isHot;
	private boolean isTop;
	private String imgCode;
	private String type;
	private Map<String,Object> custom = new HashMap<String,Object>();//自定义属性

	public String getCateCode() {
		return cateCode;
	}

	public void setCateCode(String cateCode) {
		this.cateCode = cateCode;
	}

	public String getCateTitle() {
		return cateTitle;
	}

	public void setCateTitle(String cateTitle) {
		this.cateTitle = cateTitle;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isHot() {
		return isHot;
	}

	public void setHot(boolean isHot) {
		this.isHot = isHot;
	}

	public boolean isTop() {
		return isTop;
	}

	public void setTop(boolean isTop) {
		this.isTop = isTop;
	}

	public String getImgCode() {
		return imgCode;
	}

	public void setImgCode(String imgCode) {
		this.imgCode = imgCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public Map<String,Object> getCustom(){
		return this.custom;
	}

	public void addCustom(String key,Object value){
		this.custom.put(key, value);
		
	}
	
	@Override
	public String toString(){
		String result = "";
		result = this.getCode() + "," + this.getTitle();
		return result;
	}
}
