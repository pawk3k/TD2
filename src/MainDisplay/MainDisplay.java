package MainDisplay;

import Camera.Camera;
import Renderer.Loader;
import Renderer.Model;
import Renderer.Renderer;
import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;
import Model.TextureClass;
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;
import shaders.Shader;
import Model.Kub;
import java.nio.IntBuffer;
import java.util.Objects;
import Model.ObjModel;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class MainDisplay {
    private long window;
    private static int width  = 900;
    private static int height = 600;
    Matrix4f projectionMatrix;
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
        GL.createCapabilities();// Needed for calling OpenGL functions
//        glDepthFunc(GL_LEQUAL);
//
//        GL11.glEnable(GL_DEPTH_TEST);
//        glEnable(GL_DEPTH_TEST); //Włącz test głębokości na pikselach
        TextureClass wall = new TextureClass("res/red_brick.png");
        wall.create();
        float[] verticles0 = new float[]{
                0.5f, -0.5f, 0.0f, 1.0f, 0.0f, 0.0f,   // bottom right
                -0.5f, -0.5f, 0.0f, 0.0f, 1.0f, 0.0f,   // bottom left
                0.0f, 0.5f, 0.0f, 0.0f, 0.0f, 1.0f    // top
        };

        int[] indices0 = new int[]{
                0, 1, 2,
        };

        ObjModel objModel = new ObjModel("res/firewall.obj");
//        TextureClass textureClass = new TextureClass("");

        objModel.read_object_file();
        Shader myShader = new Shader("src/shaders/vertex.glsl", "src/shaders/fragment.glsl");
        Loader myLoader = new Loader();
        Camera camera = new Camera();

        Renderer myRenderer = new Renderer();
        Kub kub = new Kub();
//        int idx0 = myLoader.createVAO(verticles0, indices0);
//        int idx1 = myLoader.createVAO(kub.getPositions(), kub.getIndecies());
        int idx2 = myLoader.createVAO(objModel.getVerticesBuffer(),objModel.getIndeciesBuffer(),objModel.getTextureBuffer());
//        Model model0 = new Model(myLoader.getVao(idx0), myLoader.getEboNum(idx0), myShader.getProgramId());
//        Model model1 = new Model(myLoader.getVao(idx1), myLoader.getEboNum(idx1), myShader.getProgramId());
        Model model2 = new Model(myLoader.getVao(idx2), myLoader.getEboNum(idx2), myShader.getProgramId());







        while (!glfwWindowShouldClose(window)) {
            myRenderer.refreshScreen();


            myRenderer.render(model2,wall);

            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }

    public static void main(String[] args) throws Exception{
        new MainDisplay().run();
    }

}