package net.teamfruit.antitimeout;

import java.util.concurrent.TimeUnit;

public class ThreadTimeoutCheck extends Thread {

	public ThreadTimeoutCheck() {
		setDaemon(true);
		setName("antitimeout-check");
	}

	private static final long TIMEOUT_MILLISECONDS = TimeUnit.MILLISECONDS.toMinutes(3);

	@Override
	public void run() {
		while (true) {
			final long timeout = System.currentTimeMillis()-AntiTimeoutHandler.instance().getLastTime();
			if (timeout>TIMEOUT_MILLISECONDS)
				throw new MinecraftTimeoutError("The main thread has stopped working for at least"+timeout+"milliseconds!");
			try {
				TimeUnit.MINUTES.sleep(1);
			} catch (final InterruptedException e) {
				Reference.logger.error(e);
			}
		}
	}
}
