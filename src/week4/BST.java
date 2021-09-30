package week4;

import java.util.ArrayDeque;
import java.util.Deque;

public class BST {

	public class Node {
		char key;
		Node parent;
		Node left;
		Node right;

		public Node(char c) {
			key = c;
		}

		public String toString() {
			return "" + key + "(" + getHeight(this) + ")";
		}
	}

	Node root;
	int numNode;

	public BST() {
		this.root = null;
	}

	public void insert(char data) {
		insert(data, null, root); // null <= parent of root
	}

	/**
	 * insert x into tree. recursively search place with given Nodes (parent & r)
	 * 
	 * @param x
	 * @param parent
	 * @param r
	 * @return the node inserted
	 */
	protected Node insert(char x, Node parent, Node r) {
		if (r == null) {
			if (parent == null) { // root
				root = insertNode(x, null);
				return root;
			} else { // leaf
				if (x < parent.key) { // left child
					parent.left = insertNode(x, parent);
					return parent.left;
				} else if (x > parent.key) {
					parent.right = insertNode(x, parent);
					return parent.right;
				} else { // never happens
					return null;
				}
			}
		} else {
			if (x < r.key) {
				return insert(x, r, r.left);
			} else if (x > r.key) {
				return insert(x, r, r.right);
			} else {
				return null;
			}
		}
	}

	private Node insertNode(char x, Node parent) {
		Node newNode = new Node(x);
		newNode.parent = parent;
		numNode++;
		return newNode;
	}

	public Node search(Node startNode, char data) {
		Node p = startNode;
		if (p == null || p.key == data) {
			return p;
		} else if (data < p.key) {
			return this.search(p.left, data);
		} else {
			return this.search(p.right, data);
		}
	}

	public Node delete(char data) {
		Node r = search(root, data);
		if (r != null) {
			numNode--;
			return delete(r);
		} else {
			return null;
		}
	}

	/**
	 * Delete node r. if r is root of tree, return null.
	 * 
	 * @param r the node to be deleted
	 * @return parent of deleted node
	 */
	private Node delete(Node r) {
		if (r.parent == null) { // r=root
			root = deleteNode(r);
			return null;
		} else if (r == r.parent.left) {
			r.parent.left = deleteNode(r);
			return r.parent;
		} else {
			r.parent.right = deleteNode(r);
			return r.parent;
		}
	}

	/**
	 * delete node and get parent of deleted node(or successor).
	 * 
	 * @param r
	 * @return
	 */
	private Node deleteNode(Node r) {
		// case 1 : no child
		if (r.left == null && r.right == null) {
			return null;
		}
		// case 2 : 1 child
		if (r.left == null && r.right != null) {
			r.right.parent = r.parent;
			return r.right;
		} else if (r.left != null && r.right == null) {
			r.left.parent = r.parent;
			return r.left;
		} else { // case 3: 2 children
			Node s = successor(r);
			r.key = s.key;
			// successor just have right child
			if (s == s.parent.left) {
				s.parent.left = s.right;
			} else {
				s.parent.right = s.right;
			}
			return s.parent;
		}
	}

	private Node successor(Node v) {
		if (v == null) {
			return null;
		}
		Node p = v.right;
		while (p.left != null) {
			p = p.left;
		}
		return p;
	}

	private Node predecessor(Node v) {
		if (v == null) {
			return null;
		}
		Node p = v.left;
		while (p.right != null) {
			p = p.right;
		}
		return p;
	}

	public void showTree() {
		if (root == null) {
			return;
		}
		Deque<Node> que = new ArrayDeque<>();
		que.add(root);
		int depthLevel = 0;
		while (que.peek() != null) {
			Deque<Node> temp = new ArrayDeque<>();
			System.out.print("Depth-level " + depthLevel + "  :  ");
			while (que.peek() != null) {
				temp.add(que.poll());
			}
			while (temp.peek() != null) {
				Node e = temp.poll();
				System.out.print(e.toString() + " ");
				if (e.left != null) {
					que.add(e.left);
				}
				if (e.right != null) {
					que.add(e.right);
				}
			}
			System.out.println();
			depthLevel++;
		}
	}

	private String toString(Node t) {
		return this.inorder(t);
	}

	private String inorder(Node t) {
		if (t == null) {
			return "";
		} else {
			return this.inorder(t.left) + " " + t.toString() + " " + this.inorder(t.right);
		}

	}

	///////////////////////////////////////////////////////////
	public int getHeight() {
		return getHeight(root);
	}

	protected int getHeight(Node r) {
		if (r == null) {
			return -1;
		} else {
			return 1 + Math.max(getHeight(r.left), getHeight(r.right));
		}
	}

	public int getIPL() {
		int count = 0;
		return getIPL(root, count);
	}

	private int getIPL(Node r, int count) {
		if (r == null) {
			return count;
		} else {
			count++;
			int lCount = getIPL(r.left, count);
			int rCount = getIPL(r.right, count);
			return count + lCount + rCount;
		}
	}

	public static void main(String[] args) {
		char[] data = { 'M', 'Y', 'U', 'N', 'G', 'I', 'S', 'W' };
		BST bt = new BST();

		for (int i = 0; i < data.length; i++) {
			bt.insert(data[i]);
		}

		System.out.print("\nTree Created : ");

		bt.showTree();

		bt.delete('S');
		System.out.print("\nAfterr deleting 'S' : ");
		bt.showTree();
		bt.delete('G');
		System.out.print("\nAfterr deleting 'G' : ");
		bt.showTree();
		bt.delete('U');
		System.out.print("\nAfterr deleting 'U' : ");
		bt.showTree();

	}
}
