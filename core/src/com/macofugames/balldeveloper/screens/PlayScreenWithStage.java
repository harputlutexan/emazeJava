package com.macofugames.balldeveloper.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.profiling.GLProfiler;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.macofugames.balldeveloper.BallDeveloper;
import com.macofugames.balldeveloper.actors.Player;
import com.macofugames.balldeveloper.actors.Princess;
import com.macofugames.balldeveloper.levelreader.Reader;
import com.macofugames.balldeveloper.maps.Level;
import com.macofugames.balldeveloper.scenes.OnscreenControls;
import com.macofugames.balldeveloper.stages.PlayScreenStageBackground;
import com.macofugames.balldeveloper.stages.PlayScreenStageUI;
import com.macofugames.balldeveloper.util.Assets;
import com.macofugames.balldeveloper.util.Collisions;
import com.macofugames.balldeveloper.util.Constants;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class PlayScreenWithStage extends ScreenAdapter {


    private BallDeveloper game;
    private Viewport gamePort;
    private Player player;
    private OnscreenControls onscreenControls;
    private Level level;
    private Collisions collisions;
    private Princess princess;
    private InputMultiplexer multiplexer;
    private PlayScreenStageBackground stageBackground;
    private PlayScreenStageUI stageUI;
    private float timer,groundOffset,SPAWN_DISTANCE;
    private Window pauseWindow;
    private TextButton continueGame,exitGame;
    private Image tapToContinue;
    private boolean updatePrefs;
    private boolean newBestTime;
    public static int playingLevel;
    public static float levelWidth;
    public static boolean pause;
    public static float levelPlayedTime;
    private float initialZoom;
    private float zoomTimer;
    private boolean zoomFinished;
    float initialX;
    private float t;
    private boolean pauseCalled;
    private boolean gotoMain,goToPlay;
    private boolean tapToContinuePressed;
    public static float backgroundWidth;
//    private GLProfiler glProfiler;


    public PlayScreenWithStage(final BallDeveloper game) {
        this.game = game;
    }

    @Override
    public void show() {
//        glProfiler = new GLProfiler(Gdx.graphics);
//        glProfiler.enable();
        pauseCalled=false;
        initialZoom=8f;
        zoomFinished=false;
        zoomTimer=0;
        initialX=2400;

        if (game.adService != null) {
            RunnableAction bannerAd = Actions.run(new Runnable() {
                @Override
                public void run() {
                    game.adService.showBanner();
                }
            });
            bannerAd.run();
        }

        if(!Assets.instance.mainMenuAssets.gameMusic.isPlaying() && game.prefs.hasMusic())
            Assets.instance.mainMenuAssets.gameMusic.play();

        SPAWN_DISTANCE=0;
        groundOffset=0;
        updatePrefs=false;
        pause = false;
        gotoMain=false;
        goToPlay=false;
        newBestTime=false;
        tapToContinuePressed=false;
        timer=0;
        game.gameCam.update();
        this.level = new Level();
        multiplexer = new InputMultiplexer();

        if(game.prefs.getFromSettings())
          level.load(game.prefs.getLevelSelected());
        else
            level.load(game.prefs.getLevel());

        SPAWN_DISTANCE = Reader.levelMaps.width - Reader.levelMaps.spawnDistance;
        gamePort = new ExtendViewport(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT, game.gameCam);

        if(game.prefs.getRevived())
            player = new Player(game.prefs.getSpawns().x,
                    game.prefs.getSpawns().y, Constants.PLAYER_RADIUS);
        else {
            levelPlayedTime=0;
            player = new Player(SPAWN_DISTANCE - Constants.PLAYER_RADIUS / 2,
                    Constants.VIEWPORT_HEIGHT / 2 - Constants.PLAYER_RADIUS / 2, Constants.PLAYER_RADIUS);
            game.prefs.setSpawns(SPAWN_DISTANCE - Constants.PLAYER_RADIUS / 2,Constants.VIEWPORT_HEIGHT
                    / 2 - Constants.PLAYER_RADIUS / 2);
        }

        princess = new Princess(Reader.levelMaps.princess.x,Reader.levelMaps.princess.y,Constants.PLAYER_RADIUS);
        levelWidth = princess.x;
        this.stageBackground = new PlayScreenStageBackground(player);
        this.stageUI = new PlayScreenStageUI(player);
        onscreenControls = new OnscreenControls(player,this);
        multiplexer.addProcessor(stageUI);
        multiplexer.addProcessor(onscreenControls);
        collisions = new Collisions(player, princess, level,game);
        collisions.determineBounds();
        backgroundWidth = (collisions.rightBoundary-collisions.leftBoundary);

        t= (2200 -(player.x+gamePort.getWorldWidth()/3))*5/7;

        pauseWindow = new Window("PAUSED",Assets.instance.skinAssets.skin,"default");
        pauseWindow.getTitleLabel().setAlignment(Align.center);
        pauseWindow.setSize(500,500);
        pauseWindow.setPosition(500,300);
        pauseWindow.setMovable(false);
        continueGame = new TextButton("RESUME",Assets.instance.skinAssets.skin,"round");
        exitGame = new TextButton("EXIT GAME",Assets.instance.skinAssets.skin,"round");


        continueGame.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pause = false;
            }
        });

        exitGame.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.mainMenuScreen);
            }
        });

        pauseWindow.add(continueGame).size(1.5f*continueGame.getWidth(),1.5f*continueGame.getHeight()).padBottom(30);
        pauseWindow.row();
        pauseWindow.add(exitGame).size(1.5f*exitGame.getWidth(),1.5f*exitGame.getHeight());


        tapToContinue = new Image(Assets.instance.gameOverAssets.tapToContinue);
        tapToContinue.setOrigin(tapToContinue.getWidth()/2,tapToContinue.getHeight()/2);
        tapToContinue.setSize(tapToContinue.getWidth()/1.5f,tapToContinue.getHeight()/1.5f);

        tapToContinue.setPosition(350,200);

        tapToContinue.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                tapToContinuePressed=true;
                if (game.adService != null) {
                    RunnableAction playWooshAction = Actions.run(new Runnable() {
                        @Override
                        public void run() {
                            game.adService.showOrLoadInterstital();
                        }
                    });
                    playWooshAction.run();
                }
                if(game.prefs.getFromSettings()){
                    gotoMain=true;
                }
                else {
                    Reader.load(game.prefs.getLevel());
                    playingLevel = game.prefs.getLevel();
                    game.prefs.setRevived(false);
                    goToPlay=true;
                }
            }
        });
        tapToContinue.setVisible(false);


        stageUI.addActor(tapToContinue);
        stageUI.addActor(pauseWindow);
        Gdx.input.setInputProcessor(multiplexer);


    }


    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 0.3f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

     //   Gdx.app.log(""," "+ Gdx.graphics.getFramesPerSecond());
    //    Gdx.app.log("" , " "+ glProfiler.getDrawCalls() + " " + glProfiler.getCalls()+ " " + glProfiler.getShaderSwitches() + " "
     //   +glProfiler.getTextureBindings() + " " + glProfiler.getVertexCount());
     //   glProfiler.reset();

        if(delta==0)
            delta=.001f;

        if(!zoomFinished)
            zoomGame(delta);
        else
            renderGame(delta);

    }

    private void renderGame(float delta){
        game.spriteBatch.setShader(null);
        pauseWindow.setVisible(pause);
        if (player.collisionNumber < Constants.PLAYER_HIT_LIMIT) {
            if(!princess.isSaved) {
                stageBackground.getViewport().apply();
                stageBackground.act(delta);
                stageBackground.draw();

                gamePort.apply();

                game.gameCam.position.lerp(new Vector3(player.x + gamePort.getWorldWidth() / 3, player.y, 0), 0.1f);
                game.gameCam.update();

                game.spriteBatch.setProjectionMatrix(game.gameCam.combined);
                game.spriteBatch.begin();
                level.render(game.spriteBatch);
                player.render(game.spriteBatch);
                princess.render(game.spriteBatch);
                game.spriteBatch.end();
                onscreenControls.render(game.spriteBatch);

                if (!pause)
                    collisions.update(delta);



                stageUI.getViewport().apply();

                stageUI.act(delta);
                stageUI.draw();
            }
            else
            {
                if(!updatePrefs)
                    updatePreferences();

                pause=true;
                pauseWindow.setVisible(false);
                timer = timer + delta;

                if(Assets.instance.mainMenuAssets.gameMusic.isPlaying())
                    Assets.instance.mainMenuAssets.gameMusic.pause();

                gamePort.apply();
                game.spriteBatch.setProjectionMatrix(game.gameCam.combined);
                game.gameCam.position.x = player.x;
                game.gameCam.position.y = player.y;
                game.gameCam.zoom = 0.7f;
                game.gameCam.update();
                game.spriteBatch.begin();
               // game.spriteBatch.draw(Assets.instance.mainMenuAssets.mainMenuBackground, groundOffset, 0,
                //        backgroundWidth+500, 400);
                game.spriteBatch.draw(Assets.instance.gameOverAssets.levelCompleted,player.x - 128,player.y + 50,256,40);

                if(newBestTime)
                    game.spriteBatch.draw(Assets.instance.gameOverAssets.bestTime,player.x - 160,player.y - 30,64,50);


                player.render(game.spriteBatch);
                princess.render(game.spriteBatch);
                game.spriteBatch.end();
                if(timer>2) {

                    tapToContinue.setVisible(true);

                }
                stageUI.getViewport().apply();

                stageUI.act(delta);
                stageUI.draw();

                if(tapToContinuePressed && !pauseCalled){
                    if(gotoMain)
                        game.setScreen(game.mainMenuScreen);
                    if(goToPlay)
                        game.setScreen(game.playScreenWithStage);
                }

            }
        }
        else{
            gamePort.apply();
            timer = timer + delta;
            delta = delta * 0.1f;

            if(timer < 1.5)
                collisions.update(delta);

            stageBackground.getViewport().apply();
            stageBackground.act(delta);
            stageBackground.draw();

            game.gameCam.position.x = player.x;
            game.gameCam.position.y = player.y;
            game.gameCam.zoom = 0.7f;
            game.gameCam.update();

            game.spriteBatch.setProjectionMatrix(game.gameCam.combined);
            game.spriteBatch.begin();
            level.render(game.spriteBatch);
            player.render(game.spriteBatch);
            princess.render(game.spriteBatch);
            game.spriteBatch.end();

            stageUI.getViewport().apply();

            stageUI.act(delta);
            stageUI.draw();

            if(timer > 2)
            {
                game.gameCam.zoom = 0.9f;
                game.gameCam.update();
            }
            if(timer > 3)
                game.setScreen(game.gameOverScreen);
        }

    }



    private void zoomGame(float delta){
        boolean zoomDone=false,posDone=false;
        stageBackground.getViewport().apply();
        stageBackground.act(delta);
        stageBackground.draw();
        gamePort.apply();
        game.spriteBatch.setProjectionMatrix(game.gameCam.combined);
        game.spriteBatch.begin();
        level.render(game.spriteBatch);
        player.render(game.spriteBatch);
        princess.render(game.spriteBatch);
        game.spriteBatch.end();
        collisions.update(delta);



        if(!pauseCalled)
            zoomTimer+=delta;

        if(zoomTimer>3) {
            initialZoom -= 5f * delta;
            initialX = initialX - 30;

            if (initialZoom < 1) {
                initialZoom = 1;
                zoomDone=true;
            }

            if(initialX < player.x + gamePort.getWorldWidth() / 3) {
                initialX = player.x + gamePort.getWorldWidth() / 3;
                posDone=true;
            }

            if(zoomDone&&posDone)
                zoomFinished=true;
        }

        game.gameCam.zoom=initialZoom;
        game.gameCam.position.set(initialX,player.y,0);
        game.gameCam.update();

    }

    public void updatePreferences(){

        int level;
        if(game.prefs.getFromSettings())
            level = game.prefs.getLevelSelected();
        else
            level = game.prefs.getLevel();

        if(game.prefs.getLevelTime(level).equals("00:00")) {
            NumberFormat f = new DecimalFormat("00");
            String time =  String.valueOf( f.format((int) ((levelPlayedTime)/ 60))
                    + ":" + String.valueOf(f.format((int)(levelPlayedTime)%60)));
            game.prefs.setLevelTime(time,level);
            newBestTime=true;
        }else{
            String oldTime = game.prefs.getLevelTime(level);
            float oldHigh =  (float) (Integer.parseInt(oldTime.substring(0,2)) *60) + (float) (Integer.parseInt(oldTime.substring(3,5))) ;

            if(levelPlayedTime < oldHigh)
            {
                NumberFormat f = new DecimalFormat("00");
                String newTime = String.valueOf( f.format((int) ((levelPlayedTime)/ 60)) + ":" + String.valueOf(f.format((levelPlayedTime)%60)));
                game.prefs.setLevelTime(newTime,level);
                newBestTime=true;
            }

        }

        if(!game.prefs.getFromSettings())
            game.prefs.increaseLevel();

        updatePrefs=true;
    }


    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height, false);
        onscreenControls.onscreenViewport.update(width, height, true);
        onscreenControls.recalculateButtonPositions();
        stageBackground.getViewport().update(width,height,true);
        stageUI.getViewport().update(width,height,true);

    }


    @Override
    public void hide() {
    }

    @Override
    public void pause() {
        pause=true;
        pauseCalled=true;
        if(game.prefs.hasMusic())
            Assets.instance.mainMenuAssets.gameMusic.pause();
    }


    @Override
    public void resume() {

        pause=false;
        pauseCalled=false;
        if(game.prefs.hasMusic())
            Assets.instance.mainMenuAssets.gameMusic.play();
    }

    @Override
    public void dispose() {
        stageBackground.dispose();
        stageUI.dispose();
//        glProfiler.disable();

    }
}