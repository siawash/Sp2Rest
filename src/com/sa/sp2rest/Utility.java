package com.sa.sp2rest;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

import javax.swing.JOptionPane;

public class Utility {

	public static boolean isNullOrEmpty(String string) {
		return string == null || string.length() == 0;
	}

	public static boolean isNullOrEmpty(List list) {
		return list == null || list.size() == 0;
	}

	public static void deleteDir(File file) {
		File[] contents = file.listFiles();
		if (contents != null) {
			for (File f : contents) {
				deleteDir(f);
			}
		}
		file.delete();
	}

	public static void write2File(String path, List<String> content) {
		try {
			FileWriter fw = new FileWriter(path);
			for (String s : content)
				fw.write(s + "\r\n");
			fw.close();
		} catch (Exception e) {
//			Logger.log(e.getMessage());
//			System.out.println(e);

			Logger.log(e.getMessage());
//			JOptionPane.showMessageDialog(null, e.getMessage());
			Thread.currentThread().stop();
//			System.exit(0);
		}
	}

	public static void log(String content) {
//		System.out.println(content);
		Logger.log(content);
	}

	public static String convert2PackageName(String name) {

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < name.length(); i++) {
			int c = name.charAt(i);
			if (c >= 65 && c <= 90) {
				if (i > 0)
					sb.append('_');
				sb.append((char) (c + 32));
			} else {
				sb.append((char) c);
			}
		}

		return sb.toString();
	}

}
