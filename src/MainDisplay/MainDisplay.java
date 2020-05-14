package MainDisplay;
import Model.Light;
import Camera.Camera;
import Model.Kub;
import Model.ObjModel;
import Renderer.Loader;
import Renderer.Model;
import Renderer.Renderer;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;
import shaders.Shader;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Objects;

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
        GL.createCapabilities();// Needed for calling OpenGL functions

        ObjModel objModel = new ObjModel("res/tower.obj","res/tower.png");
        ObjModel objModel1 = new ObjModel("res/firewall2.obj","res/red_brick.png");
        objModel.read_object_file();
        objModel1.read_object_file();

        Shader myShader = new Shader("src/shaders/vertex.glsl", "src/shaders/fragment.glsl");
        Loader myLoader = new Loader();
        Camera camera = new Camera();

        Renderer myRenderer = new Renderer();
        Kub kub = new Kub();
        Input input = new Input(window);
        int idx2 = myLoader.createVAO(objModel.getVerticesBuffer(),objModel.getIndeciesBuffer(),objModel.getTextureBuffer(),objModel.getNormalsBuffer());
        int idx3 = myLoader.createVAO(objModel1.getVerticesBuffer(),objModel1.getIndeciesBuffer(),objModel1.getTextureBuffer(),objModel1.getNormalsBuffer());
        Model model2 = new Model(myLoader.getVao(idx2), myLoader.getEboNum(idx2), myShader.getProgramId());
        Model model3 = new Model(myLoader.getVao(idx3), myLoader.getEboNum(idx3), myShader.getProgramId());

        Light light = new Light();
        Light global_sun = new Light();
        light.setColor(new Vector4f(1,1,1,1));
        global_sun.setPosition( new Vector4f(0,-6,-5,0));

        light.setPosition(new Vector4f(0,-6,-5,1)); ///this.position = new Vector4f(0,-6,-5,1);
//        light.setIntensity(5.f);

        long startTime = System.currentTimeMillis();
        long endTime;
        glEnable(GL_DEPTH_TEST);



        model2.setPosition(0.f,-3.f,0.f);
        model3.setPosition(-5.f,-3.f,0.f);
        ArrayList<Model> models = new  ArrayList<>();
        models.add(model2);
        models.add(model3);



        while (!glfwWindowShouldClose(window)) {
            endTime = System.currentTimeMillis();

            boolean secs3 = (endTime - startTime) > 3000 && (endTime - startTime) < 3019;


            myRenderer.refreshScreen();
//            model3.translate(0.f,0.0f,-.1f);

            light.setPosition(new Vector4f(0 + input.getL_x_of(),2 + -input.getL_y_of(),-5 + input.getL_z_of(),1)); //this.position = new Vector4f(0,-6,-5,1);
            camera.getViewMatrix().rotateX(1);
            camera.setPosition(new Vector3f(0.f,40.f,15.f +input.getX_of()));
//            int i = 0;
//            for( Model e : models){
//                e.translate(0.f,0.f,-0.02f);
                myRenderer.render(model2,objModel.getTextureId(), camera.getViewMatrix(),light,global_sun,0);
                myRenderer.render(model3,objModel1.getTextureId(), camera.getViewMatrix(),light,global_sun,1);

//            }
//           matrix4f.rotateY(0.01f);
//            System.out.println(endTime-startTime);
            if(secs3)
            {
                System.out.println("5 seconds elapsed");

                Model new_model = new Model(myLoader.getVao(idx2), myLoader.getEboNum(idx2), myShader.getProgramId());
                new_model.setPosition(-5.f,-3.f,0.f);
                new_model.setScale(0.5f,0.5f,0.5f);
//                models.add(new_model);
                startTime = System.currentTimeMillis();
//                startTime = 0;
//                myRenderer.render(model3,objModel.getTextureId(), camera.getViewMatrix(),light,global_sun);

            }
            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }

    public static void main(String[] args) throws Exception{
        new MainDisplay().run();
    }




}