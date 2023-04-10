package com.macofugames.balldeveloper.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.Vector2;

public class Prefs {

    private Preferences pref;
    private boolean hasSound;
    private boolean hasMusic;
    private int currentLevel;
    private String[] levelTime = new String[Constants.TOTAL_LEVEL];
    private float spawnX;
    private float spawnY;
    private boolean isRevived;
    private int levelSelected;
    private boolean fromSettings;

    private static final String PREF_NAME = "GamePreferences";
    private static final String HAS_MUSIC = "hasMusic";
    private static final String HAS_SOUND = "hasSound";
    private static final String LEVEL = "level";
    private static final String PLAYERX = "spawnX";
    private static final String PLAYERY = "spawnY";
    private static final String PLAYER_REVIVED = "isPlayerRevived";
    private static final String SELECTED_LEVEL = "levelSelected";
    private static final String FROM_SETTINGS = "fromSettings";



    public Prefs(){

        pref = Gdx.app.getPreferences(PREF_NAME);
        hasMusic = pref.getBoolean(HAS_MUSIC,true);
        hasSound = pref.getBoolean(HAS_SOUND,true);
        currentLevel = pref.getInteger(LEVEL,1);
        spawnX = pref.getFloat(PLAYERX,0f);
        spawnY = pref.getFloat(PLAYERY,0f);
        isRevived = pref.getBoolean(PLAYER_REVIVED,false);
        levelSelected = pref.getInteger(SELECTED_LEVEL,1);
        fromSettings = pref.getBoolean(FROM_SETTINGS,false);

        for (int i=0;i<Constants.TOTAL_LEVEL;i++){
            levelTime[i] = pref.getString("Level " + (i+1), "00:00");
        }

    }


    public boolean getFromSettings(){
        return fromSettings;
    }

    public void setFromSettings(boolean fromSettings){
        this.fromSettings = fromSettings;
        pref.putBoolean(FROM_SETTINGS,fromSettings);
        pref.flush();
    }

    public int getLevelSelected(){
        return levelSelected;
    }

    public void setLevelSelected(int levelSelected){
        this.levelSelected = levelSelected;
        pref.putInteger(SELECTED_LEVEL,levelSelected);
        pref.flush();
    }

    public void setRevived(boolean isRevived){
        this.isRevived = isRevived;
        pref.putBoolean(PLAYER_REVIVED,isRevived);
        pref.flush();
    }

    public Vector2 getSpawns(){
        return  new Vector2(spawnX,spawnY);
    }

    public boolean getRevived(){
        return isRevived;
    }

    public void setSpawns(float x, float y){
        this.spawnX = x;
        this.spawnY = y;
        pref.putFloat(PLAYERX,spawnX);
        pref.putFloat(PLAYERY,spawnY);
        pref.flush();

    }

    public void setSound(boolean hasSound){
        this.hasSound=hasSound;
        pref.putBoolean(HAS_SOUND,hasSound);
        pref.flush();
    }

    public void setMusic(boolean hasMusic){
        this.hasMusic=hasMusic;
        pref.putBoolean(HAS_MUSIC,hasMusic);
        pref.flush();
    }

    public void setLevelTime(String levelTime, int i){
        this.levelTime[i-1]=levelTime;
        pref.putString("Level " + i, levelTime);
        pref.flush();
    }

    public String getLevelTime (int i){

        return levelTime[i-1];
    }


    public boolean hasSound(){
        return hasSound;
    }
    public boolean hasMusic(){
        return hasMusic;
    }

    public void increaseLevel(){
        currentLevel++;
        pref.putInteger(LEVEL,currentLevel);
        pref.flush();
    }

    public int getLevel(){
        return currentLevel;
    }

    public void resetLevel(){
        currentLevel=1;
        pref.putInteger(LEVEL,currentLevel);
        pref.flush();
    }

    public void resetTimes(){
        for (int i=0;i<Constants.TOTAL_LEVEL;i++){
            pref.putString("Level " + (i+1),"00:00");
            pref.flush();
        }

    }
}
