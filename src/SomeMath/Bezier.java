package SomeMath;

import org.joml.Vector3f;
import org.lwjgl.system.CallbackI;

import java.util.ArrayList;

public class Bezier {
    public Bezier(){

    }
    public static Vector3f bezierCurveLinear(Vector3f pointA, Vector3f pointB, float time){
        return ((pointA.mul( 1 - time)).add((pointB.mul(time))));
    }
    public static Vector3f bezierCurveQuadratic(Vector3f pointA, Vector3f pointB, Vector3f pointC , float time){
        return ((pointA.mul((float)Math.pow((1-time),2)).add((pointC.mul((2*time)*(1-time))))).add(pointB.mul(time)));
    }
    public static Vector3f bezierCurveCubic(Vector3f pointA, Vector3f pointB, Vector3f pointC , Vector3f pointD,float time){
        return ((pointA.mul((float)Math.pow((1-time),2)).add((pointC.mul((2*time)*(1-time))))).add(pointB.mul(time)));
    }

    public  static int binomi(int n, int k) {
        if ((n == k) || (k == 0))
            return 1;
        else
            return binomi(n - 1, k) + binomi(n - 1, k - 1);
    }

    // FIRST and LAST are most important ito goes from first index to last
    public static Vector3f generalBezierCurve(ArrayList<Vector3f> vectors,float time){
        int n  = vectors.size() -1;
        Vector3f tempVec ;
        Vector3f sum = new Vector3f(0,0,0);
        int  k = 0;
        for (Vector3f vector : vectors){
            float vector_coef =  (float)Math.pow(time,k) * (float)Math.pow((1-time),n-k) * binomi(n,k);
            tempVec = new Vector3f(vector);
            Vector3f vector3f = tempVec.mul(vector_coef);
            sum.add(vector3f);
            k++;
        }
        return sum;
    }

}
