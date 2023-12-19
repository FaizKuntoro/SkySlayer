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


    private TextureAtlas atlas;
    private TextureRegion laserTexture, shipTexture, shieldTexture, laserEnemyTexture, enemyTexture1,
            shieldTexture1, enemyShieldTexture;

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

        atlas = new TextureAtlas("gamescreenobject.atlas");

        laserTexture = atlas.findRegion("laserBlue14b");
        laserEnemyTexture = atlas.findRegion("laserRed14");
        shipTexture = atlas.findRegion("playerShip2_blue");
        shieldTexture = atlas.findRegion("shield1");
        shieldTexture1 = atlas.findRegion("shield2");
        enemyTexture1 = atlas.findRegion("enemyRed1");
        enemyShieldTexture = atlas.findRegion("enemyshield1");

        stage = new Stage();
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new StretchViewport(WORLD_WITH, WORLD_HEIGHT, camera);

        backgroundgame = new Texture("backgroundgame.png");

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

        playership = new PlayerShip(shipTexture , laserTexture, shieldTexture, 10, 5,
                WORLD_WITH/2, (WORLD_HEIGHT /2) - 200, 100, 100, 10, 50,
                500,
        0.5f , 0.2f);
        enemyship = new EnemyShip(enemyTexture1, laserEnemyTexture, shieldTexture,10, 0,
                WORLD_WITH/2, (WORLD_HEIGHT /2) + 200, 60, 60, 10, 30,
                100,
                0.7f, 5f);

        playerLaserlist = new LinkedList<>();
        enemyLaserlist = new LinkedList<>();



        stage.addActor(Back);
        batch = new SpriteBatch();

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

        collide(deltaTime);
        renderLasers(deltaTime);

        batch.end();

    }

    private void renderExplosion(float deltaTime){

    }

    private void renderLasers(float deltaTime){
        if (playership.getShield() >= 0 && playership.getShield() <= 10) {
            playership.setShieldTexture(shieldTexture);
        } else if (playership.getShield() >= 6 && playership.getShield() <= 20) {
            playership.setShieldTexture(shieldTexture1);
        }

        if (enemyship.getShield() >= 1 && enemyship.getShield() <= 10) {
            enemyship.setShieldTexture(enemyShieldTexture);
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

        for (int i = 0 ; i < playerLaserlist.size(); i++) {
            Laser laser = playerLaserlist.get(i);

            laser.draw(batch);

            laser.boundingbox.y += laser.movementSpeed * deltaTime;

            if (laser.boundingbox.y > WORLD_HEIGHT) {
                playerLaserlist.remove(i);
                i--; //
            }
        }

        for (int i = 0; i < enemyLaserlist.size(); i++) {
            Laser laser = enemyLaserlist.get(i);

            laser.draw(batch);

            laser.boundingbox.y -= laser.movementSpeed * deltaTime;

            if (laser.boundingbox.y < 0f ){
                enemyLaserlist.remove(i);
                i--;
            }
        }
    }

    private void collide(float deltaTime){
        for (int i = 0 ; i < playerLaserlist.size(); i++) {
            Laser laser = playerLaserlist.get(i);

            if (enemyship.collide(laser.getBoundinbox())){
                enemyship.takeDamage(2);
                playerLaserlist.remove(i);
                i--;
            }
        }

        for (int i = 0 ; i < enemyLaserlist.size(); i++) {
            Laser laser = enemyLaserlist.get(i);

            if (playership.collide(laser.getBoundinbox())){
                playership.takeDamage(2);
                enemyLaserlist.remove(i);
                i--;
            }
        }
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


    }
}
