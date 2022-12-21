package com.mygdx.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import static com.badlogic.gdx.Application.LOG_DEBUG;

public class MyGdxGame extends Game {
	private SpriteBatch batch;
	private PolygonSpriteBatch polyBatch;
	private BitmapFont font;
	private World world;
	private OrthographicCamera gamCam;
	private Box2DDebugRenderer debugRenderer;
	private Viewport scalePort;
	private ButtonCreator buttonCreator;
	private InputMultiplexer multiplexer;
	private MainScreen mainScreen;
	private GameScreen gameScreen;
	private PauseMenu pauseMenu;
	private LoadScreen loadScreen;
	private TankSelectionScreen tankSelectionScreen;
	public SpriteBatch getBatch() {
		return batch;
	}
	private Win winScreen;
	@Override
	public void create () {
		batch = new SpriteBatch();
		polyBatch = new PolygonSpriteBatch();
		font = new BitmapFont();
		multiplexer = new InputMultiplexer();
		Gdx.input.setInputProcessor(multiplexer);
		buttonCreator = new ButtonCreator(this);
		gamCam = new OrthographicCamera();
		winScreen = new Win(this);
		scalePort = new StretchViewport(Utils.getWidth(), Utils.getHeight(), gamCam);
		debugRenderer = new Box2DDebugRenderer();
		world = new World(new Vector2(0, -9.8f), true);
		world.setContactListener(new ContactListener() {
			@Override
			public void beginContact(Contact contact) {
				if (contact.getFixtureA().getUserData() != null && contact.getFixtureA().getUserData().toString().equals("missile")) {
					if (contact.getFixtureB().getUserData() != null){
						if (contact.getFixtureB().getUserData().toString().equals("player1")) {
							GameScreen.setPlayersHealth(true, false);
						}
						if (contact.getFixtureB().getUserData().toString().equals("player2")) {
							GameScreen.setPlayersHealth(false, true);
						}
					}
					Missile.setDestroyed(true);
				} else if (contact.getFixtureB().getUserData() != null && contact.getFixtureB().getUserData().toString().equals("missile")) {
					if (contact.getFixtureA().getUserData() != null){
						if (contact.getFixtureA().getUserData().toString().equals("player1")) {
							GameScreen.setPlayersHealth(true, false);
						}
						if (contact.getFixtureA().getUserData().toString().equals("player2")) {
							GameScreen.setPlayersHealth(false, true);
						}
					}
					Missile.setDestroyed(true);
				}
			}
			@Override
			public void endContact(Contact contact) {}
			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {}
			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {}
		});
		loadScreen = new LoadScreen(this);
		mainScreen = new MainScreen(this);
		tankSelectionScreen = TankSelectionScreen.getInstances(this);
//		Utils.readGame();
		this.setScreen(mainScreen);
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		scalePort.update(width, height);
		font.getData().setScale(width / 100f * 0.2f, height / 100f * 0.2f);
		buttonCreator.resize(width, height);
	}

	@Override
	public void dispose () {
		buttonCreator.dispose();
		super.dispose();
	}

	public PolygonSpriteBatch getPolyBatch() {
		return polyBatch;
	}
	public BitmapFont getFont() {
		return font;
	}

	public World getWorld() {
		return world;
	}

	public OrthographicCamera getGamCam() {
		return gamCam;
	}

	public Box2DDebugRenderer getDebugRenderer() {
		return debugRenderer;
	}

	public Viewport getScalePort() {
		return scalePort;
	}

	public ButtonCreator getButtonCreator() {
		return buttonCreator;
	}

	public InputMultiplexer getMultiplexer() {
		return multiplexer;
	}

	public MainScreen getMainScreen() {
		return mainScreen;
	}

	public void setMainScreen(MainScreen mainScreen) {
		this.mainScreen = mainScreen;
	}

	public GameScreen getGameScreen() {
		return gameScreen;
	}

	public void setGameScreen(GameScreen gameScreen) {
		this.gameScreen = gameScreen;
	}

	public PauseMenu getPauseMenu() {
		return pauseMenu;
	}

	public void setPauseMenu(PauseMenu pauseMenu) {
		this.pauseMenu = pauseMenu;
	}

	public LoadScreen getLoadScreen() {
		return loadScreen;
	}

	public void setLoadScreen(LoadScreen loadScreen) {
		this.loadScreen = loadScreen;
	}

	public TankSelectionScreen getTankSelectionScreen() {
		return tankSelectionScreen;
	}

	public Win getWinScreen() {
		return winScreen;
	}
}