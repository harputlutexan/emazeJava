package com.macofugames.balldeveloper.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.macofugames.balldeveloper.BallDeveloper;
import com.macofugames.balldeveloper.util.Assets;
import com.macofugames.balldeveloper.util.Constants;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class SplashScreen extends ScreenAdapter {
    private BallDeveloper game;
    private Image macofuLogo,copyRightTextImage;
    private Stage stage;
    private Group group;

    public SplashScreen(final BallDeveloper game){
        this.game = game;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        if (game.adService != null) {
            RunnableAction bannerAd = Actions.run(new Runnable() {
                @Override
                public void run() {
                    game.adService.hideBanner();
                }
            });
            bannerAd.run();
        }

        stage = new Stage(new FitViewport(Constants.SPLASH_VIEWPORT_WIDTH,Constants.SPLASH_VIEWPORT_HEIGHT));
        group = new Group();
        stage.addActor(group);
        Runnable transitionRunnable = new Runnable() {
            @Override
            public void run() {
                game.setScreen(game.loadingScreen);
            }
        };

        Runnable soundRunnable = new Runnable() {
            @Override
            public void run() {
                if(game.prefs.hasSound())
                     Assets.instance.splashScreenAssets.introSound.play(0.5f);
                else
                    Assets.instance.splashScreenAssets.introSound.stop();
            }
        };
        macofuLogo = new Image(Assets.instance.splashScreenAssets.logo);
        copyRightTextImage = new Image(Assets.instance.splashScreenAssets.logoText);

        macofuLogo.setOrigin(macofuLogo.getWidth()/2,macofuLogo.getHeight()/2);
        macofuLogo.setPosition(stage.getWidth()/2-macofuLogo.getWidth()/2
                ,stage.getHeight()/2 - macofuLogo.getHeight()/2);
        copyRightTextImage.setOrigin(copyRightTextImage.getWidth()/2,copyRightTextImage.getHeight()/2);
        copyRightTextImage.setPosition(stage.getWidth()/2 - copyRightTextImage.getWidth()/2, 40);

        group.addActor(macofuLogo);
        group.addActor(copyRightTextImage);
        group.setOrigin(macofuLogo.getWidth()/2,macofuLogo.getHeight()/2);
        group.setPosition(stage.getWidth()/2-macofuLogo.getWidth()/2,stage.getHeight()/2-macofuLogo.getHeight()/2);


        group.addAction(sequence(alpha(0),scaleTo(0.1f,0.1f),
                parallel(fadeIn(1.5f, Interpolation.pow5),
                        scaleTo(1f, 1f, 1.5f, Interpolation.sine),
                        moveTo( group.getWidth()/2, group.getHeight()/2, 1.25f, Interpolation.smoother))
                ,run(soundRunnable),delay(1.25f),fadeOut(1f), run(transitionRunnable)));

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update(delta);
        stage.draw();

    }

    public void update(float delta) {
        stage.act(delta);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, false);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }


}
