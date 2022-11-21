package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GameScreen implements Screen {
    final MyGdxGame myGame;
    TextureRegion backGround;
    private Missile missile;
    private Terrain platform;
    private float accumulator;
    Tank player1;
    Tank player2;
    public GameScreen(MyGdxGame myGame) {
        this.myGame = myGame;
        platform = new Terrain();
        player1 = new Tank(200, 0, 2, true);
        player2 = new Tank(-200, 0, false);
        accumulator = 1;
        backGround = new TextureRegion(new Texture("BACKGROUND/bg3.png"));
        missile = new Missile(0, 0, 1000, 60);
    }

    @Override
    public void show() {
        platform.update();
        platform.render(myGame.world);
        missile.render(myGame.world);
        player1.render(myGame.world);
        player2.render(myGame.world);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        myGame.gamCam.update();
//        myGame.batch.setProjectionMatrix(myGame.gamCam.combined);
        myGame.batch.begin();
        myGame.batch.draw(backGround, -Utils.width / 2, -Utils.height / 2);
        missile.update(myGame.batch);
        player1.update(myGame.batch);
        player1.move();
        player2.update(myGame.batch);
//        player2.move();
        myGame.batch.end();
        myGame.debugRenderer.render(myGame.world, myGame.gamCam.combined);
        update(delta);
    }

    public void update(float deltaTime) {
        float frameTime = Math.min(deltaTime, 0.25f);
        accumulator += frameTime;
        while (accumulator >= Utils.TIME_STEP) {
            accumulator -= Utils.TIME_STEP;
            myGame.world.step(Utils.TIME_STEP, Utils.VELOCITY_ITERATIONS, Utils.POSITION_ITERATIONS);
        }
    }

    @Override
    public void resize(int width, int height) {
        myGame.scalePort.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        myGame.dispose();
    }
}
