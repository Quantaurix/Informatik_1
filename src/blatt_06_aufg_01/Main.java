package blatt_06_aufg_01;

import java.util.Arrays;

public class Main {
	static int n = 1000;
	static int[] a = new int[n];
	static int[] A = new int[n];
	
	public static void main(String[] args) {
		for (int i = 1; i < a.length; i++) {
			a[i] = (int)(Math.random()*1000);
			System.out.print(a[i]+", ");
		}
			
		System.out.println();
		A = a.clone();
			
		long start = System.nanoTime();
		int quick = quickselect(a,(a.length)/2,1,a.length-1);
		long end = System.nanoTime();
		long quicktime = end-start;
			
		start = System.nanoTime();
		int select = select(A,1,a.length-1,(a.length)/2);
		end = System.nanoTime();
		long selecttime = end -start;
		
		if(quick != select) throw new RuntimeException("FEHLER");
		
		System.out.println();
		System.out.println("Median Quickselect: \t" +quick +" Quickselect-Time:\t" + quicktime + "\nMedian Select:\t\t" + select + " Select-Time:\t" + selecttime);
	}
	
	public static int quickselect(int[] a, int key, int lo, int hi) {
			int i = partition_classic(a,lo,hi);
			if(i > key) return quickselect(a,key,lo,i-1);
			else if(i < key) return quickselect(a,key,i+1,hi);
			else return a[i];
	} 
	
	public static int partition_classic(int a[], int lo, int hi) {
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
	
	private static int partition_select(int [] a, int i, int j, int pivot) {
		int links;
		int rechts;
		int t;
		links = i;
		rechts = j;
		do {
			t = a[links];
			a[links] = a[rechts];
			a[rechts] = t;
			while (a[links] < pivot)
				links++;
			while (a[rechts] >= pivot)
				rechts--;
		} while (links <= rechts);
		return links;
	}
	
	
	private static void quick_sort(int [] a, int links, int rechts) {
		int i;
		if (rechts-links > 70) {
			i = partition_select(a,links,rechts, a[rechts]);
			quick_sort(a,links, i - 1);
			quick_sort(a,i+1, rechts);
		}
		insertionsort(a,links,rechts);
		
	}
	
	
	public static int select(int[] a, int i, int j, int k) {
		int pivot;
		int m;
		int t;
		if ((j - i) < 30) {
			quick_sort(a, i, j);
			return a[i + k - 1];
			} else {
			for (m = 0; m < ((j - i + 1) / 5); m++) {
				quick_sort(a, (i + 5 * m), (i + 5 * m + 4));
				t = a[(i + 5 * m + 2)];
				a[(i + 5 * m + 2)] = a[i + m];
				a[i + m] = t;
				}
			pivot = select(a, i, (i + m - 1), (m + 1) / 2);
			m = partition_select(a, i, j, pivot);
			if (k <= (m - i))
				return select(a, i, (m - 1), k);
			else
				return select(a, m, j, (k - (m - i)));
			}
	}
	
	public static void insertionsort(int[] a,int lo, int hi) {
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
