package net.teamfruit.antitimeout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ThreadShutdownHook extends Thread {

	@Override
	public void run() {
		if (ThreadTimeoutCheck.timeout&&ConfigurationHandler.onTimeoutCommand.length>0) {
			Reference.logger.info("Execute specified command at timeout ...");
			try {
				final Process process = Runtime.getRuntime().exec(ConfigurationHandler.onTimeoutCommand);
				new Thread() {
					@Override
					public void run() {
						try {
							final BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
							String line;
							while ((line = br.readLine())!=null)
								System.out.println(line);
						} catch (final IOException e) {
							Reference.logger.error("Command output failed!", e);
						}
					}
				}.start();
				process.waitFor();
			} catch (final IOException e) {
				Reference.logger.error("Command execution failed!", e);
			} catch (final InterruptedException e) {
				Reference.logger.error(e);
			}
			Reference.logger.info("Command execution ended.");
		}
	}
}
