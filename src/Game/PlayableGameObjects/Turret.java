package Game.PlayableGameObjects;

import Game.Game;
import Game.GameObject;
import org.joml.Vector2f;
import org.joml.Vector3f;

import javax.swing.*;
import java.util.Iterator;
import java.util.Map;

public class Turret {
    int myID, gun, platform, foundation;
    Vector3f position;

    public int getMyID(){
        return myID;
    }

    int lastEnemyID = 0;
    float deltaCumulative = 0;

    private float lerp(float a, float b, float f) { return a + f * (b - a); }

    public void move(float delta){
        boolean targetFound = false;
        Vector3f targetPosition = new Vector3f();
        for(Iterator<Map.Entry<Integer, Enemy>> it = Game.enemies.entrySet().iterator(); it.hasNext();){
            Enemy enemy = it.next().getValue();
            targetPosition = enemy.getPosition();
            if(targetPosition.distance(position) < 30f){
                deltaCumulative = lastEnemyID != enemy.getMyID() ? delta : deltaCumulative + delta > 1 ? 1 : deltaCumulative + delta;
                lastEnemyID = enemy.getMyID();
                targetFound = true;
                break;
            }
        }

        if(!targetFound){
            lastEnemyID = 0;
            return;
        }

        GameObject platformGO = Game.GameObjects.get(platform);

        Vector2f me = new Vector2f(this.position.z, this.position.x),
                tar = new Vector2f(targetPosition.z, targetPosition.x);
        tar.sub(me).normalize();
        platformGO.setRotation(new Vector3f(0,lerp(platformGO.getRotation().y,new Vector2f(1,0).angle(tar), deltaCumulative),0));
        platformGO.updateMF();
    }

    public Turret(int id, int gun, int platform, int foundation, Vector3f position){
        this.myID = id;
        this.gun = gun;
        this.platform = platform;
        this.foundation = foundation;
        this.position = position;
        Game.GameObjects.get(foundation).setScale(new Vector3f(2f,2f,2f));
        Game.GameObjects.get(gun).translate(new Vector3f(0,1.5f,-0.45f));
        Game.GameObjects.get(foundation).setTranslation(position);
        Game.GameObjects.get(foundation).updateMF();
    }
}
