package com.mygdx.game.extra;

import static com.mygdx.game.extra.Util.ATLAS_MAP;
import static com.mygdx.game.extra.Util.BACKGROUND_IMAGE;
import static com.mygdx.game.extra.Util.BLUE_CAR;
import static com.mygdx.game.extra.Util.CONE;
import static com.mygdx.game.extra.Util.CRASH_SOUND;
import static com.mygdx.game.extra.Util.FONT_FNT;
import static com.mygdx.game.extra.Util.FONT_PNG;
import static com.mygdx.game.extra.Util.GAME_OVER;
import static com.mygdx.game.extra.Util.GREEN_CAR;
import static com.mygdx.game.extra.Util.MOTOR;
import static com.mygdx.game.extra.Util.MUSIC;
import static com.mygdx.game.extra.Util.POTHOLE;
import static com.mygdx.game.extra.Util.RED_CAR;
import static com.mygdx.game.extra.Util.TOCA_EMPEZAR;
import static com.mygdx.game.extra.Util.YELLOW_CAR;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

public class CtrlAssets {
    private AssetManager assetManager;
    private TextureAtlas textureAtlas;

    public CtrlAssets() {
        this.assetManager = new AssetManager();

        assetManager.load(ATLAS_MAP, TextureAtlas.class);
        assetManager.load(MUSIC, Music.class);
        assetManager.load(MOTOR, Music.class);
        assetManager.load(CRASH_SOUND, Sound.class);
        assetManager.load(GAME_OVER, Texture.class);
        assetManager.finishLoading();

        this.textureAtlas = assetManager.get(ATLAS_MAP);
    }

    public TextureRegion getBackground() {
        return this.textureAtlas.findRegion(BACKGROUND_IMAGE);
    }

    public TextureRegion getTocaEmpezar(){
        return this.textureAtlas.findRegion(TOCA_EMPEZAR);
    }

    public Texture getGameOver(){
        return new Texture(GAME_OVER);
    }

    public TextureRegion getMainCar(){return new TextureRegion(textureAtlas.findRegion(RED_CAR));}

    //Se asigna una textura aleatoria a cada coche que vaya a aparecer
    public TextureRegion getNormalCar(){
        TextureRegion carTexture = new TextureRegion(textureAtlas.findRegion(GREEN_CAR));
        int carType = MathUtils.random(3);

        if(carType == 0){
            carTexture = new TextureRegion(textureAtlas.findRegion(BLUE_CAR));
        } else if(carType == 1){
            carTexture = new TextureRegion(textureAtlas.findRegion(YELLOW_CAR));
        }
        return carTexture;
    }

    public TextureRegion getPothole(){
        return new TextureRegion(textureAtlas.findRegion(POTHOLE));
    }

    public TextureRegion getCone(){
        return new TextureRegion(textureAtlas.findRegion(CONE));
    }

    public Sound getCrashSound(){
        return this.assetManager.get(CRASH_SOUND);
    }

    public Music getMusic(){
        return this.assetManager.get(MUSIC);
    }

    public Music getMotor(){
        return this.assetManager.get(MOTOR);
    }

    public BitmapFont getFont(){
        return new BitmapFont(Gdx.files.internal(FONT_FNT), Gdx.files.internal(FONT_PNG), false);
    }
}
