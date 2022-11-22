package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Button {
    private TextButton.TextButtonStyle buttonStyle;
    private TextButton newGame;
    private TextureRegion up;
    private TextureRegion down;
    private MyGdxGame mygame;

    private Stage stage;
    private TextButton exitButton;
    private TextButton loadButton;
    private TextButton resumeButton;
    private TextButton mainMenuButton;
    private float buttonHeight, buttonWidth;
    private Table table;
    Button(MyGdxGame mygame) {
        this.mygame = mygame;
        up = new TextureRegion(new Texture("BUTTON/up.png"));
        down = new TextureRegion(new Texture("BUTTON/down.png"));
        table = new Table();
        stage = new Stage(new ScreenViewport());
        stage.addActor(table);
        buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = mygame.font;
        buttonStyle.up = new TextureRegionDrawable(up);
        buttonStyle.down = new TextureRegionDrawable(down);
        buttonHeight = Utils.buttonHeight;
        buttonWidth = Utils.buttonWidth;
//        table.padTop(100);
        Gdx.input.setInputProcessor(stage);
    }
    public void addNewGameButton() {
        newGame = new TextButton("NEW GAME", buttonStyle);
        table.add(newGame).space(Utils.buttonPadding).row();
        newGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mygame.setScreen(new GameScreen(mygame));
            }
        });
    }
    public void addMainMenuButton() {
        mainMenuButton = new TextButton("MAIN MENU", buttonStyle);
        table.add(mainMenuButton).space(Utils.buttonPadding).row();
        mainMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mygame.setScreen(new MainScreen(mygame));
            }
        });
    }
    public void addLoadButton() {
        loadButton = new TextButton("LOAD GAME", buttonStyle);
        loadButton.sizeBy(10);
        table.add(loadButton).space(Utils.buttonPadding).row();
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
                mygame.setScreen(new GameScreen(mygame));
            }
        });
    }
    public void render(float delta) {
        table.setPosition(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, Align.center);
        table.debug();
        table.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        for (Cell t : table.getCells()) {
            t.size(Gdx.graphics.getWidth() / 100f *  buttonWidth, Gdx.graphics.getHeight() / 100f * buttonHeight);
        }
        mygame.font.getData().setScale(Gdx.graphics.getWidth() / 100f *  0.2f, Gdx.graphics.getHeight() / 100f * 0.2f);
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        stage.act(delta);
        stage.draw();
    }
    public void dispose() {
        stage.dispose();
    }
}
