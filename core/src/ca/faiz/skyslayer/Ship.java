package ca.faiz.skyslayer;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Ship {
    private Texture shipT, enemyshipT, shipshieldT;

    float movementspeed;
    int shield;


    float xPosition, yPosition;
    float width, height;


    public Ship(Texture shipTexture, float movementspeed,
                int shield, float xCentre, float yCentre, float
                        width, float height){

        this.shipT = shipTexture;
        this.enemyshipT = new Texture("enemyship1.png");
        this.shipshieldT  = new Texture("shipshield.png");
        this.movementspeed = movementspeed;
        this.shield = shield;
        this.xPosition = xCentre - width/2;
        this.yPosition = yCentre - height/2;
        this.width = width;
        this.height = height;



    }

    public void draw(Batch batch){
        batch.draw(shipT, xPosition, yPosition, width, height);
        if (shield > 0){
            batch.draw(shipshieldT, xPosition ,yPosition, width, height);
        }
    }
}
