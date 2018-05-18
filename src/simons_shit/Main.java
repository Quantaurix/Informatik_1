package simons_shit;

import java.util.Arrays;
import java.util.Random;

import javax.swing.plaf.synth.SynthSeparatorUI;

public class Main {

	// QuickSelect von dem Uebungsblatt

	private static int QuickSelect(int A[], int k, int i, int j) {
		int l = partition_classic(A, i, j);
		if (l > k)
			return QuickSelect(A, k, i, l - 1);
		else if (l < k)
			return QuickSelect(A, k, l + 1, j);
		else
			return A[l];
	}
	
	public static int partition_classic(int[] A, int lo, int hi) {
		int pivot = A[hi], i = lo-1, j = hi,t;
		while(true) {
			do i++; while(A[i] < pivot && i < A.length);
			do j--; while(A[j] > pivot && j > 0);
			if(i >= j) break;
			t = A[i];
			A[i] = A[j];
			A[j] = t;
		}
		t = A[i];
		A[i] = A[hi];
		A[hi] = t;
		return i;
	}

	// Select-Prozedur aus der Vorlesung

	private static int select(int[] A, int i, int j, int k) {
		int pivot, m, t;
		if ((j - i) < 6) {
			quick_sort(A, i, j);
			return A[i + k];
		} else {
			for (m = 0; m < ((j - i + 1) / 5); m++) {
				quick_sort(A, (i + 5 * m), (i + 5 * m + 4));
				t = A[(i + 5 * m + 2)];
				A[(i + 5 * m + 2)] = A[i + m];
				A[i + m] = t;
			}
			pivot = select(A, i, (i+m-1),(m+1)/2 -1);
			m = partition(A, i, j, pivot);
			if (k < (m - i))
				return select(A, i, (m -1), k);
			else
				return select(A, m, j, (k - (m - i)));
		}
	}

	private static void quick_sort(int[] A, int links, int rechts) {
		if (rechts > links) {
			int pivot;
			int i;
			i = partition_classic(A, links, rechts);
			quick_sort(A, links, i - 1);
			quick_sort(A, i+1, rechts);
		}
	}

	private static int partition(int[] A, int i, int j, int pivot) {
		int links, rechts, t;
		links = i;
		rechts = j;
		do {
			t = A[links];
			A[links] = A[rechts];
			A[rechts] = t;
			while (A[links] < pivot && links < A.length - 1)
				links++;
			while (A[rechts] >= pivot && rechts > 0)
				rechts--;
		} while (links <= rechts);
		return links;
	}

	public static void main(String[] args) {
		Random random = new Random();

//		int array_size = (2 * random.nextInt(10) + 3);
		int[] array = new int[29];
		for (int j = 0; j < array.length; j++) {
			array[j] = random.nextInt(500);
		}
		
		/*array[0] = 253;
		array[1] = 432;
		array[2] = 365;
		array[3] = 81;
		array[4] = 154;
		array[5] = 214;
		array[6] = 95;
		array[7] = 117;
		array[8] = 131;
		array[9] = 366;
		array[10] = 363;
		array[11] = 407;
		array[12]= 116;
		array[13] =43;
		array[14]= 367;
		array[15] = 438;
		array[16] = 205;
		array[17] = 91;
		array[18] = 431;
		array[19] = 229;
		array[20] = 49;
		array[21] = 275;
		array[22] = 294;
		array[23] = 17;
		array[24] = 296;
		array[25] = 354;
		array[26] = 434;
		array[27] = 419;
		array[28] = 418;
		*/
		
		int median = (array.length+1) / 2 -1;
		System.out.println("Original Array: " + Arrays.toString(array));
		int[] test = array.clone();
		Arrays.sort(test);
		for (int i = 0; i < test.length; i++) {
			if(i == median) {
				System.out.print(" "+test[i]+ " , ");
			}
			else System.out.print(test[i]+", ");
		}
		System.out.println();

		// Vergleich zwischen der Select-Prozedur und Quick-Select
		long start = System.nanoTime();
		System.out.println("Median Select-Prozedur: "
				+ select(Arrays.copyOf(array, array.length), 0, array.length - 1, median));
		long elapsed = System.nanoTime() - start;
		System.out.println("Runtime: " + (elapsed / 1000000L) + "." + String.format("%06d", elapsed % 1000000L) + " ms");

		start = System.nanoTime();
		System.out.println("Median QuickSelect: " + QuickSelect(array, median, 0, array.length - 1));
		elapsed = System.nanoTime() - start;
		System.out
				.println("Runtime: " + (elapsed / 1000000L) + "." + String.format("%06d", elapsed % 1000000L) + " ms");
	}
}