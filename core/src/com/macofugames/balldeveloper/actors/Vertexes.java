package com.macofugames.balldeveloper.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.macofugames.balldeveloper.levelreader.LevelVertex;
import com.macofugames.balldeveloper.levelreader.Reader;

import java.util.ArrayList;

public class Vertexes {

    private Vector2 vertexPoint;
    private float bCoef;
    private ArrayList<String> cMask;
    private Color color;
    public boolean visibility = true;

    public Vertexes(LevelVertex levelVertex){

        float x = levelVertex.x+ Reader.levelMaps.width;
        float y = (Reader.levelMaps.height-levelVertex.y)- (Reader.levelMaps.height-200);

        this.vertexPoint = new Vector2(x,y);
        this.bCoef = levelVertex.bCoef;


        if (levelVertex.cMask!=null){
            this.cMask = levelVertex.cMask;
        }else{
            this.cMask= new ArrayList<String>();
            cMask.add("red");
            cMask.add("wall");
        }

        this.color = levelVertex.getColor();
        this.visibility = levelVertex.getVisibility();
    }

    public Float getbCoef() {
        return bCoef;
    }

    public ArrayList<String> getcMask() {
        return cMask;
    }

    public Color getColor() {
        return color;
    }

    public Vector2 getVertexPoint(){return vertexPoint;}
}
