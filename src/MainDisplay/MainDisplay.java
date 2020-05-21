package MainDisplay;

import Game.Game;
import Game.GameObject;
import Camera.Camera;
import Renderer.Renderer;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;
import shaders.Shader;
import java.awt.*;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.nio.IntBuffer;
import java.util.Map;
import java.util.Objects;
import java.util.Vector;

import SomeMath.Bezier;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class  MainDisplay {
    private long window;
    private int width;
    private int height;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void run() throws Exception{
        init();
        loop();

        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
        glfwTerminate();
        Objects.requireNonNull(glfwSetErrorCallback(null)).free();
    }

    private void init() {

        GLFWErrorCallback.createPrint(System.err).set();

        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
        width = 1300;
        height = 768;
        window = glfwCreateWindow(getWidth(), getHeight(), "Tower Defence", NULL, NULL);

        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);
            glfwGetWindowSize(window, pWidth, pHeight);
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            glfwSetWindowPos(
                    window,
                    (Objects.requireNonNull(vidmode).width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        }

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);
        glfwShowWindow(window);
    }

    private void loop() throws Exception {
        GL.createCapabilities();

        Game mainScene = new Game();
        mainScene.init();

        Renderer myRenderer = new Renderer();
        Input input = new Input(window);

        long start  = System.currentTimeMillis();
        long end;
        int w = 250;
        int h = 200;
        int x = 0;
        int y = 0;
        glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        while (!glfwWindowShouldClose(window)) {
            w =400;
            h  =250;
            x=400;
            end = System.currentTimeMillis();
            myRenderer.refreshScreen();
            GL11.glViewport(0, 0, getWidth(), getHeight());
            input.submitKeys();
            mainScene.update((float)(glfwGetTime()));
//

            for (Map.Entry<Integer, GameObject> sceneObject : Game.GameObjects.entrySet()) {
                myRenderer.render(sceneObject.getValue(), Game.camera.getV());
            }
//            glDepthMask(GL11.GL_FALSE);  // disable writes to Z-Buffer
//            GL11.glDisable(GL_DEPTH_TEST);  // disable depth-testing
//            GL11.glDisable(GL11.GL_CULL_FACE);

//            GL11.glMatrixMode(GL11.GL_PROJECTION);

            glEnable(GL_TEXTURE_2D);
            for (Map.Entry<Integer, GameObject> sceneObject : Game.GameHudObjects.entrySet()) {
//                glRasterPos2i(600,1200);
                myRenderer.renderHud1(sceneObject.getValue(), 1300,768);
                x+=100;

            }

            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }

    public static void main(String[] args) throws Exception{
        new MainDisplay().run();
    }
}