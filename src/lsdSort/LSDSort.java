package lsdSort;

import java.util.Arrays;
import java.util.Random;

public class LSDSort {
	private static final int MAX = 1000000;
	private static final int ITERATIONS = 20;

	public static void main(String[] args) {
		long start;
		long stop;
		long timeLSD = 0;
		long timeSort = 0;

		for (int i = 0; i < ITERATIONS; i++) {

			int[] data1 = generate();
			int[] data2 = data1.clone();

			start = System.nanoTime();
			Arrays.sort(data1);
			stop = System.nanoTime();
			timeSort += (stop - start);

			start = System.nanoTime();
			lsdSort(data2, 32);
			stop = System.nanoTime();
			timeLSD += (stop - start);

			if (!Arrays.equals(data1, data2)) {
				System.out.println("wrong! not equals!");
			}

		}
		System.out.println("Sort takes " + timeSort / ITERATIONS);
		System.out.println("LSDSort takes " + timeLSD / ITERATIONS);
		System.out.println("Ratio " + (double) timeSort / (double) timeLSD);
	}

	private static void lsdSort(int[] data, int wordWidth) {
		int byteSize = 8;
		int alphabethSize = 1 << byteSize;
		int mask = alphabethSize - 1;
		int[] temp = new int[data.length];

		for (int k = 0; k < wordWidth / byteSize; k++) {

			int[] count = new int[alphabethSize + 1];

			for (int j = 0; j < data.length; j++) {
				count[((data[j] >> k * byteSize) & mask) + 1]++;
			}
			for (int j = 0; j < alphabethSize; j++) {
				count[j + 1] += count[j];
			}
			for (int j = 0; j < data.length; j++) {
				temp[count[((data[j] >> k * byteSize) & mask)]++] = data[j];
			}

			System.arraycopy(temp, 0, data, 0, data.length);
		}
	}

	private static int[] generate() {
		int[] data = new int[MAX];

		Random random = new Random();

		for (int i = 0; i < data.length; i++) {
			data[i] = random.nextInt(Integer.MAX_VALUE);
		}
		return data;
	}
}
