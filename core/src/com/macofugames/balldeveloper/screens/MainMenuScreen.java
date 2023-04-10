package com.macofugames.balldeveloper.screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.macofugames.balldeveloper.BallDeveloper;
import com.macofugames.balldeveloper.levelreader.Reader;
import com.macofugames.balldeveloper.util.Assets;
import com.macofugames.balldeveloper.util.Constants;

import static com.macofugames.balldeveloper.screens.PlayScreenWithStage.playingLevel;

public class MainMenuScreen extends ScreenAdapter {
    private BallDeveloper game;
    private Stage stage;
    private Image sound,play,noads,emaze,ball,settings,leaderboard,instructionsIcon;
    private Window window;
    private Table table;
    private ScrollPane scrollPane;
    private TextButton backToMenu;
    private boolean isVideoWatched;


    public MainMenuScreen(final BallDeveloper game){
        this.game = game;
    }

    @Override
    public void show(){
        //  game.prefs.resetLevel();
        // game.prefs.resetTimes();

        if (game.adService != null) {
            RunnableAction bannerAd = Actions.run(new Runnable() {
                @Override
                public void run() {
                    game.adService.showBanner();
                }
            });
            bannerAd.run();
        }

        isVideoWatched = false;
        Assets.instance.mainMenuAssets.gameMusic.setVolume(0.5f);
        Assets.instance.mainMenuAssets.gameMusic.setLooping(true);

        stage = new Stage(new ExtendViewport(Constants.MAINMENU_VIEWPORT_WIDTH,Constants.MAINMENU_VIEWPORT_HEIGHT,game.gameCam));
        game.gameCam.zoom=1f;
        game.gameCam.update();
        if(game.prefs.hasMusic()) {
            Assets.instance.mainMenuAssets.gameMusic.play();
            sound = new Image(Assets.instance.mainMenuAssets.soundOnButton);
        }
        else
            sound = new Image(Assets.instance.mainMenuAssets.soundOffButton);


        sound.setOrigin(sound.getWidth()/2,sound.getHeight()/2);
        sound.setPosition(stage.getWidth() - 3f*sound.getWidth(), stage.getHeight() - 1.5f*sound.getHeight());
        sound.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                if(Assets.instance.mainMenuAssets.gameMusic.isPlaying())
                {Assets.instance.mainMenuAssets.gameMusic.stop();
                    game.prefs.setSound(false);
                    game.prefs.setMusic(false);
                    sound.setDrawable(new TextureRegionDrawable( Assets.instance.mainMenuAssets.soundOffButton));
                }
                else {
                    game.prefs.setMusic(true);
                    game.prefs.setSound(true);
                    Assets.instance.mainMenuAssets.gameMusic.play();
                    sound.setDrawable(new TextureRegionDrawable( Assets.instance.mainMenuAssets.soundOnButton));
                }
            }
        });

        settings = new Image(Assets.instance.mainMenuAssets.settingsButton);
        settings.setOrigin(settings.getWidth()/2,settings.getHeight()/2);
        settings.setPosition(stage.getWidth() - 1.5f*settings.getWidth(), stage.getHeight() - 1.5f*settings.getHeight());
        settings.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.settingsScreen);
            }
        });


        instructionsIcon = new Image(Assets.instance.mainMenuAssets.instructionsButton);
        instructionsIcon.setOrigin(instructionsIcon.getWidth()/2,instructionsIcon.getHeight()/2);
        instructionsIcon.setPosition(2f*instructionsIcon.getWidth(),stage.getHeight()-1.5f*instructionsIcon.getHeight());
        instructionsIcon.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.instructionScreen);
            }
        });

//        noads = new Image(Assets.instance.mainMenuAssets.noadsButton);
//        noads.setOrigin(noads.getWidth()/2,noads.getHeight()/2);
//        noads.setPosition(2f*noads.getWidth(), stage.getHeight() - 1.5f*noads.getHeight());

