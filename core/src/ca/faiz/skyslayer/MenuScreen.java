package ca.faiz.skyslayer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

class MenuScreen implements Screen {
    private Stage stage;
    private Camera camera;
    private Viewport viewport;
    private SpriteBatch batch;
    private Texture[] backgrounds;
    private Texture background, logo, circle, background1;

    private float[] backgroundsoffsets = {0};
    private float backgroundmaxscrollingspeed;
    private int backgroundoffset;



    private Sprite sprite;

    private final int WORLD_WITH = 650;
    private final int WORLD_HEIGHT = 1000;


    public MenuScreen() {

        stage = new Stage(new FitViewport(WORLD_WITH, WORLD_HEIGHT));
        Gdx.input.setInputProcessor(stage);

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new StretchViewport(WORLD_WITH, WORLD_HEIGHT, camera);
        background = new Texture("bg1alt.png");

        backgrounds = new Texture[1];
        backgrounds[0] = new Texture("bintang02.png");


        backgroundmaxscrollingspeed = (float)(WORLD_HEIGHT) / 4;

        background1 = new Texture("bg4.png");
        logo = new Texture("sky1.png");
        circle = new Texture("circle.png");
        backgroundoffset = 0;
        sprite = new Sprite(circle);
        sprite.setPosition(WORLD_WITH / 2 - sprite.getWidth() / 2,
                ((WORLD_HEIGHT / 9*7) - sprite.getHeight()/2));

        ImageButton playButton = new ImageButton(new TextureRegionDrawable(new Texture(Gdx.files.internal("play.png"))));
        playButton.setSize(190, 190);
        playButton.setPosition( (WORLD_WITH + 100 )/ 2 ,
                ((WORLD_HEIGHT / 2) - sprite.getHeight()/2));

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Switch to the GameScreen when the play button is clicked
                skyslayer.getInstance().setScreen(new GameScreen());
            }
        });

        ImageButton listkel = new ImageButton(new TextureRegionDrawable(new Texture(Gdx.files.internal("listkel.png"))));
        listkel.setSize(200, 200);
        listkel.setPosition( WORLD_WITH / 2 - sprite.getWidth() / 2,
                ((WORLD_HEIGHT / 2) - sprite.getHeight()/2));

        listkel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Switch to the GameScreen when the play button is clicked
                skyslayer.getInstance().setScreen(new KelompokScreen());
            }
        });

        stage.addActor(playButton);
        stage.addActor(listkel);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float deltaTime) {




        backgroundoffset++;
        if(backgroundoffset % WORLD_HEIGHT == 0 ){
            backgroundoffset = 0;


        }

        batch.begin();
        stage.act(deltaTime);
        stage.draw();
        batch.draw(background, 0 , -backgroundoffset, WORLD_WITH, WORLD_HEIGHT);
        batch.draw(background, 0 , -backgroundoffset+WORLD_HEIGHT, WORLD_WITH, WORLD_HEIGHT);
        renderBackground(deltaTime);
        batch.draw(logo, WORLD_WITH / 2 - sprite.getWidth() / 2,
                ((WORLD_HEIGHT / 9*7 ) - sprite.getHeight()/2));
        sprite.rotate(60 * deltaTime);
        sprite.draw(batch);
        stage.draw();
        batch.end();


    }

    private void renderBackground(float deltaTime) {

        backgroundsoffsets[0] += deltaTime * backgroundmaxscrollingspeed / 1 ;


        int layer;
        for (layer = 0; layer < backgroundsoffsets.length; layer++) {
            if (backgroundsoffsets[layer] > WORLD_HEIGHT) {
                backgroundsoffsets[layer] = 0;
            }
            batch.draw(backgrounds[layer], 0, -backgroundsoffsets[layer],
                    WORLD_WITH, WORLD_HEIGHT);
            batch.draw(backgrounds[layer], 0, -backgroundsoffsets[layer] + WORLD_HEIGHT,
                    WORLD_WITH, WORLD_HEIGHT);
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        batch.setProjectionMatrix(camera.combined);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
