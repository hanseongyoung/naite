package kr.namoosori.naite.ri.plugin.core.exception;

public class NaiteRuntimeException extends RuntimeException {
	//
	private static final long serialVersionUID = -2430322191979640427L;

	public NaiteRuntimeException() {
        super();
    }

    public NaiteRuntimeException(String message) {
        super(message);
    }

    public NaiteRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public NaiteRuntimeException(Throwable cause) {
        super(cause);
    }
}
