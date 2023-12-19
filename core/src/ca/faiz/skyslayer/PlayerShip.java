package ca.faiz.skyslayer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

class PlayerShip extends Ship {

    TextureRegion damagedShieldTexture;


    public PlayerShip(TextureRegion shipTexture, TextureRegion LaserTexture, TextureRegion ShieldTexture, TextureRegion damagedShipTexture,
                      float movementspeed, int shield, float xCentre, float yCentre, float width, float height,
                      float laserWidth, float laserHeight, float laserMovementSpeed, float laserAttackSpeed, float regenTimer) {
        super(shipTexture, LaserTexture, ShieldTexture, movementspeed, shield, xCentre, yCentre,
                width, height, laserWidth, laserHeight, laserMovementSpeed, laserAttackSpeed, regenTimer);



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
        Laser[] laser = new Laser[3];

        laser[0] = new Laser(laserMovementSpeed, boundingbox.x, boundingbox.y + laserHeight,
                laserWidth, laserHeight, laserTexture
        );

        laser[1] = new Laser(laserMovementSpeed, boundingbox.x +boundingbox.width, boundingbox.y + laserHeight,
                laserWidth, laserHeight, laserTexture
        );

        laser[2] = new Laser(laserMovementSpeed, boundingbox.x + boundingbox.width/2, boundingbox.y + laserHeight,
                laserWidth, laserHeight, laserTexture
        );

        timeSinceLastShots = 0;
        return laser;
    }

    @Override
    public void update(float deltaTime) {
        timeSinceLastShots += deltaTime;
        shieldRegenInterval += deltaTime;

        if (shieldRegenInterval >= regenTimer) {
            if (shield >= -20) {
                shield += 2;
                shieldRegenInterval -= regenTimer;
            }

            if (shield > 20){
                shield = 20;
            }
        }
    }
}
