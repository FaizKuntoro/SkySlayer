package ca.faiz.skyslayer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;


class MenuScreen implements Screen {

    // Kamera dan Viewport
    private Camera camera;
    private Viewport viewport;
    private float backgroundmaxscrollingspeed;

    // Texture untuk Ui dan backgrounds
    private Texture[] backgrounds;
    private Texture background, logo, circle ;
    private float[] backgroundsoffsets = {0};

    //  Untuk memberikan animasi dan efek pada Texture
    private Stage stage;
    private SpriteBatch batch;
    private int backgroundoffset;
    private Label playLabel, KelompokLabel;
    private Sprite sprite;

    // Variabel konstan untuk Lebar dan Panjang Screen
    private final int WORLD_WITH = 650;
    private final int WORLD_HEIGHT = 1000;

    public MenuScreen() {


        //meginstasikan obejct sprite dari class Sprite
        sprite = new Sprite();
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        stage = new Stage(new FitViewport(WORLD_WITH, WORLD_HEIGHT));
        backgroundmaxscrollingspeed = (float)(WORLD_HEIGHT) / 4;

        Gdx.input.setInputProcessor(stage);

        Skin skin = new Skin(Gdx.files.internal("arcade-ui.json"));


        // membuat label untuk Button Play
        playLabel = new Label("Play", skin);
        playLabel.setSize(190, 30);
        playLabel.setPosition((WORLD_WITH + 230) / 2, ((WORLD_HEIGHT / 5)));


        // membuat label untuk Button Kelompok
        KelompokLabel = new Label("Kelompok", skin);
        KelompokLabel.setSize(190, 30);
        KelompokLabel.setPosition((WORLD_WITH - 440) / 2, ((WORLD_HEIGHT / 5)));



        viewport = new StretchViewport(WORLD_WITH, WORLD_HEIGHT, camera);


        // Object untuk gambar
        logo = new Texture("sky1.png");
        circle = new Texture("circle.png");
        background = new Texture("bg1alt.png");


        // Menginstasikan Array untuk efek backgrounds
        backgrounds = new Texture[1];
        backgrounds[0] = new Texture("bintang02.png");


        // membuat efek background
        backgroundoffset = 0;
        sprite = new Sprite(circle);
        sprite.setPosition(WORLD_WITH / 2 - sprite.getWidth() / 2,
                ((WORLD_HEIGHT / 9*7) - sprite.getHeight()/2));


        // Membuat button untuk ikon Play
        final ImageButton playButton = new ImageButton
                (new TextureRegionDrawable(new Texture(("play.png"))));
        playButton.setSize(190, 190);
        playButton.setPosition( (WORLD_WITH + 100 )/ 2 ,
                ((WORLD_HEIGHT / 2) - sprite.getHeight()/2));


        // Respon input dari user berupa (click, hover)
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                skyslayer.getInstance().setScreen(new GameScreen());
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor toActor) {
                stage.addActor(playLabel);
                float originalWidth = playButton.getWidth();
                float originalHeight = playButton.getHeight();
                playButton.setSize(225, 225);
                playButton.setPosition(playButton.getX() - (playButton.getWidth() - originalWidth) / 2f,
                        playButton.getY() - (playButton.getHeight() - originalHeight) / 2f);
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                playLabel.remove();
                playButton.setSize(190, 190);
                playButton.setPosition( (WORLD_WITH + 100 )/ 2 ,
                        ((WORLD_HEIGHT / 2) - sprite.getHeight()/2));
            }

        });

        // Membuat Label dari ikon Kelompok
        final ImageButton listkel = new ImageButton
                (new TextureRegionDrawable(new Texture(("listkel.png"))));
        listkel.setSize(200, 200);
        listkel.setPosition( WORLD_WITH / 2 - sprite.getWidth() / 2,
                ((WORLD_HEIGHT / 2) - sprite.getHeight()/2));

        // Respon input dari user berupa (click, hover)
        listkel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                skyslayer.getInstance().setScreen(new KelompokScreen());
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor toActor) {
                stage.addActor(KelompokLabel);
                float originalWidth = listkel.getWidth();
                float originalHeight = listkel.getHeight();
                listkel.setSize(250, 250);
                listkel.setPosition(listkel.getX() - (listkel.getWidth() - originalWidth) / 2f,
                        listkel.getY() - (listkel.getHeight() - originalHeight) / 2f);

            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                KelompokLabel.remove();
                listkel.setSize(200, 200);
                listkel.setPosition(WORLD_WITH / 2 - sprite.getWidth() / 2,
                        ((WORLD_HEIGHT / 2) - sprite.getHeight()/2));
            }


        });

        // Memanggil Label
        stage.addActor(playButton);
        stage.addActor(listkel);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float deltaTime) {

        // Membuat Efek Background
        backgroundoffset++;
        if(backgroundoffset % WORLD_HEIGHT == 0 ){
            backgroundoffset = 0;
        }

        // Membuat render
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

        // Background array efek
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
        // Dispose of resources to prevent memory leaks

        // Dispose of textures
        logo.dispose();
        circle.dispose();
        background.dispose();
        for (Texture texture : backgrounds) {
            texture.dispose();
        }

        // Dispose of sprite batch and stage
        batch.dispose();
        stage.dispose();
    }

}
