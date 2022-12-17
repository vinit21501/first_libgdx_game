package com.mygdx.game;

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
    private boolean remove;
    private float missleHeight, missleWidth, scale, x, y;
    private Body missle;
    private float degrees;
    private PolygonShape shap;
    boolean right;
    Missile(float x, float y, float speed, float degrees) {
        this.speed = speed * 0.5f;
        this.degrees = degrees;
        this.x = x;
        this.y = y;
        missleHeight = 5;
        missleWidth = 20;
        scale = 3;
        if (degrees < 0) right = true;
        else right = false;
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
        Vector2 sped = new Vector2(0, speed);
        sped.rotateDeg(degrees);
        missle.setLinearVelocity(sped);
        Utils.setAccumulator(0.05f);
    }
    public void update(SpriteBatch batch) {

//        missle.setBullet(true);
//        for (JointEdge collide : missle.getJointList()) {
//            collide.joint.getBodyA().destroyFixture(misdef);
//        }
        missle.setTransform(missle.getPosition(), (float)Math.atan(missle.getLinearVelocity().y / missle.getLinearVelocity().x));
        if (right)
            batch.draw(misssleTexture, missle.getPosition().x - missleWidth / 2f, missle.getPosition().y - missleHeight / 2f, missleWidth / 2f, missleHeight / 2f, missleWidth, missleHeight, 3f, 3f, (float) Math.toDegrees(missle.getAngle()));
        else
            batch.draw(misssleTexture, missle.getPosition().x - missleWidth / 2f, missle.getPosition().y - missleHeight / 2f, missleWidth / 2f, missleHeight / 2f, missleWidth, missleHeight, 3f, 3f, (float) Math.toDegrees(missle.getAngle()) + 180);
    }
}
