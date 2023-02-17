package com.mygdx.game.screens;

import static com.mygdx.game.extra.Util.WORLD_HEIGHT;
import static com.mygdx.game.extra.Util.WORLD_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.MainGame;
import com.mygdx.game.actors.Cone;
import com.mygdx.game.actors.MainCar;
import com.mygdx.game.actors.NormalCar;
import com.mygdx.game.actors.Pothole;

public class GameScreen extends BaseScreen{
    private Stage stage;
    private MainCar mainCar;
    private Image background;
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera ortCamera;
    NormalCar normalCar;
    Cone cone;
    Pothole porthole;

    public GameScreen(MainGame mainGame){
        super(mainGame);

        this.world = new World(new Vector2(0, -10), true);
        FitViewport fitViewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT);
        this.stage = new Stage(fitViewport);

        this.ortCamera = (OrthographicCamera) this.stage.getCamera();
        this.debugRenderer = new Box2DDebugRenderer();
    }

    public void addBackground(){
        this.background = new Image(mainGame.assetManager.getBackground());
        this.background.setPosition(0,0);
        this.background.setSize(WORLD_WIDTH, WORLD_HEIGHT);
        this.stage.addActor(this.background);
    }

    public void addMainCar(){
        TextureRegion mainCarSprite = mainGame.assetManager.getMainCar();
        this.mainCar = new MainCar(this.world, mainCarSprite, new Vector2(1.9f, 0.5f));
        this.stage.addActor(this.mainCar);
    }

    @Override
    public void show() {
        addBackground();
        addMainCar();
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.stage.act();
        this.world.step(delta, 6, 2);
        this.stage.draw();
        this.debugRenderer.render(this.world, this.ortCamera.combined);
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
    }

}
