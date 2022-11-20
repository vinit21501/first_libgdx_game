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
    private TextureRegion misssleTexture;
    private float speed;
    float x, y;
    boolean remove;
    float missleHeight, missleWidth, scale;
    private BodyDef misdef;
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
        misdef = new BodyDef();
        misdef.type = BodyDef.BodyType.DynamicBody;
//        misdef.position.set(400, 400);

//        misdef.bullet = true;
//        misdef.position.setAngleDeg(60);
        missle = world.createBody(misdef);

        shap = new PolygonShape();
//        float t = (float)Math.tanh(missle.getLinearVelocity().y / missle.getLinearVelocity().x);
        shap.setAsBox(missleWidth, missleHeight);
        missle.createFixture(shap, 1f);
        shap.dispose();
        missle.applyForce(new Vector2(1000, 50), new Vector2(missle.getPosition().x, missle.getPosition().y), true);
//        missle.applyForceToCenter(new Vector2(1000, 1), true);
//        missle.applyForceToCenter(2000, 0, true);
        missle.setLinearVelocity(speed, 0);
        missle.setGravityScale(5);
    }
    public void update(SpriteBatch batch) {

//        missle.setBullet(true);
//        for (JointEdge collide : missle.getJointList()) {
//            collide.joint.getBodyA().destroyFixture(misdef);
//        }
        missle.setTransform(missle.getPosition().x, missle.getPosition().y, missle.getAngle());
        batch.draw(misssleTexture, missle.getPosition().x - missleWidth / 2,
                missle.getPosition().y - missleHeight / 2, missleWidth / 2, missleHeight / 2,
                missleWidth, missleHeight, scale, scale, (float) Math.toDegrees(missle.getAngle()));
        if (this.x > Gdx.graphics.getHeight()) remove = true;
    }
}
