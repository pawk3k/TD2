package Renderer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import shaders.ShaderProgram;
import java.nio.IntBuffer;
import java.lang.Math.*;
import org.lwjgl.glfw.GLFW;

/**
 * Handles the rendering of a model to the screen.
 *
 * @author Karl
 *
 */
public class Renderer {

    /**
     * This method must be called each frame, before any rendering is carried
     * out. It basically clears the screen of everything that was rendered last
     * frame (using the glClear() method). The glClearColor() method determines
     * the colour that it uses to clear the screen. In this example it makes the
     * entire screen red at the start of each frame.
     */
    public void refresh() {
        GL11.glClearColor(0.29f, 0.3f, 0.3f, 1.0f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }

    /**
     * Renders a model to the screen.
     *
     * Before we can render a VAO it needs to be made active, and we can do this
     * by binding it. We also need to enable the relevant attributes of the VAO,
     * which in this case is just attribute 0 where we stored the position data.
     *
     * The VAO can then be rendered to the screen using glDrawArrays(). We tell
     * it what type of shapes to render and the number of vertices that it needs
     * to render.
     *
     * After rendering we unbind the VAO and disable the attribute.
     *
     * @param model
     *            - The model to be rendered.
     */
    public void render(Model model) throws Exception{
        ShaderProgram shaderProgram = new ShaderProgram();
        shaderProgram.createVertexShader("src/shaders/vertex.glsl");
        shaderProgram.createFragmentShader("src/shaders/fragment.glsl");
        shaderProgram.link();
        shaderProgram.bind();
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        float b = (float) Math.sin(Math.sin(GLFW.glfwGetTime())) / 2.0f + 0.5f;
        int myColor = GL30.glGetUniformLocation( shaderProgram.getProgramId(),"myColor");
        GL30.glUniform3f( myColor, 0.0f, 0.0f, b);

        float[] M = new float[]{
                1.0f, 0.0f, 0.0f, 0.0f,
                0.0f, 1.0f, 0.0f, 0.0f,
                0.0f, 0.0f, 1.0f, 0.0f,
                0.0f, 0.0f, 0.0f, 1.0f,
        };

        int M_Matrix = GL30.glGetUniformLocation( shaderProgram.getProgramId(),"M");
        GL30.glUniformMatrix4fv( M_Matrix, false, M);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        GL30.glBindVertexArray(model.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        IntBuffer indices = storeDataInIntBuffer(model.getIndices());
        GL11.glDrawElements(GL11.GL_TRIANGLES,indices);
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);
        shaderProgram.unbind();
    }

    private IntBuffer storeDataInIntBuffer(int[] data) {
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

}
