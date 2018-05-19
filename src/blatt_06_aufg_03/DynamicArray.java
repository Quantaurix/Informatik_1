package blatt_06_aufg_03;

import java.util.Arrays;

public class DynamicArray {

	Item[] a;
	int m;
	int n;
	
	public DynamicArray(int size)
	{
		m = size;
		n = 0;
		a = new Item[m];
	}
	
	public void setElement(int index, Item item) {
		if(index > m-1) return;
		a[index] = item;
	}
	
	public Item getElement(int index) {
		if(index > m-1) return null;
		return a[index];
	}
	
	public void insert(Item item) {
		if(n==0) {a[n] = item;return;}
		if(n==m-1) {
			Item[] b = new Item[2*m];
			for (int i = 0; i < a.length; i++) {
				b[i] = a[i];
			}
			m = 2*m;
			a = b;
		}

		a[++n] = item;
	}
	
	public void delete() {
		a[m-1] = null;
	}
	
	public void print() {
		for(int i = 0; i < m;i++) {
			if(a[i] == null) System.out.print("null, ");
			else System.out.print(a[i].getVal() + ", ");
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		DynamicArray a = new DynamicArray(1);
		Item<Integer> i = new Item<>(5,5);
		Item<Integer> j = new Item<>(3,6);
		a.print();
		a.insert(i);
		a.print();
		a.insert(j);
		a.print();

	}
}

class Item<T> {
	int key;
	T val;
	
	public Item(int key, T val) {
		this.key = key;
		this.val = val;
	}
	public void setVal(T val) {this.val = val;}
	public T getVal() {return val;}
}
