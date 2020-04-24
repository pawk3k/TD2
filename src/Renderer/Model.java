package Renderer;

import org.joml.Matrix4f;

public class Model {
    private int vaoID;
    private int vertexCount;
    private int[] indices;
    private Matrix4f S;
    private Matrix4f T;
    private Matrix4f R;

    public int[] getIndices() {
        return indices;
    }

    public void setIndices(int[] indices) {
        this.indices = indices;
    }

    public Model(int vaoID, int vertexCount, int[] indices) {
        this.vaoID = vaoID;
        this.vertexCount = vertexCount;
        this.indices = indices;
        this.S = new Matrix4f(
                1.0f, 0.0f, 0.0f, 0.0f,
                0.0f, 1.0f, 0.0f, 0.0f,
                0.0f, 0.0f, 1.0f, 0.0f,
                0.0f, 0.0f, 0.0f, 1.0f
        );
        this.T = new Matrix4f(
                1.0f, 0.0f, 0.0f, 0.0f,
                0.0f, 1.0f, 0.0f, 0.0f,
                0.0f, 0.0f, 1.0f, 0.0f,
                0.0f, 0.0f, 0.0f, 1.0f
        );
        this.R = new Matrix4f(
                1.0f, 0.0f, 0.0f, 0.0f,
                0.0f, 1.0f, 0.0f, 0.0f,
                0.0f, 0.0f, 1.0f, 0.0f,
                0.0f, 0.0f, 0.0f, 1.0f
        );
    }

    /**
     * @param yaw x axis
     * @param pitch y axis
     * @param roll z axis
     */
    public void rotate(float yaw, float pitch, float roll){

    };

    public void translate(float x, float y, float z){

    };

    public void scale(float x, float y, float z){

    };

    public int getVaoID() {
        return vaoID;
    }

    public int getVertexCount() {
        return vertexCount;
    }
}