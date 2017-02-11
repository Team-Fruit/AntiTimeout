package net.teamfruit.antitimeout;

import java.util.concurrent.TimeUnit;

public class ThreadTimeoutCheck extends Thread {

	public static boolean timeout;

	public ThreadTimeoutCheck() {
		setDaemon(true);
		setName("antitimeout-check");
	}

	@Override
	public void run() {
		AntiTimeoutHandler.instance().setStart(true);
		while (true) {
			final long timeout = System.currentTimeMillis()-AntiTimeoutHandler.instance().getLastTime();
			if (timeout>ConfigurationHandler.timeoutMinutes) {
				ThreadTimeoutCheck.timeout = true;
				if (ConfigurationHandler.generateCrashReport)
					CrashUtil.crash("Server timeout", new MinecraftTimeoutError("The server thread has stopped working for at least "+timeout+" milliseconds!"));
				else
					CrashUtil.exit();
			}
			try {
				TimeUnit.SECONDS.sleep(15);
			} catch (final InterruptedException e) {
				Reference.logger.error(e);
			}
		}
	}
}
