package com.macofugames.balldeveloper.actors;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.macofugames.balldeveloper.levelreader.Reader;
import com.macofugames.balldeveloper.util.Assets;

public class Princess extends Circle {

    private Vector2 princessCenter = new Vector2();
    public boolean isSaved;

    public Princess(float x, float y, float r){
        this.x = x + Reader.levelMaps.width;
        this.y = (Reader.levelMaps.height - y) - (Reader.levelMaps.height-200);
        this.radius = r;
        this.princessCenter.set(x,y);
        isSaved = false;
    }

    public void render(SpriteBatch spriteBatch){

       // spriteBatch.begin();
        spriteBatch.draw(Assets.instance.playerAssets.princess,x-radius,y-radius,2*radius,2.25f*radius);

        if(isSaved) {
            spriteBatch.draw(Assets.instance.playerAssets.hearts,x -15, y + radius + 5, 12, 12);
            spriteBatch.draw(Assets.instance.playerAssets.hearts,x -5, y+ radius + 13, 18, 18);
            spriteBatch.draw(Assets.instance.playerAssets.hearts, x+10, y+ radius + 7, 15, 15);
        }
       // spriteBatch.end();
    }

}
