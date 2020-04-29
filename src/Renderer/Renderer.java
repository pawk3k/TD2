package Renderer;

import Renderer.Model;
import Window.Window;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import Model.*;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import shaders.ShaderProgram;

import javax.sound.midi.Soundbank;
import java.nio.IntBuffer;
import java.sql.SQLOutput;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;

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
     *
     */
    public void render(Model model, Matrix4f projection, Vector3f cameraPos, TextureClass wall,boolean choose_draw) throws Exception{


        ShaderProgram shaderProgram = new ShaderProgram();
        shaderProgram.createVertexShader("src/shaders/vertex.glsl");
        shaderProgram.createFragmentShader("src/shaders/fragment.glsl");
        shaderProgram.link();
        shaderProgram.bind();
        //camera
        Matrix4f viewMatrix = new Matrix4f().identity().lookAt(0.0f, cameraPos.y,-50.0f,
                0.0f, 0.0f, 0.0f,
                0.0f, 1.0f, 0.0f);
        viewMatrix.rotate((float)Math.toRadians(cameraPos.x), new Vector3f(1, 0, 0))
                .rotate((float)Math.toRadians(cameraPos.x), new Vector3f(0, 1, 0));

        Matrix4f world = new Matrix4f().identity();
        GL30.glBindVertexArray(model.getVaoID());
        GL20.glEnableVertexAttribArray(0);
//        GL20.glEnableVertexAttribArray(1);
        IntBuffer indecies = storeDataInIntBuffer(model.getIndecies());

        shaderProgram.createUniform("worldMatrix");
        shaderProgram.setUniform("worldMatrix",world);
        shaderProgram.createUniform("projectionMatrix");
        shaderProgram.setUniform("projectionMatrix",projection);
        shaderProgram.createUniform("viewMatrix");
        shaderProgram.setUniform("viewMatrix",viewMatrix);

        GL13.glActiveTexture(GL13.GL_TEXTURE0);

        GL11.glBindTexture(GL11.GL_TEXTURE_2D,wall.getTextureID());

        if(choose_draw){
//            GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL_UNSIGNED_INT, 0);
            GL11.glDrawElements(GL11.GL_TRIANGLES,indecies);

        }else{
            GL11.glDrawElements(GL11.GL_TRIANGLES, 36, GL_UNSIGNED_INT, 0);

        }



        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D,0);

        shaderProgram.unbind();

    }



    private IntBuffer storeDataInIntBuffer(int[] data) {
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

}
