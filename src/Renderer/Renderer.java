package Renderer;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;
import Model.Light;
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
        Matrix4f viewMatrix = new Matrix4f().identity().lookAt(0.0f, -0.f, -20.0f,
                0.0f, 0.0f, 0.0f,
                0.0f, 1.0f, 0.0f);
        float aspectRatio = (float) 900 / 600;
        Matrix4f projectionMatrix = new Matrix4f().perspective((float) Math.toRadians(45.0f), aspectRatio, 0.01f, 100.0f);

        projectionMatrix.get(P);

    }

    public void refreshScreen() {
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public void render(Model model, int textureId, Matrix4f camera, Light light) throws Exception {




        camera.get(V);

        model.getM().get(M);
//        model.rotate(0, 1, 0);
        int m_Matrix = GL30.glGetUniformLocation(model.getShaderProgramId(), "M");
        int p_Matrix = GL30.glGetUniformLocation(model.getShaderProgramId(), "P");
        int v_Matrix = GL30.glGetUniformLocation(model.getShaderProgramId(), "V");
        int lightPos = GL30.glGetUniformLocation(model.getShaderProgramId(), "my_l.position");
        int lightCol = GL30.glGetUniformLocation(model.getShaderProgramId(),"my_l.color");
        Vector4f vecPos = light.getPosition();
        Vector4f vecCol = light.getColor();
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);
        GL30.glUniformMatrix4fv(m_Matrix, false, M);
        GL30.glUniformMatrix4fv(p_Matrix, false, P);
        GL30.glUniformMatrix4fv(v_Matrix, false, V);
        GL30.glUniform4f(lightPos,vecPos.x,vecPos.y,vecPos.z,vecPos.w);
        GL30.glUniform4f(lightCol,vecCol.x,vecCol.y,vecCol.z,vecCol.w);
        glUseProgram(model.getShaderProgramId());
        glBindVertexArray(model.getVaoID());

        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D,textureId);
//        glPolygonMode( GL_FRONT_AND_BACK, GL_LINE );

        glDrawElements(GL_TRIANGLES, model.getIndicesNumber(), GL_UNSIGNED_INT, 0);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);



    }


}