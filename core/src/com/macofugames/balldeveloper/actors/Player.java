package com.macofugames.balldeveloper.actors;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.macofugames.balldeveloper.util.Assets;
import com.macofugames.balldeveloper.util.Constants;

public class Player extends Circle {

    public Vector2 center = new Vector2();
    public Vector2 speed = new Vector2(0,0);
    public boolean upButtonPressed;
    public boolean leftButtonPressed;
    public boolean downButtonPressed;
    public boolean rightButtonPressed;
    public Vector2 spawnLocation;
    public boolean isFacingRight = true;
    public boolean isCollided = false;
    public boolean isCheckedIn = false;


    public int collisionNumber;

    public Player (float x, float y, float r){
        this.x = x;
        this.y = y;
        this.radius = r;
        this.center.set(x,y);
        this.spawnLocation = new Vector2(x,y);
        collisionNumber=0;
        respawn();
    }


     public void respawn() {
        x = spawnLocation.x;
        y = spawnLocation.y;
        center.set(spawnLocation);
        speed.setZero();
    }

    public void render( SpriteBatch spriteBatch){


        //spriteBatch.begin();
        if(collisionNumber < Constants.PLAYER_HIT_LIMIT){
            if(isFacingRight)
                spriteBatch.draw(Assets.instance.playerAssets.mainBlueRight, x - radius, y - radius, 2 * radius, 2 * radius);
            else
            spriteBatch.draw(Assets.instance.playerAssets.mainBlueLeft, x - radius, y - radius, 2 * radius, 2 * radius);
        }
        else
            spriteBatch.draw(Assets.instance.playerAssets.woundedPlayer, x - radius, y - radius, 2 * radius, 2 * radius);


        if(isCollided)
            spriteBatch.draw(Assets.instance.playerAssets.ouch,x,y+radius,2*radius,2*radius);
        if(isCheckedIn)
            spriteBatch.draw(Assets.instance.playerAssets.checkedIn,x,y+radius,2*radius,2*radius);

      //  spriteBatch.end();


    }


    public Vector2 getCenter(){
        return center;
    }
    public float getRadius(){return radius;}
    public Vector2 setCenterPosition(float x, float y) {
        return this.center.set(x,y);
    }
    public Vector2 getSpeed(){return speed;}
    public Vector2 setSpeed(float x, float y){
        return this.speed.set(x,y);
    }
}
