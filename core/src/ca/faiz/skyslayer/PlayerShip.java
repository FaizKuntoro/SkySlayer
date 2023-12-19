package ca.faiz.skyslayer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

class PlayerShip extends Ship {


    public PlayerShip(TextureRegion shipTexture, TextureRegion LaserTexture, TextureRegion ShieldTexture,  float movementspeed, int shield, float xCentre, float yCentre, float width, float height, float laserWidth, float laserHeight, float laserMovementSpeed, float laserAttackSpeed) {
        super(shipTexture, LaserTexture, ShieldTexture, movementspeed, shield, xCentre, yCentre, width, height, laserWidth, laserHeight, laserMovementSpeed, laserAttackSpeed);

    }

    @Override
    public Laser[] fireLasers() {
        Laser[] laser = new Laser[2];

        laser[0] = new Laser(laserMovementSpeed, xPosition, yPosition + height + 0.45f,
                laserWidth, laserHeight, laserTexture
        );

        laser[1] = new Laser(laserMovementSpeed, xPosition + width, yPosition + height + 0.45f,
                laserWidth, laserHeight, laserTexture
        );

        timeSinceLastShots = 0;
        return laser;
    }

}
