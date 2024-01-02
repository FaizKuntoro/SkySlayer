package ca.faiz.skyslayer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

class EnemyShip extends Ship {

    private List<EnemyShip> enemyShips;

    Vector2 directionVector;
    float timeSinceLastDirectionChange = 0 ;
    float directionChangeFrequency = 0.75f;


    public EnemyShip(TextureRegion shipTexture, TextureRegion LaserTexture, TextureRegion ShieldTexture,
                     int movementspeed, int shield, float xCentre, float yCentre, float width, float height,
                     float laserWidth, float laserHeight, float laserMovementSpeed, float laserAttackSpeed, float regenTimer) {
        super(shipTexture, LaserTexture, ShieldTexture, movementspeed, shield, xCentre,
                yCentre, width, height, laserWidth, laserHeight, laserMovementSpeed, laserAttackSpeed, regenTimer);

        directionVector = new Vector2(0, -1);

    }

    public EnemyShip(TextureRegion shipTexture, TextureRegion LaserTexture, TextureRegion ShieldTexture,
                     int movementspeed, int shield, float xCentre, float yCentre, float width, float height,
                     float laserWidth, float laserHeight, float laserMovementSpeed, float laserAttackSpeed, float regenTimer, float count) {
        super(shipTexture, LaserTexture, ShieldTexture, movementspeed, shield, xCentre,
                yCentre, width, height, laserWidth, laserHeight, laserMovementSpeed, laserAttackSpeed, regenTimer);

    }

    public Vector2 getDirectionVector() {
        return directionVector;
    }

    private void randomizeDirectionVector() {
        double bearing = skyslayer.random.nextDouble()*6.283185; //0 to 2*PI
        directionVector.x = (float)Math.sin(bearing);
        directionVector.y = (float)Math.cos(bearing);
    }

    @Override
    public void EnemyShipGroup() {
        enemyShips = new ArrayList<>();
    }

    // Spawn a wing pattern of enemy ships
    public void spawnWingPattern(TextureRegion shipTexture, TextureRegion laserTexture,
                                 TextureRegion shieldTexture, int movementSpeed, int shield,
                                 float x, float y, float width, float height,
                                 float laserWidth, float laserHeight, float laserMovementSpeed,
                                 float laserAttackSpeed, float regenTimer) {
        EnemyShip leftShip = new EnemyShip(shipTexture, laserTexture, shieldTexture,
                movementSpeed, shield, x, y, width, height,
                laserWidth, laserHeight, laserMovementSpeed, laserAttackSpeed, regenTimer);

        EnemyShip rightShip = new EnemyShip(shipTexture, laserTexture, shieldTexture,
                movementSpeed, shield, x, y, width, height,
                laserWidth, laserHeight, laserMovementSpeed, laserAttackSpeed, regenTimer);


        enemyShips.add(leftShip);
        enemyShips.add(rightShip);
    }

    // Spawn a horizontal pattern of enemy ships
    public void spawnHorizontalPattern(TextureRegion shipTexture, TextureRegion laserTexture,
                                       TextureRegion shieldTexture, int movementSpeed, int shield,
                                       float x, float y, float width, float height,
                                       float laserWidth, float laserHeight, float laserMovementSpeed,
                                       float laserAttackSpeed, float regenTimer, int count) {
        float spacing = width * 1.5f;

        for (int i = 0; i < count; i++) {
            EnemyShip enemyShip = new EnemyShip(shipTexture, laserTexture, shieldTexture,
                    movementSpeed, shield, x + i * spacing, y, width, height,
                    laserWidth, laserHeight, laserMovementSpeed, laserAttackSpeed, regenTimer);

            enemyShips.add(enemyShip);
        }
    }

    // Spawn a vertical pattern of enemy ships
    public void spawnVerticalPattern(TextureRegion shipTexture, TextureRegion laserTexture,
                                     TextureRegion shieldTexture, int movementSpeed, int shield,
                                     float x, float y, float width, float height,
                                     float laserWidth, float laserHeight, float laserMovementSpeed,
                                     float laserAttackSpeed, float regenTimer, int count) {
        float spacing = height * 1.5f;

        for (int i = 0; i < count; i++) {
            EnemyShip enemyShip = new EnemyShip(shipTexture, laserTexture, shieldTexture,
                    movementSpeed, shield, x, y + i * spacing, width, height,
                    laserWidth, laserHeight, laserMovementSpeed, laserAttackSpeed, regenTimer);

            enemyShips.add(enemyShip);
        }
    }

    @Override
    public Laser[] fireLasers() {
        Laser[] laser = new Laser[2];

        laser[0] = new Laser(laserMovementSpeed, boundingbox.x, boundingbox.y,
                laserWidth, laserHeight, laserTexture
        );

        laser[1] = new Laser(laserMovementSpeed, boundingbox.x +boundingbox.width, boundingbox.y,
                laserWidth, laserHeight, laserTexture
        );

        timeSinceLastShots = 0;
        return laser;
    }

    @Override
    public void draw(Batch batch) {
        batch.draw(shipTexture, boundingbox.x, boundingbox.y, boundingbox.width, boundingbox.height);
        if (shield > 0 && shield < 3) {
            batch.draw(shieldTexture, boundingbox.x, boundingbox.y, boundingbox.width, boundingbox.height);
        }
    }

    @Override
    public Laser[] fireMoreLasers(){
        Laser[] morelaser = new Laser[5];

        morelaser[0] = new Laser(laserMovementSpeed, boundingbox.x, boundingbox.y + laserHeight,
                laserWidth, laserHeight, laserTexture
        );

        morelaser[1] = new Laser(laserMovementSpeed, boundingbox.x +boundingbox.width, boundingbox.y + laserHeight,
                laserWidth, laserHeight, laserTexture
        );

        morelaser[2] = new Laser(laserMovementSpeed, boundingbox.x + boundingbox.width/2, boundingbox.y + laserHeight,
                laserWidth, laserHeight, laserTexture
        );
        morelaser[3] = new Laser(laserMovementSpeed, boundingbox.x + boundingbox.width/2 + 200, boundingbox.y + laserHeight,
                laserWidth, laserHeight, laserTexture
        );
        morelaser[4] = new Laser(laserMovementSpeed, boundingbox.x + boundingbox.width/2 + 100, boundingbox.y + laserHeight,
                laserWidth, laserHeight, laserTexture
        );

        timeSinceLastShots = 0;
        return morelaser;
    }



    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        timeSinceLastDirectionChange += deltaTime;
        if (timeSinceLastDirectionChange > directionChangeFrequency) {
            randomizeDirectionVector();
            timeSinceLastDirectionChange -= directionChangeFrequency;
        }
        timeSinceLastShots += deltaTime;
        shieldRegenInterval += deltaTime;

        if (shieldRegenInterval >= regenTimer) {
            if (shield <= 10) {
                shield += 1;
                shieldRegenInterval -= regenTimer;
            }
        }
    }
}
