package Game;

import Game.PlayableGameObjects.Turret;
import Renderer.Loader;
import Renderer.Model;
import org.joml.Vector3f;
import shaders.Shader;
import Model.ObjModel;

import java.util.HashMap;
import java.util.Map;

/**
 * e.g. Game Manager (maybe better to rename it later). Used to storage models, shaders etc.
 */
public class GameController {
    public static Map<Integer, Model> models = new HashMap<>();
    public Map<Integer, Shader> shaders = new HashMap<>();

    private int[] easyTurretParts;
    private int[] hardTurretParts;

    private Loader loader = new Loader();
    private int modelID = 1;
    private int shaderID = 1;
    private int idGameObjectsCounter = 1;
    private int idTurretsCounter = 1;

    /**
     * Load necessary models for easy turret creation into the memory
    */
    public void loadEasyTurret() throws Exception {
        easyTurretParts = new int[]{
                addModel("res/Turrets/EasyTurret/Gun.obj", "res/Turrets/EasyTurret/GoldMetal.png", (modelID == 1 ? 0 : 1), "src/shaders/vertex.glsl", "src/shaders/fragment.glsl"),
                addModel("res/Turrets/EasyTurret/BarrelGuard.obj", "res/Turrets/EasyTurret/ClippedMetal.png", 1, "", ""),
                addModel("res/Turrets/EasyTurret/Platform.obj", "res/Turrets/EasyTurret/GreyMetal.png", 1, "", ""),
                addModel("res/Turrets/EasyTurret/Foundation.obj", "res/Turrets/EasyTurret/ClippedMetal.png", 1, "", "")
        };
    }

    public void spawnTurret(int type, Vector3f position) throws Exception {
        //TODO: замінити аргумент vec3 позицію на точку на матриці(карті)
        if(type == 0){
            int gun = createGameObject(0, easyTurretParts[0]);
            createGameObject(gun, easyTurretParts[1]);
            Game.turrets.put(idTurretsCounter, new Turret(
                    idTurretsCounter, gun,
                    createGameObject(0, easyTurretParts[2]),
                    createGameObject(0, easyTurretParts[3]),
                    position));
        }
        else {
            System.out.println("Pawka-kakawka :3");
        }
    }

    public int setMap(String objPath, String texPath, int shaderID, String vertexShaderCode, String fragmentShaderCode) throws Exception {
        int mapID = addModel(objPath, texPath, shaderID, vertexShaderCode, fragmentShaderCode);
        Game.GameObjects.put(idGameObjectsCounter, new GameLevel(idGameObjectsCounter, mapID));
        return idGameObjectsCounter;
    }

    public int createGameObject(int parent, int model) throws Exception {
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
