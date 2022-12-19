package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;

public class GameScreen implements Screen {
    private static MyGdxGame myGame;
    private static TextureRegion backGround;
    private Missile missile;
    private final Terrain platform;
    private Tank player1;
    private Tank player2;
    private final ButtonCreator buttonCreator;
    private static Touchpad.TouchpadStyle touchPadStyle, moveTouchPadStyle;
    private static Skin touchPadSkin;
    private static Touchpad touchpad, moveTouchPad;
    private static Stage stage;
    private static Table table;
    private static boolean fired, turn;
    public GameScreen(MyGdxGame myGame) {
        fired = false;
        turn = true;
        GameScreen.myGame = myGame;
        platform = new Terrain();
        player1 = new Tank(100, 0, 5, false);
        player2 = new Tank(-300, 0, 3, true);
        backGround = new TextureRegion(new Texture("BACKGROUND/bg6.png"));
        missile = new Missile();
        buttonCreator = myGame.buttonCreator;
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
        table.add(touchpad).size(Gdx.graphics.getWidth() * 7 / 64f, Gdx.graphics.getHeight() * 7 / 36f).padRight(Gdx.graphics.getWidth() * 15 / 64f);
        table.add(buttonCreator.addFireButton()).size(Gdx.graphics.getWidth() * 6 / 64f, Gdx.graphics.getHeight() * 4 / 36f).padRight(Gdx.graphics.getWidth() * 15 / 64f);
        table.add(moveTouchPad).size(Gdx.graphics.getWidth() * 15 / 128f, Gdx.graphics.getHeight() * 7 / 72f);
        myGame.multiplexer.addProcessor(stage);
        platform.renderBody(myGame.world);
    }

    public static void setFired(boolean fired) {
        GameScreen.fired = fired;
    }

    @Override
    public void show() {
        buttonCreator.addPauseButton();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        myGame.batch.setProjectionMatrix(myGame.gamCam.combined);
        myGame.gamCam.update();
        myGame.batch.begin();
        myGame.batch.draw(backGround, -Utils.getWidth() / 2f, -Utils.getHeight() / 2f, Utils.getWidth(), Utils.getHeight());
        myGame.batch.end();
        myGame.polyBatch.begin();
        platform.renderTexture(myGame.polyBatch);
        myGame.polyBatch.end();
        myGame.batch.begin();
        player1.update(myGame);
        player2.update(myGame);
        platform.update();
        if (!fired) {
            if (turn) player2.move(touchpad,  moveTouchPad.getKnobPercentX());
            else player1.move(touchpad, moveTouchPad.getKnobPercentX());
        } else {
//            Utils.writes(player1);
            if (turn) missile.update(myGame, player2);
            else missile.update(myGame, player1);
        }
        moveTouchPad.draw(myGame.batch,1);
        myGame.batch.end();
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        stage.act(delta);
        stage.draw();
        buttonCreator.render(delta);
        updateWorld();
        myGame.debugRenderer.render(myGame.world, myGame.gamCam.combined);
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
            myGame.world.step(Utils.getTimeStep(), Utils.getVelocityIterations(), Utils.getPositionIterations());
            accuStep -= Utils.getTimeStep();
        }
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
        myGame.world.dispose();
        myGame.debugRenderer.dispose();
        myGame.batch.dispose();
        stage.dispose();
        myGame.buttonCreator.dispose();
        myGame.polyBatch.dispose();
    }
}
