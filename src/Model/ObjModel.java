package Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ObjModel {
    private String path;
    private float[] textureBuffer;
    private float[] verticesBuffer;
    private int[] indeciesBuffer;

    public float[] getTextureBuffer() {
        return textureBuffer;
    }

    public float[] getVerticesBuffer() {
        return verticesBuffer;
    }

    public int[] getIndeciesBuffer() {
        return indeciesBuffer;
    }

    public ObjModel(String path){
        this.path = path;
    }

    public void read_object_file(){
        ArrayList<Float> vertices = new ArrayList<>();
        ArrayList<Float> textures = new ArrayList<>();
        ArrayList<Integer> indeciesPos = new ArrayList<>();
        ArrayList<Integer> indeciesTex = new ArrayList<>();

        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {

                String data = myReader.nextLine();
                if(data.startsWith("v ")){
                    String trimmed = data.replaceAll(" {2}"," ");
                    String[] arr = trimmed.split(" ");

                    vertices.add(Float.parseFloat(arr[1]));
                    vertices.add(Float.parseFloat(arr[2]));
                    vertices.add(Float.parseFloat(arr[3]));

                }
                if(data.startsWith("vt ")){
                    String[] splited = data.split(" ");
                    textures.add(Float.parseFloat(splited[1]));
                    textures.add(Float.parseFloat(splited[2]));

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

        float[] verticesArr = new float[vertices.size()];
        int i = 0;
        for(Float element : vertices){
            verticesArr[i++] = element;
        }
        this.verticesBuffer = verticesArr;

        int[] indexArr = new int[indeciesPos.size()];
        i = 0;
        for (int element :indeciesPos){
            indexArr[i++] = element - 1;
        }
        this.indeciesBuffer = indexArr;

        float[] textureArr = new float[verticesArr.length/3*2];
        for (int j = 0; j < indeciesPos.size(); j++) {
            int should_be_id = indeciesPos.get(j);
            textureArr[should_be_id] = textures.get(indeciesTex.get(j)-1);
        }
        this.textureBuffer = textureArr;

        System.out.println(Arrays.toString(verticesArr));
        System.out.println(Arrays.toString(textureArr));
        System.out.println(Arrays.toString(indexArr));
    }
}
