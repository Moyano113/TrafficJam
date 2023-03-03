package com.mygdx.game.actors;

import static com.mygdx.game.extra.Util.OBJECT_SPEED;
import static com.mygdx.game.extra.Util.USER_NORMAL_CAR;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class NormalCar extends Actor {
    private TextureRegion normalCarTexture;
    private World world;
    private Body body;
    private Fixture fixture;

    //Se crea un coche de textura aleatoria la cual ya se pasa por parametro
    public NormalCar(World world, TextureRegion normalCarTexture, Vector2 position){
        this.world = world;
        this.normalCarTexture = normalCarTexture;

        createBody(position);
        createFixture();
    }

    private void createBody(Vector2 position){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position);
        bodyDef.type = BodyDef.BodyType.KinematicBody;

        body = world.createBody(bodyDef);
        body.setUserData(USER_NORMAL_CAR);
        body.setLinearVelocity(0, OBJECT_SPEED);
    }

    private void createFixture(){
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.4f, 0.5f);

        this.fixture = body.createFixture(shape, 8);
        shape.dispose();
    }

    //Se comprueba si el coche ha salido de la pantalla
    public boolean isOutOfScreen(){
            return this.body.getPosition().y <= -1f;
    }

    //Para el coche
    public void stopNormalCar(){
        body.setLinearVelocity(0,0);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition(body.getPosition().x-0.5f, body.getPosition().y-0.5f);
        batch.draw(this.normalCarTexture, getX(), getY(), 1, 1);
    }

    public void detach(){
        body.destroyFixture(this.fixture);
        world.destroyBody(this.body);
    }
}
