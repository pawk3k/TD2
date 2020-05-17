package Game.PlayableGameObjects;

import Game.Game;
import org.joml.Vector3f;

public class Turret {
    int id, gun, platform, foundation;
    Vector3f position,
             facing = new Vector3f(0,0,0);

    public Turret(int id, int gun, int platform, int foundation, Vector3f position){
        this.id = id;
        this.gun = gun;
        this.platform = platform;
        this.foundation = foundation;
        this.position = position;
        Game.GameObjects.get(gun).translate(new Vector3f(0,1.6f,0));
        Game.GameObjects.get(gun).updateMF();
    }
}
