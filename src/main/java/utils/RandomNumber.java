package utils;

import java.util.Random;

public class RandomNumber {
	public static int generateRandomNumber() {
		int min = 1000, max = 9999;
		Random random = new Random();
		return random.nextInt(max - min + 1) + min;
	}
}
