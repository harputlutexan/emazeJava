package com.macofugames.balldeveloper.actors.visuals;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.macofugames.balldeveloper.util.Assets;
import static com.macofugames.balldeveloper.screens.PlayScreenWithStage.pause;
import static com.macofugames.balldeveloper.screens.PlayScreenWithStage.levelPlayedTime;

import java.text.DecimalFormat;
import java.text.NumberFormat;


public class LevelTimer extends Actor {

    private Viewport viewport;
    private GlyphLayout timeTextLayout;
    private float timer;

    public LevelTimer(Viewport viewport){
        this.viewport = viewport;
        timer = 0;

    }

    @Override
    public void act(float delta){
        NumberFormat f = new DecimalFormat("00");

        if(!pause)
            levelPlayedTime+=delta;

//        levelPlayedTime = timer;
        String time =  String.valueOf( f.format((int) ((levelPlayedTime)/ 60)) + " : "
                + String.valueOf(f.format((int)(levelPlayedTime)%60)));
        timeTextLayout = new GlyphLayout(Assets.instance.fontAssets.timerFont, time);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        viewport.apply();
        super.draw(batch, parentAlpha);
        Assets.instance.fontAssets.timerFont.draw(batch,timeTextLayout,viewport.getWorldWidth()/2 ,viewport.getWorldHeight() - 15);
    }

}
