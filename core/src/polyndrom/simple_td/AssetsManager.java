package polyndrom.simple_td;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class AssetsManager {

    private static Map<String, Texture> textures = new HashMap<>();

    public static void loadTextures() {
        String[] texturesNames = new String[] {
                "empty_field",
                "road_left_to_right",
                "road_down_to_up",
                "road_up_to_right",
                "road_down_to_right",
                "road_down_to_left",
                "road_up_to_left",
                "road_4x",
                "base",
                "turret1_base",
                "turret1_gun"
        };
        for (String textureName: texturesNames) {
            textures.put(textureName, new Texture(Utils.getSpriteFile(textureName)));
        }
    }

    public static Texture getTexture(String textureName) {
        return textures.get(textureName);
    }

}
