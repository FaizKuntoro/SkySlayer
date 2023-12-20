package ca.faiz.skyslayer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

class SuperSpeed extends PowerUps{


    public SuperSpeed(float PowerupTimer, float movementSpeed, TextureRegion powerUps, float xPowerup, float yPowerup, float width, float height) {
        super(PowerupTimer, movementSpeed, powerUps, xPowerup, yPowerup, width, height);

    }

    @Override
    public void draw(Batch batch){
        batch.draw(powerUps, boundingbox.x, boundingbox.y, boundingbox.width, boundingbox.height);
    }
}
