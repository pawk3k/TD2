package Model;


public class Assets {


    private float[] vertices = new float[] {
            -1f, 1f, 0, //TOP LEFT     0
            1f, 1f, 0,  //TOP RIGHT    1
            1f, -1f, 0, //BOTTOM RIGHT 2
            -1f, -1f, 0,//BOTTOM LEFT  3
    };

    private float[] texture = new float[] {
            0,0,
            1,0,
            1,1,
            0,1,
    };


    private int[] indices = new int[] {
            0,1,2,
            2,3,0
    };

    public float[] getVertices() {
        return vertices;
    }

    public float[] getTexture() {
        return texture;
    }

    public int[] getIndices() {
        return indices;
    }

    public Assets(){

    }

	public static void initAsset() {

		
	}
	
}
