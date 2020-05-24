package Camera;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {
    public Vector3f getPosition() {
        return position;
    }

    private Vector3f position = new Vector3f(5f,10f ,0);
    private Vector3f maxPoint = new Vector3f(new Vector3f(50, 100, 0));
    private Vector3f lookPoint = new Vector3f(0.f,0.f,0.f);
    private Matrix4f V = new Matrix4f();
    private float lookDistance = 0.5f;

    public Matrix4f getV() {
        return V;
    }

    public void translate(Vector3f position) {
        float diffX = lookPoint.x + position.x;
        float diffZ = lookPoint.z + position.z;
        diffX = diffX >= 100 ? 0 : diffX <= -100 ? 0 : position.x;
        diffZ = diffZ >= 100 ? 0 : diffZ <= -100 ? 0 : position.z;

        position = new Vector3f(diffX, position.y, diffZ);

        this.maxPoint.add(position);
        this.position.add(position);
        this.lookPoint.add(position);
    }

    public void zoom(boolean in){
        if(this.lookDistance <= 0.3f && in || this.lookDistance >= 0.7f && !in) return;
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
