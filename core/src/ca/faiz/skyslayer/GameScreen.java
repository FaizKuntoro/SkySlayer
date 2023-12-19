package ca.faiz.skyslayer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import org.w3c.dom.Text;

import java.security.PrivateKey;
import java.util.LinkedList;
import java.util.ListIterator;

class GameScreen implements Screen {



    private TextureRegion textureRegion;
    //Instansi kelas untuk kamera dan viewport

    private Camera camera;
    private Viewport viewport;
    private SpriteBatch batch;
    private Texture[] backgrounds;
    private Texture backgroundgame, EnemyShipTexture, PlayerShipTexture, PlayerShieldTexture,
            PlayerLaserTexture, EnemyLaserTexture;

    private float[] backgroundsoffsets = {0,0};
    private float backgroundmaxscrollingspeed;
    private int backgroundoffset;

    private Stage stage;


    private final int WORLD_WITH = 650;
    private final int WORLD_HEIGHT = 1000;


    private Ship playership;
    private  Ship enemyship;

    private LinkedList<Laser> playerLaserlist;
    private LinkedList<Laser> enemyLaserlist;

    public GameScreen() {

        stage = new Stage();
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new StretchViewport(WORLD_WITH, WORLD_HEIGHT, camera);

        backgroundgame = new Texture("backgroundgame.png");
        EnemyShipTexture = new Texture("enemyship1.png");
        PlayerShipTexture = new Texture("ship.png");
        PlayerLaserTexture = new Texture("ship.png");
        EnemyLaserTexture = new Texture("laserRed14.png");

        Gdx.input.setInputProcessor(stage);

        backgrounds = new Texture[2];
        backgrounds[0] = new Texture("bintang01.png");
        backgrounds[1] = new Texture("bintang02.png");

        backgroundmaxscrollingspeed = (float)(WORLD_HEIGHT) / 4;

        ImageButton Back = new ImageButton(new TextureRegionDrawable
                (new Texture(Gdx.files.internal("back.png"))));
        Back.setSize(100, 100);
        Back.setPosition( 10 ,
                ((WORLD_HEIGHT -110)));

        Back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Switch to the GameScreen when the play button is clicked
                skyslayer.getInstance().setScreen(new MenuScreen());
            }
        });

        playership = new PlayerShip(PlayerShipTexture , PlayerLaserTexture, 10, 10,
                WORLD_WITH/2, (WORLD_HEIGHT /2) - 200, 100, 100, 0.4f, 4, 45,
        0.5f );
        enemyship = new EnemyShip(EnemyShipTexture, EnemyLaserTexture,10, 10,
                WORLD_WITH/2, (WORLD_HEIGHT /2) + 200, 60, 60, 0.3f, 4, 50, 0.5f);

        playerLaserlist = new LinkedList<>();
        enemyLaserlist = new LinkedList<>();



        stage.addActor(Back);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float deltaTime) {
        batch.begin();




        batch.draw(backgroundgame, 0 , -backgroundoffset, WORLD_WITH, WORLD_HEIGHT);
        batch.draw(backgroundgame, 0 , -backgroundoffset+WORLD_HEIGHT, WORLD_WITH, WORLD_HEIGHT);
        renderBackground(deltaTime);
        stage.draw();

        playership.update(deltaTime);
        enemyship.update(deltaTime);

        playership.draw(batch);
        enemyship.draw(batch);

        backgroundoffset++;
        if(backgroundoffset % WORLD_HEIGHT == 0 ){
            backgroundoffset = 0;


        }

        if (playership.canFireLaser()){
            Laser[] lasers = playership.fireLasers();
            for (Laser laser: lasers){
                playerLaserlist.add(laser);
            }
        }

        if (enemyship.canFireLaser()){
            Laser[] lasers = enemyship.fireLasers();
            for (Laser laser: lasers){
                enemyLaserlist.add(laser);
            }
        }

        ListIterator<Laser> iterator = playerLaserlist.listIterator();
        while (iterator.hasNext()){
            Laser laser = iterator.next();
            laser.draw(batch);
            laser.yPosition += laser.movementSpeed*deltaTime;
            if (laser.yPosition > WORLD_HEIGHT){
                iterator.remove();
            }
        }

        iterator = enemyLaserlist.listIterator();
        while (iterator.hasNext()){
            Laser laser = iterator.next();
            laser.draw(batch);
            laser.yPosition += laser.movementSpeed*deltaTime;
            if (laser.yPosition > WORLD_HEIGHT){
                iterator.remove();
            }
        }

        batch.end();

    }

    private void renderBackground(float deltaTime) {

        backgroundsoffsets[0] += deltaTime * backgroundmaxscrollingspeed / 1 ;
        backgroundsoffsets[1] += deltaTime * backgroundmaxscrollingspeed / 2 ;

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
        batch.dispose();
        backgroundgame.dispose();
        for (Texture background : backgrounds) {
            background.dispose();
        }
        stage.dispose(); 

    }
}
