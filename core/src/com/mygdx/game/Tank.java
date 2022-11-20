package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;

public class Tank {
    Body body;
    BodyDef bodyDef;
    Texture texture;
    TextureRegion textRegion;
    float length;
    float breadth;
    float density;
    Tank(int type) {
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(200, 200);
        if (type >= 1 && type <= 5) texture = new Texture("TANKS/tank" + type + ".png");
        else texture = new Texture("TANKS/tank1.png");
        textRegion = new TextureRegion(texture);
        length = 81;
        breadth = 44;
        density = 100f;
    }
    Tank() {
        this(3);
    }
    public void render(World world) {
        body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(length / 2,breadth / 2);
        body.createFixture(shape, density);
        body.setGravityScale(0.5f);
        shape.dispose();
    }
    public void move() {

    }
    public void update(SpriteBatch batch) {
        batch.draw(textRegion, body.getPosition().x - length / 2, body.getPosition().y - breadth / 2,length / 2, breadth / 2, length, breadth, 1, 1, (float) Math.toDegrees(body.getAngle()));
    }
}
