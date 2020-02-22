package polyndrom.simple_td.tower;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import polyndrom.simple_td.enemy.Enemy;
import polyndrom.simple_td.tower.bullet.Bullet;

import java.util.ArrayList;
import java.util.List;

public abstract class Tower implements Disposable {

    public Vector2 position;
    protected List<Bullet> bullets = new ArrayList<>();

    abstract public int maxTargets();
    abstract public Circle radius();
    abstract public int damage();
    abstract public long fireRate(); // in ms

    abstract public void render(SpriteBatch batch);
    abstract public void attack(Enemy enemy);

    public Tower(Vector2 position) {
        this.position = position;
    }

    @Override
    public void dispose() {

    }

}
