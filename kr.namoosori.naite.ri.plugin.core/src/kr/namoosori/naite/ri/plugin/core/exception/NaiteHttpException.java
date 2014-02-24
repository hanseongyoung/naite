package kr.namoosori.naite.ri.plugin.core.exception;

public class NaiteHttpException extends NaiteException {
	//
	private static final long serialVersionUID = 6439998777587514838L;
	
	private int httpCode;

	public NaiteHttpException(int httpCode) {
		super();
		this.httpCode = httpCode;
	}

	public NaiteHttpException(int httpCode, String message) {
		super(message);
		this.httpCode = httpCode;
	}

	public NaiteHttpException(int httpCode, String message, Throwable t) {
		super(message, t);
		this.httpCode = httpCode;
	}

	public NaiteHttpException(int httpCode, Throwable t) {
		super(t);
		this.httpCode = httpCode;
	}

	public int getHttpCode() {
		return httpCode;
	}

	public void setHttpCode(int httpCode) {
		this.httpCode = httpCode;
	}

}
