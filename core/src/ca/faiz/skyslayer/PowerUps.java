package ca.faiz.skyslayer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

abstract class PowerUps {

    public Rectangle boundingbox;

    TextureRegion powerUps;

    float powerUpTimer = 0;

    float movementspeeds;


    public PowerUps( float PowerupTimer, float movementSpeeds, TextureRegion powerUps, float xPowerup, float yPowerup, float width,
                    float height){

        this.boundingbox = new Rectangle(xPowerup - width / 2, yPowerup - height / 2, width, height);
        this.powerUps = powerUps;
        this.movementspeeds = movementSpeeds;
        this.powerUpTimer = PowerupTimer;

    }

    public void draw(Batch batch){
        batch.draw(powerUps, boundingbox.x, boundingbox.y, boundingbox.width, boundingbox.height);

    }

    public void update(float deltaTime){
        powerUpTimer += deltaTime;
    }

}
