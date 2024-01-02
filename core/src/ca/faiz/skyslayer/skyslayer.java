package ca.faiz.skyslayer;

import com.badlogic.gdx.Game;

import java.util.Random;

public class skyslayer extends Game {

	private static skyslayer instance;

	public static Random random = new Random();

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
