package io.github.julianmaster.chiptunetracker.lwjgl3;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import io.github.julianmaster.chiptunetracker.ChiptuneTracker;
import io.github.julianmaster.chiptunetracker.menu.ExitListener;

public class CustomLwjgl3Application extends Lwjgl3Application {

    public CustomLwjgl3Application(ApplicationListener listener) {
        super(listener);
    }

    public CustomLwjgl3Application(ApplicationListener listener, Lwjgl3ApplicationConfiguration config) {
        super(listener, config);
    }

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
                    public void run() {
                        CustomLwjgl3Application.super.exit();
                    }
                });
            }
        };
    }
}
