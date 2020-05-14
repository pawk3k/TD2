package Game;

import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import java.util.ArrayList;
import java.util.List;

public class GameObjectPrefab {
    protected int id;
    protected int parent;
    protected List<Integer> children = new ArrayList<>();
    protected Matrix4f M = new Matrix4f().identity();

    protected Vector3f localScale = new Vector3f(1.0f, 1.0f, 1.0f);
    protected Vector3f localRotation = new Vector3f();
    protected Vector3f localTranslation = new Vector3f();

    protected Vector3f globalScale = new Vector3f(1.0f, 1.0f, 1.0f);
    protected Vector3f globalRotation = new Vector3f();
    protected Vector3f globalTranslation = new Vector3f();

    public int getId(){
        return id;
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
     * Refresh all children' positions (call on child)
     * @param parentTranslation parent global translation Vector3f
     */
    public void refreshGT(Vector3f parentTranslation){
        this.globalTranslation = parentTranslation.add(this.localTranslation);
        for(int child : children){
            Game.GameObjects.get(child).refreshGT(new Vector3f(this.globalTranslation));
        }
    }
    /**
     * Move global position
     * @param translation Vector3f
     */
    public void globalTranslate(Vector3f translation){
        this.globalTranslation.add(translation);

        if(parent != 0)
            this.localTranslation.sub(this.globalTranslation, Game.GameObjects.get(parent).globalTranslation);
        else
            this.localTranslation = this.globalTranslation;

        for(int child : children){
            Game.GameObjects.get(child).refreshGT(this.globalTranslation);
        }
    }
    /**
     * Set global position
     * @param translation Vector3f
     */
    public void globalSetTranslation(Vector3f translation){
        this.globalTranslation = translation;

        if(parent != 0)
            this.localTranslation = new Vector3f(this.globalTranslation).sub(Game.GameObjects.get(parent).globalTranslation);
        else
            this.localTranslation = this.globalTranslation;

        for(int child : children){
            Game.GameObjects.get(child).refreshGT(new Vector3f(this.globalTranslation));
        }
    }
    /**
     * Move local position
     * @param translation Vector3f
     */
    public void localTranslate(Vector3f translation){
        this.localTranslation.add(translation);

        if(parent != 0)
            this.globalTranslation.add(Game.GameObjects.get(parent).globalTranslation, this.localTranslation);
        else
            this.globalTranslation = this.localTranslation;

        for(int child : children){
            Game.GameObjects.get(child).refreshGT(this.globalTranslation);
        }
    }
    /**
     * Set local position
     * @param translation Vector3f
     */
    public void localSetTranslation(Vector3f translation){
        this.localTranslation = translation;
        if(parent != 0)
            this.globalTranslation = translation.add(Game.GameObjects.get(parent).globalTranslation);
        else
            this.globalTranslation = this.localTranslation;
        for(int child : children){
            Game.GameObjects.get(child).refreshGT(this.globalTranslation);
        }
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
        this.M = new Matrix4f().identity().
                translate(globalTranslation).
                rotateX(Math.toRadians(globalRotation.x)).
                rotateY(Math.toRadians(globalRotation.y)).
                rotateZ(Math.toRadians(globalRotation.z)).
                scale(globalScale);
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
