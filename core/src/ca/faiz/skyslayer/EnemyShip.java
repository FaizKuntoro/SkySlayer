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

        laser[0] = new Laser(laserMovementSpeed, xPosition,  yPosition ,
                laserWidth, laserHeight, laserTexture
        );

        laser[1] = new Laser(laserMovementSpeed, xPosition + width, yPosition ,
                laserWidth, laserHeight, laserTexture
        );

        timeSinceLastShots = 0;
        return laser;
    }
    @Override
    public void draw(Batch batch) {
        batch.draw(shipTexture, xPosition, yPosition, width, height);
        if (shield > 0 && shield < 3) {
            batch.draw(shieldTexture, xPosition, yPosition-height*0.2f, width, height);
        }
    }



    @Override
    public void update(float deltaTime) {
        timeSinceLastShots += deltaTime;
        shieldRegenInterval += deltaTime;

        if (shieldRegenInterval >= regenTimer) {
            if (shield < 3) {
                shield += 1;
                shieldRegenInterval -= regenTimer;
            }
        }
    }
}
