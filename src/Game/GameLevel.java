package Game;

public class GameLevel extends GameObject {
    public static int[][] GameMap = new int[20][20];

    public GameLevel(int id, int model) throws Exception {
        super(id, 0, model);
    }
}
