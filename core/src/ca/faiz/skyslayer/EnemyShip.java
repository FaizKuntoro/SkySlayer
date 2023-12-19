package ca.faiz.skyslayer;

import com.badlogic.gdx.graphics.Texture;

public class EnemyShip extends Ship {

    private Texture LaserTexture;

    public EnemyShip(Texture shipTexture, Texture LaserTexture, float movementspeed, int shield, float xCentre, float yCentre, float width, float height, float laserWidth, float laserHeight, float laserMovementSpeed, float laserAttackSpeed) {
        super(shipTexture, LaserTexture, movementspeed, shield, xCentre, yCentre, width, height, laserWidth, laserHeight, laserMovementSpeed, laserAttackSpeed);
        this.LaserTexture = LaserTexture;
    }

    @Override
    public Laser[] fireLasers() {
        Laser[] lasers = new Laser[2];

        lasers[0] = new Laser(laserMovementSpeed, xPosition + width + 0.07f, yPosition + height + 0.45f,
                laserWidth, laserHeight, LaserTexture
        );

        lasers[1] = new Laser(laserMovementSpeed, xPosition + width + 0.93f, yPosition + height + 0.45f,
                laserWidth, laserHeight, LaserTexture
        );

        timeSinceLastShots = 0;
        return lasers;
    }
}
