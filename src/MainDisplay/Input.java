package MainDisplay;


import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;

public class Input {

    private long window;
    private float x_of;
    private float z_of;

    public Input(long in_window){
        this.window = in_window;

        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if(key == GLFW_KEY_D && action == GLFW_PRESS){
                this.x_of=-1.0f;
            }
            if(key == GLFW_KEY_A && action == GLFW_PRESS){
                this.x_of=1.f;
            }
            if(key == GLFW_KEY_Z && action == GLFW_PRESS){
                z_of-=0.1;
            }
            if(action== GLFW_RELEASE){
                this.x_of = 0.0f;
            }
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
        });
    }

    // @TODOO Кароче треба доробити камеру на скролл шоб віддалялась то без траблів можна зробити як показано ось тут

//    glm::mat4 V = glm::lookAt(glm::vec3(view_x, view_y, view_z), glm::vec3(0.0f, 0.0f, 0.0f), glm::vec3(0.0f, 1.0f, 0.0f)); //Wylicz macierz widoku
//void scroll_callback(GLFWwindow* window, double xOffset, double yOffset){
//    view_x = view_x + xOffset;
//    view_y = view_y + yOffset;
//    printf("%lf : %lf\n",view_x,view_z);
//
////    double view_z = -5.0f;
//
//}


    public float getX_of() {
        return x_of;
    }

    public float getZ_of() {
        return z_of;
    }
}
