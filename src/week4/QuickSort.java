package week4;

public class QuickSort {
	
	private int[] data;
	private int nOfIteration;
	
	public QuickSort(int[] data) {
		this.data = data;
	}
	
	public int[] quickSort() {
		for(int i = 0; i < data.length; i++) {
			System.out.print(data[i] + " ");
		}
		return this.quickSort(data, 0, data.length-1);
	}
	/**
	 * r을 기준으로 r보다 작은 부분과 큰 부분으로 나누어 해당 부분들을 재귀 호출한다.
	 * @param data 정렬할 배열
	 * @param p 부분 배열의 시작 인덱스
	 * @param r 부분 배열의 끝 인덱스
	 * @return
	 */
	public int[] quickSort(int[] data, int p, int r) {
		nOfIteration++;
		if (p < r) {
			int q = partition(data, p, r);
			quickSort(data, p, q - 1);
			quickSort(data, q + 1, r);
		}
		return data;
	}

	/**
	 * r보다 작은 값을 왼 쪽에, 큰 값을 오른쪽에 배치한 후 r 값의 위치를 결정하고 그 인덱스를 반환한다. 
	 * @param data 정렬할 부분 배열
	 * @param p 부분 배열의 시작 인덱스
	 * @param r 부분 배열의 끝 인덱스
	 * @return 결정된 r 인덱스
	 */
	private int partition(int[] data, int p, int r) {
		///
		int pValue = median(data, p, r);
		System.out.println("\n-- Quasi-median between " + p + " and " + r + " : " + pValue);
		
		int index = 0;
		for(int i = p; i <= r; i++) {
			if(data[i] == pValue) {
				index = i;
				break;
			}
		}
		swapData(data, r, index);
		///
		int pivot = r;
		int left = p;
		int right = r;

		while (left < right) {
			while (data[left] < data[pivot] && left < right) {
				left++;
			}
			while (data[right] >= data[pivot] && left < right) { // >이 아니라 >=여야 함.
				right--;
			}
			if (left < right) {
				swapData(data, left, right);
			}
		}
		swapData(data, pivot, right);
		return right;
	}

	private void swapData(int[] data, int i, int j) {
		int temp = data[i];
		data[i] = data[j];
		data[j] = temp;
	}

	private int median(int[] data, int p, int r) {
		if((r-p+1) <= 5) {
			return this.median5(data, p, r);
		} else {
			float f = r-p+1;
			int arrSize = (int)Math.ceil(f/5);
			int[] medianArray = new int[arrSize];
			for(int i = 0; i < arrSize; i++) { // 5개로 쪼갠 각 영역의 중앙값을 받아 medianArray[]에 넣는다.
				medianArray[i]=this.median5(data, p+5*i, (int)Math.min( p+5*i+4, r));
			}
			return this.median(medianArray, 0, arrSize-1);
		}
	}

	/**
	 * 배열의 중앙값을 반환한다.
	 */
	private int median5(int[] data, int p, int r) {
		if(p==r) {
			return data[p];
		} else {
			this.sort5(data, p, r);
			return data[p+(r-p+1)/2];
		}
	}

	private void sort5(int[] data, int p, int r) {
		for(int i = p; i < r; i++) {
			for(int j = i+1; j <= r; j++) {
				if(data[i]>data[j]) {
					this.swapData(data, i, j);
				}
			}
		}
	}
	
	public void showInfo() {
		System.out.println("\n>>> Quick Sort <<<");
		System.out.println("-- result : ");
		for(int i = 0; i<data.length; i++) {
			System.out.print(data[i] + " ");
		}
		System.out.println("\n-- Iteration(RecursiveCalls) = " + nOfIteration);
	}
	
	public static void main(String[] args) {
		int[] data = new int[100];
		for(int i = 0; i < 100; i++) {
			data[i] = i;
		}
		QuickSort q = new QuickSort(data);
		q.quickSort();
		q.showInfo();
	}

}
