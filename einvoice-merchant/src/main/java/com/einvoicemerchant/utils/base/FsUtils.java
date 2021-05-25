package com.einvoicemerchant.utils.base;


import com.einvoicemerchant.utils.convert.EncodingUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FsUtils {
	public static void ensureDirExist(File file) {
		if (!file.isDirectory())
			file = file.getParentFile(); // 文件转换成目录
		if (!file.exists())
			file.mkdirs();
	}

	// 递归删除文件及文件夹
	public static void deleteDir(File file) {
		if (file.isFile()) {
			file.delete();
			return;
		}

		if (file.isDirectory()) {
			File[] childFiles = file.listFiles();
			if (childFiles == null || childFiles.length == 0) {
				file.delete();
				return;
			}

			for (int i = 0; i < childFiles.length; i++) {
				deleteDir(childFiles[i]);
			}
			file.delete();
		}
	}

	public static boolean createTextFile(String path, String text, String charsetName) {
		return createTextFile(new File(path), text, charsetName);
	}

	public static boolean createTextFile(File file, String text, String charsetName) {
		ensureDirExist(file);
		try {
			if (file.exists())
				file.delete();
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(text.getBytes(charsetName));
			fos.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	public static String readTextFile(String path) {
		File file = new File(path);
		try {
			if (!file.exists())
				return null;
			FileInputStream fis = new FileInputStream(file);
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();

			return EncodingUtils.decodeUTF8(buffer);
		} catch (IOException e) {
			return null;
		}
	}

	public static byte[] readBinaryFile(String path) {
		try {
			Path pathFile = Paths.get(path);
			return Files.readAllBytes(pathFile);
		} catch (IOException e) {
			return null;
		}
	}

	public static boolean fileCopy(String oldFilePath, String newFilePath) {
		// 如果原文件不存在
		if (fileExists(oldFilePath) == false) {
			return false;
		}
		try {
			// 获得原文件流
			FileInputStream inputStream = new FileInputStream(new File(oldFilePath));
			byte[] data = new byte[1024];
			// 输出流
			FileOutputStream outputStream = new FileOutputStream(new File(newFilePath));
			// 开始处理流
			while (inputStream.read(data) != -1) {
				outputStream.write(data);
			}
			inputStream.close();
			outputStream.close();
			return true;
		} catch (IOException ex) {
			return false;
		}
	}

	public static boolean fileExists(String filePath) {
		File file = new File(filePath);
		return file.exists();
	}

	public static void fileDel(String filePath) {
		File file = new File(filePath);
		if (file != null && file.isFile())
			file.delete();
	}
}