package ca.faiz.skyslayer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
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
            shieldTexture1, enemyShieldTexture, damagedShieldTexture, damagedShieldTexture1, damagedShieldTexture2
            , powerUps;

    private float[] backgroundsoffsets = {0,0};
    private float backgroundmaxscrollingspeed;
    private int backgroundoffset;

    private Stage stage;


    private final int WORLD_WITH = 650;
    private final int WORLD_HEIGHT = 1000;


    private Ship playership;
    private  Ship enemyship;

    private PowerUps superspeed;

    private LinkedList<Laser> playerLaserlist;
    private LinkedList<Laser> enemyLaserlist;
    private List<PowerUps> powerUpsList = new ArrayList<>();
    private LinkedList<SuperSpeed> powerUpsLinkedList;


    public GameScreen() {

        atlas = new TextureAtlas("gamescreenobject.atlas");

        laserTexture = atlas.findRegion("laserBlue14b");
        laserEnemyTexture = atlas.findRegion("laserRed14");
        shipTexture = atlas.findRegion("playerShip2_blue");
        shieldTexture = atlas.findRegion("shield1");
        shieldTexture1 = atlas.findRegion("shield2");
        enemyTexture1 = atlas.findRegion("enemyRed1");
        enemyShieldTexture = atlas.findRegion("enemyshield1");
        damagedShieldTexture = atlas.findRegion("playerShip2_damage1");
        damagedShieldTexture1 = atlas.findRegion("playerShip2_damage2");
        damagedShieldTexture2 = atlas.findRegion("playerShip2_damage3");
        powerUps = atlas.findRegion("bold_silver");

        superspeed = new SuperSpeed(5f,100, powerUps,WORLD_WITH / 2 - 200, WORLD_HEIGHT  ,
                30, 50);

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

        playership = new PlayerShip(shipTexture , laserTexture, shieldTexture, damagedShieldTexture, 300, 5,
                WORLD_WITH/2, (WORLD_HEIGHT /2) - 200, 100, 100, 10, 50,
                700,
        0.5f , 0.5f);
        enemyship = new EnemyShip(enemyTexture1, laserEnemyTexture, shieldTexture,10, 5,
                WORLD_WITH/2, (WORLD_HEIGHT /2) + 200, 60, 60, 10, 30,
                400,
                0.5f, 5f);

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
        superspeed.update(deltaTime);


        input(deltaTime);
        playership.draw(batch);
        enemyship.draw(batch);



        backgroundoffset++;
        if(backgroundoffset % WORLD_HEIGHT == 0 ){
            backgroundoffset = 0;
        }

        renderPowerUps(deltaTime);
        collide(deltaTime);
        renderLasers(deltaTime);

        batch.end();

    }

    private void input(float deltaTime){
        float leftLimit, rightLimit, upLimit, downLimit;
        leftLimit = -playership.boundingbox.x;
        downLimit = -playership.boundingbox.y;
        rightLimit = WORLD_WITH - playership.boundingbox.x - playership.boundingbox.width;
        upLimit = WORLD_HEIGHT/2 - playership.boundingbox.y - playership.boundingbox.height;

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && rightLimit > 0) {
            playership.translate(Math.min(playership.movementspeed*deltaTime, rightLimit), 0f);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) && upLimit > 0) {
            playership.translate( 0f, Math.min(playership.movementspeed*deltaTime, upLimit));
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && leftLimit < 0) {
            playership.translate(Math.max(-playership.movementspeed*deltaTime, leftLimit), 0f);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && downLimit < 0) {
            playership.translate(0f, Math.max(-playership.movementspeed*deltaTime, downLimit));
        }
    }

    private void renderExplosion(float deltaTime){

    }

    public void renderPowerUps(float deltaTime){

        if (superspeed.powerUpTimer >= 10f && superspeed.powerUpTimer <= 20f ){
            superspeed.draw(batch);

            superspeed.boundingbox.y -= superspeed.movementspeeds * deltaTime;

            } else if (superspeed.powerUpTimer >= 15f && superspeed.powerUpTimer <= 20f ) {

        }

        if (playership.collide(superspeed.boundingbox)) {
            superspeed.powerUpTimer = 0f;
            superspeed.boundingbox.y =+ 3000;


            playership.laserAttackSpeed -= 0.4f;
        }

        if (superspeed.powerUpTimer >= 5f && playership.laserAttackSpeed < 0.5f ){
            playership.laserAttackSpeed += 0.4f;
            superspeed.powerUpTimer = 5f;
            superspeed.boundingbox.y = WORLD_HEIGHT;
            superspeed.boundingbox.x = +500;

        }

        if (superspeed.powerUpTimer >= 30f){
            superspeed.powerUpTimer = 0f;
            superspeed.boundingbox.y = WORLD_HEIGHT;
        }


    }

    private void renderLasers(float deltaTime){

        if (playership.getShield() >= 11 && playership.getShield() <= 15) {
            playership.setShieldTexture(shieldTexture);
        } else if (playership.getShield() >= 16 && playership.getShield() <=20) {
            playership.setShieldTexture(shieldTexture1);
        } else if (playership.getShield() <= -1 && playership.getShield() >= -8) {
            playership.setDamagedShieldTexture(damagedShieldTexture);
        }else if (playership.getShield() <= -9 && playership.getShield() >= -15) {
            playership.setDamagedShieldTexture(damagedShieldTexture1);
        }else if (playership.getShield() <= -16 && playership.getShield() == -20) {
            playership.setDamagedShieldTexture(damagedShieldTexture2);
        }

        if (enemyship.getShield() >= 1 && enemyship.getShield() <= 10) {
            enemyship.setShieldTexture(enemyShieldTexture);
        }

        if (enemyship.shield <= 0){
            enemyship.boundingbox.y += WORLD_HEIGHT + 300;
            enemyship.boundingbox.x =+ WORLD_WITH + 300;
        }
        if (playership.canFireLaser()){
            if (playership.shield <= 10){
                Laser[] lasers = playership.fireMoreLasers();
                for (Laser laser: lasers){
                    playerLaserlist.add(laser);
                }
            } else {
            }
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
                System.out.println(superspeed.powerUpTimer);
                System.out.println(enemyship.shield);
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
