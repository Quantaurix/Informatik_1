package blatt_06_aufg_01;

import java.util.Arrays;

public class Main {

	public static void main(String[] args) {
		int[] a = new int[20]; //1-Indizierung
		for (int i = 1; i < a.length; i++) {
			a[i] = (int)(Math.random()*100);
			System.out.print(a[i]+",");
		}
		a[0] = Integer.MIN_VALUE;
		System.out.println();
		System.out.println(quickselect(a,a.length/2,1,a.length-1));
		Arrays.sort(a);
		for (int i = 1; i < a.length; i++) {
			if(i==a.length/2) {System.out.print(" ");System.out.print(a[i]+" ,");continue;}
			System.out.print(a[i]+",");
		}
	}
	
	public static int quickselect(int[] a, int key, int lo, int hi) {
		//if(lo<hi) {
			int i = partition(a,lo,hi);
			if(i > key) return quickselect(a,key,lo,i-1);
			else if(i < key) return quickselect(a,key,i+1,hi);
			else return a[i];
		//}
		//return lo;
	}
	
	public static int partition(int[] a, int lo, int hi) {
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

}
