package ca.faiz.skyslayer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

import java.util.Random;

public class SkySlayerGame  extends Game {

    GameScreen gameScreen;

    public static Random random = new Random();

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void create() {

        gameScreen = new GameScreen();
        setScreen(gameScreen);

    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void setScreen(Screen screen) {
        super.setScreen(screen);
    }

    @Override
    public Screen getScreen() {
        return super.getScreen();
    }
}
