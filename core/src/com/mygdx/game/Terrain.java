package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.physics.box2d.*;

public class Terrain {
    private float[] heights;
    private float SLICE_WIDTH;
    private Body body;
    private BodyDef bodyDef;
    private TextureRegion textRegion;
    private Texture texture;
    private float[] actualHeight;
    private PolygonRegion polygonRegion;
    private PolygonSprite poly;
    private void createSlice(float x, int index, int scale) {
        heights[index] = x * scale;
        heights[index + 1] = -100 + ((float) (-0.143 * Math.sin(1.75 * (x + 1.73)) - 0.180 * Math.sin(2.96 * (x+4.98)) - 0.012 * Math.sin(6.23 * (x+3.17)) + 0.088 * Math.sin(8.07 * (x+4.63))) * scale);
    }
    Terrain() {
        SLICE_WIDTH = 0.05f;
        heights = new float[256];
        int index = 0;
        for (float x = -3.2f; x < 3.2 - 0.01; x += SLICE_WIDTH){
            createSlice(x, index, 215);
            index += 2;
        }
        heights[250] = Utils.getWidth() / 2;
        heights[251] = -Utils.getHeight() / 2;
        heights[252] = -Utils.getWidth() / 2;
        heights[253] = -Utils.getHeight() / 2;
        heights[254] = heights[0];
        heights[255] = heights[1];
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        texture = new Texture("BACKGROUND/terrain.png");
        texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        textRegion = new TextureRegion(texture);
        actualHeight = new float[256];
        for (int i = 0; i < 256; ++i) {
            if (i % 2 == 0) actualHeight[i] = (heights[i] + Utils.getWidth() / 2);
            else actualHeight[i] = (heights[i] + Utils.getHeight() / 2);
        }
        polygonRegion = new PolygonRegion(textRegion, actualHeight, new EarClippingTriangulator().computeTriangles(actualHeight).toArray());
        poly = new PolygonSprite(polygonRegion);
    }
    public void renderTexture(PolygonSpriteBatch batch) {
        poly.draw(batch);
    }
    public void renderBody(World world) {
        body = world.createBody(bodyDef);
        ChainShape shape = new ChainShape();
        shape.createChain(heights);
        FixtureDef terfixdef = new FixtureDef();
        terfixdef.shape = shape;
        terfixdef.restitution = 0;
        terfixdef.friction = 50;
        terfixdef.density = 0;
        body.createFixture(terfixdef);
        shape.dispose();
    }
    public void update() {
    }
    public void dispose() {
    }
}
