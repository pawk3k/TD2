package Renderer;

/**
 * Represents a loaded model. It contains the ID of the VAO that contains the
 * model's data, and holds the number of vertices in the model.
 *
 * @author Karl
 *
 */
public class Model {

    private int vaoID;
    private int vertexCount;
    private int[] indecies;

    public int[] getIndecies() {
        return indecies;
    }

    public void setIndecies(int[] indecies) {
        this.indecies = indecies;
    }

    public Model(int vaoID, int vertexCount, int[] indicies) {
        this.vaoID = vaoID;
        this.vertexCount = vertexCount;
        this.indecies = indicies;
    }

    /**
     * @return The ID of the VAO which contains the data about all the geometry
     *         of this model.
     */
    public int getVaoID() {
        return vaoID;
    }

    /**
     * @return The number of vertices in the model.
     */
    public int getVertexCount() {
        return vertexCount;
    }

}