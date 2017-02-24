package util;

import com.cqut.entity.ServerEntity;

import com.cqut.util.CollectMessage4FileNode;

import util.bPlusTree.BPlusTree;
import util.bPlusTree.interfaces.BTree;

public class ServerCache {
	private static final BTree<ServerEntity> bpt = new BPlusTree<ServerEntity>();

	public static ServerEntity getServerInfo(String key) {
		ServerEntity serverInfo = null;
		if (CollectMessage4FileNode.lessThanMin(key)) {
			serverInfo = CollectMessage4FileNode.getNoServer(1);
		} else if (CollectMessage4FileNode.moreThanMax(key)) {
			serverInfo = CollectMessage4FileNode.getNoServer(bpt.size() + 1);
		}
		if (serverInfo == null) {
			serverInfo = bpt.get(key);
		}
		return serverInfo;
	}

	public static void add(ServerEntity s) {
		bpt.insertOrUpdate(s.getMinId(), s);
	}

	public static void reAdd(ServerEntity[] serverEntityList) {
		bpt.clear();
		int length = serverEntityList.length;
		for (int i = 0; i < length; i++) {
			bpt.insertOrUpdate(serverEntityList[i].getMinId(),
					serverEntityList[i]);
		}
	}
}
