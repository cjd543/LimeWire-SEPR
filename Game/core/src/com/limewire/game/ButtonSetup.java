package com.limewire.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class ButtonSetup extends Game{
    Button button;
    Texture upTexture;
    Texture downTexture;

    public Button setup(String buttonname){
        String uptexture_name = buttonname + "-up.png";
        String downtexture_name = buttonname + "-down.png";
        upTexture = new Texture(Gdx.files.internal(uptexture_name));
        downTexture = new Texture(Gdx.files.internal(downtexture_name));
        Button.ButtonStyle style = new Button.ButtonStyle();
        style.up = new TextureRegionDrawable(new TextureRegion(upTexture));
        style.down = new TextureRegionDrawable(new TextureRegion(downTexture));
        button = new Button(style);
        return button;
    }

}
