package kr.namoosori.naite.ri.plugin.core.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

public class ZipUtils {

	private static final int COMPRESSION_LEVEL = 8;

	private static final int BUFFER_SIZE = 1024 * 2;
	private static final List<String> ZIP_EXTENSIONS;
	static {
		ZIP_EXTENSIONS = Arrays.asList(new String[] { "zip" });
	}

	/**
	 * 지정된 폴더를 Zip 파일로 압축한다.
	 * @param sourcePath 압축 대상 디렉토리
	 * @param saveFileName 저장 zip 파일 이름
	 * @throws Exception
	 */
	public static void zip(String sourcePath, String saveFileName) throws IOException {

		// 압축 대상(sourcePath)이 디렉토리나 파일이 아니면 예외.
		File sourceFile = new File(sourcePath);
		if (!sourceFile.isFile() && !sourceFile.isDirectory()) {
			throw new RuntimeException("압축 대상의 파일을 찾을 수가 없습니다.");
		}

		// output 의 확장자가 zip,cdbx,cdb 가 아니면 예외.
		if (StringUtils.isBlank(saveFileName) || !ZIP_EXTENSIONS.contains(StringUtils.substringAfterLast(saveFileName, "."))) {
			throw new RuntimeException("저장 파일명을 확인해주세요.");
		}

		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		ZipOutputStream zos = null;
		try {
			fos = new FileOutputStream(saveFileName); // FileOutputStream
			bos = new BufferedOutputStream(fos); // BufferedStream
			zos = new ZipOutputStream(bos); // ZipOutputStream
			zos.setLevel(COMPRESSION_LEVEL); // 압축 레벨 - 최대 압축률은 9, 디폴트 8
			zipEntry(sourceFile, sourcePath, zos); // Zip 파일 생성
			zos.finish(); // ZipOutputStream finish
		} finally {
			IOUtils.closeQuietly(zos);
			IOUtils.closeQuietly(bos);
			IOUtils.closeQuietly(fos);
		}
	}

	/**
	 * 압축
	 * @param sourceFile
	 * @param sourcePath
	 * @param zos
	 * @throws Exception
	 */
	private static void zipEntry(File sourceFile, String sourcePath, ZipOutputStream zos) throws IOException {
		// sourceFile 이 디렉토리인 경우 하위 파일 리스트 가져와 재귀호출
		if (sourceFile.isDirectory()) {
			if (sourceFile.getName().equalsIgnoreCase(".metadata")) { // .metadata 디렉토리 return
				return;
			}
			File[] fileArray = sourceFile.listFiles(); // sourceFile 의 하위 파일 리스트
			for (int i = 0; i < fileArray.length; i++) {
				zipEntry(fileArray[i], sourcePath, zos); // 재귀 호출
			}
		} else { // sourcehFile 이 디렉토리가 아닌 경우
			BufferedInputStream bis = null;
			try {
				String sFilePath = sourceFile.getPath();
				String zipEntryName = sFilePath.substring(sourcePath.length() + 1, sFilePath.length());

				// Unix 및 Mac 과의 호환을 위해 File Separator 를 변경
				zipEntryName = zipEntryName.replaceAll("\\\\", "/");
				
				bis = new BufferedInputStream(new FileInputStream(sourceFile));
				ZipEntry zentry = new ZipEntry(zipEntryName);
				zentry.setTime(sourceFile.lastModified());
				zos.putNextEntry(zentry);

				byte[] buffer = new byte[BUFFER_SIZE];
				int cnt = 0;
				while ((cnt = bis.read(buffer, 0, BUFFER_SIZE)) != -1) {
					zos.write(buffer, 0, cnt);
				}
				zos.closeEntry();
			} finally {
				IOUtils.closeQuietly(bis);
			}
		}
	}

	/**
	 * Zip 파일의 압축을 푼다.
	 *
	 * @param zipFile - 압축 풀 Zip 파일
	 * @param targetDir - 압축 푼 파일이 들어간 디렉토리
	 * @param fileNameToLowerCase - 파일명을 소문자로 바꿀지 여부
	 * @throws Exception
	 */
	public static void unzip(File zipFile, File targetDir, boolean fileNameToLowerCase) throws IOException {
		FileInputStream fis = null;
		ZipInputStream zis = null;
		try {
			fis = new FileInputStream(zipFile); // FileInputStream
			zis = new ZipInputStream(fis); // ZipInputStream
			ZipEntry zentry = null;
			while ((zentry = zis.getNextEntry()) != null) {
				String fileNameToUnzip = zentry.getName();
				if (fileNameToLowerCase) { // fileName toLowerCase
					fileNameToUnzip = fileNameToUnzip.toLowerCase();
				}

				File targetFile = new File(targetDir, fileNameToUnzip);
				if (zentry.isDirectory()) {// Directory 인 경우
					FileUtils.forceMkdir(targetFile); // 디렉토리 생성
				} else { // File 인 경우
					// parent Directory 생성
					FileUtils.forceMkdir(targetFile.getParentFile());
					unzipEntry(zis, targetFile);
				}
			}
		} finally {
			IOUtils.closeQuietly(zis);
			IOUtils.closeQuietly(fis);
		}
	}
	
