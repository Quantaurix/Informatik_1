package blatt_01_aufg_04;

import java.util.Arrays;
import java.util.Random;

public class Sort {
	public static void selectionSort(int[] input) {
		for(int i = 0; i < input.length; i++) {
			int current = i;
			for(int j = i + 1;j < input.length; j++) {
				if(input[j] < input[current]) {
					current = j;
				}
			}
			int temp = input[i];
			input[i] = input[current];
			input[current] = temp;
		}
	}

	public static boolean isSorted(int[] array) {
		for(int i = 1; i < array.length; i++) {
			if(!(array[i - 1] <= array[i]))
				return false;
		}
		return true;
	}
	
	public static void shuffle(int[] array, Random random) {
		for (int i = array.length - 1; i > 0; i--) {
			int index = random.nextInt(i + 1);

			int temp = array[index];
			array[index] = array[i];
			array[i] = temp;
		}
	}

	public static void bogoSort(int[] input, Random random) {
		while(!isSorted(input))
			shuffle(input, random);
	}

	public static void main(String[] args) {
		Random random = new Random();
	
		int num_trials = 1;
		int array_size = 500000;

		for(int i = 0; i < num_trials; i++) {
			int[] array = new int[array_size];
			for(int j = 0; j < array.length; j++)
				array[j] = random.nextInt(1000);
			
			if(array_size < 20)
				System.out.println("Sorting " + Arrays.toString(array));
			
			long start = System.nanoTime();
//		    selectionSort(array);
//			bogoSort(array, random);
			Arrays.sort(array);
			long elapsed = System.nanoTime() - start;

			if(array_size < 20)
				System.out.println("Result: " + Arrays.toString(array));
			System.out.println(isSorted(array) ? "Ok." : "Error!");
			System.out.println("Runtime: " + (elapsed / 1000000L)
				+ "." + String.format("%06d", elapsed % 1000000L) + " ms");
		}
	}
}

