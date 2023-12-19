package ca.faiz.skyslayer;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

abstract class Ship {

    private Texture PlayerShipTexture, EnemyShipTexture, PlayerShieldTexture, LaserShipTexture;

    float movementspeed;
    int shield;
    float shieldRegenInterval = 0;
    float regenTimer = 0;
    float xPosition, yPosition;
    float width, height;
    float laserWidth, laserHeight;
    float laserMovementSpeed;
    float laserAttackSpeed;
    float timeSinceLastShots = 0;
    TextureRegion shieldTexture, laserTexture, shipTexture;

    public Rectangle boundingbox;


    public Ship(TextureRegion shipTexture, TextureRegion LaserTexture, TextureRegion shieldTexture, float movementspeed,
                int shield, float xCentre, float yCentre, float width, float height,
                float laserWidth, float laserHeight, float laserMovementSpeed, float laserAttackSpeed,
                float regenTimer
    ) {

        this.shipTexture = shipTexture;
        this.laserTexture = LaserTexture;
        this.shieldTexture = shieldTexture;
        this.movementspeed = movementspeed;
        this.laserWidth = laserWidth;
        this.laserHeight = laserHeight;
        this.laserMovementSpeed = laserMovementSpeed;
        this.laserAttackSpeed = laserAttackSpeed;
        this.shield = shield;
        this.xPosition = xCentre - width / 2;
        this.yPosition = yCentre - height / 2;
        this.width = width;
        this.height = height;
        this.regenTimer = regenTimer;
        this.boundingbox = new Rectangle(xPosition, yPosition, width, height);

    }

    public void update(float deltaTime) {
        timeSinceLastShots += deltaTime;
        shieldRegenInterval += deltaTime;
        boundingbox.set(xPosition, yPosition, width, height);

        if (shieldRegenInterval >= regenTimer ){
            if (shield < 20 ){
                shield += 2 ;
                shieldRegenInterval -= regenTimer;
            }
       }
    }

    public void setShieldTexture(TextureRegion newShieldTexture) {
        this.shieldTexture = newShieldTexture;
    }

    public void takeDamage(int damage) {
        shield -= damage;

        // Ensure shield does not go below zero
        if (shield < 0) {
            shield = 0;

        }
    }

    public boolean canFireLaser() {
        return (timeSinceLastShots - laserAttackSpeed >= 0);
    }

    public boolean collide(Rectangle rectangle2){
        return boundingbox.overlaps(rectangle2);
    }

    public abstract Laser[] fireLasers();

    public void draw(Batch batch) {
        batch.draw(shipTexture, xPosition, yPosition, width, height);
        if (shield > 0) {
            batch.draw(shieldTexture, xPosition, yPosition, width, height);
        }
    }

    public int getShield(){
        return shield;
    }

}


