package Renderer;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Model {
    private int vaoID;
    private int[] indices;

    private Vector3f scale;
    private Vector3f rotation;
    private Vector3f translation;
    private Matrix4f M;

    public Model(int vaoID, int[] indices) {
        this.vaoID = vaoID;
        this.indices = indices;
        this.scale = new Vector3f(1.0f, 1.0f, 1.0f);
        this.rotation = new Vector3f(0.0f, 0.0f, 0.0f);
        this.translation = new Vector3f(0.0f, 0.0f, 0.0f);
        this.M = new Matrix4f();
    }

    public void rotate(float pitch, float yaw, float roll){
        this.rotation.add(new Vector3f( pitch, yaw, roll));
        updateM();
    };

    public void translate(float x, float y, float z){
        this.translation.add(new Vector3f( x, y, z));
        updateM();
    };

    public void scale(float x, float y, float z){
        this.scale.add(new Vector3f( x, y, z));
        updateM();
    }

    public void setRotation(float pitch, float yaw, float roll){
        this.rotation = new Vector3f( pitch, yaw, roll);
        updateM();
    };

    public void setPosition(float x, float y, float z){
        this.translation = new Vector3f( x, y, z);
        updateM();
    };

    public void setScale(float x, float y, float z){
        this.scale = new Vector3f( x, y, z);
        updateM();
    };

    private void updateM(){
        this.M = new Matrix4f()
                .translate(translation)
                .rotationXYZ(
                        (float) Math.toRadians(rotation.x),
                        (float) Math.toRadians(rotation.y),
                        (float) Math.toRadians(rotation.z)
                )
                .scale(scale);
    }

    public Matrix4f getM(){
        return M;
    }

    public int[] getIndices() {
        return indices;
    }

    public int getVaoID() {
        return vaoID;
    }
}