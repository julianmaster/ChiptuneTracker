package com.chiptunetracker.core.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.chiptunetracker.core.ChiptuneTracker;
import com.chiptunetracker.menu.ExitListener;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(ChiptuneTracker.getInstance(), config) {
			@Override
			public void exit() {
				new ExitListener(ChiptuneTracker.getInstance().getDataManager().getFileChooser(), ChiptuneTracker.getInstance().getDataManager().getCurrentFile()) {
					@Override
					public void endRun() {
						postRunnable(new Runnable() {
							@Override
							public void run () {
								running = false;
							}
						});
					}
				}.run();
			}
		};
	}
}
