package com.macofugames.balldeveloper.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.macofugames.balldeveloper.BallDeveloper;
import com.macofugames.balldeveloper.levelreader.Reader;
import com.macofugames.balldeveloper.util.Assets;
import com.macofugames.balldeveloper.util.Constants;
import static com.macofugames.balldeveloper.screens.PlayScreenWithStage.playingLevel;


public class SettingsScreen extends ScreenAdapter {

    private BallDeveloper game;
    private Stage stage;
    private Image instaIcon,backButton,aboutButton,followLabel,musiclabel,soundlabel,selectlabel,levellabel,
            minusButton,plusButton,soundSwitch,musicSwitch,playLabel,stripe,stripe2,stripe3,stripe4,stripe5,faceIcon,youtubeIcon;
    private Dialog aboutDialog;
    private TextButton backDialog;
    private int levelNumber;
    private Label aboutLabel;


    public SettingsScreen(BallDeveloper game){
        this.game = game;
    }

   @Override
    public void show(){

       if (game.adService != null) {
           RunnableAction bannerAd = Actions.run(new Runnable() {
               @Override
               public void run() {
                   game.adService.hideBanner();
               }
           });
           bannerAd.run();
       }

       levelNumber=game.prefs.getLevelSelected();
       stage = new Stage(new FitViewport(Constants.MAINMENU_VIEWPORT_WIDTH,Constants.MAINMENU_VIEWPORT_HEIGHT,game.gameCam));

       musiclabel = new Image(Assets.instance.settingsScreenAssets.musicLabel);
       musiclabel.setOrigin(musiclabel.getWidth()/2,musiclabel.getHeight()/2);
       musiclabel.setPosition(stage.getWidth()/2 - 2*musiclabel.getWidth()-250, stage.getHeight() - 5*musiclabel.getHeight() + 17 );


       if(game.prefs.hasMusic()) {
           Assets.instance.mainMenuAssets.gameMusic.play();
           musicSwitch = new Image(Assets.instance.settingsScreenAssets.switchOn);
       }
       else
           musicSwitch = new Image(Assets.instance.settingsScreenAssets.switchOff);

       musicSwitch.setOrigin(musicSwitch.getWidth()/2,musicSwitch.getHeight()/2);
       musicSwitch.setPosition(stage.getWidth()/2 + musicSwitch.getWidth()/2 -75, musiclabel.getY()) ;


       musicSwitch.addListener(new ClickListener(){
           @Override
           public void clicked(InputEvent event, float x, float y) {

               if(Assets.instance.mainMenuAssets.gameMusic.isPlaying())
               {Assets.instance.mainMenuAssets.gameMusic.stop();
                   musicSwitch.setDrawable(new TextureRegionDrawable( Assets.instance.settingsScreenAssets.switchOff));
                   game.prefs.setMusic(false);
               }
               else {
                   Assets.instance.mainMenuAssets.gameMusic.play();
                   musicSwitch.setDrawable(new TextureRegionDrawable( Assets.instance.settingsScreenAssets.switchOn));
                   game.prefs.setMusic(true);
               }
           }
       });

       stripe = new Image(Assets.instance.settingsScreenAssets.stripe);
       stripe.setOrigin(stripe.getWidth()/2,stripe.getHeight()/2);
       stripe.setSize(stage.getWidth()/1.05f,musicSwitch.getHeight()*1.75f);
       stripe.setPosition(stage.getWidth()/2 - stripe.getWidth()/2 ,stage.getHeight() - 5*musiclabel.getHeight() - 7);

       soundlabel = new Image(Assets.instance.settingsScreenAssets.soundLabel);
       soundlabel.setOrigin(soundlabel.getWidth()/2,soundlabel.getHeight()/2);
       soundlabel.setPosition(stage.getWidth()/2 - 2*musiclabel.getWidth() -250, stage.getHeight() - 7*musiclabel.getHeight() + 17);

       stripe2 = new Image(Assets.instance.settingsScreenAssets.stripe);
       stripe2.setOrigin(stripe2.getWidth()/2,stripe2.getHeight()/2);
       stripe2.setSize(stage.getWidth()/1.05f,musicSwitch.getHeight()*1.75f);
       stripe2.setPosition(stage.getWidth()/2 - stripe2.getWidth()/2 ,stage.getHeight() - 7f*musiclabel.getHeight() - 7);

       if(game.prefs.hasSound()) {
           soundSwitch = new Image(Assets.instance.settingsScreenAssets.switchOn);
       }
       else
           soundSwitch = new Image(Assets.instance.settingsScreenAssets.switchOff);

       soundSwitch.setOrigin(soundSwitch.getWidth()/2,soundSwitch.getHeight()/2);
       soundSwitch.setPosition(stage.getWidth()/2 + soundSwitch.getWidth()/2 - 75, soundlabel.getY()) ;

       soundSwitch.addListener(new ClickListener(){
           @Override
           public void clicked(InputEvent event, float x, float y) {


               if(game.prefs.hasSound())
               {
                   soundSwitch.setDrawable(new TextureRegionDrawable( Assets.instance.settingsScreenAssets.switchOff));
                   game.prefs.setSound(false);
               }
               else {
                   soundSwitch.setDrawable(new TextureRegionDrawable( Assets.instance.settingsScreenAssets.switchOn));
                   game.prefs.setSound(true);
               }

           }
       });


       selectlabel = new Image(Assets.instance.settingsScreenAssets.selectLabel);
       selectlabel.setOrigin(selectlabel.getWidth()/2,selectlabel.getHeight()/2);
       selectlabel.setPosition(stage.getWidth()/2 - 2*musiclabel.getWidth()-250
               , stage.getHeight() - 9.5f*musiclabel.getHeight() + 30);



       minusButton = new Image(Assets.instance.settingsScreenAssets.minusButton);
       minusButton.setOrigin(minusButton.getWidth()/2,minusButton.getHeight()/2);
       minusButton.setPosition(stage.getWidth()/2 + minusButton.getWidth()/2 - 65, stage.getHeight() - 9.5f*musiclabel.getHeight() + 27);
       minusButton.setSize(1.75f * minusButton.getWidth(),1.5f*minusButton.getHeight());

       minusButton.addListener(new ClickListener(){
           @Override
           public void clicked(InputEvent event, float x, float y) {

               if(levelNumber > 1)
                   levelNumber -=1;
               else
                   levelNumber=1;
           }
       });


       levellabel = new Image(Assets.instance.settingsScreenAssets.levelLabel);
       levellabel.setOrigin(levellabel.getWidth()/2,levellabel.getHeight()/2);
       levellabel.setPosition(stage.getWidth()/2 + 2*minusButton.getWidth() - 90  , stage.getHeight() - 9.5f*musiclabel.getHeight()+27);
       levellabel.setSize(levellabel.getWidth()- 75,minusButton.getHeight());

       stripe3 = new Image(Assets.instance.settingsScreenAssets.stripe);
       stripe3.setOrigin(stripe3.getWidth()/2,stripe3.getHeight()/2);
       stripe3.setSize(stage.getWidth()/1.05f,levellabel.getHeight()*1.25f);
       stripe3.setPosition(stage.getWidth()/2 - stripe3.getWidth()/2 ,stage.getHeight() - 9.5f*musiclabel.getHeight() + 20 - 7);

       plusButton = new Image(Assets.instance.settingsScreenAssets.plusButton);
       plusButton.setOrigin(plusButton.getWidth()/2,plusButton.getHeight()/2);
       plusButton.setPosition(stage.getWidth()/2 + minusButton.getWidth() + levellabel.getWidth()
               + plusButton.getWidth()*1.5f - 65  , stage.getHeight() - 9.5f*musiclabel.getHeight()+27);
       plusButton.setSize(1.75f*plusButton.getWidth(),1.5f*plusButton.getHeight());

       plusButton.addListener(new ClickListener(){
           @Override
           public void clicked(InputEvent event, float x, float y) {

               if(levelNumber < game.prefs.getLevel() )//Constants.TOTAL_LEVEL game.prefs.getLevel()
                   levelNumber += 1;
               else
                   levelNumber=game.prefs.getLevel();}
       });

       playLabel = new Image(Assets.instance.settingsScreenAssets.playLabel);
       playLabel.setOrigin(playLabel.getWidth()/2,playLabel.getHeight()/2);
       playLabel.setPosition(stage.getWidth()/2 + minusButton.getWidth() + levellabel.getWidth()
               + plusButton.getWidth()*3f  - 65   , stage.getHeight() - 9.5f*musiclabel.getHeight()+30);


       playLabel.addListener(new ClickListener(){
           @Override
           public void clicked(InputEvent event, float x, float y) {
               game.prefs.setLevelSelected(levelNumber);
               game.prefs.setFromSettings(true);
               Reader.load(game.prefs.getLevelSelected());
               playingLevel=game.prefs.getLevelSelected();
               game.prefs.setRevived(false);
               game.setScreen(game.playScreenWithStage);
           }
       });

       followLabel = new Image(Assets.instance.settingsScreenAssets.followLabel);
       followLabel.setOrigin(followLabel.getWidth()/2,followLabel.getHeight()/2);
       followLabel.setPosition(stage.getWidth()/2 - 2*musiclabel.getWidth()-250, stage.getHeight() - 12*musiclabel.getHeight() + 45);

       instaIcon = new Image(Assets.instance.settingsScreenAssets.instaIcon);
       instaIcon.setOrigin(instaIcon.getWidth()/2,instaIcon.getHeight()/2);
       instaIcon.setPosition(stage.getWidth()/2 + instaIcon.getWidth()*2 -250, stage.getHeight() - 12*musiclabel.getHeight()+40);

       instaIcon.addListener(new ClickListener(){
           @Override
           public void clicked(InputEvent event, float x, float y) {
               Gdx.net.openURI("https://www.instagram.com/macofugames");

           }

       });

       faceIcon = new Image(Assets.instance.settingsScreenAssets.faceIcon);
       faceIcon.setOrigin(faceIcon.getWidth()/2,faceIcon.getHeight()/2);
       faceIcon.setPosition(instaIcon.getX() + 150, instaIcon.getY());

       faceIcon.addListener(new ClickListener(){
           @Override
           public void clicked(InputEvent event, float x, float y) {
               Gdx.net.openURI("https://www.facebook.com/macofugames");
           }
       });

       youtubeIcon = new Image(Assets.instance.settingsScreenAssets.youtubeIcon);
       youtubeIcon.setOrigin(youtubeIcon.getWidth()/2,youtubeIcon.getHeight()/2);
       youtubeIcon.setPosition(faceIcon.getX()+150,faceIcon.getY());

       youtubeIcon.addListener(new ClickListener(){
           @Override
           public void clicked(InputEvent event, float x, float y) {
               Gdx.net.openURI("https://www.youtube.com/channel/UCIeRjLmq_m5BgH7NndCLFNQ");
           }
       });

       stripe4 = new Image(Assets.instance.settingsScreenAssets.stripe);
       stripe4.setOrigin(stripe4.getWidth()/2,stripe4.getHeight()/2);
       stripe4.setSize(stage.getWidth()/1.05f,instaIcon.getHeight()*1.25f);
       stripe4.setPosition(stage.getWidth()/2 - stripe4.getWidth()/2 ,stage.getHeight() - 12f*musiclabel.getHeight() +28);

       aboutButton = new Image(Assets.instance.settingsScreenAssets.aboutButton);
       aboutButton.setOrigin(aboutButton.getWidth()/2,aboutButton.getHeight()/2);
       aboutButton.setPosition(stage.getWidth()/2 - aboutButton.getWidth()/2,200);


       stripe5 = new Image(Assets.instance.settingsScreenAssets.stripe);
       stripe5.setOrigin(stripe4.getWidth()/2,stripe4.getHeight()/2);
       stripe5.setSize(stage.getWidth()/1.05f,instaIcon.getHeight()*1.25f);
       stripe5.setPosition(stage.getWidth()/2 - stripe4.getWidth()/2 ,aboutButton.getY() - 30);

       backDialog = new TextButton("Back",Assets.instance.skinAssets.skin,"round");
       backDialog.addListener(new ClickListener(){
           @Override
           public void clicked(InputEvent event, float x, float y) {

               aboutDialog.setVisible(false);

           }
       });

       aboutDialog = new Dialog("About",Assets.instance.skinAssets.skin,"default");
       aboutDialog.getTitleLabel().setAlignment(Align.center);
       aboutDialog.getButtonTable().add(backDialog).size(1.5f*backDialog.getWidth(),1.5f*backDialog.getHeight());
       aboutDialog.setSize(900,700);
       aboutDialog.setPosition(stage.getWidth()/2 -650 ,stage.getHeight()/2 -350 );
       aboutDialog.setVisible(false);
       aboutDialog.setMovable(false);
       aboutDialog.toFront();

       aboutLabel = new Label("\n\n CREATED BY MACOFU GAMES @ 2019 \n\n\n All Rights Reserved .\n\n\n Powered by Libgdx\n\n",Assets.instance.skinAssets.skin,"title");
       aboutLabel.setFontScale(1.5f);
       aboutDialog.text(aboutLabel);

       aboutButton.addListener(new ClickListener(){

           @Override
           public void clicked(InputEvent event, float x, float y) {
               aboutDialog.setVisible(true);
           }

       });

       backButton = new Image(Assets.instance.settingsScreenAssets.backButton);
       backButton.setOrigin(backButton.getWidth()/2,backButton.getHeight()/2);
       backButton.setPosition(50,stage.getHeight()- 2f*backButton.getHeight());

       backButton.addListener(new ClickListener(){
           @Override
           public void clicked(InputEvent event, float x, float y) {
               game.setScreen(game.mainMenuScreen);
           }
       });


       stage.addActor(stripe);
       stage.addActor(stripe2);
       stage.addActor(stripe3);
       stage.addActor(stripe4);
       stage.addActor(stripe5);
       stage.addActor(musiclabel);
       stage.addActor(musicSwitch);
       stage.addActor(soundlabel);
       stage.addActor(soundSwitch);
       stage.addActor(selectlabel);
       stage.addActor(minusButton);
       stage.addActor(levellabel);
       stage.addActor(plusButton);
       stage.addActor(playLabel);
       stage.addActor(followLabel);
       stage.addActor(instaIcon);
       stage.addActor(faceIcon);
       stage.addActor(youtubeIcon);
       stage.addActor(aboutButton);
       stage.addActor(backButton);
       stage.addActor(aboutDialog);

       Gdx.input.setInputProcessor(stage);
    }


    @Override
    public void resize(int width, int heigth){
        stage.getViewport().update(width,heigth,true);

    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);


        game.spriteBatch.setProjectionMatrix(stage.getCamera().combined);
        game.spriteBatch.begin();

        game.spriteBatch.draw(Assets.instance.mainMenuAssets.mainMenuBackground, 0, 0,
                stage.getWidth(), stage.getHeight());
        game.spriteBatch.end();
        stage.act(delta);
        stage.draw();
        game.spriteBatch.begin();
        if(!aboutDialog.isVisible())
         Assets.instance.fontAssets.timerFont.draw(game.spriteBatch,Integer.toString(levelNumber),levellabel.getX() + levellabel.getWidth()/2 -15 ,levellabel.getY() + levellabel.getHeight()/2 + 13);

        game.spriteBatch.end();

    }

    @Override
    public void dispose(){
        stage.dispose();
    }
}
