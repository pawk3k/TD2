package Renderer;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import Model.TextureClass;
public class Renderer {
    private FloatBuffer M;
    private FloatBuffer P;
    private FloatBuffer V;

    public Renderer() {

        MemoryStack stack = MemoryStack.stackPush();
        this.M  = stack.mallocFloat(16);
        this.P = stack.mallocFloat(16);
        this.V = stack.mallocFloat(16);
        Matrix4f viewMatrix = new Matrix4f().identity().lookAt(0.0f, 10.f, -30.0f,
                0.0f, 0.0f, 0.0f,
                0.0f, 1.0f, 0.0f);
        float aspectRatio = (float) 900 / 600;
        Matrix4f projectionMatrix = new Matrix4f().perspective((float) Math.toRadians(45.0f), aspectRatio, 0.01f, 100.0f);

        projectionMatrix.get(P);
        viewMatrix.get(V);

    }

    public void refreshScreen() {
        glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public void render(Model model, int textureId) throws Exception {
        model.rotate(0, 1, 0);


        model.getM().get(M);

        int m_Matrix = GL30.glGetUniformLocation(model.getShaderProgramId(), "M");
        int p_Matrix = GL30.glGetUniformLocation(model.getShaderProgramId(), "P");
        int v_Matrix = GL30.glGetUniformLocation(model.getShaderProgramId(), "V");
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        GL30.glUniformMatrix4fv(m_Matrix, false, M);
        GL30.glUniformMatrix4fv(p_Matrix, false, P);
        GL30.glUniformMatrix4fv(v_Matrix, false, V);

        glUseProgram(model.getShaderProgramId());
        glBindVertexArray(model.getVaoID());

        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D,textureId);
//        glPolygonMode( GL_FRONT_AND_BACK, GL_LINE );

        glDrawElements(GL_TRIANGLES, model.getIndicesNumber(), GL_UNSIGNED_INT, 0);

//        glDisableVertexAttribArray(0);
//        glDisableVertexAttribArray(1);



    }


}