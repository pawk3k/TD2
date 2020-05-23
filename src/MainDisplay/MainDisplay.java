package MainDisplay;

import Game.Game;
import Game.GameObject;
import Game.GameController;
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
import java.util.Iterator;
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

        glEnable(GL11.GL_BLEND);
        glEnable(GL11.GL_DEPTH_TEST);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        while (!glfwWindowShouldClose(window)) {
            myRenderer.refreshScreen();
            GL11.glViewport(0, 0, getWidth(), getHeight());
            input.submitKeys();
            mainScene.update((float)(glfwGetTime()));

            for(Iterator<Map.Entry<Integer, GameObject>> it = Game.GameObjects.entrySet().iterator(); it.hasNext();){
                GameObject gameObject = it.next().getValue();
                if(GameController.removeListGameObjects.contains(gameObject.getId())) it.remove();
                else myRenderer.render(gameObject, Game.camera.getV());
            }
            GameController.removeListGameObjects.clear();

            for(Iterator<Map.Entry<Integer, GameObject>> it = Game.GameHudObjects.entrySet().iterator(); it.hasNext();){
                GameObject gameHUDObject = it.next().getValue();
                myRenderer.renderHud(gameHUDObject, getWidth(), getHeight());
                if(GameController.removeListHUD.contains(gameHUDObject.getId())) it.remove();
                else myRenderer.render(gameHUDObject, Game.camera.getV());
            }

            GameController.removeListHUD.clear();

            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }

    public static void main(String[] args) throws Exception{
        new MainDisplay().run();
    }
}