package util.bPlusTree;

import java.util.Random;

import util.bPlusTree.interfaces.BTree;

public class BPlusTree<T> implements BTree<T> {
	/** 根节点 */
	protected Node<T> root;

	/** 阶数，M值 */
	protected int order = 6;
	/** 叶子节点的链表头 */
	protected Node<T> head;

	private int sizeCount = 0;

	public Node<T> getHead() {
		return head;
	}

	public void setHead(Node<T> head) {
		this.head = head;
	}

	public Node<T> getRoot() {
		return root;
	}

	public void setRoot(Node<T> root) {
		this.root = root;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	@Override
	public T get(Comparable key) {
		return root.get(key);
	}

	@Override
	public void remove(Comparable key) {
		if (root.remove(key, this)) {
			sizeCount--;
		}

	}

	@Override
	public void clear() {
		root = new Node<T>(true, true);
		head = root;
		sizeCount = 0;

	}

	@Override
	public void insertOrUpdate(Comparable key, T obj) {
		if (root.insertOrUpdate(key, obj, this)) {
			sizeCount++;
		}

	}

	public BPlusTree() {
		root = new Node<T>(true, true);
		head = root;
	}

	public BPlusTree(int order) {
		if (order < 3) {
			System.out.print("order must be greater than 2");
			return;
		}
		this.order = order;
		root = new Node<T>(true, true);
		head = root;
	}

	// 测试
	public static void main(String[] args) {
		/*
		 * BPlusTree tree = new BPlusTree(); tree.insertOrUpdate(1, 1);
		 * tree.insertOrUpdate(8, 2); tree.insertOrUpdate(100, 3); int search =
		 * 0; System.out.print(tree.get(search));
		 */
	}

	@Override
	public int size() {
		return sizeCount;
	}

}
