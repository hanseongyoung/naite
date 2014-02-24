package kr.namoosori.naite.ri.plugin.core.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import kr.namoosori.naite.ri.plugin.core.exception.NaiteException;

import org.apache.commons.io.FileUtils;

public class NaiteFileUtils {
	
	/**
	 * 디렉토리 존재여부 체크하여 없는 경우 생성함.
	 * 디렉토리가 존재할 경우 true를 리턴하고
	 * 디렉토리가 존재하지 않을 경우 해당 디렉토리를 생성 후 false를 리턴한다.
	 * @param string
	 */
	public static boolean checkDir(String dirPath) {
		File dir = new File(dirPath);
		if (!dir.exists()) {
			try {
				FileUtils.forceMkdir(dir);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return false;
		} else {
			return true;
		}
	}
	
	public static void saveFile(String saveFilePathName, InputStream in) throws NaiteException {
		//
		OutputStream out = null;
		try {
			out = new FileOutputStream(new File(saveFilePathName));
			int read = 0;
			byte[] bytes = new byte[1024];
			while((read = in.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			System.out.println("***** Done!!!");
		} catch (FileNotFoundException e) {
			throw new NaiteException("파일 저장중 오류", e);
		} catch (IOException e) {
			throw new NaiteException("파일 저장중 오류", e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void saveFile(String saveFilePathName, byte[] fileContents) throws NaiteException {
		//
		if (saveFilePathName == null) {
			throw new NaiteException("파일명이 없어 파일을 저장할 수 없습니다.");
		}

		File file = null;
		FileOutputStream fos = null;
		try {
			file = new File(saveFilePathName);
			fos = new FileOutputStream(file);
			fos.write(fileContents);
			fos.flush();
		} catch (Exception e) {
			throw new NaiteException("파일 저장중 오류가 발생했습니다.", e);
		} finally {
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
