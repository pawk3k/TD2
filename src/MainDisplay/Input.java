package MainDisplay;


import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.GLFWScrollCallback;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;

public class Input {

    private long window;
    private float x_of;
    private float z_of;
    private float l_x_of;
    private float l_y_of;

    public float getL_x_of() {
        return l_x_of;
    }

    public float getL_y_of() {
        return l_y_of;
    }

    public Input(long in_window){
        this.window = in_window;
        this.l_x_of = 0.0f;
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if(key == GLFW_KEY_D && action == GLFW_PRESS){
                this.l_x_of+=1.0f;
                System.out.println(l_x_of);
            }
            if(key == GLFW_KEY_A && action == GLFW_PRESS){
                this.l_x_of+=-1.f;
                System.out.println(l_x_of);
            }
            if(key == GLFW_KEY_W && action == GLFW_PRESS){
                this.l_y_of+=-1.f;
                System.out.println(l_x_of);
            }
            if(key == GLFW_KEY_S && action == GLFW_PRESS){
                this.l_y_of+=1.f;
                System.out.println(l_x_of);
            }
            if(key == GLFW_KEY_Z && action == GLFW_PRESS){
                z_of-=0.1;
            }
            if(action== GLFW_RELEASE){
//                this.x_of = 0.0f;
//                this.l_x_of = 0.f;
            }
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
        });
        final float[] mouseWheelVelocity = {0};

        glfwSetScrollCallback(window, (long win, double dx, double dy) ->{
            if(dy==-1.0f){
                this.x_of+=-1.0f;
            }if(dy==1.0f){
                this.x_of+=1.0f;
            }
            System.out.println(this.x_of);

        });
    }

    public float getX_of() {
        return x_of;
    }

    public float getZ_of() {
        return z_of;
    }
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




