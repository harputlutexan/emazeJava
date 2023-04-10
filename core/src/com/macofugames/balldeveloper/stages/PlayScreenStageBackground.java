package com.macofugames.balldeveloper.stages;


import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.macofugames.balldeveloper.actors.Player;
import com.macofugames.balldeveloper.actors.visuals.PlayScreenBackGround;
import com.macofugames.balldeveloper.util.Constants;


public class PlayScreenStageBackground extends Stage {

    private Player player;
    public PlayScreenStageBackground(Player player) {

        this.setViewport(new ExtendViewport(Constants.MAINMENU_VIEWPORT_WIDTH,Constants.MAINMENU_VIEWPORT_HEIGHT));
        this.player = player;

        setUpBackground();

    }

    private void setUpBackground() {addActor(new PlayScreenBackGround(this.getViewport()));}

}
