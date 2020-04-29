package Model;

public class KubWitolda {
    int vertex_count = 36;
    private float [] texCoords,vertices,colors,normals,vertexNormals;

    public int getVertex_count() {
        return vertex_count;
    }

    public float[] getTexCoords() {
        return texCoords;
    }

    public float[] getVertices() {
        return vertices;
    }

    public KubWitolda(){

        this.vertices = new float[]{
                //ściana 1
                1.0f,-1.0f,-1.0f,1.0f,
                -1.0f, 1.0f,-1.0f,1.0f,
                -1.0f,-1.0f,-1.0f,1.0f,

                1.0f,-1.0f,-1.0f,1.0f,
                1.0f, 1.0f,-1.0f,1.0f,
                -1.0f, 1.0f,-1.0f,1.0f,

                //ściana 2
                -1.0f,-1.0f, 1.0f,1.0f,
                1.0f, 1.0f, 1.0f,1.0f,
                1.0f,-1.0f, 1.0f,1.0f,

                -1.0f,-1.0f, 1.0f,1.0f,
                -1.0f, 1.0f, 1.0f,1.0f,
                1.0f, 1.0f, 1.0f,1.0f,


                //ściana 3
                -1.0f,-1.0f,-1.0f,1.0f,
                1.0f,-1.0f, 1.0f,1.0f,
                1.0f,-1.0f,-1.0f,1.0f,

                -1.0f,-1.0f,-1.0f,1.0f,
                -1.0f,-1.0f, 1.0f,1.0f,
                1.0f,-1.0f, 1.0f,1.0f,

                //ściana 4
                -1.0f, 1.0f, 1.0f,1.0f,
                1.0f, 1.0f,-1.0f,1.0f,
                1.0f, 1.0f, 1.0f,1.0f,

                -1.0f, 1.0f, 1.0f,1.0f,
                -1.0f, 1.0f,-1.0f,1.0f,
                1.0f, 1.0f,-1.0f,1.0f,

                //ściana 5
                -1.0f,-1.0f,-1.0f,1.0f,
                -1.0f, 1.0f, 1.0f,1.0f,
                -1.0f,-1.0f, 1.0f,1.0f,

                -1.0f,-1.0f,-1.0f,1.0f,
                -1.0f, 1.0f,-1.0f,1.0f,
                -1.0f, 1.0f, 1.0f,1.0f,

                //ściana 6
                1.0f,-1.0f, 1.0f,1.0f,
                1.0f, 1.0f,-1.0f,1.0f,
                1.0f,-1.0f,-1.0f,1.0f,

                1.0f,-1.0f, 1.0f,1.0f,
                1.0f, 1.0f, 1.0f,1.0f,
                1.0f, 1.0f,-1.0f,1.0f,


        };
        this.colors = new float[]{
                1.0f,0.0f,0.0f,1.0f,
                1.0f,0.0f,0.0f,1.0f,
                1.0f,0.0f,0.0f,1.0f,

                1.0f,0.0f,0.0f,1.0f,
                1.0f,0.0f,0.0f,1.0f,
                1.0f,0.0f,0.0f,1.0f,

                0.0f,1.0f,0.0f,1.0f,
                0.0f,1.0f,0.0f,1.0f,
                0.0f,1.0f,0.0f,1.0f,

                0.0f,1.0f,0.0f,1.0f,
                0.0f,1.0f,0.0f,1.0f,
                0.0f,1.0f,0.0f,1.0f,

                0.0f,0.0f,1.0f,1.0f,
                0.0f,0.0f,1.0f,1.0f,
                0.0f,0.0f,1.0f,1.0f,

                0.0f,0.0f,1.0f,1.0f,
                0.0f,0.0f,1.0f,1.0f,
                0.0f,0.0f,1.0f,1.0f,

                1.0f,1.0f,0.0f,1.0f,
                1.0f,1.0f,0.0f,1.0f,
                1.0f,1.0f,0.0f,1.0f,

                1.0f,1.0f,0.0f,1.0f,
                1.0f,1.0f,0.0f,1.0f,
                1.0f,1.0f,0.0f,1.0f,

                0.0f,1.0f,1.0f,1.0f,
                0.0f,1.0f,1.0f,1.0f,
                0.0f,1.0f,1.0f,1.0f,

                0.0f,1.0f,1.0f,1.0f,
                0.0f,1.0f,1.0f,1.0f,
                0.0f,1.0f,1.0f,1.0f,

                1.0f,1.0f,1.0f,1.0f,
                1.0f,1.0f,1.0f,1.0f,
                1.0f,1.0f,1.0f,1.0f,

                1.0f,1.0f,1.0f,1.0f,
                1.0f,1.0f,1.0f,1.0f,
                1.0f,1.0f,1.0f,1.0f,
        };
            this.texCoords = new float[]{

                    1.0f, 0.0f,   //A
                    0.0f, 1.0f,    //B
                    0.0f, 0.0f,    //C

                    1.0f, 0.0f,    //A
                    1.0f, 1.0f,    //D
                    0.0f, 1.0f,    //B


                    1.0f, 0.0f,   //A
                    0.0f, 1.0f,    //B
                    0.0f, 0.0f,    //C

                    1.0f, 0.0f,    //A
                    1.0f, 1.0f,    //D
                    0.0f, 1.0f,    //B


                    1.0f, 0.0f,   //A
                    0.0f, 1.0f,    //B
                    0.0f, 0.0f,    //C

                    1.0f, 0.0f,    //A
                    1.0f, 1.0f,    //D
                    0.0f, 1.0f,    //B


                    1.0f, 0.0f,   //A
                    0.0f, 1.0f,    //B
                    0.0f, 0.0f,    //C

                    1.0f, 0.0f,    //A
                    1.0f, 1.0f,    //D
                    0.0f, 1.0f,    //B


                    1.0f, 0.0f,   //A
                    0.0f, 1.0f,    //B
                    0.0f, 0.0f,    //C

                    1.0f, 0.0f,    //A
                    1.0f, 1.0f,    //D
                    0.0f, 1.0f,    //B


                    1.0f, 0.0f,   //A
                    0.0f, 1.0f,    //B
                    0.0f, 0.0f,    //C

                    1.0f, 0.0f,    //A
                    1.0f, 1.0f,    //D
                    0.0f, 1.0f,    //B

            };



    }
}
