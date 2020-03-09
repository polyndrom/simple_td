package polyndrom.simple_td.tower;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.TimeUtils;
import polyndrom.simple_td.Constants;
import polyndrom.simple_td.enemy.Enemy;
import polyndrom.simple_td.tower.bullet.Bullet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class Tower implements Disposable {

    protected Vector2 position;
    protected List<Enemy> targets = new CopyOnWriteArrayList<>();
    protected List<Bullet> bullets = new CopyOnWriteArrayList<>();

    private long currentFireRateTime = TimeUtils.millis();

    // Tower properties
    abstract public float radius(); // in pixels
    abstract public int damage(); //[0, 100]
    abstract public long fireRate(); // 1 shoot in fireRate() time (in millis)
    abstract public int maxTargets();

    abstract public void render(SpriteBatch batch);

    abstract protected void doAttack(Enemy enemy);

    public Tower(Vector2 position) {
        this.position = position;
    }

    public void handle(float delta) {
        for (Enemy enemy: targets) {
            if (TimeUtils.timeSinceMillis(currentFireRateTime) > fireRate()) {
                if (enemy.isAlive()) doAttack(enemy);
                else stopAttack(enemy);
                currentFireRateTime = TimeUtils.millis();
            }
        }
        for (Bullet bullet: bullets) {
            if (bullet.isHit()) {
                bullets.remove(bullet);
            }
            bullet.handle(delta);
        }
    }

    public boolean isEnemyInRadius(Enemy enemy) {
        Vector2 enemyPosition = enemy.getPosition();
        Vector2[] enemyVertices = {
                enemyPosition,
                enemyPosition.cpy().add(Constants.BLOCK_WIDTH, 0),
                enemyPosition.cpy().add(0, Constants.BLOCK_HEIGHT),
                enemyPosition.cpy().add(Constants.BLOCK_WIDTH, Constants.BLOCK_HEIGHT)
        };
        return Arrays.stream(enemyVertices).anyMatch(v -> radiusCircle().contains(v));
    }

    public Circle radiusCircle() {
        return new Circle(position.x + Constants.BLOCK_WIDTH / 2f, position.y + Constants.BLOCK_HEIGHT / 2f, radius());
    }

    public boolean isAttacked(Enemy enemy) {
        return targets.contains(enemy);
    }

    public void startAttack(Enemy enemy) {
        targets.add(enemy);
    }

    public void stopAttack(Enemy enemy) {
        targets.remove(enemy);
    }

    public Vector2 getPosition() {
        return position;
    }

    public List<Enemy> getTargets() {
        return targets;
    }

    @Override
    public void dispose() {

    }

}
