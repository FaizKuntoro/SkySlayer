package ca.faiz.skyslayer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import java.util.ArrayList;
import java.util.List;

public class EnemyShipGroup {

    private List<EnemyShip> enemyShips;

    public EnemyShipGroup() {
        enemyShips = new ArrayList<>();
    }

    // Spawn a wing pattern of enemy ships
    public void spawnWingPattern(TextureRegion shipTexture, TextureRegion laserTexture,
                                 TextureRegion shieldTexture, int movementSpeed, int shield,
                                 float x, float y, float width, float height,
                                 float laserWidth, float laserHeight, float laserMovementSpeed,
                                 float laserAttackSpeed, float regenTimer) {
        EnemyShip leftShip = new EnemyShip(shipTexture, laserTexture, shieldTexture,
                movementSpeed, shield, x, y, width, height,
                laserWidth, laserHeight, laserMovementSpeed, laserAttackSpeed, regenTimer);

        EnemyShip rightShip = new EnemyShip(shipTexture, laserTexture, shieldTexture,
                movementSpeed, shield, x, y, width, height,
                laserWidth, laserHeight, laserMovementSpeed, laserAttackSpeed, regenTimer);


        enemyShips.add(leftShip);
        enemyShips.add(rightShip);
    }

    // Spawn a horizontal pattern of enemy ships
    public void spawnHorizontalPattern(TextureRegion shipTexture, TextureRegion laserTexture,
                                       TextureRegion shieldTexture, int movementSpeed, int shield,
                                       float x, float y, float width, float height,
                                       float laserWidth, float laserHeight, float laserMovementSpeed,
                                       float laserAttackSpeed, float regenTimer, int count) {
        float spacing = width * 1.5f;

        for (int i = 0; i < count; i++) {
            EnemyShip enemyShip = new EnemyShip(shipTexture, laserTexture, shieldTexture,
                    movementSpeed, shield, x + i * spacing, y, width, height,
                    laserWidth, laserHeight, laserMovementSpeed, laserAttackSpeed, regenTimer);

            enemyShips.add(enemyShip);
        }
    }

    // Spawn a vertical pattern of enemy ships
    public void spawnVerticalPattern(TextureRegion shipTexture, TextureRegion laserTexture,
                                     TextureRegion shieldTexture, int movementSpeed, int shield,
                                     float x, float y, float width, float height,
                                     float laserWidth, float laserHeight, float laserMovementSpeed,
                                     float laserAttackSpeed, float regenTimer, int count) {
        float spacing = height * 1.5f;

        for (int i = 0; i < count; i++) {
            EnemyShip enemyShip = new EnemyShip(shipTexture, laserTexture, shieldTexture,
                    movementSpeed, shield, x, y + i * spacing, width, height,
                    laserWidth, laserHeight, laserMovementSpeed, laserAttackSpeed, regenTimer);

            enemyShips.add(enemyShip);
        }
    }

    public void draw(Batch batch) {
        for (EnemyShip enemyShip : enemyShips) {
            enemyShip.draw(batch);
        }
    }

    public void update(float deltaTime) {
        for (EnemyShip enemyShip : enemyShips) {
            enemyShip.update(deltaTime);
        }
    }

    public List<EnemyShip> getEnemyShips() {
        return enemyShips;
    }


}
