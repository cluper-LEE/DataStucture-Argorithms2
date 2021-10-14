package week6;

import java.util.function.Function;

public class Pebbles {
	int[][] peb;
	int[][] memo;
	int[][] nextPattern = {
			{0,0,0},
			{2,3,0},
			{1,3,4},
			{1,2,0},
			{2,0,0}
	};
	int nCols;
	int count;
	
	public Pebbles(int[][] data) {
		peb = data;
		nCols = peb[0].length;
		memo = new int [5][nCols]; // ù ° ���� ������� �Ⱦ�. ������ �� ����.
		reset();
	}
	public void reset() {
		count = 0;
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < nCols; j++) {
				memo[i][j] = -99999;
			}
		}
	}
	public int getCount() {
		return count;
	}
	public int maxPebbleRecursion(int n) {
		return maxPebble((j) -> pebble(n, j));
	}
	public int maxPebbleMemo(int n) {
		return maxPebble((j) -> pebbleMemo(n, j));
	}
	public int maxPebble(Function<Integer, Integer> pebble) {
		int max = -99999;
		for(int j = 1; j <= 4; j++) {
			max = Math.max(pebble.apply(j), max);
		}
		return max;
	}
	
	/**
	 * �� ��ȣ�� �� ���� ������ �Է����� �޴´�. �� ���� ������ ���� ������ �ִ��� ��ȯ�Ѵ�.
	 * Memoization (Dynamic Programming)
	 * @param i  �� ��ȣ
	 * @param p  ������ ����
	 * @return
	 */
	public int pebbleMemo(int i, int p) {
		count++;
		if(memo[p][i]>-99999) return memo[p][i]; // �̹� ���� �� ��ȯ
		if(i==1) { // end point
			memo[p][i]=aPatternValue(i, p);
			return memo[p][i];
		}
		else {
			int max = -99999;
			int k=0;
			while(k<3 && nextPattern[p][k] != 0) { // ���� ������ ���� ���ϵ��� ��
				int q = nextPattern[p][k];
				if(memo[q][i-1]==-99999) memo[q][i-1]=pebbleMemo(i-1,q);
				max = Math.max(memo[q][i-1], max);
				k++;
			}
			memo[p][i] = max + aPatternValue(i, p);
			return memo[p][i];
		}
	}
	
	/**
	 * �� ��ȣ�� �� ���� ������ �Է����� �޴´�. �� ���� ������ ���� ������ �ִ밪�� ��ȯ�Ѵ�.
	 * �Ϲ� ��ͱ���
	 * @param i  �� ��ȣ
	 * @param p  ������ ����
	 * @return
	 */
	private int pebble(int i, int p) {
		count++;
		if(i==1) {
			return aPatternValue(i, p);
		} else {
			int max = -99999;
			int k = 0;
			while(k<3 && nextPattern[p][k]!=0) {
				int q = nextPattern[p][k];
				max = Math.max(pebble(i-1,q), max);
				k++;
			}
			return aPatternValue(i, p) + max;
		}
	}
	
	/**
	 * i ���� p ���Ͽ����� ���� ��ȯ�Ѵ�.
	 * @param i �� ��ȣ
	 * @param p ���� ����
	 * @return
	 */
	private int aPatternValue(int i, int p) {
		int retVal = 0;
		switch(p) {
		case 1: retVal = peb[0][i];
		break;
		case 2: retVal = peb[1][i];
		break;
		case 3: retVal = peb[2][i];
		break;
		case 4: retVal = peb[0][i] + peb[2][i];
		break;
		}
		return retVal;
	}
	
	public static void main(String[] args) {
		int [][] input = {
				{0, 6, 7, 12, -5, 5, 3, 11, 3, 7, -2, 5, 4},
				{0, -8, 10, 14, 9, 7, 13, 8, 5, 6, 1, 3, 9},
				{0, 11, 12, 7, 4, 8, -2, 9, 4, -4, 3, 7, 10}
				};
		Pebbles myPeb = new Pebbles(input);
		for(int i=1; i<input[0].length; i++) {
			myPeb.reset();
			System.out.print(">>> " + i + " : [Recursion] "
				+ myPeb.maxPebbleRecursion(i) + ", Count = " + myPeb.getCount());
			myPeb.reset();
			System.out.println(" ===> [DP] "
					+ myPeb.maxPebbleMemo(i) + ", Count = " + myPeb.getCount());
		}
	}
}
