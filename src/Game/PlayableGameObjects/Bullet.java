package Game.PlayableGameObjects;

import Game.Game;
import Game.GameController;
import Game.GameObject;
import org.joml.Vector3f;

public class Bullet {
    public boolean deleted = false;
    private GameObject me;
    private float deltaCumulative;
    private Vector3f stPos, finPos;
    private int targetEnemy;
    private int myDamage;

    public void move(float delay){
        deltaCumulative += delay;
        if(deltaCumulative >= 1){
            if(deleted) return;
            if(Game.enemies.containsKey(targetEnemy)) Game.enemies.get(targetEnemy).damage(myDamage);
            GameController.removeListGameObjects.add(me.getId());
            deleted = true;
            return;
        }
        me.setTranslation(new Vector3f(stPos).lerp(finPos, deltaCumulative));
        me.updateM();
    }

    public Bullet(Vector3f from, Vector3f to, int model, int targetEnemy, int damage) throws Exception {
        this.myDamage = damage;
        this.targetEnemy = targetEnemy;
        this.stPos = from;
        this.finPos = to;

        int body = GameController.createGameObject(0, model);
        me = Game.GameObjects.get(body);
        me.setTranslation(from);
        me.setScale(new Vector3f(0.3f,0.3f,0.3f));
        me.updateM();
    }
}
