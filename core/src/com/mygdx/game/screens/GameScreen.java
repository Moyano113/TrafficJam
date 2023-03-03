package com.mygdx.game.screens;

import static com.mygdx.game.extra.Util.OBJECT_SPEED;
import static com.mygdx.game.extra.Util.SCREEN_HEIGHT;
import static com.mygdx.game.extra.Util.SCREEN_WIDTH;
import static com.mygdx.game.extra.Util.WORLD_HEIGHT;
import static com.mygdx.game.extra.Util.WORLD_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.MainGame;
import com.mygdx.game.actors.Cone;
import com.mygdx.game.actors.MainCar;
import com.mygdx.game.actors.NormalCar;
import com.mygdx.game.actors.Pothole;


public class GameScreen extends BaseScreen implements ContactListener {
    private static float TIME_SPAWN_OBJECT = 2f;

    private float timeCreateObject;
    private int totalScore;
    private Array<Actor> arrayActors;
    private Stage stage;
    private MainCar mainCar;
    private NormalCar normalCar;
    private Cone cone;
    private Pothole pothole;
    private Image background;
    private World world;
    private Music music;
    private Music motor;
    private Sound crash;
    private BitmapFont score;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera worldCamera;
    private OrthographicCamera fontCamera;

    public GameScreen(MainGame mainGame){
        super(mainGame);

        this.world = new World(new Vector2(0, -10), true);
        this.world.setContactListener(this);
        FitViewport fitViewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT);
        this.stage = new Stage(fitViewport);

        this.arrayActors = new Array();
        this.timeCreateObject = 0f;

        this.music = this.mainGame.assetManager.getMusic();
        this.motor = this.mainGame.assetManager.getMotor();
        this.crash = this.mainGame.assetManager.getCrashSound();
        this.worldCamera = (OrthographicCamera) this.stage.getCamera();
        this.debugRenderer = new Box2DDebugRenderer();

