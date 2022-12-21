package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.awt.*;
import java.awt.geom.RectangularShape;
import java.io.Serializable;

public class GameScreen implements Screen, Serializable {
    private static MyGdxGame myGame;
    private static TextureRegion backGround;
    private Missile missile;
    private static Terrain platform;
    private Tank player1;
    private Tank player2;
    private static ButtonCreator buttonCreator;
    private static Touchpad.TouchpadStyle touchPadStyle, moveTouchPadStyle;
    private static Skin touchPadSkin;
    private static Touchpad touchpad, moveTouchPad;
    private static Stage stage;
    private static Table table;
    private static boolean fired, turn, healthPlayer1, healthPlayer2;
    private boolean firedS, turnS;
    private static TextureRegion progressBackground, progressKnob, progressFuel;
    private static Body box;
    public GameScreen(MyGdxGame myGame, int playerType1, int playerType2) {
        fired = false;
        turn = false;
        healthPlayer1 = false;
        healthPlayer2 = false;
        GameScreen.myGame = myGame;
        platform = new Terrain();
        progressBackground = new TextureRegion(new Texture("BUTTON/ProgressBar.png"));
        progressKnob = new TextureRegion(new Texture("BUTTON/ProgressBarKnob.png"));
        progressFuel = new TextureRegion(new Texture("BUTTON/fuelProgressBar.png"));
        player1 = new Tank(100, 0, playerType1, true, "player1");
        player2 = new Tank(-300, 0, playerType2, false, "player2");
        backGround = new TextureRegion(new Texture("BACKGROUND/bg6.png"));
        missile = new Missile();
        buttonCreator = myGame.getButtonCreator();
        Utils.setAccumulator(0.005f);
        touchPadSkin = new Skin();
        touchPadSkin.add("background1", new Texture("BUTTON/TouchpadButton1.png"));
        touchPadSkin.add("background2", new Texture("BUTTON/TouchpadButton2.png"));
        touchPadSkin.add("knob", new Texture("BUTTON/TouchpadKnob.png"));
        touchPadStyle = new Touchpad.TouchpadStyle(touchPadSkin.getDrawable("background1"), touchPadSkin.getDrawable("knob"));
        touchpad = new Touchpad(10, touchPadStyle);
        moveTouchPadStyle = new Touchpad.TouchpadStyle(touchPadSkin.getDrawable("background2"), touchPadSkin.getDrawable("knob"));
        moveTouchPad = new Touchpad(0, moveTouchPadStyle);
        moveTouchPad.setSize(150, 50);
        stage = new Stage();
        table = new Table();
        stage.addActor(table);
        BodyDef def = new BodyDef();
        def.position.set(-(Utils.getWidth() / 2) + 129, -Utils.getHeight() / 2 + 72);
        def.type = BodyDef.BodyType.StaticBody;
        box = myGame.getWorld().createBody(def);
        PolygonShape shap = new PolygonShape();
        shap.setAsBox(Utils.getWidth() - 128, Utils.getHeight());
        FixtureDef fix = new FixtureDef();
        fix.shape = shap;
        box.createFixture(fix);
        shap.dispose();
        table.add(touchpad).size(Gdx.graphics.getWidth() * 7 / 64f, Gdx.graphics.getHeight() * 7 / 36f).padRight(Gdx.graphics.getWidth() * 15 / 64f);
        table.add(buttonCreator.addFireButton()).size(Gdx.graphics.getWidth() * 6 / 64f, Gdx.graphics.getHeight() * 4 / 36f).padRight(Gdx.graphics.getWidth() * 15 / 64f);
        table.add(moveTouchPad).size(Gdx.graphics.getWidth() * 15 / 128f, Gdx.graphics.getHeight() * 7 / 72f);
        myGame.getMultiplexer().addProcessor(stage);
        platform.renderBody(myGame.getWorld());
    }

    public static void setFired(boolean fired) {
        GameScreen.fired = fired;
    }
    public static void setTurn() {
        turn = !turn;
    }
    public static void setPlayersHealth(boolean healthPlayer1, boolean healthPlayer2) {
        GameScreen.healthPlayer1 = healthPlayer1;
        GameScreen.healthPlayer2 = healthPlayer2;
    }

