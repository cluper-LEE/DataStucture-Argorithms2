package week3;

import java.util.HashSet;

public class SelectPerformance {

	public static void main(String[] args) {
		int unitDataSize = 50;
		int repeatition = 20;
		int dataSize = unitDataSize * repeatition;
		int maxKeyValue = 100000;
		
		int[] data = new int[dataSize];
		HashSet<Integer> rdata = new HashSet<>();
		
		while(rdata.size()<dataSize) {
			rdata.add((int)(Math.random()*maxKeyValue));
		}
		int k = 0;
		for(int d: rdata) {
			data[k] = d;
			k++;
		}
		
		for(int j = 1; j < repeatition; j++) {
			int testDataSize = j * unitDataSize;
			
			System.out.println("Test Data Size = " + testDataSize);
			
			SelectTest s = new SelectTest();
			for(int i = 0; i < testDataSize; i++) {
				s.select(data, 0, testDataSize-1, i);
			}
			System.out.println("****** # of Recursive Calls of Select = " + s.getCount() + "[" + (s.getCount()/testDataSize) + "]");
			
			s.resetCount();
			for(int i = 0; i < testDataSize; i++) {
				s.linearSelect(data, 0, testDataSize-1, i);
			}
			System.out.println("****** # of Recursive Calls of linearSelect = " + s.getCount() + "[" + (s.getCount()/testDataSize) + "]");
			
		}

	}

}
