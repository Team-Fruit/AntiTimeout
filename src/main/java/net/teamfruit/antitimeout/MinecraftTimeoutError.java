package net.teamfruit.antitimeout;

public class MinecraftTimeoutError extends Error {

	private static final long serialVersionUID = 1525399522582905960L;

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
}
