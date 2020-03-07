package polyndrom.simple_td;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import polyndrom.simple_td.tower.Tower;

import java.util.*;

public class Level implements Disposable {

    private int width;
    private int height;
    private Vector2 base;
    private Vector2 enemySpawn;
    private Map<Character, Texture> texturesMapper;
    private List<List<Character>> charMap;

    public Vector2 getBase() {
        return base;
    }

    public Vector2 getEnemySpawn() {
        return enemySpawn;
    }

    public List<List<Character>> getCharMap() {
        return charMap;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Level(int levelId) {
        JsonReader jsonReader = new JsonReader();
        JsonValue root = jsonReader.parse(Utils.getLevelFile(levelId));
        width = root.getInt("width");
        height = root.getInt("height");
        base = new Vector2(Utils.MC2SC(root.get("base").getInt("x")),
                           Utils.MC2SC(root.get("base").getInt("y")));
        enemySpawn = new Vector2(Utils.MC2SC(root.get("enemy_spawn").getInt("x")),
                                 Utils.MC2SC(root.get("enemy_spawn").getInt("y")));
        charMap = new ArrayList<>();
        String[] buffer = Utils.getLevelMapFile(levelId).readString().split(System.lineSeparator());
        loadCharMap(buffer);
        loadMapTextures();
    }

    private void loadCharMap(String[] buffer) {
        for (int i = 0; i < width; i++) {
            charMap.add(new ArrayList<>());
            for (int j = height - 1; j >= 0; j--) {
                charMap.get(i).add(buffer[j].charAt(i));
            }
        }
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (charMap.get(i).get(j) == '#') continue;
                int[] dx = new int[]{0, 1, 0, -1};
                int[] dy = new int[]{1, 0, -1, 0};
                boolean[] hasRoads = new boolean[4];
                int roadsCount = 0;
                for (int k = 0; k < 4; k++) {
                    int x = i + dx[k];
                    int y = j + dy[k];
                    if (x < 0 || x >= width || y < 0 || y >= height) continue;
                    if (charMap.get(x).get(y) != '#') {
                        hasRoads[k] = true;
                        roadsCount++;
                    }
                }
                if (roadsCount == 1) {
                    if (hasRoads[0] || hasRoads[2]) charMap.get(i).set(j, 'v');
                    else if (hasRoads[1] || hasRoads[3]) charMap.get(i).set(j, 'h');
                }
                else if (roadsCount == 2) {
                    if (hasRoads[0] && hasRoads[2]) charMap.get(i).set(j, 'v');
                    else if (hasRoads[1] && hasRoads[3]) charMap.get(i).set(j, 'h');
                    else if (hasRoads[0] && hasRoads[1]) charMap.get(i).set(j, '1');
                    else if (hasRoads[1] && hasRoads[2]) charMap.get(i).set(j, '2');
                    else if (hasRoads[2] && hasRoads[3]) charMap.get(i).set(j, '3');
                    else if (hasRoads[3] && hasRoads[0]) charMap.get(i).set(j, '4');
                } else if (roadsCount == 4) {
                    charMap.get(i).set(j, '+');
                }
            }
        }
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
        texturesMapper.put('.', AssetsManager.getTexture("empty_field"));
    }

    public void renderMap(SpriteBatch batch) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                float x = Utils.MC2SC(i);
                float y = Utils.MC2SC(j);
                batch.draw(texturesMapper.get(charMap.get(i).get(j)), x, y, Constants.BLOCK_WIDTH, Constants.BLOCK_HEIGHT);
            }
        }
    }

    @Override
    public void dispose() {

    }

}
