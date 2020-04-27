package Renderer;

import Renderer.Model;
import Window.Window;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import shaders.ShaderProgram;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;

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
    public void prepare() {
        GL11.glClearColor(1, 0, 0, 1);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
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
     * @param window
     */
    public void render(Model model, Matrix4f projection, long window,float x_of,float z_of) throws Exception{


        ShaderProgram shaderProgram = new ShaderProgram();
        shaderProgram.createVertexShader("src/shaders/vertex.glsl");
        shaderProgram.createFragmentShader("src/shaders/fragment.glsl");
        shaderProgram.link();
        shaderProgram.bind();

        GL30.glBindVertexArray(model.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        IntBuffer indecies = storeDataInIntBuffer(model.getIndecies());

        Matrix4f world = new Matrix4f().identity().translate(x_of,0.0f,z_of-0.5f).scale(0.5f);
        shaderProgram.createUniform("worldMatrix");
        shaderProgram.setUniform("worldMatrix",world);
        shaderProgram.createUniform("projectionMatrix");
        shaderProgram.setUniform("projectionMatrix",projection);
        shaderProgram.createUniform("viewMatrix");
        shaderProgram.setUniform("viewMatrix",world);
        GL11.glDrawElements(GL11.GL_TRIANGLES,indecies);
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
