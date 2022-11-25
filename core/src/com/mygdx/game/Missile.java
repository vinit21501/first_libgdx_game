package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.*;


public class Missile {
    private final TextureRegion misssleTexture;
    private final float speed;
    private float x, y;
    private boolean remove;
    private float missleHeight, missleWidth, scale;
    private Body missle;
    private float degrees;
    private PolygonShape shap;
    Missile(float x, float y, float speed, float degrees) {
        this.speed = speed;
        this.degrees = degrees;
        this.x = x;
        this.y = y;
        missleHeight = 5;
        missleWidth = 20;
        scale = 3;
        remove = false;
        misssleTexture = new TextureRegion(new Texture("WEAPONS/missile1.png"));
    }
    public void render(World world) {
        BodyDef misdef = new BodyDef();
        misdef.type = BodyDef.BodyType.DynamicBody;
        misdef.position.set(x, y);

//        misdef.bullet = true;
//        misdef.position.setAngleDeg(60);
        missle = world.createBody(misdef);

        shap = new PolygonShape();
        shap.setAsBox(missleWidth, missleHeight);
        missle.createFixture(shap, 0.2f);
        shap.dispose();
//        missle.applyForce(new Vector2(1000, 0), new Vector2(missle.getPosition().x, missle.getPosition().y), true);
//        missle.applyForceToCenter(new Vector2(1000, 1), true);
//        missle.applyForceToCenter(2000, 0, true);
        missle.setLinearVelocity(speed, 0);
        missle.setGravityScale(4);
    }
    public void update(SpriteBatch batch) {

//        missle.setBullet(true);
//        for (JointEdge collide : missle.getJointList()) {
//            collide.joint.getBodyA().destroyFixture(misdef);
//        }
        batch.draw(misssleTexture, missle.getPosition().x - missleWidth / 2f, missle.getPosition().y - missleHeight / 2f, missleWidth / 2f, missleHeight / 2f, missleWidth, missleHeight, 3f, 3f, (float) Math.toDegrees(missle.getAngle()));
    }
}
