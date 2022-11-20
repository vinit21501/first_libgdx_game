package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

public class MyGdxGame extends Game {
	public SpriteBatch batch;
	public BitmapFont font;
	public World world;
	public OrthographicCamera gamCam;
	public Box2DDebugRenderer debugRenderer;
	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		gamCam = new OrthographicCamera();
		gamCam.setToOrtho(true);
		debugRenderer = new Box2DDebugRenderer();
		world = new World(new Vector2(0, -9.8f), true);
		this.setScreen(new MainScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
		world.dispose();
	}
}