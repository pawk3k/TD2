package Game;

import Camera.Camera;
import Game.PlayableGameObjects.Enemy;
import Game.PlayableGameObjects.Turret;
import Model.Assets;
import Model.Light;
import Renderer.Loader;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static Game.GameController.models;

public class Game {
    public static Map<Integer, GameObject> GameObjects = new HashMap<Integer, GameObject>();
    public static Map<Integer, GameObject> GameHudObjects = new HashMap<Integer, GameObject>();
    public static Map<Integer, Light> lightPoints = new HashMap<>();
    public static Map<Integer, Turret> turrets = new HashMap<>();
    public static Camera camera = new Camera();
    public static int[][] GameMap;

    private GameController gameController = new GameController();

    /**
     * GameMap explanation:
     * Road:
     *  4 last bits considered to show next direction 0000
     *  UP RIGHT LEFT DOWN                            URLD
     * Build places:
     *  8 bit marks building place, another 7 is building place ID
     *  1000000
     */
    public void init() throws Exception {
        GameMap = new int[][] {
                {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},
                {0,  0,  0,257,  0,  0,  0,  0,  0,  0,  0,  0,  4,  4,  4,  4,  4,  4,  4,  4},
                {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  8,  0,  0,  0,  0,  0,  0,  0},
                {4,  4,  4,  4,  4,  4,  1,  0,  0,  0,  0,  0,  8,  0,269,  0,  0,  0,  0,  0},
                {0,  0,  0,  0,  0,  0,  1,  0,  0,  0,  0,  0,  8,  0,  0,  0,  0,  0,  0,  0},
                {0,  0,  0,  0,258,  0,  1,  0,  0,  0,  0,  0,  8,  2,  2,  2,  2,  0,  0,  0},
                {0,  0,  0,  0,  0,  0,  1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  8,  0,  0,  0},
                {0,  0,  0,  0,  0,  0,  1,  0,  0,  0,  0,  0,267,  0,266,  0,  8,  0,268,  0},
                {0,  0,  0,  0,259,  0,  1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  8,  0,  0,  0},
                {0,  0,  0,  0,  0,  0,  3,  4,  4,  4,  4,  4,  4,  4,  4,  4,  8,  0,  0,  0},
                {0,  0,  0,  1,  2,  2,  2,  0,  0,  0,  0,  0,  0,  0,  8,  0,  0,  0,  0,  0},
                {0,  0,  0,  1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  8,  0,265,  0,  0,  0},
                {0,  0,  0,  1,  0,260,  0,  0,  0,  0,  0,  0,263,  0,  8,  0,  0,  0,  0,  0},
                {0,  0,  0,  1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  8,  0,  0,  0,  0,  0},
                {0,261,  0,  1,  0,  0,  0,  0,  0,  0,  0,  0,  4,  4,  8,  0,  0,  0,  0,  0},
                {0,  0,  0,  4,  4,  4,  4,  1,  0,  0,  0,  0,  8,  0,  0,  0,  0,  0,  0,  0},
                {0,  0,  0,  0,  0,  0,  0,  1,  0,  0,  0,  0,  8,  0,  0,  0,  0,  0,  0,  0},
                {0,  0,  0,  0,  0,262,  0,  4,  4,  4,  4,  4,  8,  0,264,  0,  0,  0,  0,  0},
                {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},
                {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},
        };

        Light light = new Light();
        lightPoints.put(0, light);

        int map = gameController.setMap("res/Map/Map.obj","res/Map/mapTexture.png", 0, "src/shaders/vertex.glsl", "src/shaders/fragment.glsl");
//        int hudModel = gameController.addModel("res/box4.obj","res/tower.png", 0, "src/shaders/vertex.glsl", "src/shaders/fragment.glsl");
//        GameObjects.get(map).scale(new Vector3f(10f,10f,10f));
//        GameObjects.get(map).updateM();
        int  hudModel1 = gameController.addModel("res/box4.obj","res/tower.png",0,"src/Hud/v_hud.glsl", "src/Hud/f_hud.glsl");
        int  hudModel2 = gameController.addModel("res/HpPlane.obj","res/hp.png",2,"", "");

//        int barrel = gameController.addModel("res/Mortar/Barrel.obj","res/Mortar/Wood_planks_texture.png",1,"", "");
        int cube = gameController.addModel("res/box1.obj","res/red_brick.png",1,"", "");
        int cube2 = gameController.addModel("res/box1.obj","res/red_brick.png",2,"", "");

        Loader loader = new Loader();
        Assets assets = new Assets();
        int res =  loader.createVAO(assets.getVertices(),assets.getIndices(),assets.getTexture());

//        models.put(res,)
//        int barrelGO1 = gameController.createGameObject(0,barrel);
//        int cubeGO2 = gameController.createGameHudObject(0,hudModel);
//        int cubeGO3 = gameController.createGameObject(0,hudModel);
//        int cubeGO4 = gameController.createGameHudObject(0,cube2);

//        int cubeGO3 = gameController.createGameHudObject(map,hudModel1);
        int cubeGO4 = gameController.createGameHudObject(map,hudModel2);


//
//        int cubeGO31 = gameController.createGameObject(map,cube);
//        int cubeGO32 = gameController.createGameObject(map,cube);



//        GameHudObjects.get(cubeGO3).setTranslation(new Vector3f(10.f,0.f,0.0f));
//        GameHudObjects.get(cubeGO3).setScale(new Vector3f(5.f,5.f,5.f));
//        GameHudObjects.get(cubeGO3).setRotation(new Vector3f(0.f,0.f,-20.0f));


//        GameHudObjects.get(cubeGO4).setTranslation(new Vector3f(-12.f,0.f,0.0f));
//        GameHudObjects.get(cubeGO4).setScale(new Vector3f(5.f,5.f,5.f));
//        GameHudObjects.get(cubeGO4).setRotation(new Vector3f(0.f,90.f,0.f));



//        GameHudObjects.get(cubeGO3).updateM();
//        GameHudObjects.get(cubeGO4).updateM();

//        enemy = new Enemy(barrelGO1,new int[] {3,0},5f);
    }

    Enemy enemy;
    float oldTime = 0;
    public void update(float time){
        float delta = time - oldTime;

        oldTime = time;
//        enemy.move(delta);
    }
}
