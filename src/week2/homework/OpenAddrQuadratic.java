package week2.homework;

public class OpenAddrQuadratic { // Quadratic Probing

	int nOfHops =0;
	double threshold = 0.5;
	
	int [] table;
	int tableSize;
	int numberOfItems;
	
	public OpenAddrQuadratic (int n) {
		tableSize = n;
		table = new int[tableSize];
		numberOfItems = 0;
		// -1 : null, -999 : deleted
		for (int i=0; i<tableSize; i++)
			table[i]=-1;
	}
		
	private int hashFunction(int d) {
		// 곱하기방법
		double temp = (double)d * 0.6180339887;
		double res = temp - Math.floor(temp);
		return (int)(res*tableSize);
	}

	public int hashInsert(int d) {
		if(this.loadfactor()>=this.threshold) {
			this.enlargeTable();
		}
		int hashCode = this.hashFunction(d);
		this.nOfHops=1;
		if(this.table[hashCode] == -1) { // 빈자리라면
			this.table[hashCode] = d;
			this.numberOfItems++;
			return this.nOfHops;
		} else { // collision
			this.nOfHops++;
			int probeIndex = (hashCode+1)%this.tableSize;
			int i = 0;
			while(table[probeIndex]!=-1 && table[probeIndex]!=-999) {
				this.nOfHops++;
				i++;
				probeIndex = (probeIndex+(int)Math.pow(i, 2))%this.tableSize;
				if(probeIndex == hashCode) {
					return 0; // enlarge로 인해 사실 발생하지 않음.
				}
			}
			this.table[probeIndex] = d;
			return this.nOfHops;
		}
	}
	
	public int hashSearch(int d) {
		int hashCode = this.hashFunction(d);
		this.nOfHops=1;
		if(this.table[hashCode] == d) { // 빈자리라면
			return this.nOfHops;
		} else { // collision
			this.nOfHops++;
			int probeIndex = (hashCode+1)%this.tableSize;
			int i = 0;
			while(this.table[probeIndex]!=-1 && this.table[probeIndex]!=d) { // -1이면 없는 것. d면 찾은 것.
				this.nOfHops++;
				i++;
				probeIndex = (probeIndex+(int)Math.pow(i, 2))%this.tableSize;
				if(probeIndex == hashCode) {
					return 0; // enlarge로 인해 사실 발생하지 않음.
				}
			}
			if(this.table[probeIndex] == d) {
				return this.nOfHops;
			} else {
				return -this.nOfHops;
			}
		}
	}
	
	
	public int hashDelete(int d) {
		int hashCode = this.hashFunction(d);
		this.nOfHops=1;
		if(this.table[hashCode] == d) { // 빈자리라면
			this.table[hashCode] = -999;
			this.numberOfItems--;
			return this.nOfHops;
		} else { // collision
			this.nOfHops++;
			int probeIndex = (hashCode+1)%this.tableSize;
			int i = 0;
			while(this.table[probeIndex]!=-1 && this.table[probeIndex]!=d) { // -1이면 없는 것. d면 찾은 것.
				this.nOfHops++;
				i++;
				probeIndex = (probeIndex+(int)Math.pow(i, 2))%this.tableSize;
				if(probeIndex == hashCode) {
					return 0; // enlarge로 인해 사실 발생하지 않음.
				}
			}
			if(this.table[probeIndex] == d) {
				this.table[probeIndex] = -999;
				this.numberOfItems--;
				return this.nOfHops;
			} else {
				return -this.nOfHops;
			}
		
		}
	}
	
	
	private void enlargeTable() {
		int[] oldTable = this.table;
		int oldSize = this.tableSize;
		this.tableSize *= 2;
		this.table = new int[this.tableSize];
		for(int i = 0; i < this.tableSize; i++) {
			this.table[i] = -1;
		}
		for(int i = 0; i < oldSize; i++) { // rehashing
			if(oldTable[i] >= 0) { // -1, -999 filtering
				this.hashInsert(oldTable[i]);				
			}
		}
	}
	
	
	

	public double loadfactor() {
		return (double)numberOfItems/tableSize;
	}
	
	public void showTable() {
		System.out.println("Current Hash Table : ");
		for (int i = 0; i<tableSize; i++)
			System.out.print(table[i]+"  ");
		System.out.println();
	}

	
	public static void main(String[] args) {
		int tableSize = 17;
		
		int [] data = {10, 12, 18, 20, 22, 23, 26, 27, 42, 57};
		int dataSize = data.length;
		
		System.out.println("\n *** Open Addressing - Quadratic Probing ***");
		
		OpenAddrQuadratic myHash = new OpenAddrQuadratic(tableSize);
		// Insert
		int sumOfSuccess = 0;
		int sumOfFailure = 0;
		int maxCount = 0;
		for (int i =0; i<dataSize; i++) {
			int count = myHash.hashInsert(data[i]);
			if (count>=0) {
				sumOfSuccess += count;
				if (count>maxCount) maxCount = count;
			}
			else
				sumOfFailure += count;
		}
		myHash.showTable();
		System.out.println("\n\n [Insert] No. of Hops : Success ="+sumOfSuccess 
				+ "  Failure = "+sumOfFailure+"   Max. Hop Count = "+ maxCount);
		System.out.println("\n Load Factors ="+myHash.loadfactor()); 
		
		// Search with existing data set
		sumOfSuccess = 0;
		sumOfFailure = 0;
		maxCount = 0;
		for (int i =0; i<dataSize; i++) {
			int count = myHash.hashSearch(data[i]);
			if (count>=0) {
				sumOfSuccess += count;
				if (count>maxCount) maxCount = count;
			}
			else {
				sumOfFailure += count;
				if ((-count)>maxCount) maxCount = -count;
			}
		}
		System.out.println("\n\n [Search 1] No. of Hops : Success ="+sumOfSuccess 
				+ "  Failure = "+sumOfFailure+"   Max. Hop Count = "+ maxCount);

		// Search with non-existing data set
		sumOfSuccess = 0;
		sumOfFailure = 0;
		maxCount = 0;
		for (int i =0; i<dataSize; i++) {
			int count = myHash.hashSearch(data[i]+1);
			if (count>=0) {
				sumOfSuccess += count;
				if (count>maxCount) maxCount = count;
			}
			else {
				sumOfFailure += count;
				if ((-count)>maxCount) maxCount = -count;
			}
		}
		System.out.println("\n\n [Search 2] No. of Hops : Success ="+sumOfSuccess 
				+ "  Failure = "+sumOfFailure+"   Max. Hop Count = "+ maxCount);

		// Delete with non-existing data set
		sumOfSuccess = 0;
		sumOfFailure = 0;
		maxCount = 0;
		for (int i =0; i<dataSize; i++) {
			int count = myHash.hashDelete(data[i]+1);
			if (count>=0) {
				sumOfSuccess += count;
				if (count>maxCount) maxCount = count;
			}
			else {
				sumOfFailure += count;
				if ((-count)>maxCount) maxCount = -count;
			}
		}
		System.out.println("\n\n [Delete] No. of Hops : Success ="+sumOfSuccess 
				+ "  Failure = "+sumOfFailure+"   Max. Hop Count = "+ maxCount);


	}

}
