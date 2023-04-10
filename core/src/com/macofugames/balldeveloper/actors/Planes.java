package com.macofugames.balldeveloper.actors;


import com.badlogic.gdx.math.Vector2;
import com.macofugames.balldeveloper.levelreader.LevelPlane;
import com.macofugames.balldeveloper.levelreader.Reader;

import java.util.ArrayList;


public class Planes {

    private float bCoef;
    public ArrayList<String> cMask;
    private ArrayList<String> cGroup;
    private int dist;
    private ArrayList<Integer> normal;
    private Vector2 startPoints;
    private Vector2 endPoints;



    public Vector2 getEndPoints() {
        return endPoints;
    }

    public Vector2 getStartPoints() {
        return startPoints;
    }



    public Planes (LevelPlane levelPlane) {
        this.bCoef = levelPlane.bCoef;
        this.cMask = levelPlane.cMask;
        this.cGroup = levelPlane.cGroup;
        this.dist = levelPlane.dist;
        this.normal = levelPlane.normal;
        int xNormal = normal.get(0);
        int yNormal = normal.get(1);
        float xStartPoint = (Reader.levelMaps.bg.width
                + (dist * xNormal))  * Math.abs(xNormal);
        float yStartPoint = (Reader.levelMaps.bg.height
                - (dist * yNormal))  * Math.abs(yNormal);
        float xEndPoint;
        float yEndPoint;
        if (xNormal == 0) {
            xEndPoint = (Reader.levelMaps.bg.width);
            yEndPoint = yStartPoint;
        } else {
            xEndPoint = xStartPoint;
            yEndPoint = (Reader.levelMaps.bg.height * 2);
        }
        this.startPoints = new Vector2(xStartPoint, yStartPoint);
        this.endPoints = new Vector2(xEndPoint, yEndPoint);
    }

    public float getbCoef() {
        return bCoef;
    }

    public int getDist() {
        return dist;
    }

    public ArrayList<String> getcMask() {
        return cMask;
    }

    public ArrayList<Integer> getNormal() {
        return normal;
    }

    public ArrayList<String> getcGroup() {
        return cGroup;
    }


}
