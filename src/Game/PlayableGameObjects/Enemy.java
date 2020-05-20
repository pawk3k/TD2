package Game.PlayableGameObjects;

import Game.Game;
import Game.GameController;
import org.joml.Vector3f;
import java.util.Random;

public class Enemy {
    private int body;
    private Vector3f oldPosition,
                     targetPosition;
    private int[] myMapPosition;
    private float height;
    private float deltaCumulative = 0;

    private void selectNextPos(){
        int selected = 0,
                hint = Game.GameMap[myMapPosition[0]][myMapPosition[1]];
        int[][] possibleDirections = new int[4][2];

        if((hint & 1) == 1) possibleDirections[selected++] = new int[] {myMapPosition[0]+1,myMapPosition[1]};//down
        if((hint & 2) == 2) possibleDirections[selected++] = new int[] {myMapPosition[0],myMapPosition[1]-1};//left
        if((hint & 4) == 4) possibleDirections[selected++] = new int[] {myMapPosition[0],myMapPosition[1]+1};//right
        if((hint & 8) == 8) possibleDirections[selected++] = new int[] {myMapPosition[0]-1,myMapPosition[1]+1};//up

        if(selected == 0){
            //TODO: call enemy damaged player + destroy this enemy
            System.out.println("Enemy damaged base");
            return;
        }
        myMapPosition = possibleDirections[new Random().nextInt(selected)];
        targetPosition = GameController.calcVec(myMapPosition,height,10);
    }

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

    public Enemy(int body, int[] mapPos, float y){
        this.body = body;
        this.height = y;
        myMapPosition = mapPos;
        oldPosition = GameController.calcVec(myMapPosition, y, 10);
        selectNextPos();
        move(0);
    }
}
