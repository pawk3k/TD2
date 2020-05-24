package Game;

import Camera.Camera;
import Game.PlayableGameObjects.Enemy;
import Game.PlayableGameObjects.Turret;
import Model.Light;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import static java.lang.Math.*;

public class Game {
    public static Map<Integer, GameObject> GameObjects = new HashMap<Integer, GameObject>();
    public static Map<Integer, GameObject> GameHudObjects = new HashMap<Integer, GameObject>();
    public static Map<Integer, GameObject> GameColoredObjects = new HashMap<Integer, GameObject>();

    public static Map<Integer, Light> lightPoints = new HashMap<>();
    public static Map<Integer, Turret> turrets = new HashMap<>();
    public static Map<Integer, Enemy> enemies = new HashMap<>();
    public static Camera camera = new Camera();
    public static int[][] GameMap;
    private static int playerHealth = 5;
    private static int[] heartsHUD = new int[playerHealth];

    private GameController gameController = GameController.getInstance();

    /**
     * GameMap explanation:
     * Road:
     *  4 last bits considered to show next direction 0000
     *  UP RIGHT LEFT DOWN                            URLD
     * Build places:
     *  16bit marks building place, 15-14 bits marks turret type, 13-1 bits marks turret id
     *  1 00 0000000000000
     */
    public void init() throws Exception {
        GameMap = new int[][] {
                {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},
                {0,  0,  0,32768,  0,  0,  0,  0,  0,  0,  0,  0,  4,  4,  4,  4,  4,  4,  4,  0},
                {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  8,  0,  0,  0,  0,  0,  0,  0},
                {4,  4,  4,  4,  4,  4,  1,  0,  0,  0,  0,  0,  8,  0,32768,  0,  0,  0,  0,  0},
                {0,  0,  0,  0,  0,  0,  1,  0,  0,  0,  0,  0,  8,  0,  0,  0,  0,  0,  0,  0},
                {0,  0,  0,  0,32768,  0,  1,  0,  0,  0,  0,  0,  8,  2,  2,  2,  2,  0,  0,  0},
                {0,  0,  0,  0,  0,  0,  1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  8,  0,  0,  0},
                {0,  0,  0,  0,  0,  0,  1,  0,  0,  0,  0,  0,32768,  0,32768,  0,  8,  0,32768,  0},
                {0,  0,  0,  0,32768,  0,  1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  8,  0,  0,  0},
                {0,  0,  0,  0,  0,  0,  5,  4,  4,  4,  4,  4,  4,  4,  4,  4,  8,  0,  0,  0},
                {0,  0,  0,  1,  2,  2,  2,  0,  0,  0,  0,  0,  0,  0,  8,  0,  0,  0,  0,  0},
                {0,  0,  0,  1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  8,  0,32768,  0,  0,  0},
                {0,  0,  0,  1,  0,32768,  0,  0,  0,  0,  0,  0,32768,  0,  8,  0,  0,  0,  0,  0},
                {0,  0,  0,  1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  8,  0,  0,  0,  0,  0},
                {0,32768,  0,  1,  0,  0,  0,  0,  0,  0,  0,  0,  4,  4,  8,  0,  0,  0,  0,  0},
                {0,  0,  0,  4,  4,  4,  4,  1,  0,  0,  0,  0,  8,  0,  0,  0,  0,  0,  0,  0},
                {0,  0,  0,  0,  0,  0,  0,  1,  0,  0,  0,  0,  8,  0,  0,  0,  0,  0,  0,  0},
                {0,  0,  0,  0,  0,32768,  0,  4,  4,  4,  4,  4,  8,  0,32768,  0,  0,  0,  0,  0},
                {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},
                {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},
        };


        System.out.println(GameController.calcVec(new int[]{1,3},1,10));
        Light light = new Light();
        lightPoints.put(0, light);

        int map = gameController.setMap("res/Map/Map.obj","res/Map/mapTexture.png", 0, "src/shaders/vertex.glsl", "src/shaders/fragment.glsl");
        GameObjects.get(map).scale(new Vector3f(10f,10f,10f));
        GameObjects.get(map).updateM();


        int  hudModel1 = gameController.addModel("res/HpPlane.obj","res/tower.png",0,"src/Hud/v_hud.glsl", "src/Hud/f_hud.glsl");
        int  coloredModel = gameController.addModel("res/HpPlane.obj","res/tower.png",0,"src/ColoredShaders/v_colored.glsl", "src/ColoredShaders/f_colored.glsl");
        int  hudModel2 = gameController.addModel("res/HpPlane.obj","res/hp.png",2,"", "");


        float x = 3.5f;
        for(int i = 0; i < playerHealth; i++){
            heartsHUD[i] = gameController.createGameHudObject(0,hudModel2);
            GameHudObjects.get(heartsHUD[i]).translate(new Vector3f(x,15.f,0.f));
            GameHudObjects.get(heartsHUD[i]).setRotation(new Vector3f(0.f,0,(float) Math.toRadians(180)));
            GameHudObjects.get(heartsHUD[i]).updateM();
            x-=2.0;
        }





/**
 *
 * Colored squres
**/

        float step = 0.0f; // 0.05
        for (int i = 0; i < GameMap[0].length; i++) {
            for(int j = 0 ; j< GameMap[0].length;j++){

                if(GameMap[i][j]>32000){
                    int coloredSquare = gameController.createGameColoredObject(0,coloredModel);
                    Vector3f position = GameController.calcVec(new int[]{i,j},3.2f,10);
                    GameColoredObjects.get(coloredSquare).setTranslation(position);
                    GameColoredObjects.get(coloredSquare).setScale(new Vector3f(0.5f,0.5f,0.5f).mul(10));

                    float id = ((float)GameMap[i][j] + (i + j)*100)/40000;
                    GameColoredObjects.get(coloredSquare).setColor(new Vector3f(step,0.f,1.0f));
                    int colorId = (int)ceil(255*step);
                    GameColoredObjects.get(coloredSquare).setColorId(colorId);
                    step+=0.03;
                    GameColoredObjects.get(coloredSquare).setPlaceOnMap(new int[]{i,j});
                    GameColoredObjects.get(coloredSquare).updateM();

                }

            }
        }


        int cubeGO4 = gameController.createGameHudObject(0,hudModel1);
        GameHudObjects.get(cubeGO4).translate(new Vector3f(4,-15.f,0.f));
        GameHudObjects.get(cubeGO4).setRotation(new Vector3f(0.f,0,(float) Math.toRadians(180)));
        GameHudObjects.get(cubeGO4).setScale(new Vector3f(2.f,2,2.0f));
        GameHudObjects.get(cubeGO4).updateM();

        int cubeGO5 = gameController.createGameHudObject(0,hudModel1);
        GameHudObjects.get(cubeGO5).translate(new Vector3f(-4,-15.f,0.f));
        GameHudObjects.get(cubeGO5).setRotation(new Vector3f(0.f,0,(float) Math.toRadians(180)));
        GameHudObjects.get(cubeGO5).setScale(new Vector3f(2.f,2,2.0f));
        GameHudObjects.get(cubeGO5).updateM();

        gameController.spawnEnemy(1, new int[] {3,0});

    }

