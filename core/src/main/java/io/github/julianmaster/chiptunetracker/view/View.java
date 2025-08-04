package io.github.julianmaster.chiptunetracker.view;

import com.asciiterminal.ui.AsciiSelectableTerminalButton;
import com.asciiterminal.ui.AsciiTerminal;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.github.julianmaster.chiptunetracker.ChiptuneTracker;

import java.util.ArrayList;
import java.util.List;

public abstract class View extends ScreenAdapter {

	public static final Color INDIGO = new Color(0x3D51B5FF);
	public static final Color DEEP_ORANGE = new Color(0xFF7808FF);
	public static final String DOT = String.valueOf((char)239);

	protected final ChiptuneTracker chiptuneTracker;
	protected final AsciiTerminal asciiTerminal;
	private List<Actor> listActor = new ArrayList<>();

	// Panels buttons
	protected AsciiSelectableTerminalButton buttonMenuView;
	protected AsciiSelectableTerminalButton buttonSampleView;
	protected AsciiSelectableTerminalButton buttonPatternView;

	public View(ChiptuneTracker chiptuneTracker) {
		this.chiptuneTracker = chiptuneTracker;
		this.asciiTerminal = chiptuneTracker.getAsciiTerminal();
		createSwitchViewButtons();
	}

	public void createSwitchViewButtons() {
		buttonMenuView = new AsciiSelectableTerminalButton(asciiTerminal, String.valueOf((char)255) + "Menu", 1, 0, Color.WHITE, DEEP_ORANGE, DEEP_ORANGE, DEEP_ORANGE, INDIGO);
		buttonMenuView.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				chiptuneTracker.setScreen(chiptuneTracker.getMenuView());
			}
		});
		listActor.add(buttonMenuView);

		buttonSampleView = new AsciiSelectableTerminalButton(asciiTerminal, String.valueOf((char)13) + "Sample", 7, 0, Color.WHITE, DEEP_ORANGE, DEEP_ORANGE, DEEP_ORANGE, INDIGO);
		buttonSampleView.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				chiptuneTracker.setScreen(chiptuneTracker.getSampleView());
			}
		});
		listActor.add(buttonSampleView);

		buttonPatternView = new AsciiSelectableTerminalButton(asciiTerminal, String.valueOf((char)14) + "Pattern", 15, 0, Color.WHITE, DEEP_ORANGE, DEEP_ORANGE, DEEP_ORANGE, INDIGO);
		buttonPatternView.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				chiptuneTracker.setScreen(chiptuneTracker.getPatternView());
			}
		});
		listActor.add(buttonPatternView);
	}

	@Override
	public void show () {
		for(Actor actor : listActor) {
			asciiTerminal.addActor(actor);
		}
	}

	@Override
	public void render(float delta) {
		for(int i = 0; i < ChiptuneTracker.WINDOW_WIDTH; i++) {
			asciiTerminal.write(i, 0, ' ', Color.WHITE, INDIGO);
			asciiTerminal.write(i, ChiptuneTracker.WINDOW_HEIGHT - 1, ' ', Color.WHITE, INDIGO);
		}
	}

	@Override
	public void hide() {
		for(Actor actor : listActor) {
			actor.remove();
		}
	}

	public List<Actor> getListActor() {
		return listActor;
	}

	public void setListActorTouchables(Touchable touchable) {
		for(Actor actor : listActor) {
			actor.setTouchable(touchable);
		}
	}
}
