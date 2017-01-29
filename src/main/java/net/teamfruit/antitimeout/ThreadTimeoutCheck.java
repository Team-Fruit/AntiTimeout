package net.teamfruit.antitimeout;

import java.util.concurrent.TimeUnit;

public class ThreadTimeoutCheck extends Thread {

	public ThreadTimeoutCheck() {
		setDaemon(true);
		setName("antitimeout-check");
	}

	private static final long TIMEOUT_MILLISECONDS = TimeUnit.MINUTES.toMillis(1);

	@Override
	public void run() {
		AntiTimeoutHandler.instance().setStart(true);
		while (true) {
			final long timeout = System.currentTimeMillis()-AntiTimeoutHandler.instance().getLastTime();
			if (timeout>TIMEOUT_MILLISECONDS)
				CrashUtil.crash("Server timeout", new MinecraftTimeoutError("The server thread has stopped working for at least "+timeout+" milliseconds!"));
			try {
				TimeUnit.SECONDS.sleep(15);
			} catch (final InterruptedException e) {
				Reference.logger.error(e);
			}
		}
	}
}
