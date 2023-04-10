package com.macofugames.balldeveloper.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;


public class Assets implements Disposable, AssetErrorListener {

    public static final String TAG = Assets.class.getName();
    public static final Assets instance = new Assets();

    private AssetManager assetManager;

    public OnScreenControlAssets onScreenControlAssets;
    public LineAssets lineAssets;
    public LoadingPageAssets loadingPageAssets;
    public SplashScreenAssets splashScreenAssets;
    public MainMenuAssets mainMenuAssets;
    public PlayScreenStageAssets playScreenStageAssets;
    public PlayerAssets playerAssets;
    public CircleAssets circleAssets;
    public SkinAssets skinAssets;
    public SetttingsScreenAssets settingsScreenAssets;
    public GameOverAssets gameOverAssets;
    public SoundAssets soundAssets;
    public FontAssets fontAssets;




    private Assets(){}

    public AssetManager getAssetManager(){
        return this.assetManager;
    }

    public void init(){
        this.assetManager = new AssetManager();
        assetManager.setErrorListener(this);

        FileHandleResolver resolver = new InternalFileHandleResolver();
        assetManager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        assetManager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));


        FreetypeFontLoader.FreeTypeFontLoaderParameter loadingFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        loadingFont.fontFileName = "fonts/gomarice_no_continue.ttf";
        loadingFont.fontParameters.size = 64;
        loadingFont.fontParameters.color = Color.PURPLE;
        assetManager.load("loadingFont.ttf", BitmapFont.class, loadingFont);


        assetManager.load(Constants.LOADING_ATLAS,TextureAtlas.class);
        assetManager.load("sounds/splashsound.mp3", Sound.class);

        assetManager.finishLoading();

        TextureAtlas loadingAtlas = assetManager.get(Constants.LOADING_ATLAS);

        splashScreenAssets = new SplashScreenAssets(loadingAtlas);
        loadingPageAssets = new LoadingPageAssets(loadingAtlas);

        // load texture atlas
        assetManager.load(Constants.TEXTURE_ATLAS, TextureAtlas.class);

        // Load sounds and music
        assetManager.load("sounds/gamemusic.mp3", Music.class);
        assetManager.load("sounds/wallcoef1.mp3",Sound.class);
        assetManager.load("sounds/wallcoefbig.mp3",Sound.class);
        assetManager.load("sounds/ballcoefbig.mp3",Sound.class);
        assetManager.load("sounds/ballcoef1.mp3",Sound.class);
        assetManager.load("sounds/levelUp.mp3",Sound.class);
        assetManager.load("sounds/plane.mp3",Sound.class);
        assetManager.load("sounds/greenwalls.mp3",Sound.class);
        assetManager.load("sounds/checkin.mp3",Sound.class);

        // Load Skin
        assetManager.load("skins/uiskin.atlas", TextureAtlas.class);
        assetManager.load("skins/uiskin.json", Skin.class, new SkinLoader.SkinParameter("skins/uiskin.atlas"));

        FreetypeFontLoader.FreeTypeFontLoaderParameter timerFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        timerFont.fontFileName = "fonts/gomarice_no_continue.ttf";
        timerFont.fontParameters.size = 48;
        timerFont.fontParameters.color = Color.WHITE;
        assetManager.load("timerFont.ttf", BitmapFont.class, timerFont);

        FreetypeFontLoader.FreeTypeFontLoaderParameter timerFont2 = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        timerFont2.fontFileName = "fonts/gomarice_no_continue.ttf";
        timerFont2.fontParameters.size = 72;
        timerFont2.fontParameters.color = Color.PURPLE;
        assetManager.load("timerFont2.ttf", BitmapFont.class, timerFont2);


        FreetypeFontLoader.FreeTypeFontLoaderParameter levelFontSmall = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        levelFontSmall.fontFileName = "fonts/gomarice_no_continue.ttf";
        levelFontSmall.fontParameters.size = 32;
        levelFontSmall.fontParameters.color = Color.WHITE;
        assetManager.load("levelFontSmall.ttf", BitmapFont.class, levelFontSmall);


        FreetypeFontLoader.FreeTypeFontLoaderParameter levelFontMedium = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        levelFontMedium.fontFileName = "fonts/gomarice_no_continue.ttf";
        levelFontMedium.fontParameters.size = 40;
        levelFontMedium.fontParameters.color = Color.WHITE;
        assetManager.load("levelFontMedium.ttf", BitmapFont.class, levelFontMedium);

        FreetypeFontLoader.FreeTypeFontLoaderParameter levelFontBig = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        levelFontBig.fontFileName = "fonts/gomarice_no_continue.ttf";
        levelFontBig.fontParameters.size = 48;
        levelFontBig.fontParameters.color = Color.WHITE;
        assetManager.load("levelFontBig.ttf", BitmapFont.class, levelFontBig);

    }

    public void createGameAssets(){

        TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS);

        soundAssets = new SoundAssets();
        skinAssets = new SkinAssets();

        onScreenControlAssets = new OnScreenControlAssets(atlas);
        mainMenuAssets = new MainMenuAssets(atlas);
        settingsScreenAssets = new SetttingsScreenAssets(atlas);
        playScreenStageAssets = new PlayScreenStageAssets(atlas);
        playerAssets = new PlayerAssets(atlas);
        circleAssets = new CircleAssets(atlas);
        gameOverAssets = new GameOverAssets(atlas);
        lineAssets = new LineAssets(atlas);
        fontAssets = new FontAssets();

    }

    public class FontAssets{

        public final BitmapFont timerFont;
        public final BitmapFont timerFont2;
        public final BitmapFont levelFontSmall;
        public final BitmapFont levelFontMedium;
        public final BitmapFont levelFontBig;

        public FontAssets(){
            timerFont = assetManager.get("timerFont.ttf",BitmapFont.class);
            timerFont.setUseIntegerPositions(false);
            timerFont2 = assetManager.get("timerFont2.ttf",BitmapFont.class);
            timerFont2.setUseIntegerPositions(false);
            levelFontSmall = assetManager.get("levelFontSmall.ttf",BitmapFont.class);
            levelFontSmall.setUseIntegerPositions(false);
            levelFontMedium = assetManager.get("levelFontMedium.ttf",BitmapFont.class);
            levelFontMedium.setUseIntegerPositions(false);
            levelFontBig = assetManager.get("levelFontBig.ttf",BitmapFont.class);
            levelFontBig.setUseIntegerPositions(false);

        }
    }

    public class SoundAssets{
        public final Sound wallCoef1;
        public final Sound wallCoefBig;
        public final Sound ballCoefBig;
        public final Sound ballCoef1;
        public final Sound levelUp;
        public final Sound planeHit;
        public final Sound greenWalls;
        public final Sound checkIn;
        public SoundAssets(){

            wallCoef1 = assetManager.get("sounds/wallcoef1.mp3",Sound.class);
            wallCoefBig = assetManager.get("sounds/wallcoefbig.mp3",Sound.class);
            ballCoefBig = assetManager.get("sounds/ballcoefbig.mp3",Sound.class);
            ballCoef1 = assetManager.get("sounds/ballcoef1.mp3",Sound.class);
            levelUp = assetManager.get("sounds/levelUp.mp3",Sound.class);
            planeHit = assetManager.get("sounds/plane.mp3",Sound.class);
            greenWalls = assetManager.get("sounds/greenwalls.mp3",Sound.class);
            checkIn = assetManager.get("sounds/checkin.mp3",Sound.class);
        }
    }

    public class GameOverAssets{

        public final TextureAtlas.AtlasRegion continueButton;
        public final TextureAtlas.AtlasRegion giveUpButton;
        public final TextureAtlas.AtlasRegion levelCompleted;
        public final TextureAtlas.AtlasRegion tapToContinue;
        public final TextureAtlas.AtlasRegion bestTime;

        public GameOverAssets(TextureAtlas atlas){

           continueButton = atlas.findRegion("continuelabel");
           giveUpButton = atlas.findRegion("giveuplabel");
           levelCompleted = atlas.findRegion("levelcompleted");
           tapToContinue = atlas.findRegion("taptocontinue");
           bestTime = atlas.findRegion("besttime");

        }
    }

    public class SetttingsScreenAssets{

        public final TextureAtlas.AtlasRegion instaIcon;
        public final TextureAtlas.AtlasRegion aboutButton;
        public final TextureAtlas.AtlasRegion backButton;
        public final TextureAtlas.AtlasRegion followLabel;
        public final TextureAtlas.AtlasRegion levelLabel;
        public final TextureAtlas.AtlasRegion minusButton;
        public final TextureAtlas.AtlasRegion musicLabel;
        public final TextureAtlas.AtlasRegion plusButton;
        public final TextureAtlas.AtlasRegion selectLabel;
        public final TextureAtlas.AtlasRegion soundLabel;
        public final TextureAtlas.AtlasRegion switchOff;
        public final TextureAtlas.AtlasRegion switchOn;
        public final TextureAtlas.AtlasRegion playLabel;
        public final TextureAtlas.AtlasRegion stripe;
        public final TextureAtlas.AtlasRegion faceIcon;
        public final TextureAtlas.AtlasRegion youtubeIcon;

        public SetttingsScreenAssets(TextureAtlas atlas){
            instaIcon = atlas.findRegion("instagra_icon");
            aboutButton = atlas.findRegion("aboutbutton");
            backButton = atlas.findRegion("backbutton");
            followLabel = atlas.findRegion("followlabel");
            levelLabel = atlas.findRegion("levellabel");
            minusButton = atlas.findRegion("minusbutton");
            musicLabel = atlas.findRegion("musiclabel");
            plusButton = atlas.findRegion("plusbutton");
            selectLabel = atlas.findRegion("selectlabel");
            soundLabel = atlas.findRegion("soundlabel");
            switchOff = atlas.findRegion("switchOff");
            switchOn = atlas.findRegion("switchOn");
            playLabel = atlas.findRegion("playlabel");
            stripe = atlas.findRegion("stripe");
            faceIcon = atlas.findRegion("facebook_icon");
            youtubeIcon=atlas.findRegion("youtube_icon");

        }
    }

    public class SkinAssets {

        public Skin skin;

        public SkinAssets(){
            skin = assetManager.get("skins/uiskin.json",Skin.class);

        }

    }

    public class CircleAssets {

        public final TextureAtlas.AtlasRegion redCircle;
        public final TextureAtlas.AtlasRegion greenCircle;
        public final TextureAtlas.AtlasRegion yellowCircle;
        public final TextureAtlas.AtlasRegion blackCircle;
        public final TextureAtlas.AtlasRegion checkPoint;

        public CircleAssets(TextureAtlas atlas){
            redCircle = atlas.findRegion("redcircle");
            greenCircle=atlas.findRegion("greencircle");
            yellowCircle = atlas.findRegion("yellowcircle");
            blackCircle = atlas.findRegion("blackcircle");
            checkPoint = atlas.findRegion("checkpoint");

        }

    }

    public class PlayerAssets{
        public final TextureAtlas.AtlasRegion mainBlueRight;
        public final TextureAtlas.AtlasRegion woundedPlayer;
        public final TextureAtlas.AtlasRegion mainBlueLeft;
        public final TextureAtlas.AtlasRegion ouch;
        public final TextureAtlas.AtlasRegion checkedIn;
        public final TextureAtlas.AtlasRegion princess;
        public final TextureAtlas.AtlasRegion hearts;


        public PlayerAssets(TextureAtlas atlas){

            mainBlueRight = atlas.findRegion("mainblueright2");
            mainBlueLeft = atlas.findRegion("mainblueleft2");
            woundedPlayer = atlas.findRegion("woundedblue");
            ouch = atlas.findRegion("ouchh");
            checkedIn = atlas.findRegion("checkedin");
            princess = atlas.findRegion("princessblue");
            hearts = atlas.findRegion("heart");

        }
    }


    public class PlayScreenStageAssets{

        public final TextureAtlas.AtlasRegion progressBarFrame;
        public final TextureAtlas.AtlasRegion progressBar;
        public final TextureAtlas.AtlasRegion healthBarFrame;
        public final TextureAtlas.AtlasRegion healthBar;
        public final TextureAtlas.AtlasRegion themeOneMiddle;
        public final TextureAtlas.AtlasRegion themeOneleft;
        public final TextureAtlas.AtlasRegion themeOneRight;
        public final TextureAtlas.AtlasRegion themeTwoMiddle;
        public final TextureAtlas.AtlasRegion themeTwoleft;
        public final TextureAtlas.AtlasRegion themeTwoRight;
        public final TextureAtlas.AtlasRegion themeThreeMiddle;
        public final TextureAtlas.AtlasRegion themeThreeleft;
        public final TextureAtlas.AtlasRegion themeThreeRight;
        public final TextureAtlas.AtlasRegion themeFourMiddle;
        public final TextureAtlas.AtlasRegion themeFourleft;
        public final TextureAtlas.AtlasRegion themeFourRight;

        public PlayScreenStageAssets(TextureAtlas atlas){


            progressBarFrame = atlas.findRegion("progress");
            progressBar = atlas.findRegion("progress2");
            healthBarFrame = atlas.findRegion("healthbar");
            healthBar = atlas.findRegion("healthbar2");
            themeOneMiddle = atlas.findRegion("theme1mid");
            themeOneleft = atlas.findRegion("theme1left");
            themeOneRight = atlas.findRegion("theme1right");
            themeTwoMiddle = atlas.findRegion("theme2mid");
            themeTwoleft = atlas.findRegion("theme2left");
            themeTwoRight = atlas.findRegion("theme2right");
            themeThreeMiddle = atlas.findRegion("theme3mid");
            themeThreeleft = atlas.findRegion("theme3left");
            themeThreeRight = atlas.findRegion("theme3right");
            themeFourMiddle = atlas.findRegion("theme4mid");
            themeFourleft = atlas.findRegion("theme4left");
            themeFourRight = atlas.findRegion("theme4right");


        }
    }

    public class MainMenuAssets{
        public final Music gameMusic;
        public final TextureAtlas.AtlasRegion playButton;
        public final TextureAtlas.AtlasRegion soundOnButton;
        public final TextureAtlas.AtlasRegion settingsButton;
        public final TextureAtlas.AtlasRegion leaderboardButton;
        public final TextureAtlas.AtlasRegion noadsButton;
        public final TextureAtlas.AtlasRegion emazeText;
        public final TextureAtlas.AtlasRegion ballText;
        public final TextureAtlas.AtlasRegion soundOffButton;
        public final TextureAtlas.AtlasRegion instructionsButton;
        public final TextureAtlas.AtlasRegion instructionsPicture;
        public final TextureAtlas.AtlasRegion mainMenuBackground;
        public final TextureAtlas.AtlasRegion zoomOutBackground;


        public MainMenuAssets(TextureAtlas atlas){
           playButton = atlas.findRegion("play");
           soundOnButton = atlas.findRegion("sound");
           settingsButton = atlas.findRegion("settings");
           leaderboardButton = atlas.findRegion("leaderboard");
           noadsButton = atlas.findRegion("noads");
           emazeText = atlas.findRegion("emaze");
           ballText = atlas.findRegion("ball");
           soundOffButton = atlas.findRegion("soundoff");
           instructionsButton = atlas.findRegion("instructionsicon");
           instructionsPicture = atlas.findRegion("instructions");
           gameMusic = assetManager.get("sounds/gamemusic.mp3", Music.class);
           mainMenuBackground = atlas.findRegion("mainmenubackground");
           zoomOutBackground = atlas.findRegion("zoomoutbackground");

        }

    }


    public class SplashScreenAssets{
        public final TextureAtlas.AtlasRegion logo;
        public final TextureAtlas.AtlasRegion logoText;
        public final Sound introSound;

        public SplashScreenAssets(TextureAtlas atlas){
            logo = atlas.findRegion("macofulogo");
            logoText = atlas.findRegion("copyrighttextwhite");
            introSound = assetManager.get("sounds/splashsound.mp3",Sound.class);
        }
    }

    public class LoadingPageAssets{

        public final TextureAtlas.AtlasRegion mainBlueRight;
        public final TextureAtlas.AtlasRegion princessBlue;
        public final TextureAtlas.AtlasRegion heart;
        public final TextureAtlas.AtlasRegion rect;
        public final TextureAtlas.AtlasRegion loadingBackground;

        public final BitmapFont loadingFont;

        public LoadingPageAssets(TextureAtlas atlas){
            mainBlueRight = atlas.findRegion("mainblueright2");
            princessBlue = atlas.findRegion("princessblue");
            heart = atlas.findRegion("heart");
            rect = atlas.findRegion("whitesquare");
            loadingFont = assetManager.get("loadingFont.ttf",BitmapFont.class);
           // loadingFont.getData(). setScale(1/86f);
            loadingFont.setUseIntegerPositions(false);
            loadingBackground = atlas.findRegion("mainmenubackground");
        }
    }

    public class LineAssets{

         public final TextureAtlas.AtlasRegion rectPixmap;
         public final TextureAtlas.AtlasRegion rectPixmapYellow;
         public final TextureAtlas.AtlasRegion rectPixmapBlack;
         public final TextureAtlas.AtlasRegion rectPixmapGreen;

        public LineAssets(TextureAtlas atlas){

            rectPixmap=atlas.findRegion("whitesquare");
            rectPixmapYellow = atlas.findRegion("yellowsquare");
            rectPixmapBlack = atlas.findRegion("blacksquare");
            rectPixmapGreen = atlas.findRegion("greensquare");

        }
    }

    public class OnScreenControlAssets{

        public final TextureAtlas.AtlasRegion moveRight;
        public final TextureAtlas.AtlasRegion moveLeft;
        public final TextureAtlas.AtlasRegion moveUp;
        public final TextureAtlas.AtlasRegion moveDown;
        public final TextureAtlas.AtlasRegion pauseButtonImage;


        public OnScreenControlAssets(TextureAtlas atlas){
            moveRight = atlas.findRegion("right");
            moveLeft = atlas.findRegion("left");
            moveDown = atlas.findRegion("down");
            moveUp = atlas.findRegion("up");
            pauseButtonImage = atlas.findRegion("pausebutton");

        }

    }


    @Override
    public void dispose() {
        assetManager.dispose();
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Couldn't load asset: " + asset.fileName, throwable);
    }



}
