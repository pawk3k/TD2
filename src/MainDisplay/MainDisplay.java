package MainDisplay;
import Game.Game;
import Game.GameObject;
import Game.GameController;
import Renderer.Renderer;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.glEnable;
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

        Renderer myRenderer = new Renderer();
        Input input = new Input(window);

        glEnable(GL_DEPTH_TEST);

        while (!glfwWindowShouldClose(window)) {
            myRenderer.refreshScreen();

            input.submitKeys();
            mainScene.update((float)(glfwGetTime()));

//            for (Map.Entry<Integer, GameObject> sceneObject : Game.GameObjects.entrySet()) {
//                myRenderer.render(sceneObject.getValue(), Game.camera.getV());
//            }
            for(Iterator<Map.Entry<Integer, GameObject>> it = Game.GameObjects.entrySet().iterator(); it.hasNext();){
                GameObject gameObject = it.next().getValue();
                if(GameController.removeListGameObjects.contains(gameObject.getId())) it.remove();
                else myRenderer.render(gameObject, Game.camera.getV());
            }
            GameController.removeListGameObjects.clear();

            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }

    public static void main(String[] args) throws Exception{
        new MainDisplay().run();
    }
}