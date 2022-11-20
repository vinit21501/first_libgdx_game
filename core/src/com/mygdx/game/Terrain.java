package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.World;

public class Terrain {
    float[] heights;
    float SLICE_WIDTH;
    Body body;
    BodyDef bodyDef;
    TextureRegion textRegion;
    Texture texture;
    PolygonSpriteBatch polygonSpriteBatch;
    PolygonRegion polygonRegion;
    private void createSlice(float x, int index, int scale) {
        heights[index] = x * scale;
        heights[index + 1] = -100 + ((float) (-0.143 * Math.sin(1.75 * (x + 1.73)) - 0.180 * Math.sin(2.96 * (x+4.98)) - 0.012 * Math.sin(6.23 * (x+3.17)) + 0.088 * Math.sin(8.07 * (x+4.63))) * scale);
    }
    Terrain() {
        heights = new float[640];
        SLICE_WIDTH = 0.02f;
        int index = 0;
        for (float x = -3.2f; x < SLICE_WIDTH * 160 - 0.01; x += SLICE_WIDTH){
            createSlice(x, index, 200);
            index += 2;
        }
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(1, 1);
        texture = new Texture("BACKGROUND/bg1.png");
        textRegion = new TextureRegion(texture);
        short triangles[] = new EarClippingTriangulator().computeTriangles(heights).toArray();
        polygonRegion = new PolygonRegion(textRegion, heights, triangles);
        polygonSpriteBatch = new PolygonSpriteBatch();
    }
    public void render(World world) {
        body = world.createBody(bodyDef);
        ChainShape shape = new ChainShape();
        shape.createChain(heights);
        body.createFixture(shape, 1.0f);
        shape.dispose();
    }
    public void update() {
        polygonSpriteBatch.begin();
        polygonSpriteBatch.draw(polygonRegion, 1, 1);
        texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        polygonSpriteBatch.end();
    }
}
