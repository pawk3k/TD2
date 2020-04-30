package Renderer;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Model {
    private final int vaoID;
    private final int indicesNum;
    private final int shaderProgramId;

    private Vector3f scale;
    private Vector3f rotation;
    private Vector3f translation;
    private Matrix4f M;

    public Model(int vaoID, int indicesNum, int shaderProgramId) {
        this.vaoID = vaoID;
        this.indicesNum = indicesNum;
        this.shaderProgramId = shaderProgramId;
        this.scale = new Vector3f(1.0f, 1.0f, 1.0f);
        this.rotation = new Vector3f(0.0f, 0.0f, 0.0f);
        this.translation = new Vector3f(0.0f, 0.0f, 0.0f);
        this.M = new Matrix4f();
    }

    public void rotate(float pitch, float yaw, float roll) {
        this.rotation.add(new Vector3f(pitch, yaw, roll));
        updateM();
    }

    ;

    public void translate(float x, float y, float z) {
        this.translation.add(new Vector3f(x, y, z));
        updateM();
    }

    ;

    public void scale(float x, float y, float z) {
        this.scale.add(new Vector3f(x, y, z));
        updateM();
    }

    public void setRotation(float pitch, float yaw, float roll) {
        this.rotation = new Vector3f(pitch, yaw, roll);
        updateM();
    }

    ;

    public void setPosition(float x, float y, float z) {
        this.translation = new Vector3f(x, y, z);
        updateM();
    }

    ;

    public void setScale(float x, float y, float z) {
        this.scale = new Vector3f(x, y, z);
        updateM();
    }

    ;

    private void updateM() {
        this.M = new Matrix4f()
                .translate(translation)
                .rotationXYZ(
                        (float) Math.toRadians(rotation.x),
                        (float) Math.toRadians(rotation.y),
                        (float) Math.toRadians(rotation.z)
                )
                .scale(scale);
    }

    public Matrix4f getM() {
        return M;
    }

    public int getIndicesNumber() {
        return indicesNum;
    }

    public int getShaderProgramId() {
        return shaderProgramId;
    }

    public int getVaoID() {
        return vaoID;
    }
}