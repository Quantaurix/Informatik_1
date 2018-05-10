package quicksort_test;

public class Main {

	public static void main(String[] args) {
		int[] a = new int[5];
		for (int i = 1; i < a.length; i++) {
			a[i] =(int) (Math.random()*100);
			System.out.print(a[i]+" ");
		}
		System.out.println();
		a[1]=41;
		a[2]=98;a[3] =56;a[4] =80;
		quicksort(a);
		for (int i : a) {
			System.out.print(i+ " ");
		}
	}
	
	public static void quicksort(int[] a) {
		quicksort_rec(a,1,a.length-1);
	}
	
	private static int partition_classic(int[] a, int lo, int hi) {
		int i = lo-1, j = hi, v = a[hi], t;
		while(true) {
			do i++; while(a[i] < v);
			do j--; while(a[j] > v);
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
	
	private static int partition_search_lecture(int[] a, int lo, int hi) {
		int i = lo, j = hi, v = a[hi], t;
		do {
			t = a[i];
			a[i] = a[j];
			a[j] = t;
			while(a[i] < v) i++;;
			while(a[j] >= v) j--;
		} while(i<=j);
		return i;
	}
	
	private static void quicksort_rec(int[] a, int lo, int hi) {
		if(hi > lo) {
			int pivot = partition_search_lecture(a,lo,hi);
			quicksort_rec(a,lo,pivot-1);
			quicksort_rec(a,pivot,hi);
		}
	}

}
