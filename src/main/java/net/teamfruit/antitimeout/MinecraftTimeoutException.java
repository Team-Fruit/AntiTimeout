package net.teamfruit.antitimeout;

public class MinecraftTimeoutException extends RuntimeException {

	public MinecraftTimeoutException() {
		super();
	}

	public MinecraftTimeoutException(final String message) {
		super(message);
	}

	public MinecraftTimeoutException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public MinecraftTimeoutException(final Throwable cause) {
		super(cause);
	}

	public MinecraftTimeoutException(final String message, final Throwable cause,
			final boolean enableSuppression,
			final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
