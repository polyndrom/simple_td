package polyndrom.simple_td;

import com.badlogic.gdx.Game;
import polyndrom.simple_td.screen.GameScreen;

public class Main extends Game {

    public static Game game = null;

    @Override
    public void create() {
        if (game == null) {
            game = this;
        }
        setScreen(new GameScreen());
    }
}
