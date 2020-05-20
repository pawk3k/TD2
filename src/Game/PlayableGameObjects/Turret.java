package Game.PlayableGameObjects;

import Game.Game;
import org.joml.Vector2f;
import org.joml.Vector3f;
import java.util.Iterator;
import java.util.Map;

public class Turret {
    int myID, gun, platform, foundation;
    Vector3f position;

    public int getMyID(){
        return myID;
    }

    public void move(float delta){
        boolean targetFound = false;
        Vector3f targetPosition = new Vector3f();
        for(Iterator<Map.Entry<Integer, Enemy>> it = Game.enemies.entrySet().iterator(); it.hasNext();){
            Enemy enemy = it.next().getValue();
            targetPosition = enemy.getPosition();
            if(targetPosition.distance(position) < 100f){
                targetFound = true;
                break;
            }
        }
        if(!targetFound) return;

        //TODO: Дописати turret look at
    }

    public Turret(int id, int gun, int platform, int foundation, Vector3f position){
        this.myID = id;
        this.gun = gun;
        this.platform = platform;
        this.foundation = foundation;
        this.position = position;
        Game.GameObjects.get(foundation).setScale(new Vector3f(3f,3f,3f));
        Game.GameObjects.get(gun).translate(new Vector3f(0,1.5f,-0.45f));


        Game.GameObjects.get(foundation).setTranslation(position);
        Game.GameObjects.get(foundation).updateMF();
    }
}
