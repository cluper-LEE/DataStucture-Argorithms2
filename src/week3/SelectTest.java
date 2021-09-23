package week3;

public class SelectTest {

	private int recursiveCount = 0;

	public void resetCount() {
		this.recursiveCount = 0;
	}

	public int getCount() {
		return this.recursiveCount;
	}

	/**
	 * 배열 data의 p~r 범위에서 i번째 값을 찾아 index를 반환한다.
	 * @return index of ith value in data
	 */
	public int select(int[] data, int p, int r, int i) {
		this.recursiveCount++;

		if (p > r) {
			System.out.println("Invalid argument. p>r");
			return -1;
		}
		if (p == r) {
			return data[p];
		}

		int q = this.partition(data, p, r);
		int k = q-p;
		if(i<k) {
			return this.select(data, p, q-1, i);
		} else if (i==k) {
			return data[q];
		} else {
			return this.select(data, q+1, r, i-(q-p+1));
		}
	}

	/**
	 * 배열 data의 p~r범위 내에서 data[r]보다 작은 값을 data[r]의 왼쪽에, 큰 값을 오른쪽에 놓고 변화한 pivot index를 반환한다.
	 */
	private int partition(int[] data, int p, int r) {
		int pivot = r;

		int left = p;
		int right = r;

		while (true) {
			while (data[left] < data[pivot] && left < right) {
				left++;
			}
			while (data[right] >= data[pivot] && left < right) {
				right--;
			}
			if (left < right) {
				this.swapData(data, left, right);
			} else {
				break;
			}
		}
		this.swapData(data, pivot, right);
		return right;
	}

	private void swapData(int[] data, int i, int j) {
		int temp = data[i];
		data[i] = data[j];
		data[j] = temp;
	}

	public int linearSelect(int[] data, int p, int r, int i) {
		this.recursiveCount++;

		if (p > r) {
			System.out.println("Invalid argument. p>r");
			return -1;
		}
		if (p == r) {
			return data[p];
		}

		int q = this.linearPartition(data, p, r);
		int k = q-p;
		if(i<k) {
			return this.linearSelect(data, p, q-1, i);
		} else if (i==k) {
			return data[q];
		} else {
			return this.linearSelect(data, q+1, r, i-(q-p+1));
		}
	}

	/**
	 * median()을 통해 결정한 pivot은 최악의 상황에는 해당하지 않는다.
	 */
	private int linearPartition(int[] data, int p, int r) {
		int pValue = this.median(data, p, r);
		int index=0;
		for(int i = p; i <= r; i++) {
			if(data[i]==pValue) {
				index=i;
				break;
			}
		}
		swapData(data, r, index);
		
		int pivot = r;

		int left = p;
		int right = r;

		while (true) {
			while (data[left] < data[pivot] && left < right) {
				left++;
			}
			while (data[right] >= data[pivot] && left < right) {
				right--;
			}
			if (left < right) {
				this.swapData(data, left, right);
			} else {
				break;
			}
		}
		this.swapData(data, pivot, right);
		return right;
	}

	/**
	 * 주어진 범위에서 배열의 중앙값을 반환한다.
	 * 주어진 배열의 영역을 5개로 쪼개어 각 영역의 중앙값을 모아 배열로 만들고 그 배열로 재귀호출을 한다.
	 * 만약 주어진 영역의 크기가 5 이하면 그 배열의 중앙값을 반환한다.
	 */
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

	public static void main(String[] args) {
		int[] data = { 5, 27, 24, 6, 35, 3, 7, 8, 18, 71, 77, 9, 11, 32, 21, 4 };

		SelectTest s = new SelectTest();
		for (int i = 0; i < data.length; i++) {
			System.out.print(s.select(data, 0, data.length - 1, i) + "  ");
		}
		System.out.println();
		System.out.println("# of Recursive Calls of Select = " + s.getCount());

		s.resetCount();
		for (int i = 0; i < data.length; i++) {
			System.out.print(s.linearSelect(data, 0, data.length - 1, i) + "  ");
		}
		System.out.println();
		System.out.println("# of Recursive Calls of LinearSelect = " + s.getCount());

	}

}
