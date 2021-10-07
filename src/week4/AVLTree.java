package week4;

public class AVLTree extends BST {

	public AVLTree() {
		super();
	}

	/**
	 * insert c into tree and rotate if the tree is imbalance
	 */
	public void insert(char c) {
		Node r = insert(c, null, root);
		// find x
		Node x = r.parent;
		while (x != null) {
			if (!isBalanced(x)) {
				break;
			}
			x = x.parent;
		}
		Node y = null;

		if (x != null) {
			if (c < x.key) {
				y = x.left;
				if (c < y.key) { // LL
					rotateRight(x);
				} else { // LR
					rotateLeft(y);
					rotateRight(x);
				}
			} else {
				y = x.right;
				if (c > y.key) { // RR
					rotateLeft(x);
				} else { // RL
					rotateRight(y);
					rotateLeft(x);
				}
			}
		}
	}

	public void AVLdelete(char c) {
		Node x = super.delete(c);
		Node y = null;
		Node z = null;
		Node w = null;
		
		while(x!=null) {
			if (!isBalanced(x)) {
				if(getHeight(x.left) >= getHeight(x.right)) {
					y = x.left;
					if(y.left!=null) {
						z = y.left;
						w = rotateRight(x);
					} else {
						z = y.right;
						rotateLeft(y);
						w = rotateRight(x);
					}
				}
				else {
					y = x.right;
					if(y.left!=null) {
						z = y.left;
						rotateRight(y);
						w = rotateLeft(x);
					} else {
						z = y.right;
						w = rotateLeft(x);
					}
				}
				if(w.parent == null) {
					root = w;
				}
				x = w.parent;
			} else {
				x = x.parent;
			}
		}
	}

	
	private Node rotateLeft(Node x) {
		Node y = x.right;
		y.parent = x.parent;
		if (y.parent == null) {
			root = y;
		} else { // parent's link
			if (x == x.parent.left) {
				x.parent.left = y;
			} else {
				x.parent.right = y;
			}
		}
		x.parent = y;
		x.right = y.left;
		if (y.left != null) {
			y.left.parent = x;
		}
		y.left = x;
		return y;
	}

	private Node rotateRight(Node x) {
		Node y = x.left;
		y.parent = x.parent;
		if (y.parent == null) {
			root = y;
		} else { // parent's link
			if (x == x.parent.left) {
				x.parent.left = y;
			} else {
				x.parent.right = y;
			}
		}
		x.parent = y;
		x.left = y.right;
		if (y.right != null) {
			y.right.parent = x;
		}
		y.right = x;
		return y;
	}

	private boolean isBalanced(Node p) {
		if (p == null) {
			return true;
		}
		if (Math.abs(getHeight(p.left) - getHeight(p.right)) <= 1) {
			return true;
		} else {
			return false;
		}
	}

	public static void main(String[] args) {
		int inputSize = 26;
		char[] data = new char[inputSize];

		for (int i = 0; i < inputSize; i++) {
			data[i] = (char) ('A' + i);
		}

		BST bt = new BST();

		for (int i = 0; i < inputSize; i++) {
			bt.insert(data[i]);
		}
		System.out.println("Inintial Skewed Tree");
		bt.showTree();

		System.out.println("Max. Height = " + bt.getHeight());
		System.out.println("IPL = " + bt.getIPL());

		AVLTree bt1 = new AVLTree();
		for (int i = 0; i < inputSize; i++) {
			bt1.insert(data[i]);
		}
		System.out.println("AVL Tree");
		bt1.showTree();
		System.out.println("Max. Height = " + bt1.getHeight());
		System.out.println("IPL = " + bt1.getIPL());
		
		System.out.println("----------------delete test-----------------");
		bt.delete('Q'); bt.delete('S'); bt.delete('R');
		System.out.println("no AVL");
		bt.showTree();
		System.out.println("AVL");
		bt1.AVLdelete('Q'); 
		bt1.AVLdelete('S'); 
		bt1.AVLdelete('R');
		bt1.AVLdelete('A');
		bt1.AVLdelete('C');
		bt1.AVLdelete('E');
		bt1.AVLdelete('G');
		bt1.AVLdelete('B');
		bt1.showTree();
		bt1.AVLdelete('P');
		bt1.showTree();
	}
}
