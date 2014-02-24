package kr.namoosori.naite.ri.plugin.teacher.server.multipart;

import java.io.IOException;

public class MaxContentLengthException extends IOException {

	private static final long serialVersionUID = 7651373857029451562L;
	
	private long maxContentLength;
    private long contentLength;

    public MaxContentLengthException(long contentLength, long maxContentLength) {
        super("Content length exceeded (" + contentLength + " > " + maxContentLength + ")");

        this.maxContentLength = maxContentLength;
        this.contentLength = contentLength;
    }

    public long getContentLength() {
        return this.contentLength;
    }

    public long getMaxContentLength() {
        return this.maxContentLength;
    }
}
