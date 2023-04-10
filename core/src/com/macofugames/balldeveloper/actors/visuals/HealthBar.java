package com.macofugames.balldeveloper.actors.visuals;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.macofugames.balldeveloper.actors.Player;
import com.macofugames.balldeveloper.util.Assets;
import com.macofugames.balldeveloper.util.Constants;


public class HealthBar extends Actor {

    private Viewport viewport;
    private Player player;

    public HealthBar(Viewport viewport, Player player){
        this.viewport = viewport;
        this.player = player;

    }

    @Override
    public void act(float delta){

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
         viewport.apply();
           super.draw(batch, parentAlpha);


        batch.draw(Assets.instance.playScreenStageAssets.healthBarFrame,
                10,viewport.getWorldHeight() - Assets.instance.playScreenStageAssets.healthBarFrame.getRegionHeight()/2 -100 ,
                100,250);

        if(player.collisionNumber < Constants.PLAYER_HIT_LIMIT)
        batch.draw(Assets.instance.playScreenStageAssets.healthBar,
                48,viewport.getWorldHeight() - 30 - ((float) player.collisionNumber*180f/Constants.PLAYER_HIT_LIMIT),
                26, ((float) player.collisionNumber*180f/Constants.PLAYER_HIT_LIMIT) );
        else
            batch.draw(Assets.instance.playScreenStageAssets.healthBar,
                    48,viewport.getWorldHeight() - 210,
                    26, 180 );

    }

}
