package week2;

import java.util.HashSet;

public class TestOpenAddrLinear {
	
	static int dataSize = 500;
	static int chainingTableSize = 512;
	static int openTableSize = 523;
	static int interval = 50;
	static int maxKeyValue = 100000;

	public static void main(String[] args) {
		int [] data = new int[dataSize];
		HashSet<Integer> rdata = new HashSet<Integer>();
		
		while (rdata.size()<dataSize) {
			rdata.add((int)(Math.random()*maxKeyValue));
		}
		int k=0;
		for (int d : rdata) {
			data[k]=d;
			k++;
		}
		
///////  OpenAddressing Linear
		
		
		// Insert
		
		int sumOfSuccess=0;
		int sumOfFailure=0;

		System.out.println(">>> OpenAddressing-Linear");
		OpenAddrLinear myHash = new OpenAddrLinear(chainingTableSize);
		int repeat = dataSize/interval;
		for (int j=0; j<repeat; j++) {
			int start = j*interval;
			int end =(j+1)*interval;
			sumOfSuccess=0;
			sumOfFailure=0;
			int maxCount=0;
			for (int i=start; i<end; i++) {
				myHash.hashInsert(data[i]);
			}

			for (int i=start; i<end; i++) {
				int count= myHash.hashSearch(data[i]);
				if (count>=0) {
					sumOfSuccess += count;
					if (count>maxCount) maxCount = count;
				}
				else {
					sumOfFailure += count;
					if ((-count)>maxCount) maxCount = -count;
				}
			}
			System.out.print("\n [Insert] Number of investigation : Success ( ~ "+ (j+1)+" * "+interval +") = "
			    +sumOfSuccess +"  Max. Hop Count = "+maxCount);
			System.out.println("  Load Factor = "+myHash.loadfactor()
			    +"  Average Hop Count = "+((double)sumOfSuccess/interval));
		}
//		myHash.showTable();
		
		// Search with existing set
		
		sumOfSuccess=0;
		sumOfFailure=0;
		int successCount=0, failCount=0;
		for (int j=0; j<dataSize; j++) {
			int count= myHash.hashSearch(data[j]);
			if (count>=0) {
				sumOfSuccess += count;
				successCount++;
			}
			else {
				sumOfFailure += count;
				failCount++;
			}
		}
		System.out.println("\n\n [Search1] Average number of investigation : Success = "+sumOfSuccess
				+"("+successCount+")"+"  Average Hop Count = "+((double)sumOfSuccess/successCount)
				+"  Failure = "+(-sumOfFailure)+"("+failCount+")"
				+"  Average Hop Count = "+((double)-sumOfFailure/failCount));

		
		// Search with new random numbers
		
		sumOfSuccess=0;
		sumOfFailure=0;
		successCount=0;
		failCount=0;
		for (int j=0; j<dataSize; j++) {
			int count= myHash.hashSearch((int)(Math.random()*maxKeyValue));
			if (count>=0) {
				sumOfSuccess += count;
				successCount++;
			}
			else {
				sumOfFailure += count;
				failCount++;
			}
		}
		System.out.println("\n\n [Search2] Average number of investigation : Success = "+sumOfSuccess
				+"("+successCount+")"+"  Average Hop Count = "+((double)sumOfSuccess/successCount)
				+"  Failure = "+(-sumOfFailure)+"("+failCount+")"
				+"  Average Hop Count = "+((double)-sumOfFailure/failCount));

		// Delete
		
		sumOfSuccess=0;
		sumOfFailure=0;
		successCount=0;
		failCount=0;
		for (int j=0; j<dataSize; j++) {
			int count= myHash.hashDelete(data[j]);
			if (count>=0) {
				sumOfSuccess += count;
				successCount++;
			}
			else {
				sumOfFailure += count;
				failCount++;
			}
		}
		System.out.println("\n\n [Delete] Average number of investigation : Success = "+sumOfSuccess
				+"("+successCount+")"+"  Average Hop Count = "+((double)sumOfSuccess/successCount)
				+"  Failure = "+(-sumOfFailure)+"("+failCount+")"
				+"  Average Hop Count = "+((double)-sumOfFailure/failCount));

		
	}

}
