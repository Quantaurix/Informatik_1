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
		Item<Integer> i = new Item<>(5,5);
		Item<Integer> j = new Item<>(3,6);
		Item<Integer> k = new Item<>(3,7);
		Item<Integer> l = new Item<>(3,123);
		Item<Integer> m = new Item<>(3,9);



		a.print();
		a.insert(i);
		a.print();
		a.insert(j);
		a.print();
		a.insert(k);
		a.print();
		a.insert(l);
		a.print();
		a.insert(m);
		a.print();
		a.setElement(2, new Item<Integer>(3,134));
		a.print();
		System.out.println(a.getElement(4).getVal());
		a.delete();
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
