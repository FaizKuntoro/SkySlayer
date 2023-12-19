package ca.faiz.skyslayer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

class PlayerShip extends Ship {


    public PlayerShip(TextureRegion shipTexture, TextureRegion LaserTexture, TextureRegion ShieldTexture,
                      float movementspeed, int shield, float xCentre, float yCentre, float width, float height,
                      float laserWidth, float laserHeight, float laserMovementSpeed, float laserAttackSpeed, float regenTimer) {
        super(shipTexture, LaserTexture, ShieldTexture, movementspeed, shield, xCentre, yCentre,
                width, height, laserWidth, laserHeight, laserMovementSpeed, laserAttackSpeed, regenTimer);

    }

    @Override
    public Laser[] fireLasers() {
        Laser[] laser = new Laser[3];

        laser[0] = new Laser(laserMovementSpeed, xPosition, yPosition + height + 0.45f,
                laserWidth, laserHeight, laserTexture
        );

        laser[1] = new Laser(laserMovementSpeed, xPosition + width, yPosition + height + 0.45f,
                laserWidth, laserHeight, laserTexture
        );

        laser[2] = new Laser(laserMovementSpeed, xPosition + width - width/2, yPosition + height + 0.45f,
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
            if (shield < 19) {
                shield += 2;
                shieldRegenInterval -= regenTimer;
            }
        }
    }
}
