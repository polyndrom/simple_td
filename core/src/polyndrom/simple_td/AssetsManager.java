package polyndrom.simple_td;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class AssetsManager {

    private static Map<String, Texture> textures = new HashMap<>();

    public static void loadTextures() {
        FileHandle[] texturesFiles = Gdx.files.internal("sprites/").list(".png");
        for (FileHandle textureFile: texturesFiles) {
            textures.put(textureFile.nameWithoutExtension(), new Texture(textureFile));
        }
    }

    public static Texture getTexture(String textureName) {
        return textures.get(textureName);
    }

}
