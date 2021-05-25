package com.management.util;

import java.math.BigDecimal;

public class BigDecimalHelper {

	//小数点后保留2位
	public static Double getCurrencyOfDouble(Double d) {
		BigDecimal b = new BigDecimal(d);
		Double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return f1;
	}

	//小数的乘法，保留2位
	public static Double doubleMul(Double d1,Double d2){
		BigDecimal b1 = new BigDecimal(d1);
		BigDecimal b2 = new BigDecimal(d2);
		b1 = b1.multiply(b2);
		Double f1 = b1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return f1;
	}
}
