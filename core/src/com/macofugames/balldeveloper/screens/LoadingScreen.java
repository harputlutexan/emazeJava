package com.macofugames.balldeveloper.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.macofugames.balldeveloper.BallDeveloper;
import com.macofugames.balldeveloper.util.Assets;
import com.macofugames.balldeveloper.util.Constants;

public class LoadingScreen extends ScreenAdapter {

    private BallDeveloper game;
    private float progress,timer;
    private Viewport loadingViewport;

    public LoadingScreen(final BallDeveloper game){
        this.game = game;
    }

    @Override
    public void show(){
        this.progress = 0f;
        timer=0;
        loadingViewport = new ExtendViewport(Constants.LOADING_VIEWPORT_WIDTH,Constants.LOADING_VIEWPORT_HEIGHT,game.gameCam);

        if (game.adService != null) {
            RunnableAction bannerAd = Actions.run(new Runnable() {
                @Override
                public void run() {
                    game.adService.hideBanner();
                }
            });
            bannerAd.run();
        }

    }

    @Override
    public void resize(int width, int height){
        loadingViewport.update(width,height,true);
    }

    @Override
    public void render(float delta){

        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
        loadingViewport.apply();

        update(delta);
        game.spriteBatch.setProjectionMatrix(game.gameCam.combined);
        game.spriteBatch.begin();
        game.spriteBatch.draw(Assets.instance.loadingPageAssets.loadingBackground, 0, 0,
                loadingViewport.getWorldWidth(), loadingViewport.getWorldHeight());
        game.spriteBatch.setColor(new Color(Color.PURPLE));
        game.spriteBatch.draw(Assets.instance.loadingPageAssets.rect,198,loadingViewport.getWorldHeight()/2 - 4,loadingViewport.getWorldWidth()-396,20);
        game.spriteBatch.setColor(new Color(Color.WHITE));
        game.spriteBatch.draw(Assets.instance.loadingPageAssets.rect,200,loadingViewport.getWorldHeight()/2 - 2,loadingViewport.getWorldWidth()-400,16);
        game.spriteBatch.setColor(new Color(Color.PURPLE));
        game.spriteBatch.draw(Assets.instance.loadingPageAssets.rect,200,loadingViewport.getWorldHeight()/2 - 2,(loadingViewport.getWorldWidth()-400)*progress,16);
        game.spriteBatch.setColor(Color.WHITE);
        game.spriteBatch.draw(Assets.instance.loadingPageAssets.mainBlueRight,168+(loadingViewport.getWorldWidth()-400)*progress,loadingViewport.getWorldHeight()/2+13,72,72);
        game.spriteBatch.draw(Assets.instance.loadingPageAssets.princessBlue,235+(loadingViewport.getWorldWidth()-400),loadingViewport.getWorldHeight()/2+18,72,80);
        Assets.instance.loadingPageAssets.loadingFont.draw(game.spriteBatch," LOADING % " + Math.round( progress * 100),loadingViewport.getWorldWidth()/2 - 50,loadingViewport.getWorldHeight()/2 - 40,0, Align.center,false);


        if(progress > 0.98) {
            game.spriteBatch.draw(Assets.instance.loadingPageAssets.heart, 240 + (loadingViewport.getWorldWidth() - 400), loadingViewport.getWorldHeight() / 2 + 83, 16, 16);
            game.spriteBatch.draw(Assets.instance.loadingPageAssets.heart, 250 + (loadingViewport.getWorldWidth() - 400), loadingViewport.getWorldHeight() / 2 + 98, 24, 24);
            game.spriteBatch.draw(Assets.instance.loadingPageAssets.heart, 270 + (loadingViewport.getWorldWidth() - 400), loadingViewport.getWorldHeight() / 2 + 73, 20, 20);
        }
        game.spriteBatch.end();

    }

    public void update(float delta){
        progress = MathUtils.lerp(progress, Assets.instance.getAssetManager().getProgress(), .1f);
        if (Assets.instance.getAssetManager().update() && progress >= Assets.instance.getAssetManager().getProgress() - .001f) {
            Assets.instance.createGameAssets();
            timer += delta;

            if(timer>1)
                game.setScreen(game.mainMenuScreen);

        }

    }

    @Override
    public void dispose(){
    }




}
