package tp1.p2.control.exceptions;

public class NotEnoughCoinsException extends GameException {

	public NotEnoughCoinsException() {
		super();
	}
	public NotEnoughCoinsException(String message) {
		super(message);
	}
	public NotEnoughCoinsException(Throwable cause) {
		super(cause);
	}
	public NotEnoughCoinsException(String message, Throwable cause) {
		super(message, cause);
	}
	public NotEnoughCoinsException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}