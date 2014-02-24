package kr.namoosori.naite.ri.plugin.core.exception;

public class NaiteException extends Exception {
	//
	private static final long serialVersionUID = 6619756879432808668L;

    public NaiteException() {
        super();
    }

    public NaiteException(String message) {
        super(message);
    }

    public NaiteException(String message, Throwable cause) {
        super(message, cause);
    }

    public NaiteException(Throwable cause) {
        super(cause);
    }

}
