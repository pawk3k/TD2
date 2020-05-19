package MainDisplay;

import Game.Game;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class Input {
    private boolean hold_w,hold_a,hold_s,hold_d,anyKeyPressed;

    public void submitKeys(){
        if(!anyKeyPressed) return;

        Vector3f translate = new Vector3f();

        if (hold_w) translate.add(new Vector3f(-2.0f, 0, 0));
        if (hold_a) translate.add(new Vector3f(0, 0, 2.0f));
        if (hold_s) translate.add(new Vector3f(2.0f, 0, 0));
        if (hold_d) translate.add(new Vector3f(0, 0, -2.0f));

        Game.camera.translate(translate.mul(0.5f));
        Game.camera.updateV();
    }

    public Input(long in_window) {

        glfwSetScrollCallback(in_window, (window, xoffset, yoffset) -> {
            if(yoffset == 1.0f) Game.camera.zoom(true);
            else Game.camera.zoom(false);
        });

        glfwSetKeyCallback(in_window, (window, key, scancode, action, mods) -> {

            if (action == GLFW_PRESS) {
                if (key == GLFW_KEY_W) hold_w = true;
                if (key == GLFW_KEY_A) hold_a = true;
                if (key == GLFW_KEY_S) hold_s = true;
                if (key == GLFW_KEY_D) hold_d = true;
                anyKeyPressed = true;
            }

            if (action == GLFW_RELEASE) {
                if (key == GLFW_KEY_W) hold_w = false;
                if (key == GLFW_KEY_A) hold_a = false;
                if (key == GLFW_KEY_S) hold_s = false;
                if (key == GLFW_KEY_D) hold_d = false;
                anyKeyPressed = hold_w || hold_a || hold_s || hold_d;
            }

            if (key == GLFW_KEY_ESCAPE) glfwSetWindowShouldClose(window, true);
        });
    }
}