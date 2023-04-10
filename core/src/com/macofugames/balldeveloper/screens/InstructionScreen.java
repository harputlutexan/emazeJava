package com.macofugames.balldeveloper.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.macofugames.balldeveloper.BallDeveloper;
import com.macofugames.balldeveloper.util.Assets;
import com.macofugames.balldeveloper.util.Constants;

public class InstructionScreen extends ScreenAdapter {

    private BallDeveloper game;
    private Stage stage;
    private Image backButton,instructionsImage;

    public InstructionScreen(final BallDeveloper game){
        this.game = game;
    }


    @Override
    public void show(){

        stage = new Stage(new ExtendViewport(Constants.INSTRUCTIONS_VIEWPORT_WIDTH,Constants.INSTRUCTIONS_VIEWPORT_HEIGHT,game.gameCam));
        if (game.adService != null) {
            RunnableAction bannerAd = Actions.run(new Runnable() {
                @Override
                public void run() {
                    game.adService.hideBanner();
                }
            });
            bannerAd.run();
        }

        backButton = new Image(Assets.instance.settingsScreenAssets.backButton);
        backButton.setOrigin(backButton.getWidth()/2,backButton.getHeight()/2);
        backButton.setPosition(50,stage.getHeight()- 2f*backButton.getHeight());

        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.mainMenuScreen);
            }
        });


        instructionsImage = new Image(Assets.instance.mainMenuAssets.instructionsPicture);
        instructionsImage.setOrigin(instructionsImage.getWidth()/2,instructionsImage.getHeight()/2);
        instructionsImage.setPosition(stage.getWidth()/2-instructionsImage.getWidth()/2,stage.getHeight()/2 - instructionsImage.getHeight()/2 + 15);


        Gdx.input.setInputProcessor(stage);

        stage.addActor(backButton);
        stage.addActor(instructionsImage);
    }

    @Override
    public void resize(int width, int height){
        stage.getViewport().update(width,height,true);
    }

    @Override
    public void render(float delta){

        Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();

    }

    @Override
    public void dispose(){
        stage.dispose();
    }
}
