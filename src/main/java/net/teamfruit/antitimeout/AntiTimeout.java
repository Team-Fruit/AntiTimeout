package net.teamfruit.antitimeout;

import java.util.Map;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLModDisabledEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.network.NetworkCheckHandler;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION, canBeDeactivated = true)
public class AntiTimeout {

	@Instance(Reference.MODID)
	public static AntiTimeout instance;

	public ThreadTimeoutCheck timeoutCheck = new ThreadTimeoutCheck();
	public ThreadShutdownHook shutdownHook = new ThreadShutdownHook();

	@EventHandler
	public void preInit(final FMLPreInitializationEvent event) {
		ConfigurationHandler.init(event.getSuggestedConfigurationFile());
		Runtime.getRuntime().addShutdownHook(this.shutdownHook);
	}

	@EventHandler
	public void init(final FMLInitializationEvent event) {
		FMLCommonHandler.instance().bus().register(this);
		FMLCommonHandler.instance().bus().register(AntiTimeoutHandler.instance());
	}

	@EventHandler
	public void onServerLoad(final FMLServerStartedEvent event) {
		if (this.timeoutCheck==null)
			this.timeoutCheck = new ThreadTimeoutCheck();
		this.timeoutCheck.start();
	}

	@EventHandler
	public void onDisable(final FMLModDisabledEvent event) {
		this.timeoutCheck.safeStop();
		this.timeoutCheck = null;
		AntiTimeoutHandler.instance().setStart(false);
		Runtime.getRuntime().removeShutdownHook(this.shutdownHook);
		Reference.logger.info("Disable");
	}

	@NetworkCheckHandler
	public boolean netCheckHandler(final Map<String, String> mods, final Side side) {
		return true;
	}
}
