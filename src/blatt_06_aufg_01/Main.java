package blatt_06_aufg_01;

import java.util.Arrays;

public class Main {
	static int n = 100;
	static int[] a = new int[n];
	static int[] A = new int[n];
	public static void main(String[] args) {
		for (int i = 1; i < a.length; i++) {
			a[i] = (int)(Math.random()*100);
			System.out.print(a[i]+",");
		}
		a[0] = Integer.MIN_VALUE;
		System.out.println();
		A = a.clone();
		
		long start = System.nanoTime();
		int quick = quickselect(a.length/2,1,a.length-1);
		long end = System.nanoTime();
		long quicktime = end-start;
		
		start = System.nanoTime();
		int select = select(1,a.length-1,a.length/2);
		end = System.nanoTime();
		long selecttime = end -start;
		
		
		Arrays.sort(a);
		for (int i = 1; i < a.length; i++) {
			if(i==a.length/2) {System.out.print(" ");System.out.print(a[i]+" ,");continue;}
			System.out.print(a[i]+",");
		}
		System.out.println();
		System.out.println(quick +" quicktime: "+quicktime + "; selecttime: " + selecttime + " " + select);
	}
	
	public static int quickselect( int key, int lo, int hi) {
		//if(lo<hi) {
			int i = partition_quickselect(lo,hi);
			if(i > key) return quickselect(key,lo,i-1);
			else if(i < key) return quickselect(key,i+1,hi);
			else return a[i];
		//}
		//return lo;
	}
	
	public static int partition_quickselect(int lo, int hi) {
		int pivot = a[hi], i = lo-1, j = hi,t;
		while(true) {
			do i++; while(a[i] < pivot);
			do j--; while(a[j] > pivot);
			if(i >= j) break;
			t = a[i];
			a[i] = a[j];
			a[j] = t;
		}
		t = a[i];
		a[i] = a[hi];
		a[hi] = t;
		return i;
	}
	
	private static int partition_select(int i, int j, int pivot) {
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
	
	private static void quick_sort(int links, int rechts) {
		int pivot;
		int i;
		if (rechts > links) {
			pivot = A[rechts];
			i = partition_select(links, rechts, pivot);
			quick_sort(links, i - 1);
			quick_sort(i+1, rechts);
		}
	}
	
	public static int select( int i, int j, int k) {
		int pivot;
		int m;
		int t;
		if ((j - i) < 74) {
			quick_sort(i, j);
			return A[i + k +1];
		} else {
			for (m = 0; m < ((j - i + 1) / 5); m++) {
				quick_sort((i + 5 * m), (i + 5 * m + 4));
				t = A[(i + 5 * m + 2)];
				A[(i + 5 * m + 2)] = A[i + m];
				A[i + m] = t;
			}
			pivot = select(i, (i + m - 1), A[(m + 1) / 2]);
			m = partition_select(i, j, pivot);
			if (k <= (m - i))
				return select(i, (m - 1), k);
			else
				 return select(m, j, (k - (m - i)));
			}
	}

}
