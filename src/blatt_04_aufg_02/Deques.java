package blatt_04_aufg_02;

public class Deques {
	class ArrayDeque{
		private final int max;
		private int headptr;
		private int tailptr;
		private int[] deque;
		private int count = 0;
		
		public ArrayDeque(int n) {
			max = 2*n;
			deque = new int[max];
			headptr = 0;
			tailptr = max-1;
		}
		
		public boolean isEmpty() {return count == 0;}
		private boolean isFull() {return count == max;}
		public int head() {if(!isEmpty()) {return deque[headptr-1];} else throw new IndexOutOfBoundsException("Deque leer!");}
		public int tail() {if(!isEmpty()) {return deque[tailptr+1];} else throw new IndexOutOfBoundsException("Deque leer!");}
		public void addFront(int val) {if(!isFull()) {deque[headptr] = val;count++; if(headptr == max-1) {headptr = 0;} else {headptr++;}}else throw new IndexOutOfBoundsException("Deque voll!");}
		public void addBack(int val) {if(!isFull()) {deque[tailptr] = val;count++; if(tailptr == 0) {tailptr = max-1;} else {tailptr--;}}else throw new IndexOutOfBoundsException("Deque voll!");}
		public int removeFront() {if(!isEmpty()) {count--; if(headptr == 0) {headptr = max-1;return deque[headptr];}else {headptr--;return deque[headptr];}} else throw new IndexOutOfBoundsException("Deque leer!");}
		public int removeBack() {if(!isEmpty()) {count--; if(tailptr == max-1) {tailptr = 0;return deque[tailptr];}else {tailptr++;return deque[tailptr];}} else throw new IndexOutOfBoundsException("Deque leer!");}

	}
	
	public static void main(String[] args) {
		Deques d = new Deques();
		Deques.ArrayDeque deque = d.new ArrayDeque(10);
		for(int i = 1; i<=20;i++) {
			if(i%2 != 0) {
				deque.addFront(i);
			}else {
				deque.addBack(i);
			}
		}
		
		for (int i = 1; i <=20; i++) {
			System.out.print(deque.removeFront()+",");
		}
		
	}
}

