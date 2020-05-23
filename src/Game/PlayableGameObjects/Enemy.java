package Game.PlayableGameObjects;

import Game.Game;
import Game.GameController;
import Game.GameObject;
import org.joml.Vector3f;
import java.util.Random;

public class Enemy {
    private int myID;
    private int body;
    private Vector3f oldPosition,
                     targetPosition;
    private int[] myMapPosition;
    private float height;
    private float myTargetRotation;
    private float deltaCumulative = 0;

    private boolean rotating = false;
    private float rotateTarget = 0;

    private int myHealth;

    public int getMyID(){
        return myID;
    }

    public void damage(int dmg){ myHealth -= dmg; }

    /**
     * select next position to move on map
     */
    private void selectNextPos(){
        int selected = 0,
                hint = Game.GameMap[myMapPosition[0]][myMapPosition[1]];
        int[] myOldPosition;
        int[][] possibleDirections = new int[4][2];

        if((hint & 1) != 0) possibleDirections[selected++] = new int[] {myMapPosition[0]+1,myMapPosition[1]};//down
        if((hint & 2) != 0) possibleDirections[selected++] = new int[] {myMapPosition[0],myMapPosition[1]-1};//left
        if((hint & 4) != 0) possibleDirections[selected++] = new int[] {myMapPosition[0],myMapPosition[1]+1};//right
        if((hint & 8) != 0) possibleDirections[selected++] = new int[] {myMapPosition[0]-1,myMapPosition[1]};//up

        if(selected == 0){
            System.out.println("Enemy damaged player");
            Game.getDamage();
            killMe();
            return;
        }
        myOldPosition = new int [] {myMapPosition[0],myMapPosition[1]};
        myMapPosition = possibleDirections[new Random().nextInt(selected)];
        targetPosition = GameController.calcVec(myMapPosition,height,10);
        if(myOldPosition[0] - myMapPosition[0] > 0) rotateTarget = (float) Math.toRadians(180);
        else if(myOldPosition[0] - myMapPosition[0] < 0) rotateTarget = 0;
        else if(myOldPosition[1] - myMapPosition[1] > 0) rotateTarget = (float) Math.toRadians(-90);
        else rotateTarget = (float) Math.toRadians(90);
        rotating = true;
    }

    private void killMe(){
        GameController.removeListGameObjects.add(Game.GameObjects.get(body).getId());
        GameController.removeListEnemies.add(myID);
    }

    /**
     * Make enemy do its own business
     * @param delta time delta on scene
     */
    public void move(float delta){
        if(myHealth <= 0){
            killMe();
            return;
        }
        deltaCumulative = deltaCumulative + delta > 1 ? 1 : deltaCumulative + delta;
        GameObject bodyGO = Game.GameObjects.get(body);

        bodyGO.setRotation(new Vector3f(0,GameController.radianInterpolation(bodyGO.getRotation().y,rotateTarget,deltaCumulative),0));

        Vector3f currentPosition = new Vector3f(oldPosition).lerp(targetPosition,deltaCumulative);
        bodyGO.setTranslation(currentPosition);
        bodyGO.updateMF();
        if(currentPosition.distance(targetPosition) <= 0.05f) {
            deltaCumulative = 0;
            oldPosition = new Vector3f(targetPosition);
            selectNextPos();
        }
    }

    public Vector3f getPosition(){
        return Game.GameObjects.get(body).getTranslation();
    }

    /**
     * @param id Enemy ID
     * @param body Enemy' game object
     * @param mapPos position on map in matrix coordinates [y, x]
     * @param y Enemy y (height) position on scene
     */
    public Enemy(int id, int body, int[] mapPos, float y, int health){
        this.myHealth = health;
        this.myID = id;
        this.body = body;
        this.height = y;
        myMapPosition = mapPos;
        oldPosition = GameController.calcVec(myMapPosition, y, 10);
        selectNextPos();
        move(0);
    }
}
