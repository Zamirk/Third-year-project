package game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class FontUtils {

    public static BitmapFont generateFont(String internalPath, int size, Color color) {
        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal(internalPath));
        BitmapFont font = null;
        FreeTypeFontGenerator.FreeTypeFontParameter xxx = new FreeTypeFontGenerator.FreeTypeFontParameter();
        xxx.color = color;
        xxx.size = size;

        font = gen.generateFont(xxx);
        font.setColor(color);
        font.setUseIntegerPositions(false);
        font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        gen.dispose();
        xxx = null;
        return font;
    }
}
