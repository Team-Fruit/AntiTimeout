package net.teamfruit.antitimeout;

import java.util.Map;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkCheckHandler;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class AntiTimeout {
	@EventHandler
	public void init(final FMLInitializationEvent event) {
		FMLCommonHandler.instance().bus().register(this);
		FMLCommonHandler.instance().bus().register(AntiTimeoutHandler.instance());
		new ThreadTimeoutCheck().start();
	}

	@NetworkCheckHandler
	public boolean netCheckHandler(final Map<String, String> mods, final Side side) {
		return true;
	}
}
