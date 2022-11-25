package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class Button {
    private final TextButton.TextButtonStyle buttonStyle;
    private TextureRegionDrawable up, down, pause;
    private MyGdxGame mygame;
    public Stage stage;
    private float buttonHeight, buttonWidth;
    private Table table;
    private boolean setter, resize;
    private TextButton newGame, exitButton, loadButton, resumeButton, mainMenuButton, pauseButton;
    Button(MyGdxGame mygame) {
        this.mygame = mygame;
        up = new TextureRegionDrawable(new TextureRegion(new Texture("BUTTON/up.png")));
        down = new TextureRegionDrawable(new TextureRegion(new Texture("BUTTON/down.png")));
        pause = new TextureRegionDrawable(new TextureRegion(new Texture("BUTTON/pausebutton.png")));
        table = new Table();
        setter = false;
        resize = false;
        stage = new Stage(new ScreenViewport());
//        stage = new Stage(new StretchViewport(1280, 720));
        stage.addActor(table);
        buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = mygame.font;
        buttonStyle.up = up;
        buttonStyle.down = down;
        buttonHeight = Utils.buttonHeight;
        buttonWidth = Utils.buttonWidth;
        mygame.multiplexer.addProcessor(stage);
    }
    public void addPauseButton() {
        buttonStyle.up = pause;
        buttonStyle.down = pause;
        pauseButton = new TextButton("", buttonStyle);
        pauseButton.setSize(Gdx.graphics.getWidth() / 100f * 4f, Gdx.graphics.getHeight() / 100f * 6f);
        pauseButton.setPosition(0, Gdx.graphics.getHeight() / 100f * 90f);
        setter = true;
        stage.clear();
        stage.addActor(pauseButton);
        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buttonStyle.up = up;
                buttonStyle.down = down;
                stage.clear();
                stage.addActor(table);
                setter = false;
                resize = true;
                if (mygame.pauseMenu == null) mygame.pauseMenu = new PauseMenu(mygame);
                mygame.setScreen(mygame.pauseMenu);
            }
        });
    }
    public void addNewGameButton() {
        newGame = new TextButton("NEW GAME", buttonStyle);
        table.add(newGame).space(Utils.buttonPadding).row();
        newGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                table.reset();
                resize = true;
//                mygame.gameScreen.dispose();
                mygame.gameScreen = new GameScreen(mygame);
                mygame.setScreen(mygame.gameScreen);
            }
        });
    }
    public void addMainMenuButton() {
        mainMenuButton = new TextButton("MAIN MENU", buttonStyle);
        table.add(mainMenuButton).space(Utils.buttonPadding).row();
        mainMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                table.reset();
                resize = true;
                if (mygame.mainScreen == null) mygame.mainScreen = new MainScreen(mygame);
                mygame.setScreen(mygame.mainScreen);
            }
        });
    }
    public void addLoadButton() {
        loadButton = new TextButton("LOAD GAME", buttonStyle);
        table.add(loadButton).space(Utils.buttonPadding).row();
        loadButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (mygame.gameScreen == null) mygame.gameScreen = new GameScreen(mygame);
                mygame.setScreen(mygame.gameScreen);
            }
        });
    }
    public void addExitButton() {
        exitButton = new TextButton("QUIT", buttonStyle);
        table.add(exitButton).space(Utils.buttonPadding).row();
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
    }
    public void addResumeButton() {
        resumeButton = new TextButton("RESUME GAME", buttonStyle);
        table.add(resumeButton).space(Utils.buttonPadding).row();
        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                table.reset();
                resize = true;
                if (mygame.gameScreen == null) mygame.gameScreen = new GameScreen(mygame);
                mygame.setScreen(mygame.gameScreen);
            }
        });
    }
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        if (setter) {
            pauseButton.setSize(width / 100f * 4f, height / 100f * 6f);
            pauseButton.setPosition(0, Gdx.graphics.getHeight() / 100f * 90);
        }
        else {
            table.setSize(width, height);
            for(Cell t: table.getCells()) {
                t.size(width / 100f * 25, height / 100f * 10);
            }
        }
    }
    public void render(float delta) {
        if (resize) {
            stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
            table.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            for(Cell t: table.getCells()) {
                t.size(Gdx.graphics.getWidth() / 100f * 25, Gdx.graphics.getHeight() / 100f * 10);
            } resize = false;
        }
        stage.act(delta);
        stage.draw();
    }
    public void dispose() {
        stage.dispose();
    }
}
