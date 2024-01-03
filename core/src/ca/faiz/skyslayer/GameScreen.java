package ca.faiz.skyslayer;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.ListIterator;


class GameScreen implements Screen {

    //Instansi kelas untuk kamera dan viewport (T1)
    private Camera camera;
    private Viewport viewport;

    // Untuk timer, berguna untuk mengtrak waktu dan instansi variable waktu (T2)
    private float timeBetweenEnemySpawns = 3f;
    private float enemySpawnTimer = 0;
    float stagetimer = 0;
    private final float TOUCH_MOVEMENT_THRESHOLD = 9f;



    // Objek yang digunakan dalam game, seperti pesawat musuh dan lain-lain (T3)
    private EnemyShipDecorator decoratedEnemyShip;
    private Ship playership;
    private SuperSpeedPowerUps superspeed;
    private MoreBulletsPowerUps morebullets;



    // Texture yang digunakan untuk objek terkait (T4)
    private TextureAtlas atlas;
    private TextureRegion laserTexture, shipTexture, shieldTexture, laserEnemyTexture, enemyTexture1,shieldTexture1, enemyShieldTexture, damagedShieldTexture, damagedShieldTexture1, damagedShieldTexture2
            , powerUps, powerUps1;
    public Texture text123;
    private Texture[] backgrounds;
    private Texture backgroundgame;



    // variabel yang digunakan untuk efek background, pergerakan musuh, dan inisial suatu variabel yang berkaitan dengan
    // nilai statik (T5)
    private final int WORLD_WITH = 650;
    private final int WORLD_HEIGHT = 1000;
    private float[] backgroundsoffsets = {0,0};
    private float backgroundmaxscrollingspeed;
    private int backgroundoffset, canfiremore;


    // Untuk membuat aktor agar tombol2 UI, agar kita bisa berinteraksi dengan tombol2 tersebut (T6)
    private Stage stage;
    private SpriteBatch batch;



    // Array untuk objek2 yang di iterasi (T7)
    private LinkedList<Laser> playerLaserlist;
    private LinkedList<Laser> enemyLaserlist;
    private LinkedList<EnemyShip> enemyShipList;