        prepareScore();
    }

    //Inicializa la camara y el valor de la puntuacion
    public void prepareScore(){
        this.totalScore = 0;
        this.score = this.mainGame.assetManager.getFont();
        this.score.getData().scale(1f);

        this.fontCamera = new OrthographicCamera();
        this.fontCamera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
        this.fontCamera.update();
    }

    public void addBackground(){
        this.background = new Image(mainGame.assetManager.getBackground());
        this.background.setPosition(0,0);
        this.background.setSize(WORLD_WIDTH, WORLD_HEIGHT);
        this.stage.addActor(this.background);
    }

    public void addMainCar(){
        TextureRegion mainCarSprite = mainGame.assetManager.getMainCar();
        Sound sound = mainGame.assetManager.getCrashSound();
        this.mainCar = new MainCar(this.world, mainCarSprite, sound, new Vector2(2.4f, 1f));
        this.stage.addActor(this.mainCar);
    }

    //En funcion de que numero aleatorio aparezca, el coche saldra por un carril u otro
    public void addNormalCar(){
        TextureRegion normalCarSprite = mainGame.assetManager.getNormalCar();
        int lane = MathUtils.random(3);
        Vector2 lanePosition = new Vector2(2.4f, 8f);

        if(lane == 0){
            lanePosition = new Vector2(1.48f, 8f);
        } else if(lane == 1){
            lanePosition = new Vector2(3.35f, 8f);
        }

        this.normalCar = new NormalCar(this.world, normalCarSprite, lanePosition);
        arrayActors.add(this.normalCar);
        this.stage.addActor(this.normalCar);
    }

    public void addCone(){
        TextureRegion coneSprite = mainGame.assetManager.getCone();
        int lane = MathUtils.random(3);
        Vector2 lanePosition = new Vector2(2.4f, 8f);

        if(lane == 0){
            lanePosition = new Vector2(1.48f, 8f);
        } else if(lane == 1){
            lanePosition = new Vector2(3.35f, 8f);
        }

        this.cone = new Cone(this.world, coneSprite, lanePosition);
        arrayActors.add(this.cone);
        this.stage.addActor(this.cone);
    }

    public void addPothole(){
        TextureRegion potholeSprite = mainGame.assetManager.getPothole();
        int lane = MathUtils.random(3);
        Vector2 lanePosition = new Vector2(2.4f, 8f);

        if(lane == 0){
            lanePosition = new Vector2(1.48f, 8f);
        } else if(lane == 1){
            lanePosition = new Vector2(3.35f, 8f);
        }

        this.pothole = new Pothole(this.world, potholeSprite, lanePosition);
        arrayActors.add(this.pothole);
        this.stage.addActor(this.pothole);
    }

    //Tope derecho
    public void addRightSidewalk(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(4.8f, WORLD_HEIGHT*0.5f);
        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1f, WORLD_HEIGHT);
        body.createFixture(shape, 3);
        shape.dispose();
    }

    //Tope izquierdo
    public void addLeftSidewalk(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0f, WORLD_HEIGHT*0.5f);
        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1f, WORLD_HEIGHT);
        body.createFixture(shape, 3);
        shape.dispose();
    }

    //Añade coche, cono u hoyo aleatoriamente
    public void addObject(float delta){
        int objectType;

        if(mainCar.getState() == MainCar.STATE_NORMAL){
            this.timeCreateObject += delta;
            if(this.timeCreateObject >= TIME_SPAWN_OBJECT){
                this.timeCreateObject -= TIME_SPAWN_OBJECT;
                objectType = MathUtils.random(3);
                switch (objectType){
                    case 0:
                        addNormalCar();
                        break;
                    case 1:
                        addCone();
                        break;
                    case 2:
                        addPothole();
                        break;
                }
            }
        }
    }

    public void removeObject(){

        for(Actor a: arrayActors){
            if(!world.isLocked()){
                if(a instanceof NormalCar){
                    if(((NormalCar)a).isOutOfScreen()){
                        ((NormalCar) a).detach();
                        ((NormalCar) a).remove();
                        arrayActors.removeValue(a, false);
                        this.totalScore ++;
                        OBJECT_SPEED -= 0.15f;
                        if(TIME_SPAWN_OBJECT > 0.7f){
                            TIME_SPAWN_OBJECT -= 0.05f;
                        }
                    }
                }else if(a instanceof Cone){
                    if(((Cone)a).isOutOfScreen()){
                        ((Cone) a).detach();
                        ((Cone) a).remove();
                        arrayActors.removeValue(a, false);
                        this.totalScore ++;
                        OBJECT_SPEED -= 0.15f;
                        if(TIME_SPAWN_OBJECT > 0.7f){
                            TIME_SPAWN_OBJECT -= 0.05f;
                        }
                    }
                }else if(a instanceof Pothole){
                    if(((Pothole)a).isOutOfScreen()){
                        ((Pothole) a).detach();
                        ((Pothole) a).remove();
                        arrayActors.removeValue(a, false);
                        this.totalScore ++;
                        OBJECT_SPEED -= 0.15f;
                        if(TIME_SPAWN_OBJECT > 0.7f){
                            TIME_SPAWN_OBJECT -= 0.05f;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void show() {
        addBackground();
        addMainCar();
        addRightSidewalk();
        addLeftSidewalk();

        OBJECT_SPEED = -1f;
        TIME_SPAWN_OBJECT = 2f;

        this.music.setLooping(true);
        this.music.setVolume(0.15f);
        this.motor.setLooping(true);

        this.music.play();
        this.motor.play();
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        addObject(delta);

        this.stage.getBatch().setProjectionMatrix(worldCamera.combined);
        this.stage.act();
        this.world.step(delta, 6, 2);
        this.stage.draw();

        removeObject();

        this.stage.getBatch().setProjectionMatrix(this.fontCamera.combined);
        this.stage.getBatch().begin();
        this.score.draw(this.stage.getBatch(), totalScore+"", 125, 775);
        this.stage.getBatch().end();
    }

    @Override
    public void dispose(){
        this.stage.dispose();
        this.world.dispose();
    }

    @Override
    public void hide(){
        this.mainCar.detach();
        this.mainCar.remove();

        this.music.stop();
        this.motor.stop();
    }


    @Override
    public void beginContact(Contact contact) {
        this.mainCar.isCrashed();
        this.crash.play();
        this.music.stop();
        this.motor.stop();

        //Para los objetos
        for(Actor a: arrayActors){
            if(a instanceof NormalCar){
                ((NormalCar) a).stopNormalCar();
            }else if(a instanceof Cone){
                ((Cone) a).stopCone();
            }else if(a instanceof Pothole){
                ((Pothole) a).stopPothole();
            }
        }

        mainGame.setScreen(new GameOverScreen(mainGame));

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
