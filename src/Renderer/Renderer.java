package Renderer;

import org.lwjgl.opengl.GL30;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class Renderer {
    private FloatBuffer M;

    public Renderer(){
        M = ByteBuffer.allocateDirect(16 << 2)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
    }

    public void refreshScreen() {
        glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT);
    }

    public void render(Model model) throws Exception{
        int m_Matrix = GL30.glGetUniformLocation(model.getShaderProgramId(), "M");
        model.getM().get(M);
        GL30.glUniformMatrix4fv(m_Matrix, false, M);

        glUseProgram(model.getShaderProgramId());
        glBindVertexArray(model.getVaoID());
        glDrawElements(GL_TRIANGLES, model.getIndicesNumber(), GL_UNSIGNED_INT, 0);

    }
}
