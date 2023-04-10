package com.macofugames.balldeveloper.levelreader;

import com.badlogic.gdx.graphics.Color;
import java.util.ArrayList;

public class LevelVertex {

    public float x;
    public float y;
    public String color;
    public ArrayList<String> cMask;
    public float bCoef;
    public String vis;

    public Color getColor(){

        if (color != null){
            float r = (float) Integer.parseInt(color.substring(0,1),16) / 255.0f;
            float g = (float) Integer.parseInt(color.substring(2,3),16) / 255.0f;
            float b = (float) Integer.parseInt(color.substring(4,5),16) / 255.0f;
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