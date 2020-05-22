package Game;

import Game.PlayableGameObjects.Enemy;
import Game.PlayableGameObjects.Turret;
import Model.ObjModel;
import Renderer.Loader;
import Renderer.Model;
import org.joml.Vector3f;
import org.lwjgl.system.MathUtil;
import shaders.Shader;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * e.g. Game Manager (maybe better to rename it later). Used to storage models, shaders etc.
 */
public class GameController {
    public static Map<Integer, Model> models = new HashMap<>();
    public  static Map<Integer, Shader> shaders = new HashMap<>();

    /**
     * Keys of objects which will be removed (DeathNotes)
     */
    public static ArrayList<Integer> removeListGameObjects = new ArrayList<>();
    public static ArrayList<Integer> removeListTurrets = new ArrayList<>();
    public static ArrayList<Integer> removeListEnemies = new ArrayList<>();

    private int[] easyTurretParts;
    private int[] hardTurretParts;
    private int[] easyEnemyParts;
    private int[] hardEnemyParts;
    private boolean[] loadedParts = new boolean[4]; //eTP, hTP, eEP, hEP

    private Loader loader = new Loader();
    private static int idGameObjectsCounter = 1;
    private int modelID = 1;
    private int shaderID = 1;
    private int idTurretsCounter = 1;
    private int idEnemyCounter = 1;

    private static float lerp(float a, float b, float f) { return a + f * (b - a); }
    public static float radianInterpolation(float from, float to, float f){
        float tmpLerp;
        if(Math.abs(from-to) < Math.PI) return lerp(from,to,f);
        if(from < 0){
            tmpLerp = lerp(from,(float)(-2*Math.PI-to),f);
            return (float) (tmpLerp < -Math.PI ? Math.PI : tmpLerp);
        }
        else{
            tmpLerp = lerp(from,(float) (2*Math.PI+to),f);
            return (float) (tmpLerp > Math.PI ? -Math.PI : tmpLerp);
        }
    }

    public static Vector3f calcVec(int[] pos, float y, float scale){
        return new Vector3f((float) (-9.5 + pos[0]) * scale, y,(float) (9.5 - pos[1]) * scale);
    }

    /**
     * Load necessary models into memory
     * @param type type of object which model is needed to load
     *             1 - easyTurretParts
     *             2 - hardTurretParts
     *             3 - easyEnemyParts
     *             4 - hardEnemyParts
     */
    private void loadPartsPack(int type) throws Exception {
        switch (type) {
            case 1:
                if (loadedParts[0]) return;
                easyTurretParts = new int[]{
                        addModel("res/Turrets/EasyTurret/Gun.obj", "res/Green.png", (modelID == 1 ? 0 : 1), "src/shaders/vertex.glsl", "src/shaders/fragment.glsl"),
                        addModel("res/Turrets/EasyTurret/BarrelGuard.obj", "res/Turrets/EasyTurret/ClippedMetal.png", 1, "", ""),
                        addModel("res/Turrets/EasyTurret/Platform.obj", "res/Turrets/EasyTurret/GreyMetal.png", 1, "", ""),
                        addModel("res/Turrets/EasyTurret/Foundation.obj", "res/Turrets/EasyTurret/ClippedMetal.png", 1, "", ""),
                        addModel("res/Turrets/Bullet/bullet.obj","res/Turrets/Bullet/bullet.png",1,"","")
                };
                loadedParts[0] = true;
                break;
            case 2:
                if (loadedParts[1]) return;
                System.out.println("hardTurretParts load test");
                loadedParts[1] = true;
                break;
            case 3:
                if (loadedParts[2]) return;
                easyEnemyParts = new int[]{
                        addModel("res/Enemy/eye.obj","res/Enemy/eye.png",(modelID == 1 ? 0 : 1),"src/shaders/vertex.glsl","src/shaders/fragment.glsl")
                };
                loadedParts[2] = true;
                break;
            case 4:
                if (loadedParts[3]) return;
                System.out.println("hardEnemyParts load test");
                loadedParts[3] = true;
                break;
            default:
                throw new Exception("Bad type");
        }
    }

