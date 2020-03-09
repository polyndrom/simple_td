package polyndrom.simple_td.tower.bullet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import polyndrom.simple_td.Constants;
import polyndrom.simple_td.enemy.Enemy;
import polyndrom.simple_td.tower.Tower;

public class TurretBullet extends Bullet {

    private ShapeRenderer shapeRenderer;

    public TurretBullet(Tower source, Enemy target) {
        super(source, target);
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(position.x, position.y, 4, 4);
        shapeRenderer.end();
        batch.begin();
    }

    @Override
    public int speed() {
        return 400;
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
