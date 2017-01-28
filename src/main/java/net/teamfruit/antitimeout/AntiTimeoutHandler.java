package net.teamfruit.antitimeout;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ServerTickEvent;

public class AntiTimeoutHandler {
	private static final AntiTimeoutHandler INSTANCE = new AntiTimeoutHandler();

	private AntiTimeoutHandler() {
	}

	public static AntiTimeoutHandler instance() {
		return INSTANCE;
	}

	private long lastTime = System.currentTimeMillis();
	private int tickCount;

	@SubscribeEvent
	public void onServerTick(final ServerTickEvent event) {
		if (this.tickCount>=20) {
			this.lastTime = System.currentTimeMillis();
			this.tickCount = 0;
		}
		this.tickCount++;
	}

	public long getLastTime() {
		return this.lastTime;
	}
}
