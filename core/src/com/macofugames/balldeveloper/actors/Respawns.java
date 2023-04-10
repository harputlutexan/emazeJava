package com.macofugames.balldeveloper.actors;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.macofugames.balldeveloper.levelreader.Reader;

public class Respawns extends Circle {

    private Vector2 respawnCenter = new Vector2();

    public Respawns(float x, float y, float radius){
        this.x = x + Reader.levelMaps.width;
        this.y = (Reader.levelMaps.height - y) - (Reader.levelMaps.height-200);
        this.radius = radius;
        this.respawnCenter.set(x,y);
    }

}
