package ca.faiz.skyslayer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MoreBulletsPowerUps extends PowerUps{

    public MoreBulletsPowerUps(float PowerupTimer, float movementSpeeds, TextureRegion powerUps, float xPowerup, float yPowerup, float width, float height) {
        super(PowerupTimer, movementSpeeds, powerUps, xPowerup, yPowerup, width, height);
    }

    @Override
    public void draw(Batch batch) {
        batch.draw(powerUps, boundingbox.x, boundingbox.y, boundingbox.width, boundingbox.height );
    }
}