    @Override
    public void show() {
        buttonCreator.addPauseButton();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (healthPlayer1) {
            player1.updateHealth();
            healthPlayer1 = false;
            if (player1.getHealth() <= 0) {
                myGame.getWinScreen().set("PLAYER 1 WINS");
                myGame.setScreen(myGame.getWinScreen());
            }
        }
        if (healthPlayer2) {
            healthPlayer2 = false;
            player2.updateHealth();
            if (player2.getHealth() <= 0) {
                myGame.getWinScreen().set("PLAYER 2 WINS");
                myGame.setScreen(myGame.getWinScreen());
            }
        }
        myGame.getBatch().setProjectionMatrix(myGame.getGamCam().combined);
        myGame.getGamCam().update();
        myGame.getBatch().begin();
        myGame.getBatch().draw(backGround, -Utils.getWidth() / 2f, -Utils.getHeight() / 2f, Utils.getWidth(), Utils.getHeight());
        myGame.getBatch().end();
        myGame.getPolyBatch().begin();
        platform.renderTexture(myGame.getPolyBatch());
        myGame.getPolyBatch().end();
        myGame.getBatch().begin();
        drawProgressBar(progressBackground, progressKnob, player1.getHealth(), 150, Utils.getHeight()/2f - 70);
        drawProgressBar(progressBackground, progressKnob, player2.getHealth(), -400, Utils.getHeight()/2f - 70);
        drawProgressBar(progressBackground, progressFuel, player1.getFuel(), 150, Utils.getHeight()/2f - 150);
        drawProgressBar(progressBackground, progressFuel, player2.getFuel(), -400, Utils.getHeight()/2f - 150);
        player1.update(myGame);
        player2.update(myGame);
        platform.update();
        if (!fired) {
            if (turn) player2.move(touchpad,  moveTouchPad.getKnobPercentX());
            else player1.move(touchpad, moveTouchPad.getKnobPercentX());
        } else {
            if (turn) missile.update(myGame, player2);
            else missile.update(myGame, player1);
        }
        moveTouchPad.draw(myGame.getBatch(),1);
        myGame.getBatch().end();
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        stage.act(delta);
        stage.draw();
        buttonCreator.render(delta);
        updateWorld();
        myGame.getDebugRenderer().render(myGame.getWorld(), myGame.getGamCam().combined);
    }
    public void drawProgressBar(TextureRegion textureRegion, TextureRegion texture, float i, float x, float y) {
        myGame.getBatch().draw(textureRegion, x, y, 400, 50);
        myGame.getBatch().draw(texture, x + (20f / i), y, 400 * i / 100f, 50);
    }
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
        table.setBounds(0, -height / 3f, width, height);
        myGame.resize(width, height);
    }
    public void updateWorld() {
        float accuStep = Utils.getAccumulator();
        float frameTime = Math.min(Gdx.graphics.getDeltaTime(), 0.25f);
        accuStep += frameTime;
        while (accuStep >= Utils.getTimeStep()) {
            myGame.getWorld().step(Utils.getTimeStep(), Utils.getVelocityIterations(), Utils.getPositionIterations());
            accuStep -= Utils.getTimeStep();
        }
    }
    public void write() {
        this.firedS = GameScreen.fired;
        this.turnS = GameScreen.turn;
        missile.write();
    }
    public void read(MyGdxGame myGame) {
        GameScreen.fired = this.firedS;
        GameScreen.turn = this.turnS;
        player1.read();
        player2.read();
        missile.read();
        GameScreen.myGame = myGame;
        player1.render(myGame.getWorld());
        player2.render(myGame.getWorld());
        missile.render(myGame.getWorld());
    }
    public void destroyObject() {
        player1.dispose(myGame.getWorld());
        player2.dispose(myGame.getWorld());
        missile.dispose(myGame.getWorld());
    }
    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
    }
    @Override
    public void dispose() {
        myGame.getWorld().dispose();
        myGame.getDebugRenderer().dispose();
        myGame.getBatch().dispose();
        stage.dispose();
        myGame.getButtonCreator().dispose();
        myGame.getPolyBatch().dispose();
    }
}
