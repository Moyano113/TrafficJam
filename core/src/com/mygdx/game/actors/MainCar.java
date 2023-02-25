package com.mygdx.game.actors;

import static com.mygdx.game.extra.Util.RED_CAR;
import static com.mygdx.game.extra.Util.SCREEN_WIDTH;
import static com.mygdx.game.extra.Util.USER_RED_CAR;
import static com.mygdx.game.extra.Util.WORLD_WIDTH;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.utils.shapebuilders.BoxShapeBuilder;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.MainGame;
import com.mygdx.game.screens.GameScreen;

public class MainCar extends Actor {
    public static final int STATE_NORMAL = 0;
    public static final int STATE_CRASHED = 1;

    private TextureRegion carTexture;
    private Vector2 position;
    private World world;
    private Body body;
    private int state;
    private Fixture fixture;
    private Sound crashSound;

    public MainCar(World world, TextureRegion carTexture, Sound crashSound, Vector2 position){
        this.carTexture = carTexture;
        this.position = position;
        this.world = world;
        this.state = STATE_NORMAL;
        this.crashSound = crashSound;

        createBody();
        createFixture();
    }

    private void createBody(){
        BodyDef bodyDef = new BodyDef();

        bodyDef.position.set(this.position);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.gravityScale = 0f;
        this.body = this.world.createBody(bodyDef);
    }


    private void createFixture(){
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.4f, 0.5f);

        this.fixture = body.createFixture(shape, 8);
        shape.dispose();
    }

    public int getState(){
        return this.state;
    }

    public void detach(){
        this.world.destroyBody(this.body);
        this.body.destroyFixture(this.fixture);
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        setPosition(body.getPosition().x-0.5f, body.getPosition().y-0.5f);
        batch.draw(this.carTexture, getX(), getY(), 1, 1);
    }

    @Override
    public void act(float delta){
        boolean touched = Gdx.input.justTouched();
        float touchedX = Gdx.input.getX();

        if(touched){
            if(touchedX > Gdx.graphics.getWidth()/2){
                this.body.setTransform(body.getPosition().x += 0.9f, 1f, 0f);
            }else if(touchedX < Gdx.graphics.getWidth()/2){
                this.body.setTransform(body.getPosition().x -= 0.9f, 1f, 0f);
            }
        }
    }

}
