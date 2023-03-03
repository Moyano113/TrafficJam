package com.mygdx.game.actors;

import static com.mygdx.game.extra.Util.OBJECT_SPEED;
import static com.mygdx.game.extra.Util.USER_CONE;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Cone extends Actor {
    private TextureRegion coneTexture;
    private World world;
    private Body body;
    private Fixture fixture;

    public Cone(World world, TextureRegion coneTexture, Vector2 position){
        this.world = world;
        this.coneTexture = coneTexture;

        createBody(position);
        createFixture();
    }

    //Creacion de body
    private void createBody(Vector2 position){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position);
        bodyDef.type = BodyDef.BodyType.KinematicBody;

        body = world.createBody(bodyDef);
        body.setUserData(USER_CONE);
        body.setLinearVelocity(0, OBJECT_SPEED);
    }

    //Creacion de la caja de colisiones
    private void createFixture(){
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.25f, 0.25f);

        this.fixture = body.createFixture(shape, 8);
        shape.dispose();
    }

    //Comprueba si esta fuera de la pantalla
    public boolean isOutOfScreen(){
        return this.body.getPosition().y <= -1f;
    }

    //Para el movimiento del objeto
    public void stopCone(){
        body.setLinearVelocity(0,0);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition(body.getPosition().x-0.25f, body.getPosition().y-0.25f);
        batch.draw(this.coneTexture, getX(), getY(), 0.5f, 0.5f);
    }

    public void detach(){
        body.destroyFixture(this.fixture);
        world.destroyBody(this.body);
    }
}
