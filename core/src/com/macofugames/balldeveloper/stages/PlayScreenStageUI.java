package com.macofugames.balldeveloper.stages;


import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.macofugames.balldeveloper.actors.Player;
import com.macofugames.balldeveloper.actors.visuals.HealthBar;
import com.macofugames.balldeveloper.actors.visuals.LevelTimer;
import com.macofugames.balldeveloper.actors.visuals.ProgressBar;
import com.macofugames.balldeveloper.util.Constants;



public class PlayScreenStageUI extends Stage {

    private Player player;
    private Image pauseButton;
    public PlayScreenStageUI(Player player) {

        this.setViewport(new ExtendViewport(Constants.ONSCREEN_CONTROLS_VIEWPORT_SIZE,Constants.ONSCREEN_CONTROLS_VIEWPORT_SIZE));
        this.player = player;
        setUpProgressBar();
        setUpHealthBar();
        setUpTimer();

    }

    private void setUpProgressBar() {addActor(new ProgressBar(this.getViewport(),player));}
    private void setUpHealthBar() {addActor(new HealthBar(this.getViewport(),player));}
    private void setUpTimer() {addActor(new LevelTimer(this.getViewport()));}



}



