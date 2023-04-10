package com.macofugames.balldeveloper.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.macofugames.balldeveloper.levelreader.Reader;

public class Constants {

    // common
    public static final String TEXTURE_ATLAS = "images/gamearts.atlas";
    public static final String LOADING_ATLAS = "images/loadingatlas.atlas";

    // onscreen controls
    public static final float ONSCREEN_CONTROLS_VIEWPORT_SIZE = 750;
    public static final float BUTTON_RADIUS = 80f;
    public static final Vector2 BUTTON_CENTER = new Vector2(63, 63);
    public static final float PAUSE_RADIUS = 64f;
    public static final Vector2 PAUSE_CENTER = new Vector2(31, 31);

    // loading screen
    public static final float LOADING_VIEWPORT_HEIGHT = 1080;
    public static final float LOADING_VIEWPORT_WIDTH = Gdx.graphics.getWidth()*LOADING_VIEWPORT_HEIGHT/Gdx.graphics.getHeight();

    // transition screen
    public static final float TRANSITION_VIEWPORT_HEIGHT = 1080;
    public static final float TRANSITION_VIEWPORT_WIDTH = Gdx.graphics.getWidth()*TRANSITION_VIEWPORT_HEIGHT/Gdx.graphics.getHeight();

    // game over screen
    public static final float GAMEOVER_VIEWPORT_HEIGHT = 1080;
    public static final float GAMEOVER_VIEWPORT_WIDTH = Gdx.graphics.getWidth()*GAMEOVER_VIEWPORT_HEIGHT/Gdx.graphics.getHeight();

    // Splash Screen
    public static final float SPLASH_VIEWPORT_HEIGHT = 1080;
    public static final float SPLASH_VIEWPORT_WIDTH = Gdx.graphics.getWidth()*SPLASH_VIEWPORT_HEIGHT/Gdx.graphics.getHeight();

    // Main Menu Screen
    public static final float MAINMENU_VIEWPORT_HEIGHT = 1080;
    public static final float MAINMENU_VIEWPORT_WIDTH = Gdx.graphics.getWidth()*MAINMENU_VIEWPORT_HEIGHT/Gdx.graphics.getHeight();

    // Instructions Screen
    public static final float INSTRUCTIONS_VIEWPORT_HEIGHT = 1080;
    public static final float INSTRUCTIONS_VIEWPORT_WIDTH = Gdx.graphics.getWidth()*INSTRUCTIONS_VIEWPORT_HEIGHT/Gdx.graphics.getHeight();

    // Play Screen Settings
    public static final float VIEWPORT_HEIGHT = 400;
    public static final float VIEWPORT_WIDTH = Gdx.graphics.getWidth()*VIEWPORT_HEIGHT/Gdx.graphics.getHeight();

    // player settings
    public static final float PLAYER_RADIUS = 13;
    public static final int CIRCLE_SEGMENTS = 1000;
    public static final float VELOCITY_INCREMENT = 16f;
    public static final float PLAYER_DAMPING = 0.93f;
    public static final float PLAYER_INV_MASS = 0.5f;
    public static final float PLAYER_BCOEF = 0.5f;
    public static final float PLAYER_MAXVELOCITY = 5000f;
    public static final float PLAYER_MINVELOCITY = 0.01f;
    public static final int PLAYER_HIT_LIMIT = 5;

    // LEVEL
    public static final int TOTAL_LEVEL=50;

}
