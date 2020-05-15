package MainDisplay;
import Model.Light;
import Camera.Camera;
import Game.Game;
import Game.GameObject;
import Game.GameController;
import Renderer.Renderer;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
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
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class MainDisplay {
    private long window;
    private static int width  = 900;
    private static int height = 600;

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

        window = glfwCreateWindow(1300, 768, "Tower Defence", NULL, NULL);
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

        Camera camera = new Camera();

        Renderer myRenderer = new Renderer();
        Input input = new Input(window);

        glEnable(GL_DEPTH_TEST);


        camera.setPosition(new Vector3f(-5.f ,0.f ,15.f));


        while (!glfwWindowShouldClose(window)) {
            myRenderer.refreshScreen();
            camera.setPosition(new Vector3f(0.f,10.f+input.getX_of(),-15.f +input.getX_of() ));

            mainScene.update(glfwGetTime());

            for (Map.Entry<Integer, GameObject> sceneObject : Game.GameObjects.entrySet()) {
                myRenderer.render(sceneObject.getValue(), camera.getViewMatrix());
            }

            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }

    public static void main(String[] args) throws Exception{
        new MainDisplay().run();
    }
}