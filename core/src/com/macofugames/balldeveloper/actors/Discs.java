package com.macofugames.balldeveloper.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.macofugames.balldeveloper.levelreader.LevelDisc;
import com.macofugames.balldeveloper.levelreader.Reader;
import java.util.ArrayList;


public class Discs {
    private Vector2 centerPosition = new Vector2();
    private Vector2 speed = new Vector2();
    private double invMass;
    private float damping;
    private float radius;
    private Color color;
    private float bCoef;
    private ArrayList<String> cMask;
    private ArrayList<String> cGroup;
    private String TAG = Discs.class.getName();


    public Discs(LevelDisc levelDisc){
        float centerXPosition = (levelDisc.pos.get(0)
                + Reader.levelMaps.width);
        float centerYPosition = (Reader.levelMaps.height
                - levelDisc.pos.get(1)) - (Reader.levelMaps.height-200);
        this.centerPosition.set(centerXPosition,centerYPosition);

        if(levelDisc.speed != null)
             this.speed.set(levelDisc.speed.get(0),-levelDisc.speed.get(1));
        else
            this.speed.set(0,0);

        this.radius = levelDisc.radius;
        this.damping = levelDisc.damping;
        this.invMass = levelDisc.invMass;
        this.color = levelDisc.getColor();
        this.cMask = levelDisc.cMask;

        if (levelDisc.cMask!=null){
            this.cMask = levelDisc.cMask;
        }else{
            this.cMask= new ArrayList<String>();
            cMask.add("red");
        }


        this.cGroup = levelDisc.cGroup;
        this.bCoef = levelDisc.bCoef;
    }

    public Vector2 getCenterPosition() {
        return centerPosition;
    }
    public Vector2 setCenterPosition(float x, float y) {
        return this.centerPosition.set(x,y);
    }


    public Float getRadius() {
        return radius;
    }

    public Float getbCoef() {
        return bCoef;
    }

    public Color getColor() {
        return color;
    }

    public Float getDamping() {
        return damping;
    }

    public Double getInvMass() {
        return invMass;
    }

    public Vector2 getSpeed() {
        return speed;
    }

    public Vector2 setSpeed(float x, float y){
        return this.speed.set(x,y);
    }

    public ArrayList<String> getcMask() {
        return cMask;
    }

    public ArrayList<String> getcGroup() {
        return cGroup;
    }
}
