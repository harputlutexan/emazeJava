package com.macofugames.balldeveloper.actors.visuals;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.macofugames.balldeveloper.util.Assets;

public class PlayScreenBackGround extends Actor {

    private Viewport viewport;

    public PlayScreenBackGround(Viewport viewport) {
        this.viewport = viewport;

    }

    @Override
    public void act(float delta) {

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        viewport.apply();
        super.draw(batch, parentAlpha);


        batch.draw(Assets.instance.mainMenuAssets.zoomOutBackground,
                0,
                0 ,
                viewport.getWorldWidth(),
                viewport.getWorldHeight());



    }
}
