package ca.faiz.skyslayer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

class PlayerShip extends Ship {

    private static PlayerShip instance;



    private PlayerShip(TextureRegion shipTexture, TextureRegion LaserTexture, TextureRegion ShieldTexture, TextureRegion damagedShipTexture,
                      float movementspeed, int shield, float xCentre, float yCentre, float width, float height,
                      float laserWidth, float laserHeight, float laserMovementSpeed, float laserAttackSpeed, float regenTimer) {
        super(shipTexture, LaserTexture, ShieldTexture, movementspeed, shield, xCentre, yCentre,
                width, height, laserWidth, laserHeight, laserMovementSpeed, laserAttackSpeed, regenTimer);

    }

    public static PlayerShip getInstance(TextureRegion shipTexture, TextureRegion laserTexture, TextureRegion shieldTexture,
                                         TextureRegion damagedShipTexture, float movementSpeed, int shield, float xCentre,
                                         float yCentre, float width, float height, float laserWidth, float laserHeight,
                                         float laserMovementSpeed, float laserAttackSpeed, float regenTimer) {
        // Lazy initialization: create the instance only if it hasn't been created yet
        if (instance == null) {
            instance = new PlayerShip(shipTexture, laserTexture, shieldTexture, damagedShipTexture, movementSpeed,
                    shield, xCentre, yCentre, width, height, laserWidth, laserHeight, laserMovementSpeed,
                    laserAttackSpeed, regenTimer);
        }
        return instance;
    }


    @Override
    public void draw(Batch batch) {
        batch.draw(shipTexture, boundingbox.x, boundingbox.y, boundingbox.width, boundingbox.height);
        if (shield > 10) {
            batch.draw(shieldTexture, boundingbox.x, boundingbox.y, boundingbox.width, boundingbox.height);
        }

        if (shield < 0) {
            batch.draw(damagedShipTexture, boundingbox.x, boundingbox.y, boundingbox.width, boundingbox.height);
        
        }
    }


    @Override
    public Laser[] fireLasers() {
        Laser[] laser = new Laser[2];

        laser[0] = new Laser(laserMovementSpeed, boundingbox.x, boundingbox.y + laserHeight,
                laserWidth, laserHeight, laserTexture
        );

        laser[1] = new Laser(laserMovementSpeed, boundingbox.x +boundingbox.width, boundingbox.y + laserHeight,
                laserWidth, laserHeight, laserTexture
        );

        timeSinceLastShots = 0;
        return laser;
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

        morelaser[2] = new Laser(laserMovementSpeed, boundingbox.x + boundingbox.width/2, boundingbox.y+10 + laserHeight,
                laserWidth, laserHeight, laserTexture
        );

        morelaser[3] = new Laser(laserMovementSpeed, boundingbox.x + boundingbox.width/4 ,boundingbox.y+5 + laserHeight,
                laserWidth, laserHeight, laserTexture
        );

        morelaser[4] = new Laser(laserMovementSpeed, boundingbox.x +(boundingbox.width)*3/4, boundingbox.y+5 + laserHeight,
                laserWidth, laserHeight, laserTexture
        );

        timeSinceLastShots = 0;
        return morelaser;
    }

    @Override
    public void update(float deltaTime) {
        timeSinceLastShots += deltaTime;
        shieldRegenInterval += deltaTime;

        if (shieldRegenInterval >= regenTimer) {
            if (shield >= -19) {
                shield += 2;
                shieldRegenInterval -= regenTimer;
            }


            if (shield > 20){
                shield = 20;
            }

            if (shield <= -20){
                skyslayer.getInstance().setScreen(new DeathScreen());
                System.out.println("mati!");
                instance = null;
            }

        }
    }
}
