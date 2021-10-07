package week5;

public class TreeDSet extends DSet{
	
	public TreeDSet() {
		rank = -1;
		key = -1;
		parent = this;
	}
	
	public DSet findSet(TreeDSet node) {
		if(node.parent != node) {
			node.parent = findSet(node.parent);
		}
		return node.parent;
	}
	
	public DSet union(TreeDSet other) {
		DSet u = findSet(this);
		DSet v = findSet(other);
		if(u.rank>v.rank) {
			v.parent = u;
			return u;
		} else if (u.rank<v.rank) {
			u.parent = v;
			return v;
		} else {
			u.rank++;
			v.parent = u;
			return u;
		}
	}
	
	public static void main(String[] args) {
		int dataSize = 7;
		
		TreeDSet[] element = new TreeDSet[dataSize];
		
		for(int i = 0; i < dataSize; i++) {
			element[i] = new TreeDSet();
			element[i].makeSet(i);
			System.out.println(element[i]);
		}
		
		System.out.println("Union 0 & 1 ==> ");
		DSet p = element[0].union(element[1]);
		System.out.println(p.toString());
		
		System.out.println("Union 2 & 1 ==> ");
		p = element[2].union(element[1]);
		System.out.println(p.toString());
		
		System.out.println("Union 3 & 4 ==> ");
		p = element[3].union(element[4]);
		System.out.println(p.toString());
		
		System.out.println("Union 2 & 4 ==> ");
		p = element[2].union(element[4]);
		System.out.println(p.toString());
		
		for(int i = 0; i < dataSize; i++) {
			element[i].showParent();
		}
		
		for(int i = 1; i < dataSize; i++) {
			element[0].union(element[i]);
		}
		System.out.println();
		for(int i = 0; i < dataSize; i++) {
			element[i].showParent();
		}
		
	}
}
