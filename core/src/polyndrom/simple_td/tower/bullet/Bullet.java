package polyndrom.simple_td.tower.bullet;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import polyndrom.simple_td.Constants;
import polyndrom.simple_td.enemy.Enemy;
import polyndrom.simple_td.tower.Tower;

public abstract class Bullet {

    protected Tower source;
    protected Enemy target;
    protected Vector2 position;

    abstract public int speed(); // pixels in ms

    abstract public void render(SpriteBatch batch);

    public Bullet(Tower source, Enemy target) {
        this.source = source;
        this.target = target;
        position = source.position.cpy().add(Constants.BLOCK_WIDTH / 2, Constants.BLOCK_HEIGHT / 2);
    }

}
