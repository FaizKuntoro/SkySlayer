package ca.faiz.skyslayer;

import com.badlogic.gdx.Game;

public class skyslayer extends Game {

	private static skyslayer instance;

	public skyslayer() {
		instance = this;
	}

	@Override
	public void create() {
		// Set the MenuScreen as the initial screen
		setScreen(new MenuScreen());
	}

	public static skyslayer getInstance() {
		return instance;
	}
}
