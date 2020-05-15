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
        body = createGameObject(0, towerModel);
        wheel1 = createGameObject(body, towerModel);
        wheel2 = createGameObject(body, towerModel);
        wheel3 = createGameObject(body, towerModel);
        wheel4 = createGameObject(body, towerModel);
        GameObjects.get(wheel1).setRotation(new Vector3f(0,0,-90));
        GameObjects.get(wheel2).setRotation(new Vector3f(0,0,90));
        GameObjects.get(wheel3).setRotation(new Vector3f(0,0,-90));
        GameObjects.get(wheel4).setRotation(new Vector3f(0,0,90));
        GameObjects.get(wheel1).setTranslation(new Vector3f(2,0,0));
        GameObjects.get(wheel2).setTranslation(new Vector3f(-2,0,0));
        GameObjects.get(wheel3).setTranslation(new Vector3f(2,4,0));
        GameObjects.get(wheel4).setTranslation(new Vector3f(-2,4,0));
        GameObjects.get(wheel1).setScale(new Vector3f(0.2f,0.2f,0.2f));
        GameObjects.get(wheel2).setScale(new Vector3f(0.2f,0.2f,0.2f));
        GameObjects.get(wheel3).setScale(new Vector3f(0.2f,0.2f,0.2f));
        GameObjects.get(wheel4).setScale(new Vector3f(0.2f,0.2f,0.2f));
    }

    int body, wheel1, wheel2, wheel3, wheel4;

    public void update(double time){
        GameObjects.get(wheel1).rotate(new Vector3f(1,0,0));
        GameObjects.get(wheel2).rotate(new Vector3f(1,0,0));
        GameObjects.get(wheel3).rotate(new Vector3f(1,0,0));
        GameObjects.get(wheel4).rotate(new Vector3f(1,0,0));
        GameObjects.get(body).updateMF();
    }
}
