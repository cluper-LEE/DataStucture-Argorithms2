package week6;

public class MatrixChain {
	private int nOfMatrix;
	private int[] p;
	private int count;
	private int[][] memo;
	
	public MatrixChain(int[] dimension) {
		p = dimension;
		nOfMatrix = p.length-1;
		memo = new int[nOfMatrix+1][nOfMatrix+1];
	}
	
	public void reset() {
		count = 0;
		for(int i = 0; i < nOfMatrix+1; i++) {
			for(int j = 0; j < nOfMatrix+1; j++) {
				memo[i][j]= -1;
			}
		}
		for(int i = 0; i < nOfMatrix; i++) {
			memo[i][i] = 0;
		}
	}
	public int getCount() {
		return count; 
	}
	public int matrixChain(int i, int j) {
		count++;
		if(i==j) return 0;
		int min = 99999999;
		for(int k = i; k < j; k++) {
			int q = matrixChain(i, k) + matrixChain(k+1, j) + p[i-1]*p[k]*p[j];
			if(q<min)min = q;
		}
		return min;
	}
	public int matrixChainDP(int i, int j) {
		count++;
		if(memo[i][j]>=0) return memo[i][j];
		int min = 99999999;
		for(int k = i; k < j; k++) {
			if(memo[i][k]<0) memo[i][k] = matrixChainDP(i, k);
			if(memo[k+1][j]<0) memo[k+1][j] = matrixChainDP(k+1, j);
			min = Math.min(min, memo[i][k] + memo[k+1][j] + p[i-1]*p[k]*p[j]);
		}
		return min;
	}
	
	public static void main(String[] args) {
		int nOfMatrix = 15;
		int[] dimension = {2,3,4,3,5,3,4,5,3,2,4,6,5,4,3,4};
		
		MatrixChain mm = new MatrixChain(dimension);
		for(int i=1; i<=nOfMatrix; i++) {
			mm.reset();
			System.out.print("recursion : " + mm.matrixChain(1, i) + "   Count=" + mm.getCount());
			mm.reset();
			System.out.println(" ===> DP : " + mm.matrixChainDP(1, i) + "   Count=" + mm.getCount());
		}
	}
}
