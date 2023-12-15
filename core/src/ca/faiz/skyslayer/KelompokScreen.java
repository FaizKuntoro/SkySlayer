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

class KelompokScreen implements Screen {
    private Stage stage;
    private Camera camera;
    private Viewport viewport;
    private SpriteBatch batch;
    private Texture[] backgrounds;
    private Texture background, kelompokbg;

    private float[] backgroundsoffsets = {0};
    private float backgroundmaxscrollingspeed;
    private int backgroundoffset;



    private Sprite sprite;

    private final int WORLD_WITH = 650;
    private final int WORLD_HEIGHT = 1000;


    public KelompokScreen() {

        stage = new Stage(new FitViewport(WORLD_WITH, WORLD_HEIGHT));
        Gdx.input.setInputProcessor(stage);

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new StretchViewport(WORLD_WITH, WORLD_HEIGHT, camera);
        background = new Texture("bg1alt.png");

        backgrounds = new Texture[1];
        backgrounds[0] = new Texture("bintang01.png");
        sprite = new Sprite();
        backgroundmaxscrollingspeed = (float)(WORLD_HEIGHT) / 4;

        kelompokbg = new Texture("kelompokbg.png");



        backgroundoffset = 0;

        ImageButton playButton = new ImageButton(new TextureRegionDrawable(new Texture(Gdx.files.internal("back.png"))));
        playButton.setSize(95, 95);
        playButton.setPosition( 120 ,
                ((WORLD_HEIGHT -110)));

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Switch to the GameScreen when the play button is clicked
                skyslayer.getInstance().setScreen(new MenuScreen());
            }
        });

        ImageButton listkel = new ImageButton(new TextureRegionDrawable(new Texture(Gdx.files.internal("listkel.png"))));
        listkel.setSize(100, 100);
        listkel.setPosition( 10,
                ((WORLD_HEIGHT -110 )));

        listkel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Switch to the GameScreen when the play button is clicked
                skyslayer.getInstance().setScreen(new MenuScreen());
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
        batch.draw(background, 0 , -backgroundoffset, WORLD_WITH, WORLD_HEIGHT);
        batch.draw(background, 0 , -backgroundoffset+WORLD_HEIGHT, WORLD_WITH, WORLD_HEIGHT);
        renderBackground(deltaTime);
        batch.draw(kelompokbg,WORLD_WITH / 2 - sprite.getWidth() / 2 - 325,
                ((WORLD_HEIGHT / 10 ) - sprite.getHeight()/2) - 170);
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
        dispose();

    }

    @Override
    public void dispose() {
        // Dispose of resources to prevent memory leaks

        // Dispose of textures
        background.dispose();
        kelompokbg.dispose();
        for (Texture texture : backgrounds) {
            texture.dispose();
        }

        // Dispose of sprite batch and stage
        batch.dispose();
        stage.dispose();
    }
}

