package net.teamfruit.antitimeout;

import java.util.concurrent.TimeUnit;

public class ThreadTimeoutCheck extends Thread {

	public ThreadTimeoutCheck() {
		setDaemon(true);
		setName("antitimeout-check");
		setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(final Thread t, final Throwable e) {
				if (e instanceof MinecraftTimeoutError)
					CrashUtil.crash("Server timeout", e);
			}
		});
	}

	private static final long TIMEOUT_MILLISECONDS = TimeUnit.MINUTES.toMillis(1);

	@Override
	public void run() {
		while (true) {
			final long timeout = System.currentTimeMillis()-AntiTimeoutHandler.instance().getLastTime();
			if (timeout>TIMEOUT_MILLISECONDS)
				throw new MinecraftTimeoutError("The main thread has stopped working for at least "+timeout+" milliseconds!");
			try {
				TimeUnit.SECONDS.sleep(15);
			} catch (final InterruptedException e) {
				Reference.logger.error(e);
			}
		}
	}

	@Override
	public synchronized void start() {
		super.start();
		AntiTimeoutHandler.instance().setStart(true);
	}
}
