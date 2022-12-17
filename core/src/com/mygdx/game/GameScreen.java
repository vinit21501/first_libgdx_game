package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import sun.jvm.hotspot.ui.ObjectHistogramPanel;

import java.awt.*;
import java.security.Key;

public class GameScreen implements Screen {
    private final MyGdxGame myGame;
    private final TextureRegion backGround;
    private final Missile missile;
    private final Terrain platform;
    private final Tank player1;
    private final Tank player2;
    private final Button button;
    private Touchpad.TouchpadStyle touchpadStyle;
    private Skin touchPadSkin;
    private Touchpad touchpad;
    private float deltaX, deltaY;
    public GameScreen(MyGdxGame myGame) {
        this.myGame = myGame;
        platform = new Terrain();
        player1 = new Tank(300, 0, 5, false);
        player2 = new Tank(-200, 0, 6, true);
        backGround = new TextureRegion(new Texture("BACKGROUND/bg6.png"));
        missile = new Missile(300, 100, 100, -60);
        button = myGame.button;
        Utils.setAccumulator(0);
        touchPadSkin = new Skin();
        touchPadSkin.add("background", new Texture("BUTTON/TouchpadButton.png"));
        touchPadSkin.add("knob", new Texture("BUTTON/TouchpadKnob.png"));
        touchpadStyle = new Touchpad.TouchpadStyle(touchPadSkin.getDrawable("background"), touchPadSkin.getDrawable("knob"));
        touchpad = new Touchpad(100, touchpadStyle);
        touchpad.setBounds(400, -300, 150, 150);
        myGame.button.getStage().addActor(touchpad);
        touchpad.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                deltaX = ((Touchpad) actor).getKnobPercentX();
                deltaY = ((Touchpad) actor).getKnobPercentY();
                Gdx.app.log("point get", "delX" + deltaX + "delY" + deltaY);
            }
        });
        platform.renderBody(myGame.world);
        missile.render(myGame.world);
        player1.render(myGame.world);
        player2.render(myGame.world);
    }

    @Override
    public void show() {
        button.addPauseButton();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        myGame.batch.setProjectionMatrix(myGame.gamCam.combined);
        myGame.gamCam.update();
        myGame.batch.begin();
        myGame.batch.draw(backGround, -Utils.width / 2f, -Utils.height / 2f, Utils.width, Utils.height);
        myGame.batch.end();
        myGame.polyBatch.begin();
        platform.renderTexture(myGame.polyBatch);
        myGame.polyBatch.end();
        myGame.batch.begin();
        missile.update(myGame.batch);
        player1.update(myGame.batch);
        player2.update(myGame.batch);
        platform.update();
//        player1.move();
        player2.move(deltaX, deltaY);
//        touchpad.draw(myGame.batch, 1);
//        touchpad.
        myGame.batch.end();
        button.render(delta);
//        updateWorld();
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) updateWorld();
//        if (Gdx.input.isKeyPressed(Input.Keys.F)) myGame.world.dispose();
        myGame.debugRenderer.render(myGame.world, myGame.gamCam.combined);
    }

    @Override
    public void resize(int width, int height) {
        myGame.resize(width, height);
    }
    public void updateWorld() {
        float accuStep = Utils.getAccumulator();
        float frameTime = Math.min(Gdx.graphics.getDeltaTime(), 0.25f);
        accuStep += frameTime;
        while (accuStep >= Utils.TIME_STEP) {
            myGame.world.step(Utils.TIME_STEP, Utils.VELOCITY_ITERATIONS, Utils.POSITION_ITERATIONS);
            accuStep -= Utils.TIME_STEP;
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
        myGame.button.dispose();
        myGame.polyBatch.dispose();
    }
}
