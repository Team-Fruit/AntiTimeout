package net.teamfruit.antitimeout;

public class MinecraftTimeoutError extends Error {
	private static final long serialVersionUID = -8682842383044426861L;

	public MinecraftTimeoutError() {
		super();
	}

	public MinecraftTimeoutError(final String message) {
		super(message);
	}

	public MinecraftTimeoutError(final String message, final Throwable cause) {
		super(message, cause);
	}

	public MinecraftTimeoutError(final Throwable cause) {
		super(cause);
	}

	public MinecraftTimeoutError(final String message, final Throwable cause,
			final boolean enableSuppression,
			final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
