package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.box2d.*;


public class Missle {
    private Texture misTexture;
    private float speed;
    float x, y;
    boolean remove;
    private BodyDef misdef;
    private Body missle;
    private float degrees;
    private PolygonShape shap;
    Missle(float x, float y, float speed, float degrees) {
        this.speed = speed;
        this.degrees = degrees;
        this.x = x;
        this.y = y;
        remove = false;
        misTexture = new Texture("WEAPONS/missle1.png");
    }
    public void render(World world) {
        misdef = new BodyDef();
        misdef.type = BodyDef.BodyType.DynamicBody;
        misdef.position.set(400, 400);
//        misdef.bullet = true;
//        misdef.position.setAngleDeg(60);
        missle = world.createBody(misdef);

        shap = new PolygonShape();
//        float t = (float)Math.tanh(missle.getLinearVelocity().y / missle.getLinearVelocity().x);
        shap.setAsBox(20, 5);
        missle.createFixture(shap, 1f);
        shap.dispose();
    }
    public void update(float delta) {
        missle.setTransform(missle.getPosition(), missle.getAngle());
        missle.applyForce(new Vector2(1000, 0), new Vector2(missle.getPosition().x, missle.getPosition().y), true);
//        missle.applyForceToCenter(new Vector2(1000, 1), true);
//        missle.applyForceToCenter(2000, 0, true);
        missle.setLinearVelocity(speed, 0);
//        missle.setBullet(true);
//        for (JointEdge collide : missle.getJointList()) {
//            collide.joint.getBodyA().destroyFixture(misdef);
//        }
        missle.setGravityScale(5);
        if (this.x > Gdx.graphics.getHeight()) remove = true;
    }
}
