package polyndrom.simple_td;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import polyndrom.simple_td.enemy.Enemy;
import polyndrom.simple_td.tower.Tower;

import java.util.ArrayList;
import java.util.List;

public class GameHandler {

    private List<Tower> towers = new ArrayList<>();
    private List<Enemy> enemies = new ArrayList<>();
    private Vector2 enemySpawn;
    private Vector2 base;

    public GameHandler(Vector2 base, Vector2 enemySpawn) {
        this.base = base;
        this.enemySpawn = enemySpawn;
    }

    public void render(SpriteBatch batch) {
        for (Tower tower: towers) {
            tower.render(batch);
        }
        for (Enemy enemy: enemies) {
            enemy.render();
        }
    }

    public void handle(float delta) {
        for (Tower tower: towers) {
            for (Enemy enemy: enemies) {
                Vector2 enemyPosition = enemy.position;
                if (tower.radius().contains(enemyPosition) ||
                    tower.radius().contains(enemyPosition.cpy().add(Constants.BLOCK_WIDTH, 0)) ||
                    tower.radius().contains(enemyPosition.cpy().add(0, Constants.BLOCK_HEIGHT)) ||
                    tower.radius().contains(enemyPosition.cpy().add(Constants.BLOCK_WIDTH, Constants.BLOCK_HEIGHT))) {
                    tower.attack(enemy);
                }
            }
        }
    }

    private void spawnEnemy() {

    }

}
