package com.mygdx.game.actors;

import static com.mygdx.game.extra.Util.RED_CAR;
import static com.mygdx.game.extra.Util.USER_RED_CAR;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.utils.shapebuilders.BoxShapeBuilder;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class MainCar extends Actor {
    private static final int STATE_NORMAL = 0;
    private static final int STATE_CRASHED = 1;
    private static final float SLIDE_SPEED = 50f;

    private TextureRegion carTexture;
    private Vector2 position;
    private World world;
    private Body body;
    private int state;
    Fixture fixture;

    public MainCar(World world, TextureRegion carTexture, Vector2 position){
        this.carTexture = carTexture;
        this.position = position;
        this.world = world;
        this.state = STATE_NORMAL;


        createBody();
        createFixture();
    }

    private void createBody(){
        BodyDef bodyDef = new BodyDef();

        bodyDef.position.set(this.position);
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        this.body = this.world.createBody(bodyDef);
    }

    private void createFixture(){
        CircleShape circle = new CircleShape();
        circle.setRadius(0.30f);

        this.fixture = this.body.createFixture(circle, 8);
        this.fixture.setUserData(USER_RED_CAR);

        circle.dispose();

    }

    public void detach(){
        this.world.destroyBody(this.body);
        this.body.destroyFixture(this.fixture);
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        setPosition(body.getPosition().x, body.getPosition().y);
        batch.draw(this.carTexture, getX(), getY(), 1, 1);
    }

    @Override
    public void act(float delta){
        boolean jump  = Gdx.input.justTouched();

        if(jump && this.state == STATE_NORMAL){
            this.body.setLinearVelocity(0,SLIDE_SPEED);
        }
    }
}