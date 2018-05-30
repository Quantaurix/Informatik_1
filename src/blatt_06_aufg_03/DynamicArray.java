package blatt_06_aufg_03;

import java.util.LinkedList;

public class DynamicArray {

	Item[] a;
	int m;
	int n;
	
	public DynamicArray(int size)
	{
		m = size;
		n = 0;
		a = new Item[m+1]; //1-Indizierung
	}
	
	public void setElement(int index, Item item) {
		if(index > m) return;
		a[index] = item;
	}
	
	public Item getElement(int index) {
		if(index > m) return null;
		return a[index];
	}
	
	public void insert(Item item) {
		if(n==0) {a[++n] = item;return;} //1-Indizierung, a[0] ist leer
		if(n==m) {
			//System.out.println("n:" + n +" m: " + m);
			Item[] b = new Item[2*m+1];
			for (int i = 0; i < a.length; i++) {
				b[i] = a[i];
			}
			m = 2*m;
			a = b;
		}

		a[++n] = item;
	}
	
	public void delete() {
		a[n--] = null;
	}
	
	public void print() {
		for(int i = 0; i <= m;i++) {
			if(a[i] == null) System.out.print("null, ");
			else System.out.print(a[i].getVal() + ", ");
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		DynamicArray a = new DynamicArray(1);
		
		long start = System.nanoTime();
		for(int o = 0; o < 10000000; o++) {
			a.insert(new Item(1,(int)(Math.random()*100)));	
		}
		long end = System.nanoTime();
		long dynamicTime = end-start;
		
		LinkedList<Item> list = new LinkedList<>();
		start = System.nanoTime();
		for(int o = 0; o < 10000000; o++) {
			list.add(new Item(1,(int)(Math.random()*100)));	
		}
		end = System.nanoTime();
		long ListTime = end-start;
		System.out.println("Dynamic Time: " + dynamicTime/1000000 + "; List Time: " + ListTime/1000000);
		
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
