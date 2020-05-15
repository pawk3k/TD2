package Renderer;

public class Model {
    private final int vaoID;
    private final int indicesNum;
    private final int shaderProgramId;
    private int textureID;

    public Model(int vaoID, int indicesNum, int shaderProgramId, int textureID) {
        this.vaoID = vaoID;
        this.indicesNum = indicesNum;
        this.shaderProgramId = shaderProgramId;
        setTextureID(textureID);
    }

    public void setTextureID(int textureID){
        this.textureID = textureID;
    }

    public int getTextureID(){
        return textureID;
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