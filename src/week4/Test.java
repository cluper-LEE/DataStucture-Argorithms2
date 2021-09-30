package week4;

public class Test extends BST {

	public Test() {
		super();
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

	public void rotateTest(int n) {
		for (int i = 0; i < n; i++) {
			Node p = root;
			if (p != null) {
				if (p.right != null) {
					rotateLeft(p);
				} else {
					if (p.left != null) {
						rotateRight(p);
					}					
				}
			}
		}
	}

	public static void main(String[] args) {
		int inputSize = 26;
		char[] data = new char[inputSize];

		for (int i = 0; i < inputSize; i++) {
			data[i] = (char) ('A' + i);
		}

		Test bt = new Test();

		for (int i = 0; i < inputSize; i++) {
			bt.insert(data[i]);
		}
		System.out.println("Inintial Skewed Tree");
		bt.showTree();

		System.out.println("Max. Height = " + bt.getHeight());
		System.out.println("IPL = " + bt.getIPL());

		bt.rotateTest(5);
		System.out.println("After a few rotations");
		bt.showTree();

		System.out.println("Max. Height = " + bt.getHeight());
		System.out.println("IPL = " + bt.getIPL());
	}
}
