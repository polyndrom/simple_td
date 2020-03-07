package polyndrom.simple_td.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import polyndrom.simple_td.*;

import java.util.Map;
import java.util.TreeMap;

public class GameScreen implements Screen {

    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Level level;
    private GameHandler gameHandler;

    @Override
    public void show() {
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.x = Gdx.graphics.getWidth() / 2f;
        camera.position.y = Gdx.graphics.getHeight() / 2f;
        batch = new SpriteBatch();
        AssetsManager.loadTextures();
        level = new Level(1);
        PathFinder.find(level, Utils.SP2MP(level.getEnemySpawn()), Utils.SP2MP(level.getBase()));
        gameHandler = new GameHandler(level.getBase(), level.getEnemySpawn());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        camera.update();
        
        batch.begin();
        level.renderMap(batch);
        gameHandler.render(batch);
        batch.end();

        gameHandler.handle(delta);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        level.dispose();
        batch.dispose();
    }
}
