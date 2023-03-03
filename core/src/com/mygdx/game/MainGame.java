package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.mygdx.game.extra.CtrlAssets;
import com.mygdx.game.screens.GameOverScreen;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.StartScreen;

public class MainGame extends Game {
    public GameScreen gameScreen;
    public StartScreen startScreen;
    public GameOverScreen gameOverScreen;
    public CtrlAssets assetManager;


    @Override
    public void create() {
        this.assetManager = new CtrlAssets();
        this.gameScreen = new GameScreen(this);
        this.startScreen = new StartScreen(this);
        this.gameOverScreen = new GameOverScreen(this);
        setScreen(this.startScreen);
    }

}
