package median_of_medians;

import java.util.Arrays;

public class Main {

	public static void main(String[] args) {
		for(int j = 0; j < 100000; j++) {
			int n = 1000;
			int[] array = new int[n];
			array[0] = Integer.MIN_VALUE;
			for(int i = 0; i < array.length; i++) {
				array[i] = (int) (Math.random()*1000);
				//System.out.print(array[i] + ", ");
				
			}
			System.out.println();
			System.out.println("Median:" + select(array,1,array.length-1,array.length/2));
			/*Arrays.sort(array);
			for (int i = 1; i < array.length; i++) {
				if(i == array.length/2) { System.out.print("    " + array[i] + "    "); continue;}
				System.out.print(array[i] + ", ");
			}*/
		}
	}

	private static int partition(int[] A, int i, int j, int pivot) {
		int links;
		int rechts;
		int t;
		links = i;
		rechts = j;
		do {
			t = A[links];
			A[links] = A[rechts];
			A[rechts] = t;
			while (A[links] < pivot)
				links++;
			while (A[rechts] >= pivot)
				rechts--;
		} while (links <= rechts);
		return links;
	}

	private static void quick_sort(int[] A, int links, int rechts) {
		int pivot;
		int i;
		if (rechts - links > 100) {
			pivot = A[rechts];
			i = partition(A, links, rechts, pivot);
			quick_sort(A, links, i - 1);
			quick_sort(A, i, rechts);
		}
		insertionsort(A, links, rechts);
	}

	public static int select(int[] A, int i, int j, int k) {
		int pivot;
		int m;
		int t;
		if ((j - i) < 100) {
			quick_sort(A, i, j);
			return A[i + k - 1];
			} else {
			for (m = 0; m < ((j - i + 1) / 5); m++) {
				quick_sort(A, (i + 5 * m), (i + 5 * m + 4));
				t = A[(i + 5 * m + 2)];
				A[(i + 5 * m + 2)] = A[i + m];
				A[i + m] = t;
				}
			pivot = select(A, i, (i + m - 1), (m + 1) / 2);
			m = partition(A, i, j, pivot);
			if (k <= (m - i))
				return select(A, i, (m - 1), k);
			else
				return select(A, m, j, (k - (m - i)));
			}
	}

	public static void insertionsort(int[] a, int lo, int hi) {
		int i;
		int key;
		for (int j = lo + 1; j <= hi; j++) {
			key = a[j];
			i = j - 1;
			while (i > 0 && a[i] > key) {
				a[i + 1] = a[i];
				i--;
			}
			a[i + 1] = key;
		}
	}

}
