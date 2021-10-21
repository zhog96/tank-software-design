package ru.mipt.bit.platformer.level;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class LevelFromFileGenerator implements LevelGenerator {
    private List<String> content;

    public LevelFromFileGenerator(String path) {
        try {
            content = Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> generate() {
        return content;
    }
}
