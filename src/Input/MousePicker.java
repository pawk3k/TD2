package Input;


import Camera.Camera;
import MainDisplay.Input;
import Renderer.Renderer;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class MousePicker {

    private static final int RECURSION_COUNT = 200;
    private static final float RAY_RANGE = 600;

    private Vector3f currentRay = new Vector3f();

    private Matrix4f projectionMatrix;
    private Matrix4f viewMatrix;
    private Camera camera;
    private Input input;
    private long window;

    private Vector3f currentTerrainPoint;

    public MousePicker(Camera cam, Renderer render ,Input input) {
        this.input = input;
        camera = cam;
        projectionMatrix = render.getProjectionMatrix();
        viewMatrix = camera.getV();
    }

    public Vector3f getCurrentTerrainPoint() {
        return currentTerrainPoint;
    }

    public Vector3f getCurrentRay() {
        return currentRay;
    }


    public void update() {
        viewMatrix = camera.getV();
        currentRay = calculateMouseRay();
        System.out.println( vecTostring(currentRay) );
//        if (intersectionInRange(0, RAY_RANGE, currentRay)) {
//            currentTerrainPoint = binarySearch(0, 0, RAY_RANGE, currentRay);
//        } else {
//            currentTerrainPoint = null;
//        }
    }
    private String vecTostring(Vector3f vector3f){
        String out =  String.format(" X : %.2f | Y: %.2f |  Z: %.2f",vector3f.x,vector3f.y,vector3f.z);
        return out;
    }

    public Vector3f calculateMouseRay() {
        float mouseX = (float)input.getMouseX();
        float mouseY = (float)input.getMouseY();
        Vector2f normalizedCoords = getNormalisedDeviceCoordinates(mouseX, mouseY);//done
        Vector4f clipCoords = new Vector4f(normalizedCoords.x, normalizedCoords.y, -1.0f, 1.0f);//don
        Vector4f eyeCoords = toEyeCoords(clipCoords);
        return toWorldCoords(eyeCoords);
//        return new Vector3f();
    }

    private Vector2f getNormalisedDeviceCoordinates(float mouseX, float mouseY) {
        float x = (2.0f * mouseX) / 1300 - 1.0f;
        float y = 1.0f - (2.0f * mouseY) / 768;
        return new Vector2f(x, y);
    }
    private Vector4f toEyeCoords(Vector4f clipCoords) {
        Matrix4f invertedProjection = new Matrix4f(projectionMatrix);
        invertedProjection.invert();
        Vector4f clipC = new Vector4f(clipCoords);
        Vector4f eyeCoords = invertedProjection.transform(clipC);

        return new Vector4f(eyeCoords.x, eyeCoords.y, -1f, 0f);
    }

    private Vector3f toWorldCoords(Vector4f eyeCoords) {
        Matrix4f invertedView = new Matrix4f(viewMatrix);
        invertedView.invert();
        Vector4f eyeC = new Vector4f(eyeCoords);
        Vector4f rayWorld = invertedView.transform(eyeC);
        Vector3f mouseRay = new Vector3f(rayWorld.x, rayWorld.y, rayWorld.z);
        mouseRay =   mouseRay.normalize(1.0f);
        return mouseRay;
    }
//
//
//
//
//
//    //**********************************************************
//
//    private Vector3f getPointOnRay(Vector3f ray, float distance) {
//        Vector3f camPos = camera.getPosition();
//        Vector3f start = new Vector3f(camPos.x, camPos.y, camPos.z);
//        Vector3f scaledRay = new Vector3f(ray.x * distance, ray.y * distance, ray.z * distance);
//        return Vector3f.add(start, scaledRay, null);
//    }
//
//    private Vector3f binarySearch(int count, float start, float finish, Vector3f ray) {
//        float half = start + ((finish - start) / 2f);
//        if (count >= RECURSION_COUNT) {
//            Vector3f endPoint = getPointOnRay(ray, half);
//            Terrain terrain = getTerrain(endPoint.getX(), endPoint.getZ());
//            if (terrain != null) {
//                return endPoint;
//            } else {
//                return null;
//            }
//        }
//        if (intersectionInRange(start, half, ray)) {
//            return binarySearch(count + 1, start, half, ray);
//        } else {
//            return binarySearch(count + 1, half, finish, ray);
//        }
//    }
//
//    private boolean intersectionInRange(float start, float finish, Vector3f ray) {
//        Vector3f startPoint = getPointOnRay(ray, start);
//        Vector3f endPoint = getPointOnRay(ray, finish);
//        if (!isUnderGround(startPoint) && isUnderGround(endPoint)) {
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    private boolean isUnderGround(Vector3f testPoint) {
//        Terrain terrain = getTerrain(testPoint.getX(), testPoint.getZ());
//        float height = 0;
//        if (terrain != null) {
//            height = terrain.getHeightOfTerrain(testPoint.getX(), testPoint.getZ());
//        }
//        if (testPoint.y < height) {
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    private Terrain getTerrain(float worldX, float worldZ) {
//        return terrain;
//    }
//
}