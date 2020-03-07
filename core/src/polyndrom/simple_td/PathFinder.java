package polyndrom.simple_td;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Queue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PathFinder {

    public static List<Vector2> find(Level level, Vector2 from, Vector2 to) {
        List<Vector2> path = new ArrayList<>();
        Queue<Vector2> queue = new Queue<>();
        boolean[][] visited = new boolean[level.getWidth()][level.getHeight()];
        int[][] weights = new int[level.getWidth()][level.getHeight()];
        queue.addLast(from);
        weights[(int) from.x][(int) from.y] = 0;
        visited[(int) from.x][(int) from.y] = true;
        while (queue.size > 0) {
            Vector2 pos = queue.first();
            queue.removeFirst();
            int x0 = (int) pos.x;
            int y0 = (int) pos.y;
            int[] dx = new int[]{1, -1, 0, 0};
            int[] dy = new int[]{0, 0, 1, -1};
            for (int i = 0; i < 4; i++) {
                int x = x0 + dx[i];
                int y = y0 + dy[i];
                if (x < 0 || x >= level.getWidth() || y < 0 || y >= level.getHeight()) continue;
                if (visited[x][y]) continue;
                if (level.getCharMap().get(x).get(y) == '#') continue;
                weights[x][y] = weights[x0][y0] + 1;
                visited[x][y] = true;
                queue.addLast(new Vector2(x, y));
            }
        }
        int currentX = (int) to.x;
        int currentY = (int) to.y;
        while (currentX != (int) from.x || currentY != (int) from.y) {
            path.add(new Vector2(currentX, currentY));
            int prevX = currentX;
            int prevY = currentY;
            int[] dx = new int[]{1, -1, 0, 0};
            int[] dy = new int[]{0, 0, 1, -1};
            for (int i = 0; i < 4; i++) {
                int x = currentX + dx[i];
                int y = currentY + dy[i];
                if (x < 0 || x >= level.getWidth() || y < 0 || y >= level.getHeight()) continue;
                if (!visited[x][y]) continue;
                if (weights[x][y] < weights[prevX][prevY]) {
                    prevX = x;
                    prevY = y;
                }
            }
            currentX = prevX;
            currentY = prevY;
        }
        path.add(new Vector2((int) from.x, (int) from.y));
        Collections.reverse(path);
        return path;
    }

}
