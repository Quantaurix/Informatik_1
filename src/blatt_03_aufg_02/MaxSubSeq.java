package blatt_03_aufg_02;

import java.util.Arrays;
import java.util.Random;

public class MaxSubSeq {
	
	public static final class MyElement {
		private int dat;
		private MyElement next;
		
		public MyElement(int x) {
			dat = x;
		}
		
		public MyElement() {
			dat = 0;
		}
		
		public int zugriff() {
			return this.next.dat;
		}
	}

	
	public static final class MyList {
		private MyElement head;
		private MyElement tail;
		
		MyList() {
			head = new MyElement();
			tail = new MyElement();
			head.next = tail;
			tail.next = head;
		}

		void insert(int number) {
			// Implementieren Sie diese Methode, die Zahlen am Ende der Liste einfügt.
			MyElement toInsert = new MyElement(number);
			toInsert.next = tail;
			tail.next.next = toInsert;
			tail.next = toInsert;
		}

		MyElement search(int val) {
			MyElement pos = head;
			while(pos.next != tail && pos.next.zugriff() != val) {
				pos=pos.next;
			}
			if(pos.next == tail) {pos = null;}
			return pos;
		}
		
		void delete(MyElement p) {
			if(p.next ==tail) throw new IllegalArgumentException("Kann nicht geloescht werden");
			MyElement q = p.next.next;
			p.next = q;
			if(p.next == tail) {tail.next = p;}
		}
	}

	
	
	
	
	
	static void maximalSubList(MyList list) {
		// Implementieren Sie hier den Algorithm.
		MyElement current = list.head;
		int scanmax = 0, bismax = 0;
		int lower = 1;
		int upper = 1;
		int currentLower = 1;
		int pos = 1;
		
		while(current.next != list.tail) {
			if(scanmax + current.next.dat > 0) {scanmax += current.next.dat;} else {scanmax = 0; currentLower = pos+1;}
			if(scanmax > bismax) { bismax = scanmax; lower = currentLower; upper = pos;}
			pos++;
			current = current.next;
		}
		System.out.println("My answer is: ["+(lower-1)+", "+(upper)+"), sum: "+bismax);
	}

	// Lassen sie diese Methode unverändert!
	static void maximalSubArray(int[] sequence) {
		int suffix_start = 0, suffix_sum = 0;
		
		int global_start = 0, global_length = 0, global_sum = 0;
		for(int i = 0; i < sequence.length; i++) {
			if(sequence[i] + suffix_sum >= 0) {
				suffix_sum += sequence[i];
			}else{
				suffix_sum = 0;
				suffix_start = i + 1;
			}
			
			if(suffix_sum > global_sum) {
				global_start = suffix_start;
				global_length = i - suffix_start + 1;
				global_sum = suffix_sum;
			}
		}

		System.out.println("Correct answer is: [" + global_start
				+ ", " + (global_start + global_length) + "), sum: " + global_sum);
	}

	// Lassen sie diese Methode unverändert!
	public static void main(String[] args) {
		int seq_length = 10000;

		Random random = new Random();
		
		int range = 20;

		int[] array = new int[seq_length];
		MyList list = new MyList();
		for(int j = 0; j < seq_length; j++) {
			int abs = random.nextInt(range);
			int number = (random.nextBoolean() ? -abs : abs);
			array[j] = number;
			list.insert(number);
		}
		
		if(seq_length <= 100) {
			System.out.println(Arrays.toString(array));
		}else{
			System.out.println("Sequence is too long; omitting it from output");
		}
		long liststart = System.nanoTime();
		maximalSubList(list);
		long listend = System.nanoTime();
		long arraystart = System.nanoTime();
		maximalSubArray(array);
		long arrayend = System.nanoTime();
		
		
		System.out.println("list: " + (listend-liststart)/1000000);
		System.out.println("array: " + (arrayend-arraystart)/1000000);
		
		
	}
}

