package polyndrom.simple_td.tower;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import polyndrom.simple_td.AssetsManager;
import polyndrom.simple_td.Constants;
import polyndrom.simple_td.enemy.Enemy;
import polyndrom.simple_td.tower.bullet.Bullet;
import polyndrom.simple_td.tower.bullet.TurretBullet;

public class TurretTower extends Tower {

    public TurretTower(Vector2 position) {
       super(position);
    }

    @Override
    public void attack(Enemy enemy) {
        bullets.add(new TurretBullet(this, enemy));
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(AssetsManager.getTexture("turret1_base"), position.x, position.y);
        batch.draw(AssetsManager.getTexture("turret1_gun"), position.x, position.y);
    }

    @Override
    public int maxTargets() {
        return 1;
    }

    @Override
    public Circle radius() {
        return new Circle(position.x + Constants.BLOCK_WIDTH / 2, position.y + Constants.BLOCK_HEIGHT / 2,
                          2 * Constants.BLOCK_WIDTH);
    }

    @Override
    public int damage() {
        return 5;
    }

    @Override
    public long fireRate() {
        return 500;
    }
}
