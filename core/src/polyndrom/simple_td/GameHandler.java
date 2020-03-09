package polyndrom.simple_td;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import polyndrom.simple_td.enemy.Enemy;
import polyndrom.simple_td.enemy.TankEnemy;
import polyndrom.simple_td.tower.Tower;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameHandler {

    private List<Tower> towers = new CopyOnWriteArrayList<>();
    private List<Enemy> enemies = new CopyOnWriteArrayList<>();
    private Vector2 enemySpawn;
    private Vector2 base;
    private Level level;

    public GameHandler(Level level) {
        System.out.println(Utils.SP2MP(new Vector2(0, 0)));
        System.out.println(Utils.SP2MP(new Vector2(48, 90)));
        System.out.println(Utils.SP2MP(new Vector2(48 * 3 + 1, 48 * 5)));
        this.level = level;
        this.base = level.getBase();
        this.enemySpawn = level.getEnemySpawn();
        List<Vector2> path = PathFinder.find(level, Utils.SP2MP(enemySpawn), Utils.SP2MP(base));
        enemies.add(new TankEnemy(path));
    }

    public void render(SpriteBatch batch) {
        for (Tower tower: towers) {
            tower.render(batch);
        }
        for (Enemy enemy: enemies) {
            enemy.render(batch);
        }
    }

    long t = TimeUtils.millis() - 1500;

    public void handle(float delta) {
        if (TimeUtils.timeSinceMillis(t) >= 1500) {
            enemies.add(new TankEnemy(PathFinder.find(level, Utils.SP2MP(enemySpawn), Utils.SP2MP(base))));
            t = TimeUtils.millis();
        }
        for (Tower tower: towers) {
            tower.handle(delta);
        }
        for (Tower tower: towers) {
            for (Enemy enemy: enemies) {
                if (tower.isEnemyInRadius(enemy)) {
                    if (!tower.isAttacked(enemy)) {
                        tower.startAttack(enemy);
                    }
                } else {
                    if (tower.isAttacked(enemy)) {
                        tower.stopAttack(enemy);
                    }
                }
            }
        }
        for (Enemy enemy: enemies) {
            if (!enemy.isAlive()) {
                enemies.remove(enemy);
            }
            enemy.handle();
        }
    }

    private void spawnEnemy() {

    }

}
