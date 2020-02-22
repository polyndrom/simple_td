package polyndrom.simple_td;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import polyndrom.simple_td.tower.Tower;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Level implements Disposable {

    private int width;
    private int height;
    private Vector2 base;
    private Vector2 enemySpawn;
    private Map<Character, Texture> texturesMapper;
    private List<List<Character>> charMap;

    public static Level load(int levelId) {
        JsonReader jsonReader = new JsonReader();
        JsonValue root = jsonReader.parse(Utils.getLevelFile(levelId));
        int width = root.getInt("width");
        int height = root.getInt("height");
        List<List<Character>> charMap = new ArrayList<>();
        String[] buffer = Utils.getLevelMapFile(levelId).readString().split(System.lineSeparator());
        for (int i = 0; i < height; i++) {
            charMap.add(new ArrayList<Character>());
            for (int j = 0; j < width; j++) {
                charMap.get(i).add(buffer[i].charAt(j));
            }
        }
        return new LevelBuilder()
                .width(width)
                .height(height)
                .base(new Vector2(root.get("base").getInt("x"), root.get("base").getInt("x")))
                .enemySpawn(new Vector2((float) root.get("enemy_spawn").getInt("x"),
                                                 (float) root.get("enemy_spawn").getInt("x")))
                .charMap(charMap)
                .build();
    }

    public Vector2 getBase() {
        return base;
    }

    public Vector2 getEnemySpawn() {
        return enemySpawn;
    }

    private Level(int width, int height, Vector2 base,
                  Vector2 enemySpawn, List<List<Character>> charMap) {
        this.width = width;
        this.height = height;
        this.base = base;
        this.enemySpawn = enemySpawn;
        this.charMap = charMap;
        loadMapTextures();
    }

    private void loadMapTextures() {
        texturesMapper = new TreeMap<>();
        texturesMapper.put('#', AssetsManager.getTexture("empty_field"));
        texturesMapper.put('h', AssetsManager.getTexture("road_left_to_right"));
        texturesMapper.put('v', AssetsManager.getTexture("road_down_to_up"));
        texturesMapper.put('1', AssetsManager.getTexture("road_up_to_right"));
        texturesMapper.put('2', AssetsManager.getTexture("road_down_to_right"));
        texturesMapper.put('3', AssetsManager.getTexture("road_down_to_left"));
        texturesMapper.put('4', AssetsManager.getTexture("road_up_to_left"));
        texturesMapper.put('+', AssetsManager.getTexture("road_4x"));
        texturesMapper.put('B', AssetsManager.getTexture("base"));
    }

    public void renderMap(SpriteBatch batch) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                float x = Utils.MC2SC(j);
                float y = Gdx.graphics.getHeight() - Constants.BLOCK_HEIGHT - Utils.MC2SC(i);
                batch.draw(texturesMapper.get(charMap.get(i).get(j)), x, y, Constants.BLOCK_WIDTH, Constants.BLOCK_HEIGHT);
            }
        }
    }

    @Override
    public void dispose() {

    }

    private static class LevelBuilder {

        private int width;
        private int height;
        private Vector2 base;
        private Vector2 enemySpawn;
        private List<List<Character>> charMap;

        private LevelBuilder width(int width) {
            this.width = width;
            return this;
        }

        private LevelBuilder height(int height) {
            this.height = height;
            return this;
        }

        private LevelBuilder base(Vector2 base) {
            this.base = base;
            return this;
        }

        private LevelBuilder enemySpawn(Vector2 enemySpawn) {
            this.enemySpawn = enemySpawn;
            return this;
        }

        private LevelBuilder charMap(List<List<Character>> charMap) {
            this.charMap = charMap;
            return this;
        }

        private Level build() {
            return new Level(width, height, base, enemySpawn, charMap);
        }

    }
}
