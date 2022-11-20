package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

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
    private float buttonHeight, buttonWidth;
    private Table table;
    Button(MyGdxGame mygame) {
        this.mygame = mygame;
        up = new TextureRegion(new Texture("BUTTON/up.png"));
        down = new TextureRegion(new Texture("BUTTON/down.png"));
        table = new Table();
        table.setPosition(Utils.width / 2, Utils.height / 2, Align.center);
        stage = new Stage();
        stage.addActor(table);
        buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = mygame.font;
        buttonStyle.up = new TextureRegionDrawable(up);
        buttonStyle.down = new TextureRegionDrawable(down);
        buttonHeight = Utils.buttonHeight;
        buttonWidth = Utils.buttonWidth;
        table.padTop(100);
        Gdx.input.setInputProcessor(stage);
    }
    public void addNewGameButton() {
        newGame = new TextButton("NEW GAME", buttonStyle);
        table.add(newGame).size(buttonWidth, buttonHeight).row();
        newGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mygame.setScreen(new GameScreen(mygame));
            }
        });
    }
    public void addLoadButton() {
        loadButton = new TextButton("LOAD GAME", buttonStyle);
        table.add(loadButton).size(buttonWidth, buttonHeight).row();

    }
    public void addExitButton() {
        exitButton = new TextButton("QUIT", buttonStyle);
        table.add(exitButton).size(buttonWidth, buttonHeight).row();
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
    }
    public void addResumeButton() {
        resumeButton = new TextButton("RESUME GAME", buttonStyle);
        table.add(resumeButton).size(buttonWidth, buttonHeight).row();
    }
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }
    public void dispose() {
        stage.dispose();
    }
}
