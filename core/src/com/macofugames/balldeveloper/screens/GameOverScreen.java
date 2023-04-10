package com.macofugames.balldeveloper.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.macofugames.balldeveloper.BallDeveloper;
import com.macofugames.balldeveloper.util.Assets;
import com.macofugames.balldeveloper.util.Constants;
import com.macofugames.balldeveloper.util.Helpers;


public class GameOverScreen extends ScreenAdapter {

    private BallDeveloper game;
    private Viewport viewport;
    private float timer;
    private Stage stage;
    private Image continueButton,giveUpButton;
    private boolean continuePressed,giveUpPressed;
    private boolean amPaused;

    public GameOverScreen(BallDeveloper game){
        this.game = game;
    }


    @Override
    public void show(){

        continuePressed=false;
        giveUpPressed=false;
        amPaused = false;

        if (game.adService != null) {
            RunnableAction bannerAd = Actions.run(new Runnable() {
                @Override
                public void run() {
                    game.adService.hideBanner();
                }
            });
            bannerAd.run();
        }

        timer=0;
        game.gameCam.zoom=1f;
        game.gameCam.update();
        viewport = new ExtendViewport(Constants.GAMEOVER_VIEWPORT_WIDTH,Constants.GAMEOVER_VIEWPORT_HEIGHT,game.gameCam);
        stage = new Stage(viewport);

        continueButton = new Image(Assets.instance.gameOverAssets.continueButton);
        continueButton.setOrigin(continueButton.getWidth()/2,continueButton.getHeight()/2);
        continueButton.setPosition(stage.getWidth()/2 -continueButton.getWidth()/2 - 200, stage.getHeight()/2 );


        continueButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (game.adService != null) {
                    RunnableAction playWooshAction = Actions.run(new Runnable() {
                        @Override
                        public void run() {
                            game.adService.showRewardedAd();
                        }
                    });
                    playWooshAction.run();
                }

                continuePressed=true;
                //game.setScreen(game.mainMenuScreen);

            }
        });

        giveUpButton = new Image(Assets.instance.gameOverAssets.giveUpButton);
        giveUpButton.setOrigin(giveUpButton.getWidth()/2,giveUpButton.getHeight()/2);
        giveUpButton.setPosition(stage.getWidth()/2 - giveUpButton.getWidth()/2 - 200, stage.getHeight()/2 - 200);

        giveUpButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (game.adService != null) {
                    RunnableAction playWooshAction = Actions.run(new Runnable() {
                        @Override
                        public void run() {
                            game.adService.showOrLoadInterstital();
                        }
                    });
                    playWooshAction.run();
                }
                giveUpPressed=true;
                //game.setScreen(game.mainMenuScreen);
            }
        });

        stage.addActor(continueButton);
        stage.addActor(giveUpButton);
        Gdx.input.setInputProcessor(stage);
    }


    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        timer += delta;

        float degree = 360 * timer /10;

        if(degree>=360)
            degree=360;

        game.spriteBatch.setProjectionMatrix(game.gameCam.combined);

        game.spriteBatch.begin();

        game.spriteBatch.draw(Assets.instance.mainMenuAssets.mainMenuBackground, 0, 0,
                stage.getWidth(), stage.getHeight());
        game.spriteBatch.end();

        stage.act(delta);
        stage.draw();

        Helpers.drawArc2(game.spriteBatch, stage.getViewport().getWorldWidth()/2, stage.getViewport().getWorldHeight()- 200
                , 100, 0, degree, Color.PURPLE);

        game.spriteBatch.begin();
        Assets.instance.fontAssets.timerFont2.draw(game.spriteBatch,Integer.toString((int) timer),stage.getViewport().getWorldWidth()/2 - 16,
                stage.getViewport().getWorldHeight() - 185);
        Assets.instance.fontAssets.timerFont2.draw(game.spriteBatch
                ,"Watch video ad to try again\n or give up for main menu"
                ,continueButton.getX() -100 ,
                continueButton.getY() + 300);
        game.spriteBatch.draw(Assets.instance.playerAssets.mainBlueRight,continueButton.getX() + 600,stage.getViewport().getWorldHeight()/2 - 24, 128,128);
        game.spriteBatch.draw(Assets.instance.playerAssets.princess,continueButton.getX() + 730,stage.getViewport().getWorldHeight()/2 - 24, 128,140);
        game.spriteBatch.draw(Assets.instance.playerAssets.hearts, continueButton.getX() + 680, stage.getViewport().getWorldHeight()/2 +118, 24, 24);
        game.spriteBatch.draw(Assets.instance.playerAssets.hearts, continueButton.getX() + 695, stage.getViewport().getWorldHeight()/2 +158, 40, 40);
        game.spriteBatch.draw(Assets.instance.playerAssets.hearts, continueButton.getX() + 725, stage.getViewport().getWorldHeight()/2 +128, 32, 32);
        game.spriteBatch.draw(Assets.instance.playerAssets.woundedPlayer,giveUpButton.getX() + 580,giveUpButton.getY() - 24, 128,128);
        game.spriteBatch.end();

        if (timer > 10.9)
            game.setScreen(game.mainMenuScreen);

        if(!amPaused && ( continuePressed|| giveUpPressed))
            game.setScreen(game.mainMenuScreen);

    }

    @Override
    public void resize(int width, int height){
        stage.getViewport().update(width,height,true);
    }

    @Override
    public void dispose(){
        stage.dispose();

    }

    @Override
    public void pause(){
        amPaused=true;
        if(game.prefs.hasMusic())
            Assets.instance.mainMenuAssets.gameMusic.pause();
    }

    @Override
    public void resume(){
        amPaused=false;
        if(game.prefs.hasMusic())
            Assets.instance.mainMenuAssets.gameMusic.play();
    }
}
