package com.mygdx.game.screens;

import static com.mygdx.game.extra.Util.WORLD_HEIGHT;
import static com.mygdx.game.extra.Util.WORLD_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.MainGame;

public class StartScreen extends BaseScreen {
    private Stage sStage;
    private World sWorld;
    private Music sMusic;
    private Image sBackground;
    private Image sTocaEmpezar;
    private Image sMainCar;
    private Box2DDebugRenderer sDebugRenderer;
    private OrthographicCamera sWorldCamera;

    public StartScreen(MainGame mainGame){
        super(mainGame);

        this.sWorld = new World(new Vector2(0, -10), true);
        FitViewport fitViewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT);
        this.sStage = new Stage(fitViewport);

        this.sMusic = this.mainGame.assetManager.getMusic();
        this.sWorldCamera = (OrthographicCamera) this.sStage.getCamera();
        this.sDebugRenderer = new Box2DDebugRenderer();
    }

    public void addBackground(){
        this.sBackground = new Image(mainGame.assetManager.getBackground());
        this.sBackground.setPosition(0,0);
        this.sBackground.setSize(WORLD_WIDTH, WORLD_HEIGHT);
        this.sStage.addActor(this.sBackground);
    }

    public void addTocaEmpezar(){
        this.sTocaEmpezar = new Image(mainGame.assetManager.getTocaEmpezar());
        this.sTocaEmpezar.setPosition(0.675f,3.5f);
        this.sTocaEmpezar.setSize(3.5f, 3.5f);
        this.sStage.addActor(this.sTocaEmpezar);
    }

    public void addMainCar(){
        this.sMainCar = new Image(mainGame.assetManager.getMainCar());
        this.sMainCar.setPosition(1.9f, 1f);
        this.sMainCar.setSize(1f, 1f);
        this.sStage.addActor(this.sMainCar);
    }

    @Override
    public void show() {
        addBackground();
        addMainCar();
        addTocaEmpezar();

        this.sMusic.setLooping(true);
        this.sMusic.setVolume(0.15f);

        this.sMusic.play();
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.sStage.act();
        this.sWorld.step(delta, 6, 2);
        this.sStage.draw();
        this.sDebugRenderer.render(this.sWorld, this.sWorldCamera.combined);

        boolean touched = Gdx.input.justTouched();
        if(touched){
            this.sStage.addAction(Actions.sequence(Actions.delay(0f), Actions.run(new Runnable() {
                @Override
                public void run() {
                    mainGame.setScreen(mainGame.gameScreen);
                }
            })));
        }
    }

    @Override
    public void dispose(){
        this.sStage.dispose();
        this.sWorld.dispose();
    }

    @Override
    public void hide(){
        this.sMusic.stop();
    }

}
