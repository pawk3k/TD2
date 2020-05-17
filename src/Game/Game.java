package Game;

import Game.PlayableGameObjects.Turret;
import Model.Light;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.Map;

public class Game {
    public static Map<Integer, GameObject> GameObjects = new HashMap<Integer, GameObject>();
    public static Map<Integer, Light> lightPoints = new HashMap<>();
    public static Map<Integer, Turret> turrets = new HashMap<>();
    public static Camera Camera = new Camera();
//    public static ArrayList<Vector3f> bezierPoints = new ArrayList<>();

    private GameController gameController = new GameController();

    public void init() throws Exception {
        int barrel = gameController.addModel("res/Mortar/Barrel.obj","res/Mortar/Gold_metal_texture.png", 0, "src/shaders/vertex.glsl", "src/shaders/fragment.glsl");

        gameController.loadEasyTurret();
        gameController.spawnTurret(0, new Vector3f(0,0,0));

        Light light = new Light();
        lightPoints.put(0, light);

        //bezierPoints.add(new Vector3f(-2,0,0));
    }

    public void update(float time){
        //lightPoints.get(0).setPosition(new Vector4f(0,0,3 - time,1));

    }
}
