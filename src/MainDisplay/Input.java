package MainDisplay;


import Game.Game;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.GLFWScrollCallback;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;

public class Input {
    private long window;
    private boolean hold_w,hold_a,hold_s,hold_d;

    public Input(long in_window) {
        this.window = in_window;

        glfwSetScrollCallback(window, (window, xoffset, yoffset) -> {
            if(yoffset == 1.0f) Game.Camera.zoom(true);
            else Game.Camera.zoom(false);
        });

        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {

            if (action == GLFW_PRESS) {
                if (key == GLFW_KEY_W) hold_w = true;
                if (key == GLFW_KEY_A) hold_a = true;
                if (key == GLFW_KEY_S) hold_s = true;
                if (key == GLFW_KEY_D) hold_d = true;
            }

            if (action == GLFW_RELEASE) {
                if (key == GLFW_KEY_W) hold_w = false;
                if (key == GLFW_KEY_A) hold_a = false;
                if (key == GLFW_KEY_S) hold_s = false;
                if (key == GLFW_KEY_D) hold_d = false;
            }

            if (key == GLFW_KEY_ESCAPE) glfwSetWindowShouldClose(window, true);

            Vector3f translate = new Vector3f();

            if (hold_w) translate.add(new Vector3f(-1.0f, 0, 0));
            if (hold_a) translate.add(new Vector3f(0, 0, 1.0f));
            if (hold_s) translate.add(new Vector3f(1.0f, 0, 0));
            if (hold_d) translate.add(new Vector3f(0, 0, -1.0f));

            Game.Camera.translate(translate.mul(0.5f));
            Game.Camera.updateV();
        });
    }
}