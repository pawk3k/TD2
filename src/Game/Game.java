package Game;

import org.joml.Vector3f;

import java.util.HashMap;
import java.util.Map;

public class Game {
    public static Map<Integer, GameObject> GameObjects = new HashMap<Integer, GameObject>();
    private int idCounter = 1;
    private GameController gameController = new GameController();

    public int createGameObject(int parent, int model) throws Exception {
        GameObjects.put(idCounter, new GameObject(idCounter, parent, model));
        return idCounter++;
    }

    public void init() throws Exception {
        int towerModel = gameController.addModel("res/tower.obj","res/tower.png", 0, "src/shaders/vertex.glsl", "src/shaders/fragment.glsl");
        towerA = createGameObject(0, towerModel);
        towerB = createGameObject(towerA, towerModel);
        GameObjects.get(towerB).localSetTranslation(new Vector3f(2f,0,0));
        GameObjects.get(towerB).updateM();
    }

    int towerA, towerB;
    double lastTime = 0;

    public void update(double time){
        GameObjects.get(towerA).globalSetTranslation(new Vector3f(0,(float) time % 3,0));
        GameObjects.get(towerA).updateMF();
    }
}
