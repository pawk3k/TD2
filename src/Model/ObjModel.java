package Model;

import org.joml.Vector2f;
import org.joml.Vector3f;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ObjModel {
    private String pathO;

    public float[] getTextureBuffer() {
        return textureBuffer;
    }

    private float[] textureBuffer;

    private float[] verticesBuffer;

    private float[] normalsBuffer;


    private int textureId;

    public float[] getNormalsBuffer() { return normalsBuffer; }

    public float[] getVerticesBuffer() {
        return verticesBuffer;
    }

    public int[] getIndeciesBuffer() {
        return indeciesBuffer;
    }

    private int[] indeciesBuffer;

    public int getTextureId() {
        return textureId;
    }

    public ObjModel(String path_to_object, String path_to_texture){
        this.pathO = path_to_object;
        TextureClass wall = new TextureClass(path_to_texture);
        wall.create();
        this.textureId = wall.getTextureID();

        read_object_file();
    }

    private void read_object_file(){
        ArrayList<Float> vertices = new ArrayList<>(); // vertecies arr where every float is single vertex pos x y z
        ArrayList<Integer> indeciesPos = new ArrayList<>();
        ArrayList<Integer> indeciesNormals = new ArrayList<>();
        ArrayList<Vector3f> positionV = new ArrayList<>();
        ArrayList<Vector3f> normalsV = new ArrayList<>();
        ArrayList<Integer> indeciesTex = new ArrayList<>();
        ArrayList<Vector2f> texturesV = new ArrayList<>();


        try {

            File myObj = new File(pathO);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {

                String data = myReader.nextLine();
                if(data.startsWith("v ")){
                    String trimmed = data.replaceAll(" {2}"," ");
                    String[] arr = trimmed.split(" ");

                    vertices.add(Float.parseFloat(arr[1]));
                    vertices.add(Float.parseFloat(arr[2]));
                    vertices.add(Float.parseFloat(arr[3]));


                    float x = Float.parseFloat(arr[1]);
                    float y = Float.parseFloat(arr[2]);
                    float z = Float.parseFloat(arr[3]);

                    Vector3f vector3f = new Vector3f(x,y,z);
                    positionV.add(vector3f);

                }
                if(data.startsWith("vt ")){
                    String[] splited = data.split(" ");
                    float u  = Float.parseFloat(splited[1]);
                    float v  = Float.parseFloat(splited[2]);
                    Vector2f vector2f = new Vector2f(u,v);
                    texturesV.add(vector2f);


                }
                if(data.startsWith("vn ")){
                    String[] splitted = data.split(" ");
                    float x = Float.parseFloat(splitted[1]);
                    float y = Float.parseFloat(splitted[2]);
                    float z = Float.parseFloat(splitted[3]);

                    Vector3f normalVec = new Vector3f(x,y,z);
                    normalsV.add(normalVec);
                }

                if(data.startsWith("f")){
                    String[] line_arr = data.split(" ");
                    for(int i = 1;i<4;i++){
                        String[] splitted_arr =  line_arr[i].split("/");
                        indeciesPos.add(Integer.parseInt(splitted_arr[0]));
                        indeciesTex.add(Integer.parseInt(splitted_arr[1]));
                        indeciesNormals.add(Integer.parseInt(splitted_arr[2]));

                    }
                }

            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }


        // Converting data from ArrayList to Float array

        float[] verticesArr = new float[vertices.size()];
        int i = 0;
        for(Float element : vertices){
            verticesArr[i++] = element;
        }
        this.verticesBuffer = verticesArr;

        int[] indexArr = new int[indeciesPos.size()];
        i = 0;
        for (int element :indeciesPos){
            indexArr[i++] = element -1;
        }
        this.indeciesBuffer = indexArr;


        // Reordering Texture Positions
        float[] textureArr = new float[positionV.size()*2];

        for (int j = 0; j < indeciesPos.size() ; j++) {
            int current_pos = indexArr[j] ;
            Vector2f current_tex = texturesV.get(indeciesTex.get(j)-1);
            textureArr[current_pos *2 ] = current_tex.x;
            textureArr[current_pos *2 +1 ] =  1 - current_tex.y;


        }
        this.textureBuffer = textureArr;

        // Reordering Normals postions
        float[] normalsArr = new float[positionV.size()*3];

        for (int j = 0; j < indeciesPos.size(); j++) {
            int current_pos = indexArr[j];
            Vector3f current_norm = normalsV.get(indeciesNormals.get(j)-1);
            normalsArr[current_pos*3] = current_norm.x;
            normalsArr[current_pos*3+1] = current_norm.y;
            normalsArr[current_pos*3+2] = current_norm.z;
        }
        System.out.println("kek");
        this.normalsBuffer = normalsArr;
    }
}