package net.teamfruit.antitimeout;

import java.io.File;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class ConfigurationHandler {
	private static final ConfigurationHandler INSTANCE = new ConfigurationHandler();
	public static final String VERSION = "1";
	public static Configuration configuration;

	public static ConfigurationHandler instance() {
		return INSTANCE;
	}

	public static final int TIMEOUT_MINUTES_DEFAULT = 3;
	public static int timeoutMinutes = TIMEOUT_MINUTES_DEFAULT;
	public static Property propTimeoutMinutes = null;

	public static final boolean GENERATE_CRASHREPORT_DEFAULT = true;
	public static boolean generateCrashReport = GENERATE_CRASHREPORT_DEFAULT;
	public static Property propGenerateCrashReport = null;

	public static final String[] ON_TIMEOUT_COMMAND_DEFAULT = new String[0];
	public static String[] onTimeoutCommand = ON_TIMEOUT_COMMAND_DEFAULT;
	public static Property propOnTimeoutCommand = null;

	public static void init(final File configFile) {
		if (configuration==null) {
			configuration = new Configuration(configFile, VERSION);
			loadConfiguration();
		}
	}

	public static void loadConfiguration() {
		propTimeoutMinutes = configuration.get("general", "Timeout Minutes", TIMEOUT_MINUTES_DEFAULT,
				"Time to judge timeout (minutes)");
		timeoutMinutes = propTimeoutMinutes.getInt(TIMEOUT_MINUTES_DEFAULT);

		propGenerateCrashReport = configuration.get("general", "Generate crash-report", GENERATE_CRASHREPORT_DEFAULT,
				"Generate a crash report when forcibly terminating with timeout.");
		generateCrashReport = propGenerateCrashReport.getBoolean();

		propOnTimeoutCommand = configuration.get("general", "On Timeout Command", new String[0],
				"This command is executed when the server terminating with timeout. Use Runtime#exec()");
		onTimeoutCommand = propOnTimeoutCommand.getStringList();

		if (configuration.hasChanged()) {
			configuration.save();
		}
	}

	private ConfigurationHandler() {
	}

	@SubscribeEvent
	public void onConfigurationChangedEvent(final ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.modID.equalsIgnoreCase(Reference.MODID)) {
			loadConfiguration();
		}
	}
}
