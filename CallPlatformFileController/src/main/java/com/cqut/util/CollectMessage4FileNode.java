package com.cqut.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import util.ServerCache;

import com.cqut.entity.ServerEntity;

public class CollectMessage4FileNode {
	private static Map<String, ServerEntity> serverList = new TreeMap<String, ServerEntity>();
	private static volatile String minName;
	private static volatile String maxName;
	
	
    public static ServerEntity getNoServer(int index) {
    	Iterator<Entry<String,ServerEntity>> iterator = serverList.entrySet().iterator();
    	if(iterator.hasNext() == false) {
    		return null;
    	}
    	Entry<String,ServerEntity> entry = null;
    	int i = 0;
		while(iterator.hasNext()) {
			i++;
			entry = iterator.next();
			if(i == index) {
				return entry.getValue();
			}
		}
		return entry.getValue();
		
	}

	public static boolean lessThanMin(String min) {
		if(minName==null) {
			return true;
		}
		return min.compareTo(minName) < 0;
	}
	
	
	public static boolean moreThanMax(String max) {
		if(maxName==null) {
			return true;
		}
		return max.compareTo(maxName) > 0;
	}
	
	public static void add2Map(ServerEntity s) {
		synchronized (serverList) {
			serverList.put(s.getServerName(), s);
			if(s.getMinId()!=null && s.getMaxId() != null) {
				ServerCache.add(s);
				if(minName==null) {
					minName = s.getMinId();
				}
				else {
					if(minName.compareTo( s.getMinId()) > 0) {
						minName = s.getMinId();
					}
				}
				if(maxName == null) {
					maxName = s.getMaxId();
				}
				else {
					if(maxName.compareTo( s.getMaxId()) < 0) {
						maxName = s.getMaxId();
					}
				}
			}
			
		}
	}
}
