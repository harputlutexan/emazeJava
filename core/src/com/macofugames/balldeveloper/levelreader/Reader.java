package com.macofugames.balldeveloper.levelreader;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.google.gson.Gson;

public class Reader {

	public static LevelMaps levelMaps;
	public static void load(int currentLevel){

		FileHandle fileHandle = Gdx.files.internal("levels/" + String.valueOf(currentLevel) + ".txt");
		String level = fileHandle.readString();
		Gson gson = new Gson();
		levelMaps = gson.fromJson(level, LevelMaps.class);
	}

}