package ca.faiz.skyslayer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Laser {

    private Texture LaserPlayerTexture;
    float movementSpeed;
    float xPosition, yPosition;
    float width, height;




    public Laser(float movementSpeed, float xPosition, float yPosition, float width, float height, Texture LaserTexture) {
        this.movementSpeed = movementSpeed;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = width;
        this.height = height;
        this.LaserPlayerTexture = LaserTexture;
        this.LaserPlayerTexture = new Texture("laserBlue14.png");
    }


    public void draw(Batch batch) {
            batch.draw(LaserPlayerTexture, xPosition - width/2, yPosition, width, height);
        }

}
