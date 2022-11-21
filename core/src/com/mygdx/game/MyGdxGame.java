package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MyGdxGame extends Game {
	public SpriteBatch batch;
	public BitmapFont font;
	public World world;
	public OrthographicCamera gamCam;
	public Box2DDebugRenderer debugRenderer;
	public Viewport scalePort;
	public Button button;
	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		button = new Button(this);
		gamCam = new OrthographicCamera();
		scalePort = new StretchViewport(Utils.width, Utils.height, gamCam);
		debugRenderer = new Box2DDebugRenderer();
		world = new World(new Vector2(0, -9.8f), true);
		this.setScreen(new MainScreen(this));
	}

	@Override
	public void render () {
		super.render();
//		gamCam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	@Override
	public void resize(int width, int height) {
		scalePort.update(width, height);
	}

	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
		world.dispose();
		button.dispose();
	}
}