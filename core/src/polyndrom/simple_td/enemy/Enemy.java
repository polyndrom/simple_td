package polyndrom.simple_td.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import polyndrom.simple_td.Constants;
import polyndrom.simple_td.Utils;

import java.util.List;

public abstract class Enemy {

    protected List<Vector2> path;
    protected int currentPoint = 0;
    protected Vector2 position;
    protected int hp;
    protected int rotation = 0;

    // Enemy properties
    public abstract int speed();
    public abstract int damage();

    protected abstract void doMove();

    public abstract void render(SpriteBatch batch);

    public void hit(int damage) {
        hp -= damage;
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public void handle() {
        int byX = 0;
        int byY = 0;
        int prevX = (int) (currentPoint - 1 < 0 ? path.get(currentPoint).x - 48 : path.get(currentPoint - 1).x);
        int prevY = (int) (currentPoint - 1 < 0 ? path.get(currentPoint).y: path.get(currentPoint - 1).y);
        int currentX = (int) path.get(currentPoint).x;
        int currentY = (int) path.get(currentPoint).y;
        int nextX = (int) path.get(currentPoint + 1).x;
        int nextY = (int) path.get(currentPoint + 1).y;
        if (currentX == nextX) {
            if (currentY < nextY)  byY = 1;
            else byY = -1;
        }
        if (currentY == nextY) {
            if (currentX < nextX) byX = 1;
            else byX = -1;
        }
        if (prevY == currentY && currentX == nextX) {
            if (byY == 1) rotation = 90;
            else rotation = 270;
        }
        if (prevX == currentX && currentY == nextY) {
            if (byX == 1) rotation = 0;
            else rotation = 180;
        }
        position.x += speed() * Gdx.graphics.getDeltaTime() * byX;
        position.y += speed() * Gdx.graphics.getDeltaTime() * byY;
        int x = (int) path.get(currentPoint + 1).x * 48;
        int y = (int) path.get(currentPoint + 1).y * 48;
        Rectangle nextRect = new Rectangle(x + 23, y + 23, 2, 2);
        if (nextRect.contains(position.cpy().add(24, 24))) {

            currentPoint++;
        }
        doMove();
    }

    public Enemy(List<Vector2> path) {
        this.path = path;
        position = Utils.MP2SP(path.get(0));
    }

    public Rectangle hitBox() {
        return new Rectangle(position.x + Constants.BLOCK_WIDTH / 4f, position.y + Constants.BLOCK_HEIGHT / 4f,
                Constants.BLOCK_WIDTH / 2f, Constants.BLOCK_HEIGHT / 2f);
    }

    public Vector2 getPosition() {
        return position;
    }

    public int getHp() {
        return hp;
    }
}
