package com.macofugames.balldeveloper.levelreader;

import com.badlogic.gdx.graphics.Color;

import java.util.ArrayList;

public class LevelSegment {

    public float bCoef;
    public ArrayList<String> cMask;
    public ArrayList<String> cGroup;
    public int v0;
    public int v1;
    public double curve;
    public String color;
    public String vis;

    public Color getColor(){

        if (color != null) {

            float r = (float) Integer.parseInt(color.substring(0,2),16) / 255.0f;
            float g = (float) Integer.parseInt(color.substring(2,4),16) / 255.0f;
            float b = (float) Integer.parseInt(color.substring(4,6),16) / 255.0f;

            return new Color(r,g,b,1);
        }

        else
            return Color.BLACK;
    }

    public boolean getVisibility(){
        if (vis==null || vis=="true")
            return true;
        else
            return false;
    }

}