    // (1) Methode yang Gamescreen berfungsi untuk menampilkan layar utama saat bermain game,
    public GameScreen() {

        // (1.1) Instansi variable texture

        // (1.1.1) instansi variable untuk penggunaan Texture yang nanti digunakan suatu objek
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
        powerUps1 = atlas.findRegion("star_bronze");

        // (1.1.2) instansi background tanpa Atlas
        backgroundgame = new Texture("backgroundgame.png");
        backgrounds = new Texture[2];
        backgrounds[0] = new Texture("bintang01.png");
        backgrounds[1] = new Texture("bintang02.png");
        text123 = new Texture("back.png");

        // (1.1.3) instansi texture untuk UI
        ImageButton Back = new ImageButton(new TextureRegionDrawable
                (new Texture(Gdx.files.internal("back.png"))));


        // (1.2) membuat variable untuk array objek Game
        // (1.2.1-1.2.3) disini vairbale untuk laser pemain dan laser musuh di instansikan
        playerLaserlist = new LinkedList<>();
        enemyLaserlist = new LinkedList<>();
        enemyShipList = new LinkedList<>();


        // (1.3) Instansi Objek Game
        // (1.3.2) instansi Objek powerup dengan menggunakan BUILDER DESIGN PATTERN
        superspeed = new SuperSpeedPowerUps.Builder()
                .setPowerupTimer(10.0f)
                .setMovementSpeed(100)
                .setPowerUps(powerUps)
                .setXPowerup(skyslayer.random.nextFloat()*(WORLD_WITH-10) + 5)
                .setYPowerup(WORLD_HEIGHT )
                .setWidth(30)
                .setHeight(50)
                .build();

        // (1.3.2) Instansi Obejk powerup morebullets dengan metode FACTORY DESIGN PATTERN
        morebullets = MoreBulletsPowerUps.MoreBulletsPowerUpsFactory.createMoreBulletsPowerUps(
               5f,100, powerUps1,skyslayer.random.nextFloat()*(WORLD_WITH-10) + 5 , WORLD_HEIGHT  ,
               30, 50);

        // (1.3.3) Instansi Objek pesawat Player menggunakan SINGLETON DESIGN PATTERN
        playership = PlayerShip.getInstance(shipTexture , laserTexture, shieldTexture, damagedShieldTexture, 300, 5,
                WORLD_WITH/2, (WORLD_HEIGHT /2) - 200, 100, 100, 10, 50,
                1000,
                0.5f , 2f);

        // (1.3.4) Instansi kelas obejk dalam kelas dekorator meggunakan DECORATOR DESIGN PATTERN yang
        // akan digunakan untuk obek enemyship nanti
        decoratedEnemyShip = new EnemyShipDecorator(enemyTexture1, laserEnemyTexture, shieldTexture,10, 5,
                skyslayer.random.nextFloat()*(WORLD_WITH-10) + 5, (WORLD_HEIGHT /2) + 200, 60, 60, 10, 30,
                400,
                0.5f, 5f);


        // (1.4) instansi Objek untuk posisi kamera, viewport dang juga animasi UI dan background
       stage = new Stage();
       batch = new SpriteBatch();
       camera = new OrthographicCamera();
       viewport = new StretchViewport(WORLD_WITH, WORLD_HEIGHT, camera);
       backgroundmaxscrollingspeed = (float)(WORLD_HEIGHT) / 4;


        // (1.5) Tombol dan Background
       Gdx.input.setInputProcessor(stage);
       stage.addActor(Back);
       Back.setSize(100, 100);
       Back.setPosition( 10 ,((WORLD_HEIGHT -110)));

       // (1.5.1) game pindah ke gamescrren saat tombol Back ditekan
       Back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                skyslayer.getInstance().setScreen(new MenuScreen());
            }
        });
    }


    // (2) methode show untuk menampilakan layar gamescreen
    @Override
    public void show() {

    }

    // (3) untuk mengrender objek2 yang telah dibuat
    @Override
    public void render(float deltaTime) {

        // (3.1) Batch untuk rendering objek
        batch.begin();

        // (3.2) Menyertakan background dan UI
        renderMainBackground(deltaTime);
        stage.draw();
        playership.draw(batch);

        // (3.3) Update dan render objek player, powerups, dan musuh
        playership.update(deltaTime);
        superspeed.update(deltaTime);
        morebullets.update(deltaTime);
        stageTimer(deltaTime);
        renderPowerUps(deltaTime);
        renderPowerUps1(deltaTime);
        collide(deltaTime);
        renderLasers(deltaTime);
        damagedTexture(deltaTime);

        // (3.4) Draw dan update musuh
        ListIterator<EnemyShip> enemyShipListIterator = enemyShipList.listIterator();
        while (enemyShipListIterator.hasNext()) {
            EnemyShip enemyship = enemyShipListIterator.next();
            moveEnemies(enemyship, deltaTime);
            enemyship.update(deltaTime);
            enemyship.draw(batch);
        }
        spawnEnemyShip(deltaTime);

        // (3.5) Input dari pengguna
        input(deltaTime);

        // (3.6) Animasi background dan pergerakan musuh
        batch.end();

    }

    // (4) Methode untuk menampilkan dan mengupdate background utama dengan animasi scrolling
    private void renderMainBackground(float deltaTime){

        // (4.1) Menggambar background game
        batch.draw(backgroundgame, 0 , -backgroundoffset, WORLD_WITH, WORLD_HEIGHT);
        batch.draw(backgroundgame, 0 , -backgroundoffset+WORLD_HEIGHT, WORLD_WITH, WORLD_HEIGHT);
        backgroundoffset++;
        if(backgroundoffset % WORLD_HEIGHT == 0 ){
            backgroundoffset = 0;
        }
        backgroundsoffsets[0] += deltaTime * backgroundmaxscrollingspeed / 1 ;
        backgroundsoffsets[1] += deltaTime * backgroundmaxscrollingspeed / 2 ;

        // (4.2) Animasi scrolling background dan menggambar layer latar belakang
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

    // (5) Methode untuk menggerakkan musuh (enemy ship) sesuai dengan waktu dan batasan layar
    private void moveEnemies(EnemyShip enemyship, float deltaTime){

        // (5.1) Menghitung batasan pergerakan musuh sesuai dengan layar
        float leftLimit, rightLimit, upLimit, downLimit;
        leftLimit = -enemyship.boundingbox.x;
        downLimit = (float)WORLD_HEIGHT/2-enemyship.boundingbox.y;
        rightLimit = WORLD_WITH - enemyship.boundingbox.x - enemyship.boundingbox.width;
        upLimit = WORLD_HEIGHT - enemyship.boundingbox.y - enemyship.boundingbox.height;


        // (5.2) Menggerakkan musuh berdasarkan arah dan kecepatan
        float xMove = enemyship.getDirectionVector().x * enemyship.movementspeed * 12 * deltaTime;
        float yMove = enemyship.getDirectionVector().y * enemyship.movementspeed * 12 * deltaTime;

        if (stagetimer >= 15) {
            xMove = enemyship.getDirectionVector().x * decoratedEnemyShip.getMovementspeed() * 12 * deltaTime;
            yMove = enemyship.getDirectionVector().y * decoratedEnemyShip.getMovementspeed() * 12 * deltaTime;
        }

        if (xMove > 0) xMove = Math.min(xMove, rightLimit);
        else xMove = Math.max(xMove,leftLimit);

        if (yMove > 0) yMove = Math.min(yMove, upLimit);
        else yMove = Math.max(yMove,downLimit);

        // mengubah posisi pesawat
        enemyship.translate(xMove,yMove);
    }

    // (6) Methode untuk menghandle input dari pengguna (keyboard dan touch)
    private void input(float deltaTime){

        // (6.1) Mendapatkan batasan pergerakan sesuai dengan layar
        float leftLimit, rightLimit, upLimit, downLimit;
        leftLimit = -playership.boundingbox.x;
        downLimit = -playership.boundingbox.y;
        rightLimit = WORLD_WITH - playership.boundingbox.x - playership.boundingbox.width;
        upLimit = WORLD_HEIGHT/2 - playership.boundingbox.y - playership.boundingbox.height;

        //(6.2) Menggerakkan pemain sesuai dengan input keyboard
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

        // (6.3) Menggerakkan pemain sesuai dengan input touch pada layar
        if (Gdx.input.isTouched()) {
            float xTouchPixels = Gdx.input.getX();
            float yTouchPixels = Gdx.input.getY();

            Vector2 touchPoint = new Vector2(xTouchPixels, yTouchPixels);
            touchPoint = viewport.unproject(touchPoint);

            Vector2 playerShipCentre = new Vector2(
                    playership.boundingbox.x + playership.boundingbox.width/2,
                    playership.boundingbox.y + playership.boundingbox.height/2);

            float touchDistance = touchPoint.dst(playerShipCentre);

            if (touchDistance > TOUCH_MOVEMENT_THRESHOLD) {
                float xTouchDifference = touchPoint.x - playerShipCentre.x;
                float yTouchDifference = touchPoint.y - playerShipCentre.y;

                float xMove = xTouchDifference / touchDistance * playership.movementspeed*5 * deltaTime;
                float yMove = yTouchDifference / touchDistance * playership.movementspeed*5 * deltaTime;

                if (xMove > 0) xMove = Math.min(xMove, rightLimit);
                else xMove = Math.max(xMove,leftLimit);

                if (yMove > 0) yMove = Math.min(yMove, upLimit);
                else yMove = Math.max(yMove,downLimit);

                playership.translate(xMove,yMove);
            }
        }
    }

    // (7) Methode untuk spawning musuh (enemy ship) sesuai dengan waktu tertentu
    private void spawnEnemyShip(float deltaTime){

        // (7.1) Menambahkan musuh baru ke dalam list musuh jika waktu spawn sudah mencapai batas
        enemySpawnTimer += deltaTime;
        if (enemySpawnTimer > timeBetweenEnemySpawns) {
            enemyShipList.add (new EnemyShip(enemyTexture1, laserEnemyTexture, shieldTexture,10, 5,
                    skyslayer.random.nextFloat()*(WORLD_WITH-10) + 5, (WORLD_HEIGHT /2) + 200, 60, 60, 15, 25,
                    350,
                    1.5f, 5f));
            enemySpawnTimer -= timeBetweenEnemySpawns;
        }
    }

    // (8) Methode untuk menghandle waktu dan melakukan perpindahan layar saat mencapai batas tertentu
    private void stageTimer(float deltaTime){

        // (8.1) Mengupdate waktu stage
        stagetimer += deltaTime;

        // (8.2) Pindah ke layar WinScreen jika waktu stage mencapai batas tertentu
        if (stagetimer >= 50){
            skyslayer.getInstance().setScreen(new WinScreen());
        }
    }


    // (9) Methode untuk merender dan mengupdate power-ups yang ada di layar
    public void renderPowerUps(float deltaTime){

        // (9.1) Menggambar dan mengupdate power-up superspeed
        if (superspeed.powerUpTimer >= 10f && superspeed.powerUpTimer <= 20f ){
            superspeed.draw(batch);
            superspeed.boundingbox.y -= superspeed.movementspeeds * deltaTime;
            } else if (superspeed.powerUpTimer >= 15f && superspeed.powerUpTimer <= 20f ) {
        }

        // (9.2) Melakukan aksi terkait power-up superspeed ketika pemain bersentuhan
        if (playership.collide(superspeed.boundingbox)) {
            superspeed.powerUpTimer = 0f;
            superspeed.boundingbox.y =+ 3000;
            playership.laserAttackSpeed -= 0.4f;
        }

        // (9.3) mengspawn ulang powerups dalam waktu tertentu
        if (superspeed.powerUpTimer >= 5f && playership.laserAttackSpeed < 0.5f ){
            playership.laserAttackSpeed += 0.4f;
            superspeed.powerUpTimer = 5f;
            superspeed.boundingbox.y = WORLD_HEIGHT;
            superspeed.boundingbox.x = skyslayer.random.nextFloat()*(WORLD_WITH-10) + 5;
        }

        // (9.3) Mengembalikan keadaan awal power-up superspeed setelah batas waktu tertentu
        if (superspeed.powerUpTimer >= 30f){
            superspeed.powerUpTimer = 0f;
            superspeed.boundingbox.y = WORLD_HEIGHT;
        }
    }

    // (10) Methode untuk merender dan mengupdate power-ups lainnya yang ada di layar
    public void renderPowerUps1(float deltaTime){

        // (10.1) Menggambar dan mengupdate power-up morebullets
        if (morebullets.powerUpTimer >= 10f && morebullets.powerUpTimer <= 20f ){
            morebullets.draw(batch);
            morebullets.boundingbox.y -= morebullets.movementspeeds * deltaTime;
        }

        // (10.2) Melakukan aksi terkait power-up morebullets ketika pemain bersentuhan
        if (playership.collide(morebullets.boundingbox)) {
            morebullets.powerUpTimer = 0f;
            morebullets.boundingbox.y =+ 3000;
            canfiremore = 1;
        }

        if (morebullets.powerUpTimer >= 5f && canfiremore == 1){
            canfiremore = 0;
            morebullets.powerUpTimer = 5f;
            morebullets.boundingbox.y = WORLD_HEIGHT;
            morebullets.boundingbox.x = skyslayer.random.nextFloat()*(WORLD_WITH-10) + 5;
        }

        // (10.3) Mengembalikan keadaan awal power-up morebullets setelah batas waktu tertentu
        if (morebullets.powerUpTimer >= 30f){
            morebullets.powerUpTimer = 0f;
            morebullets.boundingbox.y = WORLD_HEIGHT;
        }


    }


    // (11) mengecek jika player atau musuh terkena damage maka texture akan berubah
    private void damagedTexture(float deltaTime){
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
    }


    // (12) Methode untuk merender dan mengupdate laser yang ditembakkan oleh pemain dan musuh
    private void renderLasers(float deltaTime){

        // (12.1) Menggambar dan mengupdate laser pemain
        if (playership.canFireLaser()){
            if (canfiremore == 1){
                Laser[] morelasers = playership.fireMoreLasers();
                for (Laser laser: morelasers) {
                    playerLaserlist.add(laser);
                }
            } else if ( canfiremore == 0){
                Laser[] lasers = playership.fireLasers();
                for (Laser laser: lasers){
                    playerLaserlist.add(laser);
                }}

        }

        ListIterator<EnemyShip> enemyShipListIterator = enemyShipList.listIterator();
        while (enemyShipListIterator.hasNext()) {
            EnemyShip enemyShip = enemyShipListIterator.next();
            if (enemyShip.canFireLaser()) {
                Laser[] lasers = enemyShip.fireLasers();
                enemyLaserlist.addAll(Arrays.asList(lasers));
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

    // (13) Methode untuk menangani tabrakan antara laser dan objek dalam permainan
    private void collide( float deltaTime){
        ListIterator<Laser> laserListIterator = playerLaserlist.listIterator();
        while (laserListIterator.hasNext()) {
            Laser laser = laserListIterator.next();
            ListIterator<EnemyShip> enemyShipListIterator = enemyShipList.listIterator();
            while (enemyShipListIterator.hasNext()) {
                EnemyShip enemyShip = enemyShipListIterator.next();
                if (enemyShip.collide(laser.boundingbox)) {
                    if (enemyShip.shield <= 0){
                        enemyShipListIterator.remove();
                    }
                    enemyShip.takeDamage(1);
                    laserListIterator.remove();
                    break;
                }
            }

        }
        laserListIterator = enemyLaserlist.listIterator();
        while (laserListIterator.hasNext()) {
            Laser laser = laserListIterator.next();
            if (playership.collide(laser.boundingbox)) {
                //contact with player ship
                playership.takeDamage(1);
                laserListIterator.remove();
            }
        }
    }

    // (14) Methode untuk meresize layar dan viewport
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
