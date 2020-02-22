package polyndrom.simple_td;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Utils {

    private static FileHandle getFromAssets(String filePath) {
        return Gdx.files.internal(String.format("%s", filePath));
    }

    public static FileHandle getLevelFile(int levelId) {
        return getFromAssets(String.format("levels/%d/level.json", levelId));
    }

    public static FileHandle getLevelMapFile(int levelId) {
        return getFromAssets(String.format("levels/%d/map.txt", levelId));
    }

    public static FileHandle getSpriteFile(String spriteName) {
        return getSpriteFile(spriteName, "png");
    }

    public static FileHandle getSpriteFile(String spriteName, String ext) {
        return getFromAssets(String.format("sprites/%s.%s", spriteName, ext));
    }

    // Map coordinate to screen coordinate
    public static float MC2SC(int mapCoordinate) {
        return MC2SC((float) mapCoordinate);
    }

    // Map coordinate to screen coordinate
    public static float MC2SC(float mapCoordinate) {
        return mapCoordinate * Constants.BLOCK_WIDTH;
    }

    // Screen coordinate to map coordinate
    public static int SC2MC(float screenCoordinate) {
        return MathUtils.floor(screenCoordinate / Constants.BLOCK_WIDTH);
    }

    // Screen coordinate to map coordinate
    public static int SC2MC(int screenCoordinate) {
        return SC2MC((float) screenCoordinate);
    }

    public static Vector2 mapPositionToScreenPosition(Vector2 mapPosition) {
        return new Vector2(MC2SC((int) mapPosition.x),
                            MC2SC((int) mapPosition.y));
    }

    public static Vector2 screenPositionToMapPosition(Vector2 screenPosition) {
        return new Vector2(SC2MC(screenPosition.x),
                            SC2MC(screenPosition.y));
    }

}
