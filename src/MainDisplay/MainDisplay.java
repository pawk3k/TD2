package MainDisplay;

import Renderer.Loader;
import Renderer.Model;
import Renderer.Renderer;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;
import java.nio.IntBuffer;
import java.util.Objects;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class MainDisplay {
    private long window;

    public void run() throws Exception{
        //System.out.println("Hello LWJGL " + Version.getVersion() + "!");
        //System.out.println("OS name " + System.getProperty("os.name"));
        //System.out.println("OS version " + System.getProperty("os.version"));
        //System.out.println("OpenGL version: " + GL11.glGetString(GL11.GL_VERSION));

        init();
        loop();

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
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

        float[] positions = new float[] {
                0.0f,  0.5f,  0.0f,
                0.5f,  -0.5f,  0.0f,
                -0.5f,  -0.5f,  0.0f,
        };

        float[] colours = new float[]{
                0.0f, 0.0f, 0.0f,
                0.0f, 0.0f, 0.0f,
                0.0f, 0.0f, 0.0f,
        };

        int[] indices = new int[] {
                0, 1, 2,
        };
        
        Loader loader = new Loader();
        Renderer renderer = new Renderer();
        Model model = loader.loadToVAO(positions,colours,indices);

        while ( !glfwWindowShouldClose(window) ) {
            renderer.refresh();

            renderer.render(model);

            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }

    public static void main(String[] args) throws Exception{
        new MainDisplay().run();
    }

}