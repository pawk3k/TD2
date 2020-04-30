package Renderer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class Loader {

    private List<Integer> vaos = new ArrayList<>();
    private List<Integer> eboNums = new ArrayList<>();
    private int last_index = 0;

    public int createVAO(float[] vertices, int[] indices,float[] textures) {
        int vao, vbo1,vbo2, ebo;
//        glEnable(GL_DEPTH_TEST);
        vao = glGenVertexArrays();
        vbo1 = glGenBuffers();
        vbo2 = glGenBuffers();
        ebo = glGenBuffers();
        glBindVertexArray(vao);
//        FloatBuffer verticesBuffer = storeDataInIntBuffer(vertices);
//        FloatBuffer texturesBuffer = storeDataInIntBuffer(vertices);
//
//        glBindBuffer(GL_ARRAY_BUFFER, vbo1);
//        glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);
//
//
//
//
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

        storeDataInAttributeList(0,3,vertices);
        storeDataInAttributeList(1,2,textures);
//
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
//
//        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
//        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
//        glBindBuffer(GL_ARRAY_BUFFER, vbo2);
//        glBufferData(GL_ARRAY_BUFFER, texturesBuffer, GL_STATIC_DRAW);
//
//
//        glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
//
//        glEnableVertexAttribArray(0);
//
//        glEnableVertexAttribArray(1);
//
//        glBindBuffer(GL_ARRAY_BUFFER, 0);
//        glBindVertexArray(0);

        vaos.add(vao);
        eboNums.add(indices.length);

        return last_index++;
    }

    public int getVao(int index) {
        return vaos.get(index);
    }

    public int getEboNum(int index) {
        return eboNums.get(index);
    }

    private FloatBuffer storeDataInFloatBuffer(float[] data) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    private void storeDataInAttributeList(int attributeNumber,int size, float[] data) {
        int vboID = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
        FloatBuffer buffer = storeDataInFloatBuffer(data);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(attributeNumber, size, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }
}