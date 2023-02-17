package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.mygdx.game.extra.CtrlAssets;
import com.mygdx.game.screens.GameScreen;

public class MainGame extends Game {
    public CtrlAssets assetManager;

    public GameScreen gameScreen;

    @Override
    public void create() {
        this.assetManager = new CtrlAssets();
        this.gameScreen = new GameScreen(this);
        setScreen(this.gameScreen);
    }
}
