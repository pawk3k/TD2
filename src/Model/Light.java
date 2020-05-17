package Model;

import org.joml.Vector3f;
import org.joml.Vector4f;

public class Light {
    private Vector4f position;

    public Vector4f getPosition() {
        return position;
    }

    public void setPosition(Vector4f position) {
        this.position = position;
    }

    public Vector4f getColor() {
        return color;
    }

    public void setColor(Vector4f color) {
        this.color = color;
    }

    public float getIntensity() {
        return intensity;
    }

    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }

    private Vector4f color;
    private float intensity;

    public Light(){
        this.color = new Vector4f(1,1,1,1);
        this.intensity = 1;
        this.position = new Vector4f(0,-6,-5,1);
    }
}
