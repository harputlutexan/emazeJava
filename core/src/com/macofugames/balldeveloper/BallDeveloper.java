package com.macofugames.balldeveloper;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.macofugames.balldeveloper.screens.GameOverScreen;
import com.macofugames.balldeveloper.screens.InstructionScreen;
import com.macofugames.balldeveloper.screens.LoadingScreen;
import com.macofugames.balldeveloper.screens.MainMenuScreen;
import com.macofugames.balldeveloper.screens.PlayScreenWithStage;
import com.macofugames.balldeveloper.screens.SettingsScreen;
import com.macofugames.balldeveloper.screens.SplashScreen;
import com.macofugames.balldeveloper.util.AdService;
import com.macofugames.balldeveloper.util.Assets;
import com.macofugames.balldeveloper.util.Prefs;

public class BallDeveloper extends Game {

	public OrthographicCamera gameCam;
	public SpriteBatch spriteBatch;

	//screens
	public LoadingScreen loadingScreen;
	public SplashScreen splashScreen;
	public MainMenuScreen mainMenuScreen;
	public PlayScreenWithStage playScreenWithStage;
	public GameOverScreen gameOverScreen;
	public SettingsScreen settingsScreen;
	public InstructionScreen instructionScreen;

	// Preferences
	public Prefs prefs;

	public AdService adService;

	public BallDeveloper(AdService ads){
		this.adService=ads;
	}

	public BallDeveloper(){
	}



	@Override
	public void create () {

        Assets.instance.init();
        prefs = new Prefs();
		spriteBatch = new SpriteBatch();
		loadingScreen = new LoadingScreen(this);
		splashScreen = new SplashScreen(this);
		playScreenWithStage = new PlayScreenWithStage(this);
		mainMenuScreen = new MainMenuScreen(this);
		settingsScreen = new SettingsScreen(this);
		gameOverScreen = new GameOverScreen(this);
		instructionScreen = new InstructionScreen(this);
		gameCam = new OrthographicCamera();
		gameCam.setToOrtho(false);
		this.setScreen(splashScreen);
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		spriteBatch.dispose();
		loadingScreen.dispose();
		splashScreen.dispose();
		mainMenuScreen.dispose();
		playScreenWithStage.dispose();
		settingsScreen.dispose();
		gameOverScreen.dispose();
		instructionScreen.dispose();
	}
}
