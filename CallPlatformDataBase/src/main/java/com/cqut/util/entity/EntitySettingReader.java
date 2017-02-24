package com.cqut.util.entity;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.cqut.util.BeanUtil;
import com.cqut.util.StringUtil;

public class EntitySettingReader {

	public static void readEntitySetting(String entityName) {
		Map<String, EntitySetting> settings = null;
			try {
				InputStream in = EntitySettingReader.class.getResourceAsStream("/com/cqut/entity/entitySetting/" + entityName + ".xml");
				//
				if(in != null){
					SAXReader reader = new SAXReader();
					Document document = reader.read(in);
					Element rootElm = document.getRootElement();
					List<?> nodes = rootElm.elements("relation");
					settings = new HashMap<String, EntitySetting>(nodes.size());
					EntitySetting setting = null;
					for(Object item : nodes){
						setting = createEntitySetting(entityName, (Element)item);
						if(setting != null){
							settings.put(/*setting.getSourceFiled()*/setting.getClassAlias(), setting);
						}
					}
					BeanUtil.addEntitySetting(entityName, settings);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	private static EntitySetting createEntitySetting(String entityName, Element element){
		EntitySetting setting = new EntitySetting();
		setting.setSourceEntity(entityName);
		setting.setSourceFiled(element.element("source").getTextTrim());
		
		Element target = element.element("target");
		String[] values = StringUtil.split(target.getTextTrim(), "."); 
		if(values.length == 2){
			setting.setTargetEntity(values[0]);
			setting.setTargetField(values[1]);
			setting.setClassAlias(target.attribute("classAlias").getText());
			Attribute linkType = target.attribute("linkType");
			if(linkType != null){
				setting.setLinkType(linkType.getText());
			}
			Attribute simpleText = target.attribute("simpleText");
			if(simpleText != null){
				setting.setSimpleText(simpleText.getText());
			}
			return setting;
		}
		return null;
	}
}
