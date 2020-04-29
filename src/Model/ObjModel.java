package Model;
import Renderer.Model;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner; // Import the Scanner class to read text files
public class ObjModel {
    private String path;

    private float[] verticesBuffer;

    public float[] getVerticesBuffer() {
        return verticesBuffer;
    }

    public int[] getIndeciesBuffer() {
        return indeciesBuffer;
    }

    private int[] indeciesBuffer;

    public ObjModel(String path){
        this.path = path;
    }


    public void read_object_file(){
        ArrayList<Float> vertices = new ArrayList<>();
        ArrayList<Integer> indecies = new ArrayList<>();

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
                if(data.startsWith("f")){
                    String[] line_arr = data.split(" ");
                    for(int i = 1;i<4;i++){
                        String[] splitted_arr =  line_arr[i].split("/");
                        indecies.add(Integer.parseInt(splitted_arr[0]));
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
        int[] indexArr = new int[indecies.size()];
        i = 0;
        for (int element :indecies){
            indexArr[i++] = element;
        }
        this.indeciesBuffer = indexArr;

    }



}
