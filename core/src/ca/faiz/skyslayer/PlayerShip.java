package ca.faiz.skyslayer;

import com.badlogic.gdx.graphics.Texture;

class PlayerShip extends Ship {



    public PlayerShip(Texture shipTexture, Texture LaserTexture, float movementspeed, int shield, float xCentre, float yCentre, float width, float height, float laserWidth, float laserHeight, float laserMovementSpeed, float laserAttackSpeed) {
        super(shipTexture, LaserTexture, movementspeed, shield, xCentre, yCentre, width, height, laserWidth, laserHeight, laserMovementSpeed, laserAttackSpeed);
    }

    @Override
    public Laser[] fireLasers() {
        Laser[] laser = new Laser[2];


        Texture LaserTexture = new Texture("laserBlue14.png");

        laser[0] = new Laser(laserMovementSpeed, xPosition+width+0.07f , yPosition+height+0.45f,
                laserWidth, laserHeight, LaserTexture
        );

        laser[1] = new Laser(laserMovementSpeed, xPosition+width+0.93f , yPosition+height+0.45f,
                laserWidth, laserHeight, LaserTexture
        );

        timeSinceLastShots = 0;
        return laser;
    }



}
