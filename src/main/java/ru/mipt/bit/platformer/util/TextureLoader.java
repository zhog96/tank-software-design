package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;

import java.util.HashMap;

public final class TextureLoader implements Disposable {
    private final HashMap<String, Texture> textures = new HashMap<>();

    public Texture load(String path) {
        Texture texture = textures.get(path);
        if (texture == null) {
            texture = new Texture(path);
            textures.put(path, texture);
        }
        return texture;
    }

    @Override
    public void dispose() {
        textures.forEach((path, texture) -> texture.dispose());
    }
}
