package Game;

import Camera.Camera;
import Game.PlayableGameObjects.Turret;
import Model.Light;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.Map;

public class Game {
    public static Map<Integer, GameObject> GameObjects = new HashMap<Integer, GameObject>();
    public static Map<Integer, Light> lightPoints = new HashMap<>();
    public static Map<Integer, Turret> turrets = new HashMap<>();
    public static Camera camera = new Camera();

//    public static ArrayList<Vector3f> bezierPoints = new ArrayList<>();

    private GameController gameController = new GameController();

    public void init() throws Exception {

        //gameController.loadEasyTurret();
        //gameController.spawnTurret(0, new Vector3f(0,0,0));
        int map = gameController.setMap("res/Map/Map.obj","res/Map/mapTexture.png", 0, "src/shaders/vertex.glsl", "src/shaders/fragment.glsl");
        GameObjects.get(map).scale(new Vector3f(10f,10f,10f));
        GameObjects.get(map).updateM();

        gameController.loadEasyTurret();
        gameController.spawnTurret(0, new Vector3f());

        Light light = new Light();
        lightPoints.put(0, light);

        //bezierPoints.add(new Vector3f(-2,0,0));
    }

    public void update(float time){
        //lightPoints.get(0).setPosition(new Vector4f(0,0,3 - time,1));

    }
}
