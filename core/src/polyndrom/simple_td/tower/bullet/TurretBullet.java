package polyndrom.simple_td.tower.bullet;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import polyndrom.simple_td.Constants;
import polyndrom.simple_td.enemy.Enemy;
import polyndrom.simple_td.tower.Tower;

public class TurretBullet extends Bullet {

    public TurretBullet(Tower source, Enemy target) {
        super(source, target);
    }

    @Override
    public int speed() {
        return 5;
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.end();
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(position.x, position.y, 2, 2);
        shapeRenderer.end();
        batch.begin();
        position.add(speed(), speed());
    }

}
