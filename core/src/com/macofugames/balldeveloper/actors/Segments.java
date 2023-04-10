package com.macofugames.balldeveloper.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.macofugames.balldeveloper.levelreader.Reader;
import com.macofugames.balldeveloper.levelreader.LevelSegment;


import java.util.ArrayList;


public class Segments {
    private String TAG = Segments.class.getName();

    private Vector2 startPoints = new Vector2();
    private Vector2 endPoints = new Vector2();
    private float bCoef;
    private ArrayList<String> cMask;
    private ArrayList<String> cGroup;
    private Color color;
    private double curveDegree;
    public boolean visibility = true;

    public Segments(LevelSegment levelSegment) {
        float startXPosition = (Reader.levelMaps.vertexes.get(levelSegment.v0).x
                + Reader.levelMaps.width);
        float startYPosition = (Reader.levelMaps.height
                - Reader.levelMaps.vertexes.get(levelSegment.v0).y) - (Reader.levelMaps.height-200);
        float endXPosition = (Reader.levelMaps.vertexes.get(levelSegment.v1).x
                + Reader.levelMaps.width);
        float endYPosition = (Reader.levelMaps.height
                - Reader.levelMaps.vertexes.get(levelSegment.v1).y)-(Reader.levelMaps.height-200);


        this.startPoints.set(startXPosition,startYPosition);
        this.endPoints.set(endXPosition,endYPosition);

        this.bCoef = levelSegment.bCoef;

        if (levelSegment.cMask!=null){
            this.cMask = levelSegment.cMask;
        }else{
            this.cMask= new ArrayList<String>();
            cMask.add("wall");
        }

        this.color = levelSegment.getColor();

        if (levelSegment.cGroup!=null){
            this.cGroup = levelSegment.cGroup;
        }else{
            this.cGroup= new ArrayList<String>();
        }

        this.visibility = levelSegment.getVisibility();
        this.curveDegree = levelSegment.curve;

    }


    public double getCurveDegree() {
        return curveDegree;
    }

    public Vector2 getStartPoints() {
        return startPoints;
    }

    public Vector2 getEndPoints() {
        return endPoints;
    }

    public Float getbCoef() {
        return bCoef;
    }

    public ArrayList<String> getcMask() {
        return cMask;
    }

    public ArrayList<String> getcGroup() {
        return cGroup;
    }

    public Color getColor() {
        return color;
    }
}
