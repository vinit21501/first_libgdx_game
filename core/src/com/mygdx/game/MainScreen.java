package com.mygdx.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MainScreen implements Screen {
    private MyGdxGame myGame;
    private TextureRegion backGround;
    private Button button;
    MainScreen(MyGdxGame game) {
        myGame = game;
        backGround = new TextureRegion(new Texture("BACKGROUND/mainMenu.png"));
        button = game.button;
        button.addNewGameButton();
        button.addResumeButton();
        button.addLoadButton();
        button.addExitButton();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        myGame.batch.begin();
        myGame.batch.draw(backGround, -Utils.width / 2f , -Utils.height / 2f, Utils.width, Utils.height);
        myGame.batch.end();
        button.render(delta);
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

    }

    @Override
    public void dispose() {
        myGame.dispose();
    }
}
