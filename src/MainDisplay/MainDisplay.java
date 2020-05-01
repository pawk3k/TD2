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
        GLFWErrorCallback.createPrint(System.err).set();

        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);

        window = glfwCreateWindow(900, 600, "Tower Defence", NULL, NULL);
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
        TextureClass wall = new TextureClass("res/red_brick.png");
        wall.create();

        ObjModel objModel = new ObjModel("res/firewall.obj");
        objModel.read_object_file();

        Shader myShader = new Shader("src/shaders/vertex.glsl", "src/shaders/fragment.glsl");
        Loader myLoader = new Loader();
        Camera camera = new Camera();

        Renderer myRenderer = new Renderer();
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