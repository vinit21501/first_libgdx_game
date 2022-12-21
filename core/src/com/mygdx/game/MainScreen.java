package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MainScreen implements Screen {
    private MyGdxGame myGame;
    private TextureRegion backGround;
    private ButtonCreator buttonCreator;
    MainScreen(MyGdxGame game) {
        myGame = game;
        buttonCreator = myGame.getButtonCreator();
        backGround = new TextureRegion(new Texture("BACKGROUND/mainMenu.png"));
    }

    @Override
    public void show() {
        buttonCreator.addNewGameButton();
        buttonCreator.addResumeButton();
        buttonCreator.addLoadButton();
        buttonCreator.addExitButton();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        myGame.getBatch().setProjectionMatrix(myGame.getGamCam().combined);
        myGame.getGamCam().update();
        myGame.getBatch().begin();
        myGame.getBatch().draw(backGround, -Utils.getWidth() / 2f , -Utils.getHeight() / 2f, Utils.getWidth(), Utils.getHeight());
        myGame.getBatch().end();
        buttonCreator.render(delta);
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
        show();
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        myGame.getBatch().dispose();
        buttonCreator.dispose();
    }
}
