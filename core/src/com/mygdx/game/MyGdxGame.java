package com.mygdx.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MyGdxGame extends Game {
	public SpriteBatch batch;
	public PolygonSpriteBatch polyBatch;
	public BitmapFont font;
	public World world;
	public OrthographicCamera gamCam;
	public Box2DDebugRenderer debugRenderer;
	public Viewport scalePort;
	public Button button;
	public InputMultiplexer multiplexer;
	public MainScreen mainScreen;
	public GameScreen gameScreen;
	public PauseMenu pauseMenu;
	@Override
	public void create () {
		batch = new SpriteBatch();
		polyBatch = new PolygonSpriteBatch();
		font = new BitmapFont();
		multiplexer = new InputMultiplexer();
		Gdx.input.setInputProcessor(multiplexer);
		button = new Button(this);
		gamCam = new OrthographicCamera();
		scalePort = new StretchViewport(Utils.width, Utils.height, gamCam);
		debugRenderer = new Box2DDebugRenderer();
		world = new World(new Vector2(0, -9.8f), true);
		mainScreen = new MainScreen(this);
		this.setScreen(mainScreen);
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		scalePort.update(width, height);
		font.getData().setScale(width / 100f * 0.2f, height / 100f * 0.2f);
		button.resize(width, height);
	}

	@Override
	public void dispose () {
		button.dispose();
		super.dispose();
	}
}