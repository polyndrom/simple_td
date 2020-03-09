package polyndrom.simple_td.tower;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import polyndrom.simple_td.AssetsManager;
import polyndrom.simple_td.Constants;
import polyndrom.simple_td.enemy.Enemy;
import polyndrom.simple_td.tower.bullet.Bullet;
import polyndrom.simple_td.tower.bullet.TurretBullet;

public class TurretTower extends Tower {

    private float gunRotation = 0;

    public TurretTower(Vector2 position) {
       super(position);
    }

    @Override
    protected void doAttack(Enemy enemy) {
        bullets.add(new TurretBullet(this, enemy));
    }

    @Override
    public void render(SpriteBatch batch) {
        for (Bullet bullet : bullets) {
            bullet.render(batch);
        }
        batch.draw(AssetsManager.getTexture("turret1_base"), position.x, position.y,
                                            Constants.BLOCK_WIDTH, Constants.BLOCK_HEIGHT);
        batch.draw(new TextureRegion(AssetsManager.getTexture("turret1_gun")), position.x, position.y,
                Constants.BLOCK_WIDTH / 2f, Constants.BLOCK_WIDTH / 2f, Constants.BLOCK_WIDTH, Constants.BLOCK_HEIGHT,
                1, 1, gunRotation);
    }

    @Override
    public void handle(float delta) {
        super.handle(delta);
        if (targets.size() > 0) {
            Enemy enemy = targets.get(0);
            Vector2 v = new Vector2(0, 1000);
            Vector2 enemyVector = new Vector2(enemy.getPosition().x - position.x, enemy.getPosition().y - position.y);
            gunRotation = v.angle(enemyVector);
        }
    }

    @Override
    public float radius() {
        return 2.5f * Constants.BLOCK_WIDTH;
    }

    @Override
    public int damage() {
        return 20;
    }

    @Override
    public long fireRate() {
        return 300;
    }

    @Override
    public int maxTargets() {
        return 1;
    }

}
