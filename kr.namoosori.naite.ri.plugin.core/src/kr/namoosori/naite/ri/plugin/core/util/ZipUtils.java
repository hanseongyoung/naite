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
	 * ������ ������ Zip ���Ϸ� �����Ѵ�.
	 * @param sourcePath ���� ��� ���丮
	 * @param saveFileName ���� zip ���� �̸�
	 * @throws Exception
	 */
	public static void zip(String sourcePath, String saveFileName) throws IOException {

		// ���� ���(sourcePath)�� ���丮�� ������ �ƴϸ� ����.
		File sourceFile = new File(sourcePath);
		if (!sourceFile.isFile() && !sourceFile.isDirectory()) {
			throw new RuntimeException("���� ����� ������ ã�� ���� �����ϴ�.");
		}

		// output �� Ȯ���ڰ� zip,cdbx,cdb �� �ƴϸ� ����.
		if (StringUtils.isBlank(saveFileName) || !ZIP_EXTENSIONS.contains(StringUtils.substringAfterLast(saveFileName, "."))) {
			throw new RuntimeException("���� ���ϸ��� Ȯ�����ּ���.");
		}

		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		ZipOutputStream zos = null;
		try {
			fos = new FileOutputStream(saveFileName); // FileOutputStream
			bos = new BufferedOutputStream(fos); // BufferedStream
			zos = new ZipOutputStream(bos); // ZipOutputStream
			zos.setLevel(COMPRESSION_LEVEL); // ���� ���� - �ִ� ������� 9, ����Ʈ 8
			zipEntry(sourceFile, sourcePath, zos); // Zip ���� ����
			zos.finish(); // ZipOutputStream finish
		} finally {
			IOUtils.closeQuietly(zos);
			IOUtils.closeQuietly(bos);
			IOUtils.closeQuietly(fos);
		}
	}

	/**
	 * ����
	 * @param sourceFile
	 * @param sourcePath
	 * @param zos
	 * @throws Exception
	 */
	private static void zipEntry(File sourceFile, String sourcePath, ZipOutputStream zos) throws IOException {
		// sourceFile �� ���丮�� ��� ���� ���� ����Ʈ ������ ���ȣ��
		if (sourceFile.isDirectory()) {
			if (sourceFile.getName().equalsIgnoreCase(".metadata")) { // .metadata ���丮 return
				return;
			}
			File[] fileArray = sourceFile.listFiles(); // sourceFile �� ���� ���� ����Ʈ
			for (int i = 0; i < fileArray.length; i++) {
				zipEntry(fileArray[i], sourcePath, zos); // ��� ȣ��
			}
		} else { // sourcehFile �� ���丮�� �ƴ� ���
			BufferedInputStream bis = null;
			try {
				String sFilePath = sourceFile.getPath();
				String zipEntryName = sFilePath.substring(sourcePath.length() + 1, sFilePath.length());

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
	 * Zip ������ ������ Ǭ��.
	 *
	 * @param zipFile - ���� Ǯ Zip ����
	 * @param targetDir - ���� Ǭ ������ �� ���丮
	 * @param fileNameToLowerCase - ���ϸ��� �ҹ��ڷ� �ٲ��� ����
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
				if (zentry.isDirectory()) {// Directory �� ���
					FileUtils.forceMkdir(targetFile); // ���丮 ����
				} else { // File �� ���
					// parent Directory ����
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
				if (zentry.isDirectory()) {// Directory �� ���
					FileUtils.forceMkdir(targetFile); // ���丮 ����
				} else { // File �� ���
					// parent Directory ����
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
	 * Zip ������ �� �� ��Ʈ���� ������ Ǭ��.
	 *
	 * @param zis - Zip Input Stream
	 * @param filePath - ���� Ǯ�� ������ ���
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
}
