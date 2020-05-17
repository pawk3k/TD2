package Camera;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {
    private Vector3f position = new Vector3f(5f,10f ,0);
    private Vector3f maxPoint = new Vector3f(new Vector3f(50, 100, 0));
    private Vector3f lookPoint = new Vector3f();
    private Matrix4f V = new Matrix4f();
    private float lookDistance = 0.1f;

    public Matrix4f getV() {
        return V;
    }

    public void translate(Vector3f position) {
        this.maxPoint.add(position);
        this.position.add(position);
        this.lookPoint.add(position);
    }

    public void zoom(boolean in){
        if(this.lookDistance <= 0.2f && in || this.lookDistance >= 0.7f && !in) return;
        this.lookDistance += in ? -0.1f : 0.1f;
        this.position = new Vector3f(this.lookPoint).lerp(maxPoint, lookDistance);
        updateV();
    }

    public void updateV(){
        V = new Matrix4f().identity().lookAt(position, lookPoint, new Vector3f(0,1,0));
    }

    public Camera(){
        updateV();
    }
}
