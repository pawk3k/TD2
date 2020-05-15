package Game;

import Renderer.Loader;
import Renderer.Model;
import shaders.Shader;
import Model.ObjModel;

import java.util.HashMap;
import java.util.Map;

/**
 * e.g. Game Manager (maybe better to rename it later). Used to storage models, shaders etc.
 */
public class GameController {
    public static Map<Integer, Model> models = new HashMap<Integer, Model>();
    public static Map<Integer, Shader> shaders = new HashMap<Integer, Shader>();
    private Loader loader = new Loader();
    private int modelID = 1;
    private int shaderID = 1;

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
            shaders.put(shaderID++, shader);
        };

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
