package Game;

import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import java.util.ArrayList;
import java.util.List;

public class GameObjectPrefab {
    protected int id;
    protected int parent;
    private List<Integer> children = new ArrayList<>();
    protected Matrix4f M = new Matrix4f().identity();

    protected Vector3f scale = new Vector3f(1.0f, 1.0f, 1.0f);
    protected Vector3f rotation = new Vector3f();
    protected Vector3f translation = new Vector3f();

    public int getId(){
        return id;
    }

    public List<Integer> getChildren(){
        return children;
    }

    public Vector3f getScale(){
        return this.scale;
    }

    public Vector3f getRotation(){
        return this.rotation;
    }

    public Vector3f getTranslation(){
        return this.translation;
    }

    public int getParent(){
        return parent;
    }

    public void addChild(int child){
        children.add(child);
    }

    public void removeChild(int child){
        children.remove(Integer.valueOf(child));
    }

    /**
     * Move position
     * @param translation Vector3f
     */
    public void translate(Vector3f translation){
        this.translation.add(translation);
    }
    /**
     * Set position
     * @param translation Vector3f
     */
    public void setTranslation(Vector3f translation){
        this.translation = translation;
    }

    /**
     * Rotate
     * @param rotation Vector3f
     */
    public void rotate(Vector3f rotation){
        this.rotation.add(rotation);
    }
    /**
     * Set rotation
     * @param rotation Vector3f
     */
    public void setRotation(Vector3f rotation){
        this.rotation = rotation;
    }

    /**
     * Scale
     * @param scale Vector3f
     */
    public void scale(Vector3f scale){
        this.scale.mul(scale);
    }
    /**
     * Set scale
     * @param scale Vector3f
     */
    public void setScale(Vector3f scale){
        this.scale = scale;
    }

    /**
     * update matrix for full object (children included)
     */
    public void updateMF(){
        updateM();
        for(int child : children){
            Game.GameObjects.get(child).updateMF();
        }
    }
    /**
     * update matrix for this object only (children excluded)
     */
    public void updateM() {
        if(parent != 0)
            this.M = new Matrix4f(Game.GameObjects.get(parent).getM()).
                    translate(translation).
                    rotateX(rotation.x).
                    rotateY(rotation.y).
                    rotateZ(rotation.z).
                    scale(scale);
        else
            this.M = new Matrix4f().identity().
                    translate(translation).
                    rotateX(rotation.x).
                    rotateY(rotation.y).
                    rotateZ(rotation.z).
                    scale(scale);
    }

    public Matrix4f getM(){
        return M;
    }

    /**
     * @param id ID of an object (non zero)
     * @param parent parent id (0 if no parent)
     */
    public GameObjectPrefab(int id, int parent) throws Exception{
        if(id == 0) throw new Exception("Given 0 id to an Object");
        if(parent != 0) Game.GameObjects.get(parent).addChild(id);
        this.id = id;
        this.parent = parent;
    }
}
