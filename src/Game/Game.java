package Game;

import MainDisplay.Input;
import Model.Light;
import SomeMath.Bezier;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Game {
    public static Map<Integer, GameObject> GameObjects = new HashMap<Integer, GameObject>();
//    public static Map<Integer, GameObject> PointLights = new HashMap<Integer, GameObject>();
    public static Map<Integer, Light> lightPoints = new HashMap<>();
    public static ArrayList<Vector3f> bezierPoints = new ArrayList<>();
    private int idCounter = 1;
    private GameController gameController = new GameController();

    public int createGameObject(int parent, int model) throws Exception {
        GameObjects.put(idCounter, new GameObject(idCounter, parent, model));
        return idCounter++;
    }




    public void init() throws Exception {
        int barrel = gameController.addModel("res/Mortar/Barrel.obj","res/Mortar/Gold_metal_texture.png", 0, "src/shaders/vertex.glsl", "src/shaders/fragment.glsl");
        int cannonBall = gameController.addModel("res/Mortar/Cannonball.obj","res/Mortar/Wood_planks_texture.png", 0, "src/shaders/vertex.glsl", "src/shaders/fragment.glsl");
        int foundation = gameController.addModel("res/Mortar/Foundation.obj","res/Mortar/Metal_plates_texture.png", 0, "src/shaders/vertex.glsl", "src/shaders/fragment.glsl");
        body = createGameObject(0, cannonBall);

        Light light = new Light();
//        light.setPosition(new Vector4f(0,0,0,1));
        light.setColor(new Vector4f(1,0,0,1));
        lightPoints.put(0,light);
        bezierPoints.add(new Vector3f(-2,0,0));
        bezierPoints.add(new Vector3f(1,2,0));
        bezierPoints.add(new Vector3f(4,0,0));
        wheel1 = createGameObject(body, barrel);

        wheel2 = createGameObject(body, foundation);
        wheel3 = createGameObject(body, cannonBall);
        wheel4 = createGameObject(body, cannonBall);
        GameObjects.get(wheel1).setRotation(new Vector3f(0,0,-90));
        GameObjects.get(wheel2).setRotation(new Vector3f(0,0,90));
        GameObjects.get(wheel3).setRotation(new Vector3f(0,0,-90));
        GameObjects.get(wheel4).setRotation(new Vector3f(0,0,90));
        GameObjects.get(wheel1).setTranslation(new Vector3f(2,0,0));
        GameObjects.get(wheel2).setTranslation(new Vector3f(-2,0,0));
        GameObjects.get(wheel3).setTranslation(new Vector3f(2,4,0));
        GameObjects.get(wheel4).setTranslation(new Vector3f(-2,4,0));
        GameObjects.get(wheel1).setScale(new Vector3f(0.4f,0.4f,0.4f));
        GameObjects.get(wheel2).setScale(new Vector3f(0.4f,0.4f,0.4f));
        GameObjects.get(wheel3).setScale(new Vector3f(0.4f,0.4f,0.4f));
        GameObjects.get(wheel4).setScale(new Vector3f(0.4f,0.4f,0.4f));
    }

    int body, wheel1, wheel2, wheel3, wheel4;

    public void update(float time){
        System.out.println(time);


        lightPoints.get(0).setPosition(new Vector4f(0,0,3-(float)time,1));
        GameObjects.get(wheel4).setTranslation(new Vector3f(0,0,3-(float)time));
        GameObjects.get(wheel1).setTranslation(Bezier.generalBezierCurve(bezierPoints,time));
        GameObjects.get(wheel2).rotate(new Vector3f(1,0,0));
        GameObjects.get(wheel3).rotate(new Vector3f(1,0,0));
        GameObjects.get(wheel4).rotate(new Vector3f(1,0,0));
        GameObjects.get(body).updateMF();
    }
}