    public static void getDamage() {
        if(playerHealth > 0) {
            playerHealth--;
            GameController.removeListHUD.add(heartsHUD[playerHealth]);
        }
    }

    boolean en;
    float oldTime = 0;
    public void update(float time) throws Exception {
        if(playerHealth <= 0){
            if(playerHealth == -1) return;
            else{
                playerHealth--;
                int  gameOverModel = gameController.addModel("res/HpPlane.obj","res/GameOver.png",0,"src/Hud/v_hud.glsl", "src/Hud/f_hud.glsl");
                int hgo = gameController.createGameHudObject(0,gameOverModel);
                GameHudObjects.get(hgo).setRotation(new Vector3f(0.f,0,(float) Math.toRadians(180)));
                GameHudObjects.get(hgo).setScale(new Vector3f(-10, 10, 0));
                GameHudObjects.get(hgo).updateM();
            }
        }

        float delta = time - oldTime;
        oldTime = time;
        if(((int)time) % 2 == 0){
            if(!en){
                gameController.spawnEnemy(new Random().nextInt(2) + 1, new int[] {3,0});
                en = true;
            }
        }
        else en = false;

        for(Iterator<Map.Entry<Integer, Turret>> it = Game.turrets.entrySet().iterator(); it.hasNext();){
            Turret turret = it.next().getValue();
            try {
                turret.move(delta);

            }catch (Exception e){
                System.out.println(e);
            }
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