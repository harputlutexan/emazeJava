package com.macofugames.balldeveloper.actors.visuals;


import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.macofugames.balldeveloper.actors.Player;
import com.macofugames.balldeveloper.util.Assets;

import static com.macofugames.balldeveloper.screens.PlayScreenWithStage.playingLevel;
import static com.macofugames.balldeveloper.screens.PlayScreenWithStage.levelWidth;

public class ProgressBar extends Actor {

    private Viewport viewport;
    private Player player;
    private GlyphLayout currentlevelLayout;
    private GlyphLayout nextlevelLayout;

    public ProgressBar(Viewport viewport, Player player) {
        this.viewport = viewport;
        this.player = player;


        if (playingLevel < 10) {
            currentlevelLayout = new GlyphLayout(Assets.instance.fontAssets.levelFontBig, Integer.toString(playingLevel));
            nextlevelLayout = new GlyphLayout(Assets.instance.fontAssets.levelFontBig, Integer.toString(playingLevel + 1));
        } else if (playingLevel < 100) {
            currentlevelLayout = new GlyphLayout(Assets.instance.fontAssets.levelFontMedium, Integer.toString(playingLevel));
            nextlevelLayout = new GlyphLayout(Assets.instance.fontAssets.levelFontMedium, Integer.toString(playingLevel + 1));
        } else {
            currentlevelLayout = new GlyphLayout(Assets.instance.fontAssets.levelFontSmall, Integer.toString(playingLevel));
            nextlevelLayout = new GlyphLayout(Assets.instance.fontAssets.levelFontSmall, Integer.toString(playingLevel + 1));
        }
    }

    @Override
    public void act(float delta) {

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        viewport.apply();
        super.draw(batch, parentAlpha);

        batch.draw(Assets.instance.playScreenStageAssets.progressBarFrame,
                viewport.getWorldWidth() / 2 - 120,
                viewport.getWorldHeight() - 120,
                400,
                90);

        batch.draw(Assets.instance.playScreenStageAssets.progressBar,
                viewport.getWorldWidth() / 2 - 30,
                viewport.getWorldHeight() - 86,
                220 * (player.x / levelWidth),
                22);


        if (playingLevel < 10) {
            Assets.instance.fontAssets.levelFontBig.draw(batch, currentlevelLayout, viewport.getWorldWidth() / 2 - 76, viewport.getWorldHeight() - 53);
            Assets.instance.fontAssets.levelFontBig.draw(batch, nextlevelLayout, viewport.getWorldWidth() / 2 + 215, viewport.getWorldHeight() - 53);
        } else if (playingLevel < 100) {
            Assets.instance.fontAssets.levelFontMedium.draw(batch, currentlevelLayout, viewport.getWorldWidth() / 2 - 82, viewport.getWorldHeight() - 56);
            Assets.instance.fontAssets.levelFontMedium.draw(batch, nextlevelLayout, viewport.getWorldWidth() / 2 + 209, viewport.getWorldHeight() - 56);
        } else{
            Assets.instance.fontAssets.levelFontSmall.draw(batch, currentlevelLayout, viewport.getWorldWidth() / 2 - 84, viewport.getWorldHeight() - 49);
             Assets.instance.fontAssets.levelFontSmall.draw(batch, nextlevelLayout, viewport.getWorldWidth() / 2 + 220, viewport.getWorldHeight() - 49);
    }

}



}
