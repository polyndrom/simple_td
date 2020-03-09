package polyndrom.simple_td.tower.bullet;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import polyndrom.simple_td.Constants;
import polyndrom.simple_td.enemy.Enemy;
import polyndrom.simple_td.tower.Tower;

public abstract class Bullet implements Disposable {

    protected Tower tower;
    protected Enemy target;
    protected Vector2 position;

    // Bullet properties
    abstract public int speed();

    abstract public void render(SpriteBatch batch);

    public boolean isHit() {
        return target.hitBox().contains(position);
    }

    public Bullet(Tower tower, Enemy target) {
        this.tower = tower;
        this.target = target;
        position = tower.getPosition().cpy().add(Constants.BLOCK_WIDTH / 2, Constants.BLOCK_HEIGHT / 2);
    }

    public void handle(float delta) {
        if (isHit()) {
            target.hit(tower.damage());
            System.out.println("hit " + target.getHp());
        }
        Vector2 targetPosition = target.getPosition().cpy().add(Constants.BLOCK_WIDTH / 2, Constants.BLOCK_HEIGHT / 2);
        float dst = position.dst(targetPosition);
        targetPosition.sub(position);
        position.add(targetPosition.x / dst * delta * speed(),
                     targetPosition.y / dst * delta * speed());
    }

    public Tower getTower() {
        return tower;
    }

    public Enemy getTarget() {
        return target;
    }

}
