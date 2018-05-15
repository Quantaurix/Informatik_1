package binarysearchtest;

public class Main {
	public static void main(String[] args) {
		int[] a = new int[10];
		for(int i = 0; i < 10; i++){
				a[i] = i;
		}
		
		System.out.println(binarysearch_recursive(a, 0, a.length-1,-2));
	}
	
	public static int binarysearch_iterative(int[] a, int lo, int hi, int key) {
		int mid;
		do {
			mid = (lo+hi)/2;
			if(key < a[mid]) hi = mid-1;
			else lo = mid+1;
		}while(key != a[mid] && lo <= hi);
		return key == a[mid] ? mid : -1;
	}
	
	public static int binarysearch_recursive(int[] a, int lo, int hi, int key) {
		int mid = (lo+hi)/2;
		if(a[mid] != key){
			if(lo < hi) {
				if(key < a[mid]) return binarysearch_recursive(a,lo,mid-1,key);
				else return binarysearch_recursive(a, mid+1, hi, key);
			}
			return -1;
		}else {
			return mid;
		}
	}
}
