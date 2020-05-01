package Model;
import Renderer.Model;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner; // Import the Scanner class to read text files
public class ObjModel {
    private String pathO;

    public float[] getTextureBuffer() {
        return textureBuffer;
    }

    private float[] textureBuffer;


    private float[] verticesBuffer;

    private int textureId;


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
    }


    public void read_object_file(){
        ArrayList<Float> vertices = new ArrayList<>();
        ArrayList<Float> textures = new ArrayList<>();
        ArrayList<Integer> indeciesPos = new ArrayList<>();
        ArrayList<Vector3f> postionsT = new ArrayList<>();
        ArrayList<Integer> indeciesTex = new ArrayList<>();
        ArrayList<Vector2f> texturesT = new ArrayList<>();

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
                    postionsT.add(vector3f);

                }
                if(data.startsWith("vt ")){
                    String[] splited = data.split(" ");
                    textures.add(Float.parseFloat(splited[1]));
                    textures.add(Float.parseFloat(splited[2]));
                    float u  = Float.parseFloat(splited[1]);
                    float v  = Float.parseFloat(splited[2]);
                    Vector2f vector2f = new Vector2f(u,v);
                    texturesT.add(vector2f);


                }
                if(data.startsWith("f")){
                    String[] line_arr = data.split(" ");
                    for(int i = 1;i<4;i++){
                        String[] splitted_arr =  line_arr[i].split("/");
                        indeciesPos.add(Integer.parseInt(splitted_arr[0]));
                        indeciesTex.add(Integer.parseInt(splitted_arr[1]));
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
        float[] textureArr = new float[postionsT.size()*2];

        for (int j = 0; j < indeciesPos.size() ; j++) {
            int current_pos = indexArr[j] ;
            Vector2f current_tex = texturesT.get(indeciesTex.get(j)-1);
            textureArr[current_pos *2 ] = current_tex.x;
            textureArr[current_pos *2 +1 ] =  1 - current_tex.y;


        }

        this.textureBuffer = textureArr;




    }
}
