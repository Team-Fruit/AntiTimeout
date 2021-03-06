package net.teamfruit.antitimeout;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.crash.CrashReport;
import net.minecraft.util.ReportedException;

public class CrashUtil {
	private static File crashreportDirectory = FMLCommonHandler.instance().getMinecraftServerInstance().getFile("crash-reports");
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");

	private CrashUtil() {
	}

	public static void crash(final String description, final Throwable t) {
		Reference.logger.error("Encountered an unexpected exception", t);
		try {
			CrashReport crashreport = null;
			if (t instanceof ReportedException)
				crashreport = FMLCommonHandler.instance().getMinecraftServerInstance().addServerInfoToCrashReport(((ReportedException) t).getCrashReport());
			else
				crashreport = FMLCommonHandler.instance().getMinecraftServerInstance().addServerInfoToCrashReport(new CrashReport(description, t));
			final File file = new File(crashreportDirectory, "crash-"+dateFormat.format(new Date())+"-server.txt");
			if (crashreport.saveToFile(file))
				Reference.logger.error("This crash report has been saved to: "+file.getAbsolutePath());
			else
				Reference.logger.error("We were unable to save this crash report to disk.");
		} catch (final Exception e) {
			Reference.logger.error(e);
		}
		exit();
	}

	public static void save() {
		try {
			FMLCommonHandler.instance().getMinecraftServerInstance().stopServer();
		} catch (final Throwable throwable) {
			Reference.logger.error("Exception stopping the server", throwable);
		}
	}

	public static void exit() {
		FMLCommonHandler.instance().exitJava(1, false);
	}
}