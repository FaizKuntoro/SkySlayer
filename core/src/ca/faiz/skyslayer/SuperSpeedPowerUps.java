package ca.faiz.skyslayer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

class SuperSpeedPowerUps extends PowerUps {

    private SuperSpeedPowerUps(float powerupTimer, float movementSpeed, TextureRegion powerUps,
                               float xPowerup, float yPowerup, float width, float height) {
        super(powerupTimer, movementSpeed, powerUps, xPowerup, yPowerup, width, height);
    }

    public static class Builder {
        private float powerupTimer;
        private float movementSpeed;
        private TextureRegion powerUps;
        private float xPowerup;
        private float yPowerup;
        private float width;
        private float height;

        public Builder setPowerupTimer(float powerupTimer) {
            this.powerupTimer = powerupTimer;
            return this;
        }

        public Builder setMovementSpeed(float movementSpeed) {
            this.movementSpeed = movementSpeed;
            return this;
        }

        public Builder setPowerUps(TextureRegion powerUps) {
            this.powerUps = powerUps;
            return this;
        }

        public Builder setXPowerup(float xPowerup) {
            this.xPowerup = xPowerup;
            return this;
        }

        public Builder setYPowerup(float yPowerup) {
            this.yPowerup = yPowerup;
            return this;
        }

        public Builder setWidth(float width) {
            this.width = width;
            return this;
        }

        public Builder setHeight(float height) {
            this.height = height;
            return this;
        }

        public SuperSpeedPowerUps build() {
            return new SuperSpeedPowerUps(powerupTimer, movementSpeed, powerUps, xPowerup, yPowerup, width, height);
        }
    }

    @Override
    public void draw(Batch batch) {
        batch.draw(powerUps, boundingbox.x, boundingbox.y, boundingbox.width, boundingbox.height);
    }
}
