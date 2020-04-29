package MainDisplay;

import Renderer.Loader;
import Renderer.Model;
import Renderer.Renderer;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;
import shaders.Shader;

import java.nio.IntBuffer;
import java.util.Objects;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class MainDisplay {
    private long window;

    public void run() throws Exception{
        init();
        loop();

        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
        glfwTerminate();
        Objects.requireNonNull(glfwSetErrorCallback(null)).free();
    }

    private void init() {
        GLFWErrorCallback.createPrint(System.err).set();                                       // Setup an error callback.

        if ( !glfwInit() )                                                                     // Initialize GLFW
            throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints();                                                               // Configure GLFW
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);                                             // the window will be resizable

        window = glfwCreateWindow(900, 600, "Tower Defence", NULL, NULL);    // Create the window
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        ////////////////////////////////////////////////////////////////////////////KEY_BINDING/////////////////////////
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
        });
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        // Get the thread stack and push a new frame
        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);
            glfwGetWindowSize(window, pWidth, pHeight);
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());                    // Get monitor resolution

            glfwSetWindowPos(
                    window,
                    (Objects.requireNonNull(vidmode).width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        }

        glfwMakeContextCurrent(window);                                               // Make the OpenGL context current
        glfwSwapInterval(1);                                                          // Enable v-sync
        glfwShowWindow(window);                                                       // Make the window visible
    }

    private void loop() throws Exception {
        GL.createCapabilities();                                                  // Needed for calling OpenGL functions

        float[] positions0 = new float[] {
                0.0f,  1.0f,  0.0f,
                0.5f,  -0.5f,  0.0f,
                -0.5f,  -0.5f,  0.0f,
        };

        float[] positions1 = new float[] {
                0.5f,  0.5f, 0.0f,
                0.5f, -0.5f, 0.0f,
                -0.5f, -0.5f, 0.0f,
                -0.5f,  0.5f, 0.0f
        };

        int[] indices0 = new int[] {
                0, 1, 2,
        };

        int[] indices1 = new int[] {
                0, 1, 3,
                1, 2, 3
        };

        Shader myShader = new Shader("src/shaders/vertex.glsl", "src/shaders/fragment.glsl");
        Loader myLoader = new Loader();
        Renderer myRenderer = new Renderer();

        int idx0 = myLoader.createVAO(positions0,indices0);
        int idx1 = myLoader.createVAO(positions1,indices1);
        Model model0 = new Model(myLoader.getVao(idx0), myLoader.getEboNum(idx0), myShader.getProgramId());
        Model model1 = new Model(myLoader.getVao(idx1), myLoader.getEboNum(idx1), myShader.getProgramId());

        while ( !glfwWindowShouldClose(window) ) {
            myRenderer.refreshScreen();

            model0.rotate(1,0,0);
            myRenderer.render(model0);

            model1.rotate(0,1,0);
            myRenderer.render(model1);

            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }

    public static void main(String[] args) throws Exception{
        new MainDisplay().run();
    }

}