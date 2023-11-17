package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationLogger;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGdxGame extends Game {


	public AssetManager getManager() {
		return manager;
	}

	private AssetManager manager;
	@Override
	public void create() {
		manager= new AssetManager();
		manager.load("dino100.png",Texture.class);
		manager.load("roka.png",Texture.class);
		manager.load("suelo.png",Texture.class);
		manager.finishLoading();
		setScreen(new GameScreen(this));


	}
}