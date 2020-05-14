package Camera;

import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {
    private Vector3f position;
    private Vector3f rotation;
    private Matrix4f V;

    public Matrix4f getV() {
        return V;
    }

    public Camera(){

        position = new Vector3f(0, 14, -20);
        rotation = new Vector3f(0, 1, 0);
        V =  new Matrix4f().identity().lookAt(position.x, position.y, position.z,
                0.0f, 0.0f, 0.0f,
                0.0f, 1.0f, 0.0f);



    }

    public void movePosition(float offsetX, float offsetY, float offsetZ) {
        if ( offsetZ != 0 ) {
            position.x += (float)Math.sin(Math.toRadians(rotation.y)) * -1.0f * offsetZ;
            position.z += (float)Math.cos(Math.toRadians(rotation.y)) * offsetZ;
        }
        if ( offsetX != 0) {
            position.x += (float)Math.sin(Math.toRadians(rotation.y - 90)) * -1.0f * offsetX;
            position.z += (float)Math.cos(Math.toRadians(rotation.y - 90)) * offsetX;
        }
        position.y += offsetY;
    }


    public Matrix4f getViewMatrix() {
        Vector3f cameraPos = this.getPosition();
        Vector3f rotation = this.getRotation();

        // First do the rotation so camera rotates over its position
//        V.rotate((float)Math.toRadians(rotation.x), new Vector3f(1, 0, 0))
//                .rotate((float)Math.toRadians(rotation.y), new Vector3f(0, 1, 0));
//        // Then do the translation
//        V.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);



        return
                new Matrix4f().identity().lookAt(cameraPos.x,cameraPos.y,cameraPos.z,
                0.f,0.f,0.f,
                0.f,1.f,0.f);
    }

    public Matrix4f getViewMatrix1() {
        Vector3f cameraPos = getPosition();
        Vector3f rotation = getRotation();

        V.identity();
        // First do the rotation so camera rotates over its position
        V.rotate((float)Math.toRadians(rotation.x), new Vector3f(1, 0, 0))
                .rotate((float)Math.toRadians(rotation.y), new Vector3f(0, 1, 0));
        // Then do the translation
        V.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);
        return V;
    }
    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getPosition(){
        return position;
    }
    public Vector3f getRotation(){
        return rotation;
    }

    public void setRotation(float x, float y, float z) {
        rotation.x = x;
        rotation.y = y;
        rotation.z = z;
    }

    public void moveRotation(float offsetX, float offsetY, float offsetZ) {
        rotation.x += offsetX;
        rotation.y += offsetY;
        rotation.z += offsetZ;
    }


}
