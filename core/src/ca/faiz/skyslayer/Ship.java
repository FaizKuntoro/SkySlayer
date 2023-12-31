package ca.faiz.skyslayer;


import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

abstract class Ship {

    float movementspeed;
    int shield;
    float shieldRegenInterval = 0;
    float regenTimer = 0;

    float laserWidth, laserHeight;
    float laserMovementSpeed;
    float laserAttackSpeed;
    float timeSinceLastShots = 0;
    TextureRegion shieldTexture, laserTexture, shipTexture, damagedShipTexture;

    public Rectangle boundingbox;


    public Ship(TextureRegion shipTexture, TextureRegion LaserTexture, TextureRegion shieldTexture,  float movementspeed,
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
        this.regenTimer = regenTimer;
        this.boundingbox = new Rectangle(xCentre - width / 2, yCentre - height / 2, width, height);

    }

    public void translate(float xChange, float yChange) {
        boundingbox.setPosition(boundingbox.x+xChange, boundingbox.y+yChange);
    }

    public abstract Laser[] fireMoreLasers();

    public void update(float deltaTime) {
        timeSinceLastShots += deltaTime;
        shieldRegenInterval += deltaTime;

        if (shieldRegenInterval >= regenTimer ){
            if (shield > 0 ){
                shield += 2 ;
                shieldRegenInterval -= regenTimer;
            }
       }
    }


    //
    public void setShieldTexture(TextureRegion newShieldTexture) {
        this.shieldTexture = newShieldTexture;
    }

    public void takeDamage(int damage) {
        shield -= damage;

        if (shield <= -20) {
            shield = -20;

        }
    }

    public float getMovementspeed(){
        return movementspeed;
    };



    public void laserBuff(float newlaserspeed){
        this.laserAttackSpeed = newlaserspeed;
    }

    public boolean canFireLaser() {
        return (timeSinceLastShots - laserAttackSpeed >= 0);
    }

    public boolean collide(Rectangle rectangle2){
        return boundingbox.overlaps(rectangle2);
    }

    public abstract Laser[] fireLasers();


    public void draw(Batch batch) {
        batch.draw(shipTexture, boundingbox.x, boundingbox.y, boundingbox.width, boundingbox.height);
        if (shield > 0) {
            batch.draw(shieldTexture, boundingbox.x, boundingbox.y, boundingbox.width, boundingbox.height);
        }
    }

    public void EnemyShipGroup() {
    }

    public int getShield(){
        return shield;
    }

    public void setDamagedShieldTexture(TextureRegion newDamagedTexture) {
        this.damagedShipTexture = newDamagedTexture;
    }

}




