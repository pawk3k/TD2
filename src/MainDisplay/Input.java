package MainDisplay;


import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;

public class Input {

    public long window;

    public float getX_of() {
        return x_of;
    }

    public float getZ_of() {
        return z_of;
    }

    public float x_of;
    public float z_of;
    public Input(long widow){
        this.window = widow;

        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if(key == GLFW_KEY_D && action == GLFW_PRESS){
                this.x_of+=0.1f;
            }
            if(key == GLFW_KEY_A && action == GLFW_PRESS){
                this.x_of-=1.0f;
            }
            if(key == GLFW_KEY_Z && action == GLFW_PRESS){
                z_of-=0.1;
            }
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
        });

    }
}
