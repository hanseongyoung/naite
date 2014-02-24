package kr.namoosori.naite.ri.plugin.teacher.server.multipart;

import java.io.IOException;
import java.io.InputStream;

/**
 * A dummy input stream class.
 * 
 * @author Copyright (C) 2001-2006 Jason Pell
 * @version 2.00b3
 */
public class EmptyInputStream extends InputStream {
	public EmptyInputStream() {
    }

    public int read() throws IOException {
        return -1;
    }

    public int available() throws IOException {
        return 0;
    }
}
