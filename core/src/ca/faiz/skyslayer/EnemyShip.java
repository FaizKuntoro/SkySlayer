package ca.faiz.skyslayer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

class EnemyShip extends Ship {


    public EnemyShip(TextureRegion shipTexture, TextureRegion LaserTexture, TextureRegion ShieldTexture,
                     int movementspeed, int shield, float xCentre, float yCentre, float width, float height,
                     float laserWidth, float laserHeight, float laserMovementSpeed, float laserAttackSpeed, float regenTimer) {
        super(shipTexture, LaserTexture, ShieldTexture, movementspeed, shield, xCentre,
                yCentre, width, height, laserWidth, laserHeight, laserMovementSpeed, laserAttackSpeed, regenTimer);

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
