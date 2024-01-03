package ca.faiz.skyslayer;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class EnemyShipDecorator extends  EnemyShip {


    public EnemyShipDecorator(TextureRegion shipTexture, TextureRegion LaserTexture, TextureRegion ShieldTexture, int movementspeed, int shield, float xCentre, float yCentre, float width, float height, float laserWidth, float laserHeight, float laserMovementSpeed, float laserAttackSpeed, float regenTimer) {
        super(shipTexture, LaserTexture, ShieldTexture, movementspeed, shield, xCentre, yCentre, width, height, laserWidth, laserHeight, laserMovementSpeed, laserAttackSpeed, regenTimer);
    }

    @Override
    public float getMovementspeed() {
        return super.getMovementspeed() + 0.4f;
    }
}
