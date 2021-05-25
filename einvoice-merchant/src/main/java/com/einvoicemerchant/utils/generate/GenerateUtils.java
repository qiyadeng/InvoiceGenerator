package com.einvoicemerchant.utils.generate;

import java.util.Random;
import java.util.UUID;

public class GenerateUtils {

	// 生成UUID,32为16进制(数字和字母)
	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replace("-", "").toLowerCase();
	}

	// 产生一个6位数字验证码
	public static String genSixRandomCode() {
		int randomInteger = getRandomNumber(100000, 999999, new Random());
		return String.valueOf(randomInteger);
	}

	// 产生一个4位数字验证码
	public static String genfourRandomCode() {
		int randomInteger = getRandomNumber(1000, 9999, new Random());
		return String.valueOf(randomInteger);
	}


	// 产生一个6位数字
	public static int genSixRandomNumber() {
		return getRandomNumber(100000, 999999, new Random());
	}

	public static Integer getRandomNumber(int start, int end) {
		return getRandomNumber(start, end, new Random());
	}

	public static Integer getRandomNumber(int start, int end, Random random) {
		if (start > end) {
			throw new IllegalArgumentException("Start cannot exceed End.");
		}
		// get the range, casting to long to avoid overflow problems
		long range = (long) end - (long) start + 1;
		// compute a fraction of the range, 0 <= frac < range
		long fraction = (long) (range * random.nextDouble());
		int randomNumber = (int) (fraction + start);
		return randomNumber;
	}

}
