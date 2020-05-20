package Game.PlayableGameObjects;

import Game.Game;
import Game.GameController;
import org.joml.Vector3f;
import java.util.Random;

public class Enemy {
    private int myID;
    private int body;
    private Vector3f oldPosition,
                     targetPosition;
    private int[] myMapPosition;
    private float height;
    private float deltaCumulative = 0;

    public int getMyID(){
        return myID;
    }

    /**
     * select next position to move on map
     */
    private void selectNextPos(){
        int selected = 0,
                hint = Game.GameMap[myMapPosition[0]][myMapPosition[1]];
        int[][] possibleDirections = new int[4][2];

        if((hint & 1) != 0) possibleDirections[selected++] = new int[] {myMapPosition[0]+1,myMapPosition[1]};//down
        if((hint & 2) != 0) possibleDirections[selected++] = new int[] {myMapPosition[0],myMapPosition[1]-1};//left
        if((hint & 4) != 0) possibleDirections[selected++] = new int[] {myMapPosition[0],myMapPosition[1]+1};//right
        if((hint & 8) != 0) possibleDirections[selected++] = new int[] {myMapPosition[0]-1,myMapPosition[1]};//up

        if(selected == 0){
            //TODO: call enemy damaged player
            System.out.println("Enemy damaged player");

            GameController.removeListGameObjects.add(Game.GameObjects.get(body).getId());
            GameController.removeListEnemies.add(myID);
//            Game.GameObjects.keySet().removeIf(key -> key == Game.GameObjects.get(body).getId());
//            Game.enemies.keySet().removeIf(key -> key == myID);
            return;
        }
        myMapPosition = possibleDirections[new Random().nextInt(selected)];
        targetPosition = GameController.calcVec(myMapPosition,height,10);
    }

    /**
     * Make enemy do its own business
     * @param delta time delta on scene
     */
    public void move(float delta){
        deltaCumulative = deltaCumulative + delta > 1 ? 1 : deltaCumulative + delta;
        Vector3f currentPosition = new Vector3f(oldPosition).lerp(targetPosition,deltaCumulative);
        Game.GameObjects.get(body).setTranslation(currentPosition);
        Game.GameObjects.get(body).updateMF();
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
    public Enemy(int id, int body, int[] mapPos, float y){
        this.myID = id;
        this.body = body;
        this.height = y;
        myMapPosition = mapPos;
        oldPosition = GameController.calcVec(myMapPosition, y, 10);
        selectNextPos();
        move(0);
    }
}
