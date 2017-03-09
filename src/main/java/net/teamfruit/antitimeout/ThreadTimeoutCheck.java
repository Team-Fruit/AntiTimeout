package net.teamfruit.antitimeout;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import net.minecraft.server.MinecraftServer;

public class ThreadTimeoutCheck extends Thread {

	public static boolean timeout;

	private volatile boolean state = true;

	public ThreadTimeoutCheck() {
		setDaemon(true);
		setName("antitimeout-check");
	}

	public void safeStop() {
		this.state = false;
	}

	@Override
	public void run() {
		AntiTimeoutHandler.instance().setStart(true);
		while (this.state) {
			final long timeout = System.currentTimeMillis()-AntiTimeoutHandler.instance().getLastTime();
			if (timeout>TimeUnit.MINUTES.toMillis(ConfigurationHandler.timeoutMinutes)) {
				ThreadTimeoutCheck.timeout = true;

				if (ConfigurationHandler.createStampFile) {
					final File file = MinecraftServer.getServer().getFile(ConfigurationHandler.stampFileName);
					try {
						file.createNewFile();
					} catch (final IOException e) {
						Reference.logger.error(e);
					}
				}

				if (ConfigurationHandler.forceSaveServer)
					CrashUtil.save();
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
