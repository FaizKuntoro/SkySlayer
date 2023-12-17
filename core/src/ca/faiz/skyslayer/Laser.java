package ca.faiz.skyslayer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Laser {

    float movementSpeed;
    float xPosition, yPosition;
    float width, height;

    Texture texture;


    public Laser(float movementSpeed, float xPosition, float yPosition, float width, float height, Texture texture) {
        this.movementSpeed = movementSpeed;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = width;
        this.height = height;
        this.texture = texture;
    }



}
