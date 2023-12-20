package ca.faiz.skyslayer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Laser {

    TextureRegion textureRegion;
    float movementSpeed;
    Rectangle boundingbox;


    public Laser(float movementSpeed, float xLaser, float yLaser, float width, float height, TextureRegion textureRegion) {
        this.movementSpeed = movementSpeed;
        this.boundingbox = new Rectangle(xLaser - width / 2, yLaser - height/2,width, height);
        this.textureRegion = textureRegion;
    }


    public void draw(Batch batch) {
            batch.draw(textureRegion, boundingbox.x - boundingbox.width/2, boundingbox.y, boundingbox.width, boundingbox.height);
        }


    public Rectangle getBoundinbox(){
        return new Rectangle(boundingbox.x, boundingbox.y, boundingbox.width, boundingbox.height);
    }
}