    /**
     * @param type type of turret
     *             1 - easy Turret
     *             2 - hard Turret
     * @param pos position on map in matrix coordinates [y, x]
     */
    public void spawnTurret(int type, int[] pos) throws Exception {
        Vector3f position = calcVec(pos, 3f, 10f);
        switch (type) {
            case 1:
                loadPartsPack(1);
                int foundation = createGameObject(0, easyTurretParts[3]),
                        platform = createGameObject(foundation, easyTurretParts[2]),
                        gun = createGameObject(platform, easyTurretParts[0]);
                createGameObject(gun, easyTurretParts[1]);

                Game.turrets.put(idTurretsCounter, new Turret(idTurretsCounter, gun, platform, foundation, position, easyTurretParts[4]));
                break;
            case 2:
                loadPartsPack(2);
                //TODO: Додати турель 2
                break;
            default:
                throw new Exception("No such turret type");
        }
        idTurretsCounter++;
    }

    /**
     * @param type type of enemy
     *             1 - easy Enemy
     *             2 - hard Enemy
     * @param pos position on map in matrix coordinates [y, x]
     */
    public void spawnEnemy(int type, int[] pos) throws Exception {
        switch (type) {
            case 1:
                loadPartsPack(3);

                int body = createGameObject(0, easyEnemyParts[0]);
                Game.GameObjects.get(body).setRotation(new Vector3f(0,(float)Math.toRadians(90),0));
                Game.GameObjects.get(body).setScale(new Vector3f(2,2,2));

                Game.enemies.put(idEnemyCounter,new Enemy(idEnemyCounter, body, pos, 5f));
                break;
            case 2:
                loadPartsPack(4);
                //TODO: Додати ворога 2
                break;
            default:
                throw new Exception("No such turret type");
        }
        idEnemyCounter++;
    }

    /**
     * Load map to scene
     * @param objPath path to obj model
     * @param texPath path to model texture
     * @param shaderID if 0 load new shader, if another use loaded shader
     * @param vertexShaderCode path to vertex shader
     * @param fragmentShaderCode path to fragment shader
     * @return map game object ID
     */
    public int setMap(String objPath, String texPath, int shaderID, String vertexShaderCode, String fragmentShaderCode) throws Exception {
        int mapID = addModel(objPath, texPath, shaderID, vertexShaderCode, fragmentShaderCode);
        Game.GameObjects.put(idGameObjectsCounter, new GameLevel(idGameObjectsCounter, mapID));
        return idGameObjectsCounter++;
    }

    public int createGameHudObject(int parent, int model) throws Exception {
        Game.GameHudObjects.put(idGameObjectsCounter, new GameObject(idGameObjectsCounter, parent, model));
        return idGameObjectsCounter++;

    }

    /**
     * @param parent game object' parent, 0 if no parent
     * @param model game object' model
     * @return game object ID
     */
    public static int createGameObject(int parent, int model) throws Exception {
        Game.GameObjects.put(idGameObjectsCounter, new GameObject(idGameObjectsCounter, parent, model));
        return idGameObjectsCounter++;
    }


    /**
     * Add new model to storage in memory. Returns key to get model from models map.
     * @param objPath path to .obj file
     * @param texPath path to .png texture
     * @param shaderID set 0 to load new shader from vertexShaderCode and fragmentShaderCode
     *                 or id of already loaded shader into map and values from vertexShaderCode and fragmentShaderCode
     *                 will be not considered
     */
    public int addModel(String objPath, String texPath, int shaderID, String vertexShaderCode, String fragmentShaderCode) throws Exception {
        ObjModel loadedModel = new ObjModel(objPath, texPath);
        Shader shader;

        if (shaderID != 0) shader = shaders.get(shaderID);
        else{
            shader = new Shader(vertexShaderCode, fragmentShaderCode);
            shaders.put(this.shaderID++, shader);
        }

        int resource = loader.createVAO(
                loadedModel.getVerticesBuffer(),
                loadedModel.getIndeciesBuffer(),
                loadedModel.getTextureBuffer(),
                loadedModel.getNormalsBuffer()
        );

        models.put(modelID,new Model(
                loader.getVao(resource),
                loader.getEboNum(resource),
                shader.getProgramId(),
                loadedModel.getTextureId()
        ));
        return modelID++;
    }
}