package ca.faiz.skyslayer;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

abstract class Ship {
    private Texture PlayerShipTexture, EnemyShipTexture, PlayerShieldTexture, LaserShipTexture;

    float movementspeed;
    int shield;
    float xPosition, yPosition;
    float width, height;
    float laserWidth, laserHeight;
    float laserMovementSpeed;
    float laserAttackSpeed;
    float timeBetweenShots;
    float timeSinceLastShots = 0;


    public Ship(Texture shipTexture, Texture LaserTexture, float movementspeed,
                int shield, float xCentre, float yCentre, float width, float height,
                float laserWidth, float laserHeight, float laserMovementSpeed, float laserAttackSpeed
    ){

        this.PlayerShipTexture = shipTexture;
        this.LaserShipTexture = LaserTexture;
        this.EnemyShipTexture = new Texture("enemyship1.png");
        this.PlayerShieldTexture  = new Texture("shipshield.png");
        this.LaserShipTexture  = new Texture("laserBlue14.png");
        this.movementspeed = movementspeed;
        this.laserWidth = laserWidth;
        this.laserHeight = laserHeight;
        this.laserMovementSpeed = laserMovementSpeed;
        this.laserAttackSpeed = laserAttackSpeed;
        this.shield = shield;
        this.xPosition = xCentre - width/2;
        this.yPosition = yCentre - height/2;
        this.width = width;
        this.height = height;

    }

    public void update(float deltaTime){
        timeSinceLastShots += deltaTime;
    }

    public boolean canFireLaser(){
        return (timeSinceLastShots - timeBetweenShots >= 0);
    }

    public abstract Laser[] fireLasers();

    public void draw(Batch batch){
        batch.draw(PlayerShipTexture, xPosition, yPosition, width, height);
        if (shield > 0){
            batch.draw(PlayerShieldTexture, xPosition ,yPosition, width, height);
        }
    }
}
