package net.teamfruit.antitimeout;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Reference {
	public static final String MODID = "antitimeout";
	public static final String NAME = "AntiTimeout";
	public static final String VERSION = "${version}";
	public static final String FORGE = "${forgeversion}";
	public static final String MINECRAFT = "${mcversion}";

	public static Logger logger = LogManager.getLogger(MODID);
}
