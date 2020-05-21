package Game;

import Camera.Camera;
import Game.PlayableGameObjects.Enemy;
import Game.PlayableGameObjects.Turret;
import Model.Light;
import Renderer.Loader;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Game {
    public static Map<Integer, GameObject> GameObjects = new HashMap<Integer, GameObject>();
    public static Map<Integer, GameObject> GameHudObjects = new HashMap<Integer, GameObject>();
    public static Map<Integer, Light> lightPoints = new HashMap<>();
    public static Map<Integer, Turret> turrets = new HashMap<>();
    public static Map<Integer, Enemy> enemies = new HashMap<>();
    public static Camera camera = new Camera();
    public static int[][] GameMap;

    private GameController gameController = new GameController();

    /**
     * GameMap explanation:
     * Road:
     *  4 last bits considered to show next direction 0000
     *  UP RIGHT LEFT DOWN                            URLD
     * Build places:
     *  8 bit marks building place, another 7 is building place ID (number)
     *  1000000
     */
    public void init() throws Exception {
        GameMap = new int[][] {
                {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},
                {0,  0,  0,257,  0,  0,  0,  0,  0,  0,  0,  0,  4,  4,  4,  4,  4,  4,  4,  0},
                {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  8,  0,  0,  0,  0,  0,  0,  0},
                {4,  4,  4,  4,  4,  4,  1,  0,  0,  0,  0,  0,  8,  0,269,  0,  0,  0,  0,  0},
                {0,  0,  0,  0,  0,  0,  1,  0,  0,  0,  0,  0,  8,  0,  0,  0,  0,  0,  0,  0},
                {0,  0,  0,  0,258,  0,  1,  0,  0,  0,  0,  0,  8,  2,  2,  2,  2,  0,  0,  0},
                {0,  0,  0,  0,  0,  0,  1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  8,  0,  0,  0},
                {0,  0,  0,  0,  0,  0,  1,  0,  0,  0,  0,  0,267,  0,266,  0,  8,  0,268,  0},
                {0,  0,  0,  0,259,  0,  1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  8,  0,  0,  0},
                {0,  0,  0,  0,  0,  0,  5,  4,  4,  4,  4,  4,  4,  4,  4,  4,  8,  0,  0,  0},
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

        // Hearts
        for (int i = 0; i < 6; i++) {
            int cubeGO4 = gameController.createGameHudObject(0,hudModel2);
        }

        float x = 3.5f;

        for( Integer go : GameHudObjects.keySet()){
            GameHudObjects.get(go).translate(new Vector3f(x,15.f,0.f));
            GameHudObjects.get(go).rotate(new Vector3f(0.f,0,180.f));
            GameHudObjects.get(go).updateM();
            x-=2.0;
        }
        //Hearts
        gameController.spawnEnemy(1, new int[] {3,0});

        gameController.spawnTurret(1, new int[] {1,3});
        gameController.spawnTurret(1, new int[] {5,4});
        gameController.spawnTurret(1, new int[] {8,4});
        gameController.spawnTurret(1, new int[] {12,5});
        gameController.spawnTurret(1, new int[] {14,1});
        gameController.spawnTurret(1, new int[] {17,5});
        gameController.spawnTurret(1, new int[] {3,14});
        gameController.spawnTurret(1, new int[] {7,14});
        gameController.spawnTurret(1, new int[] {7,12});
        gameController.spawnTurret(1, new int[] {7,18});
        gameController.spawnTurret(1, new int[] {11,16});
        gameController.spawnTurret(1, new int[] {12,12});
        gameController.spawnTurret(1, new int[] {17,14});
    }

    boolean en;

    float oldTime = 0;
    public void update(float time) throws Exception {
        float delta = time - oldTime;

        oldTime = time;

        if(((int)time) % 4 == 0){
            if(!en){
                gameController.spawnEnemy(1, new int[] {3,0});
                en = true;
            }
        }
        else en = false;

        for(Iterator<Map.Entry<Integer, Turret>> it = Game.turrets.entrySet().iterator(); it.hasNext();){
            Turret turret = it.next().getValue();
            turret.move(delta);
            if(GameController.removeListTurrets.contains(turret.getMyID())) it.remove();
        }
        GameController.removeListTurrets.clear();

        for(Iterator<Map.Entry<Integer, Enemy>> it = Game.enemies.entrySet().iterator(); it.hasNext();){
            Enemy enemy = it.next().getValue();
            enemy.move(delta);
            if(GameController.removeListEnemies.contains(enemy.getMyID())) it.remove();
        }
        GameController.removeListEnemies.clear();
    }
}
