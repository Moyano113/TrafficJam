package com.mygdx.game.extra;

import static com.mygdx.game.extra.Util.ATLAS_MAP;
import static com.mygdx.game.extra.Util.BACKGROUND_IMAGE;
import static com.mygdx.game.extra.Util.RED_CAR;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class CtrlAssets {
    private AssetManager assetManager;
    private TextureAtlas textureAtlas;

    public CtrlAssets() {
        this.assetManager = new AssetManager();

        assetManager.load(ATLAS_MAP, TextureAtlas.class);
        assetManager.finishLoading();

        this.textureAtlas = assetManager.get(ATLAS_MAP);
    }

    public TextureRegion getBackground() {
        return this.textureAtlas.findRegion(BACKGROUND_IMAGE);
    }

    public TextureRegion getMainCar(){
        return new TextureRegion(textureAtlas.findRegion(RED_CAR));
    }
}
