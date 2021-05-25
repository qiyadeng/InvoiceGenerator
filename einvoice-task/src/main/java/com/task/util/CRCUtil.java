package com.task.util;

import java.io.UnsupportedEncodingException;

public class CRCUtil {

	private static int crc16 = 0x8005;

	public static String crc(String input){

		try{
			byte[] inputs = input.getBytes("GBK");
			int a = 0;

			for (int i = 0; i < inputs.length; i++) {
				a= div(inputs[i],a);
			}
			System.out.println(a);

			byte r = 0;
			a= div(r,a);
			a= div(r,a);

			//System.out.println(Integer.toHexString(a));
			return Integer.toHexString(a).toUpperCase();
		} catch (Exception e){
			e.printStackTrace();
		}
		return "FFFF";
		//String input = "上海奇伢信息科技有限公司</>91310116MA1J81HA25</>上海市瞿溪路1236号2号楼4楼C05 18616018007</>招商银行股份有限公司上海浦江镇支行 121918228910401</>";
	}

	private static int div(byte input, int a) {
		int temp;
		int data = input;
		for (int i = 0; i < 8; i++) {
			temp = a & 0x8000;
			a = a << 1;
			a = a & 0x0000ffff;

			int numIn = data & 0x80;
			numIn = numIn >> 7;

			a = a ^ numIn;

			if (temp == 0x8000) {
				a = a ^ crc16;
			}

			data = data << 1;
			a = a & 0x0000ffff;
		}
		return a;
	}

}