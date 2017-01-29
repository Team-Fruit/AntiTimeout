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

	private boolean start;
	private long lastTime;
	private int tickCount;

	@SubscribeEvent
	public void onServerTick(final ServerTickEvent event) {
		if (this.start) {
			if (this.tickCount>=20) {
				this.lastTime = System.currentTimeMillis();
				this.tickCount = 0;
			}
			this.tickCount++;
		}
	}

	public void setStart(final boolean b) {
		this.start = b;
		this.lastTime = System.currentTimeMillis();
	}

	public long getLastTime() {
		return this.lastTime;
	}
}