	public static void unzip(InputStream inputStream, File targetDir, boolean fileNameToLowerCase) throws IOException {
		ZipInputStream zis = null;
		try {
			zis = new ZipInputStream(inputStream); // ZipInputStream
			ZipEntry zentry = null;
			while ((zentry = zis.getNextEntry()) != null) {
				String fileNameToUnzip = zentry.getName();
				if (fileNameToLowerCase) { // fileName toLowerCase
					fileNameToUnzip = fileNameToUnzip.toLowerCase();
				}

				File targetFile = new File(targetDir, fileNameToUnzip);
				if (zentry.isDirectory()) {// Directory 인 경우
					FileUtils.forceMkdir(targetFile); // 디렉토리 생성
				} else { // File 인 경우
					// parent Directory 생성
					FileUtils.forceMkdir(targetFile.getParentFile());
					unzipEntry(zis, targetFile);
				}
			}
		} finally {
			IOUtils.closeQuietly(zis);
			IOUtils.closeQuietly(inputStream);
		}
	}

	/**
	 * Zip 파일의 한 개 엔트리의 압축을 푼다.
	 *
	 * @param zis - Zip Input Stream
	 * @param filePath - 압축 풀린 파일의 경로
	 * @return
	 * @throws Exception
	 */
	protected static File unzipEntry(ZipInputStream zis, File targetFile) throws IOException {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(targetFile);

			byte[] buffer = new byte[BUFFER_SIZE];
			int len = 0;
			while ((len = zis.read(buffer)) != -1) {
				fos.write(buffer, 0, len);
			}
		} finally {
			IOUtils.closeQuietly(fos);
		}
		return targetFile;
	}
	
	//----------------------------------------------------------------------------
	public static void zip2(String sourcePath, String saveFileName) throws IOException {

		// 압축 대상(sourcePath)이 디렉토리나 파일이 아니면 예외.
		File sourceFile = new File(sourcePath);
		if (!sourceFile.isFile() && !sourceFile.isDirectory()) {
			throw new RuntimeException("압축 대상의 파일을 찾을 수가 없습니다.");
		}

		// output 의 확장자가 zip,cdbx,cdb 가 아니면 예외.
		if (StringUtils.isBlank(saveFileName) || !ZIP_EXTENSIONS.contains(StringUtils.substringAfterLast(saveFileName, "."))) {
			throw new RuntimeException("저장 파일명을 확인해주세요.");
		}

		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		ZipOutputStream zos = null;
		try {
			fos = new FileOutputStream(saveFileName); // FileOutputStream
			bos = new BufferedOutputStream(fos); // BufferedStream
			zos = new ZipOutputStream(bos); // ZipOutputStream
			zos.setLevel(COMPRESSION_LEVEL); // 압축 레벨 - 최대 압축률은 9, 디폴트 8
			zipEntry2(sourceFile, sourcePath, zos); // Zip 파일 생성
			zos.finish(); // ZipOutputStream finish
		} finally {
			IOUtils.closeQuietly(zos);
			IOUtils.closeQuietly(bos);
			IOUtils.closeQuietly(fos);
		}
	}

	
	private static void zipEntry2(File sourceFile, String sourcePath, ZipOutputStream zos) throws IOException {
		// sourceFile 이 디렉토리인 경우 하위 파일 리스트 가져와 재귀호출
		if (sourceFile.isDirectory() && sourceFile.listFiles().length > 0) {
			if (sourceFile.getName().equalsIgnoreCase(".metadata")) { // .metadata 디렉토리 return
				return;
			}
			File[] fileArray = sourceFile.listFiles(); // sourceFile 의 하위 파일 리스트
			for (int i = 0; i < fileArray.length; i++) {
				zipEntry(fileArray[i], sourcePath, zos); // 재귀 호출
			}
		} else { // sourcehFile 이 디렉토리가 아닌 경우
			BufferedInputStream bis = null;
			try {
				String sFilePath = sourceFile.getPath();
				String zipEntryName = sFilePath.substring(sourcePath.length() + 1, sFilePath.length());

				// Unix 및 Mac 과의 호환을 위해 File Separator 를 변경
				zipEntryName = zipEntryName.replaceAll("\\\\", "/");
				
				bis = new BufferedInputStream(new FileInputStream(sourceFile));
				ZipEntry zentry = new ZipEntry(zipEntryName);
				zentry.setTime(sourceFile.lastModified());
				zos.putNextEntry(zentry);

				byte[] buffer = new byte[BUFFER_SIZE];
				int cnt = 0;
				while ((cnt = bis.read(buffer, 0, BUFFER_SIZE)) != -1) {
					zos.write(buffer, 0, cnt);
				}
				zos.closeEntry();
			} finally {
				IOUtils.closeQuietly(bis);
			}
		}
	}
	//----------------------------------------------------------------------------
	
	public static void main(String[] args) throws Exception {
//		System.setProperty("file.separator", "/");
//		System.out.println("system seperator:"+System.getProperty("file.separator"));
//		System.out.println("seperator:"+File.separator);
//		
//		File file = new File("D:/work/test.txt");
//		System.out.println("file:"+file.toString());
		
		zip2("D:\\Project\\naitae\\workspaces\\naitae\\testZip", "D:\\testZip.zip");
	}
}
