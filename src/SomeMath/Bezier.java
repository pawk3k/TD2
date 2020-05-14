package SomeMath;

import org.joml.Vector3f;

public class Bezier {
    public Bezier(){

    }
    public static Vector3f bezierCurveLinear(Vector3f pointA, Vector3f pointB, float time){
        return ((pointA.mul( 1 - time)).add((pointB.mul(time))));
    }
    public static Vector3f bezierCurveQuadratic(Vector3f pointA, Vector3f pointB, Vector3f pointC , float time){


        return ((pointA.mul((float)Math.pow((1-time),2)).add((pointC.mul((2*time)*(1-time))))).add(pointB.mul(time)));
    }
}
