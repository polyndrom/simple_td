package polyndrom.simple_td.enemy;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import polyndrom.simple_td.AssetsManager;
import polyndrom.simple_td.Constants;

import java.util.List;

public class TankEnemy extends Enemy {

    public TankEnemy(List<Vector2> path) {
        super(path);
        hp = 50;
    }

    @Override
    public int speed() {
        return 96;
    }

    @Override
    public int damage() {
        return 0;
    }

    @Override
    protected void doMove() {
        // Animation ???
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(new TextureRegion(AssetsManager.getTexture("enemy1")), position.x, position.y,
                   Constants.BLOCK_WIDTH / 2f, Constants.BLOCK_WIDTH / 2f, Constants.BLOCK_WIDTH, Constants.BLOCK_HEIGHT,
                   1, 1, rotation);
    }

}
