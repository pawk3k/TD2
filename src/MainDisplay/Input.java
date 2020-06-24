package MainDisplay;

import Game.Game;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class Input {
    private double mouseX,mouseY;
    private boolean hold_w;
    private boolean hold_a;
    private boolean hold_s;
    private boolean hold_d;
    private boolean anyKeyPressed;
    private boolean isMouseLPressed;

    public void setMouseLPressed(boolean mouseLPressed) {
        isMouseLPressed = mouseLPressed;
    }

    public boolean isMouseLPressed() {
        return isMouseLPressed;
    }

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

    public double getMouseX() {
        return mouseX;
    }

    public double getMouseY() {
        return mouseY;
    }

    public Input(long in_window) {

        glfwSetScrollCallback(in_window, (window, xOffset, yOffset) -> {
            if(yOffset == 1.0f) Game.camera.zoom(true);
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

        glfwSetCursorPosCallback(in_window,((window1, xPos, yPos) -> {
            mouseX = xPos;
            mouseY = yPos;
        }));

        glfwSetMouseButtonCallback(in_window,((window1, button, action, mods) -> {
            if (button == GLFW_MOUSE_BUTTON_LEFT && action == GLFW_PRESS){
                isMouseLPressed = true;
            }
            if (button == GLFW_MOUSE_BUTTON_LEFT && action == GLFW_RELEASE){
                isMouseLPressed = false;
            }
        }));
    }
}