//        noads.addListener(new ClickListener(){
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                if (Gdx.app.getType()==Application.ApplicationType.Android){
//                    Gdx.net.openURI(
//                            "https://play.google.com/store/apps/details?id=com.macofugames.balldeveloperpemium");
//                }else{
//                    Gdx.net.openURI("https://apps.apple.com/us/app/emaze-ball-premium/id1493275038?app=itunes&ign-mpt=uo%3D4");
//
//                }
//            }
//        });

        leaderboard = new Image(Assets.instance.mainMenuAssets.leaderboardButton);
        leaderboard.setOrigin(leaderboard.getWidth()/2,leaderboard.getHeight()/2);
        leaderboard.setPosition(0.5f*leaderboard.getWidth(), stage.getHeight() - 1.5f*leaderboard.getHeight());

        window = new Window("BEST TIMES",Assets.instance.skinAssets.skin,"default");
        window.getTitleLabel().setAlignment(Align.center);
        window.top();
        window.setMovable(false);
        window.setVisible(false);
        window.setSize(800,800);
        window.setPosition(stage.getWidth()/2 - 400 ,stage.getHeight()/2-400);
        window.toFront();
        table = new Table();

        for (int i=1;i<=Constants.TOTAL_LEVEL;i++){
            Label label1 = new Label("Level " + (i),Assets.instance.skinAssets.skin,"title");
            Label label2 = new Label(game.prefs.getLevelTime(i),Assets.instance.skinAssets.skin,"title");
            label1.setFontScale(2f);
            label2.setFontScale(2f);
            table.add(label1).expandX().align(Align.center).colspan(3).pad(5.0f);
            table.add(label2).expandX().align(Align.center).colspan(3).pad(5.0f);
            table.row();
        }
        backToMenu = new TextButton("OK",Assets.instance.skinAssets.skin,"round");
        backToMenu.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                window.setVisible(false);
            }
        });
        scrollPane = new ScrollPane(table,Assets.instance.skinAssets.skin);
        window.add(scrollPane).expandX().fill();
        window.row();
        window.add(backToMenu).size(1.5f*backToMenu.getWidth(),1.5f*backToMenu.getHeight());
        leaderboard.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                window.setVisible(true);
            }
        });

        emaze = new Image(Assets.instance.mainMenuAssets.emazeText);
        emaze.setOrigin(emaze.getWidth()/2,emaze.getHeight()/2);
        emaze.setSize(emaze.getWidth()/1.5f,emaze.getHeight()/1.25f);
        emaze.setPosition(stage.getWidth()/2 - emaze.getWidth()/2 - 100
                , stage.getHeight() - emaze.getHeight()*5/4-50);

        ball = new Image(Assets.instance.mainMenuAssets.ballText);
        ball.setOrigin(ball.getWidth()/2,ball.getHeight()/2);
        ball.setSize(ball.getWidth()/1.5f,ball.getHeight()/1.25f);
        ball.setPosition(stage.getWidth()/2 - ball.getWidth()/2 + 100, stage.getHeight() - ball.getHeight()*37/16-50);

        play = new Image(Assets.instance.mainMenuAssets.playButton);
        play.setOrigin(play.getWidth()/2,play.getHeight()/2);
        play.setPosition(stage.getWidth()/2 - play.getWidth()/2 -25 , 2*play.getHeight() + 50);
        play.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.prefs.setFromSettings(false);
                Reader.load(game.prefs.getLevel());
                playingLevel=game.prefs.getLevel();
                game.prefs.setRevived(false);
                game.setScreen(game.playScreenWithStage);
            }
        });




        stage.addActor(sound);
        stage.addActor(settings);
//        stage.addActor(noads);
        stage.addActor(leaderboard);
        stage.addActor(emaze);
        stage.addActor(ball);
        stage.addActor(play);
        stage.addActor(instructionsIcon);
        stage.addActor(window);

        Gdx.input.setInputProcessor(stage);
    }




    @Override
    public void render(float delta){

        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);

        game.spriteBatch.setProjectionMatrix(game.gameCam.combined);
        game.spriteBatch.begin();
        game.spriteBatch.draw(Assets.instance.mainMenuAssets.mainMenuBackground, 0, 0,
                stage.getWidth(), stage.getHeight());
        game.spriteBatch.end();
        if(isVideoWatched) {
            if(game.prefs.getFromSettings()) {
                Reader.load(game.prefs.getLevelSelected());
                playingLevel=game.prefs.getLevelSelected();
            }
            else{
                Reader.load(game.prefs.getLevel());
                playingLevel=game.prefs.getLevel();
            }

            game.prefs.setRevived(true);
            game.setScreen(game.playScreenWithStage);
        }
        else {
            update(delta);
            stage.draw();
        }


    }


    public void update(float delta) {
        stage.act(delta);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);

    }

    @Override
    public void pause() {

        if(game.prefs.hasMusic())
            Assets.instance.mainMenuAssets.gameMusic.pause();
    }

    @Override
    public void resume() {

        if(game.adService != null)
            isVideoWatched = game.adService.checkAdWatched();
        else
            isVideoWatched = false;


        if(game.prefs.hasMusic())
            Assets.instance.mainMenuAssets.gameMusic.play();

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }

}
