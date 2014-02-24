package kr.namoosori.naite.ri.plugin.teacher.server.multipart;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MultipartFile {
	private String m_fieldname;
    private String m_rawFileName;
    private String m_fileName;
    private File m_tmpFile;
    private byte[] m_fileContents;
    private long m_fileSize;
    private String m_contentType;

    /**
     * @param fieldName - name of file field
     * @param pathName - Full path name of file as provided from the browser, in many cases this will not
     *            differ from filename.
     * @param filename - Basename of file as provided from the browser.
     * @param contentType - Content type of file
     * @param fileSize - size of file
     * @param tmpFile - file contents
     */
    protected MultipartFile(String fieldName, String fileName, String contentType, long fileSize, File tmpFile) {
        m_fieldname = fieldName;
        m_rawFileName = fileName;
        m_fileName = getBasename(m_rawFileName);
        m_contentType = contentType;
        m_tmpFile = tmpFile;
        m_fileSize = fileSize;
    }

    /**
     * @param fieldName - name of file field
     * @param pathName - Full path name of file as provided from the browser, in many cases this will not
     *            differ from filename.
     * @param filename - Basename of file as provided from the browser.
     * @param contentType - Content type of file
     * @param fileSize - size of file
     * @param fileContents - file contents
     */
    protected MultipartFile(String fieldName, String fileName, String contentType, long fileSize,
                            byte[] fileContents) {
        m_fieldname = fieldName;
        m_rawFileName = fileName;
        m_fileName = getBasename(m_rawFileName);
        m_contentType = contentType;
        m_fileContents = fileContents;
        m_fileSize = fileSize;
    }

    public String getFieldName() {
        return m_fieldname;
    }

    public String getPathName() {
        return m_rawFileName;
    }

    public String getName() {
        return m_fileName;
    }

    public String getContentType() {
        return m_contentType;
    }

    public long getSize() {
        return m_fileSize;
    }

    public InputStream getInputStream() throws IOException {
        if (m_tmpFile != null) {
            return new FileInputStream(m_tmpFile);
        } else if (m_fileContents != null) {
            return new ByteArrayInputStream(m_fileContents);
        } else {
            return new EmptyInputStream();
        }
    }

    public String toString() {
        return "fieldName=" + getFieldName() + "; pathName=" + getPathName() + "; name=" + getName()
               + "; contentType=" + getContentType() + "; size=" + getSize();
    }

    /**
     * This needs to support the possibility of a / or a \ separator. Returns strFilename after removing all
     * characters before the last occurence of / or \.
     */
    private String getBasename(String strFilename) {
        if (strFilename == null)
            return strFilename;

        int intIndex = strFilename.lastIndexOf("/");
        if (intIndex == -1 || strFilename.lastIndexOf("\\") > intIndex)
            intIndex = strFilename.lastIndexOf("\\");

        if (intIndex != -1)
            return strFilename.substring(intIndex + 1);
        else
            return strFilename;
    }
}
