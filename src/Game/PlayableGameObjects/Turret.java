package Game.PlayableGameObjects;

import Game.Game;
import Game.GameController;
import Game.GameObject;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.*;

public class Turret {
    private int myID, gun, platform, foundation, bulletModelID;
    private int maxDamage;
    private ArrayList<Bullet> myBullets = new ArrayList<>();
    Vector3f position;

    public int getMyID(){
        return myID;
    }
    int lastEnemyID = 0;
    float deltaCumulative = 0,
            fireDelay = 0;

    public void move(float delta) throws Exception {
        boolean targetFound = false;
        Vector3f targetPosition = new Vector3f();
        Enemy targetEnemy = null;

        for(Iterator<Map.Entry<Integer, Enemy>> it = Game.enemies.entrySet().iterator(); it.hasNext();){
            targetEnemy = it.next().getValue();
            targetPosition = targetEnemy.getPosition();
            if(targetPosition.distance(position) < 30f){
                deltaCumulative = lastEnemyID != targetEnemy.getMyID() ? delta : deltaCumulative + delta > 1 ? 1 : deltaCumulative + delta;
                lastEnemyID = targetEnemy.getMyID();
                targetFound = true;
                break;
            }
        }

        for(ListIterator<Bullet> bulletIt = myBullets.listIterator(); bulletIt.hasNext(); ){
            Bullet bullet = bulletIt.next();
            if(bullet.deleted) bulletIt.remove();
            else bullet.move(delta*5);
        }

        if(!targetFound){
            lastEnemyID = 0;
            return;
        }

        GameObject platformGO = Game.GameObjects.get(platform);
        float myRotation = platformGO.getRotation().y, destRotation;

        Vector2f me = new Vector2f(this.position.z, this.position.x),
                tar = new Vector2f(targetPosition.z, targetPosition.x);
        tar.sub(me).normalize();
        destRotation = new Vector2f(1,0).angle(tar);
        platformGO.setRotation(new Vector3f(0, GameController.radianInterpolation(myRotation,destRotation,deltaCumulative),0));
        platformGO.updateMF();

        fireDelay += delta;
        Vector3f gunTip = new Vector3f();
        new Matrix4f(Game.GameObjects.get(gun).getM()).translate(new Vector3f(0,0.7f,2.3f)).getTranslation(gunTip);
        if(deltaCumulative > 0.3 && fireDelay >= 0.15){
            fireDelay = 0;
            myBullets.add(new Bullet(new Vector3f(gunTip), targetPosition, bulletModelID, targetEnemy.getMyID(), new Random().nextInt(maxDamage)));
        }
    }

    public void destroy(){
        for(ListIterator<Bullet> bulletIt = myBullets.listIterator(); bulletIt.hasNext(); ){
            bulletIt.next().move(1.0f);
            bulletIt.remove();
        }
        GameController.removeGOTree(foundation);
        GameController.removeListTurrets.add(myID);
    }

    public Turret(int id, int gun, int platform, int foundation, Vector3f position, int bulletModelID, int dmg) {
        this.maxDamage = dmg;
        this.myID = id;
        this.gun = gun;
        this.platform = platform;
        this.foundation = foundation;
        this.position = position;
        this.bulletModelID = bulletModelID;
        Game.GameObjects.get(foundation).setScale(new Vector3f(2f,2f,2f));
        Game.GameObjects.get(gun).translate(new Vector3f(0,1.5f,-0.45f));
        Game.GameObjects.get(foundation).setTranslation(position);
        Game.GameObjects.get(foundation).updateMF();
    }
}
