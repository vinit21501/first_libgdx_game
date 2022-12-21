package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

public class Win implements Screen {
    private MyGdxGame myGame;
    private String msg;
    Win(MyGdxGame game) {
        myGame = game;
    }
    public void set(String msg) {
        this.msg = msg;
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
            myGame.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            myGame.setScreen(myGame.getMainScreen());
        }
        myGame.getBatch().setProjectionMatrix(myGame.getGamCam().combined);
        myGame.getGamCam().update();
        myGame.getBatch().begin();
        myGame.getFont().getData().setScale(Gdx.graphics.getWidth() / 200f, Gdx.graphics.getHeight() / 200f);
        myGame.getFont().draw(myGame.getBatch(), msg, -Gdx.graphics.getWidth() * 0.234f, 0);
        myGame.getFont().getData().setScale(Gdx.graphics.getWidth() / 100f * 0.2f, Gdx.graphics.getHeight() / 100f * 0.2f);
        myGame.getFont().draw(myGame.getBatch(), "please enter any to exit", -Gdx.graphics.getWidth() * 0.156f,-Gdx.graphics.getHeight() * 0.138f);
        myGame.getBatch().end();
    }

    @Override
    public void resize(int width, int height) {
        myGame.getScalePort().update(width, height);
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
        myGame.getBatch().dispose();
    }
}
