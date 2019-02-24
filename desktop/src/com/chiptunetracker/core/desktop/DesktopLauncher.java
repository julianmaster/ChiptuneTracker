package com.chiptunetracker.core.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.chiptunetracker.core.ChiptuneTracker;
import com.chiptunetracker.menu.ExitListener;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.useGL30 = true;
		config.addIcon("icon_128.png", Files.FileType.Internal);
		config.addIcon("icon_32.png", Files.FileType.Internal);
		config.addIcon("icon_16.png", Files.FileType.Internal);
		new LwjglApplication(ChiptuneTracker.getInstance(), config) {
			@Override
			public void exit() {
				new ExitListener(ChiptuneTracker.getInstance().getDataManager().getFileChooser(), ChiptuneTracker.getInstance().getDataManager()) {
					@Override
					public void additionalYesActions() {
						endRun();
					}

					@Override
					public void additionalNoActions() {
						endRun();
					}

					public void endRun() {
						postRunnable(new Runnable() {
							@Override
							public void run () {
								running = false;
							}
						});
					}
				};
			}
		};
	}
}
