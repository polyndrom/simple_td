package polyndrom.simple_td;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Level implements Disposable {

    private int width;
    private int height;
    private Position basePosition;
    private Position enemySpawnPosition;
    private Map<Character, Texture> textures;
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
                .basePosition(new Position(root.get("base").getInt("x"), root.get("base").getInt("x")))
                .enemySpawnPosition(new Position((float) root.get("enemy_spawn").getInt("x"),
                                                 (float) root.get("enemy_spawn").getInt("x")))
                .textures(loadTextures())
                .charMap(charMap)
                .build();
    }

    private Level(int width, int height, Position basePosition, Position enemySpawnPosition,
                  Map<Character, Texture> textures, List<List<Character>> charMap) {
        this.width = width;
        this.height = height;
        this.basePosition = basePosition;
        this.enemySpawnPosition = enemySpawnPosition;
        this.textures = textures;
        this.charMap = charMap;
    }

    private static Map<Character, Texture> loadTextures() {
        Map<Character, FileHandle> charMapper = new TreeMap<>();
        Map<Character, Texture> textureMapper = new TreeMap<>();
        charMapper.put('#', Utils.getSpriteFile("empty_field"));
        charMapper.put('h', Utils.getSpriteFile("road_left_to_right"));
        charMapper.put('v', Utils.getSpriteFile("road_down_to_up"));
        charMapper.put('1', Utils.getSpriteFile("road_up_to_right"));
        charMapper.put('2', Utils.getSpriteFile("road_down_to_right"));
        charMapper.put('3', Utils.getSpriteFile("road_down_to_left"));
        charMapper.put('4', Utils.getSpriteFile("road_up_to_left"));
        charMapper.put('+', Utils.getSpriteFile("road_4x"));
        charMapper.put('B', Utils.getSpriteFile("base"));
        for (Character ch: charMapper.keySet()) {
            textureMapper.put(ch, new Texture(charMapper.get(ch)));
        }
        return textureMapper;
    }

    public void renderMap(SpriteBatch batch) {
        batch.begin();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                float x = Utils.MC2SC(j);
                float y = Gdx.graphics.getHeight() - Constants.BLOCK_HEIGHT - Utils.MC2SC(i);
                batch.draw(textures.get(charMap.get(i).get(j)), x, y, Constants.BLOCK_WIDTH, Constants.BLOCK_HEIGHT);
            }
        }
        // batch.draw(textures.get('B'), Utils.MC2SC(basePosition.x), Utils.MC2SC(basePosition.y));
        batch.end();
    }

    @Override
    public void dispose() {
        for (Texture texture: textures.values()) {
            texture.dispose();
        }
    }

    private static class LevelBuilder {

        private int width;
        private int height;
        private Position basePosition;
        private Position enemySpawnPosition;
        private Map<Character, Texture> textures;
        private List<List<Character>> charMap;

        private LevelBuilder width(int width) {
            this.width = width;
            return this;
        }

        private LevelBuilder height(int height) {
            this.height = height;
            return this;
        }

        private LevelBuilder basePosition(Position basePosition) {
            this.basePosition = basePosition;
            return this;
        }

        private LevelBuilder enemySpawnPosition(Position enemySpawnPosition) {
            this.enemySpawnPosition = enemySpawnPosition;
            return this;
        }

        private LevelBuilder textures(Map<Character, Texture> textures) {
            this.textures = textures;
            return this;
        }

        private LevelBuilder charMap(List<List<Character>> charMap) {
            this.charMap = charMap;
            return this;
        }

        private Level build() {
            return new Level(width, height, basePosition, enemySpawnPosition, textures, charMap);
        }

    }
}
